package com.yijiajiao.finance.controller;

import com.yijiajiao.finance.bean.BatchPayDetail;
import com.yijiajiao.finance.bean.BatchPayment;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.SystemStatus;
import com.yijiajiao.finance.bean.page.Pagination;
import com.yijiajiao.finance.bean.query.BatchPayDetailQuery;
import com.yijiajiao.finance.service.IBatchPayService;
import com.yijiajiao.finance.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.List;

@Controller
@Path("/batchpay")
public class BatchPayController {
	@Autowired
	private IBatchPayService batchPayService;
	private ResultMapper resultBean = new ResultMapper();
	/**
	 *@description 批量转账
	 *@date 2016-2-2
	 *@return ResultMapper
	 *@return
	 */
	@POST
	@Path("/paylist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper batchPay(List<BatchPayDetail> batchPayDetails){
		return batchPayService.batchPay(batchPayDetails);
	}
	/**
	 *@description 查询数据库，组合支付宝批量付款请求的url，用于教师收入结算
	 *@date 2016-3-21
	 *@return ResultMapper
	 *@return
	 */
	@GET
	@Path("/paysalary")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper PaySalaryForTeacher(List<BatchPayDetail> batchPayDetails){
		return batchPayService.batchPay(batchPayDetails);
	}
	/**
	 *@description 获得教师的工资数据列表
	 *@date 2016-3-28
	 *@return ResultMapper
	 */
	@GET
	@Path("/salarylist")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultMapper getTeacherSalaryList(BatchPayDetailQuery batchPayDetailQuery){
		try {
			Pagination pagination = batchPayService.querySettleInfo(batchPayDetailQuery);
			this.resultBean.setSucResult(pagination);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return this.resultBean;
	}
	
	/**
	 *@description	计算所有老师上个月收入，并获取第一页数据
	 *@date 2016-4-1
	 *@return ResultMapper
	 *@return
	 */
	@GET
	@Path("/salary")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultMapper getTeacherSalary(){
		try {
			int isSuc = batchPayService.getAndSaveSalary();	
			BatchPayDetailQuery batchPayDetailQuery = new BatchPayDetailQuery();
			batchPayDetailQuery.setPageNum(1);
			batchPayDetailQuery.setPageSize(10);
			batchPayDetailQuery.setIs_dispose(0);
			Pagination pagination = batchPayService.querySettleInfo(batchPayDetailQuery);
			this.resultBean.setSucResult(pagination);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return this.resultBean;
	}
	
	@POST
	@Path("/updateBatchPayment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper modBatchPayment(BatchPayment bpayment){
		try {
			bpayment.setNotify_time(DateUtil.getNowTime());
			batchPayService.modBatchPayment(bpayment);
			batchPayService.modBatchPayDetailAndMoneyTiemer(bpayment.getBatch_no());
			this.resultBean.setSucResult("修改成功！！");
		} catch (Exception e) {
			e.printStackTrace();
			this.resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return this.resultBean;
	}
	/**
	 *@description  获得根据条件获得教师工资对账单
	 *@date 2016-4-6
	 *@return ResultMapper
	 *@param batchPayDetailQuery
	 *@return
	 */
	@POST
	@Path("/salaryStatement")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper getSalaryStatement(BatchPayDetailQuery batchPayDetailQuery){
		return batchPayService.querySettleInfoByDate(batchPayDetailQuery);
	}
	@GET
	@Path("/salaryStatementByd")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper getSalaryStatement(@QueryParam("queryDate") String queryDate){
		return batchPayService.querySettleInfoByDate(queryDate);
	}
	
	
	
	
	
}
