package com.yijiajiao.finance.bean;

import java.io.Serializable;

/**
 * @description 用户交易账务信息实体
 */
public class ResultMapper implements Serializable {
	private int code;
	private String requestId = "";
	private String httpCode = "";
	private String message;
	private Object result = "";

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	/**
	 * @param status
	 *            异常返回枚举类型
	 */
	public void setFailMsg(SystemStatus status) {
		setCode(status.getCode());
		setMessage(status.getStr());
		setResult("");
	}

	/**
	 * @param result
	 *            成功返回数据
	 */
	public void setSucResult(Object result) {
		setCode(SystemStatus.OK.getCode());
		setMessage(SystemStatus.OK.getStr());
		setResult(result);
	}

	@Override
	public String toString() {
		return "ResultMapper [code=" + code + ", requestId=" + requestId
				+ ", httpCode=" + httpCode + ", message=" + message
				+ ", result=" + result + "]";
	}

}
