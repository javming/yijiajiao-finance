package com.yijiajiao.finance.controller;

import com.yijiajiao.finance.bean.DailySettleAccounts;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.service.ISettleAccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Controller
@Path("/settleaccounts")
public class SettleAccountsController {
	@Autowired
	private ISettleAccountsService settleAccountsService;
	
	@POST
	@Path("/dailysettle")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper addDailySettleAccounts(List<DailySettleAccounts> dailySettleAccounts){
		return settleAccountsService.addDailySettleAccounts(dailySettleAccounts);
	}
	
	
	
}
