package com.yijiajiao.finance.service.impl;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.MoneyTimer;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.dao.IFinanceLogDAO;
import com.yijiajiao.finance.dao.IMoneyTimerDAO;
import com.yijiajiao.finance.dao.ISettleAccountsDAO;
import com.yijiajiao.finance.service.IMoneyTimerService;
import com.yijiajiao.finance.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


@Service("moneyTimerService")
public class IMoneyTimerServiceImpl implements IMoneyTimerService {
	private static Logger log = (Logger) LoggerFactory.getLogger(IMoneyTimerServiceImpl.class);
	@Autowired
	private IMoneyTimerDAO moneyTimerDAO;
	@Autowired
	private IFinanceLogDAO financeLogDAO;
	@Autowired
	private ISettleAccountsDAO settleAccountsDAO;

	@Override
	public ResultMapper getRemainMoney(String openId) {
		ResultMapper resultMapper = new ResultMapper();
		MoneyTimer moneyTimer = moneyTimerDAO
				.queryMoneyTimerByOpenId(openId);
		if (moneyTimer == null) {
			moneyTimer=new MoneyTimer();
			moneyTimer.setOpenId(openId);
			moneyTimer.setId(0);
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
		}else{
			moneyTimer.setUpdateTime(DateUtil.StringPattern(moneyTimer.getUpdateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS_MS, DateUtil.YYYY_MM_DD_HH_MM_SS));
		}
		DateUtil du = new DateUtil();
		//获得本月第一天
		Date beginMonth = du.calcBeginMonth(DateUtil.getNowTime());
		//获得本月最后一天
		Date endMonth = du.calcEndMonth(DateUtil.getNowTime());
		//获得本月11号日期  11 00:00:00
		Date ten = DateUtil.addDays(beginMonth, 10);
		Date now = new Date();
		BigDecimal income1;
		BigDecimal income2;
		if(now.getTime()>= beginMonth.getTime() && now.getTime() < ten.getTime()){
			//计算本期账户余额,如果查询日期在本月1号到11号0点之前，本期余额为上个月收入
			System.out.println("如果查询日期在本月1号到11号0点之前，本期余额为上个月收入");
			income1 = financeLogDAO.queryTeacherIncomeForLastMonth(openId);
			if(income1==null) income1=BigDecimal.valueOf(0.00);
			income2= settleAccountsDAO.queryTeacherIncomeForLastMonth(openId);
			if(income2==null) income2=BigDecimal.valueOf(0.00);
		}else {
			System.out.println("如果查询日期在本月11号0点到月末之前，本期余额为本月收入");
			//计算本期账户余额,如果查询日期在本月11号0点到月末之前，本期余额为本月收入
			income1 = financeLogDAO.queryTeacherIncomeForThisMonth(openId);
			if(income1==null) income1=BigDecimal.valueOf(0.00);
			income2= settleAccountsDAO.queryTeacherIncomeForThisMonth(openId);
			if(income2==null) income2=BigDecimal.valueOf(0.00);
		}
		moneyTimer.setWithdrawalCash(income1.add(income2));
		log.info("查询数据为 moneyTimer=="+moneyTimer);
		resultMapper.setSucResult(moneyTimer);
		return resultMapper;
	}


}
