package org.jbiz.demo.db.bean;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jbiz.demo.db.bean.BaseZEntity;

@Entity
@Table(name = "t_location")
public class LocationEntity extends BaseZEntity {

	private Integer id;
	private Integer carId;
	private String longitude;
	private String latitude;
	private Date timestamp;
	
	
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarId() {
		return carId;
	}
	
	public void setCarId(Integer carId) {
		this.carId = carId;
	}



	public String getLongitude() {
		return longitude;
	}



	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	public String getLatitude() {
		return latitude;
	}



	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}



	public Date getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}



	
}
