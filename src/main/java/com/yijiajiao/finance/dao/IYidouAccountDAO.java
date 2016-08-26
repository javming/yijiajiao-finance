package com.yijiajiao.finance.dao;


import com.yijiajiao.finance.bean.YidouAccount;

public interface IYidouAccountDAO {
	YidouAccount selectByOpenId(String openId);
	void insertYidouAccount(YidouAccount yidouAccount);
	void updateYidouAccount(YidouAccount yidouAccount);
}
