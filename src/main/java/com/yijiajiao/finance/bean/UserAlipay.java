package com.yijiajiao.finance.bean;

import java.io.Serializable;

public class UserAlipay implements Serializable{
	private int id;
	private String open_id;
	private String alipay_account;
	private String alipay_name;
	private String create_time;
	private int is_del;
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getOpen_id() {
		return open_id;
	}
	public void setOpen_id(String open_id) {
		this.open_id = open_id;
	}
	public String getAlipay_account() {
		return alipay_account;
	}
	public void setAlipay_account(String alipay_account) {
		this.alipay_account = alipay_account;
	}
	public String getAlipay_name() {
		return alipay_name;
	}
	public void setAlipay_name(String alipay_name) {
		this.alipay_name = alipay_name;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	@Override
	public String toString() {
		return "UserAlipay [id=" + id + ", openId=" + open_id
				+ ", alipay_account=" + alipay_account + ", alipay_name="
				+ alipay_name + ", create_time=" + create_time + "]";
	}
	
}
