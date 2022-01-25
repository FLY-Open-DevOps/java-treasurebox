## SQL语言

### 概念

- SQL (Structured Query Language) **结构化查询语言**，用于存取数据更新查询和管理关系数据库系统的程序设计语言。
- 经验：通常执行对数据库的“增删改查”称C(Create) R (Read) U (Update) D(Delete)

### MySQL应用

对于数据库的操作，需要在进入MSQ环境下进行指令输入，并在一句指令的未尾使用 ; (英文字符)结束

### 基本命令

- 进入MySQL环境：mysql -uroot -p123456 （-u 用户名 -p密码）

- 查看MySQL中所有数据库：mysql> show databases; 或者 mysql> SHOW DATABASES；

  | 数据库名称         | 描述                                                         |
  | ------------------ | ------------------------------------------------------------ |
  | information_schema | 信息数据库，其中保存着关于所有数据库的信息(元数据)。 元数据是关于数据的数据，如数据库名或表名，列的数据类型,或访问权限等。 |
  | mysql              | 核心数据库，主要负责存储数据库的用户、权限设置、关键字等， 以及需要使用的控制和管理信息，不可以删除。 |
  | performance_schema | 性能优化的数据库，MySQL 5.5版本中新增的一个性能优化的引擎.   |
  | sys                | 系统新据库，MySQL5.7版本中新增的可以快速的了解元数据信息的系统库 便于发现数据库的多样信息，解决性能瓶颈问题 |

- 创建自定义数据库：

  ```sql
  mysql> CREATE DATABASE mydb1;   //创建名为 mydb1 的数据库
  mysql>  CREATE DATABASE mydb2 CHARACTER SET gbk; //创建名为 mydb12的数据库并设置编码
  mysql> CREATE DATABASE IF NOT EXISTS mydb4;  //如果数据库mydb4不存在，就创建
  ```

- 查看数据库创建基本信息：

  ```sql
  mysql> SHOW CREATE DATABASE mydb2;  //查看创建数据库时的基本信息
  ```

- 修改数据库：

  ```sql
  mysql> ALTER DATABASE mydb2 CHARACTER SET utf8; 修改编码为utf-8
  ```

- 删除数据库

  ```sql
  mysql> drop database mydb1; //删除数据库mydb1
  ```

- 查看当前所使用的数据库

  ```sql
  mysql> select database(); //查看当前所使用的数据库
  ```

- 使用数据库

  ```sql
  mysql> use mydb2; //使用数据库mydb2
  ```

## SQL概述

1. 什么是SQL：结构化查询语言(Structured Query Language)。
2. SQL的作用：客户端使用SQL来操作服务器。

> 启动mysql.exe，连接服务器后，就可以使用sql来操作服务器了。
> 将来会使用Java程序连接服务器，然后使用sql来操作服务器。

1. SQL标准(例如SQL99，即1999年制定的标准)：

> 由国际标准化组织(ISO)制定的，对DBMS的统一操作方式(例如相同的语句可以操作：mysql、oracle等)。

1. SQL方言

> 某种DBMS不只会支持SQL标准，而且还会有一些自己独有的语法，这就称之为方言！例如limit语句只在MySQL中可以使用

### SQL语法

1. SQL语句可以在单行或多行书写，以分号结尾
2. 可使用空格和缩进来增强语句的可读性
3. MySQL不区别大小写，建议使用大写

### SQL语句分类(*****)

1. DDL（Data Definition Language）：数据定义语言，用来定义数据库对象：库、表、列等；

> 创建、删除、修改：库、表结构！！！

1. DML（Data Manipulation Language）：数据操作语言，用来定义数据库记录（数据）；

> 增、删、改：表记录

1. DCL（Data Control Language）：数据控制语言，用来定义访问权限和安全级别；
2. DQL*****（Data Query Language）：数据查询语言，用来查询记录（数据）。

ddl：数据库或表的结构操作(****\*)
dml：对表的记录进行更新（增、删、改）(\*****)
dql：对表的记录的查询（*****，难点）
dcl：对用户的创建，及授权！

## Innodb与Myisam引擎的区别与应用场景

##### 区别：

（1）事务处理：

MyISAM是非事务安全型的，而InnoDB是事务安全型的（支持事务处理等高级处理）；

（2）锁机制不同：

MyISAM是表级锁，而InnoDB是行级锁；

（3）select ,update ,insert ,delete 操作：

MyISAM：如果执行大量的SELECT，MyISAM是更好的选择

