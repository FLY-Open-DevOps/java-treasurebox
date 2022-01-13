# 什么是NoSQL？



![](https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=188078676,4140482821&fm=26&gp=0.jpg)

## 为什么要用NoSQL？

先从历史开始讲吧

> 1、单机数据库时代

90年代，一个基本的网站访问量较小，单个数据库可以顶住当时的访问量

在那个时候，都会去使用静态网页html，因为服务器压力不会太大

缺点：

- 数据量太大，一台机子放不下了
- 当数据量达到300万以上，就需要建立索引，MySQL索引，B+树。数据量一大，电脑内存也放不下了
- 在当时的数据库单机时代，读写一体，服务器承受不了

如果说以上三个条件满足了至少一个，那么就需要做出改变了

![](https://img-blog.csdnimg.cn/20201119121849610.png#pic_center)

> 2、Memcached（缓存）+ MySQL + 垂直拆分（读写分离）

网站在大部分情况下都是在读，当用户在界面中按下一个button，就会对数据库发送一个查询请求，如果说，当一个或者多个用户都在发送一个相同的请求，而这个请求每次都要查询数据库，这很耗费性能，这时候就需要减轻数据库的压力，可以使用缓存来保证效率

发展：优化数据结构和索引----->文件缓存（IO操作）----->Memcached（当时的热门技术）

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119121950571.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

> 3、分库分表 + 水平拆分 + MySQL集群

技术与业务在发展的同时对人开始有越来越高的要求

本质都是在解决数据库的读和写问题

以MySQL的存储引擎为例

- MyISAM：这个存储引擎支持表锁，并且不支持事务的ACID，影响操作，在高并发下会出现严重的锁问题
- InnoDB：行锁+表锁，支持事务的ACID

慢慢的由于数据量的增大，慢慢的开始使用分库分表解决写压力！在当时MySQL还推出了一个 *表分区* 的概念，但是并没有多少的公司愿意去使用

MySQL集群的解决方案，已经在当时解决了大部分的需求

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122047240.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

> 4、如今最近的年代

2010-2020，过了十年，世界发生了巨大变化，从按键手机到智能手机，定位，也成为了一种数据

MySQL等关系型数据库开始出现性能瓶颈！大数据，数据量很多，变化很快

MySQL可以用来存储一些比较大的文件，博客，图片！数据库表很大，执行IO操作的效率就会很低下，如果有一种数据库专门用来处理这种数据，就可以用来缓解MySQL的压力（如何处理这些问题）

> 为什么要用NoSQL

用户个人信息，社交网络，地理位置，用户自己产生的信息数据，等等一系列的弹性数据爆发式增长，关系型数据的传统的表结构已经承载不了了，这时候就需要使用NoSQL数据库来解决，NoSQL可以很好处理以上情况

## NoSQL简介

什么是NoSQL

NoSQL翻译为Not Only SQL，译为**不仅仅是SQL**，意指**非关系型数据库**

web2.0的诞生，传统的关系型数据库已经很难对付web2.0时代！特别是指大规模高并发社区！会出现很多问题，NoSQL在大数据时代发展的十分迅速，尤其是Redis

很多的数据类型用户的个人信息，社交网络，地理位置，这些数据类型的存储不需要一个固定的格式，不需要太多操作就可以实现横向拓展，就比如Redis，它是使用类似于Java的Map<String, Object>来实现存储，键值对的形式存储，这只是NoSQL的解决方式之一

> NoSQL 特点

解耦

1. 方便扩展（数据之间没有联系可以快速拓展）

2. 大数据量高性能，Redis可以支持8w的并发量（写操作），11w访问量（读操作），NoSQL的缓存记录级，是一种细粒度的缓存，性能比较高

3. 数据类型多样性（不需要事先设计数据库，随取随用，数据量过大就无法设计）

4. 传统的关系数据库管理系统（Relational Database Management System：*RDBMS*）和NoSQL的区别

   传统的RDBMS

   - 结构化
   - SQL
   - 数据和关系存在于单独的表中 row（行） column（列）
   - 数据操作，数据定义语言
   - 严格的一致性
   - 基础的事务

   NoSQL

   - 不仅仅是数据
   - 没有固定的查询语言
   - 键值对存储，列存储，文档存储，图形数据库
   - 最终一致性
   - CAP定律和BASE理论

> 大数据时代的3V + 3高

大数据时代的3V

1. 海量Volume
2. 多样Variety
3. 实时Velocity

大数据时代的3高

1. 高并发
2. 高可用（随时水平拆分，机器不够了，随时扩展）
3. 高性能（保证用户体验和性能）

真正在公司中用到的实践，NoSQL + 关系型数据库，这是最强组合，也是阿里巴巴的架构演进

### 阿里巴巴架构演进

[PDF](https://docs.huihoo.com/infoq/qcon-alibaba-chiness-architecture-design-practice-20111021.pdf)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122135351.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122214516.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

没有什么是加一层解决不了的，如果有，就加两层

```bash
# 商品的基本信息
名称、价格、商家信息
MySQL / Oracle 去IOE化（IOE：IBM、Oracle、EMC存储设备）

# 商品描述
评论，文本信息多
文档型数据库，MongoDB

# 图片
分布式文件系统 FastDFS
淘宝：TFS
Google：GFS
Hadoop：HDFS
阿里云：OSS

# 商品关键字（搜索）
搜索引擎 solr elasticsearch
淘宝：ISearch，ISearch作者，阿里的多隆

# 商品热门波段信息
内存数据库
Redis  Tair  Memcached...

# 商品交易，外部支付接口
第三方应用
```

推荐文章：https://developer.aliyun.com/article/653511

大型互联网应用

- 数据类型多样！
- 数据源多样，频繁重构！
- 数据改造，大面积改动！

解决问题

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122243761.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

热点缓存

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122302616.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

### NoSQL的四大分类

**KV键值对**

- 新浪：Redis
- 美团：Redis + Tair
- 阿里，百度：Redis + Memcached

**文档型数据库（Bson，Binary Json，二进制Json）**

- MongoDB，需要掌握，它是一种基于分布式文件存储的数据库，由C++编写，主要用来处理大量的文档

  它是一种介于关系型数据库和非关系型数据库之间的一种中间产品，功能丰富，而且MongoDB是NoSQL中最像关系型数据库的产品

- ConthDB

**列存储数据库**

- HBASE
- 分布式文件系统

**图形关系数据库**

- 它不是存图片的！它存放的是关系，就好比一个人的社交圈，可以动态扩充
- Neo4j，InfoGrid

> 4种分类的对比

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122339567.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

# Redis入门

> 本次使用的Redis的版本采用的是5.0.10

## Redis简介

> 什么是Redis？

Redis（Remote Directory Server），中文译为**远程字典服务**，免费开源，由C语言编写，支持网络，可基于内存也可持久化的日志型，KV键值对数据库，并且提供多种语言的API，是当下NoSQL中最热门的技术之一！被人们称之为结构化数据库！

并且Redis支持多种语言（如下图）

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122420819.png#pic_center)

redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步

> Redis能干嘛？

1. 内存存储，持久化，因为内存断电即失，并且Redis支持两种持久化方式，RDB / AOF

2. 效率高，可用于高速缓存

3. 消息发布订阅（消息队列）

4. 地图信息分析

5. 计数器（eg：微博浏览量）

   …

> 特性

1. 数据类型多样

2. 持久化

3. Redis集群

4. 事务

   …

> 配置（在哪下）

官网：https://redis.io/

Redis中文文档：http://www.redis.cn/documentation.html

下载地址：进入官网下载即可（Windows版本需要在GitHub上下载，并且Redis版本已停更较长时间，不建议使用）

并且，Redis官方推荐在Linux服务器上进行搭建

## Linux安装Redis

安装Redis的第一种，官网下载安装包

1. 下载安装包，redis-5.0.10.tar.gz

2. 下载到Windows之后，用Xftp工具上传至Linux

3. 解压安装包并将其解压到opt目录下

   ```shell
   tar -zxvf redis-5.0.10.tar.gz -C /opt 
   ```

并且解压之后可以看见Redis的配置文件redis.conf

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122507548.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

同时还需要基本的环境搭建

```shell
# 保证Redis的正常运行，gcc的安装也是必要的
yum install gcc-c++
# 安装Redis所需要的环境
make
# 此命令只是为了确认当前所有环境全部安装完毕，可以选择不执行
make install 
新版本已经是6.0.8了 Redis6以上需要gcc版本在7以上。
运行下面命令升级gcc
#第一步
sudo yum install centos-release-scl
#第二步
sudo yum install devtoolset-7-gcc*
#第三步
scl enable devtoolset-7 bash
```

Redis的安装，默认在/usr/local/bin下

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122530504.png#pic_center)

之后，需要将Redis的配置文件复制到bin目录下，可以提前准备好一个目录，然后在复制到新创建好的目录中

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122548302.png#pic_center)

然后修改复制之后的配置文件，修改一条信息，修改的信息就是图中划红线的位置，它的意思是指守护进程模式启动，即可以在后台运行Redis

![](https://img-blog.csdnimg.cn/20201119122617116.png#pic_center)

随后就可以开始启动Redis服务（通过指定的配置文件启动服务）

```shell
redis-server /usr/local/bin/myconfig/redis.conf
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122654237.png#pic_center)

使用Redis客户端连接指定的端口号

```shell
redis-cli -p 6379
```

## redis-benchmark性能测试工具

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122718408.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

```shell
# 当前命令表示，性能测试，在本机，端口号6379，并发连接数100，每个连接10w个请求数量
redis-benchmark -h localhost -p 6379 -c 100 -n 100000
# 测试结果如下，以Redis的set命令为例
====== SET ======
  100000 requests completed in 1.85 seconds # 十万个请求在1.85秒之内被处理
  100 parallel clients # 每次请求都有100个客户端在执行
  3 bytes payload # 一次处理3个字节的数据
  keep alive: 1 # 每次都保持一个服务器的连接，只用一台服务器处理这些请求

28.68% <= 1 milliseconds
97.99% <= 2 milliseconds
99.47% <= 3 milliseconds
99.59% <= 4 milliseconds
99.62% <= 5 milliseconds
99.68% <= 6 milliseconds
99.79% <= 7 milliseconds
99.90% <= 22 milliseconds
99.97% <= 23 milliseconds
100.00% <= 23 milliseconds # 所有的请求在23秒之内完成
54054.05 requests per second # 平均每秒处理54054.05个请求
```

## Redis基础知识

> 备注：在Redis中，关键字语法不区分大小写！

**Redis有16个数据库支持**，为啥嘞，可以查看redis.conf配置文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119122754945.png#pic_center)

并且初始数据库默认使用0号数据库（16个数据库对应索引0到15）

可以使用select命令切换数据库：select n（0-15）

```shell
127.0.0.1:6379> SELECT 12
OK
127.0.0.1:6379[12]> SELECT 0
OK
127.0.0.1:6379> DBSIZE # 查看当前库的key数量
(integer) 0
```

删除数据库信息

```shell
127.0.0.1:6379> keys * 
1) "mylist"
2) "myset:__rand_int__"
3) "counter:__rand_int__"
4) "key:__rand_int__"
127.0.0.1:6379> FLUSHDB # 删除当前数据库
OK
127.0.0.1:6379> keys * 
(empty list or set)
# 还有一个删除的命令，叫做FLUSHALL，它的意思是删除16个数据库中的全部信息。
# 不管在那种数据库中，删库一直都是需要慎重操作的
```

题外话：为什么Redis选用6379作为默认端口号？

6379在是手机按键上MERZ对应的号码，而MERZ取自意大利歌女Alessia Merz的名字。MERZ长期以来被Redis作者antirez及其朋友当作愚蠢的代名词。后来Redis作者在开发Redis时就选用了这个端口。（摘自知乎）

> Redis是单线程的（从Redis6.0.1开始支持多线程）

Redis的读写速度很快，官方表示，Redis基于内存操作，CPU不是Redis的性能瓶颈，Redis的性能瓶颈是根据机器的内存和带宽

Redis是C语言编写，官方提供的数据为10万+的QPS（Queries-Per-Second，每秒内的查询次数）

> Redis单线程为什么速度还是这么快？

对于Redis，有两个误区：

1. 高性能的服务器一定是多线程的？
2. 多线程一定比单线程效率高？

Redis将所有的数据全部放在内存中，使用单线程去操作效率比较高，对于多线程，CPU有一种东西叫做*上下文切换*，这种操作耗时，对于内存系统来说，没有上下文切换，效率一定是最高的。

Redis使用单进程的模式来处理客户端的请求，对大部分事件的响应都是通过epoll函数的加强封装，Redis的实际处理速度依靠主进程的执行效率，epoll可以显著提高程序在大量并发连接中系统的CPU利用率

# Redis五大数据类型

Redis中文网翻译：

Redis 是一个开源（BSD许可）的，内存中的数据结构存储系统，它可以用作**数据库**、**缓存**和**消息中间件**。 它支持多种类型的数据结构，如 字符串（strings）， 散列（hashes）， 列表（lists）， 集合（sets）， 有序集合（sorted sets） 与范围查询， bitmaps， hyperloglogs 和 地理空间（geospatial） 索引半径查询。 Redis 内置了 复制（replication），LUA脚本（Lua scripting）， LRU驱动事件（LRU eviction），事务（transactions） 和不同级别的 磁盘持久化（persistence）， 并通过 Redis哨兵（Sentinel）和自动 分区（Cluster）提供高可用性（high availability）。

## Redis-key

```shell
# 基础语法：
# SET key value	          设置一个key
# GET key                 获取一个key对应value
# EXISTS key              查询key是否存在
# MOVE key n（数字）	     将当前key移动到指定的几号数据库中
# KEYS *                  查询当前数据库中全部的key
# EXPIRE key time         设置当前key的过期时间
# TTL key                 查询当前key的存活时间
# TYPE key                查看key的数据类型
127.0.0.1:6379> set name xiaohuang # 设置key-value
OK
127.0.0.1:6379> get name # 查询key指定的value
"xiaohuang"
127.0.0.1:6379> EXISTS name # 查看当前key是否存在
(integer) 1
127.0.0.1:6379> EXISTS name1
(integer) 0
127.0.0.1:6379> MOVE name 1 # 将当前key移动到1号数据库
(integer) 1
127.0.0.1:6379> KEYS *
(empty list or set)
127.0.0.1:6379> SELECT 1 # 选择数据库
OK
127.0.0.1:6379[1]> KEYS *
1) "name"
127.0.0.1:6379[1]> EXPIRE name 10 # 设置当前key的过期时间，单位是秒
(integer) 1
127.0.0.1:6379[1]> KEYS *
1) "name"
127.0.0.1:6379[1]> KEYS *
1) "name"
127.0.0.1:6379[1]> KEYS *
1) "name"
127.0.0.1:6379[1]> KEYS *
1) "name"
127.0.0.1:6379[1]> ttl name # 查看指定key的存活时间，
(integer) -2 # 返回-2表示当前key已经过期，如果为-1，表示永不过期
127.0.0.1:6379[1]> KEYS *
(empty list or set)
127.0.0.1:6379> set name xiaojiejie
OK
127.0.0.1:6379> set age 26
OK
127.0.0.1:6379> KEYS *
1) "age"
2) "name"
127.0.0.1:6379> TYPE name # 查看指定key的value的数据类型
string
127.0.0.1:6379> TYPE age
string
127.0.0.1:6379> 
```

## String（字符串）

```shell
# 语法：
# APPEND key appendValue                 对指定key实现字符串拼接，如果key不存在，等同于set
# STRLEN key                             查看指定key的长度
# INCR key                               对指定key进行自增，类似于Java中的i++
# DECR key                               自减，类似于Java的i--
# INCRBY key n                           对指定key按照指定的步长值进行自增
# DECRBY key n											   按照指定的步长值自减
# SETRANGE key index value               从指定key的索引开始，插入指定的value值。
# 																    如果key不存在且索引>1,那么当前的索引之前的数据，会用\x00代替并占用一个索引位置，相当于ASCII码中的null
# GETRANGE key startIndex endIndex			将指定key按照索引的开始和结束范围进行截取，成为一个新的key
# SETEX key time value							 	 设置一个有存活时间的key
# SETNX key value                     	如果这个key不存在，即创建
# MSET key value ...								 	 设置多个key value
# MGET key ...												获取多个key指定的value
# GETSET key value                    	先获取指定的key，然后再设置指定的value
127.0.0.1:6379> set k1 v1
OK
127.0.0.1:6379> get k1
"v1"
127.0.0.1:6379> KEYS * # 获取当前数据库中所有的key
1) "k1"
127.0.0.1:6379> APPEND k1 hello #给k1再继续追加value值
(integer) 7
127.0.0.1:6379> get k1
"v1hello"
127.0.0.1:6379> APPEND k1 ,xiaohuang
(integer) 17
127.0.0.1:6379> STRLEN k1 # 查看当前k1的长度
(integer) 17
127.0.0.1:6379> get k1
"v1hello,xiaohuang"
127.0.0.1:6379> KEYS * 
1) "k1"
127.0.0.1:6379> APPEND name xiaohuang
(integer) 9
127.0.0.1:6379> get name
"xiaohuang"
127.0.0.1:6379> KEYS *
1) "k1"
2) "name"
###################实现自增自减效果################
127.0.0.1:6379> set views 0
OK
127.0.0.1:6379> get views
0
127.0.0.1:6379> INCR views # 设置value的自增效果
(integer) 1
127.0.0.1:6379> INCR views 
(integer) 2
127.0.0.1:6379> get views
"2"
127.0.0.1:6379> DECR views # 设置value的自减效果
(integer) 1
127.0.0.1:6379> DECR views
(integer) 0
127.0.0.1:6379> get views
"0"
############可以在自增自减时设置步长##############
127.0.0.1:6379> INCRBY views 2 # 自增，设置步长为2
(integer) 2
127.0.0.1:6379> INCRBY views 2
(integer) 4
127.0.0.1:6379> get views
"4"
127.0.0.1:6379> DECRBY views 3 # 自减，设置步长为3
(integer) 1
127.0.0.1:6379> DECRBY views 3
(integer) -2
127.0.0.1:6379> get views
"-2"
# 注意：value的自增和自减只适用于Integer类型
127.0.0.1:6379> incr name 
(error) ERR value is not an integer or out of range
#################实现字符串截取效果#################
127.0.0.1:6379> set k1 hello,xiaohuang
OK
127.0.0.1:6379> get k1
"hello,xiaohuang"
127.0.0.1:6379> GETRANGE k1 0 3 # 实现字符串截取，有起始索引和结束索引，相当于Java中的subString()
"hell"
# 如果结束索引为-1，则表示当前截取的字符串为全部
127.0.0.1:6379> GETRANGE k1 0 -1
"hello,xiaohuang"
###############实现字符串的替换效果#################
127.0.0.1:6379> set key2 abcdefg
OK
127.0.0.1:6379> get key2
"abcdefg"
127.0.0.1:6379> SETRANGE key2 2 hello # 实现字符串的替换效果，命令中的数字“2”表示从索引2的位置开始将其替换为指定字符串
(integer) 7
127.0.0.1:6379> get key2
"abhello"
##################################################
# setex(set with expire) # 设置过期时间
# setnx(set with not exist) # 如果key不存在，创建（分布式锁中常用）
127.0.0.1:6379> setex k3 10 v3 # 设置一个k3，过期时间为10秒
OK
127.0.0.1:6379> keys *
1) "k1"
2) "key2"
3) "name"
4) "views"
5) "k3"
# 10秒之后会自动删除
127.0.0.1:6379> keys *
1) "k1"
2) "key2"
3) "name"
4) "views"
127.0.0.1:6379> setnx lan redis # 如果key不存在，即创建
(integer) 1 
127.0.0.1:6379> setnx lan mongodb
(integer) 0 # 0表示没有设置成功，也可理解为“有0行受到影响”
127.0.0.1:6379> get lan
"redis"
######################一次性设置（获取）多个键值对#####################
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3 # 同时设置多个值
OK
127.0.0.1:6379> KEYS * 
1) "k2"
2) "k1"
3) "k3"
127.0.0.1:6379> mget k1 k2 k3 # 同时获取多个值
1) "v1"
2) "v2"
3) "v3"
# 也可以在这边的语法前面加上一个m，代表设置多个
127.0.0.1:6379> msetnx k1 vv1 k4 v4 
(integer) 0
# 但是这边同时设置多个值，如果有一个key已经存在，那么这一条设置语句便不会执行成功，
# 因为Redis单条语句是原子操作，要么同时成功，要么同时失败
127.0.0.1:6379> keys * 
1) "k2"
2) "k1"
3) "k3"
# 在Redis中，还推荐了一个比较有意思的东西
# 这是Redis中关于key的命名，可以用“:”来代表层次结构，可以对指定的key进行分类存储
127.0.0.1:6379> mset user:1:name xiaohuang user:1:age 21
OK
127.0.0.1:6379> mget user:1:name user:1:age
1) "xiaohuang"
2) "21"
127.0.0.1:6379> getset sqlan redis # 先获取当前key指定的value，如果不存在，会返回nil（null），然后设置新值
(nil)
127.0.0.1:6379> get sqlan
"redis"
127.0.0.1:6379> getset sqlan hbase
"redis"
127.0.0.1:6379> get sqlan
"hbase"
####################################################################
```

类似于Redis中String这样的使用场景，value值可以是字符串，也可以是其他类型

**String的存储的字符串长度最大可以达到512M**

主要用途

- 计数器
- 统计多单位的数量
- 一个用户的粉丝数
- 一个有过期时间的验证码

## List（列表）

Redis中的List列表可以做很多事情，可以将其看成数据结构中的栈，也可以是队列，或者阻塞队列

```shell
# 命令：
# LPUSH key value1 value2 ...                      设置一个key，从头部插入数据（头插法）
# RPUSH key value1 value2 ...										 设置一个key，从尾部插入数据（尾插法）
# LRANGE key startIndex endIndex                   返回列表中从开始索引到结束索引位置的value值
# LPOP key                                         从key头部弹出一个value
# RPOP key                                         从尾部弹出一个value
# LINDEX index                                     返回key中指定索引的value值
# LREM key n value                                 删除key中的指定的value值，n代表删除几个
# LLEN key                                         返回key的长度
# LTRIM key startIndex endIndex                    截取key，截取的范围从开始索引到结束索引
# LSET key index value                             从当前key的索引开始插入指定的value值
# RPOPLPUSH key1 key2                              从key1的尾部弹出一个元素，将此元素从key2的头部插入
# LINSERT key BEFORE|AFTER oldValue newValue       从指定key中已存在的value的前面或者后面插入一个指定的value
127.0.0.1:6379> LPUSH list one # 从list头部插入一个或者多个元素（从左边插入，看命令首字母）
(integer) 1
127.0.0.1:6379> LPUSH list two
(integer) 2
127.0.0.1:6379> LPUSH list three
(integer) 3
# 返回存储在列表中指定范围的元素，0和-1代表开始索引和结束索引，
# -1不代表实际位置的索引，它表示需要返回到这个列表的最后一个元素
127.0.0.1:6379> LRANGE list 0 -1 
1) "three"
2) "two"
3) "one"
127.0.0.1:6379> LRANGE list 0 1 # 返回list中指定位置的元素
1) "three"
2) "two"
127.0.0.1:6379> RPUSH list right # 从list尾部插入一个或多个元素（从右边插入，同第一行一样）
(integer) 4
127.0.0.1:6379> LRANGE list 0 -1
1) "three"
2) "two"
3) "one"
4) "right"
127.0.0.1:6379> LPOP list # 从list头部（左）弹出（删除）一个元素
"three"
127.0.0.1:6379> RPOP list # 从list末尾（右）弹出一个元素
"right"
127.0.0.1:6379> LRANGE list 0 -1
1) "two"
2) "one"
######################################################################################################################
127.0.0.1:6379> LRANGE list 0 -1
1) "two"
2) "one"
127.0.0.1:6379> LINDEX list 0 # 从头部查询list中指定索引的元素
"two"
127.0.0.1:6379> LINDEX list 1 
"one"
######################################################################################################################
127.0.0.1:6379> lrange list 0 -1 
1) "three"
2) "two"
3) "one"
127.0.0.1:6379> LLEN list # 返回list的长度
(integer) 3
127.0.0.1:6379> LPUSH list three
(integer) 4
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "three"
3) "two"
4) "one"
# ================================================================================================================================
# 表示删除key中的value值
# 语法为 LREM key 删除个数 value
# ================================================================================================================================
127.0.0.1:6379> LREM list 1 three # 删除list中的1个three
(integer) 1
127.0.0.1:6379> lrange list 0 -1
1) "three"
2) "two"
3) "one"
##################信息截取操作#########################################################################################
127.0.0.1:6379> LRANGE mylist 0 -1 
1) "zero"
2) "one"
3) "two"
4) "three"
5) "four"
127.0.0.1:6379> LTRIM mylist 0 2 # 截取mylist列表，只保留从开始索引到结束索引的元素 
OK
127.0.0.1:6379> LRANGE mylist 0 -1 
1) "zero"
2) "one"
3) "two"
127.0.0.1:6379> 
######################################################################################################################
# 复杂命令：rpoplpush
# 语法：rpoplpush source（源列表，必须存在） destination（目标列表，如不存在，即创建）
127.0.0.1:6379> LRANGE mylist 0 -1
1) "zero"
2) "one"
3) "two"
4) "three"
5) "four"
# 表示移除mylist中的最后一个元素，将这个被删除掉的元素从头部进入一个list
127.0.0.1:6379> RPOPLPUSH mylist list 
"four"
127.0.0.1:6379> lrange list 0 -1
1) "four"
127.0.0.1:6379> lrange mylist 0 -1
1) "zero"
2) "one"
3) "two"
4) "three"
######################################################################################################################
# lset 命令，类似于关系型数据库中的Update语句，将指定索引的value值修改为其他的指定value值
127.0.0.1:6379> EXISTS list # 判断key是否存在
(integer) 0
127.0.0.1:6379> lset list 0 item # 如果当前这个key不存在，无法修改，报错
(error) ERR no such key
127.0.0.1:6379> LPUSH list v1 
(integer) 1
127.0.0.1:6379> LRANGE list 0 0
1) "v1"
127.0.0.1:6379> lset list 0 item01 # lset命令只对已存在的列表操作才是有效的
OK
127.0.0.1:6379> LRANGE list 0 0
1) "item01"
127.0.0.1:6379> lset list 1 item02 # 如果要更新的索引超过列表的长度，那么它也会报错
(error) ERR index out of range
######################################################################################################################
# linsert 命令，从列表中的某一个value值的前面或后面插入一个新的value值
# 语法：linsert key before|after 列表中的value值 新value值
127.0.0.1:6379> LPUSH mylist hello 
(integer) 1
127.0.0.1:6379> LPUSH mylist xiaohuang
(integer) 2
127.0.0.1:6379> LRANGE mylist 0 -1 
1) "xiaohuang"
2) "hello"
127.0.0.1:6379> LINSERT mylist after hello world 
(integer) 3
127.0.0.1:6379> LRANGE mylist 0 -1 
1) "xiaohuang"
2) "hello"
3) "world"
127.0.0.1:6379> LINSERT mylist before xiaohuang nihao
(integer) 4
127.0.0.1:6379> LRANGE mylist 0 -1 
1) "nihao"
2) "xiaohuang"
3) "hello"
4) "world"
```

**List列表实际上它是一个数据结构的链表**

- 可以在Node节点的before或者after，left或者right插入值
- key不存在，创建新链表
- key存在，新增内容
- 如果移除了所有了value，也代表不存在
- 在Node节点的两边插入，效率最高！中间元素效率较低

Redis中可以将这个列表灵活的使用

栈（lpush lpop | rpush rpop），队列（lpush rpop | rpush lpop）

## set（集合）

set集合无序不重复

```shell
# 命令：
# SADD key value1 value2 ...          设置一个key
# SMEMBERS key                        查看当前key
# SISMEMBER key value                 查看key中指定的value是否存在
# SCARD key                           查看key的长度
# SREM key value                      删除key中的指定value
# SPOP key                            随机删除key中的一个value
# SRANDMEMBER key [n]                 随机查看当前key中的一个或者多个value
# SMOVE key1 key2 key1Value           将key1中的value移动到key2中
# SDIFF key1 key2                     两个key相交，求第一个key的补集
# SINTER key1 key2                    两个key相交，求交集
# SUNION key1 key2                    两个key相交，求并集
127.0.0.1:6379> SADD myset hello # myset集合添加元素
(integer) 1
127.0.0.1:6379> SADD myset xiaohuang
(integer) 1
127.0.0.1:6379> SADD myset love
(integer) 1
127.0.0.1:6379> SMEMBERS myset # 查看myset的所有值
1) "love"
2) "hello"
3) "xiaohuang"
127.0.0.1:6379> SISMEMBER myset hello # 判断hello是否在set集合中
(integer) 1 
127.0.0.1:6379> SISMEMBER myset world
(integer) 0 
127.0.0.1:6379> SADD myset hello # 如果往set集合中添加一个重复的值，不会报错，但是也不会插入成功，因为set集合无序且不重复
(integer) 0 
127.0.0.1:6379> SCARD myset # 查看myset集合中的元素个数
(integer) 3
127.0.0.1:6379> SREM myset hello # 删除set集合中hello
(integer) 1
127.0.0.1:6379> SMEMBERS myset
1) "love"
2) "xiaohuang"
######################################################################################################################
# 在一个无序集合中，随机抽取一个数
127.0.0.1:6379> SMEMBERS nums
1) "1"
2) "2"
3) "3"
4) "4"
5) "5"
6) "6"
7) "7"
8) "8"
9) "9"
127.0.0.1:6379> SRANDMEMBER nums # 随机抽取
"8"
127.0.0.1:6379> SRANDMEMBER nums
"7"
127.0.0.1:6379> SRANDMEMBER nums
"5"
127.0.0.1:6379> SRANDMEMBER nums 2 # 随机抽取指定个数的元素 
1) "5"
2) "4"
######################################################################################################################
# 删除指定key，随机删除key
# 以上面的数字集合为例
127.0.0.1:6379> SPOP nums # 随机删除一个set集合元素
"5"
127.0.0.1:6379> SPOP nums
"8"
127.0.0.1:6379> SPOP nums
"7"
127.0.0.1:6379> SMEMBERS nums
1) "1"
2) "2"
3) "3"
4) "4"
5) "6"
6) "9"
######################################################################################################################
# 一个set集合中的值移动到另外一个set集合中
# 语法： smove source(源set集合，必须存在) destination（目标集合，被添加元素） value（必须是源set集合中存在的value）
127.0.0.1:6379> SMEMBERS set01
1) "hello"
2) "xiaohuang"
3) "world"
127.0.0.1:6379> SMEMBERS set02
1) "me"
127.0.0.1:6379> SMOVE set01 set02 xiaohuang # 将set01中的xiaohuang放入到set02中
(integer) 1
127.0.0.1:6379> SMEMBERS set02
1) "xiaohuang"
2) "me"
127.0.0.1:6379> SMEMBERS set01
1) "hello"
2) "world"
######################################################################################################################
# 生活中的一个小现象，就比如说微信公众号，会有共同关注，还有QQ的共同好友
# 数学集合关系中的：交、并、补。微信公众号中的共同关注，以及QQ的共同好友，就是关系中的交！
127.0.0.1:6379> SMEMBERS k1
1) "b"
2) "c"
3) "a"
127.0.0.1:6379> SMEMBERS k2
1) "e"
2) "d"
3) "c"
# 上面的两个集合都有c这个元素
127.0.0.1:6379> SDIFF k1 k2 # k1 与 k2 之间的差集（以k1为主）
1) "b"
2) "a"
127.0.0.1:6379> SINTER k1 k2 # k1 和 k2 之间的交集，公众号的共同关注，QQ中的共同好友就可以这么来实现
1) "c"
127.0.0.1:6379> SUNION k1 k2 # k1 和 k2 之间的并集
1) "c"
2) "a"
3) "e"
4) "b"
5) "d"
# 在多聊一嘴，其实这些命令都可以在英语单词中找到一些规律
# 把SDIFF、SINTER还有SUNION这三个单词首字母去掉，可以得到
# DIFF：different，它代表不同的，用一句Redis官网的翻译来描述：返回的集合元素是第一个key的集合与后面所有key的集合的差集
# INTER：intersection，翻译过来为交叉，同样的，意指数学关系中的交集
# UNION：union，翻译为联合，与数学关系中的并集也是可以沾边的
######################################################################################################################
```

这里的命令，实际上也可以和生活中的东西都有关系，上面提到的共同关注，共同爱好，还有QQ当中的同一个星座，或者是“二度好友”，什么是二度好友，就是类似于QQ当中给你推荐QQ用户的意思，起源于六度分割理论（什么是六度分割理论，简单地说：就是你和任何一个陌生人之间所间隔的人不会超过五个，最多通过六个人你就能够认识任何一个陌生人）

## Hash（哈希）

Redis中的哈希，本质上KV相同但是KV中的V，它也是一个键值对，本质和操作字符串区别不大

```shell
# 命令：
# HSET key field value                 设置单个hash
# HGET key field                       获取单个
# HMSET key field1 v1 field2 v2        设置多个
# HMGET key field                      获取多个
# HGETALL key                          获取hash中全部的field-value
# HLEN key                             获取hash长度
# HEXISTS key field                    查询hash中指定的field是否存在
# HKEYS key                            只获取hash中的field
# HVALS key                            只获取hash中value
# HINCRBY key field n                  对hash中指定的field设置自增自减
127.0.0.1:6379> HSET myhash k1 v1 
(integer) 1
127.0.0.1:6379> HGET myhash k1
"v1"
127.0.0.1:6379> HMSET myhash k2 v2 k3 v3 k4 v4 
OK
127.0.0.1:6379> hmget myhash k1 k2 k3 k4
1) "v1"
2) "v2"
3) "v3"
4) "v4"
127.0.0.1:6379> HGETALL myhash # 获取myhash中全部的kv键值对
1) "k1"
2) "v1"
3) "k2"
4) "v2"
5) "k3"
6) "v3"
7) "k4"
8) "v4"
127.0.0.1:6379> HDEL myhash k4 # 删除myhash一个指定的键值对元素
(integer) 1
127.0.0.1:6379> HGETALL myhash
1) "k1"
2) "v1"
3) "k2"
4) "v2"
5) "k3"
6) "v3"
######################################################################################################################
127.0.0.1:6379> HLEN myhash # 获取当前hash中的value长度
(integer) 3
127.0.0.1:6379> HEXISTS myhash k1 # 判断当前hash中的kv键值对是否存在
(integer) 1
127.0.0.1:6379> HEXISTS myhash k4
(integer) 0
127.0.0.1:6379> HKEYS myhash # 只获取hash中的key
1) "k1"
2) "k2"
3) "k3"
127.0.0.1:6379> HVALS myhash # 只获取hash中key对应的value
1) "v1"
2) "v2"
3) "v3"
######################################################################################################################
# 备注：Redis中，有自增，没有自减，即使是没有自减，也可以在自增的步长当中设置一个负数即可
# hincrby key field 步长值
127.0.0.1:6379> hset myhash num 3
(integer) 1
127.0.0.1:6379> HINCRBY myhash num 2
(integer) 5
127.0.0.1:6379> HINCRBY myhash num 2
(integer) 7
127.0.0.1:6379> HSETNX myhash k4 v4 # 如果hash中的一个元素不存在，即可创建
(integer) 1
127.0.0.1:6379> HSETNX myhash k4 vv4 # 存在，即创建失败
(integer) 0
######################################################################################################################
# 可以使用hash做一些临时变更的数据，可以是用户信息，或者是经常变动的信息
# 上面的String也提到了使用“:”进行层次分割，不过hash更适合对象存储，String适合于文本的存储
127.0.0.1:6379> HMSET user:1 name xiaohuang age 21 sex boy
OK
127.0.0.1:6379> HGETALL user:1
1) "name"
2) "xiaohuang"
3) "age"
4) "21"
5) "sex"
6) "boy"
```

## Zset（有序集合）

在set的基础上增加了一个score的值，相当于zset k1 score v1，使用score来对当前key中元素进行排序

```shell
# 命令：
# ZADD key score1 value1 score2 value2 ...              zset中添加一个或多个元素
# ZRANGE key startIndex endIndex                        查询从开始到结束索引的zset集合
# ZRANGEBYSCORE key min max [WITHSCORES]                对hash中按照指定数值进行升序排列
# ZREVRANGE key startIndex endIndex                     对指定开始和结束索引进行降序排列
# ZREM key field                                        删除hash中指定的field
# ZCARD key                                             查询hash长度
# ZCOUNT key [min max]                                  查询hash数量，还可以增加最大值和最小值的范围
127.0.0.1:6379> ZADD myset 1 one
(integer) 1
127.0.0.1:6379> ZADD myset 2 two # zset集合添加一个值
(integer) 1
127.0.0.1:6379> ZRANGE myset 0 -1
1) "one"
2) "two"
127.0.0.1:6379> ZADD myset 3 three 4 four
(integer) 2
127.0.0.1:6379> ZRANGE myset 0 -1 
1) "one"
2) "two"
3) "three"
4) "four"
######################################################################################################################
# 实现元素的排序
# 根据zset中score的值来实现元素的排序
127.0.0.1:6379> ZADD salary 3500 xiaohong 6500 xiaohuang 3900 zhangsan
(integer) 3
# 当前命令，inf在Unix系统中代表的意思是无穷，所以当前命令是指，将当前zset，以从小到大的形式进行排列
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf 
1) "xiaohong"
2) "zhangsan"
3) "xiaohuang"
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf withscores # 在排列的同时，将score和指定的元素全部展示
1) "xiaohong"
2) "3500"
3) "zhangsan"
4) "3900"
5) "xiaohuang"
6) "6500"
127.0.0.1:6379> ZREVRANGE salary 0 -1 withscores # 将数据从大到小进行排列
1) "xiaohuang"
2) "6500"
3) "zhangsan"
4) "3900"
5) "xiaohong"
6) "3500"
127.0.0.1:6379> ZRANGEBYSCORE salary -inf 4000 withscores # 展示的同时还可以指示score的查询最大值，指定查询范围
1) "xiaohong"
2) "3500"
3) "zhangsan"
4) "3900"
127.0.0.1:6379> ZREM salary zhangsan # 删除zset中的一个元素
(integer) 1
127.0.0.1:6379> ZRANGE salary 0 -1 
1) "xiaohong"
2) "xiaohuang"
127.0.0.1:6379> ZCARD salary
(integer) 2
######################################################################################################################
127.0.0.1:6379> ZADD myset 1 hello 2 world 3 xiaohuang 4 xiaohei 5 xiaolan
(integer) 5
# 语法：ZCOUNT key min max ，min和max包左也包右，它是一个闭区间
127.0.0.1:6379> ZCOUNT myset 2 5 # 获取指定区间的成员数量
(integer) 4
127.0.0.1:6379> 
```

其他的API，如果说在工作中出现了，可以查看Redis的官方文档：http://www.redis.cn/commands.html

案例：zset是Redis的数据类型，可以排序，生活中也有案例，班级成绩，员工工资

设置权重，1、普通消息；2、重要消息；添加权重进行消息判断其重要性

来一个更接地气的案例，可以打开B站，排行榜，B站会根据视频的浏览量和弹幕量进行综合评分，进行排名

# 三种特殊数据类型

## geospatial 地理位置

微信朋友圈中的朋友的位置，或者是QQ中也有的附近的人，饿了么中外卖小哥的位置距离

这个在Redis中被定为特殊的数据类型可叫做Geo，它是Redis3.2正式推出的一个特性，可以推导出两个地方的地理位置，两地之间的距离，方圆几千米之内的人。

对于这个关于地理的数据类型，它有6个命令

- GEOADD
- GEODIST
- GEOHASH
- GEOPOS
- GEORADIUS
- GEORADIUSBYMEMBER

因为这个特殊的数据类型和地理相关，需要用到地理的经纬度，可以推荐一个网站查看指定城市的经纬度：http://www.jsons.cn/lngcode/

> GEOADD

```shell
# geoadd 添加地理位置
# 语法：GEOADD key 经度 纬度 城市名称 ...
# 注意：南北极无法直接添加。用添加城市数据来说，一般都会使用Java的Jedis来操作，而这些城市数据都是被下载下来通过JavaAPI调用
# 有效经度从-180到180度
# 有效纬度从-85.05112878 到 85.05112878 度。超过范围会出现(error) ERR invalid longitude,latitude pair
127.0.0.1:6379> GEOADD china:city 116.40 39.90 beijing 121.47 31.23 shanghai
(integer) 2
127.0.0.1:6379> GEOADD china:city 113.28 23.12 guangzhou 114.08 22.54 shenzhen
(integer) 2
127.0.0.1:6379> GEOADD china:city 119.30 26.07 fuzhou 118.11 24.49 xiamen
(integer) 2
```

> GEOPOS

```shell
# 语法：GEOPOS key member1 member2 ...
127.0.0.1:6379> GEOPOS china:city beijing
1) 1) "116.39999896287918091"
   2) "39.90000009167092543"
