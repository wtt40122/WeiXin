package com.wt.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.wt.po.MessageText;

/**
 * @Description:消息工具类
 * @author wt
 * @date 2018年5月24日 下午2:24:21
 */
public class MessageUtil {
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	/**
	 * xml转map
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		reader.setEncoding("utf-8");
		InputStream inputStream = request.getInputStream();
		Document doc = reader.read(inputStream);
		Element root = doc.getRootElement();
		List<Element> elements = root.elements();
		for (Element element : elements) {
			map.put(element.getName(), element.getText());
		}
		inputStream.close();
		return map;
	}
	public static String textToXml(MessageText messageText){
		XStream xStream = new XStream();
		xStream.alias("xml", messageText.getClass());
		return xStream.toXML(messageText);
	}
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎你的关注，请按照菜单提示进行操作:\n");
		sb.append("1.公众号介绍\n");
		sb.append("2.公司介绍");
		return sb.toString();
	}
	public static String initText(String toUserName,String fromUserName,String content){
		MessageText messageText = new MessageText();
		messageText.setFromUserName(toUserName);
		messageText.setToUserName(fromUserName);
		messageText.setMsgType(MessageUtil.MESSAGE_TEXT);
		messageText.setCreateTime(String.valueOf(new Date().getTime()));
		messageText.setContent(content);
		return MessageUtil.textToXml(messageText);
	}
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("本套课程介绍公众号开发，主要涉及公众号的到公众平台开发者文档，详细了解公众平台所有接口的介绍和使用说明:\n");
		return sb.toString();
	}
	public static String secondtMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("什么是文人士大夫精神？是一种进可以建功立业、经世济民，退可以渔樵江渚、躬耕丘野的精神，"
				+ "是一种贵而不骄、贫而不忧的恬淡的人生态度。而这种精神和态度正是这个社会所匮乏的。"
				+"遗憾的是，当今社会，鲜有对这种精神的继承者。:\n");
		return sb.toString();
	}
}
