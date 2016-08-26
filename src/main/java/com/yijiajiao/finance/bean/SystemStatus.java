package com.yijiajiao.finance.bean;

public enum SystemStatus {

	OK(200, "success"),

	// 500- 服务器错误
	SERVER_ERROR(500, "服务器异常"),
	// 400- 请求错误
	BAD_REQUEST(400, "Bad Request"), 
	UNAUTHORIZED(401, "参数不匹配"), 
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"), 
	ID_NOT_FOUND(400001, "查询数据不存在"), 
	REMAINTIME_NOT_ENOUGH(400002, "答疑时长余额不足！"), 
	UNSUPPORTED_ENCODING(400003, "参数编码错误"), 
	PARAM_NOT_NULL(400004, "参数不能为空"),
	COMMODITYTYPE_NOT_NULL(400005,"商品类型有误！提示: 课程:1  答疑:2  密卷:3  诊断:4"),
	LOG_AREADY_BEEN(400006,"记录已存在，请勿重复添加！"),
	HAVE_NOT_ANSWERTIME(400007,"该用户无答疑时长，请购买时长包！"),
	REMAINMONEY_NOT_ENOUGH(400008,"账户可提现金额不足！"),
	VARIABLEMONEY_NOT_ENOUGH(400009,"账户可退款金额不足！"),
	BATCH_NUM(400010,"每个批次最多转款1000笔"),
	BATCH_DETAIL_ERROR(400011,"批量付款详情数据异常！"),
	USER_ALIPAY_HAS(400012,"该账户已绑定支付宝账号"),
	PARAM_NULL(400013,"有数据为空，请验证！"),
	PHONE_NOT_HAS(400014,"手机号码有误或不存在"),
	PHONE_NOT_NULL(400015,"手机号码不能为空"),
	DATE_NOT_NULL(400016,"日期不能为空"),
	REMAINYIDOU_NOT_ENOUGH(400017,"账户亿豆余额不足");
	SystemStatus(int status, String str) {
		setCode(status);
		setStr(str);
	}

	private int code;

	private String str;

	public int getCode() {
		return code;
	}

	public String getStr() {
		return str;
	}

	private void setCode(int code) {
		this.code = code;
	}

	private void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("code");
		sb.append(":");
		sb.append(code);
		sb.append(",");
		sb.append("message");
		sb.append(":");
		sb.append(str);
		return sb.toString();
	}

}
