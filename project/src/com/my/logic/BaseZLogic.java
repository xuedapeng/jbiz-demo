package com.my.logic;

import javax.persistence.EntityManager;


import fw.jbiz.common.conf.ZSystemConfig;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.ext.service.accessstats.ZStatsFilter;
import fw.jbiz.logic.ZLogic;
import fw.jbiz.logic.ZLogicParam;

public abstract class BaseZLogic extends ZLogic {

	public BaseZLogic() {
		// 增加访问统计过滤器
//		addFilter(new ZStatsFilter());
	}
	
	protected String getPersistenceUnitName() {
		return ZSystemConfig.getProperty("PersistenceUnitName");
	}
	

   /*
    * 身份认证
    */
	@Override
   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em){

		// todo: 
		return true;
   }
	
}
