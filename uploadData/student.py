import pandas
import pymysql

df = pandas.read_excel('f:/学生基本信息.xlsx')
data = df.to_dict(orient='records')
college = set([i['院系名称'] for i in data])
major = set([(i['专业名称'], i['院系名称']) for i in data])
className = set([(i['班级名称'], i['专业名称']) for i in data])

conn = pymysql.connect(host='127.0.0.1', user='root', password='guyanjie0908', database='graduate')
cursor = conn.cursor()
for i in college:
    sql = "insert into college(college) values ('{}')".format(i)
    cursor.execute(sql)
conn.commit()
sql = 'select * from college'
cursor.execute(sql)
college = cursor.fetchall()
major = set([(i['专业名称'], j[0]) for i in data for j in college if j[1] == i['院系名称']])
for i, j in major:
    sql = "insert into major(major,college_id) values ('{}',{})".format(i, j)
    cursor.execute(sql)
conn.commit()
sql = 'select * from major'
cursor.execute(sql)
major = cursor.fetchall()
className = set([(i['班级名称'], j[0]) for i in data for j in major if i['专业名称'] == j[1]])
for i, j in className:
    sql = "insert into class(class,major_id) values ('{}',{})".format(i, j)
    cursor.execute(sql)
conn.commit()

sql = 'select * from college'
cursor.execute(sql)
college = cursor.fetchall()
sql = 'select * from major'
cursor.execute(sql)
major = cursor.fetchall()
sql = 'select * from class'
cursor.execute(sql)
className = cursor.fetchall()

for student in data:
    college_id = 0
    major_id = 0
    class_id = 0
    for j in className:
        if student['班级名称'] == j[1]:
            class_id = j[0]
            break
    major_id = className[class_id - 1][2]
    college_id = major[major_id - 1][2]
    student['class_id'] = class_id
    student['major_id'] = major_id
    student['college_id'] = college_id

for student in data:
    sql = "insert into student(id,name,class_id, major_id,college_id,sex,grade) values('{}','{}',{},{},{},'{}',{})".format(
        student['学生（已加密）'],
        student['姓名'],
        student['class_id'],
        student['major_id'],
        student['college_id'],
        student['性别'],
        student['年级']
    )
    cursor.execute(sql)
    conn.commit()
