package org.jbiz.demo.action;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.jbiz.demo.logic.TrackRetrieveLogic;
import org.jbiz.demo.logic.param.TrackRetrieveLogicParam;

@Produces("application/json;charset=UTF-8")
@Path("gps")
public class GpsAction extends BaseZAction {

	static Logger logger = Logger.getLogger(GpsAction.class);

	@POST
	@Path("gpsRetrieve.do")
	public String gpsRetrieve(
			@FormParam("carId") Integer carId,
			@FormParam("starttime") String starttime,
			@FormParam("endtime") String endtime,
			@FormParam("nowPage") Integer nowPage,
			@FormParam("pageSize") Integer pageSize,
			@FormParam("avgField") String avgField,
			@Context HttpServletRequest request) {
		
		TrackRetrieveLogicParam myParam = new TrackRetrieveLogicParam(null, null, request);
		myParam.setCarId(carId);
		myParam.setStarttime(starttime);
		myParam.setEndtime(endtime);
		myParam.setNowPage(nowPage);
		myParam.setPageSize(pageSize);
		myParam.setAvgField(avgField);
		
		return new TrackRetrieveLogic().process(myParam);
	}
	
}
