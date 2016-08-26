package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.BatchPayment;
import com.yijiajiao.finance.dao.IBatchPaymentDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class IBatchPaymentDAOImpl implements IBatchPaymentDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public void insertBatchPayment(BatchPayment batchPayment) {
		sessionTemplate.insert("com.yijiajiao.finance.dao.IBatchPaymentDAO.insertBatchPayment", batchPayment);
	}
	@Override
	public void updateBatchPayment(BatchPayment batchPayment) {
		sessionTemplate.update("com.yijiajiao.finance.dao.IBatchPaymentDAO.updateBatchPayment", batchPayment);
	}
	@Override
	public BatchPayment selectBatchPaymentByBatch_no(String batch_no) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IBatchPaymentDAO.selectBatchPaymentByBatch_no", batch_no);
	}

}
