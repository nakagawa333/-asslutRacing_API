TRUNCATE table user;
TRUNCATE setting_info;

insert into `user`
(user_name,password,create_time,update_time,delete_flag,mail)
 values 
('田中太郎','password',now(),now(),'1','nakagawa@mail.com');

insert into setting_info
(`title`,`maker_id`,`course_id`,`car_id`,`abs`,`power_steering`,`diffgear`,`frontTire_pressure`,`rearTire_pressure`,`tire_id`,`air_pressure`,`gear_final`,`gear_one`,`gear_two`,`gear_three`,`gear_four`,`gear_five`,`gear_six`,`stabiliser_ago`,`stabiliser_after`,`max_rudder_angle`,`ackermann_angle`,`camber_ago`,`camber_after`,`break_power`,`break_ballance`,`car_high`,`off_set`,`hoilesize`,`memo`,`delete_flag`,`user_id`,`create_time`,`update_time`)
values('R35 ニュル仕様',12,5,105,1,100,0,10,13.5,5,13.5,4.4,3.8,2.4,1.9,1.4,1.1,0,1,1,40,-3.4,-1.4,-4,1,0,8,0,17,'ニュル仕様2',0,1,'2023/01/02 3:01:31','2023/01/02 3:01:31')

