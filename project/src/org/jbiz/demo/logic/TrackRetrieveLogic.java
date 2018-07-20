package org.jbiz.demo.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.jbiz.demo.db.bean.LocationEntity;
import org.jbiz.demo.db.bean.VLocationAvgHourEntity;
import org.jbiz.demo.db.dao.LocationDao;
import org.jbiz.demo.db.dao.VLocationAvgHourDao;
import org.jbiz.demo.logic.param.TrackRetrieveLogicParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import fw.jbiz.logic.interfaces.IResponseObject;

public class TrackRetrieveLogic extends BaseZLogic {


	Integer carId = null;
	Date starttime = null;
	Date endtime = null;
	Integer pageSize = null;
	Integer nowPage = null;
	String avgField = null;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {

		if ("hour".equals(avgField)) {
			return this.exeAvgHour(res, em);
		}
		
		return this.exeWhole(res, em);
		
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		
		TrackRetrieveLogicParam myParam = (TrackRetrieveLogicParam)logicParam;

		carId = myParam.getCarId();
		String starttimeStr = myParam.getStarttime();
		String endtimeStr = myParam.getEndtime();
		pageSize = myParam.getPageSize();
		nowPage = myParam.getNowPage();
		avgField = myParam.getAvgField();
		
		if (carId == null) {
			res.add("status", IResponseObject.RSP_CD_ERR_PARAM);
			res.add("msg", "need carId.");
			return false;
		}
		
		
		try {
			if (starttimeStr != null) {
				starttime = sdf.parse(starttimeStr);
			}
			if (endtimeStr != null) {
				endtime = sdf.parse(endtimeStr);
			}
		} catch(ParseException pe) {
			res.add("status", "-11");
			res.add("msg", "starttime/endtime format error.");
			return false;
		}
		
		return true;
	}
	
	private boolean exeWhole(ZSimpleJsonObject res, EntityManager em) {

		LocationDao dao = new LocationDao(em);
		List<LocationEntity> list = dao.findByDate(starttime, endtime, carId, nowPage, pageSize);
		
		// time, longitude, latitude
		List<List<String>> resultList = new ArrayList<List<String>>();
		
		for (LocationEntity entity: list) {
			Date timestamp = entity.getTimestamp();
			String longtitude = entity.getLongitude();
			String latitude = entity.getLatitude();
			
			String timestampStr = sdf.format(timestamp);
			
			List<String> item = new ArrayList<String>();
			item.add(timestampStr);
			item.add(longtitude);
			item.add(latitude);
			resultList.add(item);
			
		}

		res.add("status", IResponseObject.RSP_CD_OK);
		res.add("msg", "search whole ok.");
		res.add("result", resultList);
		
		return true;
	}

	private boolean exeAvgHour(ZSimpleJsonObject res, EntityManager em) {

		VLocationAvgHourDao dao = new VLocationAvgHourDao(em);
		List<VLocationAvgHourEntity> list = dao.findByHour(starttime, endtime, carId, nowPage, pageSize);
		
		// time, longitude, latitude
		List<List<String>> resultList = new ArrayList<List<String>>();
		
		for (VLocationAvgHourEntity entity: list) {
			String timeline = entity.getTimeline();
			String longtitude = entity.getLongitude();
			String latitude = entity.getLatitude();
			
			
			List<String> item = new ArrayList<String>();
			item.add(timeline);
			item.add(longtitude);
			item.add(latitude);
			resultList.add(item);
			
		}

		res.add("status", IResponseObject.RSP_CD_OK);
		res.add("msg", "search avg hour ok.");
		res.add("result", resultList);
		
		return true;
	}

}
