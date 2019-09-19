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
	jmcd varchar(30),
    round varchar(100),
    w_recept_start date,
    w_recept_end date,
    w_exam_start date,
    w_exam_end date,
    w_presentation date,
    p_recept_start date,
    p_recept_end date,
    p_exam_start date,
    p_exam_end date,
    p_presentation date,
    etc varchar(500)
);

create table examinformation_sub (
	jmcd varchar(30) primary key references short_examination(jmcd),
	caution varchar(500),
    price varchar(200)
);

create table short_examination(
      jmcd varchar(30) primary key,
      name varchar(30)
);

create table autologin (
	phone_id varchar(50) primary key,
	cookies varchar(100),
    id varchar(30) references customerinformation(id)
);

alter database ssafyproject default character set utf8;
alter table customerinformation convert to character set utf8;
alter table customerinterest convert to character set utf8;
alter table examinformation convert to character set utf8;
alter table examinformation_sub convert to character set utf8;
alter table short_examination convert to character set utf8;
alter table autologin convert to character set utf8;

set charset utf8;

select * from customerinformation;
select * from customerinterest;
select * from examinformation;
select * from examinformation_sub;
select * from short_examination;
select * from autologin;

select name from short_examination where jmcd = '0493';

delete from customerinformation;
delete from customerinterest;
delete from examinformation;
delete from examinformation_sub;
delete from short_examination;
delete from autologin;

drop table customerinformation;
drop table customerinterest;
drop table examinformation;
drop table examinformation_sub;
drop table short_examination;
drop table autologin;