package com.yijiajiao.finance.dao.impl;

import com.yijiajiao.finance.bean.BatchPayDetail;
import com.yijiajiao.finance.bean.query.BatchPayDetailQuery;
import com.yijiajiao.finance.dao.IBatchPayDetailDAO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class IBatchPayDetailDAOImpl implements IBatchPayDetailDAO {
	@Autowired
	private SqlSessionTemplate sessionTemplate;
	@Override
	public void insertBatchPayDetail(BatchPayDetail batchPayDetail) {
		sessionTemplate.insert("com.yijiajiao.finance.dao.IBatchPayDetailDAO.insertBatchPayDetail", batchPayDetail);
	}
	@Override
	public void updateIsDispose(String batch_no) {
		sessionTemplate.update("com.yijiajiao.finance.dao.IBatchPayDetailDAO.updateIsDispose", batch_no);
	}
	@Override
	public List<BatchPayDetail> queryByBatch_no(String batch_no) {
		return sessionTemplate.selectList("com.yijiajiao.finance.dao.IBatchPayDetailDAO.queryByBatch_no", batch_no);
	}
	@Override
	public List<BatchPayDetail> getBatchPayDetailsByConditions(
			BatchPayDetailQuery batchPayDetailQuery) {
		return sessionTemplate.selectList("com.yijiajiao.finance.dao.IBatchPayDetailDAO.getBatchPayDetailsByConditions", batchPayDetailQuery);
	}
	@Override
	public int getCountByConditions(BatchPayDetailQuery batchPayDetailQuery) {
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IBatchPayDetailDAO.getCountByConditions", batchPayDetailQuery);
	}
	@Override
	public void updateNumber(BatchPayDetail bpd) {
		sessionTemplate.update("com.yijiajiao.finance.dao.IBatchPayDetailDAO.updateNumber", bpd);
	}
	@Override
	public int getCountByDateAndPhone(BatchPayDetailQuery batchPayDetailQuery) {
		// TODO Auto-generated method stub
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IBatchPayDetailDAO.getCountByDateAndPhone", batchPayDetailQuery);
	}
	@Override
	public BigDecimal getSumIncome(BatchPayDetailQuery batchPayDetailQuery) {
		// TODO Auto-generated method stub
		return sessionTemplate.selectOne("com.yijiajiao.finance.dao.IBatchPayDetailDAO.getSumIncome", batchPayDetailQuery);
	}

}
