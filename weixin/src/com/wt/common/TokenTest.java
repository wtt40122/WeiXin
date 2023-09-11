package com.wt.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;


/**
 * @Description:
 * @author wt
 * @date 2018年5月30日
 */
public class TokenTest {
	//微信号Id
	private static final String appID = "wxfcd15d9848c4968b";
	//微信号密钥
	private static final String appSecret = "c1276c49c9fde15a58a5e7063f3a7f0b";
	public static void main(String[] args) throws Exception {
		//微信获取token接口地址
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+appID+"&secret="+appSecret;
		//建立连接
		URL url = new URL(tokenUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		//使用自定义的信任管理器
		TrustManager[] tm = {	new MyX509TrustManager()	};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new SecureRandom());
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		conn.setSSLSocketFactory(ssf);
		conn.setDoInput(true);
		//设置请求方式
		conn.setRequestMethod("GET");
		//取得输入流
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		//读取响应内容
		StringBuffer buffer = new StringBuffer();
		String str = null;
		while((str =bufferedReader.readLine()) != null){
			buffer.append(str);
		}
		//关闭流
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		conn.getClass();
		System.out.println(buffer);

		
		JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
		String accessToken = jsonObject.getString("access_token");
		int expireIn = jsonObject.getInt("expires_in");
		System.out.println("access_token:"+accessToken+"--expires_in"+expireIn);
	}
}
