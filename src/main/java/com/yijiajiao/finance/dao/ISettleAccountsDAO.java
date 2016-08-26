package com.yijiajiao.finance.dao;

import com.yijiajiao.finance.bean.DailySettleAccounts;
import com.yijiajiao.finance.bean.query.QueryParam;

import java.math.BigDecimal;



public interface ISettleAccountsDAO {
	void insertDailySettleAccounts(DailySettleAccounts dailySettleAccounts);
	BigDecimal queryTeacherIncomeForLastMonth(String openId);
	BigDecimal queryTeacherIncomeForThisMonth(String openId);
	BigDecimal queryTeacherIncomeByMonth(QueryParam qp);
	int queryInfoForCheckSame(DailySettleAccounts dsa);
}
