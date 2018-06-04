package com.zlwh.yzh.admin.modules.basis.entity;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thinkgem.jeesite.common.utils.DateUtils;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	//合作身份者ID，以2088开头由16位纯数字组成的字符串
	//沙箱
//	 public static String partner = "2088102175375627";
	 //线上
	 public static String partner = "2088221665498322";
	
 // 收款支付宝账号，一般情况下收款账号就是签约账号
	//沙箱
//	    public static String seller_id = "2088102175375627";
	//线上
  public static String seller_id = "2088221665498322";
    
	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//	public static String app_id = "2016091300498208";//沙箱测试
	public static String app_id = "2018011701932704";//线上
//								   2018011701932704
	// 商户私钥，您的PKCS8格式RSA2私钥
	//沙箱测试
//    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZv+Txs/pfGbAw/7S0sj0n++zGkfdF+wJZ3uf2fTbkII6Me/m4q7LPwLhet+frzDupVInZ78IQP/rJx/mCn6WxM60xrjOv3WUCtErN17fz9MmRKPkhVYhfP9xglOe4+MLgmt1wYCh4HmOxgxk0IucAJkNSrGo11u6pk+MaoP7ZKkcowAyHw14u20tJ9/ZUSCVbXnIBsKEkxai2WrwCiIILMOHlKr4tN4BUD/UKVJBw1SUaS/lEBgzox3PNm4LU3CFqoNxrLCofIVJDm4PimozPQKY3xv9gC6yJMKI6rW4osEW2IVFiJDZx//JqWFa7E8UA5UBmVGPx7IVesOdBRqb1AgMBAAECggEAIoAO7bXHLuPix3v5nrqIQpqjTQOgCxICOwxr3MHIOXScyCVhqfDNVRUXyhlWb3EKyjpwHHlSgLb6naDD+m28joDcElPWwVXavrqaNop7esCpImuhS7uXuFfqKboALV+UyzW1Duxk6F9ZW5Hha/ORBlWPsdOCmcY7E85dnbIWvcAH0v6KFEObY2uR56uot/HLkLijM4FXCbZqdbdLz+41jO5NWJnoq5gZIO1/sS2h78M003HE+VJLGGljdPmll+k/sm7QEhK3b8wDdez+7k8aFcxKzP04AJHZRoUVH0cJRJEm233BUtU+8HgyArsiYt9cMJtRIpbPyF2Wl7wznKTJwQKBgQDNxjCI0P8S1bStyFaOd+vN5EpEEG1muA5ddINHvZeOd7Sxl/lGEF1DMsql6W/EI9v0S2AJ6GQCKZUtSKmxCAl4ZkQI4yvPsqSLrABfY4GhvQZ4AFYC9m4v1qt/12nDh7qvCsvdp7IWqEBQwtBiwwCF7Vvs4T8U+TDYn8YHoGy56QKBgQC/RvIGOV2yzX0BO1hbBUuIwVZUp7AElMxjqSTkfmvt/FpF+dt8rqW5XpH/Ua6szZASXjZ32fUJED0s0ajEKPcnZ1W3EkaVxpC0CVdOfw/b+va7aCXw24AAOYd5JGtP5+gOJUhY4Alv8FblvONTJ7Ue+g9WIfa72s1y7bsVOtWRLQKBgDMoM4bAFrnzAUE3DjXLIIeGWuRDBT/FQGUBg2XBf70+i2CffKxrgxQiu3i16UBk/djlKFIuQTa8acbhSralXd1d50oiWBEBw6CpuUn9rgust7/7rWZ9J3WjM+3LexLOWp4L3BUWDNNPLvYlCLOU8TtagEEK2qj3LqqcRho9SuqJAoGAQ4xiXCBWHpbC1nSGt14fVqQwQKUXXAbQR0GsbaTXqKu9lYivIlDq5ZOoK2pFLXFKt7HdumWWSyUY9YL0/pEY9yle88/YbXw6DdpeorHsf9sY1wXooC5j/bBTT0y1OPrE0HoKGpjDvGw7w9X40ZzsMYxNbjWAVMboVvXtY4nrceECgYA1U39oZhG/FlADtsZkWVomwTE0KQPmV3momUvlltfwBsB/2Bhup3Ksz3n53WOpRso4fU5ace8vD2kNhh2MuunAkkMMWzRT0HpJYUEUcbncJPLJORVk0xY9DNWZPIxbF4f3RHexL0IOHhsmYejYIqhW7UjKqdIiTQ9tqWbIJ2q07Q==";
//	线上
	public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCTOKn+p4bXROubdXhrajruZGxqfu81i3chAsaFbMYUpw/RWV2PvIgS8YBpKuXjtHMTXMT34vppj4rq7rI6R49TDicAMmi/Ja7Qb/G4UZoNCNMIDT+zJmGsdTpfh9xEC24O/KZ9sW7ae8phM66LcbXbTOSaedpYOaGndEHYKwTUHMtDTN3XnfFMS16sEnchtGu/gpl2uzfvxXewrZl9SHQpe+IWQZ27dFSeTJs40AiIWJoD8UVtt2lj6avnZr3WMpmH/GoBmRyYCJ/toyi65teYIdQ+ONFAgXICLRhTliJ/kDZYGgnaH9SZ9ikNpBiHDWKP50vHdY665EynkvjgO0IvAgMBAAECggEASePtxmdU0STBUARUjS0DKPV0SLsu2mQGRaY792mRvXngAYXl5KeOcFuPxLkIKxI9k6FDJttgLa0i3ykwgPm7oMcAoiOq2Q1TwT/+RTGHrTUZnffwWb1Q1pr70kjBL9HAynraIUn1xmjNOZ4T498NlGTyc+nmwdMjJIXAKnvk74RFRcXvycbrW1IMUJIoO+cwatNxZ2lv0rIUt4NNrcGHrRAr52KUFb9W1x1cNPm6QzJRctA6pv58HZDUzN+s13y7kdTbqPYO6Qy8+u+K/p6LQYJpNNkQMm82ec4o677ArxpI2kozwOR++rhcINmFwBkH/vlFgXlHSBOZAnac5TbboQKBgQDFTbAQLzOv7gPpwUIy+FevaFaC6YZeTdpul++Abm/RwHK2dsRCdp6nJN2c/7gqku+0VujXfll2+1dDAuyrW7A7gqWGPZUkTXhB4fKeT3mi5dfUN4c/AjUMMj37vNdFD2lXc7cFXUXAEzZoBK/zx+JGfWdWbRFQkwvn0lUp4ZLI8QKBgQC/BM2M7jKxXG5zkOUiLG7j+HDg6eCrXtS+CtmfyyZTrL1buWcWE8DCucXC2Ghq0gBQhG65G4KQbicQ/OlCd1JU0TLz4KSTtJOK4o2qnakSe5gUWFVOQbJChF2jM3p1Yg6yloD3J67BF8tt76+EkFtC3JwM9djcwj/ZoQ+Fesu9HwKBgQC8CK/0nKeL0TBVhGMMpHUcrDlWhiwSj+vOXmpYYeebZOduUX31QYsuDApQZ70iheqAWhuJ09Z7eEnPiWf4X2dzL62EZAhREqu7ThSlH2TzBE1O6KSyPe5Ec1VzJqOaI3fnalZQfHXO+gBt3Zm6oRJaz4S+by+DNMCx7FhRk7MKYQKBgBxiEFDii/HCtD7Dxug/BfMl1U/2MIYGP0JFxnRx8S35uuFIOUMUiE3m4LXFBi7pKVbNds5Wgh4H4BIsIlTX+gnUU+fc91djO5ElfLtfVeIDL2PGhFZueJhdprQ7T/TlLx3mMx+Ga/cRHA8n0EK4WHck5KMgXKrgyjACOJYy2tctAoGAFz8YcBsnVRWt5WC3+72bty38+adeK5Lv8ZgRFhIBrzC8/9ug4Bk1jVKx27mnHIGsXzeWjIlKHztqAPrtzx0szWvU0Wim3nR5pdEbUNcqvwfNNNmdzEMZYAuLUsM3I39N66i9lLlVprPNr4kigxdspwrVbxC4Jeeh8VyhnMc754E=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	//沙箱测试
