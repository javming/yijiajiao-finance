package com.yijiajiao.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinanceLog implements Serializable {
	private Integer id;
	private String openId;
	private String sellOpenId;// 被购买的用户主键(教师提现时的openId也存于此字段)
	private String teacherPhoneNum;//教师手机号
	private String studentPhoneNum;//学生手机号
	private String tradeTime;// 交易时间（包括购买时间，学生答疑消费时间，教师转出金额时间）
	private String orderNumber;// 订单号
	private String trade_no;//支付宝交易号
	private String commodityId;// 商品号
	private Integer commodityType;// 商品类型(1课程 2答疑 3密卷 4诊断)
	private String curriculumName;// 商品名称
	private Integer curriculumType;// 课程类型(1一对一直播课  3小班直播课 4大班直播课 2视频课)
	private String curriculumInfo;//课程描述
	private Integer teachMode;// 表示一对一教学方式(0线上 1线下)
	private BigDecimal totalPrice;// 金额
	private Integer tradeType;// 交易类型 支付宝 微信 网银
	private String accountNumber;// 交易账户 包括支付、退款和提现
	private String batchNo;
	private BigDecimal duration = BigDecimal.valueOf(0.00);// 学生购买或者消费的答疑时长
	private String solutionId;//消耗答疑记录的id
	private BigDecimal teacherIncome =BigDecimal.valueOf(0.00);// 教师收入
	private BigDecimal systemIncome = BigDecimal.valueOf(0.00);// 平台收入
	private BigDecimal variableMoneyChange =BigDecimal.valueOf(0.00);// 可变的金额变化值(主要涉及到退款的部分)
	private BigDecimal withdrawalCashChange =BigDecimal.valueOf(0.00);// 可提现的金额变化值
	private BigDecimal teacherOutput =BigDecimal.valueOf(0.00);// 老师转出的金额
	private int financeLogsType;// 财务记录类型(1订单 2退款  3消费答疑 4账户提现(每月15日发上个月工资))
	private Integer openIdIsdel = 0;// 消费者（主要学生）删除 1表示删除 0表示显示
	private Integer sellOpenIdIsdel = 0;// 销售者（主要是教师）是否删除 0否 1删除
	private List<WaresSlave> waresSlaves = new ArrayList<WaresSlave>();//主要用于接收0退款时每小节课的信息
	private int curriculumCount;//课程节数
	private String gradeCode;
	private int validDay;//时长包有效期（天）

	public int getValidDay() {
		return validDay;
	}

	public void setValidDay(int validDay) {
		this.validDay = validDay;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public int getCurriculumCount() {
		return curriculumCount;
	}

	public void setCurriculumCount(int curriculumCount) {
		this.curriculumCount = curriculumCount;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
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

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(Integer commodityType) {
		this.commodityType = commodityType;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public Integer getCurriculumType() {
		return curriculumType;
	}

	public void setCurriculumType(Integer curriculumType) {
		this.curriculumType = curriculumType;
	}

	public String getCurriculumInfo() {
		return curriculumInfo;
	}

	public void setCurriculumInfo(String curriculumInfo) {
		this.curriculumInfo = curriculumInfo;
	}

	public Integer getTeachMode() {
		return teachMode;
	}

	public void setTeachMode(Integer teachMode) {
		this.teachMode = teachMode;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		if (totalPrice == null || "".equals(totalPrice))
			totalPrice = BigDecimal.valueOf(0.00);
		this.totalPrice = totalPrice;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		if (duration == null || "".equals(duration))
			duration =BigDecimal.valueOf(0.00);
		this.duration = duration;
	}

	public String getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(String solutionId) {
		this.solutionId = solutionId;
	}

	public BigDecimal getTeacherIncome() {
		return teacherIncome;
	}

	public void setTeacherIncome(BigDecimal teacherIncome) {
		if (teacherIncome == null || "".equals(teacherIncome))
			teacherIncome =BigDecimal.valueOf(0.00);
		this.teacherIncome = teacherIncome;
	}

	public BigDecimal getSystemIncome() {
		return systemIncome;
	}

	public void setSystemIncome(BigDecimal systemIncome) {
		if (systemIncome == null || "".equals(systemIncome))
			systemIncome = BigDecimal.valueOf(0.00);
		this.systemIncome = systemIncome;
	}

	public BigDecimal getVariableMoneyChange() {
		return variableMoneyChange;
	}

	public void setVariableMoneyChange(BigDecimal variableMoneyChange) {
		if (variableMoneyChange == null || "".equals(variableMoneyChange))
			variableMoneyChange = BigDecimal.valueOf(0.00);
		this.variableMoneyChange = variableMoneyChange;
	}

	public BigDecimal getWithdrawalCashChange() {
		return withdrawalCashChange;
	}

	public void setWithdrawalCashChange(BigDecimal withdrawalCashChange) {
		if (withdrawalCashChange == null || "".equals(withdrawalCashChange))
			withdrawalCashChange =BigDecimal.valueOf(0.00);
		this.withdrawalCashChange = withdrawalCashChange;
	}

	public BigDecimal getTeacherOutput() {
		return teacherOutput;
	}

	public void setTeacherOutput(BigDecimal teacherOutput) {
		if (teacherOutput == null || "".equals(teacherOutput))
			teacherOutput =BigDecimal.valueOf(0.00);
		this.teacherOutput = teacherOutput;
	}

	public int getFinanceLogsType() {
		return financeLogsType;
	}

	public void setFinanceLogsType(int financeLogsType) {
		this.financeLogsType = financeLogsType;
	}

	public Integer getOpenIdIsdel() {
		return openIdIsdel;
	}

	public void setOpenIdIsdel(Integer openIdIsdel) {
		if (openIdIsdel == null)
			openIdIsdel = 0;
		this.openIdIsdel = openIdIsdel;
	}

	public Integer getSellOpenIdIsdel() {
		return sellOpenIdIsdel;
	}

	public void setSellOpenIdIsdel(Integer sellOpenIdIsdel) {
		if (sellOpenIdIsdel == null)
			sellOpenIdIsdel = 0;
		this.sellOpenIdIsdel = sellOpenIdIsdel;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public List<WaresSlave> getWaresSlaves() {
		return waresSlaves;
	}

	public void setWaresSlaves(List<WaresSlave> waresSlaves) {
		this.waresSlaves = waresSlaves;
	}

	@Override
	public String toString() {
		return "FinanceLog [id=" + id + ", openId=" + openId + ", sellOpenId="
				+ sellOpenId + ", teacherPhoneNum=" + teacherPhoneNum
				+ ", studentPhoneNum=" + studentPhoneNum + ", tradeTime="
				+ tradeTime + ", orderNumber=" + orderNumber + ", trade_no="
				+ trade_no + ", commodityId=" + commodityId
				+ ", commodityType=" + commodityType + ", curriculumName="
				+ curriculumName + ", curriculumType=" + curriculumType
				+ ", curriculumInfo=" + curriculumInfo + ", teachMode="
				+ teachMode + ", totalPrice=" + totalPrice + ", tradeType="
				+ tradeType + ", accountNumber=" + accountNumber + ", batchNo="
				+ batchNo + ", duration=" + duration + ", solutionId="
				+ solutionId + ", teacherIncome=" + teacherIncome
				+ ", systemIncome=" + systemIncome + ", variableMoneyChange="
				+ variableMoneyChange + ", withdrawalCashChange="
				+ withdrawalCashChange + ", teacherOutput=" + teacherOutput
				+ ", financeLogsType=" + financeLogsType + ", openIdIsdel="
				+ openIdIsdel + ", sellOpenIdIsdel=" + sellOpenIdIsdel
				+ ", waresSlaves=" + waresSlaves + "curriculumCount="+curriculumCount+"]";
	}

}
