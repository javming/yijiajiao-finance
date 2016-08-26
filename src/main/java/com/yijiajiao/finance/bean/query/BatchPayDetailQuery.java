package com.yijiajiao.finance.bean.query;


import com.yijiajiao.finance.bean.BatchPayDetail;

public class BatchPayDetailQuery extends BatchPayDetail {
	private int pageNum;
	private int pageSize;
	private int startRow;
	private String queryDate;
	
	private String _;
	
	
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public String get_() {
		return _;
	}
	public void set_(String _) {
		this._ = _;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		// 计算开始行
		if (pageNum != 0) {
			this.startRow = (pageNum - 1) * pageSize;
			this.pageNum = pageNum;
		} else {
			this.pageNum = pageNum;
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		// 计算开始行
		if (pageSize != 0) {
			this.startRow = (pageNum - 1) * pageSize;
			this.pageSize = pageSize;
		} else {
			this.pageSize = pageSize;
		}
	}
	
	
}
