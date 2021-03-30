import pymysql
import random
import time

college = ['人工智能与数据科学学院', '理学院', '外国语学院', '电气学院']
classname = {
    '人工智能与数据科学学院': ['计171', '计172', '计173', '计174'],
    '理学院': ['信计171', '信计172'],
    '外国语学院': ['英语171'],
    '电气学院': ['电气171']
}

if __name__ == '__main__':
    conn = pymysql.connect(host='172.18.0.2', user='root', password='guyanjie', database='graduate')
    cursor = conn.cursor()
    for i in range(1, 500):
        collegeName = random.choice(college)
        studentClassName = random.choice(classname[collegeName])
        sex = random.choice(['n', 'w'])
        sid = str(100000 + i)
        name = sid
        sql = "insert into student(id,name,class,college,sex) values('{}','{}','{}','{}','{}')".format(sid,name,studentClassName,collegeName,sex)
        cursor.execute(sql)
        conn.commit()
        for j in range(1, random.randint(1, 400)):
            execution_time = time.strftime("%Y-%m-%d %H:%M:%S", time.localtime())
            money = random.random() * 100
            merchant_name = random.choice(['212', '235', '456'])
            sql = "insert into consume(sid,execution_time,money,merchant_name) values ('{}','{}',{},'{}')".format(sid,execution_time,money,merchant_name)
            cursor.execute(sql)
            conn.commit()
    cursor.close()
    conn.close()
    pass



