package com.yijiajiao.finance.dao;

import com.yijiajiao.finance.bean.FinanceLog;
import com.yijiajiao.finance.bean.query.FinanceLogQuery;
import com.yijiajiao.finance.bean.query.QueryParam;

import java.math.BigDecimal;
import java.util.List;



/**
 * @description IFinanceLogDAO
 * @author zhaoming
 * @date 2016-1-5
 */
public interface IFinanceLogDAO {
	/**
	 * @description 根据id查询单条记录
	 * @date 2015-12-29
	 * @return FinanceLog
	 * @param id
	 */
	FinanceLog queryById(int id);

	/**
	 * @description 添加财务记录
	 * @date 2015-12-29
	 * @return void
	 * @param financeLog
	 */
	void saveFinanceLog(FinanceLog financeLog);

	/**
	 * @description 通过openId查询个人财务记录
	 * @date 2015-12-29
	 * @return List<FinanceLog>
	 * @param openId
	 */
	List<FinanceLog> queryFinanceLogsByOpenId(String openId);
	
	/**
	 *@description 通过条件查询财务记录，返回单条记录
	 *@date 2016-1-27
	 *@return FinanceLog
	 *@param financeLog
	 */
	FinanceLog queryFinanceLogByConditions(FinanceLog financeLog);

	/**
	 * @description 通过条件查询财务记录，返回多条记录
	 * @date 2016-1-5
	 * @return List<FinanceLog>
	 * @param financeLog
	 */
	List<FinanceLog> queryFinanceLogsByConditions(FinanceLogQuery financeLog);
	/**
	 *@description 获取上个月教师收入总和，主要是计算finance_log表中withdrawal_cash_change字段的总和
	 *								作为收入总和的一部分，配合daily_settle_accounts表计算总共收入总和
	 *@date 2016-3-17
	 *@return double
	 *@param openId
	 *@return
	 */
	BigDecimal queryTeacherIncomeForLastMonth(String openId);
	/**
	 *@description 获取本月教师收入总和，主要是计算finance_log表中withdrawal_cash_change字段的总和
	 *								作为收入总和的一部分，配合daily_settle_accounts表计算总共收入总和
	 *@date 2016-3-31
	 *@return double
	 *@param openId
	 *@return
	 */
	BigDecimal queryTeacherIncomeForThisMonth(String openId);

	List<FinanceLog> queryTeacherTradeLogs(FinanceLogQuery flq);
	/**
	 *@description	通过订单号查询订单详情
	 *@date 2016-4-1
	 *@return FinanceLog
	 *@param orderNumber
	 *@return
	 */
	FinanceLog queryFinanceLogByOrderNum(String orderNumber);
	/**
	 *@description	获得指定月的教师收入
	 *@date 2016-4-12
	 *@return double
	 *@return
	 */
	BigDecimal queryTeacherIncomeByMonth(QueryParam qp);
}