InnoDB：如果你的数据执行大量的INSERT或UPDATE，出于性能方面的考虑，应该使用InnoDB表

（4）查询表的行数不同：

MyISAM：select count(*) from table,MyISAM只要简单的读出保存好的行数，注意的是，当count(*)语句包含  where条件时，两种表的操作是一样的

InnoDB ： InnoDB 中不保存表的具体行数，也就是说，执行select count(*) from table时，InnoDB要扫描一遍整个表来计算有多少行

（5）外键支持：

mysiam表不支持外键，而InnoDB支持

##### 为什么MyISAM会比Innodb 的查询速度快。

INNODB在做SELECT的时候，要维护的东西比MYISAM引擎多很多；
1）数据块，INNODB要缓存，MYISAM只缓存索引块， 这中间还有换进换出的减少； 
2）innodb寻址要映射到块，再到行，MYISAM 记录的直接是文件的OFFSET，定位比INNODB要快
3）INNODB还需要维护MVCC一致；虽然你的场景没有，但他还是需要去检查和维护

MVCC ( Multi-Version Concurrency Control )多版本并发控制 

##### 应用场景

MyISAM适合：(1)做很多count 的计算；(2)插入不频繁，查询非常频繁；(3)没有事务。

InnoDB适合：(1)可靠性要求比较高，或者要求事务；(2)表更新和查询都相当的频繁，并且行锁定的机会比较大的情况。

#### DDL

1. 数据库

- 查看所有数据库：SHOW DATABASES
- 切换（选择要操作的）数据库：USE 数据库名
- 创建数据库：CREATE DATABASE [IF NOT EXISTS] mydb1 [CHARSET=utf8]
- 删除数据库：DROP DATABASE [IF EXISTS] mydb1
- 修改数据库编码：ALTER DATABASE mydb1 CHARACTER SET utf8

1. 数据类型(列类型)

   int：整型
   double：浮点型，例如double(5,2)表示最多5位，其中必须有2位小数，即最大值为999.99；
   decimal：浮点型，在表单钱方面使用该类型，因为不会出现精度缺失问题；
   char：固定长度字符串类型； char(255)，数据的长度不足指定长度，补足到指定长度！
   varchar：可变长度字符串类型； varchar(65535), zhangSan
   text(clob)：字符串类型；

   > 很小
   > 小
   > 中
   > 大

   blob：字节类型；

   > 很小
   > 小
   > 中
   > 大

   date：日期类型，格式为：yyyy-MM-dd；
   time：时间类型，格式为：hh:mm:ss
   timestamp：时间戳类型；

2. 表

- 创建表：
  CREATE TABLE [IF NOT EXISTS] 表名(ITCAST_0001
  列名 列类型,
  列名 列类型,
  …
  列名 列类型
  );

- 查看当前数据库中所有表名称：SHOW TABLES;

- 查看指定表的创建语句：SHOW CREATE TABLE 表名(了解);

- 查看表结构：DESC 表名;

- 删除表：DROP TABLE 表名;

- 修改表：前缀：ALTER TABLE 表名

  > 修改之添加列：
  > ALTER TABLE 表名 ADD (
  > 列名 列类型,
  > 列名 列类型,
  > …
  > );
  >
  > 修改之修改列类型(如果被修改的列已存在数据，那么新的类型可能会影响到已存在数据)：
  >
  > ALTER TABLE 表名 MODIFY 列名 列类型;
  >
  > 修改之修改列名：ALTER TABLE 表名 CHANGE 原列名 新列名 列类型;
  >
  > 修改之删除列：ALTER TABLE 表名 DROP 列名;
  >
  > 修改表名称：ALTER TABLE 原表名 RENAME TO 新表名;

#### DML

DQL：SELECT * FROM 表名

DML(数据操作语言，它是对表记录的操作(增、删、改)！)

1. 插入数据

- INTERT INTO 表名(列名1,列名2, …) VALUES(列值1, 列值2, …);

  > 在表名后给出要插入的列名，其他没有指定的列等同与插入null值。所以插入记录总是插入一行，不可能是半行。
  > 在VALUES后给出列值，值的顺序和个数必须与前面指定的列对应

- INTERT INTO 表名 VALUES(列值1, 列值2)

  > 没有给出要插入的列，那么表示插入所有列。
  > 值的个数必须是该表列的个数。
  > 值的顺序，必须与表创建时给出的列的顺序相同。

1. 修改数据

