package com.yijiajiao.finance.controller;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.FinanceLog;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.SystemStatus;
import com.yijiajiao.finance.bean.query.FinanceLogQuery;
import com.yijiajiao.finance.service.IAnswerTimerService;
import com.yijiajiao.finance.service.IFinanceLogService;
import com.yijiajiao.finance.service.IMoneyTimerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("/FinanceLog")
public class FinanceLogController {
	private static Logger log = (Logger) LoggerFactory.getLogger(FinanceLogController.class);
	private ResultMapper resultBean = null;
	@Autowired
	private IFinanceLogService financeLogService;
	@Autowired
	private IAnswerTimerService answerTimerService;
	@Autowired
	private IMoneyTimerService moneyTimerService;

	/**
	 *@description 订单记录接口
	 *@date 2016-1-11
	 *@return ResultMapper
	 *@param financeLog
	 */
	@POST
	@Path("/orderFinanceLog")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper orderFinanceLog(FinanceLog financeLog) {
		return financeLogService.addOrderFinanceLog(financeLog);
	}

	/**
	 *@description 退款记录接口
	 *@date 2016-1-13
	 *@return ResultMapper
	 *@param financeLog
	 */
	@POST
	@Path("/refundMoney")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper refundMoney(FinanceLog financeLog) {
		return financeLogService.addRefundMoneyLog(financeLog);
	}
	/**
	 *@description 消费答疑时长接口
	 *@date 2016-1-11
	 *@return ResultMapper
	 */
	@GET
	@Path("/consumeAnswerTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper consumeAnswerTime(@QueryParam("openId") String openId,@QueryParam("teacherId")String teacherId,
			@QueryParam("gradeCode") String gradeCode,@QueryParam("consumeTime") String consumeTime) {
		return answerTimerService.consumeAnswerTime(openId,teacherId,gradeCode,consumeTime);
	}
	/**
	 * @description 获取个人账户所剩答疑时长
	 * @date 2015-12-28
	 * @return ResultMapper
	 * @param openId
	 */
	@GET
	@Path("/getRemainAnswerTime")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper getRemainAnswerTime(@QueryParam("openId") String openId,@QueryParam("teacherId") String teacherId,@QueryParam("gradeCode")String gradeCode) {
		log.info("参数 ：openid=" + openId+",teacherId="+teacherId+",gradeCode="+gradeCode);
		return answerTimerService.getRemainAnswerTime(openId,teacherId,gradeCode);
	}
	
	/**
	 *@description	获取个人答疑时长列表
	 *@date 2016-8-12
	 */
	@GET
	@Path("/getRemainTimes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper getAnswerTimes(@QueryParam("openId") String openId,@QueryParam("teacherId") String teacherId){
		log.info("参数 ：openid=" + openId+",teacherId="+teacherId);
		return answerTimerService.getRemainAnswerTimes(openId, teacherId);
	}

	/**
	 * @description 获取个人用户所剩金额
	 * @date 2015-12-28
	 * @return ResultMapper
	 * @param openId
	 */
	@GET
	@Path("/getRemainMoney")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper getRemainMoney(@QueryParam("openId") String openId) {
		log.info("参数：openId==" + openId);
		return moneyTimerService.getRemainMoney(openId);
	}
	
	/**
	 *@description 教师账户批量提现，用于每月15日发工资
	 *@date 2016-3-17
	 *@return ResultMapper
	 *@param financeLogs
	 *@return
	 */
	@POST
	@Path("/monthSettleAccounts")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper monthSettleAccounts(List<FinanceLog> financeLogs){
		return financeLogService.addMonthSettleAccounts(financeLogs);
	}
	
	@POST
	@Path("/queryTeacherIncomeLastMonth")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper queryTeacherIncomeLastMonth(List<String> openIds){
		resultBean = new ResultMapper();
		try {
			List<FinanceLog> list = financeLogService.queryTeacherIncomeLastMonth(openIds);
			resultBean.setSucResult(list);
		} catch (Exception e) {
			e.printStackTrace();
			resultBean.setFailMsg(SystemStatus.SERVER_ERROR);
		}
		return resultBean;
	}
	
	/**
	 *@description		通过手机号和时间获取教师交易详情列表
	 *@date 2016-4-1
	 *@return ResultMapper
	 *@param flq
	 *@return
	 */
	@POST
	@Path("/personalFinance")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper personalFinanceLogs(FinanceLogQuery flq){
		if(flq.getTeacherName()!= null && !"".equals(flq.getTeacherName())){
			return financeLogService.queryTeacherTradeLogsByName(flq);
		}
		return financeLogService.queryTeacherTradeLogs(flq);
	}
}
