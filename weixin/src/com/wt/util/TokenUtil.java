package com.wt.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.wt.common.MyX509TrustManager;

import net.sf.json.JSONObject;

/**
 * @Description:
 * @author wt
 * @date 2018年5月31日
 */
public class TokenUtil {
	//微信号Id
	private static final String appID = "wxc5fab93cb0fee393";
	//微信号密钥
	private static final String appSecret = "5ec6fcbb25c16f6478240e2810e29901";
	public static String getToken(){
		String accessToken = "";
		try{
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
			conn.disconnect();
			System.out.println(buffer);
			
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
			accessToken = jsonObject.getString("access_token");
			int expireIn = jsonObject.getInt("expires_in");
			System.out.println("access_token:"+accessToken+"--expires_in"+expireIn);
		}catch(Exception e){
			e.printStackTrace();
		}
		return accessToken;
	}
}
