package com.yijiajiao.finance.service.impl;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.*;
import com.yijiajiao.finance.bean.query.FinanceLogQuery;
import com.yijiajiao.finance.dao.*;
import com.yijiajiao.finance.service.IFinanceLogService;
import com.yijiajiao.finance.util.Config;
import com.yijiajiao.finance.util.DateUtil;
import com.yijiajiao.finance.util.HttpClient;
import com.yijiajiao.finance.util.JsonUtil;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;



@Service("financeLogService")
public class IFinanceLogServiceImpl implements IFinanceLogService {
	private static Logger log = (Logger) LoggerFactory.getLogger(IFinanceLogServiceImpl.class);
	@Autowired
	private IFinanceLogDAO financeLogDAO;
	@Autowired
	private IAnswerTimerDAO answerTimerDAO;
	@Autowired
	private IMoneyTimerDAO moneyTimerDAO;
	@Autowired
	private IWaresSlaveDAO waresSlaveDAO;
	@Autowired
	private ISettleAccountsDAO settleAccountsDAO;
	private final BigDecimal percentage =BigDecimal.valueOf(Config.getDouble("percentage"));//教师分成比例
	private final BigDecimal opercentage = BigDecimal.valueOf(1.00).subtract(percentage);//平台分成比例
	@Override
	@Transactional
	public ResultMapper addOrderFinanceLog(FinanceLog financeLog) {
		ResultMapper resultBean= new ResultMapper();
		// 通用属性赋值
		financeLog.setFinanceLogsType(1);// 表示订单记录
		if(financeLog.getTradeTime()==null)financeLog.setTradeTime(DateUtil.getNowTime());
		FinanceLog financeLog2 = financeLogDAO.queryFinanceLogByConditions(financeLog);
		if(financeLog2 != null ){//防止重复添加
			resultBean.setFailMsg(SystemStatus.LOG_AREADY_BEEN);
			return resultBean;
		}
		switch (financeLog.getCommodityType()) {
		case 1:// 课程
			financeLog.setTeacherIncome(financeLog.getTotalPrice().multiply(percentage));
			financeLog.setSystemIncome(financeLog.getTotalPrice().multiply(opercentage));
			boolean ifRefund = false;// 判断该订单是否涉及到退款
			if (financeLog.getCurriculumType() != 2)
				ifRefund = true;
			if (ifRefund) {
				System.out.println("涉及退款，存入ariableMoneyChange="+financeLog.getTeacherIncome());
				financeLog.setVariableMoneyChange(financeLog
						.getTeacherIncome());
			} else {
				System.out.println("不涉及退款，存入withdrawalcahs="+financeLog.getTeacherIncome());
				financeLog.setWithdrawalCashChange(financeLog
						.getTeacherIncome());
			}
			break;
		case 2:// 答疑
			financeLog.setSolutionId(financeLog.getCommodityId());
			financeLog.setTeacherIncome(financeLog.getTotalPrice().multiply(percentage));
			financeLog.setSystemIncome(financeLog.getTotalPrice().multiply(opercentage));
			financeLog.setWithdrawalCashChange(financeLog
					.getTeacherIncome());
			String user = HttpClient.httpRest(Config.getString("user.server"), Config.getString("userinfo.url")+financeLog.getOpenId(), null, null, "GET");
			JSONObject json = JSONObject.fromObject(user);
			JSONObject usermap = (JSONObject) json.get("result");
			String phone = (String) usermap.get("username");
			financeLog.setStudentPhoneNum(phone);
			user = HttpClient.httpRest(Config.getString("user.server"), Config.getString("userinfo.url")+financeLog.getSellOpenId(), null, null, "GET");
			json = JSONObject.fromObject(user);
			usermap = (JSONObject) json.get("result");
			phone = (String) usermap.get("username");
			financeLog.setTeacherPhoneNum(phone);
			saveOrUpdateAnswerTimer(financeLog);
			break;
		case 3:// 密卷

			break;
		case 4:// 诊断

			break;
		default:
			resultBean.setFailMsg(SystemStatus.COMMODITYTYPE_NOT_NULL);
			return resultBean;
		}
		// 对金额账户表的修改
		MoneyTimer moneyTimer = moneyTimerDAO
				.queryMoneyTimerByOpenId(financeLog.getSellOpenId());
		if (moneyTimer == null) {
			moneyTimer = new MoneyTimer();
			System.out.println(moneyTimer);
			moneyTimer.setOpenId(financeLog.getSellOpenId());
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
			moneyTimer.setVariableMoney(financeLog
					.getVariableMoneyChange());
			moneyTimer.setWithdrawalCash(financeLog
					.getWithdrawalCashChange());
			moneyTimer.setTotalSettleMoney(financeLog.getWithdrawalCashChange());
			moneyTimer.setTotalMoney(moneyTimer.getVariableMoney().add(moneyTimer.getWithdrawalCash()));
			moneyTimerDAO.saveMoneyTimer(moneyTimer);
			log.info("该用户无记录，进行记录添加" + moneyTimer);
		} else {
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
			moneyTimer.setVariableMoney(moneyTimer.getVariableMoney().add(financeLog.getVariableMoneyChange()));
			moneyTimer.setWithdrawalCash(moneyTimer.getWithdrawalCash().add(financeLog.getWithdrawalCashChange()));
			moneyTimer.setTotalMoney(moneyTimer.getVariableMoney().add(moneyTimer.getWithdrawalCash()));
			moneyTimer.setTotalSettleMoney(moneyTimer.getTotalSettleMoney().add(financeLog.getWithdrawalCashChange()));
			moneyTimerDAO.updateMoneyTimer(moneyTimer);
			log.info("金额修改后：variableMoney="
					+ moneyTimer.getVariableMoney()
					+ " withdrawalCash="
					+ moneyTimer.getWithdrawalCash() + "totalMoney="
					+ moneyTimer.getTotalMoney()+"TotalSettleMoney="+moneyTimer.getTotalSettleMoney());
		}
		financeLogDAO.saveFinanceLog(financeLog);// 保存财务记录
		log.info("保存的财务记录数据==" + financeLog);
		resultBean.setSucResult("保存成功！");
		return resultBean;
	}
	private void saveOrUpdateAnswerTimer(FinanceLog financeLog){
		AnswerTimer query = new AnswerTimer();
		query.setOpenId(financeLog.getOpenId());
		query.setTeacherId(financeLog.getSellOpenId());
		query.setGradeCode(financeLog.getGradeCode());
		AnswerTimer timer = answerTimerDAO.queryAnswerTimerByOpenId(query);
		if(timer == null){
			query.setCreateTime(DateUtil.getNowTime());
			query.setUpdateTime(DateUtil.getNowTime());
			query.setRemainTime(financeLog.getDuration());
			query.setDueTime(DateUtil.calcDate(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS, 5, financeLog.getValidDay()));
			answerTimerDAO.saveAnswerTimer(query);
			System.out.println("添加答疑时长成功："+query);
		}else{
			timer.setRemainTime(timer.getRemainTime().add(financeLog.getDuration()));
			timer.setUpdateTime(DateUtil.getNowTime());
			timer.setDueTime(DateUtil.calcDate(DateUtil.stringTodate(timer.getDueTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_MS), 
					DateUtil.YYYY_MM_DD_HH_MM_SS, 5, financeLog.getValidDay()));
			answerTimerDAO.updateAnswerTimer(timer);
			System.out.println("修改答疑时长成功："+timer);
		}
	}
	@Override
	@Transactional
	public ResultMapper addRefundMoneyLog(FinanceLog financeLog) {
		ResultMapper resultBean = new ResultMapper();
		financeLog.setFinanceLogsType(2);// 表示退款记录
		financeLog.setTrade_no(financeLog.getBatchNo());
		if(financeLog.getTradeTime() == null)financeLog.setTradeTime(DateUtil.getNowTime());
		FinanceLog financeLog2 = financeLogDAO.queryFinanceLogByConditions(financeLog);
		if(financeLog2 != null){
			resultBean.setFailMsg(SystemStatus.LOG_AREADY_BEEN);
			return resultBean;
		}
		BigDecimal percentage = BigDecimal.valueOf(Config.getDouble("percentage"));
		BigDecimal opercentage = BigDecimal.valueOf(1.00).subtract(percentage);
		BigDecimal totalPrice = financeLog.getTotalPrice();
		financeLog.setVariableMoneyChange(BigDecimal.valueOf(0.00).subtract(totalPrice.multiply(percentage)));
		financeLog.setTeacherIncome(financeLog.getVariableMoneyChange());
		financeLog.setSystemIncome(BigDecimal.valueOf(0.00).subtract(totalPrice.multiply(opercentage)));
		MoneyTimer moneyTimer = moneyTimerDAO.queryMoneyTimerByOpenId(financeLog.getSellOpenId());
		if (moneyTimer == null){
			moneyTimer = new MoneyTimer();
			moneyTimer.setOpenId(financeLog.getSellOpenId());
			moneyTimer.setVariableMoney(moneyTimer.getVariableMoney().add(financeLog.getVariableMoneyChange()));// 日志里存的负值所以直接加
			moneyTimer.setTotalMoney(moneyTimer.getTotalMoney().add(financeLog.getVariableMoneyChange()));
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
			moneyTimerDAO.saveMoneyTimer(moneyTimer);
		}else{
			moneyTimer.setVariableMoney(moneyTimer.getVariableMoney().add(financeLog.getVariableMoneyChange()));// 日志里存的负值所以直接加
			moneyTimer.setTotalMoney(moneyTimer.getTotalMoney().add(financeLog.getVariableMoneyChange()));
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
			moneyTimerDAO.updateMoneyTimer(moneyTimer);
		}
		log.info("保存数据：moneyTimer===="+moneyTimer);

		financeLogDAO.saveFinanceLog(financeLog);
		log.info("保存数据：financeLog===="+financeLog);
		for (WaresSlave ws : financeLog.getWaresSlaves()) {
			ws.setOrderNumber(financeLog.getOrderNumber());
			ws.setCreateTime(DateUtil.getNowTime());
			waresSlaveDAO.insertWaresSlave(ws);
		}

		resultBean.setSucResult("保存成功！");
		return resultBean;
	}

