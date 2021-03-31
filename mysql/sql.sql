create database graduate;
use graduate;
#原始信息表
create table student(
    id char(6) primary key comment '学号',
    name varchar(50) not null comment '姓名',
    class varchar(50) not null comment '班级',
    major varchar(50) not null comment '专业',
    college varchar(50) not null comment '学院',
    sex enum('男','女') not null comment '性别'
)comment '学生信息表';
create table consume(
    id int AUTO_INCREMENT primary key ,
    sid char(6) not null comment '学号',
    execution_time datetime not null comment '交易时间',#交易时间
    money float not null comment '交易金额',#交易金额
    store_name varchar(50) not null comment '商户名称',#商户
    foreign key (sid) references student(id)
)comment '消费信息表';
#各个学院、专业、班级、性别人数表
create table college_student_count(
    id int AUTO_INCREMENT primary key ,
    college varchar(50) unique comment '学院名',
    student_count int not null comment '人数'
)comment '学院人数表';
create table major_student_count(
    id int AUTO_INCREMENT primary key ,
    major varchar(50) unique comment '专业名',
    student_count int not null comment '人数',
    college varchar(50),
    foreign key (college) references college_student_count(college)
)comment '专业人数表';
create table class_student_count(
    id int AUTO_INCREMENT primary key ,
    class varchar(50) unique comment  '班级名',
    student_count int not null comment '人数',
    major varchar(50),
    foreign key (major) references major_student_count(major)
)comment '班级人数表';
create table sex_student_count(
    id int AUTO_INCREMENT primary key ,
    sex enum('男','女') not null unique comment '性别' ,
    student_count int not null comment '人数'
)comment '性别人数表';
#学生消费信息表
create table student_consumption_statistics(
    sid char(6) primary key comment '学号',
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    foreign key (sid) references student(id)
)comment '学生消费信息';
#各个学院、专业、班级、性别消费信息表
create table college_consumption_statistics(
    id int AUTO_INCREMENT primary key ,
    college varchar(50) not null unique comment '学院',
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (college) references college_student_count(college)
)comment '学院消费信息';
create table major_consumption_statistics(
    id int AUTO_INCREMENT primary key ,
    major varchar(50) not null unique comment '专业',
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (major)references major_student_count(major)
)comment '专业消费信息';
create table class_consumption_statistics(
    id int AUTO_INCREMENT primary key ,
    class varchar(50) not null unique comment '班级',
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (class) references class_student_count(class)
)comment '班级消费信息';
create table sex_consumption_statistics(
    id int AUTO_INCREMENT primary key ,
    sex enum('男','女') not null unique comment '性别',
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (sex) references sex_student_count(sex)
)comment '性别消费信息';
#学生三餐信息统计表
create table student_three_meals_statistics(
   sid char(6) primary key comment '学号',
   consumption_category enum('早','午','晚') comment '吃饭时间段',
   consumption_count int not null comment '消费次数',
   consumption_total_money float not null comment '消费总金额',
   consumption_average_money float not null comment '消费平均金额',
   foreign key (sid) references student(id)
)comment '学生三餐信息统计';
#各个学院、专业、班级、性别三餐消费信息表
create table college_three_meals_statistics(
   id int AUTO_INCREMENT primary key ,
   college varchar(50) not null comment '学院',
   consumption_category enum('早','午','晚'),
   consumption_count int not null comment '消费次数',
   consumption_total_money float not null comment '消费总金额',
   consumption_average_money float not null comment '消费平均金额',
   consumption_student_average_money float not null comment '消费人均金额',
   foreign key (college) references college_student_count(college)
)comment '学院三餐消费信息表';
create table major_three_meals_statistics(
    id int AUTO_INCREMENT primary key ,
    major varchar(50) not null  comment '专业',
    consumption_category enum('早','午','晚'),
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (major)references major_student_count(major)
)comment '专业三餐消费信息表';
create table class_three_meals_statistics(
    id int AUTO_INCREMENT primary key ,
    class varchar(50) not null comment '班级',
    consumption_category enum('早','午','晚'),
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (class) references class_student_count(class)
)comment '班级三餐消费信息表';
create table sex_three_meals_statistics(
    id int AUTO_INCREMENT primary key ,
    sex enum('男','女') not null comment '性别',
    consumption_category enum('早','午','晚'),
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null  comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (sex) references sex_student_count(sex)
)comment '性别三餐消费信息表';
#统计商户
create table store(
    id int AUTO_INCREMENT primary key ,
    store_name varchar(50) not null unique comment '商户名字'
) comment '商户信息';
#学生在商店消费数据统计表
create table student_store_statistics(
    id int AUTO_INCREMENT primary key ,
    sid char(6) not null comment '学号',
    store_name varchar(50) not null comment '商户名称',
    consumption_count int not null comment '消费次数' ,
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    foreign key (sid) references student(id),
    foreign key (store_name) references store(store_name)
)comment '学生在商店消费数据统计';
#商店消费数据统计表
create table store_consumption_statistics(
    id int AUTO_INCREMENT primary key ,
    store_name varchar(50) not null comment '商户名字',
    consumption_count int not null comment '消费次数' ,
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额',
    foreign key (store_name) references store(store_name)
)comment '商店消费数据统计';

#学生不同时间段消费数据统计
create table time_consumption_statistics(
    id int AUTO_INCREMENT primary key ,
    start_time time comment '开始时间',
    end_time time comment '结束时间',
    time_type enum('5','10','15','30') comment '时间间隔',
    consumption_count int not null comment '消费次数',
    consumption_total_money float not null comment '消费总金额',
    consumption_average_money float not null comment '消费平均金额',
    consumption_student_average_money float not null comment '消费人均金额'
)comment '学生不同时间段消费数据统计';

#贫困生预测表
