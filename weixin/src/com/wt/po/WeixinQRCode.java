package com.wt.po;
/**
 * @Description:
 * @author wt
 * @date 2018年6月12日
 */
public class WeixinQRCode {
	private String ticket;			//获取的二维码ticket
	private int expireSeconds;		//二维码有效时间
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpireSeconds() {
		return expireSeconds;
	}
	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
	
	
}
