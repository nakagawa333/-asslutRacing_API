delete from password_resets;

insert into password_resets
(mail,token,create_time)
values
('assolutoracingses@gmail.com','token',now())