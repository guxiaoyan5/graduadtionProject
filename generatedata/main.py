import pymysql
import random
import time
import datetime

college = ['人工智能与数据科学学院', '理学院', '外国语学院', '电气学院']
classname = {
    '人工智能与数据科学学院': ['计171', '计172', '计173', '计174'],
    '理学院': ['信计171', '信计172'],
    '外国语学院': ['英语171'],
    '电气学院': ['电气171']
}


def randomTime():
    end_time = datetime.datetime.now()
    start_time = datetime.datetime.now() + datetime.timedelta(minutes=random.randint(-30, 0),
                                                              hours=random.randint(-6, 0),
                                                              days=random.randint(-10, 0))  # 当前时间减去3分钟

    a1 = tuple(start_time.timetuple()[0:9])  # 设置开始日期时间元组（2020-04-11 16:30:21）
    a2 = tuple(end_time.timetuple()[0:9])  # 设置结束日期时间元组（2020-04-11 16:33:21）

    start = time.mktime(a1)  # 生成开始时间戳
    end = time.mktime(a2)  # 生成结束时间戳

    # 随机生成日期字符串
    t = random.randint(start, end)  # 在开始和结束时间戳中随机取出一个
    date_touple = time.localtime(t)  # 将时间戳生成时间元组
    date = time.strftime("%Y-%m-%d %H:%M:%S", date_touple)  # 将时间元组转成格式化字符串（2020-04-11 16:33:21）
    return date


if __name__ == '__main__':
    conn = pymysql.connect(host='127.0.0.1', user='root', password='guyanjie0908', database='graduate')
    cursor = conn.cursor()
    s = 0
    for i in range(1, 500):
        collegeName = random.choice(college)
        studentClassName = random.choice(classname[collegeName])
        sex = random.choice(['男', '女'])
        sid = str(100000 + i)
        name = sid
        sql = "insert into student(id,name,class, major,college,sex) values('{}','{}','{}','{}','{}','{}')".format(sid, name,
                                                                                                             studentClassName,
                                                                                                             studentClassName,
                                                                                                             collegeName,
                                                                                                             sex)
        cursor.execute(sql)
        conn.commit()
        for j in range(1, random.randint(1, 1000)):
            execution_time = randomTime()
            money = random.random() * 100
            merchant_name = random.choice(['南212', '北235', '东456','西45','北452'])
            sql = "insert into consume(sid,execution_time,money,store_name) values ('{}','{}',{},'{}')".format(sid,
                                                                                                                  execution_time,
                                                                                                                  money,
                                                                                                                  merchant_name)
            cursor.execute(sql)
            conn.commit()
        print(i)
    cursor.close()
    conn.close()
    pass
