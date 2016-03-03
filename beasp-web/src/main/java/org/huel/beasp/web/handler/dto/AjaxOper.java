package org.huel.beasp.web.handler.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Ajax数据传输对象
 * 
 * @author 001
 *
 */
public class AjaxOper {
	private int status;//返回状态
	private String msg;//返回信息
	private int result;//返回结果，1代表成功，其他代表失败
	private Map<String, Object> data = new HashMap<String, Object>();

	public AjaxOper() {
		super();
	}

	public AjaxOper(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public AjaxOper(int result) {
		super();
		this.result = result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