127.0.0.1:6379> GEOPOS china:city shanghai guangzhou # 获取一个或多个地理信息
1) 1) "121.47000163793563843"
   2) "31.22999903975783553"
2) 1) "113.27999979257583618"
   2) "23.1199990030198208"
```

> GEODIST

GEODIST命令表示指定两个位置的距离

单位：

- **m**表示单位米
- **km**表示千米
- **mi**表示英里
- **ft**表示英尺

```shell
# 语法：GEODIST key member1 member2 [unit]
# 后面的unit加了中括号表示可选操作，即当前这个命令计算出来的结果为m（米）
127.0.0.1:6379> GEODIST china:city beijing shanghai # 查看beijing和shanghai两个位置的直线距离
"1067378.7564"
127.0.0.1:6379> GEODIST china:city beijing shanghai km
"1067.3788"
127.0.0.1:6379> GEODIST china:city beijing fuzhou km
"1561.6135"
127.0.0.1:6379> 
```

> GEORADIUS

对于社交软件来说，附近的人，就相当于，你现在所在的地址，再加上一定的半径来进行查找

```shell
# 语法：GEORADIUS key 经度 纬度 半径 [单位] [WITHCOORD（搜寻到的目标的经纬度）] [WITHDIST（直线距离）] [count] 
127.0.0.1:6379> GEORADIUS china:city 111 31 1000 km # 以111经度31纬度为中心，1000km为半径搜寻在器范围之内的城市
1) "shenzhen"
2) "guangzhou"
3) "fuzhou"
4) "shanghai"
127.0.0.1:6379> GEORADIUS china:city 111 31 1000 km WITHCOORD WITHDIST # 追加参数，目标经纬度，直线距离
1) 1) "shenzhen"
   2) "989.2821"
   3) 1) "114.08000081777572632"
      2) "22.53999903789756587"
