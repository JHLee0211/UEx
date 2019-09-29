create database ssafyproject;
use ssafyproject;

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

create table autologin(
	phone_id varchar(50) primary key,
    cookies varchar(100),
    id varchar(30) references customerinformation(id)
);

insert into short_examination values ('9999', 'test');
insert into short_examination values ('9998', 'te');
insert into examinformation values ('9999', '1회', '2019-09-03', '2019-09-04', '2019-10-03', '2019-10-04', '2019-10-10', '2019-11-04', '2019-11-04', '2019-12-04', '2019-12-04', '2019-12-20', null);
insert into examinformation_sub values ('9999', '이 시험은 09:00 정각에 시작을 합니다.', '- 필기 : 15000원  - 실기 : 30000원');
insert into examinformation values ('9999', '2회', null, null, null, null, null, '2019-11-04', '2019-11-04', '2019-12-04', '2019-12-04', '2019-12-20', '이 시험은 특정 대상들만 시험 볼 수 있습니다.');
insert into examinformation values ('9998', '1회', '2019-09-03', '2019-09-04', '2019-10-03', '2019-10-04', '2019-10-10', '2019-11-04', '2019-11-04', '2019-12-04', '2019-12-04', '2019-12-20', '두 번째 시험입니다.');
insert into examinformation_sub values ('9998', '이 시험은 09:00 정각에 시작을 합니다.', '- 필기 : 10000원  - 실기 : 35000원');

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