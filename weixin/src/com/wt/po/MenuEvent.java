package com.wt.po;
/**
 * @Description:
 * @author wt
 * @date 2018年5月25日 下午6:01:05
 */
public class MenuEvent extends BaseEvent {
	private String EventKey;			//事件KEY值，与自定义菜单接口中KEY值对应
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
}