2) 1) "guangzhou"
   2) "905.0108"
   3) 1) "113.27999979257583618"
      2) "23.1199990030198208"
3) 1) "fuzhou"
   2) "978.4847"
   3) 1) "119.29999798536300659"
      2) "26.06999873822022806"
4) 1) "shanghai"
   2) "996.9549"
   3) 1) "121.47000163793563843"
      2) "31.22999903975783553"
127.0.0.1:6379> GEORADIUS china:city 111 31 1000 km WITHCOORD WITHDIST count 2 # 还可以限制查询的结果条数，只显示两条
1) 1) "guangzhou"
   2) "905.0108"
   3) 1) "113.27999979257583618"
      2) "23.1199990030198208"
2) 1) "fuzhou"
   2) "978.4847"
   3) 1) "119.29999798536300659"
      2) "26.06999873822022806"
```

> GEORADIUSBYMEMBER

找出指定元素周围的其他元素，就是以城市为中心，一定长度为半径搜索

```shell
# 语法：GEORADIUSBYMEMBER key member 长度 [unit]单位
127.0.0.1:6379> GEORADIUSBYMEMBER china:city shanghai 2500 km 
1) "shenzhen"
2) "guangzhou"
3) "xiamen"
4) "fuzhou"
5) "shanghai"
6) "beijing"
127.0.0.1:6379> GEORADIUSBYMEMBER china:city shanghai 1000 km # 找出以shanghai为中心，1000km为半径搜索
1) "xiamen"
2) "fuzhou"
3) "shanghai"
127.0.0.1:6379> 
```

> GEOHASH

返回一个或多个元素的GeoHash表示，该命令返回11个字符组成的GeoHash字符串

```shell
# GEOHASH key member
127.0.0.1:6379> GEOHASH china:city beijing 
1) "wx4fbxxfke0"
# 多聊一嘴GeoHash，Redis原先是将二维的经纬度通过一定的策略转换为一维的52位的字符串编码，这个时候的编码描述的地理位置是准确的，这种操作俗称“降维打击”
# 当前命令相比于52位的字符串来说，它砍掉了右边的大多数字符串，这也意味着它失去了一定的精度，但是地理位置的指向不变
```

**geospatial的底层，实际上它就是一个zset集合**，geospatial数据类型是zset的一层封装

可以使用zset的基本命令来查看当前的key

```shell
127.0.0.1:6379> ZRANGE china:city 0 -1 # 使用zset命令查看geospatial
1) "shenzhen"
2) "guangzhou"
3) "xiamen"
4) "fuzhou"
5) "shanghai"
6) "beijing" 
```

并且在这个特殊的数据类型中，并没有删除操作，因为**使用zset的基本命令即可删除**

```shell
127.0.0.1:6379> ZREM china:city beijing
(integer) 1
127.0.0.1:6379> ZRANGE china:city 0 -1
1) "shenzhen"
2) "guangzhou"
3) "xiamen"
4) "fuzhou"
5) "shanghai"
```

## Hyperloglog

> 在讲Hyperloglog之前，什么是基数？

集合中包含的不重复元素即为基数，就比如一个A数据集，A{1,3.7,9,11}，它的基数为5，可以接受误差

Hyperloglog是Redis2.8.9更新的，它是一种数据结构，主要是针对于基数统计的算法

优点，占用的内存很小，只需要使用12KB的内存即可统计2^64的数据

在实际业务中，网页的UV（Unique Visitor，独立访客），一个人访问一个网站多次，只能算作是一个

用传统的方式，set集合保存用户的id，然后统计set中元素个数作为标准来判断。使用这种方式来进行数据统计的话，大量的内存用来浪费给保存用户id了，目的是为了计数，而不是为了保存用户id

Hyperloglog计数的错误率在0.81%，用来执行UV任务，可以忽略不计

```shell
# 语法：
# PFADD key value1 value2...                创建一组数据集，如果数据集中有相同的元素就会有去重效果
# PFCOUNT key                               查看元素的长度
# PFMERGE key3 key1 key2                    将两组元素合并成一个新数组，并带有去重效果，相当于数学中的并集
127.0.0.1:6379> PFADD k1 a b c d e f g h i j
(integer) 1 
127.0.0.1:6379> PFCOUNT k1 
(integer) 10
127.0.0.1:6379> PFADD k2 h i j k l n m o p q
(integer) 1
127.0.0.1:6379> PFCOUNT k2 
(integer) 10
127.0.0.1:6379> PFMERGE k3 k1 k2 
OK
127.0.0.1:6379> PFCOUNT k3 
(integer) 17
127.0.0.1:6379> 
```

如果在项目中允许容错，可以使用Hyperloglog

如果不行，就可以直接使用set或者Java的HashMap来实现

## Bitmaps

> Bitmaps是一种位存储的数据类型，在Redis2.2.0被推出，

生活中可以实现的场景，统计活跃用户，在线状态，用户签到，这些都是表示两种状态之间的，可以使用Bitmaps

Bitmaps，译为位图，也是一种数据结构，操作二进制位进行记录，只有0和1两种状态。Bitmaps通过最小的单位bit来进行存储，表示元素对应的状态

```shell
# 语法：
# SETBIT key offset value                    设置一个key，在指定的offset位置上设置一个value，这个value只能是0或者1
# GETBIT key offset                          获取指定key上的offset位的value值
# BITCOUNT key [start] [end]                 在指定key中计算被设置为 1 的比特位的数量。
# BITOP operation destKey key1 key2 ...      对一个或者多个key进行二进制的逻辑运算
# BiTPOS key bit [start] [end]               指定key中返回value中第一个出现0或1的offset    
127.0.0.1:6379> SETBIT week 1 0
(integer) 0
127.0.0.1:6379> SETBIT week 2 0
(integer) 0
127.0.0.1:6379> SETBIT week 3 0
(integer) 0
127.0.0.1:6379> SETBIT week 4 1
(integer) 0
127.0.0.1:6379> SETBIT week 5 1
(integer) 0
127.0.0.1:6379> SETBIT week 6 1
(integer) 0
127.0.0.1:6379> SETBIT week 7 1
(integer) 0
127.0.0.1:6379> GETBIT week 3 
(integer) 0
127.0.0.1:6379> GETBIT week 5 
(integer) 1
127.0.0.1:6379> BITCOUNT week
(integer) 4
127.0.0.1:6379> BITPOS week 1 0 -1 # 在week中返回第一个出现1的value值
(integer) 4
########################################################################################################################################
# BITOP逻辑运算
# 一共有4种逻辑运算，AND、OR、NOT、XOR，分别代表 并、或、非、异或
127.0.0.1:6379> SETBIT bit-1 0 1
(integer) 0
127.0.0.1:6379> SETBIT bit-1 1 1
(integer) 0
127.0.0.1:6379> SETBIT bit-1 3 1
(integer) 0
# bit-1的二进制数为1011，为什么嘞，这和基本数据类型中的list有点相似。
# list列表的LPUSH和LPOP组合起来可以作为一个栈的数据结构，在指定的比特位当中设置一个0或1，先进先出，如果没有设置值，就默认为0来进行处理
127.0.0.1:6379> SETBIT bit-2 0 1
(integer) 0
127.0.0.1:6379> SETBIT bit-2 3 1
(integer) 0
# bit-2的二进制数为1001
# 对bit-1和bit-2进行 并 操作
127.0.0.1:6379> BITOP AND and-bit bit-1 bit-2
(integer) 1
127.0.0.1:6379> GETBIT and-bit 0
(integer) 1
127.0.0.1:6379> GETBIT and-bit 1
(integer) 0
127.0.0.1:6379> GETBIT and-bit 2
(integer) 0
127.0.0.1:6379> GETBIT and-bit 3
(integer) 1
# and-bit：1001

