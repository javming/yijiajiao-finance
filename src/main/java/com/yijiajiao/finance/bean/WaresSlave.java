package com.yijiajiao.finance.bean;

import java.io.Serializable;
import java.util.Date;

//该类用于接收
public class WaresSlave implements Serializable {
	private int id;
	private int waresId;
	private int slaveId;
	private String orderNumber;
	private String startTime;
	private String endTime;
	private String date121;
	private Integer schedule;
	private Integer teachMode;
	private String curriculumInfo;
	private String createTime;
	private String updateTime;
	private Integer seq;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWaresId() {
		return waresId;
	}
	public void setWaresId(Integer waresId) {
		this.waresId = waresId;
	}
	public Integer getSlaveId() {
		return slaveId;
	}
	public void setSlaveId(Integer slaveId) {
		this.slaveId = slaveId;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	public String getDate121() {
		return date121;
	}
	public void setDate121(String date121) {
		this.date121 = date121;
	}
	public Integer getSchedule() {
		return schedule;
	}
	public void setSchedule(Integer schedule) {
		this.schedule = schedule;
	}
	public Integer getTeachMode() {
		return teachMode;
	}
	public void setTeachMode(Integer teachMode) {
		this.teachMode = teachMode;
	}
	public String getCurriculumInfo() {
		return curriculumInfo;
	}
	public void setCurriculumInfo(String curriculumInfo) {
		this.curriculumInfo = curriculumInfo;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	@Override
	public String toString() {
		return "WaresSlave [id=" + id + ", waresId=" + waresId + ", slaveId="
				+ slaveId + ", orderNumber=" + orderNumber + ", startTime="
				+ startTime + ", endTime=" + endTime + ", date121=" + date121
				+ ", schedule=" + schedule + ", teachMode=" + teachMode
				+ ", curriculumInfo=" + curriculumInfo + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", seq=" + seq
				+ "]";
	}



}
