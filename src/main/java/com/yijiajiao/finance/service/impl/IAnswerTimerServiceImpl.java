package com.yijiajiao.finance.service.impl;

import com.yijiajiao.finance.bean.AnswerTimer;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.dao.IAnswerTimerDAO;
import com.yijiajiao.finance.service.IAnswerTimerService;
import com.yijiajiao.finance.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("answerTimerService")
public class IAnswerTimerServiceImpl implements IAnswerTimerService {
	private static Logger log = LoggerFactory.getLogger(IAnswerTimerServiceImpl.class);
	@Autowired
	private IAnswerTimerDAO answerTimerDao;

	public ResultMapper getRemainAnswerTime(String openId, String teacherId, String gradeCode) {
		ResultMapper resultMap = new ResultMapper();
		AnswerTimer query = new AnswerTimer();
		query.setOpenId(openId);
		query.setTeacherId(teacherId);
		query.setGradeCode(gradeCode);
		AnswerTimer answerTimer = answerTimerDao.queryAnswerTimerByOpenId(query);
		Map<String,Object> remain = new HashMap<String, Object>();
		if (answerTimer == null) {
			remain.put("remainTime", "0");
			remain.put("dueTime", "");
		}else{
			remain.put("remainTime", answerTimer.getRemainTime());
			remain.put("dueTime", DateUtil.StringPattern(answerTimer.getDueTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_MS, DateUtil.YYYY_MM_DD_HH_MM_SS));
		}
		log.info("查询数据 answerTimer=="+answerTimer);
		resultMap.setSucResult(remain);
		return resultMap;
	}

	@Override
	@Transactional
	public ResultMapper consumeAnswerTime(String openId, String teacherId,
			String gradeCode, String consumeTime) {
		ResultMapper result = new ResultMapper();
		AnswerTimer query = new AnswerTimer();
		query.setOpenId(openId);
		query.setTeacherId(teacherId);
		query.setGradeCode(gradeCode);
		AnswerTimer answerTimer = answerTimerDao.queryAnswerTimerByOpenId(query);
		answerTimer.setUpdateTime(DateUtil.getNowTime());
		answerTimer.setRemainTime(answerTimer.getRemainTime().subtract(new BigDecimal(consumeTime)));
		answerTimerDao.updateAnswerTimer(answerTimer);
		log.info("修改成功："+answerTimer);
		result.setSucResult("修改成功！");
		return result;
	}

	@Override
	public ResultMapper getRemainAnswerTimes(String openId, String teacherId) {
		ResultMapper result = new ResultMapper();
		AnswerTimer query = new AnswerTimer();
		query.setOpenId(openId);
		query.setTeacherId(teacherId);
		List<AnswerTimer> list =answerTimerDao.queryTimeList(query);
		if(list == null) list = new ArrayList<AnswerTimer>();
		result.setSucResult(list);
		return result;
	}
}
