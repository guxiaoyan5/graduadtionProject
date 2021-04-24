import pymysql
import random
import time
import datetime

college = ['人工智能与数据科学学院', '理学院', '外国语学院', '电气学院']
major = {
    '人工智能与数据科学学院': ['计算机科学与技术', '物联网', '软件工程', '网络工程'],
    '理学院': ['信息与科学计算', '数学', '统计', '物理'],
    '外国语学院': ['英语'],
    '电气学院': ['电气专业']
}
classname = {
    '计算机科学与技术': ['计171', '计172', '计173', '计174'],
    '物联网': ['物联网171', '物联网172'],
    '软件工程': ['软件171', '软件172'],
    '网络工程': ['网络171', '网络172'],
    '信息与科学计算': ['信计171', '信计172'],
    '数学': ['数学171'],
    '统计': ['统计171'],
    '物理': ['物理171'],
    '英语': ['英语171', '英语172'],
    '电气专业': ['电气171']
}


def random_time():
    end_time = datetime.datetime.now()
    start_time = datetime.datetime.now() + datetime.timedelta(minutes=random.randint(-10, 0),
                                                              hours=random.randint(-10, 0),
                                                              days=random.randint(-22, 0),
                                                              )

    a1 = tuple(start_time.timetuple()[0:9])
    a2 = tuple(end_time.timetuple()[0:9])

    a1 = list(a1)
    a1[2] = random.randint(1, 23)
    a1[1] = random.randint(1, 4)
    a1 = tuple(a1)

    start = time.mktime(a1)  # 生成开始时间戳
    end = time.mktime(a2)  # 生成结束时间戳

    # 随机生成日期字符串
    t = random.randint(start, end)  # 在开始和结束时间戳中随机取出一个
    date_tuple = time.localtime(t)  # 将时间戳生成时间元组
    date = time.strftime("%Y-%m-%d %H:%M:%S", date_tuple)  # 将时间元组转成格式化字符串（2020-04-11 16:33:21）
    return date


if __name__ == '__main__':
    conn = pymysql.connect(host='127.0.0.1', user='root', password='guyanjie0908', database='graduate')
    cursor = conn.cursor()
    for i in college:
        sql = "insert into college(college) values ('{}')".format(i)
        cursor.execute(sql)
    conn.commit()

    for i, j in major.items():
        for k in j:
            sql = "insert into major(major,college_id) select '{}',id from college where college='{}'".format(k, i)
            cursor.execute(sql)
    conn.commit()

    for i,j in classname.items():
        for k in j:
            sql = "insert into class(class,major_id) select '{}',id from major where major='{}'".format(k, i)
            cursor.execute(sql)
    conn.commit()

    for i in ['南212', '北235', '东456', '西45', '北452']:
        sql = "insert into store(store_name) values ('{}')".format(i)
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
    classname = cursor.fetchall()
    sql = 'select * from store'
    cursor.execute(sql)
    store = cursor.fetchall()

    s = 0
    for i in range(0, 500):
        studentClassName = random.choice(classname)
        studentMajorName = major[studentClassName[2] - 1]
        studentCollegeName = college[studentMajorName[2] - 1]
        sex = random.choice(['男', '女'])
        sid = str(100000 + i)
        name = sid
        sql = "insert into student(id,name,class_id, major_id,college_id,sex) values('{}','{}','{}','{}','{}','{}')".format(
            sid,
            name,
            studentClassName[
                0],
            studentMajorName[
                0],
            studentCollegeName[
                0],
            sex)
        cursor.execute(sql)
        conn.commit()

        for j in range(0, random.randint(1, 1000)):
            execution_time = random_time()
            money = random.random() * 100
            merchant_name = random.choice(store)
            sql = "insert into consume(sid,execution_time,money,store_id) values ('{}','{}',{},'{}')".format(sid,
                                                                                                             execution_time,
                                                                                                             money,
                                                                                                             merchant_name[
                                                                                                                 0])
            cursor.execute(sql)
            conn.commit()
        print(i)
    cursor.close()
    conn.close()
    pass
