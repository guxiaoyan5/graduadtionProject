# # create database graduate;
use graduate;
# #原始信息表
#
create table college
(
    id      int AUTO_INCREMENT primary key,
    college varchar(50) comment '学院名'
) comment '学院表';
create table major
(
    id         int AUTO_INCREMENT primary key,
    major      varchar(50) comment '专业名',
    college_id int,
    foreign key (college_id) references college (id)
) comment '专业表';
create table class
(
    id       int AUTO_INCREMENT primary key,
    class    varchar(50) comment '班级名',
    major_id int,
    foreign key (major_id) references major (id)
) comment '班级表';
create table store
(
    id         int AUTO_INCREMENT primary key,
    store_name varchar(50) not null unique comment '商户名字'
) comment '商户信息';
create table student
(
    id         char(6) primary key comment '学号',
    name       varchar(50)    not null comment '姓名',
    class_id   int            not null comment '班级',
    major_id   int            not null comment '专业',
    college_id int            not null comment '学院',
    sex        enum ('男','女') not null comment '性别',
    foreign key (class_id) references class (id),
    foreign key (major_id) references major (id),
    foreign key (college_id) references college (id)
) comment '学生信息表';
create table consume
(
    id             int AUTO_INCREMENT primary key,
    sid            char(6)  not null comment '学号',
    execution_time datetime not null comment '交易时间',#交易时间
    money          float    not null comment '交易金额',#交易金额
    store_id       int      not null comment '商户',#商户
    mode           varchar(50) comment '交易方式',
    foreign key (sid) references student (id),
    foreign key (store_id) references store (id)
) comment '消费信息表';


