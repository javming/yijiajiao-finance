package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.MoneyTimer;
import com.yijiajiao.finance.dao.IMoneyTimerDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("moneyTimerDAO")
public class MoneyTimerDAOImpl implements IMoneyTimerDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	@Override
	public MoneyTimer queryMoneyTimerByOpenId(String openId) {
		return sessionTemplate
				.selectOne(
						"com.yijiajiao.finance.dao.IMoneyTimerDAO.queryMoneyTimerByOpenId",
						openId);
	}

	@Override
	public void saveMoneyTimer(MoneyTimer moneyTimer) {
		sessionTemplate.insert(
				"com.yijiajiao.finance.dao.IMoneyTimerDAO.saveMoneyTimer",
				moneyTimer);
	}

	@Override
	public void updateMoneyTimer(MoneyTimer moneyTimer) {
		sessionTemplate.update(
				"com.yijiajiao.finance.dao.IMoneyTimerDAO.updateMoneyTimer",
				moneyTimer);
	}

	@Override
	public List<String> queryAllOpenIds() {
		return sessionTemplate.selectList("com.yijiajiao.finance.dao.IMoneyTimerDAO.queryAllOpenIds");
	}

}