- UPDATE 表名 SET 列名1=列值1, 列名2=列值2, … [WHERE 条件]

- 条件(条件可选的)：

  > 条件必须是一个boolean类型的值或表达式：UPDATE t_person SET gender=‘男’, age=age+1 WHERE sid=‘1’;
  > 运算符：=、!=、<>、>、<、>=、<=、BETWEEN…AND、IN(…)、IS NULL、NOT、OR、AND

  WHERE age >= 18 AND age <= 80
  WHERE age BETWEEN 18 AND 80

  WHERE name=‘zhangSan’ OR name=‘liSi’
  WHERE name IN (‘zhangSan’, ‘liSi’)
  WHERE age IS NULL, 不能使用等号
  WHERE age IS NOT NULL

1. 删除数据

- DELETE FROM 表名 [WHERE 条件];
- TRUNCATE TABLE 表名：TRUNCATE是DDL语句，它是先删除drop该表，再create该表。而且无法回滚！！！

------

在数据库中所有的字符串类型，必须使用单引，不能使用双引！
日期类型也要使用单引！

// 插入所有列
INSERT INTO stu(
number, name, age, gender
)
VALUES(
‘ITCAST_0001’, ‘zhangSan’, 28, ‘male’
);

// 插入部分列，没有指定的列默认为NULL值
INSERT INTO stu(
number, name
) VAKLUES(
‘ITCAST_0002’, ‘liSi’
)

// 不给出插入列，那么默认为插入所有列！值的顺序要与创建表时列的顺序相同
INSERT INTO stu VALUES(
‘ITCAST_0003’, ‘wangWu’, 82, ‘female’
);

#### DCL(理解)

- 一个项目创建一个用户！一个项目对应的数据库只有一个！
- 这个用户只能对这个数据库有权限，其他数据库你就操作不了了！

1. 创建用户

- CREATE USER 用户名@IP地址 IDENTIFIED BY ‘密码’;

  > 用户只能在指定的IP地址上登录

- CREATE USER 用户名@’%’ IDENTIFIED BY ‘密码’;

  > 用户可以在任意IP地址上登录

1. 给用户授权

- GRANT 权限1, … , 权限n ON 数据库.* TO 用户名@IP地址

  > 权限、用户、数据库
  > 给用户分派在指定的数据库上的指定的权限
  > 例如；GRANT CREATE,ALTER,DROP,INSERT,UPDATE,DELETE,SELECT ON mydb1.* TO user1@localhost;
  >
  > - 给user1用户分派在mydb1数据库上的create、alter、drop、insert、update、delete、select权限

- GRANT ALL ON 数据库.* TO 用户名@IP地址;

  > 给用户分派指定数据库上的所有权限

1. 撤销授权

- REVOKE 权限1, … , 权限n ON 数据库.* FROM 用户名@IP地址;

  > 撤消指定用户在指定数据库上的指定权限
  > 例如；REVOKE CREATE,ALTER,DROP ON mydb1.* FROM user1@localhost;
  >
  > - 撤消user1用户在mydb1数据库上的create、alter、drop权限

1. 查看权限

- SHOW GRANTS FOR 用户名@IP地址

  > 查看指定用户的权限

1. 删除用户

- DROP USER 用户名@IP地址

#### DQL

*****DQL – 数据查询语言
查询不会修改数据库表记录！

一、 基本查询

1. 字段(列)控制
   1. 查询所有列
      SELECT * FROM 表名;
      SELECT * FROM emp;
      –> 其中“*”表示查询所有列

1. 查询指定列
   SELECT 列1 [, 列2, … 列N] FROM 表名;
   SELECT empno, ename, sal, comm FROM 表名;
2. 完全重复的记录只一次
   当查询结果中的多行记录一模一样时，只显示一行。一般查询所有列时很少会有这种情况，但只查询一列（或几列）时，这总可能就大了！
   SELECT DISTINCT * | 列1 [, 列2, … 列N] FROM 表名;
   SELECT DISTINCT sal FROM emp;
   –> 保查询员工表的工资，如果存在相同的工资只显示一次！
3. 列运算
   I 数量类型的列可以做加、减、乘、除运算
   SELECT sal*1.5 FROM emp;
   SELECT sal+comm FROM emp;

II 字符串类型可以做连续运算
SELECT CONCAT(’$’, sal) FROM emp;

