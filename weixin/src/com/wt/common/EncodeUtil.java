package com.wt.common;

import java.net.URLEncoder;

/**
 * @Description:
 * @author wt
 * @date 2018年6月12日
 */
public class EncodeUtil {
	public static String urlEncodeUTF8(String source){
		String result = source;
		try{
			result = URLEncoder.encode(source, "utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}

		return result;
	}
	public static void main(String[] args) {
		String oauthUrl = "http://wangtao.ngrok.xiaomiqiu.cn/weixin/oauthServlet";
		oauthUrl = urlEncodeUTF8(oauthUrl);
		System.out.println(oauthUrl);
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		url = url.replace("APPID", CommonUtil.appID).replace("REDIRECT_URI", oauthUrl).replace("SCOPE", "snsapi_userinfo");
		System.out.println(url);
	}
}
