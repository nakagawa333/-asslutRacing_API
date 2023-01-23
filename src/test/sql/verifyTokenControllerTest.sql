truncate table temp_user;
truncate table user;

insert into temp_user
(
 user_name,mail,auth_token,password,create_time,delete_flag
) values
('username','test@co.jp','token','password',now(),0)