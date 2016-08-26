package com.yijiajiao.finance.service;

import com.yijiajiao.finance.bean.FinanceLog;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.query.FinanceLogQuery;

import java.util.List;


public interface IFinanceLogService {
	/**
	 * @description 添加订单财务记录
	 * @date 2015-12-29
	 * @return ResultMapper
	 * @param financeLog
	 */
	ResultMapper addOrderFinanceLog(FinanceLog financeLog);

	/**
	 * @description 退款
	 * @date 2016-1-8
	 * @return ResultMapper
	 * @param financeLog
	 */
	ResultMapper addRefundMoneyLog(FinanceLog financeLog);

	/**
	 * @description 消费答疑
	 * @date 2016-1-8
	 * @return ResultMapper
	 * @param financeLog
	 */
	ResultMapper addConsumeAnswerTimeLog(FinanceLog financeLog);

	/**
	 *@description 添加教师提现记录（批量发工资）
	 *@date 2016-3-16
	 *@return ResultMapper
	 *@param financeLogs
	 *@return
	 */
	ResultMapper addMonthSettleAccounts(List<FinanceLog> financeLogs);
	
	List<FinanceLog> queryTeacherIncomeLastMonth(List<String> openIds);
	/**
	 *@description	通过条件查询教师本月的交易记录(通过手机号查询)
	 *@date 2016-3-30                                                                                                                                                                                                                              
	 *@return FinanceLog
	 *@param flq
	 *@return
	 */
	ResultMapper queryTeacherTradeLogs(FinanceLogQuery flq);
	/**
	 *@description  通过条件查询教师本月的交易记录(通过姓名查询)
	 *@date 2016-7-21

	 */
	ResultMapper queryTeacherTradeLogsByName(FinanceLogQuery flq);
}
