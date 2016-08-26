package com.yijiajiao.finance.dao;


import com.yijiajiao.finance.bean.BatchPayment;

public interface IBatchPaymentDAO {
	void insertBatchPayment(BatchPayment batchPayment);
	void updateBatchPayment(BatchPayment batchPayment);
	BatchPayment selectBatchPaymentByBatch_no(String batch_no);
}
