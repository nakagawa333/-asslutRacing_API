truncate table user;
truncate table setting_info;

insert into user(user_name,password,create_time,update_time,delete_flag,mail) values 
    ('nakagawa','341552a8ece3764e01c8cbaaed7b34cf02666e6b093cf465358b4fb4110e0312',TIMESTAMP '2022-11-20 14:49:37.000',TIMESTAMP '2022-11-20 14:49:37.000','0','assolutoracingses@gmail.com');
    
insert into setting_info(title,maker_id,course_id,car_id,abs,power_steering,diffgear,`frontTire_pressure`,`rearTire_pressure`,tire_id,air_pressure,gear_final,gear_one,gear_two,gear_three,gear_four,gear_five,gear_six,stabiliser_ago,stabiliser_after,max_rudder_angle,ackermann_angle,camber_ago,camber_after,break_power,break_ballance,car_high,off_set,hoilesize,memo,delete_flag,user_id,create_time,update_time) values 
    ('test',8,5,20,'0',82,79.0,39.0,37.0,2,33.0,5.37,4.85,4.70,4.60,4.53,4.44,0.00,0.7,0.7,53.0,0.7,0.0,0.0,0.8,0.5,10.0,3.0,-0.5,'最新投稿',0,1,TIMESTAMP '2023-01-02 03:04:26.000',TIMESTAMP '2023-01-02 16:49:12.000');
 