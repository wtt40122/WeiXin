package com.wt.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import com.wt.util.MenuUtil;
import com.wt.util.TokenUtil;

/**
 * @Description:
 * @author wt
 * @date 2018年5月31日
 */
public class MenuTest {
	public static void main(String[] args) throws Exception {
		String token = TokenUtil.getToken();
		String menuCreateUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+token;
		URL url = new URL(menuCreateUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
		TrustManager[] tm = {	new MyX509TrustManager()	};
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new SecureRandom());
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		httpUrlConn.setSSLSocketFactory(ssf);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setDoOutput(true);
		//设置请求方式
		httpUrlConn.setRequestMethod("POST");
		//向输出流写菜单结构
		OutputStream outputStream = httpUrlConn.getOutputStream();
		outputStream.write(MenuUtil.initMenu().getBytes("UTF-8"));
		outputStream.close();
		//取得输入流
		InputStream inputStream = httpUrlConn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		//读取相应内容
		StringBuffer sb = new StringBuffer();
		String str = null;
		while((str = bufferedReader.readLine()) != null){
			sb.append(str);
		}
		//关闭，释放资源
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		outputStream.close();
		httpUrlConn.disconnect();
		System.out.println(sb.toString());
	}
}
