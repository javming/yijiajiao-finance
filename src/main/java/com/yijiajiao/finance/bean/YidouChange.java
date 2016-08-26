package com.yijiajiao.finance.bean;

import java.math.BigDecimal;

public class YidouChange {
	private int id;
	private String openId;
	private BigDecimal yidouChange;
	private int isCharge;//1充值 2消费
	private int chargeType;//充值类型 0消费，1现金，2积分兑换，3可提现金额兑换，4退款
	private String orderNumber;//订单号
	private BigDecimal resuourceSpend;//花费的资源量：现金、积分、可提现金额
	private String changeDate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public BigDecimal getYidouChange() {
		return yidouChange;
	}
	public void setYidouChange(BigDecimal yidouChange) {
		this.yidouChange = yidouChange;
	}
	public int getIsCharge() {
		return isCharge;
	}
	public void setIsCharge(int isCharge) {
		this.isCharge = isCharge;
	}
	public int getChargeType() {
		return chargeType;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public BigDecimal getResuourceSpend() {
		return resuourceSpend;
	}
	public void setResuourceSpend(BigDecimal resuourceSpend) {
		this.resuourceSpend = resuourceSpend;
	}
	public String getChangeDate() {
		return changeDate;
	}
	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}
	@Override
	public String toString() {
		return "YidouChange [id=" + id + ", openId=" + openId
				+ ", yidouChange=" + yidouChange + ", isCharge=" + isCharge
				+ ", chargeType=" + chargeType + ", orderNumber=" + orderNumber
				+ ", resuourceSpend=" + resuourceSpend + ", changeDate="
				+ changeDate + "]";
	}
	
	
}