III 转换NULL值
有时需要把NULL转换成其它值，例如com+1000时，如果com列存在NULL值，那么NULL+1000还是NULL，而我们这时希望把NULL当前0来运算。
SELECT IFNULL(comm, 0)+1000 FROM emp;
–> IFNULL(comm, 0)：如果comm中存在NULL值，那么当成0来运算。

IV 给列起别名
你也许已经注意到了，当使用列运算后，查询出的结果集中的列名称很不好看，这时我们需要给列名起个别名，这样在结果集中列名就显示别名了
SELECT IFNULL(comm, 0)+1000 AS 奖金 FROM emp;
–> 其中AS可以省略

1. 条件控制
   1. 条件查询
      与前面介绍的UPDATE和DELETE语句一样，SELECT语句也可以使用WHERE子句来控制记录。

- SELECT empno,ename,sal,comm FROM emp WHERE sal > 10000 AND comm IS NOT NULL;
- SELECT empno,ename,sal FROM emp WHERE sal BETWEEN 20000 AND 30000;
- SELECT empno,ename,job FROM emp WHERE job IN (‘经理’, ‘董事长’);

1. 模糊查询
   当你想查询姓张，并且姓名一共两个字的员工时，这时就可以使用模糊查询

- SELECT * FROM emp WHERE ename LIKE ‘张_’;
  –> 模糊查询需要使用运算符：LIKE，其中_匹配一个任意字符，注意，只匹配一个字符而不是多个。
  –> 上面语句查询的是姓张，名字由两个字组成的员工。
- SELECT * FROM emp WHERE ename LIKE ‘___’; /*姓名由3个字组成的员工*/

如果我们想查询姓张，名字几个字可以的员工时就要使用“%”了。
SELECT * FROM emp WHERE ename LIKE ‘张%’;
–> 其中%匹配0~N个任意字符，所以上面语句查询的是姓张的所有员工。
SELECT * FROM emp WHERE ename LIKE ‘%阿%’;
–> 千万不要认为上面语句是在查询姓名中间带有阿字的员工，因为%匹配0~N个字符，所以姓名以阿开头和结尾的员工也都会查询到。
SELECT * FROM emp WHERE ename LIKE ‘%’;
–> 这个条件等同与不存在，但如果姓名为NULL的查询不出来！

二、排序

1. 升序
   SELECT * FROM WHERE emp ORDER BY sal ASC;
   –> 按sal排序，升序！
   –> 其中ASC是可以省略的
2. 降序
   SELECT * FROM WHERE emp ORDER BY comm DESC;
   –> 按comm排序，降序！
   –> 其中DESC不能省略
3. 使用多列作为排序条件
   SELECT * FROM WHERE emp ORDER BY sal ASC, comm DESC;
   –> 使用sal升序排，如果sal相同时，使用comm的降序排

三、聚合函数
聚合函数用来做某列的纵向运算。

1. COUNT
   SELECT COUNT(*) FROM emp;
   –> 计算emp表中所有列都不为NULL的记录的行数
   SELECT COUNT(comm) FROM emp;
   –> 云计算emp表中comm列不为NULL的记录的行数
2. MAX
   SELECT MAX(sal) FROM emp;
   –> 查询最高工资
3. MIN
   SELECT MIN(sal) FROM emp;
   –> 查询最低工资
4. SUM
   SELECT SUM(sal) FROM emp;
   –> 查询工资合
5. AVG
   SELECT AVG(sal) FROM emp;
   –> 查询平均工资

四、分组查询
分组查询是把记录使用某一列进行分组，然后查询组信息。
例如：查看所有部门的记录数。
SELECT deptno, COUNT(*) FROM emp GROUP BY deptno;
–> 使用deptno分组，查询部门编号和每个部门的记录数
SELECT job, MAX(SAL) FROM emp GROUP BY job;
–> 使用job分组，查询每种工作的最高工资

组条件
以部门分组，查询每组记录数。条件为记录数大于3
SELECT deptno, COUNT(*) FROM emp GROUP BY deptno HAVING COUNT(*) > 3;

五、limit子句(方言)
LIMIT用来限定查询结果的起始行，以及总行数。
例如：查询起始行为第5行，一共查询3行记录
SELECT * FROM emp LIMIT 4, 3;
–> 其中4表示从第5行开始，其中3表示一共查询3行。即第5、6、7行记录。

select * from emp limit 0, 5;

```
1. 一页的记录数：10行
2. 查询第3页
```

select * from emp limit 20, 10;

(当前页-1) * 每页记录数
(3-1) * 10

(17-1) * 8, 8

==============================

select from where group by having order by