	@Override
	@Transactional
	public ResultMapper addConsumeAnswerTimeLog(FinanceLog financeLog) {
		ResultMapper resultBean = new ResultMapper();
		financeLog.setFinanceLogsType(3);// 表示消费答疑时长记录
		if(financeLog.getTradeTime() == null)financeLog.setTradeTime(DateUtil.getNowTime());
		AnswerTimer query = new AnswerTimer();
		query.setOpenId(financeLog.getOpenId());
		//query.setSolutionId(financeLog.getSolutionId());
		AnswerTimer answerTimer = answerTimerDAO
				.queryAnswerTimerByOpenId(query);
		if (answerTimer == null){
			resultBean.setFailMsg(SystemStatus.HAVE_NOT_ANSWERTIME);
			return resultBean;
		}
		BigDecimal remainTime = answerTimer.getRemainTime();
		if (remainTime.compareTo(financeLog.getDuration())<0 ) {
			resultBean.setFailMsg(SystemStatus.REMAINTIME_NOT_ENOUGH);
			return resultBean;
		}
		answerTimer.setUpdateTime(DateUtil.getNowTime());
		answerTimer.setRemainTime(remainTime.subtract(financeLog.getDuration()));
		answerTimerDAO.updateAnswerTimer(answerTimer);
		log.info("修改时长余额为 openId="+answerTimer.getOpenId()+",remainTime="+answerTimer.getRemainTime());
		financeLogDAO.saveFinanceLog(financeLog);
		log.info("消耗答疑时长记录保存成功："+financeLog.getOpenId()+"timeChange="+financeLog.getDuration());
		resultBean.setSucResult("保存成功！");
		return resultBean;
	}

