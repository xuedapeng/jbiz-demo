### create sql

* v_location

		select id, carId, longitude, latitude, timestamp, createTime, updateTime, 
		year(timestamp) as year, 
		month(timestamp) as month, 
		day(timestamp) as day, 
		hour(timestamp) as hour, 
		minute(timestamp) as minute, 
		second(timestamp) as second 
		
		from t_location

* v_location_avg_hour

		select CONCAT(carId, '-', year, '-', month, '-', day, '-', hour) as id, carId, 
		FORMAT(AVG(longitude),2) as longitude , 
		FORMAT(AVG(latitude),2) as latitude, 
		CONCAT(year, '-', LPAD(month,2,0) , '-',LPAD(day,2,0), 
		' ', LPAD(hour,2,0)) as timeline 
		from v_location 
		GROUP BY carId, year, month, day, hour 
		order by timeline desc 