TRUNCATE table user;
delete from setting_info;

insert into `user`
(user_name,password,create_time,update_time,delete_flag,mail)
 values 
('田中太郎','password',now(),now(),'1','nakagawa@mail.com');