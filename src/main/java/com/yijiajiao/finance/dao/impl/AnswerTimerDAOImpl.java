package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.AnswerTimer;
import com.yijiajiao.finance.dao.IAnswerTimerDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository("answerTimer")
public class AnswerTimerDAOImpl implements IAnswerTimerDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;

	@Override
	public AnswerTimer queryAnswerTimerByOpenId(AnswerTimer query) {
		return sessionTemplate
				.selectOne(
						"com.jishijiajiao.finance.dao.IAnswerTimerDAO.queryAnswerTimerByOpenId",
						query);
	}

	@Override
	public void saveAnswerTimer(AnswerTimer answerTimer) {
		sessionTemplate.insert(
				"com.jishijiajiao.finance.dao.IAnswerTimerDAO.saveAnswerTimer",
				answerTimer);
	}

	@Override
	public void updateAnswerTimer(AnswerTimer answerTimer) {
		sessionTemplate
				.update("com.jishijiajiao.finance.dao.IAnswerTimerDAO.updateAnswerTimer",
						answerTimer);
	}

	@Override
	public List<AnswerTimer> queryTimeList(AnswerTimer query) {
		return sessionTemplate.selectList("com.jishijiajiao.finance.dao.IAnswerTimerDAO.queryRemainTimes", query);
	}

}
