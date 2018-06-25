package com.wt.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wt.common.CommonUtil;
import com.wt.po.menu.Button;
import com.wt.po.menu.ClickButton;
import com.wt.po.menu.ComplexButton;
import com.wt.po.menu.Menu;
import com.wt.po.menu.ViewButton;

import net.sf.json.JSONObject;

/**
 * @Description:自定义菜单工具类
 * @author wt
 * @date 2018年5月31日
 */
public class MenuUtil {
	private static Logger log = LoggerFactory.getLogger(MenuUtil.class);
	//菜单创建
	private final static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?"
			+ "access_token=ACCESS_TOKEN";
	//菜单查询
	private final static String menu_get_url = "https://api.weixin.qq.com/cgi-bin/menu/get?"
			+ "access_token=ACCESS_TOKEN";
	//删除菜单
	private final static String menu_delete_url = "https://api.weixin.qq.com/cgi-bin/menu/delete?"
			+ "access_token=ACCESS_TOKEN";
	/**
	 * 创建菜单
	 * @param menu
	 * @param accessToken
	 * @return
	 */
	public static boolean createMenu(Menu menu,String accessToken){
		boolean result = false;
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		String jsonMenu = JSONObject.fromObject(menu).toString();
		//发送http post请求创建菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);
		if(null != jsonObject){
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if(0 == errorCode){
				result = true;
			}else{
				result = false;
				log.error("创建菜单失败 errcode:{} errmsg:{}",errorCode,errorMsg);
			}
			
		}
		return result;
	}
	/**
	 * 查询菜单
	 * @param accessToken
	 * @return
	 */
	public static String getMenu(String accessToken){
		String result = null;
		String requestUrl = menu_get_url.replace("ACCESS_TOKEN", accessToken);
		//发送get请求查询菜单
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			result = jsonObject.toString();
		}
		return result;
	}
	/**
	 * 删除失败
	 * @param accessToken
	 * @return
	 */
	public static boolean deleteMenu(String accessToken){
		boolean result = false;
		String requestUrl = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);
		if(null != jsonObject){
			int errorcode = jsonObject.getInt("errcode");
			String errormsg = jsonObject.getString("errmsgs");
			if(0 == errorcode){
				result = true;
			}else{
				result = false;
				log.error("删除菜单失败 errcode:{} errmsg:{}",errorcode,errormsg);
			}
		}
		return result;
	}
	public static String initMenu(){
		ClickButton cBtn1 = new ClickButton();
		cBtn1.setName("历史今日");
		cBtn1.setType("click");
		cBtn1.setKey("V1001_TODAY_HISTORY");
		
		ViewButton vBtn1 = new ViewButton();
		vBtn1.setName("英雄人物");
		vBtn1.setType("view");
		vBtn1.setUrl("http://www.qstheory.cn/politics/2015-09/29/c_1116700869.htm");
		
		ClickButton cBtn2 = new ClickButton();
		cBtn2.setName("诗书之家");
		cBtn2.setType("click");
		cBtn2.setKey("V1001_HOME_POEM");
		
		ClickButton cBtn3 = new ClickButton();
		cBtn3.setName("小说家");
		cBtn3.setType("click");
		cBtn3.setKey("V1001_HOME_NOVEL");
		
		//符合按钮报货两个click类型的按钮
		ComplexButton complexButton = new ComplexButton();
		complexButton.setName("精彩之家");
		complexButton.setSub_button(new Button[]{cBtn2,cBtn3});
		
		//创建菜单对象
		Menu menu = new Menu();
		menu.setButton(new Button[]{cBtn1,vBtn1,complexButton});
		
		String jsonMenu = JSONObject.fromObject(menu).toString();
		System.out.println(jsonMenu);
		return jsonMenu;
	}
}
