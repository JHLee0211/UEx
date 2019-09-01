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

set sql_safe_updates=0;

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

create table short_examination(
      jmcd int primary key,
      name varchar(30)
);

create table examinformation_sub (
	jmcd int primary key,
	caution varchar(500),
    price varchar(200)
);
select * from short_examination;
insert into short_examination values(1320,"정보처리기사");
insert into short_examination values(2290,"정보처리산업기사");
insert into short_examination values(1104,"금속재료기사");
-- insert into short_examination values(6120,"정밀측정기능사");
insert into short_examination values(1297,"섬유기사");
insert into short_examination values(1350,"도시계획기사");
insert into short_examination values(1611,"방사선비파괴검사기사");
insert into short_examination values(9546,"스포츠경영관리사");
insert into short_examination values(2045,"철도운송산업기사");
insert into short_examination values(1530,"식품기사");
insert into short_examination values(1150,"전기기사");
insert into short_examination values(9539,"임상심리사1급");
-- insert into short_examination values(0210,"화공기술사");
-- insert into short_examination values(7893,"제빵기능사");
insert into short_examination values(2193,"사무자동화산업기사");
insert into short_examination values(1240,"항공기사");
insert into short_examination values(1220,"조선기사");
-- insert into short_examination values(7910,"한식조리기능사");
insert into short_examination values(1340,"에너지관리기사");


alter database ssafyproject default character set utf8;
alter table examinformation convert to character set utf8;
alter table short_examination convert to character set utf8;
alter table examinformation convert to character set utf8;
alter table examinformation_sub convert to character set utf8;
select * from short_examination;
select distinct jmcd from examinformation;
select * from examinformation_sub;
set charset utf8;
drop table short_examination;
drop table examinformation_sub;
select * from customerinformation;
select * from customerinterest;
select * from examinformation;
delete from customerinformation;
delete from customerinterest;
delete from examinformation;
delete from examinformation_sub;
desc short_examination;