package com.yijiajiao.finance.dao;

import com.yijiajiao.finance.bean.BatchPayDetail;
import com.yijiajiao.finance.bean.query.BatchPayDetailQuery;

import java.math.BigDecimal;
import java.util.List;



public interface IBatchPayDetailDAO {
	void insertBatchPayDetail(BatchPayDetail batchPayDetail);
	/**
	 *@description	将每个教师账户的该批次的付款改为已处理，表示转账成功
	 *@date 2016-3-21
	 *@return void
	 *@param batch_no
	 */
	void updateIsDispose(String batch_no);
	List<BatchPayDetail> queryByBatch_no(String batch_no);
	List<BatchPayDetail> getBatchPayDetailsByConditions(
            BatchPayDetailQuery batchPayDetailQuery);
	int getCountByConditions(BatchPayDetailQuery batchPayDetailQuery);
	/**
	 *@description	增加批次号和流水号
	 *@date 2016-4-1
	 *@return void
	 *@param bpd
	 */
	void updateNumber(BatchPayDetail bpd);
	
	int getCountByDateAndPhone(BatchPayDetailQuery batchPayDetailQuery);
	BigDecimal getSumIncome(BatchPayDetailQuery batchPayDetailQuery);
}
