package com.yijiajiao.finance.bean;

import java.math.BigDecimal;

public class BatchPayDetail {
	private int id;
	private String teacher_name;//姓名
	private String phone_num;//手机号
	private String start_time;//结算起时间
	private String end_time;//结算止时间
	private String running_no;//流水号
	private String proceeds_account;//收款方支付宝账号
	private String proceeds_name;//收款账号支付宝姓名
	private BigDecimal proceeds_fee;//收款金额
	private String remark;//备注说明
	private String open_id;
	private String batch_no;//批次号
	private String proceeds_time;//收款时间
	private int is_dispose;


	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}


	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getProceeds_time() {
		return proceeds_time;
	}

	public void setProceeds_time(String proceeds_time) {
		this.proceeds_time = proceeds_time;
	}

	public int getIs_dispose() {
		return is_dispose;
	}

	public void setIs_dispose(int is_dispose) {
		this.is_dispose = is_dispose;
	}

	public String getOpen_id() {
		return open_id;
	}

	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRunning_no() {
		return running_no;
	}

	public void setRunning_no(String running_no) {
		this.running_no = running_no;
	}

	public String getProceeds_account() {
		return proceeds_account;
	}

	public void setProceeds_account(String proceeds_account) {
		this.proceeds_account = proceeds_account;
	}

	public String getProceeds_name() {
		return proceeds_name;
	}

	public void setProceeds_name(String proceeds_name) {
		this.proceeds_name = proceeds_name;
	}
	

	public BigDecimal getProceeds_fee() {
		return proceeds_fee;
	}

	public void setProceeds_fee(BigDecimal proceeds_fee) {
		this.proceeds_fee = proceeds_fee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}
	public String getPayString(){
		return running_no+"^"+proceeds_account+"^"+proceeds_name+"^"+proceeds_fee+"^"+remark;
	}
}
