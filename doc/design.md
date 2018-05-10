* 故事

	一群自驾游爱好者（Tom、Jack 、Rose、their families）组成车队前往西藏，在旅行途中，他们需要实时了解车队其它车辆的位置，以便调整车速保持队形。
	
* 场景
	
	1. Tom注册成为系统用户，并登记他的车辆。Jack、Rose也一样注册并登记车辆。
	
	2. Tom把他的车辆轨迹共享给Jack和Rose，他们也把轨迹共享给Tom。
	
	3. Tom、Jack和Rose互相查看车辆实时位置和行车轨迹。
		
		 
* jbiz使用hibernate对关系数据库实现对象／关系映射（ORM），方便起见，demo采用mysql数据库。
* 创建表：

    * t_user 
    
    		id,	account, password, userId, apiKey, 
    		nickName, mobileNum, emailAddr, 
      		createTime, updateTime
      		
    * t_privilege
    
    		id, user_id, car_id, privilege, 
    		createTime, updateTime
      
    * t_car
    	
    		id, carNum, nickName, 
    		createTime, updateTime
    		
    * t_location(将来使用mongoDb)
    	
    		id, latitude, longitude, timestamp,
    		createTime, updateTime
    		
	
    		
