package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.UserAlipay;
import com.yijiajiao.finance.dao.IUserAlipayDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("userAlipayDAO")
public class IUserAlipayDAOImpl implements IUserAlipayDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public int insertUserAlipay(UserAlipay userAlipay) {
		return sessionTemplate.insert("com.yijiajiao.finance.dao.IUserAlipayDAO.insertUserAlipay", userAlipay);
	}
	@Override
	public UserAlipay queryUserAlipayByOpenId(String open_id) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IUserAlipayDAO.queryUserAlipayByOpenId", open_id);
	}
	@Override
	public List<UserAlipay> queryUserAlipayByOpenIds(List<String> openIds) {
		return sessionTemplate.selectList("com.yijiajiao.finance.dao.IUserAlipayDAO.queryUserAlipayByOpenIds", openIds);
	}
	@Override
	public void updateUserAlipay(UserAlipay userAlipay) {
		sessionTemplate.update("com.yijiajiao.finance.dao.IUserAlipayDAO.updateUserAlipay", userAlipay);
	}
	@Override
	public void deleteUserAlipay(String open_id) {
		sessionTemplate.update("com.yijiajiao.finance.dao.IUserAlipayDAO.deleteUserAlipay", open_id);
	}

}
