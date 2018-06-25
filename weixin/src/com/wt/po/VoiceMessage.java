package com.wt.po;
/**
 * @Description:
 * @author wt
 * @date 2018年5月25日 下午2:02:47
 */
public class VoiceMessage extends BaseMessage {
	private String MediaId;		//语音消息媒体id，可以调用多媒体文件下载接口拉取该媒体
	private String Format;		//语音格式：amr
	private String Recognition;	//语音识别结果，UTF8编码
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	
	
}
