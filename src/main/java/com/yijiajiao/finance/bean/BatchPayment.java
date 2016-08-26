package com.yijiajiao.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class BatchPayment implements Serializable {
	private int id;
	private String email;//付款账号
	private String  batch_no;//批量转款的批次号
	private int batch_num;//付款总笔数
	private BigDecimal batch_fee;//付款总金额
	private String pay_date;//支付日日期
	private String notify_time;//通知时间 
	private String notify_type;//通知类型
	private String notify_id;//通知校验ID
	private String pay_user_id;//付款账号ID
	private String pay_user_name;//付款账号姓名
	private String pay_account_no;//付款账号(付款成功后支付宝返回的付款账号)
	private String sign;//签名
	private String sign_type;//签名方式
	private int is_dispose;//是否处理 0未处理 1已处理
	private String success_details;//转账成功的详细信息
	private String fail_details;//转账失败的详细信息
	private List<BatchPayDetail> batchPayDetails;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBatch_no() {
		return batch_no;
	}
	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public int getBatch_num() {
		return batch_num;
	}
	public void setBatch_num(int batch_num) {
		this.batch_num = batch_num;
	}
	public BigDecimal getBatch_fee() {
		return batch_fee;
	}
	public void setBatch_fee(BigDecimal batch_fee) {
		this.batch_fee = batch_fee;
	}
	public String getPay_date() {
		return pay_date;
	}
	public void setPay_date(String pay_date) {
		this.pay_date = pay_date;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getPay_user_id() {
		return pay_user_id;
	}
	public void setPay_user_id(String pay_user_id) {
		this.pay_user_id = pay_user_id;
	}
	public String getPay_user_name() {
		return pay_user_name;
	}
	public void setPay_user_name(String pay_user_name) {
		this.pay_user_name = pay_user_name;
	}
	public String getPay_account_no() {
		return pay_account_no;
	}
	public void setPay_account_no(String pay_account_no) {
		this.pay_account_no = pay_account_no;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public int getIs_dispose() {
		return is_dispose;
	}
	public void setIs_dispose(int is_dispose) {
		this.is_dispose = is_dispose;
	}
	public String getSuccess_details() {
		return success_details;
	}
	public void setSuccess_details(String success_details) {
		this.success_details = success_details;
	}
	public String getFail_details() {
		return fail_details;
	}
	public void setFail_details(String fail_details) {
		this.fail_details = fail_details;
	}
	public List<BatchPayDetail> getBatchPayDetails() {
		return batchPayDetails;
	}
	public void setBatchPayDetails(List<BatchPayDetail> batchPayDetails) {
		this.batchPayDetails = batchPayDetails;
	}
	@Override
	public String toString() {
		return "BatchPayment [id=" + id + ", batch_no=" + batch_no
				+ ", batch_num=" + batch_num + ", batch_fee=" + batch_fee
				+ ", pay_date=" + pay_date + ", notify_time=" + notify_time
				+ ", notify_type=" + notify_type + ", notify_id=" + notify_id
				+ ", pay_user_id=" + pay_user_id + ", pay_user_name="
				+ pay_user_name + ", pay_account_no=" + pay_account_no
				+ ", is_dispose=" + is_dispose + ", success_details=" + success_details + ", fail_details="
				+ fail_details + ", batchPayDetails=" + batchPayDetails + "]";
	}

}
