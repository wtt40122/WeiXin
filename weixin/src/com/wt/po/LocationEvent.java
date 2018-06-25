package com.wt.po;
/**
 * @Description:
 * @author wt
 * @date 2018年5月25日 下午5:53:06
 */
public class LocationEvent extends BaseEvent {
	
	private String Latitude;		//地理位置维度
	
	private String Longitude;		//地理位置经度
	
	private String Precision;		//地理位置精度
	
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	
}
