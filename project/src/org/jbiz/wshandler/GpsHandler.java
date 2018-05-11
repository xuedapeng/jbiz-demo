package org.jbiz.wshandler;

import org.apache.log4j.Logger;
import org.jbiz.wshandler.base.BaseHandler;
import org.jbiz.wshandler.param.GpsHandlerParam;

import fw.jbiz.common.helper.BeanHelper;
import fw.jbiz.ext.websocket.ZWsHandlerParam;
import fw.jbiz.ext.websocket.annotation.WsHandler;
import fw.jbiz.logic.interfaces.IResponseObject;

@WsHandler(path="gps")
public class GpsHandler extends BaseHandler {

	static Logger logger = Logger.getLogger(GpsHandler.class);
	

	@Override
	public boolean validate(ZWsHandlerParam handlerParam, IResponseObject response) {

		logger.info(BeanHelper.dumpBean(handlerParam));
		return true;
	}

	@Override
	public void onMessage(ZWsHandlerParam handlerParam, IResponseObject response) {
		
		GpsHandlerParam myParam = (GpsHandlerParam)handlerParam;
		
		logger.info(BeanHelper.dumpBean(handlerParam));
		response.add(IResponseObject.RSP_KEY_STATUS, IResponseObject.RSP_CD_INFO);
		response.add(IResponseObject.RSP_KEY_MSG, "this is from respond()");
		response.add("latitude", myParam.getLatitude());
		response.add("longitude", myParam.getLongitude());

		
		
	}

	@Override
	public void onSignIn(ZWsHandlerParam handlerParam, IResponseObject response) {

		logger.info(BeanHelper.dumpBean(handlerParam));
		
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
