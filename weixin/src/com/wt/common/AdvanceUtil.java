package com.wt.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.wt.po.SNSUserInfo;
import com.wt.po.WeixinOauth2Token;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @Description:
 * @author wt
 * @date 2018年6月11日
 */
public class AdvanceUtil {
	private static Logger logger = LoggerFactory.getLogger(AdvanceUtil.class);
	/**
	 * 获取网页授权凭证
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static WeixinOauth2Token getOauth2AcessToken(String appId,String appSecret,String code){
		WeixinOauth2Token wat = null;
		String requestUrl = " https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId).replace("SECRET", appSecret).replace("CODE", code);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch(Exception e){
				e.printStackTrace();
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.info("获取网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;
	}
	/**
	 * 刷新网页授权凭证
	 * @param appId
	 * @param refreshToken
	 * @return
	 */
	public static WeixinOauth2Token refreshOatuth2AccessToken(String appId,String refreshToken){
		WeixinOauth2Token wat = null;
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?"
				+ "appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl = requestUrl.replace("appid", appId).replace("refresh_token", refreshToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			}catch (Exception e) {
				e.printStackTrace();
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errormsg");
				logger.error("刷新网页授权凭证失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return wat;	
	}
	/**
	 * 通过网页授权获取用户信息
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static SNSUserInfo getSNSUserInfo(String accessToken,String openId){
		SNSUserInfo snsUserInfo = null;
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			try{
				snsUserInfo = new SNSUserInfo();
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				//性别 1:男  2:女
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				snsUserInfo.setCountry(jsonObject.getString("country"));
				snsUserInfo.setProvince(jsonObject.getString("province"));
				snsUserInfo.setCity(jsonObject.getString("city"));
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"),List.class));
			}catch(Exception e){
				e.printStackTrace();
				int errorcode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errormsg");
				logger.error("获取用户信息失败  errcode:{} errmsg:{}",errorcode,errorMsg);
			}
		}
		return snsUserInfo;
	}
}