# 对bit-1和bit-2进行 或 操作
127.0.0.1:6379> BITOP OR or-bit bit-1 bit-2
(integer) 1
127.0.0.1:6379> GETBIT or-bit 0
(integer) 1
127.0.0.1:6379> GETBIT or-bit 1
(integer) 1
127.0.0.1:6379> GETBIT or-bit 2
(integer) 0
127.0.0.1:6379> GETBIT or-bit 3
(integer) 1
# or-bit：1011

# 对bit-1进行 非 操作，注意：非操作只针对一个key
127.0.0.1:6379> BITOP NOT not-bit bit-1
(integer) 1
127.0.0.1:6379> GETBIT not-bit 0
(integer) 0
127.0.0.1:6379> GETBIT not-bit 1
(integer) 0
127.0.0.1:6379> GETBIT not-bit 2
(integer) 1
127.0.0.1:6379> GETBIT not-bit 3
(integer) 0
# not-bit：0100
127.0.0.1:6379> BITOP NOT not-bit2 bit-1 bit-2
(error) ERR BITOP NOT must be called with a single source key.

# 对bit-1和bit-2进行 异或 操作
127.0.0.1:6379> BITOP XOR xor-bit bit-1 bit-2
(integer) 1
127.0.0.1:6379> GETBIT xor-bit 0
(integer) 0
127.0.0.1:6379> GETBIT xor-bit 1
(integer) 1
127.0.0.1:6379> GETBIT xor-bit 2
(integer) 0
127.0.0.1:6379> GETBIT xor-bit 3
(integer) 0
# xor-bit：0010
# 备注：BITOP执行命令较慢，因为其时间复杂度为O(n)。
# 在进行计数时，如果数据量过大，建议直接将其指派到master-slave中的slave节点进行处理，避免阻塞master
########################################################################################################################################
```

# 事务

**Redis单条命令保持原子性，但是Redis事务不保证原子性**

Redis事务本质，可以将Redis的事务看成是一个队列，将一组命令进行“入队”，然后一起执行

一个事务中的所有命令都会被序列化，在事务执行的过程中，会按照顺序执行

Redis事务的三个特性：**一致性**，**顺序性**，**排他性**

```shell
# 命令：
# MULTI            开启事务
# EXEC             执行事务
# DISCARD          关闭事务
127.0.0.1:6379> MULTI 
OK
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3 
QUEUED # 表示命令入队，等待客户端执行事务
127.0.0.1:6379> MGET k1 k2 k3
QUEUED
127.0.0.1:6379> SADD set1 s1 s2 s3 s4 s5
QUEUED
127.0.0.1:6379> SMEMBERS set1 
QUEUED
127.0.0.1:6379> LPUSH list l1 l2 l3 l4 l5 
QUEUED
127.0.0.1:6379> LRANGE list 0 -1
QUEUED
127.0.0.1:6379> EXEC 
1) OK
2) 1) "v1"
   2) "v2"
   3) "v3"
