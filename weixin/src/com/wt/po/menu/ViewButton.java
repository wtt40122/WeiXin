package com.wt.po.menu;
/**
 * @Description:view 访问网页类型的按钮
 * @author wt
 * @date 2018年5月31日
 */
public class ViewButton extends Button {
	private String type;
	private String url;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
