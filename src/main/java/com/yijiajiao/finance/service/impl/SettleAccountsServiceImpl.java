package com.yijiajiao.finance.service.impl;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.DailySettleAccounts;
import com.yijiajiao.finance.bean.MoneyTimer;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.dao.IMoneyTimerDAO;
import com.yijiajiao.finance.dao.ISettleAccountsDAO;
import com.yijiajiao.finance.service.ISettleAccountsService;
import com.yijiajiao.finance.util.Config;
import com.yijiajiao.finance.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


@Service
public class SettleAccountsServiceImpl implements
		ISettleAccountsService {
	protected static Logger log = (Logger) LoggerFactory.getLogger(SettleAccountsServiceImpl.class);
	@Autowired
	private ISettleAccountsDAO dailySettleAccountsDAO;
	@Autowired
	private IMoneyTimerDAO moneyTimerDAO;
	@Override
	@Transactional
	public ResultMapper addDailySettleAccounts(List<DailySettleAccounts> dailySettleAccounts) {
		ResultMapper resultBean = new ResultMapper();
		log.info("请求数量==="+dailySettleAccounts.size());
		for(DailySettleAccounts dsa: dailySettleAccounts){
			int count = dailySettleAccountsDAO.queryInfoForCheckSame(dsa);
			if(count>0){
				log.info("数据重复！跳过此条！:"+dsa);
				continue;
			}
			if(dsa.getSaveTime()==null) dsa.setSaveTime(DateUtil.getNowTime());
			MoneyTimer moneyTimer = moneyTimerDAO.queryMoneyTimerByOpenId(dsa.getSellOpenId());
			BigDecimal percentage = BigDecimal.valueOf(Config.getDouble("percentage"));
			BigDecimal settleMoney =dsa.getSettleMoney().multiply(percentage);
			System.out.println("settleMoney=============="+settleMoney);
			if(moneyTimer == null) moneyTimer = new MoneyTimer();
			moneyTimer.setVariableMoney(moneyTimer.getVariableMoney().subtract(settleMoney));
			moneyTimer.setWithdrawalCash(settleMoney.add(moneyTimer.getWithdrawalCash()));
			moneyTimer.setTotalSettleMoney(settleMoney.add(moneyTimer.getTotalSettleMoney()));
			moneyTimer.setTotalMoney(moneyTimer.getVariableMoney().add(moneyTimer.getWithdrawalCash()));
			moneyTimer.setUpdateTime(DateUtil.getNowTime());
			dsa.setSettleMoney(settleMoney);
			dailySettleAccountsDAO.insertDailySettleAccounts(dsa);
			if(moneyTimer.getOpenId() == null){
				moneyTimer.setOpenId(dsa.getSellOpenId());
				moneyTimerDAO.saveMoneyTimer(moneyTimer);
			}else{
				moneyTimerDAO.updateMoneyTimer(moneyTimer);
			}
			log.info("修改的数据："+moneyTimer);
		}
		resultBean.setSucResult("保存成功！");
		return resultBean;
	}

}
