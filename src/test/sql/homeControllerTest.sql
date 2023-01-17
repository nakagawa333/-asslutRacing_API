TRUNCATE table user;
TRUNCATE table setting_info;

insert into `user`
(user_name,password,create_time,update_time,delete_flag,mail)
 values 
('田中太郎','password',now(),now(),'1','nakagawa@mail.com');

insert into setting_info
   (title,
	maker_id,
	course_id,
	car_id,
	user_id,
	create_time,
	update_time
	)
values(
    'test',
    1,
    1,
    1,
    1,
    now(),
    now()
)