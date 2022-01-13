drop database if exists test;

create database if not exists test;

create table t_student (
   id int primary key auto_increment,
   name varchar(20),
   sex boolean,
   age int,
   address varchar(50)
);

insert into t_student values(null,'孙悟空',1,30,'水帘洞'),(null,'猪八戒',1,26,'高老庄'),(null,'白骨精',0,22,'白骨洞');

select * from t_student;

create table t_user (
   id int primary key auto_increment,
   name varchar(20),
   password varchar(32)
);

insert into t_user values(null, 'NewBoy','123'),(null,'Rose','456');

select * from t_user;