	@Override
	@Transactional
	public ResultMapper addMonthSettleAccounts(List<FinanceLog> financeLogs) {
		ResultMapper resultBean = new ResultMapper();
		for(FinanceLog flg:financeLogs){
			flg.setTradeTime(DateUtil.getNowTime());
			flg.setFinanceLogsType(4);

			financeLogDAO.saveFinanceLog(flg);
			MoneyTimer moneyTimer = moneyTimerDAO.queryMoneyTimerByOpenId(flg.getSellOpenId());
			BigDecimal cash = moneyTimer.getWithdrawalCash();
			moneyTimer.setWithdrawalCash(cash.subtract(flg.getTeacherOutput()));
			moneyTimer.setTotalSettleMoney(moneyTimer.getTotalSettleMoney().add(flg.getTeacherOutput()));
			moneyTimer.setTotalMoney(moneyTimer.getVariableMoney().add(moneyTimer.getWithdrawalCash()));
			moneyTimerDAO.updateMoneyTimer(moneyTimer);
		}
		resultBean.setSucResult("保存成功！！");
		return resultBean;
	}
	
	
	public List<FinanceLog> queryTeacherIncomeLastMonth(List<String> openIds){
		List<FinanceLog> financeLogs = new ArrayList<FinanceLog>();
		for(String openId: openIds){
			BigDecimal income1=financeLogDAO.queryTeacherIncomeForLastMonth(openId);
			BigDecimal income2=settleAccountsDAO.queryTeacherIncomeForLastMonth(openId);
			if(income1 == null) income1=BigDecimal.valueOf(0.00);
			if(income2==null) income2=BigDecimal.valueOf(0.00);
			System.out.println("teacher_income========="+(income1.add(income2)));
			FinanceLog flg = new FinanceLog();
			flg.setOpenId(openId);
			flg.setTeacherIncome(income1.add(income2));
			financeLogs.add(flg);
		}
		return financeLogs;
		
	}

