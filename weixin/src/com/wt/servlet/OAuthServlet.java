package com.wt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wt.common.AdvanceUtil;
import com.wt.common.CommonUtil;
import com.wt.po.SNSUserInfo;
import com.wt.po.WeixinOauth2Token;

/**
 * @Description:
 * @author wt
 * @date 2018年6月12日
 */
public class OAuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		//用户同意授权后，能获取到code
		String code = req.getParameter("code");
		
		if(!"authdeny".equals(code)){
			//获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = AdvanceUtil.getOauth2AcessToken(CommonUtil.appID, CommonUtil.appSecret, code);
			//网页授权接口访问凭证
			String accessToken = weixinOauth2Token.getAccessToken();
			//用户标识
			String openId = weixinOauth2Token.getOpenId();
			SNSUserInfo snsUserInfo = AdvanceUtil.getSNSUserInfo(accessToken, openId);
			req.setAttribute("snsUserInfo", snsUserInfo);
		}
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
	//https://open.weixin.qq.com/connect/oauth2/authorize?
	//appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#
	//wechat_redirect 若提示“该链接无法访问”，请检查参数是否填写错误，是否拥有scope参数对应的授权作用域权限。
}
