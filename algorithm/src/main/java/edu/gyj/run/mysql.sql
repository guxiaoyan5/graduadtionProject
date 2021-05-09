create table student_month_data
as
select sid, month, year, consumption_count, consumption_total_money, consumption_average_money
from student_month_consumption_statistics
where year between 2017 and 2020;

delete
from student_month_data
where month between 7 and 8;

delete
from student_month_data
where month between 1 and 2;

select count(*)
from student_month_data