package com.yijiajiao.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class DailySettleAccounts implements Serializable {
	private int id;
	private String openId;//学生openId
	private String sellOpenId;//教师openId
	private String orderNumber;//订单号
	private String commodityId;//商品号
	private Integer curriculumType;// 课程类型(0直播课(小班课) 1一对一课 2 视频课)
	private String curriculumName;// 课程名称
	private String curriculumInfo;//课程内容
	private Integer slaveId;
	private BigDecimal settleMoney;//结算金额
	private BigDecimal orderPrice;//订单金额
	private String startTime;//上课开始时间
	private String endTime;//结束时间
	private String saveTime;
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
	public String getSellOpenId() {
		return sellOpenId;
	}
	public void setSellOpenId(String sellOpenId) {
		this.sellOpenId = sellOpenId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public Integer getCurriculumType() {
		return curriculumType;
	}
	public void setCurriculumType(Integer curriculumType) {
		this.curriculumType = curriculumType;
	}
	public String getCurriculumName() {
		return curriculumName;
	}
	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}
	public String getCurriculumInfo() {
		return curriculumInfo;
	}
	public void setCurriculumInfo(String curriculumInfo) {
		this.curriculumInfo = curriculumInfo;
	}
	public Integer getSlaveId() {
		return slaveId;
	}
	public void setSlaveId(Integer slaveId) {
		this.slaveId = slaveId;
	}
	public BigDecimal getSettleMoney() {
		return settleMoney;
	}
	public void setSettleMoney(BigDecimal settleMoney) {
		this.settleMoney = settleMoney;
	}
	
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSaveTime() {
		return saveTime;
	}
	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}
	@Override
	public String toString() {
		return "DailySettleAccounts [id=" + id + ", openId=" + openId
				+ ", sellOpenId=" + sellOpenId + ", orderNumber=" + orderNumber
				+ ", commodityId=" + commodityId + ", curriculumType="
				+ curriculumType + ", curriculumName=" + curriculumName
				+ ", curriculumInfo=" + curriculumInfo + ", slaveId=" + slaveId
				+ ", settleMoney=" + settleMoney + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", saveTime=" + saveTime + "]";
	}
	
	
	
}
