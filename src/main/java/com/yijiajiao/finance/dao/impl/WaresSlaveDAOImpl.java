package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.WaresSlave;
import com.yijiajiao.finance.dao.IWaresSlaveDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class WaresSlaveDAOImpl implements IWaresSlaveDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public void insertWaresSlave(WaresSlave waresSlave) {
		sessionTemplate.insert("com.yijiajiao.finance.dao.IWaresSlaveDAO.insertWaresSlave", waresSlave);
	}

	@Override
	public void updateWaresSlave(WaresSlave waresSlave) {
		// TODO Auto-generated method stub

	}

}
