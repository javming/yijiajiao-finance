package com.yijiajiao.finance.bean.query;



public class FinanceLogQuery{
	//查询2011的数据：select * from  表 where year(date)='2011';
	//	查找月份为12的数据：select * from 表 where month(date)='12';
	//查找天数为本年第二天的数据：select * from 表 where dayofyear(date)='2';
	private int month;//标识要查询几月份的数据
	private int begin;
	private int size;
	private String startTime;//查询的开始时间 
	private String endTime;//查询的截止时间
	private String phoneNum;//手机号
	private String teacherName; //教师姓名
	private String openId;
	
	public void setOpenId(String openId){
		this.openId = openId;
	}
	public String getOpenId(){
		return this.openId;
	}
	private int ray=0;//0表示时间降序，1表示时间升序
	
	private String _;
	
	
	
	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String get_() {
		return _;
	}

	public void set_(String _) {
		this._ = _;
	}

	public int getRay() {
		return ray;
	}

	public void setRay(int ray) {
		this.ray = ray;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return " startTime=" + startTime + ", endTime="
				+ endTime + ", phoneNum=" + phoneNum + ", teacherName="
				+ teacherName + ", openId=" + openId + ", ray=" + ray;
	}
	
	
}
