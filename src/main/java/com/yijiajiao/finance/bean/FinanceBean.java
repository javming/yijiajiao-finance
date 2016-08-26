package com.yijiajiao.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FinanceBean implements Serializable {
	private String openId;
	private String teacherPhoneNum;//教师手机号
	private String studentPhoneNum;//学生手机号
	private String tradeTime;// 交易时间（包括购买时间，学生答疑消费时间，教师转出金额时间）
	private String orderNumber;// 订单号
	private String trade_no;//支付宝交易号（结算流水号）
	private String curriculumName;// 课程名称
	private String curriculumType;//课程类型
	private BigDecimal totalPrice;// 金额
	private String tradeType;// 交易类型 支付宝 微信 网银（结算方式）
	private BigDecimal teacherIncome = BigDecimal.valueOf(0.00);// 教师收入（或者退款）
	private BigDecimal systemIncome = BigDecimal.valueOf(0.00);// 平台收入（或者退款）
	private int financeLogsType;// 财务记录类型(1订单 2退款  3消费答疑)
	private int curriculumCount;//课程节数
	private String teacherName;//教师姓名
	private String inviteSelfcode;//工作室邀请码
	private BigDecimal singlePrice;//每节课时价格
	
	private BigDecimal totalIncome;//收入总和
	private BigDecimal totalRefund;//退款总和
	private BigDecimal totalTeacherIncome; //教师收入总和
	private BigDecimal totalSytemIncome;//平台收入总和
	
	
	public BigDecimal getSinglePrice() {
		return singlePrice;
	}
	public void setSinglePrice(BigDecimal singlePrice) {
		this.singlePrice = singlePrice;
	}
	public int getCurriculumCount() {
		return curriculumCount;
	}
	public void setCurriculumCount(int curriculumCount) {
		this.curriculumCount = curriculumCount;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getInviteSelfcode() {
		return inviteSelfcode;
	}
	public void setInviteSelfcode(String inviteSelfcode) {
		this.inviteSelfcode = inviteSelfcode;
	}
	public String getCurriculumType() {
		return curriculumType;
	}
	public void setCurriculumType(String curriculumType) {
		this.curriculumType = curriculumType;
	}
	public BigDecimal getTotalTeacherIncome() {
		return totalTeacherIncome;
	}
	public void setTotalTeacherIncome(BigDecimal totalTeacherIncome) {
		this.totalTeacherIncome = totalTeacherIncome;
	}
	public BigDecimal getTotalSytemIncome() {
		return totalSytemIncome;
	}
	public void setTotalSytemIncome(BigDecimal totalSytemIncome) {
		this.totalSytemIncome = totalSytemIncome;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getTeacherPhoneNum() {
		return teacherPhoneNum;
	}
	public void setTeacherPhoneNum(String teacherPhoneNum) {
		this.teacherPhoneNum = teacherPhoneNum;
	}
	public String getStudentPhoneNum() {
		return studentPhoneNum;
	}
	public void setStudentPhoneNum(String studentPhoneNum) {
		this.studentPhoneNum = studentPhoneNum;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCurriculumName() {
		return curriculumName;
	}
	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public BigDecimal getTeacherIncome() {
		return teacherIncome;
	}
	public void setTeacherIncome(BigDecimal teacherIncome) {
		this.teacherIncome = teacherIncome;
	}
	public BigDecimal getSystemIncome() {
		return systemIncome;
	}
	public void setSystemIncome(BigDecimal systemIncome) {
		this.systemIncome = systemIncome;
	}

	public int getFinanceLogsType() {
		return financeLogsType;
	}
	public void setFinanceLogsType(int financeLogsType) {
		this.financeLogsType = financeLogsType;
	}
	
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	public BigDecimal getTotalRefund() {
		return totalRefund;
	}
	public void setTotalRefund(BigDecimal totalRefund) {
		this.totalRefund = totalRefund;
	}
	@Override
	public String toString() {
		return "FinanceBean [openId=" + openId + ", teacherPhoneNum="
				+ teacherPhoneNum + ", studentPhoneNum=" + studentPhoneNum
				+ ", tradeTime=" + tradeTime + ", orderNumber=" + orderNumber
				+ ", trade_no=" + trade_no + ", curriculumName="
				+ curriculumName + ", curriculumType=" + curriculumType
				+ ", totalPrice=" + totalPrice + ", tradeType=" + tradeType
				+ ", teacherIncome=" + teacherIncome + ", systemIncome="
				+ systemIncome + ", financeLogsType=" + financeLogsType
				+ ", totalIncome=" + totalIncome + ", totalRefund="
				+ totalRefund + ", totalTeacherIncome=" + totalTeacherIncome
				+ ", totalSytemIncome=" + totalSytemIncome + "]";
	}
	

	
}
