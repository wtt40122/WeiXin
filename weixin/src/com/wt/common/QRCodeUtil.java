package com.wt.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wt.po.Token;
import com.wt.po.WeixinQRCode;

import net.sf.json.JSONObject;

/**
 * @Description:
 * @author wt
 * @date 2018年6月12日
 */
public class QRCodeUtil {
	private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);
	/**
	 * 获取临时二维码
	 * @param accessToken
	 * @param expireSeconds
	 * @param sceneId
	 * @return
	 */
	public static WeixinQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId){
		WeixinQRCode weixinQRCode = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		requestUrl = requestUrl.replace("TOKEN", accessToken);
		String jsonMsg = "{\"expire_seconds\":%d,\"action_name\":\"QR_SCENE\","
				+ "\"action_info\":{\"scene\":{\"scene_id\":%d}}}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, expireSeconds,sceneId));
		if(null != jsonObject){
			try{
				weixinQRCode = new WeixinQRCode();
				weixinQRCode.setExpireSeconds(jsonObject.getInt("expire_seconds"));
				weixinQRCode.setTicket(jsonObject.getString("ticket"));
				logger.info("创建临时带二维码成功 ticket:{} expire_seconds:{}",weixinQRCode.getTicket(),weixinQRCode.getExpireSeconds());;
			}catch(Exception e){
				e.printStackTrace();
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error("创建临时二维码失败errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return weixinQRCode;
	}
	/**
	 * 创建永久带参二维码
	 * @param accessToken
	 * @param sceneId场景ID
	 * @return
	 */
	public static String createPermanentQRCode(String accessToken,int sceneId){
		String ticket = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		requestUrl = requestUrl.replace("TOKEN", accessToken);
		String jsonMsg = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": "
				+ "{\"scene\": {\"scene_id\": %d}}}";
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);
		if(null != jsonObject){
			try{
				ticket = jsonObject.getString("ticket");
				logger.info("创建永久带参二维码成功 ticket:{}",ticket);
			}catch(Exception e){
				e.printStackTrace();
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				logger.error("创建永久带参二维码失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
		}
		return ticket;
	}
	/**
	 * 根据ticket获取二维码
	 * @param ticket
	 * @param savePath
	 * @return
	 */
	public static String getQRCode(String ticket,String savePath){
		String filePath = null;
		String requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET", EncodeUtil.urlEncodeUTF8(ticket));
		try{
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			
			if(!savePath.endsWith("/")){
				savePath += "/";
			}
			filePath = savePath + ticket + ".jpg";
			
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			byte[] buf = new byte[8096];
			
			int size = 0;
			while((size = bis.read(buf)) != -1){
				fos.write(buf, 0, size);
			}
			fos.close();
			bis.close();
			
			conn.disconnect();
			logger.info("根据ticket换取二维码成功,filePath = "+filePath);
		}catch(Exception e){
			logger.error("根据二维码获取二维码失败,{}",e);
		}
		return filePath;
	}
	public static void main(String[] args) {
		String token = CommonUtil.getToken(CommonUtil.appID, CommonUtil.appSecret).getAccessToken();
		WeixinQRCode weixinQRCode = createTemporaryQRCode(token, 900, 111111);
		String ticket = createPermanentQRCode(token, 617);
		System.out.println(weixinQRCode.getTicket());
		System.out.println("永久二维码:"+ticket);
		getQRCode(ticket, "d:/download");
		System.out.println(weixinQRCode.getExpireSeconds());
	}
}
