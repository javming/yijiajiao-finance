package com.yijiajiao.finance.service;


import com.yijiajiao.finance.bean.ResultMapper;

public interface IMoneyTimerService {

	/**
	 * @description 通过用户openid获取账户金额信息，返回ResultMapper实体
	 * @date 2015-12-25
	 * @return ResultMapper
	 * @param openId
	 */
	ResultMapper getRemainMoney(String openId);

}
