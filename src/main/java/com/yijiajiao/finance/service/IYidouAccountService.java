package com.yijiajiao.finance.service;

import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.YidouAccount;
import com.yijiajiao.finance.bean.YidouChange;

import java.math.BigDecimal;



public interface IYidouAccountService {
	ResultMapper queryByOpenId(String openId);
	ResultMapper saveYidouAccount(YidouAccount yidouAccount);
	ResultMapper modYidouAccount(YidouAccount yidouAccount);
	ResultMapper yidouCharge(YidouChange yidouChange);
	ResultMapper yidouConsume(String openId, BigDecimal yidouCount);
}
