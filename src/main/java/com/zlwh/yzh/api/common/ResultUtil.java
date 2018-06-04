package com.zlwh.yzh.api.common;

import com.zlwh.yzh.api.domain.base.Result;

/**
 * 接口返回统一
 * @author hcq
 *@date 2018年1月6日
 * @version 1.0
 */
public class ResultUtil {

	private  int value;
	private  String reasonPhrase;
	
	
	public ResultUtil(ReturnCode returnCode) {
		super();
		this.value = returnCode.value();
		this.reasonPhrase=returnCode.getReasonPhrase();
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getReasonPhrase() {
		return reasonPhrase;
	}
	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}
	
	 public static Result success(Object object) {
	        Result result = new Result(ReturnCode.SUCCESS,object);
	        return result;
	    }

	    public static Result success() {
	        return success("操作成功");
	    }

	    public static Result error(Object object) {
	    	Result result = new Result(ReturnCode.ERROR,object);
	    	return result;
	    }
	    public static Result error(ReturnCode returnCode,Object object) {
	    	Result result = new Result(returnCode,object);
	    	return result;
	    }
//	    public static Result error(ReturnCode returnCode) {
//	    	Result result = new Result(returnCode);
//	        return result;
//	    }
	    public static Result error(ReturnCode returnCode) {
	    	return error(returnCode,returnCode.getReasonPhrase());
	    }
	    
	    public static Result warn(ReturnCode returnCode,String reason) {
	    	Result result = new Result(returnCode,reason);
	    	return result;
	    }
	
}

