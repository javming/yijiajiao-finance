package com.yijiajiao.finance.service;


import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.UserAlipay;

public interface UserAlipayService {
	/**
	 *@description 添加教师账户绑定支付宝账号
	 *@date 2016-3-21
	 *@return ResultMapper
	 *@param userAliapy
	 *@return
	 */
	ResultMapper addUserAlipay(UserAlipay userAliapy);
	/**
	 *@description 
	 *@date 2016-3-21
	 *@return ResultMapper
	 *@return
	 */
	ResultMapper queryAllUsersAlipay();
	
	/**
	 *@description  修改支付宝账号
	 *@date 2016-3-29
	 *@return ResultMapper
	 *@param userAliapy
	 *@return
	 */
	ResultMapper modUserAlipay(UserAlipay userAliapy);
	/**
	 *@description   通过openId获取支付宝账号
	 *@date 2016-3-29
	 *@return ResultMapper
	 *@param openId
	 *@return
	 */
	ResultMapper queryUserAlipayByOpenId(String openId);
	/**
	 *@description 通过openId删除（逻辑删除）
	 *@date 2016-3-29
	 *@return ResultMapper
	 *@param openId
	 *@return
	 */
	ResultMapper delUserAlipayByOpenId(String openId);
}