	@Override
	@Transactional
	public ResultMapper queryTeacherTradeLogs(FinanceLogQuery flq) {
		ResultMapper resultBean = new ResultMapper();
		log.info("查询请求参数："+flq);
		//通过手机号查询openId
		System.out.println("通过手机号查询openId-->"+Config.getString("user.server")+Config.getString("open_id.url")+flq.getPhoneNum());
		String httpRest = HttpClient.httpRest(Config.getString("user.server"), Config.getString("open_id.url")+flq.getPhoneNum(), null, null, "GET");
		System.out.println("httpRest=="+httpRest);
		JSONObject json = JSONObject.fromObject(httpRest);
		Object result = json.get("result");
		System.out.println("result============"+result);
		if(result.equals(null) || "".equals(result)){
			resultBean.setFailMsg(SystemStatus.PHONE_NOT_HAS);
			return resultBean;
		}
		JSONObject obj = (JSONObject) json.get("result");
		String openId = obj.getString("userOpenId");
		String name= obj.getString("name");
		String inviteSelfcode= obj.getString("invite_selfcode");
		System.out.println("查询结果  openId=="+openId);
		flq.setOpenId(openId);
/*			Date endTime = DateUtil.stringTodate(flq.getEndTime(), "yyyy-MM-dd");
		flq.setEndTime(DateUtil.dateToString(DateUtil.addDays(endTime, 1), "yyyy-MM-dd"));*/

		List<FinanceLog> conditions = financeLogDAO.queryTeacherTradeLogs(flq);
		List<FinanceBean> financeBeans = new ArrayList<FinanceBean>();
		BigDecimal totalIncome=BigDecimal.valueOf(0.00);//收入总和
		BigDecimal totalRefund=BigDecimal.valueOf(0.00);//退款总和
		BigDecimal totalTeacherIncome=BigDecimal.valueOf(0.00); //教师部分总和
		BigDecimal totalSytemIncome=BigDecimal.valueOf(0.00);//平台总和
		for(FinanceLog fl : conditions){
			FinanceBean financeBean = new FinanceBean();
			financeBean.setTeacherName(name);
			financeBean.setInviteSelfcode(inviteSelfcode);
			financeBean.setOpenId(fl.getSellOpenId());
			financeBean.setTeacherPhoneNum(fl.getTeacherPhoneNum());
			financeBean.setStudentPhoneNum(fl.getStudentPhoneNum());
			financeBean.setTradeTime(DateUtil.StringPattern(fl.getTradeTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_MS, DateUtil.YYYY年MM月DD_HH_MM_SS));
			financeBean.setOrderNumber(fl.getOrderNumber());
			financeBean.setTrade_no(fl.getTrade_no());
			financeBean.setCurriculumName(fl.getCurriculumName());
			financeBean.setTotalPrice(fl.getTotalPrice());
			if(fl.getCurriculumCount()>0){//
				financeBean.setSinglePrice(fl.getTotalPrice().divide(new BigDecimal(fl.getCurriculumCount()), 2, BigDecimal.ROUND_HALF_EVEN));
			}
			if(fl.getTradeType() != null){
				switch (fl.getTradeType()) {
				case 1:financeBean.setTradeType("支付宝");
					break;
				case 2:financeBean.setTradeType("微信");
					break;
				case 3:financeBean.setTradeType("银行卡");
					break;
				default:
					break;
				}
			}
			if(fl.getCurriculumType() != null){
				switch (fl.getCurriculumType()) {
				case 1:financeBean.setCurriculumType("一对一直播课");
					break;
				case 2:financeBean.setCurriculumType("视频课");
					break;
				case 3:financeBean.setCurriculumType("小班直播课");
					break;
				case 4:financeBean.setCurriculumType("大班直播课");
				default:
					break;
				}
			}
			financeBean.setTeacherIncome(fl.getTeacherIncome());
			financeBean.setSystemIncome(fl.getSystemIncome());
			if(fl.getFinanceLogsType()==1){
				totalIncome=totalIncome.add(fl.getTotalPrice());
			}else if(fl.getFinanceLogsType()==2){
				totalRefund=totalRefund.subtract(fl.getTotalPrice());
				financeBean.setTotalPrice(fl.getTotalPrice().multiply(new BigDecimal(-1)));
			}
			financeBean.setFinanceLogsType(fl.getFinanceLogsType());
			totalTeacherIncome=totalTeacherIncome.add(fl.getTeacherIncome());
			totalSytemIncome=totalSytemIncome.add(fl.getSystemIncome());
			System.out.println("totalIncome="+totalIncome+",totalRefund="+totalRefund+",totalTeacherIncome="+totalTeacherIncome+",totalSystemIncome="+totalSytemIncome);
			financeBeans.add(financeBean);
		}
		if(flq.getRay()==0){
			Collections.sort(financeBeans, new sortByDateDesc0());
		}else{
			Collections.sort(financeBeans, new sortByDateDesc1());
		}
		FinanceBean financeBean = new FinanceBean();
		System.out.println("totalIncome="+totalIncome+",totalRefund="+totalRefund+",totalTeacherIncome="+totalTeacherIncome+",totalSystemIncome="+totalSytemIncome);
		financeBean.setTotalIncome(totalIncome);
		financeBean.setTotalRefund(totalRefund);
		financeBean.setTotalTeacherIncome(totalTeacherIncome);
		financeBean.setTotalSytemIncome(totalSytemIncome);
		financeBeans.add(financeBean);
		resultBean.setSucResult(financeBeans);
		return resultBean;
	}

