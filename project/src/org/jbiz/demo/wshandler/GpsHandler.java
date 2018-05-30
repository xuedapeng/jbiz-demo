package org.jbiz.demo.wshandler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbiz.demo.wshandler.base.BaseHandler;
import org.jbiz.demo.wshandler.param.GpsHandlerParam;
import org.jbiz.demo.db.bean.LocationEntity;
import org.jbiz.demo.db.dao.LocationDao;
import org.jbiz.demo.wshandler.GpsHandler;

import fw.jbiz.common.helper.BeanHelper;
import fw.jbiz.db.ZDao;
import fw.jbiz.ext.json.ZGsonObject;
import fw.jbiz.ext.websocket.ZWsEventChannel;
import fw.jbiz.ext.websocket.ZWsHandlerParam;
import fw.jbiz.ext.websocket.annotation.WsHandler;
import fw.jbiz.logic.interfaces.IResponseObject;

@WsHandler(path="gps")
public class GpsHandler extends BaseHandler {

	static Logger logger = Logger.getLogger(GpsHandler.class);
	
	static Map<Integer, Date> _lastSavedDateMap = new HashMap<Integer, Date>();
	static Date _epochDate =  new Date(0);
	
	@Override
	public boolean validate(ZWsHandlerParam handlerParam, IResponseObject response) {

		logger.info(BeanHelper.dumpBean(handlerParam));
		return true;
	}

	@Override
	public void onMessage(ZWsHandlerParam handlerParam, IResponseObject response) {

		logger.info(BeanHelper.dumpBean(handlerParam));
		
		GpsHandlerParam myParam = (GpsHandlerParam)handlerParam;
		
		Integer carId = myParam.getCarId();
		
		
		// 以下，通过订阅／发布 到客户端
		IResponseObject response2 = new ZGsonObject();
		response2.add(IResponseObject.RSP_KEY_STATUS, IResponseObject.RSP_CD_OK)
			.add(IResponseObject.RSP_KEY_MSG, "this is from publish")
			.add("latitude", myParam.getLatitude())
			.add("longitude", myParam.getLongitude())
			.add("carId", carId);
		
		ZWsEventChannel.publish("tom", response2);
		
		// 55秒以内不保存
		if (!_lastSavedDateMap.containsKey(carId)) {
			_lastSavedDateMap.put(carId, _epochDate);
		}
			
		Date nowDate = new Date();
		if (nowDate.getTime() - _lastSavedDateMap.get(carId).getTime() < 55*1000 ) {
			return;
		}
		
		// 保存到数据库
		LocationEntity locationEntity = new LocationEntity();
		
		locationEntity.setCarId(myParam.getCarId());
		locationEntity.setLongitude(myParam.getLongitude());
		locationEntity.setLatitude(myParam.getLatitude());
		locationEntity.setTimestamp(nowDate);
		
		LocationDao locationDao = new LocationDao(null);
		ZDao.saveAsy(locationEntity, locationDao);
		
		_lastSavedDateMap.put(carId, nowDate);
	}

	@Override
	public void onSignIn(ZWsHandlerParam handlerParam, IResponseObject response) {

		logger.info(BeanHelper.dumpBean(handlerParam));
		
		// 订阅
		ZWsEventChannel.subscribe("tom", this.getSession().getId());
		
		response.add("status", 1);
		response.add("msg", "sign in OK");
	}

	@Override
	public void onSignOut(ZWsHandlerParam handlerParam, IResponseObject response) {

		logger.info(BeanHelper.dumpBean(handlerParam));

		response.add("status", 1);
		response.add("msg", "sign out OK");
	}

	@Override
	public ZWsHandlerParam getHandlerParam() {

		return new GpsHandlerParam();
	}


}
