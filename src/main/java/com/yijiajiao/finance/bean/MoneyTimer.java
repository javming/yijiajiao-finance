package com.yijiajiao.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @decription 教师账户余额
 */
public class MoneyTimer implements Serializable {
	private int id;
	private String openId;
	private BigDecimal variableMoney;//浮动金额，可能退款
	private BigDecimal withdrawalCash;//本期可提现金额
	private BigDecimal totalMoney;//总金额
	private BigDecimal totalSettleMoney;//收入总金额(包括已提现的和本期可提现的金额)
	private String updateTime;
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
	public BigDecimal getVariableMoney() {
		return variableMoney;
	}
	public void setVariableMoney(BigDecimal variableMoney) {
		this.variableMoney = variableMoney;
	}
	public BigDecimal getWithdrawalCash() {
		return withdrawalCash;
	}
	public void setWithdrawalCash(BigDecimal withdrawalCash) {
		this.withdrawalCash = withdrawalCash;
	}
	public BigDecimal getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public BigDecimal getTotalSettleMoney() {
		return totalSettleMoney;
	}
	public void setTotalSettleMoney(BigDecimal totalSettleMoney) {
		this.totalSettleMoney = totalSettleMoney;
	}
	@Override
	public String toString() {
		return "MoneyTimer [id=" + id + ", openId=" + openId
				+ ", variableMoney=" + variableMoney + ", withdrawalCash="
				+ withdrawalCash + ", totalMoney=" + totalMoney
				+ ", totalSettleMoney=" + totalSettleMoney + ", updateTime="
				+ updateTime + "]";
	}
	
}