	@Override
	public ResultMapper queryTeacherTradeLogsByName(FinanceLogQuery flq) {
		ResultMapper resultBean = new ResultMapper();
		log.info("查询请求参数:"+flq);
		List<FinanceBean> financeBeans = new ArrayList<FinanceBean>();
		//通过手机号查询openId
		System.out.println("通过姓名查询openId-->"+Config.getString("user.server")+Config.getString("open_id.url")+flq.getPhoneNum());
		String httpRest = HttpClient.httpRest(Config.getString("user.server"), Config.getString("open_id.url")+flq.getPhoneNum(), null, null, "GET");
		System.out.println("httpRest=="+httpRest);
		JSONObject json = JSONObject.fromObject(httpRest);
		String result = (String) json.get("result");
		System.out.println("result============"+result);
		if(result.equals(null) || "".equals(result)){
			resultBean.setSucResult(financeBeans);
			return resultBean;
		}
		List<UserInfoBean>  userInfoBeans = JsonUtil.jsonToList(result, UserInfoBean.class);
		BigDecimal totalIncome=BigDecimal.valueOf(0.00);//收入总和
		BigDecimal totalRefund=BigDecimal.valueOf(0.00);//退款总和
		BigDecimal totalTeacherIncome=BigDecimal.valueOf(0.00); //教师部分总和
		BigDecimal totalSytemIncome=BigDecimal.valueOf(0.00);//平台总和
		for(UserInfoBean uib:userInfoBeans){
			String name = uib.getName();
			String inviteSelfcode = uib.getInvite_selfcode();
			
			flq.setOpenId(uib.getUserOpenId());
			List<FinanceLog> conditions = financeLogDAO.queryTeacherTradeLogs(flq);
			for(FinanceLog fl :conditions){
				FinanceBean financeBean = new FinanceBean();
				financeBean.setTeacherName(name);
				financeBean.setInviteSelfcode(inviteSelfcode);
				financeBean.setOpenId(fl.getSellOpenId());
				financeBean.setTeacherPhoneNum(fl.getTeacherPhoneNum());
				financeBean.setStudentPhoneNum(fl.getStudentPhoneNum());
				financeBean.setTradeTime(DateUtil.StringPattern(fl.getTradeTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_MS, DateUtil.YYYY年MM月DD_HH_MM_SS));
				financeBean.setOrderNumber(fl.getOrderNumber());
				financeBean.setTrade_no(fl.getTrade_no());
				financeBean.setCurriculumName(fl.getCurriculumName());
				financeBean.setTotalPrice(fl.getTotalPrice());
				if(fl.getCurriculumCount()>0){//
					financeBean.setSinglePrice(fl.getTotalPrice().divide(new BigDecimal(fl.getCurriculumCount()), 2, BigDecimal.ROUND_HALF_EVEN));
				}
				if(fl.getTradeType() != null){
					switch (fl.getTradeType()) {
					case 1:financeBean.setTradeType("支付宝");
						break;
					case 2:financeBean.setTradeType("微信");
						break;
					case 3:financeBean.setTradeType("银行卡");
						break;
					default:
						break;
					}
				}
				if(fl.getCurriculumType() != null){
					switch (fl.getCurriculumType()) {
					case 1:financeBean.setCurriculumType("一对一直播课");
						break;
					case 2:financeBean.setCurriculumType("视频课");
						break;
					case 3:financeBean.setCurriculumType("小班制博客");
						break;
					case 4:financeBean.setCurriculumType("大班直播课");
					default:
						break;
					}
				}
				financeBean.setTeacherIncome(fl.getTeacherIncome());
				financeBean.setSystemIncome(fl.getSystemIncome());
				if(fl.getFinanceLogsType()==1){
					totalIncome=totalIncome.add(fl.getTotalPrice());
				}else if(fl.getFinanceLogsType()==2){
					totalRefund=totalRefund.subtract(fl.getTotalPrice());
					financeBean.setTotalPrice(fl.getTotalPrice().multiply(new BigDecimal(-1)));
				}
				financeBean.setFinanceLogsType(fl.getFinanceLogsType());
				totalTeacherIncome=totalTeacherIncome.add(fl.getTeacherIncome());
				totalSytemIncome=totalSytemIncome.add(fl.getSystemIncome());
				System.out.println("totalIncome="+totalIncome+",totalRefund="+totalRefund+",totalTeacherIncome="+totalTeacherIncome+",totalSystemIncome="+totalSytemIncome);
				financeBeans.add(financeBean);
			}
			if(flq.getRay()==0){
				Collections.sort(financeBeans, new sortByDateDesc0());
			}else{
				Collections.sort(financeBeans, new sortByDateDesc1());
			}
			FinanceBean financeBean = new FinanceBean();
			System.out.println("totalIncome="+totalIncome+",totalRefund="+totalRefund+",totalTeacherIncome="+totalTeacherIncome+",totalSystemIncome="+totalSytemIncome);
			financeBean.setTotalIncome(totalIncome);
			financeBean.setTotalRefund(totalRefund);
			financeBean.setTotalTeacherIncome(totalTeacherIncome);
			financeBean.setTotalSytemIncome(totalSytemIncome);
			financeBeans.add(financeBean);
			resultBean.setSucResult(financeBeans);
		}
		return resultBean;
	}
	

}
//时间降序
class sortByDateDesc0 implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		FinanceBean fb1 = (FinanceBean) o1;
		FinanceBean fb2 = (FinanceBean) o2;
		return DateUtil.compare_date0(fb1.getTradeTime(), fb2.getTradeTime());
	}
}
//时间升序
class sortByDateDesc1 implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		FinanceBean fb1 = (FinanceBean) o1;
		FinanceBean fb2 = (FinanceBean) o2;
		return DateUtil.compare_date1(fb1.getTradeTime(), fb2.getTradeTime());
	}
}
