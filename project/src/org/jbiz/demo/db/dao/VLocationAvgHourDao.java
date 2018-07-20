package org.jbiz.demo.db.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jbiz.demo.db.bean.VLocationAvgHourEntity;


public class VLocationAvgHourDao extends BaseZDao {

	public VLocationAvgHourDao(EntityManager _em) {
		super(_em);
	}

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
	
	public List<VLocationAvgHourEntity> findByHour(Date starttime, Date endtime, Integer carId, 
			Integer nowPage, Integer pageSize) {
		
		if (nowPage == null) {
			nowPage = 1;
		}
		
		if (pageSize == null) {
			pageSize = 1000;
		}
		
		
		String queryString ="from VLocationAvgHourEntity where carId = :carId";
		
		if(starttime != null){
			queryString +=" and timeline >= :starttime";
		}
		if(endtime !=null){
			queryString +=" and timeline < :endtime";
		}
		
		
		
		queryString +=" order by timeline desc";
		
		Query query = getEntityManager().createQuery(queryString);

		query.setParameter("carId", carId);
		
		if(starttime != null){
			query.setParameter("starttime", sdf.format(starttime));
		}

		if(endtime !=null){
			query.setParameter("endtime", sdf.format(endtime));
		}
		

		query.setFirstResult((nowPage-1)*pageSize);
		query.setMaxResults(pageSize);
			
		List<VLocationAvgHourEntity> list = query.getResultList();
		
		if(list == null){
			list = new ArrayList<VLocationAvgHourEntity>();
		}
		
		return list;
	}

}
