package com.yijiajiao.finance.bean.page;

import java.io.Serializable;
import java.util.List;

public class Page<E> implements Serializable{
	public static int DEFAULT_PAGE_SIEZE=15;
	private int pageNum=1;//当前页
	private int pageSize=DEFAULT_PAGE_SIEZE;//每页有多少条
	private int totalCount;//总条数
	private int pageCount=-1;//总页数
	private int previousPageIndex,nextPageIndex,lastPageIndex;
	private List<E> results;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		if(totalCount<0){
			this.totalCount =0;
			return ;
		}
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount){
		this.pageCount= pageCount;
	}
	public int getPreviousPageIndex() {
		return pageNum-1;
	}
	public int getNextPageIndex() {
		return pageNum+1;
	}
	public int getLastPageIndex() {
		return pageCount;
	}
	public List<E> getResults() {
		return results;
	}
	public void setResults(List<E> results) {
		this.results = results;
	}
	
}
