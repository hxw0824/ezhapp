package com.zlwh.yzh.api.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @description HttpRequestTool.java
 * @author zhaoxin
 * @date 创建时间：2015年11月19日 上午11:53:26
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class HttpRequestTool {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(HttpRequestTool.class);
		/**
		 * 向指定URL发送GET方法的请求
		 * 
		 * @param url
		 *            发送请求的URL
		 * @param param
		 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
		 * @return URL 所代表远程资源的响应结果
		 */
		public static String sendGet(String url, String param) {
			String result = "";
			BufferedReader in = null;
			try {
				String urlNameString = url + "?" + param;
				URL realUrl = new URL(urlNameString);
				// 打开和URL之间的连接
				URLConnection connection = realUrl.openConnection();
				// 设置通用的请求属性
				connection.setRequestProperty("accept", "*/*");
				connection.setRequestProperty("connection", "Keep-Alive");
				/*connection
						.setRequestProperty("user-agent",
								"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");*/
				// 建立实际的连接
				connection.connect();
				// 获取所有响应头字段
				Map<String, List<String>> map = connection.getHeaderFields();
				// 遍历所有的响应头字段
				for (String key : map.keySet()) {
					logger.info(key + "--->" + map.get(key));
				}
				// 定义 BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				System.out.println("发送GET请求出现异常！" + e);
				e.printStackTrace();
			}
			// 使用finally块来关闭输入流
			finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 向指定 URL 发送POST方法的请求
		 * 
		 * @param url
		 *            发送请求的 URL
		 * @param param
		 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
		 * @return 所代表远程资源的响应结果
		 */
		public static String sendPost(String url, String param) {
			PrintWriter out = null;
			BufferedReader in = null;
			String result = "";
			try {
				URL realUrl = new URL(url);
				// 打开和URL之间的连接
				URLConnection conn = realUrl.openConnection();
				// 设置通用的请求属性
				conn.setRequestProperty("accept", "*/*");
				conn.setRequestProperty("connection", "Keep-Alive");
				conn.setRequestProperty("user-agent",
						"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				// 发送POST请求必须设置如下两行
				conn.setDoOutput(true);
				conn.setDoInput(true);
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(param);
				// flush输出流的缓冲
				out.flush();
				// 定义BufferedReader输入流来读取URL的响应
				in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			} catch (Exception e) {
				System.out.println("发送 POST 请求出现异常！" + e);
				e.printStackTrace();
			}
			// 使用finally块来关闭输出流、输入流
			finally {
				try {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			return result;
		}
		public static String sendSSLPost(String url) throws ClientProtocolException, IOException {
			  String line = null;
			  StringBuilder stringBuilder = new StringBuilder();
			 CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
	            HttpGet getdd=new HttpGet();
	            HttpPost httpPost = new HttpPost(url);
	                httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");

	               // httpPost.setEntity(new StringEntity(JSON.toJSONString(param), "UTF-8"));

	                HttpResponse response = httpClient.execute(httpPost);
	            if (response.getStatusLine().getStatusCode() == 200) {

                  org.apache.http.HttpEntity httpEntity = response.getEntity();

                  if (httpEntity != null) {
                      try {
                          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                          while ((line = bufferedReader.readLine()) != null) {
                              stringBuilder.append(line);
                          }
                         
                      }catch(Exception e){
                      	
                      }
                  }
	            }
				return stringBuilder.toString();
	            
		}
}
