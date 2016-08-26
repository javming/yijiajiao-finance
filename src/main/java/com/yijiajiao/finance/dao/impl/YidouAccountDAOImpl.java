package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.YidouAccount;
import com.yijiajiao.finance.dao.IYidouAccountDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("yidouAccountDAO")
public class YidouAccountDAOImpl implements IYidouAccountDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public YidouAccount selectByOpenId(String openId) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IYidouAccountDAO.selectByOpenId", openId);
	}

	@Override
	public void insertYidouAccount(YidouAccount yidouAccount) {
		sessionTemplate.insert("com.yijiajiao.finance.dao.IYidouAccountDAO.insertYidouAccount",yidouAccount);
	}

	@Override
	public void updateYidouAccount(YidouAccount yidouAccount) {
		sessionTemplate.update("com.yijiajiao.finance.dao.IYidouAccountDAO.updateYidouAccount", yidouAccount);

	}

}