//    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1yE5LBPrhw4zhMsL+nel9AmW7VsoTWrdHdTw8b8eVu4ZSeXMs6jBwUQ1jTObSQZMxkb5R2Uc5AvKMwIKxLmcjdqfaLmYWol1Nxk/B5fz4dY4u/qLuPamyanlN23satspwdDNPVckDAnSZ0ckceIly3qg6WpovpndF8msqLVcAsrWCwlCYYUQfdT39N8+MU359BFU98EW0ZRxXsVPCWFlPex318mGQYIfZyX3Yki8PB4LpG9KsE/j9SeFjBh2qFI7/p31Nt++feBxuoSc6vhDpzwS25X4F0hMF0kQwSZdKMGEqxkvN99GJrR9D1jeqjDfkwzkbKxuKOKmYAh959oSmQIDAQAB";
	//线上
	public static String alipay_public_key ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3grDF3k1q9aDXeu4ncmLHdgLfBHfXRreAClTToVzIfPS78I7fEcncvkdZR13+hSpnkznQVYpl9hIu+LmWA1qLeAqLNxqKgs6GGHv1SBRqjoFF0FOROv6en3heGd32tCvEk4KxT4kWJOx1PrEA1EQyDvAAnAW+yohnFniDVVcnE7qG1KdZKqpOxPPVU/1hV3nilcNU15cCUNX30POExVP5Qjee5Vz6WB8M9Nl8g+WYznD/rImiZAapjbKbQn2ufkIEI99z85dFxcLWHY6r9Ncqmsr8ZOilCvWdgQc3PRL4e1Gvya+pg6e5m6v6kFQcGeVIWvm82B2EjQ8GYHdYD8FkQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String notify_url = "http://192.168.9.57:8080/yah_fa/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
//	public static String notify_url = "http://192.168.9.57:8080/yzh_fa/v1.0/alipay/notify";
//	public static String notify_url = "http://1.119.55.44:8080/yzh_fa/v1.0/alipay/notify";
	public static String notify_url = "http://jyhd.ezhihe.cn/v1.0/alipay/notify";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//	public static String return_url = "http://192.168.9.57:8080/yah_fa/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
//	public static String return_url = "http://192.168.9.57:8080/yzh_fa/v1.0/alipay/sucsess";
//	public static String return_url = "http://1.119.55.44:8080/yzh_fa/v1.0/alipay/sucsess";
//	public static String return_url = "http://openapi.ezhihe.cn/v1.0/alipay/sucsess";
//	public static String return_url = "http://192.168.9.243:9090/return_url.jsp";
	public static String return_url = "http://f.ezhihe.cn/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "UTF-8";
	
	// 线上支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	//沙箱
//	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "d:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
	public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
    public static void main(String[] args) throws ParseException {
		System.out.println(transferLongToDate("yyyy-MM-dd HH:mm:ss",22395031670215514l));
		System.out.println(DateUtils.formatDateTime(DateUtils.addDate(new Date(),30)));
	}
}

