package com.yijiajiao.finance.timer;

import com.yijiajiao.finance.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("timerService")
public class TimerService {
	protected static Logger log = LoggerFactory.getLogger(TimerService.class);
	@Autowired
	//private IBatchPayService batchPayService;
	/**
	 *@description 每月结算定时器，每月指定时间结算上个月所有老师的收入
	 *@date 2016-5-6
	 */
	public void settleAccountsForMonth() {
		log.info("今天是本月第一天："+ DateUtil.getNowTime()+",进行教师上月收入结算");
			//int count = batchPayService.getAndSaveSalary();
			//log.info("结算完成，人数="+count);
		
	}
	public void testTimer() {
		// TODO Auto-generated method stub
		System.out.println(DateUtil.getNowTime());
		
	}
}
