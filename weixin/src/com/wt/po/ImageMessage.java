package com.wt.po;
/**
 * @Description:
 * @author wt
 * @date 2018年5月25日 下午2:01:44
 */
public class ImageMessage extends BaseMessage {
	private String PicUrl;		//图片链接（由系统生成）

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	
	
}
