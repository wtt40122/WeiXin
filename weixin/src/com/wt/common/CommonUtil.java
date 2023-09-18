package com.wt.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.wt.po.Token;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @Description:通用工具类
 * @author wt
 * @date 2018年5月31日
 */
public class CommonUtil {
	
	//微信号Id
	//public static final String appID = "wxfcd15d9848c4968b";
	//微信号密钥
	//public static final String appSecret = "c1276c49c9fde15a58a5e7063f3a7f0b";

	//
	//测试公众号
	//微信号Id
	public static final String appID = "wxc5fab93cb0fee393";
	//微信号密钥
	public static final String appSecret = "5ec6fcbb25c16f6478240e2810e29901";
	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type="
			+ "client_credential&appid=APPID&secret=APPSECRET";

	public static JSONObject httpsRequest(String requestUrl,String requestMethod,String outputStr){
		JSONObject jsonObject = null;
		try{
			//创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = {	new MyX509TrustManager()	};
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			//从sslContext中得到SSLSocketFacory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			
			conn.setRequestMethod(requestMethod);
			if(outputStr != null){
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(outputStr.getBytes("uTF-8"));
				outputStream.close();
			}
			//从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer sb = new StringBuffer();
			while((str = bufferedReader.readLine()) != null){
				sb.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(sb.toString());
		}catch(ConnectException ce){
			log.error("链接超时：{}",ce);
		}catch(Exception e){
			log.error("https请求异常:{}",e);
		}
		return jsonObject;
	}
	/**
	 * 获取接口访问凭证
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static Token getToken(String appId,String appSecret){
		Token token = null;
		String requestUrl = token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
		//发起get请求获取凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				token = new Token();
				token.setAccessToken(jsonObject.getString("access_token"));
				token.setExpiresIn(jsonObject.getInt("expires_in"));
			}catch(JSONException e){
				token = null;
				//获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}",jsonObject.getInt("errcode"),
						jsonObject.getString("errmsg"));
			}
		}
		return token;
	}
	/**
	 * 发送客服消息
	 * @param accessToken
	 * @param jsonMsg
	 * @return
	 */
	public static boolean sendCustomMessage(String accessToken,String jsonMsg){
		log.info("消息内容:{}",jsonMsg);
		boolean result = false;
		//拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?"
				+ "access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		//发送客服信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(null != jsonObject){
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(0==errorCode){
				result = true;
				log.info("客服消息发送成功 errcode:{} errormsg:{}",errorCode,errorMsg);
			}else{
				log.info("客服消息发送失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return result;
	}
}
