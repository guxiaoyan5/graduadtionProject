sudo docker run \
-p 3306:3306 \
--name graduate_database \
-v /home/guxiaoyan/IdeaProjects/graduadtionProject/mysql/data/conf:/etc/mysql:rw \
-v /home/guxiaoyan/IdeaProjects/graduadtionProject/mysql/data/data:/var/lib/mysql:rw \
-v /home/guxiaoyan/IdeaProjects/graduadtionProject/mysql/data/logs:/var/log/mysql:rw \
-v /home/guxiaoyan/IdeaProjects/graduadtionProject/mysql/data/mysql-files:/var/lib/mysql-files \
-e MYSQL_ROOT_PASSWORD=guyanjie \
--network=graduate_network \
-d \
mysql