3) (integer) 5
4) 1) "s3"
   2) "s2"
   3) "s1"
   4) "s4"
   5) "s5"
5) (integer) 5
6) 1) "l5"
   2) "l4"
   3) "l3"
   4) "l2"
   5) "l1"
###################################################放弃事务###############################################################
127.0.0.1:6379> MULTI 
OK
127.0.0.1:6379> MSET k1 v1 k2 v2
QUEUED
127.0.0.1:6379> SET k3 v3
QUEUED
127.0.0.1:6379> DISCARD # 一旦放弃事务，之前入队的全部命令都不会执行
OK
127.0.0.1:6379> GET k3 
(nil)
####################################################编译型异常##############################################################
127.0.0.1:6379> MULTI 
OK
127.0.0.1:6379> mset k1 v1 k2 v2 
QUEUED
127.0.0.1:6379> LPUSH list l1 l2 l3 
QUEUED
127.0.0.1:6379> GETSET k3 # 错误的命令
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379> mget k1 k2 
QUEUED
127.0.0.1:6379> LRANGE list 0 -1
QUEUED
127.0.0.1:6379> EXEC # 事务执行失败
(error) EXECABORT Transaction discarded because of previous errors.
127.0.0.1:6379> get k2 # 编译型异常（又叫入队错误）的特点：事务中有错误的命令，会导致默认放弃事务，所有的命令都不会执行
(nil)
###################################################运行时异常################################################################
127.0.0.1:6379> MULTI 
OK
127.0.0.1:6379> set k1 v1 
QUEUED
127.0.0.1:6379> INCR k1 # 语法正确，但是对一个String类型的k1执行了错误的操作
QUEUED
127.0.0.1:6379> SADD set s1 s2 s3 
QUEUED
127.0.0.1:6379> LPUSH list l1 l2 l3 
QUEUED
127.0.0.1:6379> set k2 v2 
QUEUED
127.0.0.1:6379> EXEC 
1) OK
2) (error) ERR value is not an integer or out of range # 第二条命令报错，但是不影响事务整体的运行
3) (integer) 3
4) (integer) 3
5) OK
127.0.0.1:6379> SMEMBERS set
1) "s3"
2) "s2"
3) "s1"
# 运行时异常（又叫执行错误）：在事务执行的过程中语法没有出现任何问题，但是它对不同类型的key执行了错误的操作，
# Redis只会将返回的报错信息包含在执行事务的结果中，并不会影响Redis事务的一致性
```

> 监控

**悲观锁**：

- 很悲观，无论执行什么操作都会出现问题，所以会对所有的操作加锁

**乐观锁**

- 很乐观，任何情况下都不会出问题，所以不会加锁！但是在数据更新时需要判断在此之前是否有人修改过这个数据
- 可以添加一个字段叫version用来查询
- 在进行数据更新时对version进行比较

> Redis监视测试

首先先模拟正常状态

```shell
# 模拟客户转账
127.0.0.1:6379> WATCH money # 语法：WATCH key ... # 对指定key进行监控，监控这个key在事务执行之前是否被修改
OK
127.0.0.1:6379> MULTI # 如果没有被修改，那么这个事务是可以正常执行成功的
OK
127.0.0.1:6379> DECRBY money 20 # 转账20
QUEUED
127.0.0.1:6379> INCRBY out 20 
QUEUED
127.0.0.1:6379> EXEC 
1) (integer) 80
2) (integer) 20
127.0.0.1:6379> 
```

如果被监控的key在事务之外被修改了

```shell
127.0.0.1:6379> WATCH money
OK
127.0.0.1:6379> MULTI 
OK
127.0.0.1:6379> DECRBY money 30
QUEUED
127.0.0.1:6379> INCRBY out 30
QUEUED
# 这个时候开始模拟另外一个客户端恶意修改被监控的key
# =======================================================表示另一个客户端==============================================================
127.0.0.1:6379> get money
"80"
127.0.0.1:6379> INCRBY money 200 # 修改被监控的数据
(integer) 280
127.0.0.1:6379> get money
"280"
# ===================================================================================================================================
# 再次执行事务，会直接返回nil，代表执行失败
127.0.0.1:6379> EXEC 
(nil)
127.0.0.1:6379> get money # 再次查看，当前监控的key已经被修改
"280"
# 多聊一嘴：实际上关于WATCH，还有一个命令，UNWATCH，意思是解除所有监控，但是官网的原话是，一旦你执行了DISCARD或者EXEC，就没必要在执行UNWATCH
127.0.0.1:6379> MGET money out
1) "280"
2) "20"
127.0.0.1:6379> WATCH money 
OK
127.0.0.1:6379> MULTI 
OK
127.0.0.1:6379> DECRBY money 30 
QUEUED
127.0.0.1:6379> INCRBY out 30
QUEUED
127.0.0.1:6379> EXEC 
1) (integer) 250
2) (integer) 50
```

# Jedis

> 什么是Jedis

Jedis是Redis官方推荐的Java连接Redis的连接开发工具！使用Java操作Redis的中间件

导入依赖

```xml
<!--导入Jedis-->
<dependency>
  <groupId>redis.clients</groupId>
  <artifactId>jedis</artifactId>
  <version>3.2.0</version>
</dependency>
<!--fastjson-->
<dependency>
  <groupId>com.alibaba</groupId>
  <artifactId>fastjson</artifactId>
  <version>1.2.70</version>
