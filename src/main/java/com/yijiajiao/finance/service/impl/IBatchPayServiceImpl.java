package com.yijiajiao.finance.service.impl;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.alipay.config.AlipayConfig;
import com.yijiajiao.finance.alipay.sign.RSA;
import com.yijiajiao.finance.alipay.util.AlipayCore;
import com.yijiajiao.finance.bean.*;
import com.yijiajiao.finance.bean.page.Pagination;
import com.yijiajiao.finance.bean.query.BatchPayDetailQuery;
import com.yijiajiao.finance.dao.*;
import com.yijiajiao.finance.service.IBatchPayService;
import com.yijiajiao.finance.util.Config;
import com.yijiajiao.finance.util.DateUtil;
import com.yijiajiao.finance.util.HttpClient;
import com.yijiajiao.finance.util.RandomUtil;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;


@Service("batchPayService")
public class IBatchPayServiceImpl implements IBatchPayService {
	
	private static Logger log = (Logger) LoggerFactory.getLogger(IBatchPayServiceImpl.class);
	@Autowired
	private IBatchPaymentDAO batchPaymentDAO;
	@Autowired
	private IBatchPayDetailDAO batchPayDetailDAO;
	@Autowired
	private IMoneyTimerDAO moneyTimerDAO;
	@Autowired
	private IUserAlipayDAO userAlipayDAO;
	@Autowired
	private ISettleAccountsDAO settleAccountsDAO;
	@Autowired
	private IFinanceLogDAO financeLogDAO;
	@Override
	@Transactional
	public ResultMapper batchPay(List<BatchPayDetail> batchPayDetails) {
		ResultMapper resultBean = new ResultMapper();
		try {
			if(batchPayDetails.size()>1000){
				resultBean.setFailMsg(SystemStatus.BATCH_NUM);
				return resultBean;
			}
			StringBuilder detail_data = new StringBuilder();
			String batch_no = DateUtil.getNowTime("yyyyMMddhhmmss")+ RandomUtil.getRandomCharNum(2);//获得唯一批次号
			BigDecimal batch_fee = BigDecimal.valueOf(0.00);
			int running = 1001;
			for(BatchPayDetail bpd :batchPayDetails){
				bpd.setBatch_no(batch_no);
				bpd.setRunning_no(batch_no+running);//生成流水号
				System.out.println("bpd2==="+bpd.getPayString());
				boolean is_insert=bpd.getRunning_no() ==null || "".equals(bpd.getRunning_no())||bpd.getProceeds_account() == null || "".equals(bpd.getProceeds_account())
						|| bpd.getProceeds_name()==null || "".equals(bpd.getProceeds_name())||bpd.getRemark()==null || "".equals(bpd.getRemark());
				if(is_insert){
					resultBean.setFailMsg(SystemStatus.BATCH_DETAIL_ERROR);
					return resultBean;
				}
				if(detail_data.length()>0){
					detail_data.append("|"+bpd.getPayString());		
				}else{
					detail_data.append(bpd.getPayString());	
				}
				batch_fee=batch_fee.add(bpd.getProceeds_fee());
				running++;
			}
			
			System.out.println("detail_data="+detail_data.toString());
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("account_name",AlipayConfig.account_name);
			sParaTemp.put("batch_fee", batch_fee+"");
			sParaTemp.put("batch_no",batch_no);
			sParaTemp.put("batch_num",batchPayDetails.size()+"");
			sParaTemp.put("detail_data", detail_data.toString());
			sParaTemp.put("email", AlipayConfig.email);
			sParaTemp.put("partner", AlipayConfig.partner);
			sParaTemp.put("pay_date", DateUtil.getNowTime("yyyyMMdd"));
			sParaTemp.put("_input_charset",AlipayConfig.input_charset);
			sParaTemp.put("notify_url", Config.getString("notify_url"));
			sParaTemp.put("service",Config.getString("service"));
			String content = AlipayCore.createLinkString(sParaTemp);
			System.out.println("content=="+content);
			String sign_content = RSA.sign(content, AlipayConfig.private_key, AlipayConfig.input_charset);
			System.out.println("sign_content=="+sign_content);
			String result = Config.getString("web_pay_url")+content+"&sign="+URLEncoder.encode( sign_content )+"&sign_type="+AlipayConfig.sign_type;
			log.info("result=="+result);
			resultBean.setSucResult(result);
			
			BatchPayment batchPayment = new BatchPayment();
			batchPayment.setBatch_no(batch_no);
			batchPayment.setBatch_fee(batch_fee);
			batchPayment.setBatch_num(batchPayDetails.size());
			batchPayment.setPay_date(DateUtil.getNowTime());
			batchPayment.setEmail(sParaTemp.get("email"));
			batchPayment.setPay_user_name(sParaTemp.get("account_name"));
			batchPaymentDAO.insertBatchPayment(batchPayment);
			log.info("batchPayment保存成功！"+batchPayment);
			DateUtil du = new DateUtil();
			Date beginMonth = du.calcBeginMonth(DateUtil.getNowTime());
			//上个月第一天
			String lasb = DateUtil.calcDate(beginMonth,DateUtil.YYYY_MM_DD, 2, -1);
			for(BatchPayDetail bpd :batchPayDetails){
				bpd.setStart_time(lasb);
				batchPayDetailDAO.updateNumber(bpd);
			}
			log.info("本批次发放工资的人数=="+batchPayDetails.size());
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return resultBean; 
	}
	@Override
	public BatchPayment getBatchPaymentByBatch_no(String batch_no) {
		BatchPayment batchPayment = batchPaymentDAO.selectBatchPaymentByBatch_no(batch_no);
		return batchPayment;
	}
	@Override
	@Transactional
	public void modBatchPayment(BatchPayment batchPayment) {
		BatchPayment batc = batchPaymentDAO.selectBatchPaymentByBatch_no(batchPayment.getBatch_no());
		if(batc == null){
			batchPaymentDAO.insertBatchPayment(batchPayment);
		}else{
			batc.setFail_details(batchPayment.getFail_details());
			batc.setIs_dispose(batchPayment.getIs_dispose());
			batc.setNotify_id(batchPayment.getNotify_id());
			batc.setNotify_time(batchPayment.getNotify_time());
			batc.setNotify_type(batchPayment.getNotify_type());
			batc.setPay_user_id(batchPayment.getPay_user_id());
			batc.setPay_account_no(batchPayment.getPay_account_no());
			batc.setSuccess_details(batchPayment.getSuccess_details());
			batchPaymentDAO.updateBatchPayment(batc);
		}
	}
	@Override
	public Pagination querySettleInfo(BatchPayDetailQuery batchPayDetailQuery) throws ParseException{
		DateUtil du = new DateUtil();
		Date beginMonth = du.calcBeginMonth(DateUtil.getNowTime());
		//上个月第一天
		String lasb = DateUtil.calcDate(beginMonth,DateUtil.YYYY_MM_DD, 2, -1);
		batchPayDetailQuery.setStart_time(lasb);
		List<BatchPayDetail> batchPayDetails=batchPayDetailDAO.getBatchPayDetailsByConditions(batchPayDetailQuery);
		int totalCount =batchPayDetailDAO.getCountByConditions(batchPayDetailQuery);
		Pagination pagination = new Pagination(batchPayDetailQuery.getPageNum(), batchPayDetailQuery.getPageSize(), totalCount, batchPayDetails);
		return pagination;
	}
	@Override
	@Transactional
	public void modBatchPayDetailAndMoneyTiemer(String batch_no) {
		batchPayDetailDAO.updateIsDispose(batch_no);//标记为1 表示已转账成功,同时标记收款成功时间
		List<BatchPayDetail>  batchPayDetails=batchPayDetailDAO.queryByBatch_no(batch_no);
		for(BatchPayDetail bpd :batchPayDetails){
			MoneyTimer moneyTimer = moneyTimerDAO.queryMoneyTimerByOpenId(bpd.getOpen_id());
			log.info("moneyTimer=="+moneyTimer);
			moneyTimer.setWithdrawalCash(moneyTimer.getWithdrawalCash().subtract(bpd.getProceeds_fee()));
			moneyTimer.setTotalMoney(moneyTimer.getVariableMoney().add(moneyTimer.getWithdrawalCash()));
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
			moneyTimerDAO.updateMoneyTimer(moneyTimer);
		}
	}
	@Override
	@Transactional
	public int getAndSaveSalary() throws ParseException {
		List<BatchPayDetail> batchPayDetails = new ArrayList<BatchPayDetail>();
		//获得所有拥有收入的教师的openid
		List<String> openIds = moneyTimerDAO.queryAllOpenIds();
		//获得上个月的第一天和最后一天
		DateUtil du = new DateUtil();
		Date beginMonth = du.calcBeginMonth(DateUtil.getNowTime());
		//上个月第一天
		String lasb = DateUtil.calcDate(beginMonth,DateUtil.YYYY_MM_DD, 2, -1);
		//上个月最后天
		String lase = DateUtil.calcDate(beginMonth, DateUtil.YYYY_MM_DD, 5, -1);
		//组合批量付款数据
		for(String openId : openIds){
			UserAlipay alipay = userAlipayDAO.queryUserAlipayByOpenId(openId);
			//调用用户信息接口获得教师姓名和手机号
			System.out.println("调用用户信息接口url="+Config.getString("user.server")+Config.getString("userinfo.url")+openId);
			String httpRest = HttpClient.httpRest(Config.getString("user.server"),Config.getString("userinfo.url")+openId, null, null, "GET");
			System.out.println("解析：httpRest=="+httpRest);
			JSONObject json = JSONObject.fromObject(httpRest);
			Object object = json.get("result");
			if(object.equals(null)) continue;
			JSONObject job = (JSONObject)object;
			String name = job.getString("name");
			String phone = job.getString("username");
			//计算上月收入，包括两部分 直接订单收入和每日课程结算收入
			BigDecimal income1=BigDecimal.valueOf(0.00);//直接订单收入
			BigDecimal income2=BigDecimal.valueOf(0.00); //每日课程结算收入
			try {
				income1 = financeLogDAO.queryTeacherIncomeForLastMonth(openId);
				if(income1==null) income1=BigDecimal.valueOf(0.00);
			} catch (Exception e) {
			}
			try {
				income2= settleAccountsDAO.queryTeacherIncomeForLastMonth(openId);
				if(income2 == null) income2=BigDecimal.valueOf(0.00);
			} catch (Exception e) {
			}
			System.out.println(income1+"----"+income2);
			System.out.println("teacher_income========="+income1.add(income2));
			if(income1.add(income2).doubleValue()<=0.00) continue;
			BatchPayDetail bpd = new BatchPayDetail();
			bpd.setTeacher_name(name);
			bpd.setPhone_num(phone);
			bpd.setStart_time(lasb);
			bpd.setEnd_time(lase);
			bpd.setOpen_id(openId);
			if(alipay != null){
				bpd.setProceeds_account(alipay.getAlipay_account());
				bpd.setProceeds_name(alipay.getAlipay_name());
			}
			bpd.setProceeds_fee(income1.add(income2));
			bpd.setProceeds_time(DateUtil.getNowTime());
			bpd.setRemark("教师课程收入");
			
			batchPayDetailDAO.insertBatchPayDetail(bpd);
			System.out.println("保存成功！");
		}
		return 1;
	}
	@Override
	@Transactional
	public ResultMapper querySettleInfoByDate(BatchPayDetailQuery batchPayDetailQuery) {
		ResultMapper resultBean = new ResultMapper();
		log.info("请求参数：date="+batchPayDetailQuery.getQueryDate()+",phone="+batchPayDetailQuery.getPhone_num()
				+",pageNum="+batchPayDetailQuery.getPageNum()+",pageSize="+batchPayDetailQuery.getPageSize());
		try {
			String pattern = DateUtil.StringPattern(batchPayDetailQuery.getQueryDate(), DateUtil.YYYY年MM月, DateUtil.YYYY_MM_DD);
			batchPayDetailQuery.setQueryDate(pattern);
			if(batchPayDetailQuery.getPageNum()==0) batchPayDetailQuery.setPageNum(1);
			if(batchPayDetailQuery.getPageSize() ==0) batchPayDetailQuery.setPageSize(20);
			int totalCount = batchPayDetailDAO.getCountByDateAndPhone(batchPayDetailQuery);
			System.out.println("总记录数totalCount=="+totalCount);
			List<BatchPayDetail> list =new ArrayList<BatchPayDetail>();
			list= batchPayDetailDAO.getBatchPayDetailsByConditions(batchPayDetailQuery);
			BigDecimal sumIncome=BigDecimal.valueOf(0.00);
			sumIncome= batchPayDetailDAO.getSumIncome(batchPayDetailQuery);
			if(list.size()>0){
				for(BatchPayDetail bpd :list){
					bpd.setStart_time(DateUtil.StringPattern(bpd.getStart_time(), DateUtil.YYYY_MM_DD, DateUtil.YYYY年MM月DD));
					bpd.setEnd_time(DateUtil.StringPattern(bpd.getEnd_time(), DateUtil.YYYY_MM_DD, DateUtil.YYYY年MM月DD));
				}
			}
			System.out.println("本期应付薪酬sumIncome="+sumIncome);
			Pagination page = new Pagination(batchPayDetailQuery.getPageNum(), batchPayDetailQuery.getPageSize(), totalCount, list);
			page.setTotalFee(sumIncome);
			resultBean.setSucResult(page);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return resultBean;
	}
	@Override
	@Transactional
	public ResultMapper querySettleInfoByDate(String queryDate) {
		ResultMapper resultBean = new ResultMapper();
		System.out.println("请求参数为queryDate=="+queryDate);
		BatchPayDetailQuery batchPayDetailQuery = new BatchPayDetailQuery();
		try {
			String pattern = DateUtil.StringPattern(queryDate, DateUtil.YYYY年MM月, DateUtil.YYYY_MM_DD);
			batchPayDetailQuery.setQueryDate(pattern);
			List<BatchPayDetail> list =new ArrayList<BatchPayDetail>();
			list= batchPayDetailDAO.getBatchPayDetailsByConditions(batchPayDetailQuery);
			System.out.println("总数量=="+list.size());
			if(list.size()>0){
				for(BatchPayDetail bpd :list){
					bpd.setStart_time(DateUtil.StringPattern(bpd.getStart_time(), DateUtil.YYYY_MM_DD, DateUtil.YYYY年MM月DD));
					bpd.setEnd_time(DateUtil.StringPattern(bpd.getEnd_time(), DateUtil.YYYY_MM_DD, DateUtil.YYYY年MM月DD));
				}
			}
			resultBean.setSucResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return resultBean;
	}
	@Override
	@Transactional
	public void saveBatchPayment(BatchPayment batchPayment) {
		batchPaymentDAO.insertBatchPayment(batchPayment);
		
	}
	
/*	public int getAndSaveSalary(String month) throws ParseException{
		DateUtil du = new DateUtil();
		String beginMonth = DateUtil.dateToString(du.calcBeginMonth(month), DateUtil.YYYY_MM_DD);
		String endMonth = DateUtil.dateToString(du.calcEndMonth(month), DateUtil.YYYY_MM_DD);
		//获得所有拥有收入的教师的openid
		List<String> openIds = moneyTimerDAO.queryAllOpenIds();
		//组合批量付款数据
		for(String openId : openIds){
			UserAlipay alipay = userAlipayDAO.queryUserAlipayByOpenId(openId);
			//调用用户信息接口获得教师姓名和手机号
			System.out.println("调用用户信息接口url="+Config.getString("user.server")+Config.getString("userinfo.url")+openId);
			String httpRest = HttpClient.httpRest(Config.getString("user.server"),Config.getString("userinfo.url")+openId, null, null, "GET");
			System.out.println("解析：httpRest=="+httpRest);
			JSONObject json = JSONObject.fromObject(httpRest);
			JSONObject object = (JSONObject) json.get("result");
			if(object.equals(null)) continue;
			String name = object.getString("name");
			String phone = object.getString("username");
			//计算上月收入，包括两部分 直接订单收入和每日课程结算收入
			double income1;//直接订单收入
			double income2; //每日课程结算收入
			QueryParam qp = new QueryParam();
			qp.setDate(beginMonth);
			qp.setOpenId(openId);
			try {
				income1 = financeLogDAO.queryTeacherIncomeByMonth(qp);
			} catch (Exception e) {
				e.printStackTrace();
				income1=0.0;
			}
			try {
				income2= settleAccountsDAO.queryTeacherIncomeByMonth(qp);
			} catch (Exception e) {
				income2=0.0;
			}
			System.out.println("teacher_income========="+(income1+income2));
			if((income1+income2)<=0) continue;
			BatchPayDetail bpd = new BatchPayDetail();
			bpd.setTeacher_name(name);
			bpd.setPhone_num(phone);
			bpd.setStart_time(beginMonth);
			bpd.setEnd_time(endMonth);
			bpd.setOpen_id(openId);
			if(alipay != null){
				bpd.setProceeds_account(alipay.getAlipay_account());
				bpd.setProceeds_name(alipay.getAlipay_name());
			}
			bpd.setProceeds_fee(income1+income2);
			bpd.setProceeds_time(DateUtil.getNowTime());
			bpd.setRemark("教师课程收入");
			
			batchPayDetailDAO.insertBatchPayDetail(bpd);
			System.out.println("保存成功！");
		}
		return 1;
	}*/
}
