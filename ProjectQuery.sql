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

create table examinformation (
	jmcd int,
    name varchar(30),
    round varchar(100),
    w_recept_start date,
    w_recept_end date,
    w_exam date,
    w_presentation date,
    p_recept_start date,
    p_recept_end date,
    p_exam_start date,
    p_exam_end date,
    p_presentation date,
    caution varchar(500),
    price varchar(200)
);

set charset utf8;

select * from customerinformation;
select * from customerinterest;
select * from examinformation;

delete from customerinformation;
delete from customerinterest;
delete from examinformation;

alter database ssafyproject default character set utf8;
alter table examinformation convert to character set utf8;