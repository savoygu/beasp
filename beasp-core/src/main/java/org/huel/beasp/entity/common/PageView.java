package org.huel.beasp.entity.common;

import java.util.List;

import org.huel.beasp.utils.Constants;

public class PageView<T> {
	/* 分页数据 */
	private List<T> records;
	/* 页码开始索引和结束索引 */
	private PageIndex pageIndex;
	/* 总页数 */
	private long totalPage;
	/* 每页显示记录数 */
	private int maxResult = Constants.SEARCH_PAGE_SIZE_TWELVE_FRONT;
	/* 当前页 */
	private int currentPage = 1;
	/* 总记录数 */
	private long totalRecord;
	/* 页码数量 */
	private int pageCode = 10;

	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		this.currentPage = currentPage;
	}

	/**
	 * 获取每页第一个数据
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return (this.currentPage - 1) * this.maxResult;
	}

	public void setQueryResult(QueryResult<T> qr) {
		setTotalRecord(qr.getTotalRecord());
		setRecords(qr.getResultList());
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public PageIndex getPageIndex() {
		return pageIndex;
	}

	/*
	 * public void setPageIndex(PageIndex pageIndex) { this.pageIndex =
	 * pageIndex; }
	 */

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
		this.pageIndex = PageIndex.getPageIndex(pageCode, currentPage,
				totalPage);
	}

	public int getMaxResult() {
		return maxResult;
	}

	/*public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}*/

	public int getCurrentPage() {
		return currentPage;
	}

	/*public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}*/

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord%this.maxResult == 0 ? this.totalRecord/this.maxResult : this.totalRecord/this.maxResult + 1);
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

}
