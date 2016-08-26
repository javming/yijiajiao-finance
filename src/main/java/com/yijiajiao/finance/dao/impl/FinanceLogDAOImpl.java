package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.FinanceLog;
import com.yijiajiao.finance.bean.query.FinanceLogQuery;
import com.yijiajiao.finance.bean.query.QueryParam;
import com.yijiajiao.finance.dao.IFinanceLogDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;



@Repository("financeLogDAO")
public class FinanceLogDAOImpl implements IFinanceLogDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	@Override
	public FinanceLog queryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveFinanceLog(FinanceLog financeLog) {
		sessionTemplate.insert(
				"com.yijiajiao.finance.dao.IFinanceLogDAO.saveFinanceLog",
				financeLog);
	}

	@Override
	public List<FinanceLog> queryFinanceLogsByOpenId(String openId) {
		return null;
	}

	@Override
	public List<FinanceLog> queryFinanceLogsByConditions(FinanceLogQuery financeLog) {
		return sessionTemplate.selectList(
				"com.yijiajiao.finance.dao.IFinanceLogDAO.queryFinanceLogsByConditions", 
				financeLog);
	}

	@Override
	public FinanceLog queryFinanceLogByConditions(FinanceLog financeLog) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IFinanceLogDAO.queryFinanceLogByConditions", financeLog);
	}

	@Override
	public BigDecimal queryTeacherIncomeForLastMonth(String openId) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IFinanceLogDAO.queryTeacherIncomeForLastMonth", openId);
	}

	@Override
	public List<FinanceLog> queryTeacherTradeLogs(FinanceLogQuery flq) {
		return sessionTemplate.selectList("com.yijiajiao.finance.dao.IFinanceLogDAO.queryTeacherTradeLogs", flq);
	}

	@Override
	public BigDecimal queryTeacherIncomeForThisMonth(String openId) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IFinanceLogDAO.queryTeacherIncomeForThisMonth", openId);
	}

	@Override
	public FinanceLog queryFinanceLogByOrderNum(String orderNumber) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IFinanceLogDAO.queryFinanceLogByOrderNum", orderNumber);
	}

	@Override
	public BigDecimal queryTeacherIncomeByMonth(QueryParam qp) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IFinanceLogDAO.queryTeacherIncomeByMonth", qp);
	}

}
