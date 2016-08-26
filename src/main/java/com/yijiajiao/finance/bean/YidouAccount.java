package com.yijiajiao.finance.bean;

import java.math.BigDecimal;

public class YidouAccount {
	public int id;
	public String openId;
	public BigDecimal yidouCount;
	public String createTime;
	public String lastUpdateTime;
	public int isSpend;//消费还是充值  0 消费 1充值
	
	public int getIsSpend() {
		return isSpend;
	}
	public void setIsSpend(int isSpend) {
		this.isSpend = isSpend;
	}
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
	public BigDecimal getYidouCount() {
		return yidouCount;
	}
	public void setYidouCount(BigDecimal yidouCount) {
		this.yidouCount = yidouCount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	@Override
	public String toString() {
		return "YidouAccount [id=" + id + ", openId=" + openId
				+ ", yidouCount=" + yidouCount + ", createTime=" + createTime
				+ ", lastUpdateTime=" + lastUpdateTime + "]";
	}
	
	
}
