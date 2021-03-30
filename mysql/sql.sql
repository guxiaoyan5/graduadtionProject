#create database graduate;
use graduate;
create table student(
    id char(6) primary key ,
    name varchar(20),
    class varchar(20),
    college varchar(30),
    sex char(1)
);
create table consume(
    id int AUTO_INCREMENT primary key ,
    sid char(6),
    execution_time datetime,
    money float,
    merchant_name varchar(50),
    foreign key (sid) references student(id)
)