</dependency>
```

编码测试

- 连接数据库
- 操作命令
- 断开连接

```java
public class TestPing {
    public static void main(String[] args) {
        // new一个Jedis对象
        Jedis jedis = new Jedis("192.168.1.107", 6379);
        // Jedis中的API就是之前学习的命令
        System.out.println(jedis.ping());
    }
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020111912302339.png#pic_center)

不过这边有一个小问题，如果你的Redis是远程连接的话，会出现连接超时或者是拒绝访问的问题，在这边需要做两件事情，当然，**防火墙的关闭也是必不可少的**

打开redis.conf配置文件

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123045548.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

随后进行API测试

```java
public class JedisType {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.107", 6379);
        System.out.println(jedis.ping());
        // String
        System.out.println(jedis.set("k1", "v1"));
        System.out.println(jedis.get("k1")); // v1
        System.out.println(jedis.append("k1", "+value"));
        System.out.println(jedis.get("k1")); // v1+value
        System.out.println(jedis.strlen("k1")); // 8
        System.out.println("=====================================================");
        // List
        System.out.println(jedis.lpush("listKey", "l1", "l2", "l3"));
        System.out.println(jedis.lrange("listKey", 0, -1)); // [l3, l2, l1]
        System.out.println(jedis.llen("listKey"));
        System.out.println("=====================================================");
        // Hash
        System.out.println(jedis.hset("hashKey", "k1", "v1"));
        System.out.println(jedis.hset("hashKey", "k2", "v2"));
        System.out.println(jedis.hset("hashKey", "k3", "v3"));
        System.out.println(jedis.hmget("hashKey", "k1", "k2", "k3")); // [v1, v2, v3]
        System.out.println(jedis.hgetAll("hashKey")); // {k3=v3, k2=v2, k1=v1}
        System.out.println("=====================================================");
        // Set
        System.out.println(jedis.sadd("setKey", "s1", "s2", "s3", "s4"));
        System.out.println(jedis.smembers("setKey")); // [s2, s1, s4, s3]
        System.out.println(jedis.scard("setKey"));
        System.out.println("=====================================================");
        // ZSet
        System.out.println(jedis.zadd("ZKey", 90, "z1"));
        System.out.println(jedis.zadd("ZKey", 80, "z2"));
        System.out.println(jedis.zadd("ZKey", 85, "z3"));
        System.out.println(jedis.zrange("ZKey", 0, -1)); // [z2, z3, z1]
    }
}
```

测试事务

```java
public class TestTX {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.1.107", 6379);
        jedis.flushDB();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "xiaohuang");
        jsonObject.put("age", "21");
        jsonObject.put("sex", "boy");
        Transaction multi = jedis.multi(); //  开启事务
        String user = jsonObject.toJSONString();
        try {
            multi.set("user1", user);
            multi.set("user2", user);
            multi.exec();
        } catch (Exception e) {
            multi.discard(); // 出现问题，放弃事务
            e.printStackTrace();
        } finally {
            System.out.println(jedis.mget("user1", "user2"));
            jedis.close(); // 关闭连接
        }
    }
}
```

# SpringBoot整合Redis

> 备注：从SpringBoot2.x之后，原先使用的Jedis被lettuce替代

Jedis：采用直连，模拟多个线程操作会出现安全问题。为避免此问题，需要使用Jedis Pool连接池！类似于BIO模式

lettuce：采用netty网络框架，对象可以在多个线程中被共享，完美避免线程安全问题，减少线程数据，类似于NIO模式

首先先查看RedisAutoConfiguration中的源码

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
public class RedisAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
        throws UnknownHostException {
     // 默认的RedisTemplate直接使用此类内部默认设置操作数据，但是Redis对象需要序列化
     // 泛型都是Object，后面使用的话，大都是RedisTemplate<String, Object>
     RedisTemplate<Object, Object> template = new RedisTemplate<>();
     template.setConnectionFactory(redisConnectionFactory);
     return template;
  }

  @Bean
  @ConditionalOnMissingBean
  public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory)
        throws UnknownHostException {
     StringRedisTemplate template = new StringRedisTemplate();
     template.setConnectionFactory(redisConnectionFactory);
     return template;
  }
}
```

上面的@Import注解导入了两个配置类，有Lettuce和Jedis，可以点开这两个类查看

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020111912312068.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123138848.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

对比一下可以发现，Jedis配置类中有两个类是默认不存在的，不存在就无法使用

1、导入依赖

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

2、属性配置

```properties
# 配置Redis
spring.redis.host=192.168.1.107
spring.redis.port=6379
```

备注：这边的配置，需要注意的是，SpringBoot整合的是Lettuce，如果在配置文件中添加额外的配置，比如Redis的最大等待时间、超时时间等，在对应的RedisProperties类所映射的配置文件中，属性名称一定要加上带有lettuce，如果加上jedis，它默认不会生效

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123209469.png#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123225123.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

3、测试连接

```java
@Test
void contextLoads() {
    ValueOperations ops = redisTemplate.opsForValue();
    redisTemplate.opsForGeo();
    ops.set("k1", "xiaohuang");
    Object o = ops.get("k1");
    System.out.println(o);
}
```

测试了之后在控制台可以成功获取

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123245628.png#pic_center)

但是在Linux中的客户端取到的却是乱码

```shell
127.0.0.1:6379> keys *
1) "\xac\xed\x00\x05t\x00\x02k1"
```

这与RedisTemplate默认序列化有关

先展示RedisTemplate的部分源码

```java
// 这些是RedisTemplate的序列化配置
private @Nullable RedisSerializer keySerializer = null;
private @Nullable RedisSerializer valueSerializer = null;
private @Nullable RedisSerializer hashKeySerializer = null;
private @Nullable RedisSerializer hashValueSerializer = null;

@Override
public void afterPropertiesSet() {
  super.afterPropertiesSet();
  boolean defaultUsed = false;
  if (defaultSerializer == null) {
    // 这边默认使用JDK的序列化方式，可以自定义一个配置类，采用其他的序列化方式
    defaultSerializer = new JdkSerializationRedisSerializer(
      classLoader != null ? classLoader : this.getClass().getClassLoader());
   }
	}
}
```

自定义RedisTemplate

```java
// 自定义RedisTemplate
// 这是RedisTemplate的一个模板
@Bean
@SuppressWarnings("all")
public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
  throws UnknownHostException {
  // 为了开发方便，可以直接使用<String, Object>
  RedisTemplate<String, Object> template = new RedisTemplate<>();

  // 序列化配置
  Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
  template.setDefaultSerializer(serializer);
  template.setConnectionFactory(redisConnectionFactory);
  ObjectMapper om = new ObjectMapper();
  om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
  om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance);
  serializer.setObjectMapper(om);
  StringRedisSerializer srs = new StringRedisSerializer();
  // 对于String和Hash类型的Key，可以采用String的序列化方式
  template.setKeySerializer(srs);
  template.setHashKeySerializer(srs);
  // String和Hash类型的value可以使用json的方式进行序列化
  template.setValueSerializer(serializer);
  template.setHashValueSerializer(serializer);
  template.afterPropertiesSet();
  return template;
}
```

## RedisUtil工具类

在SpringBoot中，如要操作Redis，就需要一直调用RedisTemplate.opsxxx的方法，一般在工作中不会去这样使用，公司里都会内部将这些操作数据类型的API进行一个封装，就像在学JDBC还有Mybatis等框架的时候，都会有一个XxxUtil的Java工具类，使用起来比较简单

这边推荐一个GitHub：https://github.com/iyayu/RedisUtil.git

# Redis.conf配置文件

Redis在启动的时候是通过配置文件进行启动的

### 1、Units

单位，Redis配置文件中的单位对大小写不敏感

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123309871.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

### 2、includes

包含，可以在Redis启动的时候再加载一些除了Redis.conf之外的其他的配置文件，和Spring的import，jsp的include类似

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123326776.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

### 3、NETWORK

网络，表示Redis启动时开放的端口默认与本机绑定

```shell
bind 127.0.0.1
```

Redis指定监听端口，默认为6379

```shell
port 6379
```

表示服务器闲置多长时间（秒）后被关闭，如果这个这个数值为0，表示这个功能不起作用

```shell
timeout 300
```

是否开启保护模式，Redis默认开启，如果没有设置bind的IP地址和Redis密码，那么服务就会默认只能在本机运行

```shell
protected-mode yes
```

### 4、GENERAL

是否以守护进程的方式运行，即后台运行，一般默认为no，需要手动改为yes

```shell
daemonize yes
```

如果以守护进程的方式运行，就需要指定一个pid文件，在Redis启动时创建，退出时删除

```shell
pidfile /var/run/redis_6379.pid
```

配置日志等级，日志等级的可选项如下（翻译自配置文件，有改动）：

- debug：打印的信息较多，在工作中主要用于开发和测试
- verbose：打印的信息仅次于debug，但是格式较为工整
- notice：Redis默认配置，在生产环境中使用
- warning：只打印一些重要信息，比如警告和错误

```shell
loglevel notice
```

打印的日志文件名称，如果为空，表示标准输出，在配置守护进程的模式下会将输出信息保存到/dev/null

```shell
logfile ""
```

数据库支持数量，16个

```shell
databases 16
```

### 5、SNAPSHOTTING

中文翻译为快照，如果在规定的时间内，数据发生了几次更新，那么就会将数据同步备份到一个文件中

Redis的持久化有两种方式，一种是RDB，一种是AOF。SNAPSHOTTING主要针对的是Redis持久化中的RDB

Redis是一个内存数据库，如果不采用持久化对数据进行保存，那么就会出现断电即失的尴尬场面

```shell
# 在900秒内，至少有一个key被修改（添加），就会进行持久化操作
save 900 1
# 在300秒内，至少有10个key被修改，就会进行持久化操作
save 300 10
# 在60秒内，至少有1万个key被修改，就会进行持久化操作
save 60 10000
```

如果Redis在进行持久化的时候出现错误，是否停止写入，默认为是

```shell
top-writes-on-bgsave-error yes
```

是否在进行数据备份时压缩持久化文件，默认为是，这个操作会耗费CPU资源，可以设置为no

```shell
rdbcompression yes
```

在保存持久化文件的同时，对文件内容进行数据校验

```shell
rdbchecksum yes
```

持久化文件保存的目录，默认保存在当前目录下

```shell
dir ./
```

### 6、REPLICATION

复制主机上的数据，当前配置所指定的IP和端口号即为主机

```shell
# Redis在配置文件中将此配置注释，默认不使用，下同
# replicaof <masterip> <masterport>
```

如果配置的主机有密码，需要配置此密码以通过master的验证

```shell
# masterauth <master-password>
```

### 7、SECRULITY

安全，可以在配置文件中设置Redis的登录密码

### 8、CLIENT

Redis允许存在的客户端的最大数量，默认有一万个

```shell
maxclients 10000
```

Redis配置最大的内存容量

```shell
maxmemory <bytes>
```

内存达到上限之后默认的处理策略

```shell
maxmemory-policy noeviction
```

处理策略有以下几种

- noeviction:默认策略，不淘汰，如果内存已满，添加数据是报错。
- allkeys-lru:在所有键中，选取最近最少使用的数据抛弃。
- volatile-lru:在设置了过期时间的所有键中，选取最近最少使用的数据抛弃。
- allkeys-random: 在所有键中，随机抛弃。
- volatile-random: 在设置了过期时间的所有键，随机抛弃。
- volatile-ttl:在设置了过期时间的所有键，抛弃存活时间最短的数据

### 9、APPEND ONLY MODE

这是Redis持久化的另一种方式，AOF，AOF模式默认不开启，Redis默认开启的是持久化模式是RDB，在大部分情况下，RDB的模式完全够用

```shell
appendonly no
```

AOF持久化的文件名称

```shell
appendfilename "appendonly.aof"
```

每秒执行一次同步，但是可能会丢失这一秒的数据

```shell
# 对于 appendfsync 它有以下几个属性 
# appendfsync always 表示每次修改都会进行数据同步，速度较慢，消耗性能
# appendfsync no 不执行同步，不消耗性能
appendfsync everysec # 数据不同步，每秒记录一次
```

# Redis持久化

## RDB

RDB，全称Redis DataBase。什么是RDB，在指定的时间间隔内将数据集快照写入到磁盘中，在恢复数据的时候将这些快照文件读取到内存中，对应的配置文件中的**SNAPSHOTTING**，可以查看在上面提到的Redis的配置文件

Redis会单独创建出一个子进程（fork）来进行持久化，会将数据写入到一个临时文件中，待持久化操作结束之后，临时文件会将已经持久化完成的文件替换掉，在这个过程中，主进程不进行任何IO操作，这也就确保RDB极高的性能，相比于RDB和AOF，RDB的模式会比AOF更加的高效。如果在进行大数据恢复，并且对于数据的精度要求不高，那么就可以使用RDB，Redis的持久化默认的也是RDB，在一般情况下（生产环境）不需要修改这个配置

什么是fork？fork就是复制一个和当前一模一样的进程作为原进程的子进程

**RDB保存的文件就是dump.rdb文件**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123349353.png#pic_center)

> RDB触发机制

1、满足配置文件的save规则的情况下，会自动触发RDB规则

2、执行FLUSHALL命令，也会触发RDB规则，但是没有意义，因为文件内容为空

3、退出Redis，也会产生dump.rdb文件（退出Redis默认执行save命令）

4、在客户端中使用save或者bgsave命令，也可以触发RDB规则但是这两种规则有所不同

- save命令会完全占用当前进程去进行持久化操作，也就是说，save命令只管保存，不管其他，只要有进程过来，一律阻塞
- bgsave命令会在后台运行，手动fork子进程进行操作，并且还会相应客户端的请求

> 如何进行数据恢复

1、首先使用一个Redis命令查看持久化文件保存的位置

```shell
config get dir
127.0.0.1:6379> CONFIG GET dir
1) "dir"
2) "/usr/local/bin"
```

2、然后将dump.rdb文件放到Redis的启动目录下即可

优点：

1. 适合大规模数据修复！
2. 对数据精度要求不高

缺点：

1. 在持久化的时候需要一定的时间间隔，如果在一定的间隔时间内服务器意外宕机，那么就会丢失最后一次持久化的数据
2. 因为RDB持久化是需要fork出一份子进程进行IO操作的，也就是说，在原本的进程当中再复制出一个一模一样的进程作为子进程在内存中运行，内存的承载就会变为原来的两倍

## AOF

Redis的另一种持久化方式，AOF，全名为Append Only File，它用日志的形式来记录每一个写操作，将Redis执行过的命令进行记录（读操作不记录），只追加文件，不改写文件。Redis在启动时会自动加载AOF持久化的文件重新构建数据，也就是Redis重启会把AOF持久化文件中的信息从前到后执行一次以完成数据的恢复

AOF持久化对应的配置文件的位置是**APPEND ONLY MODE**

如何启动AOF，看下图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123413354.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

修改完配置之后，只需重新启动就可

这里有一个小细节需要注意：**如果AOF和RDB模式在配置文件中都有开启的话，为了保证数据的安全性，在Redis启动时会优先使用AOF**

Redis的AOF持久化保存的文件名称就叫做**appendonly.aof**

如果说appendonly.aof文件的内容发生了一些错误，那么在Redis进行启动时，会出现问题

```shell
[root@bogon bin]# redis-server myconfig/redis.conf 
1574:C 16 Nov 2020 03:47:18.350 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
1574:C 16 Nov 2020 03:47:18.350 # Redis version=5.0.10, bits=64, commit=00000000, modified=0, pid=1574, just started
1574:C 16 Nov 2020 03:47:18.350 # Configuration loaded
[root@bogon bin]# redis-cli -p 6379
# 下面的这一个返回结果，表示连接被拒绝
Could not connect to Redis at 127.0.0.1:6379: Connection refused 
not connected> exit
```

如果appendonly.aof内部发生错误，咋办？Redis中提供了一个可以修复aof文件的修复工具叫做redis-check-aof

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123439448.png#pic_center)

怎么使用呢，下面有一条命令

```shell
redis-check-aof --fix appendonly.aof

