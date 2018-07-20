package org.jbiz.demo.logic.param;

import javax.servlet.http.HttpServletRequest;

import org.jbiz.demo.logic.BaseZLogicParam;

public class TrackRetrieveLogicParam extends BaseZLogicParam {

	public TrackRetrieveLogicParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);

	}
	
	private Integer carId;
	private String starttime;
	private String endtime;
	private Integer nowPage;
	private Integer pageSize;
	private String avgField;
	
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public Integer getNowPage() {
		return nowPage;
	}
	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getAvgField() {
		return avgField;
	}
	public void setAvgField(String avgField) {
		this.avgField = avgField;
	}
	
	
}

