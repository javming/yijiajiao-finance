package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.DailySettleAccounts;
import com.yijiajiao.finance.bean.query.QueryParam;
import com.yijiajiao.finance.dao.ISettleAccountsDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository("dailySettleAccountsDAO")
public class SettleAccountsDAOImpl implements ISettleAccountsDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public void insertDailySettleAccounts(
			DailySettleAccounts dailySettleAccounts) {
		sessionTemplate.insert("com.yijiajiao.finance.dao.IDailySettleAccountsDAO.insertDailySettleAccounts", dailySettleAccounts);
	}
	@Override
	public BigDecimal queryTeacherIncomeForLastMonth(String openId) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IDailySettleAccountsDAO.queryTeacherIncomeForLastMonth", openId);
	}
	@Override
	public BigDecimal queryTeacherIncomeForThisMonth(String openId) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IDailySettleAccountsDAO.queryTeacherIncomeForThisMonth", openId);
	}
	@Override
	public BigDecimal queryTeacherIncomeByMonth(QueryParam qp) {
		// TODO Auto-generated method stub
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IDailySettleAccountsDAO.queryTeacherIncomeByMonth",qp);
	}
	@Override
	public int queryInfoForCheckSame(DailySettleAccounts dsa) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IDailySettleAccountsDAO.queryInfoForCheckSame", dsa);
	}
	
	
}
