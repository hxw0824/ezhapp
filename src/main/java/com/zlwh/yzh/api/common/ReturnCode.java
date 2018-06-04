package com.zlwh.yzh.api.common;

public enum ReturnCode {
	// 接口调用成功
	SUCCESS(0,"成功"),
	UNSUCCESS(1,"操作失败"),
	// 系统的未知错误，Exception
	ERROR(-1,"系统错误"),
	// 以1开头为参数校验的错误
	PARAM_NOT_VALID(1000,"参数不正确"),
	TYPE_PARAM_NOT_VALID(1001,"请标明发送验证码类型"),
	SNKEY_PARAM_NOT_VALID(1002,"SignKey不合法"),
	DEVICE_PARAM_NOT_VALID(1003,"设备类型错误不合法"),
	PARAM_IS_ERROR(1004,"参数错误"),
	
	//2开头 账号操作相关
	ACCOUNT_IS_EXISTS(2000,"帐号已存在"),
	TOKEN_NOT_VALID(2001,"用户token不合法"),
	FILE_IS_NULL(2002,"请选择上传的图片或资源"),
	
	ORDER_IS_NOT_EXISTS(3000,"订单不存在"),	
	
	//7版本相关
	VERSION_NOT_FOUND(7000,"未检测到版本信息,请检查网络状况"),
	//8开头 课程操作相关
	COURCES_NOT_FOUND(8000,"未找到相关课程"),
	COURCES_IS_COLLECT(8001,"课程已收藏过"),
	COURCES_NOT_COLLECT(8002,"没有找到收藏的课程"),
	COURCESPARM_NOT_VALID(8003,"课程参数出错"),
	COURCES_MON_VAL_NOT_VALID(8004,"未找到对应月份课程"),
	// 以9开头的为业务方面的异常
	BUSINESS_EXCEPTION(9000, "业务异常"),
	PHONE_NOT_VALID(9001,"手机号码不合法"),
	SEND_VERIFYCODE_NOT_VALID(9002, "60秒内禁止重复发送验证码"),
	SEND_VERIFYCODE_ERROR(9003,"发送验证码失败"),
	VERIFYCODE_NOT_CORRECT(9004,"验证码不正确"),
	SIMPLE_PASSWORD(9005,"密码过于简单"),
	USERNAME_PASSWORD_NOT_CORRECT(9006,"用户名或密码不正确"),
	USER_EXIST(9007,"用户已存在"),
	USER_NOT_LOGIN(9008,"用户未登录"),
	USER_KICK_OUT(9009,"用户被踢出"),
	USER_NOT_FOUND(9010,"未找到用户"),
	USER_OTHER_LOGIN(9011,"帐号在另一台设备登录"),
	USER_LOGIN_OUT(9012,"帐号已经登出"),
	USER_LOGIN_FAILURE(9013,"登录失效，请重新登录"),
	USER_LOCK(9011,"用户已被冻结"),
	
	
	CARD_NOT_EXIST(9101,"授权卡不存在或已过期"),
	CARD_USED(9102,"授权卡使用次数超过2次"),
	USER_OWNED(9103,"用户已经使用过授权卡"),
	PERIOD_ERROR(9104,"学段信息错误"),
	CARD_PW_NOT_NULL(9105,"授权卡密码不匹配"),
	CARD_CLASS_NOT_EXIST(9106,"授权卡学段与用户所在学段不符，请修改个人信息中的学段"),
	CARD_BOND_ERROR(9107,"授权卡绑定失败"),
	CARD_BOND_SUCCES(9108,"授权卡绑定失败");
	
	private final int value;
	private final String reasonPhrase;

	private ReturnCode(Integer value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}
	

	/**
	 * Return the integer value of this status code.
	 */
	public Integer value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return reasonPhrase;
	}


	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(value);
	}

}
