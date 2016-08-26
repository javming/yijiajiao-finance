package com.yijiajiao.finance.dao;

import com.yijiajiao.finance.bean.UserAlipay;

import java.util.List;


public interface IUserAlipayDAO {
	int insertUserAlipay(UserAlipay userAlipay);
	UserAlipay queryUserAlipayByOpenId(String open_id);
	List<UserAlipay> queryUserAlipayByOpenIds(List<String> openIds);
	void updateUserAlipay(UserAlipay userAlipay);
	void deleteUserAlipay(String open_id);
}
