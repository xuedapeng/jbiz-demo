package org.jbiz.wshandler.param;

import java.util.HashMap;
import java.util.Map;

import org.jbiz.wshandler.base.BaseHandlerParam;

import fw.jbiz.common.helper.JsonHelper;
import fw.jbiz.ext.websocket.ZWsHandlerParam;

public class GpsHandlerParam extends BaseHandlerParam {

	private String latitude;
	private String longitude;
	
	@Override
	public ZWsHandlerParam fromJson(String jsonMsg) {
		
		Map<String, Object> map = JsonHelper.jsonStr2Map(jsonMsg);
		latitude = (String)map.get("latitude");
		longitude = (String)map.get("longitude");
		
		return this;
	}

	@Override
	public String toJson() {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		return JsonHelper.map2JsonStr(map);
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
