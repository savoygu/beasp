package org.huel.beasp.entity.common;

import java.util.List;

public class QueryResult<T> {
	
	/**查询得出的数据List**/
	public List<T> resultList;
	/**查询得出的总数**/
	public long totalRecord;

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}

}
