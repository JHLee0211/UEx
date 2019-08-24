create database ssafyproject;

create table customerinformation(
	id varchar(30) primary key not null,
    password varchar(30) not null,
    name varchar(30) not null,
    birth date not null,
    sex varchar(10)
);

create table customerinterest(
	id varchar(30) primary key references customerinformation(id),
	interestexam1 varchar(30),
    interestexam2 varchar(30),
    interestexam3 varchar(30)
);

select * from customerinformation;

insert into customerinformation values ('asf', 'asd', 'asd', '2019-08-24', 'man');

delete from customerinformation;

drop table customerinformation;
drop table customerinterest;

select password from customerinformation where id = 'asd'