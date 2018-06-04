package com.zlwh.yzh.api.common;

import com.zlwh.yzh.api.domain.base.Result;
/**
 * 短信工具类
 * @author hcq
 *@date 2018年1月6日
 * @version 1.0
 */
public class SMSUtil {
	private Integer code;
	 private String msg;

    public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public SMSUtil(ClienEnum clienEnum) {
        this.code = clienEnum.getCode();
        this.msg = clienEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
   
}