package com.yijiajiao.finance.dao;

import com.yijiajiao.finance.bean.MoneyTimer;

import java.util.List;


public interface IMoneyTimerDAO {
	/**
	 * 
	 * @description
	 * @date 2015-12-25
	 * @return MoneyTimer
	 * @param openId
	 */
	MoneyTimer queryMoneyTimerByOpenId(String openId);

	/**
	 * @description
	 * @date 2015-12-25
	 * @return void
	 * @param moneyTimer
	 */
	void saveMoneyTimer(MoneyTimer moneyTimer);

	/**
	 * @description
	 * @date 2015-12-25
	 * @return void
	 * @param moneyTimer
	 */
	void updateMoneyTimer(MoneyTimer moneyTimer);
	/**
	 *@description 获得所有具有收入的教师账户
	 *@date 2016-3-21
	 *@return List<String>
	 *@return
	 */
	List<String> queryAllOpenIds();
}
