package com.yijiajiao.finance.bean;


import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description 学生答疑计费器
 */
public class AnswerTimer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8385190997100598540L;
	private int id;
	private String openId;
	private String teacherId;
	private String gradeCode;//年级code
	private BigDecimal remainTime = BigDecimal.valueOf(0.00);// 答疑所剩时长
	private String dueTime;//失效日期
	private String updateTime;
	private String createTime;
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
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public BigDecimal getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(BigDecimal remainTime) {
		this.remainTime = remainTime;
	}

	public String getDueTime() {
		return dueTime;
	}
	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "AnswerTimer [id=" + id + ", openId=" + openId + ", teacherId="
				+ teacherId + ", gradeCode=" + gradeCode + ", remainTime="
				+ remainTime + ", dueTime=" + dueTime + ", updateTime="
				+ updateTime + ", createTime=" + createTime + "]";
	}
	

}