[root@bogon bin]# redis-check-aof --fix appendonly.aof 
0x             167: Expected \r\n, got: 6769
AOF analyzed: size=383, ok_up_to=351, diff=32
This will shrink the AOF from 383 bytes, with 32 bytes, to 351 bytes
Continue? [y/N]: y
Successfully truncated AOF
```

> Redis优缺点

优点：

它支持 ***同步记录*** 和 ***异步记录*** ，对应的配置文件的属性分别是下面两个

```shell
appendfsync always       # 同步记录，客户端中一有写操作，即刻记录，数据的完整性好，但是性能较差
appendfsync everysec     # 异步记录，每秒记录一次，但是服务器如果在这一秒之内宕机，这一秒的数据就会丢失
appendfsync no           # 不记录
```

缺点：

从恢复数据的角度来说，AOF所恢复的数据量一定是比RDB来得大的，从恢复数据的时间的角度来说，AOF的时间也是大于RDB的

### AOF的重写机制

AOF持久化本质就是采用日志的形式对文件内容进行追加，为了防止追加之后这个文件变得越大，所以Redis推出了一种针对于AOF文件的**重写机制**，如果AOF文件的大小超过配置文件中所设定的阈值时，会自动触发重写机制对文件内容进行压缩，只对可以恢复数据的命令进行保留，针对于这种重写机制，也可以在客户端中对这种重写机制进行手动触发，只需要一个命令

```shell
bgrewriteaof
```

原理

当AOF文件持久追加并且越来越大时，Redis会fork出一条新进程来对文件进行重写，和RDB一样，AOF也是先写临时文件再将其替换掉。Redis会对新进程中的数据进行遍历，每次都会遍历set和set有关的命令。重写并没有读取原来的appendonly.aof文件，而是使用命令将内存中的数据库内容进行重写得到一个新的文件

**Redis会将上一次重写的AOF文件大小进行记录，如果当前文件的大小超过源文件的一倍并且大小大于64M时就会触发重写操作**

可以在配置文件中查看重写的信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123501266.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

## 总结

- RDB可以在指定的时间间隔内对数据集进行持久化快照存储
- AOF持久化记录每次客户端发送给Redis服务器的写操作，服务器中重启时会重新执行命令恢复原始数据，AOF持久化的每一次记录都会追加在文件的末尾，并且Redis有重写机制的存在使得AOF的文件被控制在合理的大小
- 如果Redis只做缓存，如果说只希望数据在服务器启动的时候存在，可以不使用任何的持久化方式
- 刚刚上面讲到一个小细节，如果两种持久化同时开启，Redis服务器会默认先找AOF持久化，因为AOF的保存数据集要比RDB要完整，这也就是Redis考虑安全的原因

# Redis发布订阅

Redis的发布订阅（publish/subscribe）是一种消息通信模式，发送者（publish）发送消息，订阅者（subscribe）接收消息

Redis客户端可以订阅任意数量的频道

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123521181.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

如图，有三个客户端订阅了一个Channel1

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123536594.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

当Channel1的后台发送了一个数据到Channel1的频道中，这三个订阅了Channel1的客户端就会同时收到这个数据

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123557985.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

命令：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123616735.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

这些都是用来实现数据通信的命令，现实中的场景可以是网络聊天室，广播等

订阅端

```shell
127.0.0.1:6379> SUBSCRIBE smallyellow   # 订阅一个频道叫smallyellow
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "smallyellow"
3) (integer) 1
# 一旦开始订阅，会立即占用当前进程去监听自己所订阅的那个Channel
1) "message"
2) "smallyellow"
3) "Hello!I love Java!!"
1) "message"
2) "smallyellow"
3) "Hello!I love Redis!!"
```

发送端

```shell
127.0.0.1:6379> PUBLISH smallyellow "Hello!I love Java!!"  # 往频道smallyellow中发布一条消息
(integer) 1
127.0.0.1:6379> PUBLISH smallyellow "Hello!I love Redis!!"
(integer) 1
127.0.0.1:6379> 
```

原理：

Redis是C语言编写，在实现消息的发布和订阅的时候，Redis将其封装在一个后缀名为点c的文件中，pubsub.c

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123637685.png#pic_center)

通过subscribe和publish命令实现消息的发布和订阅，当用户订阅了一个频道之后，redis-server里面维护了一个字典，字典里有很多个Channel（频道），字典的值就是一个链表，链表中保存的是订阅了这个频道的用户，使用publish命令往频道发送数据之后，redis-server使用此频道作为key，去遍历这个指定的value链表，将信息依次发送过去

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123704135.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

发布订阅的实现场景

1、实时沟通消息系统

2、微信公众号（点击关注，后台发送一篇博客，订阅的用户就可以监听到）

…

还有一些比较复杂的场景，可以使用消息中间件来做，RabbitMQ，RocketMQ，kafka…

# Redis主从复制

Redis的主从复制，实际上就是将一台Redis服务器的数据，复制到其他Redis的服务器。前者被称为服务器的主节点（master/leader），后者就是从节点（slave/follower），数据的复制只能由主节点到从节点，Master以写主，slave以读为主

实际上每台服务器都是一个主节点，一个主节点可以有零个或多个从节点，并且每一个从节点只能有一个主节点

**主从复制有哪些作用？**

1、主从复制可以实现数据备份，这是除了持久化的另一种实现数据保存的方式

2、实现故障快速修复，因为要实现主从复制必然需要多台服务器，一旦主节点挂掉，从节点可以代替主节点提供服务

3、主从复制配合读写分离，可以实现分担服务器负载，如果在生产环境中读的操作远大于写的操作时，可以通过多个从节点进行分担负载，以提高Redis的并发量

4、主从复制也是Redis集群搭建和哨兵机制的基础

将Redis运用于项目中，是不会只使用一台服务器进行搭建的，因为：

- 如果是单个Redis，那么单个服务器将会独自完成来自客户端的读写操作，负载较大，而且只有一台Redis服务器，太容易挂掉了，一不小心宕掉了就很麻烦
- 单个服务器，内存容量是有上限的，不管这台Redis服务器的内存再怎么大，也不可能完全用来存储内存，并且单台Redis服务器的内存占用一般不会超过20个G

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123737911.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

主从复制，读写分离，在大部分情况下，很多人浏览网页更多的则是读操作，这是架构中常常会用到的一种模式，用来缓解读压力

## 配置

因为Redis默认启动时本身就是一个master，所以在进行主从复制的搭建时，只需要配置从库即可，查询当前库的状态信息，可以使用下面这个命令

```shell
127.0.0.1:6379> INFO replication                            # 语法：INFO replication  查看当前库的信息
# Replication
role:master                                                 # 当前库的角色：master 
connected_slaves:0                                          # 连接的节点数量：0
master_replid:dac6206fe3b477c70197db4e02696ca29b85e6be
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

复制出三个配置文件，修改4个信息

1、搭建主从复制，不同的服务器就需要不同的端口号

2、pidfile的文件名称

3、logfile 日志文件名称

4、Redis默认RDB持久化，所以dump.rdb的名称也是需要修改的

修改完毕之后启动，如果进程存在，就表示搭建完毕

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123805947.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

## 一主二从

所有的Redis服务器在默认情况下都是一个主节点，在进行配置时，一般都是只需配置从机即可

说白了，就是“认老大”，那怎么“认老大”嘞，可以看下面的命令

```shell
# 从机 6380 “认老大”
127.0.0.1:6380> SLAVEOF 127.0.0.1 6379
OK
127.0.0.1:6380> INFO replication
# Replication
role:slave                              # 当前角色 从机
master_host:127.0.0.1                   # 主机信息 127.0.0.1
master_port:6379                        # 主机端口号 6379
master_link_status:up                   # 主机状态 在线
master_last_io_seconds_ago:9
master_sync_in_progress:0
slave_repl_offset:14
slave_priority:100
slave_read_only:1
connected_slaves:0
master_replid:f99776a9a6bc1e30551ac53e3ca674e6c9988120
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:14
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:14

# 可以在主机中查看当前有何改动

127.0.0.1:6379> INFO replication
# Replication
role:master
connected_slaves:1                                             # 连接的从机数量 1
slave0:ip=127.0.0.1,port=6380,state=online,offset=154,lag=1    # 从机信息，有IP地址，端口号，状态等
master_replid:f99776a9a6bc1e30551ac53e3ca674e6c9988120
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:154
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:154
```

