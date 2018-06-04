package com.zlwh.yzh.api.common;

import com.thinkgem.jeesite.common.config.Global;


public class EncryptUncrypt {

	    /**
	     * 
	    *<pre>
	    *<b>说明:</b> 异或加密
	    *<b>日期:</b> 2016年6月13日 下午4:34:19
	     */
		public static byte[] encrypt(byte[] data) {
			String strKey  = Global.getConfig("securitKey");
			byte[] keyData = strKey.getBytes();
			int keyIndex = 0;
			int length = data.length;
			for (int x = 0; x < length; x++) {
				data[x] = (byte) (data[x] ^ keyData[keyIndex]);
				if (++keyIndex == keyData.length) {
					keyIndex = 0;
				}
			}
			return data;
		}
}
