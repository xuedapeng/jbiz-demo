package org.jbiz.demo.wshandler.param;

import java.util.HashMap;
import java.util.Map;

import org.jbiz.demo.wshandler.base.BaseHandlerParam;

import fw.jbiz.common.helper.JsonHelper;
import fw.jbiz.ext.websocket.ZWsHandlerParam;

public class GpsHandlerParam extends BaseHandlerParam {

	private String latitude;
	private String longitude;
	private Integer carId;
	
	@Override
	public ZWsHandlerParam fromJson(String jsonMsg) {
		
		Map<String, Object> map = JsonHelper.jsonStr2Map(jsonMsg);
		latitude = (String)map.get("latitude");
		longitude = (String)map.get("longitude");
		String carIdStr = (String)map.get("carId");
		
		if (carIdStr != null) {
			carId = Integer.valueOf(carIdStr);
		}
		
		
		
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

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	
}
