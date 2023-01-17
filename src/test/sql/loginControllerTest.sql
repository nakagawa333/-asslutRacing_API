TRUNCATE table user;
delete from setting_info;

insert into `user`
(user_name,password,create_time,update_time,delete_flag,mail)
 values 
('田中太郎','5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8',now(),now(),'1','test@mail.com');