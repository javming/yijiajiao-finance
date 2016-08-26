package com.yijiajiao.finance.service;


import com.yijiajiao.finance.bean.ResultMapper;

public interface IAnswerTimerService {

	/**
	 * @description 通过openid获取所剩答疑时长
	 * @date 2015-12-25
	 */
	ResultMapper getRemainAnswerTime(String openId, String teacherId, String gradeCode);

	/**
	 *@description	消耗答疑时长
	 *@date 2016-8-11
	 */
	ResultMapper consumeAnswerTime(String openId, String teacherId,
                                   String gradeCode, String consumeTime);

	ResultMapper getRemainAnswerTimes(String openId, String teacherId);

}
