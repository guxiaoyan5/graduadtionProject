# # create database graduate;
use graduate;
#原始信息表

create table college
(
    id      int AUTO_INCREMENT primary key,
    college varchar(150) comment '学院名'
) comment '学院表';
create table major
(
    id         int AUTO_INCREMENT primary key,
    major      varchar(150) comment '专业名',
    college_id int,
    foreign key (college_id) references college (id)
) comment '专业表';
create table class
(
    id       int AUTO_INCREMENT primary key,
    class    varchar(150) comment '班级名',
    major_id int,
    foreign key (major_id) references major (id)
) comment '班级表';
create table store
(
    id         int AUTO_INCREMENT primary key,
    store_name varchar(150) not null unique comment '商户名字'
) comment '商户信息';
create table student
(
    id         varchar(20) primary key comment '学号',
    name       varchar(150)   not null comment '姓名',
    class_id   int            not null comment '班级',
    major_id   int            not null comment '专业',
    college_id int            not null comment '学院',
    grade      int            not null comment '年级',
    sex        enum ('男','女') not null comment '性别',
    foreign key (class_id) references class (id),
    foreign key (major_id) references major (id),
    foreign key (college_id) references college (id)
) comment '学生信息表';
create table consume
(
    id             int AUTO_INCREMENT primary key,
    sid            varchar(20)  not null comment '学号',
    execution_time datetime not null comment '交易时间',#交易时间
    money          float    not null comment '交易金额',#交易金额
    store_id       int      not null comment '商户',#商户
    mode           varchar(50) comment '交易方式',
    foreign key (sid) references student (id),
    foreign key (store_id) references store (id)
) comment '消费信息表';

#
create table school_user
(
    id       char(10) primary key,
    name     varchar(10) not null,
    password varchar(20) not null
);

create table admin_user
(
    name     char(4) primary key,
    password varchar(20) not null
);

