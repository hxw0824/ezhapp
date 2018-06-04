package com.zlwh.yzh.api.common;

public enum LogType {
	// 接口调用成功
	REGISTER(0,"注册"),
	LOGIN(1,"登录"),
	LOGOUT(2,"退出"),
	RESETPWD(3,"重置密码"),
	REBINDMOBILE(4,"更换手机号"),
	UPLOADWORK(5,"上传作品"),
	TELSMS(6,"发送验证码");
	
	private final int value;

	private final String remark;


	private LogType(int value, String remark) {
		this.value = value;
		this.remark = remark;
	}

	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getRemark() {
		return remark;
	}


	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return Integer.toString(value);
	}

}
