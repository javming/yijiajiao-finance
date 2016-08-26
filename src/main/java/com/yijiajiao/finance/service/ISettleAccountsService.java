package com.yijiajiao.finance.service;


import com.yijiajiao.finance.bean.DailySettleAccounts;
import com.yijiajiao.finance.bean.ResultMapper;

import java.util.List;

public interface ISettleAccountsService {
	ResultMapper addDailySettleAccounts(List<DailySettleAccounts> dailySettleAccounts);
}
