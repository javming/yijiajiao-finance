package com.yijiajiao.finance.controller;

import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.UserAlipay;
import com.yijiajiao.finance.service.UserAlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Controller
@Path("/userAlipay")
public class UserAlipayController {
	@Autowired
	private UserAlipayService userAlipayService;
	
	/**
	 *@description 账户绑定支付宝账号
	 *@date 2016-3-29
	 *@return ResultMapper
	 *@param userAliapy
	 *@return
	 */
	@POST
	@Path("/adduser")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultMapper addUserAlipay(UserAlipay userAliapy){
		return userAlipayService.addUserAlipay(userAliapy);
	}
	/**
	 *@description 通过openId查询
	 *@date 2016-3-29
	 *@return ResultMapper
	 *@param openId
	 *@return
	 */
	@GET
	@Path("/queryByOpenId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultMapper queryUserAlipayByOpenId(@QueryParam("openId") String openId){
		return userAlipayService.queryUserAlipayByOpenId(openId);
	}
	/**
	 *@description 解绑支付宝
	 *@date 2016-3-29
	 *@return ResultMapper
	 *@param openId
	 *@return
	 */
	@GET
	@Path("/delByOpenId")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResultMapper delUserAlipayByOpenId(@QueryParam("openId") String openId){
		return userAlipayService.delUserAlipayByOpenId(openId);
	}
	
	public ResultMapper queryAllUsersAlipay(){
		return userAlipayService.queryAllUsersAlipay();
	}
}
