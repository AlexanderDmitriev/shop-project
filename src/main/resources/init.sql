drop table users;
create table `user`(
id int not null auto_increment,
username varchar(32) not null default 'someuser',
email varchar(32) not null,
password varchar(64) not null,
role_user enum('ROLE_USER','ROLE_ADMIN') not null default 'ROLE_USER',
primary key(id));


insert into `user`(email,password,role_user) values ('user3@test.com','7e0414c0d6dfd46f53b3fc3be44301a746c20056','ROLE_ADMIN'),('user4@test.com','7e0414c0d6dfd46f53b3fc3be44301a746c20056','ROLE_ADMIN');
