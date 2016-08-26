package com.yijiajiao.finance.dao;


import com.yijiajiao.finance.bean.AnswerTimer;

import java.util.List;

public interface IAnswerTimerDAO {
	/**
	 *@description
	 *@date 2016-8-2
	 *@param query
	 */
	AnswerTimer queryAnswerTimerByOpenId(AnswerTimer query);

	/**
	 * @description 添加用户答疑计时信息
	 * @param answerTimer
	 * @return void
	 */
	void saveAnswerTimer(AnswerTimer answerTimer);

	/**
	 * @description 修改用户答疑计时信息
	 * @param answerTimer
	 * @return void
	 */
	void updateAnswerTimer(AnswerTimer answerTimer);

	List<AnswerTimer> queryTimeList(AnswerTimer query);
}
