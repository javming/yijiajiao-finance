package com.yijiajiao.finance.service.impl;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.SystemStatus;
import com.yijiajiao.finance.bean.YidouAccount;
import com.yijiajiao.finance.bean.YidouChange;
import com.yijiajiao.finance.dao.IYidouAccountDAO;
import com.yijiajiao.finance.service.IYidouAccountService;
import com.yijiajiao.finance.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service("yidouAccountService")
public class YidouAccountServiceImpl implements IYidouAccountService {
	@Autowired
	private IYidouAccountDAO yidouAccountDAO;
	private static Logger log = (Logger) LoggerFactory.getLogger(YidouAccountServiceImpl.class);
	@Override
	public ResultMapper queryByOpenId(String openId) {
		ResultMapper resultBean = new ResultMapper();
		YidouAccount yidouAccount = yidouAccountDAO.selectByOpenId(openId);
		if(yidouAccount == null){
			yidouAccount = new YidouAccount();
			yidouAccount.setOpenId(openId);
			yidouAccount.setYidouCount(new BigDecimal("0.00"));
			yidouAccount.setCreateTime(DateUtil.getNowTime());
			yidouAccount.setLastUpdateTime(DateUtil.getNowTime());
		}
		log.info("亿豆余额为："+yidouAccount.getYidouCount()+"查询数据为："+yidouAccount);
		resultBean.setSucResult(yidouAccount);
		return resultBean;
	}

	@Override
	@Transactional
	public ResultMapper saveYidouAccount(YidouAccount yidouAccount) {
		ResultMapper resultBean = new ResultMapper();
		yidouAccount.setCreateTime(DateUtil.getNowTime());
		yidouAccount.setLastUpdateTime(DateUtil.getNowTime());
		yidouAccountDAO.insertYidouAccount(yidouAccount);
		resultBean.setSucResult("保存成功！");
		return resultBean;
	}

	@Override
	@Transactional
	public ResultMapper modYidouAccount(YidouAccount yidouAccount) {
		ResultMapper resultBean = new ResultMapper();
		YidouAccount account = yidouAccountDAO.selectByOpenId(yidouAccount.getOpenId());
		if(yidouAccount.getIsSpend() ==0){
			account.setYidouCount(account.getYidouCount().subtract(yidouAccount.getYidouCount()));
		}else{
			account.setYidouCount(account.getYidouCount().add(yidouAccount.getYidouCount()));
		}
		account.setLastUpdateTime(DateUtil.getNowTime());
		yidouAccountDAO.updateYidouAccount(account);
		resultBean.setSucResult("修改成功！");
		return resultBean;
	}

	@Override
	@Transactional
	public ResultMapper yidouCharge(YidouChange yidouChange) {
		ResultMapper resultBean = new ResultMapper();
		yidouChange.setChangeDate(DateUtil.getNowTime());
		//yidouChangeDAO.insertYidouChange(yidouChange);

		YidouAccount yidouAccount = yidouAccountDAO.selectByOpenId(yidouChange.getOpenId());
		if(yidouAccount == null ) {
			yidouAccount = new YidouAccount();
			yidouAccount.setOpenId(yidouChange.getOpenId());
			yidouAccount.setYidouCount(yidouChange.getYidouChange());
			yidouAccount.setLastUpdateTime(DateUtil.getNowTime());
			yidouAccount.setCreateTime(DateUtil.getNowTime());
			yidouAccountDAO.insertYidouAccount(yidouAccount);
		}else{
			yidouAccount.setYidouCount(yidouAccount.getYidouCount().add(yidouChange.getYidouChange()));
			yidouAccount.setLastUpdateTime(DateUtil.getNowTime());
			yidouAccountDAO.updateYidouAccount(yidouAccount);
		}
		resultBean.setSucResult("保存成功");
		return resultBean;
	}

	@Override
	public ResultMapper yidouConsume(String openId, BigDecimal yidouCount) {
		ResultMapper resultBean = new ResultMapper();
		YidouAccount yidouAccount = yidouAccountDAO.selectByOpenId(openId);
		if(yidouAccount.getYidouCount().compareTo(yidouCount) == -1){
			resultBean.setFailMsg(SystemStatus.REMAINYIDOU_NOT_ENOUGH);
			return resultBean;
		}
		yidouAccount.setYidouCount(yidouAccount.getYidouCount().subtract(yidouCount));
		yidouAccount.setLastUpdateTime(DateUtil.getNowTime());
		yidouAccountDAO.updateYidouAccount(yidouAccount);
		resultBean.setSucResult("支付成功！");
		return resultBean;
	}

}
