package com.yijiajiao.finance.service.impl;

import ch.qos.logback.classic.Logger;
import com.yijiajiao.finance.bean.ResultMapper;
import com.yijiajiao.finance.bean.SystemStatus;
import com.yijiajiao.finance.bean.UserAlipay;
import com.yijiajiao.finance.dao.IUserAlipayDAO;
import com.yijiajiao.finance.service.UserAlipayService;
import com.yijiajiao.finance.util.DateUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userAlipayService")
public class UserAlipayServiceImpl implements UserAlipayService {
	private static Logger log = (Logger) LoggerFactory.getLogger(UserAlipayServiceImpl.class);
	@Autowired
	private IUserAlipayDAO userAlipayDAO;
	@Override
	@Transactional
	public ResultMapper addUserAlipay(UserAlipay userAlipay) {
		ResultMapper resultBean = new ResultMapper();
		//验证数据是否为空
		boolean is_null= userAlipay.getOpen_id()==null || "".equals(userAlipay.getOpen_id()) || userAlipay.getAlipay_account()==null
				||"".equals(userAlipay.equals(userAlipay.getAlipay_account())) || userAlipay.getAlipay_name()==null || "".equals(userAlipay.getAlipay_name());
		if(is_null){
			resultBean.setFailMsg(SystemStatus.PARAM_NULL);
			return resultBean;
		}
		UserAlipay byOpenId = userAlipayDAO.queryUserAlipayByOpenId(userAlipay.getOpen_id());
		if(byOpenId != null){
			resultBean.setFailMsg(SystemStatus.USER_ALIPAY_HAS);
			return resultBean;
		}
		userAlipay.setCreate_time(DateUtil.getNowTime());
		int i = userAlipayDAO.insertUserAlipay(userAlipay);
		log.info("i===="+i+"保存的数据==="+userAlipay);
		resultBean.setSucResult("保存成功");
		return resultBean;
	}
	@Override
	public ResultMapper queryAllUsersAlipay() {
		return null;
	}
	@Override
	@Transactional
	public ResultMapper modUserAlipay(UserAlipay userAlipay) {
		ResultMapper resultBean = new ResultMapper();
		//验证数据是否为空
		boolean is_null= userAlipay.getOpen_id()==null || "".equals(userAlipay.getOpen_id()) || userAlipay.getAlipay_account()==null
				||"".equals(userAlipay.equals(userAlipay.getAlipay_account())) || userAlipay.getAlipay_name()==null || "".equals(userAlipay.getAlipay_name());
		if(is_null){
			resultBean.setFailMsg(SystemStatus.PARAM_NULL);
			return resultBean;
		}

		UserAlipay user=userAlipayDAO.queryUserAlipayByOpenId(userAlipay.getOpen_id());
		if(user == null ){
			userAlipayDAO.insertUserAlipay(userAlipay);
		}else{
			user.setAlipay_account(userAlipay.getAlipay_account());
			user.setAlipay_name(userAlipay.getAlipay_name());
			user.setCreate_time(DateUtil.getNowTime());
			userAlipayDAO.updateUserAlipay(userAlipay);
		}
		resultBean.setSucResult("修改成功！！");
		return resultBean;
	}
	@Override
	public ResultMapper queryUserAlipayByOpenId(String openId) {
		ResultMapper resultBean = new ResultMapper();
		UserAlipay alipay = userAlipayDAO.queryUserAlipayByOpenId(openId);
		if(alipay == null ) resultBean.setFailMsg(SystemStatus.ID_NOT_FOUND);
		else resultBean.setSucResult(alipay);
		return resultBean;
	}
	@Override
	@Transactional
	public ResultMapper delUserAlipayByOpenId(String openId) {
		ResultMapper resultBean = new ResultMapper();
		userAlipayDAO.deleteUserAlipay(openId);
		resultBean.setSucResult("解绑成功");
		return resultBean;
	}

}
