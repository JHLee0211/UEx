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
    round varchar(100),
    w_recept_start date,
    w_recept_end date,
    w_exam date,
    w_presentation date,
    p_recept_start date,
    p_recept_end date,
    p_exam_start date,
    p_exam_end date,
    p_presentation date
);

create table examinformation_sub (
	jmcd int primary key,
	caution varchar(500),
    price varchar(200)
);

alter database ssafyproject default character set utf8;
alter table customerinformation convert to character set utf8;
alter table customerinterest convert to character set utf8;
alter table examinformation convert to character set utf8;
alter table examinformation_sub convert to character set utf8;

set charset utf8;

select * from customerinformation;
select * from customerinterest;
select * from examinformation;
select * from examinformation_sub;

delete from customerinformation;
delete from customerinterest;
delete from examinformation;
delete from examinformation_sub;

drop table customerinformation;
drop table customerinterest;
drop table examinformation;
drop table examinformation_sub;