如果从机都配置完毕，就可以在主机中使用INFO命令进行查看当前状态

实际上，这样在命令行中的这种主从配置，只是暂时的，真正要想实现永久的主从配置，就需要在配置文件中配置，可以查看上面的Redis配置文件中讲到的

配置完了之后，主机可以写，从机不能写，这就是读写分离

```shell
# 主机中设置值
127.0.0.1:6379> SET k1 v1 
OK
127.0.0.1:6379> SET k2 v2 
OK
127.0.0.1:6379> KEYS *
1) "k2"
2) "k1"
```

主机的设置值，同样会被两个从机获取到

```shell
127.0.0.1:6380> KEYS *
1) "k2"
2) "k1"
#######################################################################################################################33
127.0.0.1:6381> KEYS *
1) "k2"
2) "k1"
```

但是作为从机在默认情况下是不能写的

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123830494.png#pic_center)

> 在实现一主二从时需要注意的问题

1、如果说在实现一主二从时，主机意外的挂掉了，那么**剩下的从机在主机还没到来之前原地待命**，这个时候主机掉了，就意味着失去了写操作。并且主机重新上线之后，还是保持着原来的身份，从机依旧可以获取到主机写入的信息

2、这个时候是如果是从机挂掉了，就代表这个从机已经断开连接，等待再次上线时，就不会连接原来的主机，它自己又变成了主机，但是原先的数据依旧存在，如果其再次重新连接，那么主机当中新添加的数据同样也会被共享到

上面的两个小问题，就直接引出了接下来要聊的**复制原理**了

> 什么是复制原理？

如果一个Slave指定了一个master做老大，那么Master节点就会发送一个叫做sync的同步命令，在使用了SLAVEOF命令之后，Master接收到了，就会启动后台的*存盘进程*，开始收集修改数据集的命令，在存盘进程执行完毕之后，会将数据文件发送给Slave，实现一次完全同步

说到同步，这个有两个概念需要多聊一嘴

1、全量复制，Slave文件在接到Master发来的数据文件之后，会将其存盘并加载到内存

2、增量复制，Master和Slave已经实现同步的情况下，如果Master继续修改数据集，会将命令再次收集起来，再发送给Slave

**只要是重新连接Master的服务器，在连接之后都会自动执行全量复制！！！**

## 一带一路

啥叫一带一路？*这一个节点它可以是上一个几点的Slave，可以是下一个节点的Master，这种既是Master又是Slave的状态就叫一带一路（随便乱取的，别搞我）*

这种主从复制的特性，在实际应用场景中可以有多个节点，这样的节点具有Master和Slave的双重身份（但是实际上它还是一个Slave节点，只不过它是一个可以进行写操作的Slave节点），可以分担Redis的写压力，并且在真正的Master节点挂掉之后，这些具有双重身份的就可以顶上去工作，有效解决了一主二从的不可写问题

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123849226.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

可以使用SLAVEOF命令重新定义，被指定的IP和端口号的节点，就是具有Master和Slave双重身份的节点

```shell
SLAVEOF 127.0.0.1 6380  # 在端口号为6381的服务器输入命令
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123905996.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

## 谋权篡位

在一主二从的基础上，如果主机挂了，不能让两个从机待在那傻傻的待命吧，总得写吧，那要写，咋写？有一个命令

```shell
SLAVEOF no one  # 这个命令表示当前数据库停止与其他数据库的同步关系，使得当前数据库成为主库
```

当前数据库如果成为主库，那么那个挂掉的机子，就只能认它当老大了

## 哨兵模式

从原本的主从复制来说，如果主机宕掉了，需要手动设置一台从机为一个新的主机，但是这种手动的配置需要人工干预，耗费时间和人力，并且在人工干预的时间内还会造成服务不可用的情况，对于开发者来说这不是一种好的解决方案。Redis从2.8开始出现了哨兵模式

哨兵模式可以理解为谋权篡位的自动版，可以在后台监视主机是否故障，如果发生了故障就会以投票的方式在从机中选出一个作为主机

Redis提供了哨兵的命令，并且它是一个特殊的模式，它会创建出一个完全独立于Redis服务器的进程，**一个哨兵可以对多台服务器进行监控，并且可以有多个哨兵**，每个哨兵会定时发送PING命令给服务器，并且还要在一定时间内得到服务器的响应，得到响应之后哨兵模式才会判定你现在状态正常。如果在规定的时间内它发送的请求主机没有得到响应，那么哨兵便会初步判断，当前主机是**主观下线**，其余的哨兵发现这台主机没有在规定时间内响应数据，那么便会以每秒一次的频率对主机进行判断，它确实是主观下线了，那么主机就会被标记为**客观下线**，主机挂掉之后，哨兵便会通过投票的方式在挂掉的主机下的从机中选出一个作为新主机

> 测试哨兵模式

在Linux中使用编辑器编写一个文件，文件名称叫**sentinel.conf**，这里需要注意，配置文件名称定死，不能乱写

```shell
# 哨兵服务默认端口
port 26379

# 哨兵模式默认工作目录
dir /tmp

# 属性格式：sentinel monitor 主机名称 IP port 1 后面的数字1代表主机挂了，Slave投票让谁成为新主机
sentinel monitor myredis 127.0.0.1 6379 1
```

启动哨兵服务

```shell
redis-sentinel sentinel.conf
[root@bogon bin]# redis-sentinel sentinel.conf 
1616:X 17 Nov 2020 11:11:25.752 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
1616:X 17 Nov 2020 11:11:25.752 # Redis version=5.0.10, bits=64, commit=00000000, modified=0, pid=1616, just started
1616:X 17 Nov 2020 11:11:25.752 # Configuration loaded
1616:X 17 Nov 2020 11:11:25.753 * Increased maximum number of open files to 10032 (it was originally set to 1024).
                _._                                                  
           _.-``__ ''-._                                             
      _.-``    `.  `_.  ''-._           Redis 5.0.10 (00000000/0) 64 bit
  .-`` .-```.  ```\/    _.,_ ''-._                                   
 (    '      ,       .-`  | `,    )     Running in sentinel mode
 |`-._`-...-` __...-.``-._|'` _.-'|     Port: 26379
 |    `-._   `._    /     _.-'    |     PID: 1616
  `-._    `-._  `-./  _.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |           http://redis.io        
  `-._    `-._`-.__.-'_.-'    _.-'                                   
 |`-._`-._    `-.__.-'    _.-'_.-'|                                  
 |    `-._`-._        _.-'_.-'    |                                  
  `-._    `-._`-.__.-'_.-'    _.-'                                   
      `-._    `-.__.-'    _.-'                                       
          `-._        _.-'                                           
              `-.__.-'                                               

1616:X 17 Nov 2020 11:11:25.753 # WARNING: The TCP backlog setting of 511 cannot be enforced because /proc/sys/net/core/somaxconn is set to the lower value of 128.
1616:X 17 Nov 2020 11:11:25.753 # Sentinel ID is 8cfc91b9eb7651b632c2dc77dd1af983c6ccdb78
1616:X 17 Nov 2020 11:11:25.753 # +monitor master myredis 127.0.0.1 6379 quorum 1
1616:X 17 Nov 2020 11:11:25.755 * +slave slave 127.0.0.1:6381 127.0.0.1 6381 @ myredis 127.0.0.1 6379
1616:X 17 Nov 2020 11:11:25.759 * +slave slave 127.0.0.1:6380 127.0.0.1 6380 @ myredis 127.0.0.1 6379
```

如果Master主节点挂掉了，从机中就会随机选择一个出来成为一个新的Master，这是C语言的内部算法，可以在哨兵服务的进程日志中查看新的Master是谁

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123925153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119123941825.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

就算此时原来的Master回来了，在6380面前也只能是个弟弟

> 哨兵模式的优劣

优点：

1、哨兵集群是基于主从复制来实现的，主从复制的优点全部具备

2、主从可以切换，故障可以转移，提升系统可用性

3、哨兵模式就是主从模式的升级，谋权篡位的手动到自动，更加健壮

缺点：

1、在线扩容比较麻烦，集群的数量达到上限，就会变得十分繁琐

2、实现哨兵模式的配置较为麻烦，如果出现故障，还会涉及到一些shell脚本的运行，这些都是非常麻烦的操作

关于哨兵模式的全部配置，有兴趣的建议你们看看这篇博客：https://blog.csdn.net/u012441222/article/details/80751390

# 缓存穿透和雪崩

对于Redis缓存来说，使用Redis的缓存，它提升了应用程序的性能和效率，并且缓存在高并发场景中起到了非常重要的作用，如果针对数据的一致性来说，Redis的缓存就是一个非常致命的问题，这种问题有三个。

## 缓存穿透

在大多数场景中，数据库里的id字段一般来说都是自增。如果说，用户发送了一个请求，会首先进入缓存，查不到，就进入数据库，进了数据库，也查不到，查不到的话，如果说访问量较少，那还好，直接返回不存在嘛，因为这种少量的缓存穿透实际上是不可避免的，但是，一旦有一些不怀好意的坏蛋，在发送请求查询数据库的时候，主键的id字段故意给你来一个负数或者是一些远大于数据库最大id的数，而且进行巨大量的并发访问，这时候缓存中肯定是没有的，那这些请求就直接压给数据库了，数据库扛不住这么大的东西呀，那咋办，不解决数据库就只能挂掉呀。对此，有三种解决方案

1、在进行项目的整合时需要使用到API接口层，在接口层中定义自己的规则，对于不合法的参数可以直接返回，对于调用此接口的API的对象进行严查，任何可能发生的情况都要考虑到

2、在缓存中设置一个空对象，使用空对象完成后续请求

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119124016698.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

3、使用一个东西，叫做布隆过滤器（Bloom Filter），布隆过滤器使用的是一个bit数组和一个hash算法实现的数据结构，并且内存的使用率极低。使用布隆过滤器可以快速判断当前key是否存在，和Java的Optional类有点相似，**布隆过滤器告诉你这个key不存在，那么它就一定不存在**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119124045179.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

## 缓存击穿

缓存击穿，它是缓存穿透的一种特殊情况，一般情况下没有公司会去实现这样的业务，因为没有这样一条非常非常高频的热点数据能够搞垮一台服务器，可能性是非常小的

举个栗子，如果有一个非常高频的热点key，在某一个时刻过期，与此同时又有非常非常多的请求并发访问这个key，因为缓存时间已过，现在全部的请求又开始全部压在数据库上面了，很容易导致服务器挂掉

### 解决方案

可以设置为当前key永不过期，但是不推荐这种做法，因为这样会长期占用Redis的内存空间

还有一种就是Redis的分布式锁，如果说当前有非常非常多的请求传进来，这个时候有分布式锁的保护，可以允许这些请求中的其中一个放进来，缓存找不到就直接查数据库嘛，查完了再把数据放到缓存中让其他的请求直接查缓存即可

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201119124106736.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80NjQ2ODQ3NA==,size_16,color_FFFFFF,t_70#pic_center)

## 缓存雪崩

缓存雪崩指的是，在同一个时间内，一大批的缓存，集体失效，就好像没有被缓存过一样，这个时候假设说有双十一双十二的这种秒杀活动，恰好在这个时刻缓存是成批成批的失效，那么缓存失效，这些请求也还是直接压上数据库的，如果服务器在没有加锁的情况下，这种流量几乎是瞬间达到一个峰值的，对于一个服务器来说，也是会出现宕机的情况

### 解决方案

1、搭建Redis集群，在高可用的情况下，配置多台服务器，在服务器中保存同样的数据，这样即使是集群中的一台甚至是多台挂掉的情况下，也依旧能够正常工作

2、如果不搭建集群，也可以这么做：项目启动时，将数据库和Redis进行数据同步，将数据库中部分的数据信息首先加载进入Redis缓存，并且在加载进入缓存时，可以将这些缓存数据的存活时间设置为随机，并且在数据库和Redis缓存需要在一定的时间之内进行同步更新