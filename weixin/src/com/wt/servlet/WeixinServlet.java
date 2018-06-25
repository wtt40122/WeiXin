package com.wt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.wt.po.MessageText;
import com.wt.util.CheckUtil;
import com.wt.util.MessageUtil;

/**
 * @Description:
 * @author wt
 * @date 2018年5月23日 下午1:26:42
 */
public class WeixinServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String signature = req.getParameter("signature");		//微信加密签名
		String timestamp = req.getParameter("timestamp");		//时间戳
		String nonce = req.getParameter("nonce");				//随机数
		String echostr = req.getParameter("echostr");			//随机字符串
		PrintWriter writer = resp.getWriter();
		if(CheckUtil.checkSignature(signature, timestamp, nonce)){
			writer.println(echostr);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Map<String,String> map = MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String message = null;
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)){	//文本类型
				if("1".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondtMenu());
				}else if("?".equals(content) || "？".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}else{
					message = MessageUtil.initText(toUserName, fromUserName, "该关键字未收录");
				}
			}else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){//时间类型
				String 	eventType = map.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, "谢谢你的关注");
				}else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, "祝你一路好走，有时间回来看看");
				}else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){
					//时间key值，有创建菜单时的key值对应
					String eventKey = map.get("EventKey");
					if(eventKey.equals("V1001_TODAY_HISTORY")){//今日历史
						message = MessageUtil.initText(toUserName, fromUserName, "用户点击了今日历史按钮");
					}
					if(eventKey.equals("V1001_HOME_POEM")){
						message = MessageUtil.initText(toUserName, fromUserName, "用户点击了诗书之家按钮");
					}
					if(eventKey.equals("V1001_HOME_NOVEL")){
						message = MessageUtil.initText(toUserName, fromUserName, "用户点击了小说家按钮");
					}
				}
			}else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
				String picUrl = map.get("picUrl");
				//TODO 处理图片消息信息
			}else if(MessageUtil.MESSAGE_VOICE.equals(msgType)){
				String media = map.get("MediaId");
				String format = map.get("Format");
				String recognition =  map.get("Recognition");
				message = MessageUtil.initText(toUserName, fromUserName, 
						"media:"+media+"***format="+format+"***recognition="+recognition);
				//TODO 处理语音消息请求
			}else if(MessageUtil.MESSAGE_VIDEO.equals(msgType)){
				String mediaId = map.get("MediaId");
				String thumbMediaId = map.get("ThumbMediaId");
				//TODO 处理视频消息请求
			}else if(MessageUtil.MESSAGE_LOCATION.equals(msgType)){
				String lat = map.get("Location_X");
				String lng = map.get("Location_Y");
				String label = map.get("Label");
				String scale = map.get("Scale");
				//TODO 处理地理位置消息请求
			}else if(MessageUtil.MESSAGE_LINK.equals(msgType)){
				String title = map.get("Title");
				String description = map.get("Description");
				String url = map.get("Url");
				//TODO 处理链接消息请求
			}
			System.out.println(message);
			out.println(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
