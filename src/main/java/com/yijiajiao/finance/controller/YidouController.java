package com.yijiajiao.finance.controller;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.YidouChange;
import com.yijiajiao.finance.service.IYidouAccountService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

@Controller
@Path("/yidou")
public class YidouController {
	@Autowired
	private IYidouAccountService yidouAccountService;
	private static Logger log = (Logger) LoggerFactory.getLogger(YidouController.class);
	/**
	 *@description 获得个人账户亿豆余额
	 *@date 2016-6-13
	 */
	@GET
	@Path("/remainYidou")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper getRemainYidou(@QueryParam("openId") String openId){
		log.info("请求参数：openId="+openId);
		return yidouAccountService.queryByOpenId(openId);
	}
	
	/**
	 *@description 亿豆充值
	 *@date 2016-6-13
	 */
	@POST
	@Path("/yidouCharge")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResultMapper yidouCharge(YidouChange yidouChange){
		return yidouAccountService.yidouCharge(yidouChange);
	}
	
	/**
	 *@description 亿豆支付
	 *@date 2016-6-13
	 */
	@GET
	@Path("/yidouPay")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultMapper yidouPay(@QueryParam("openId")String openId,@QueryParam("yidouCount")BigDecimal yidouCount){
		return yidouAccountService.yidouConsume(openId,yidouCount);
	}
}
