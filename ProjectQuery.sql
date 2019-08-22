create database ssafyproject;

create table customerinformation(
	id varchar(30) primary key not null,
    password varchar(30) not null,
    name varchar(30) not null,
    birth date not null,
    sex boolean not null
);

create table customerinterest(
	id varchar(30) primary key references customerinformation(id),
	interestexam1 varchar(30),
    interestexam2 varchar(30),
    interestexam3 varchar(30)
);

drop table customerinformation;
drop table customerinterest;