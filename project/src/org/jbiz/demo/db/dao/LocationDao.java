package org.jbiz.demo.db.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jbiz.demo.db.bean.LocationEntity;


public class LocationDao extends BaseZDao {

	public LocationDao(EntityManager _em) {
		super(_em);
	}
	
	
	public List<LocationEntity> findByDate(Date starttime, Date endtime, Integer carId, 
			Integer nowPage, Integer pageSize) {
		
		if (nowPage == null) {
			nowPage = 1;
		}
		
		if (pageSize == null) {
			pageSize = 1000;
		}
		
		String queryString ="from LocationEntity where carId = :carId";
		
		if(starttime != null){
			queryString +=" and timestamp >= :starttime";
		}
		if(endtime !=null){
			queryString +=" and timestamp < :endtime";
		}
		
		queryString +=" order by timestamp desc";
		
		Query query = getEntityManager().createQuery(queryString);

		query.setParameter("carId", carId);
		
		if(starttime != null){
			query.setParameter("starttime", starttime);
		}

		if(endtime !=null){
			query.setParameter("endtime", endtime);
		}
		

		query.setFirstResult((nowPage-1)*pageSize);
		query.setMaxResults(pageSize);
			
		List<LocationEntity> list = query.getResultList();
		
		if(list == null){
			list = new ArrayList<LocationEntity>();
		}
		
		return list;
	}

}
