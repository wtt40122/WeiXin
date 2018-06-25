package com.wt.common;

import java.util.ArrayList;
import java.util.List;

import com.wt.po.Article;
import com.wt.po.Token;

import net.sf.json.JSONArray;

/**
 * @Description:微信客服发送消息
 * @author wt
 * @date 2018年5月31日
 */
public class SendMessage {
	/**
	 * 组装文本客服信息
	 * @param openId
	 * @param content
	 * @return
	 */
	public static String makeTextCustomMessage(String openId,String content){
		content = content.replace("\"", "\\\"");
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId, content);
	}
	/**
	 * 组装图片客服信息
	 * @param openId
	 * @param mediaId
	 * @return
	 */
	public static String makeImageCustomMessage(String openId,String mediaId){
		String jsonMsg = "{\"touse\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":%s\"}}";
		return String.format(jsonMsg, openId,mediaId);
	}
	/**
	 * 组装语音客服信息
	 * @param openId
	 * @param mediaId
	 * @return
	 */
	public static String makeVoiceCustomMessage(String openId,String mediaId){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\","
				+ "\"voice\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId,mediaId);
	}
	/**
	 * 组装图文客服消息
	 * 
	 * @param openId
	 *            消息发送对象
	 * @param articleList
	 *            图文消息列表
	 * @return
	 */
	public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
		jsonMsg = String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
		// 将jsonMsg中的picUrl替换为picurl
		jsonMsg = jsonMsg.replace("picUrl", "picurl");
		return jsonMsg;
	}
	public static void main(String[] args) {
		//openId=fromUsername
		//获取接口访问凭证
		String accessToken = CommonUtil.getToken(CommonUtil.appID, CommonUtil.appSecret).getAccessToken();
		//组装文本客服消息
		String jsonTextMsg = makeTextCustomMessage("oSACm07i7ahfsvugQkUC8P_pgnao", "青青河边草，，悠悠天不老");
		
		CommonUtil.sendCustomMessage(accessToken, jsonTextMsg);
		
		Article article = new Article();
		article.setDescription("这是一篇文本消息");
		article.setTitle("学习能救中国吗?");
		article.setPicUrl("https://mmbiz.qpic.cn/mmbiz_jpg/C9tGicHorOXASrAwareITCsdZD4CVKnKyMU4Ubuoaw6aXCAMUeLmiaMFw8mUbYVlEoZxPyDj9NOO3QY37vwVNdMg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");
		article.setUrl("https://mp.weixin.qq.com/s?__biz=MzUyMTgyMTc1NQ==&tempkey=OTYwX2xGdVMyQ0JuUGFpVmwza296dUN5ampaZFVNTDRNcHZST0RYNDVNRHRNampWVUx1LXdvQlVIVndmV29ZU19DTWhDSy0yeVdkdjB1dVU4RU9LY2E1RXd0RkxxaXBqX0NKZndXMzBRaTVlRzRiRVR3UUVMeDRjd3pPdDNHZ2h3QXN5LTdjeUtaRmpKdW9OQ0xNRl9iUEE0ZnI5V3hxV2Z1Mng3Rm1FTHd%2Bfg%3D%3D&chksm=79d40a564ea3834090cd034d6aab9aa20471538014de97eeeed933ce5200a105e0f6bbede141#rd");
		List<Article> articles = new ArrayList<>();
		articles.add(article);
		String jsonTextImg = makeNewsCustomMessage("oSACm07i7ahfsvugQkUC8P_pgnao",articles);
		CommonUtil.sendCustomMessage(accessToken, jsonTextImg);
	}
}
