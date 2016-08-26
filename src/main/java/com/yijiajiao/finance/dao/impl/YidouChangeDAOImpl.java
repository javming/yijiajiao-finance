package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.YidouChange;
import com.yijiajiao.finance.dao.IYidouChangeDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;



public class YidouChangeDAOImpl implements IYidouChangeDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public void insertYidouChange(YidouChange yidouChange) {
		sessionTemplate.insert("com.yijiajiao.finance.dao.IYidouChangeDAO.insertYidouChange", yidouChange);
	}

}