# create table student_day_consumption_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     day                       date comment '日期',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均次金额',
#     consumption_low_count     int comment '低于平均次金额的次数',
#     consumption_high_count    int comment '高于平均次金额的次数',
#     foreign key (sid) references student (id)
# ) comment '学生每日消费信息';
# create table student_month_consumption_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     month                     int comment '月份',
#     year                      year comment '年',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均次金额',
#     consumption_low_count     int comment '低于平均次金额的次数',
#     consumption_high_count    int comment '高于平均次金额的次数',
#     foreign key (sid) references student (id)
#
# ) comment '学生每月消费信息';
# create table student_consumption_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均次金额',
#     consumption_low_count     int comment '低于平均次金额的次数',
#     consumption_high_count    int comment '高于平均次金额的次数',
#     modify                    datetime comment '最后一次修改时间',
#     foreign key (sid) references student (id)
# ) comment '学生消费信息';
# create table student_three_meals_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     consumption_category      enum ('早','午','晚') comment '吃饭时间段',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均金额',
#     consumption_low_count     int comment '低于平均次金额的次数',
#     consumption_high_count    int comment '高于平均次金额的次数',
#     foreign key (sid) references student (id)
# ) comment '学生三餐信息统计';
# create table student_month_three_meals_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     month                     int comment '月份',
#     year                      year comment '年',
#     consumption_category      enum ('早','午','晚') comment '吃饭时间段',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均金额',
#     consumption_low_count     int comment '低于平均次金额的次数',
#     consumption_high_count    int comment '高于平均次金额的次数',
#     foreign key (sid) references student (id)
# ) comment '学生每月三餐信息统计';
# create table student_day_three_meals_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     day                       date comment '日期',
#     consumption_category      enum ('早','午','晚') comment '吃饭时间段',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均金额',
#     consumption_low_count     int comment '低于平均次金额的次数',
#     consumption_high_count    int comment '高于平均次金额的次数',
#     foreign key (sid) references student (id)
# ) comment '学生每日三餐信息统计';
#
#
#
# create table college_day_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     college_id                        int comment '学院',
#     day                               date comment '日期',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (college_id) references college (id)
#
# ) comment '学院每日消费信息';
# create table college_month_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     college_id                        int comment '学院',
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (college_id) references college (id)
# ) comment '学院每月消费信息';
# create table college_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     college_id                        int comment '学院',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (college_id) references college (id)
# ) comment '学院消费信息';
# create table college_day_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     college_id                        int comment '学院',
#     day                               date comment '日期',
#     consumption_category              enum ('早','午','晚'),
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (college_id) references college (id)
# ) comment '学院每日三餐消费信息表';
# create table college_month_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     college_id                        int comment '学院',
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_category              enum ('早','午','晚'),
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (college_id) references college (id)
# ) comment '学院每月三餐消费信息表';
# create table college_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     college_id                        int comment '学院',
#     consumption_category              enum ('早','午','晚'),
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (college_id) references college (id)
# ) comment '学院三餐消费信息表';
#
#
# create table major_day_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     major_id                          int not null comment '专业',
#     day                               date comment '日期',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (major_id) references major (id)
# ) comment '专业每日消费信息';
# create table major_month_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     major_id                          int not null comment '专业',
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (major_id) references major (id)
# ) comment '专业每月消费信息';
# create table major_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     major_id                          int not null comment '专业',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (major_id) references major (id)
# ) comment '专业消费信息';
# create table major_day_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     major_id                          int comment '专业',
#     consumption_category              enum ('早','午','晚'),
#     day                               date comment '日期',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (major_id) references major (id)
# ) comment '专业每日三餐消费信息表';
# create table major_month_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     major_id                          int comment '专业',
#     consumption_category              enum ('早','午','晚'),
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (major_id) references major (id)
# ) comment '专业每月三餐消费信息表';
# create table major_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     major_id                          int comment '专业',
#     consumption_category              enum ('早','午','晚'),
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (major_id) references major (id)
# ) comment '专业三餐消费信息表';
#
#
# create table class_day_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     class_id                          int not null comment '班级',
#     day                               date comment '日期',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (class_id) references class (id)
# ) comment '班级每日消费信息';
# create table class_month_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     class_id                          int not null comment '班级',
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (class_id) references class (id)
# ) comment '班级每月消费信息';
# create table class_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     class_id                          int not null comment '班级',
#
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (class_id) references class (id)
# ) comment '班级消费信息';
# create table class_day_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     class_id                          int not null comment '班级',
#     consumption_category              enum ('早','午','晚'),
#     day                               date comment '日期',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (class_id) references class (id)
# ) comment '班级每日三餐消费信息表';
# create table class_month_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     class_id                          int not null comment '班级',
#     consumption_category              enum ('早','午','晚'),
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (class_id) references class (id)
# ) comment '班级每月三餐消费信息表';
# create table class_three_meals_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     class_id                          int not null comment '班级',
#     consumption_category              enum ('早','午','晚'),
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     consumption_low_count             int comment '低于平均次金额的次数',
#     consumption_high_count            int comment '高于平均次金额的次数',
#     student_low_count                 int comment '低于人均金额的人数',
#     student_high_count                int comment '高于人均金额的人数',
#     foreign key (class_id) references class (id)
# ) comment '班级三餐消费信息表';
#
#
# create table student_store_month_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     store_id                  int comment '商户名称',
#     month                     int comment '月份',
#     year                      year comment '年',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均金额',
#     foreign key (sid) references student (id),
#     foreign key (store_id) references store (id)
# ) comment '学生在商店每月消费数据统计';
# create table store_month_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     store_id                          int comment '商户名字',
#     month                             int comment '月份',
#     year                              year comment '年',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     foreign key (store_id) references store (id)
# ) comment '商店消费每月数据统计';
# create table student_store_day_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     store_id                  int comment '商户名称',
#     day                       date comment '日期',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均金额',
#     foreign key (sid) references student (id),
#     foreign key (store_id) references store (id)
# ) comment '学生在商店消费数据统计';
# create table store_day_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     store_id                          int comment '商户名字',
#     day                               date comment '日期',
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     foreign key (store_id) references store (id)
# ) comment '商店消费数据统计';
# create table student_store_statistics
# (
#     id                        int AUTO_INCREMENT primary key,
#     sid                       char(6) comment '学号',
#     store_id                  int comment '商户名称',
#     consumption_count         int comment '消费次数',
#     consumption_total_money   float comment '消费总金额',
#     consumption_average_money float comment '消费平均金额',
#     foreign key (sid) references student (id),
#     foreign key (store_id) references store (id)
# ) comment '学生在商店消费数据统计';
# create table store_consumption_statistics
# (
#     id                                int AUTO_INCREMENT primary key,
#     store_id                          int comment '商户名字',
#
#     consumption_count                 int comment '消费次数',
#     consumption_total_money           float comment '消费总金额',
#     consumption_average_money         float comment '消费平均金额',
#     consumption_student_average_money float comment '消费人均金额',
#     student_count                     int comment '消费人数',
#     foreign key (store_id) references store (id)
# ) comment '商店消费数据统计';
#
# #
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

