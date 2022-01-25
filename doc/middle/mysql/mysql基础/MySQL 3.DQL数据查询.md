# 数据查询【重点】基础

## 1. 数据库表的基本结构

- 关系结构数据库是以表格(Table) 进行数据存储，表格由"行和“列“组成
- 经验：执行查询语句返回的结果集是一张**虚拟表。**

## 2. 基本查询

> 语法: SELECT 列名 FROM 表名 ;

| 关键字 | 描述           |
| ------ | -------------- |
| SELECT | 指定要查询的列 |
| FROM   | 指定要查询的表 |

### 2.1查询部分列

```mysql
#查询员工表中所有员工的编号、名字、邮箱
SELECT employee_id, first_name, email FROM t_employees ;
```

### 2.2查询所有列

```mysql
#査洵员工表中所有员工的所有信息(所有列)
SELECT 所有列的列名 FROM t_employees; 
SELECT * FROM t_employees;
```

- 注意: 生产环境下，优先使用列名查询。*的方式需转换成全列名，效率低，可读性差。

### 2.3对列中的数据进行计算

```sql
#查询员工表中所有员工的编号，名字、年薪
SELECT employee_id,first_name,salary*12 FROM t_employees ;
```

| 算数运算符 | 描述           |
| ---------- | -------------- |
| +          | 两列做加法运算 |
| -          | 两列做减法运算 |
| *          | 两列做乘法运算 |
| /          | 两列做除法运算 |

- 注意：% 是占位符，不是模运算符

### 2.4 列的别名

> 列 as ‘别名’

```mysql
#查询员工表中所有员工的编号、名字、年薪(列名均为中文)
SELECT employee_id as '编号', first_name as '名字', salary*12 as '年薪' FROM t_employees;
```

### 2.5查询结果去重

> distinct 列名

```mysql
#查询员工表中所有经理的ID。
SELECT distinct manager_id FROM t_employees :
```

## 3.排序查询

> 语法：SELECT 列名 FROM 表名 ORDER BY 排序列 [排序规则]

| 排序规则 | 描述                   |
| -------- | ---------------------- |
| ASC      | 对前面排序列做升序排序 |
| DESC     | 对前面排序列做降序排序 |

### 3.1依据单列排序

```mysql
#查询员工的编号，名字，薪资。按照工资高低进行降序排序。 
SELECT employee_id , first_name , salary 
FROM t_employees 
ORDER BY salary DESC;
```

### 3.2依据多列排序

```mysql
#查询员工的编号，名字，薪资。按照工资高低进行升序排序（薪资相同时，按照编号进行升序排序）。
SELECT employee_id , first_name , salary 
FROM t_employees
ORDER BY salary DESC , employee_id ASC;
```

## 4.条件查询

> 语法：SELECT 列名 FROM 表名 WHERE 条件 ;

| 关键字    | 描述                                                   |
| --------- | ------------------------------------------------------ |
| WHERE条件 | 在查询结果中，筛选符合条件的查询结果，条件为布尔表达式 |



### 4.1等值判断（=)

```mysql
#查询薪资是11000的员工信息（编号、名字、薪资）
SELECT employee_id , first_name , salary 
FROM t_employees 
WHERE salary = 11000;
```

- 注意：与java不同（==) ,mysql中等值判断使用=

### 4.2逻辑判断（and、or、not)

```mysql
#查询薪资是11000并且提成是0.30的员工信息（编号、名字、薪资) 
SELECT employee_id , first_name , salary 
FROM t_employees
WHERE salary = 11000 AND commission_pct = 0.30;
```

### 4.3 不等值判断（>、<、>=、<=、！=、<>)

```mysql
#查询员工的薪资在6000〜10000之间的员工信息（编号，名字，薪资>
SELECT employee_id , first_name , salary 
FROM t_employees
WHERE salary >= 6000 AND salary <= 10000;
```

### 4.4 区间判断（between and)

```mysql
#查询员工的薪资在6000〜10000之间的员工信息（编号，名字，薪资>
SELECT employee_id ,first_name , salary 
FROM t_employees
WHERE salary BETWEEN 6000 AND 10000; #闭区间，包含区间边界的两个值
```

- 注：在区间判断语法中，小值在前，大值在后，反之，得不到正确结果

### 4.5 NULL 值判断（IS NULL、IS NOT NULL)

> • IS NULL （是空）用法： where 列名 IS NULL ;
>
> • IS NOT NULL （非空）用法：where 列名 IS NOT NULL ;

```mysql
#查询没有提成的员工信息（编号，名字，薪资，提成）
SELECT employee_id , first_name , salary , commission_pct
FROM t_employees
WHERE oommission.pct IS NULL;
```

### 4.6枚举查询（IN (值1，值2,值3))

```mysql
#查询部门编号为70、80、90的员工信息（编号，名字，薪资，部门编号）,也可用 or 来拼接条件  
SELECT employee_id , first_name , salary , department_id 
FROM t_employees
WHERE department_id IN(70,80,90);
```

- 注：in的查询效率较低，可通过多条件拼接。

### 4.7模糊查询

> • LIKE _ (单个任意字符）
>
> 用法：where 列名 LIKE '张_ ’ ;
>
> • LIKE% (任意长度的任意字符）
>
> 用法：where 列名 LIKE ‘张％’ ;

- 注意：模糊查询只能和LIKE关键字结合使用

```mysql
#查询名字以“L"开头的员工信息（编号，名字，薪资，部门编号）
SELECT employee_id , first_name , salary , department_id
FROM t_employees
WHERE first_name LIKE 'L%';

#查询名字以“L"开头并且长度为4的员工信息（编号，名字，薪资，部门编号) 
SELECT employee_id , first_name , salary , department_id
FROM t_employees 
WHERE first_name LIKE 'L___';
```

### 4.8分支结构查询

```mysql
CASE
    WHEN 条件1 THEN 结果1
    WHEN 条件2 THEN 结果2
    WHEN 条件3 THEN 结果3 
    ELSE 结果 
END
```

- 注意：通过使用CASE END逬行条件判断，每条数据对应生成一个值。
- 经验：类似Java中的switch。

```mysql
#查询员工信息（编号，名字，薪资，薪资级别<对应条件表达式生成>) 
SELECT employee_id , first_name , salary , department_id, 
	CASE
		WHEN salary>=10000 THEN 'A'
		WHEN salary>=8000 AND salary<10000 THEN 'B'
		WHEN salary>=6000 AND salary<8000 THEN 'C'
		WHEN salary>=4000 AND salary<6000 THEN 'D'
		ELSE 'E'
	END as "LEVEL"
FROM t_employees;
```

## 5.时间查询

> 语法：SELECT 时间函数 ([参数列表]) ;

- 经验：执行时间函数查询，会自动生成一张虛表（一行一列)

| 时间函数              | 描述                                   |
| --------------------- | -------------------------------------- |
| SYSDATE()             | 当前系统时间（日、月、年、时、分、秒） |
| CURDATE()             | 获取当前日期                           |
| CURTIMEO              | 获取当前时间                           |
| WEEK(DATE)            | 获取指定日期为一年中的第几周           |
| YEAR(DATE)            | 获取指定日期的年份                     |
| HOUR(TIME)            | 获取指定时间的小时值                   |
| MINUTE(TIME)          | 获取时间的分钟值                       |
| DATEDIFF(DATE1,DATE2) | 获取DATE1和DATE2之间相隔的天数         |
| ADDDATE(DATE,N)       | 计算DATE加上N天后的日期                |

### 5.1获得当前系统时间

```mysql
#查询当前时间 
SELECT SYSDATE()；

#查询当前时间 
SELECT NOW();

#获取当前日期 
SELECT CURDATE()；

#获取当前时间 
SELECT CURTIME()；
```

## 6.字符串查询

> 语法：SELECT 字符串函数（[参数列表]) ;

- 注意：下标从1开始

| 字符串函数                 | 说明                                            |
| -------------------------- | ----------------------------------------------- |
| CONCAT(strl,str2,str…)     | 将多个字符串连接                                |
| INSERT(str,pos,len,newStr) | 将str中指定pos位置幵始len长度的内容替换为newStr |
| LOWER(str)                 | 将指定字符串转换为小写                          |
| UPPER(str)                 | 将指定字符串转换为大写                          |
| SUBSTRING(str,num,len)     | 将str字符串指定num位置开始截取len个内容         |

### 6.1字符串应用

```mysql
#拼接内容
SELECT CONCAT('My','S','QL');

#字符串替换
select insert('这是一个数据库',3,2,'MySql');#结果为这是 mysql 数据库 
     
#指定内容转换为小写
SELECT L0WER('MYSQL');#mysql
             
#指定内容转换为大写
SELECT UPPER('mysql');#MYSQL
             
#指定内容截取
SELECT SUBSTRING('JavaMySQLOracle',5,5);#MySQL
```

## 7.聚合函数

> 语法：SELECT 聚合函数(列名）FROM表名；

- 经验：对多条数据的单列进行统计，返回统计后的一行结果。(求单列所有数据)
- 会自动忽略null值

| 聚合函数 | 说明                     |
| -------- | ------------------------ |
| SUM()    | 求所有行中单列结果的总和 |
| AVG()    | 平均值                   |
| MAX()    | 最大值                   |
| MIN()    | 最小值                   |
| COUNTQ   | 求总行数                 |

### 7.1单列总和

```mysql
#统计所有员工每月的工资总和 
SELECT sum(salary)
FROM  t_employees;
```

### 7.2单列平均值

```mysql
#統计所有员工每月的平均工资 
SELECT AVG(salary)
FROM  t_employees;
```

### 7.3单列最大值

```mysql
#统计所有员工中月薪最高的工资 
SELECT MAX(salary)
FROM  t_employees;
```

### 7.4单列最小值

```mysql
#统计所有员工中月薪最低的工资 
SELECT MIN(salary)
FROM t_employees;
```

### 7.5总行数

```mysql
#统计员工总数
SELECT C0UNT(*) 
FROM t_employees;
 #统计有提成的员工人数  
SELECT COUNT(COMMISSION_PCT) FROM t_employees;
```

- 注意：聚合函数自动忽略null值，不进行统计。

## 8.分组查询

> 语法：SELECT 列名 FROM 表名 WHERE 条件 GROUP BY 分组依据（列）；

| 关键字   | 说明                          |
| -------- | ----------------------------- |
| GROUP BY | 分组依据，必须在WHERE之后生效 |

### 8.1查询各部门的总人数

```mysql
#思路：
#1.按照部门编号进行分组（分组依据是department_id) 
#2.再针对各部门的人数进行统计（count)
SELECT department_id , COUNT(EMPLOYEE_ID)
FROM t_employees
GROUP BY DEPARTMENT_ID;  
```

### 8.2查询各部门的平均工资

```mysql
#思路：
#1.按照部门编号进行分组（分组依据depa「tment_id)
#2.针对每个部门进行平均工资统计（avg)
SELECT department_id , AVG(salary)
FROM t_employees
GROUP BY department_id
```

### 8.3查询各个部门、各个岗位的人数

```mysql
#思路：
#1.按照部门编号进行分组（分组依据<iepartment_id)。
#2.按照岗位名称进行分组（分组依据：job_id)。
#3.针对每个部门中的各个岗位进行人数统计（count)。
SELECT department_id , job_id , COUNT(employee_id)
FROM t_employees
GROUP BY department_id , job_id;
```

### 8.4常见问题

```mysql
#查询各个部门id、总人数、first_name
SELECT department_id , C0UNT(*) , first_name
FROM t_employees
GROUP BY department_id; #error
```

- 注：分组查询中，select显示的列只能是分组依据列，或者聚合函数列，不能出现其他列。

## 9.分组过滤查询

> 语法：SELECT 列名 FROM 表名 WHERE 条件 GROUP BY 分组列 HAVING 过滤规则;

| 关键字         | 说明                               |
| -------------- | ---------------------------------- |
| HAVING过滤规则 | 过滤规则定义对分组后的数据进行过滤 |

### 9.1统计部门的最高工资

```mysql
#统计60、70、90号部门的最高工资 
#思路：
#1).确定分组依据（department_id)
#2).对分组后的数据，过滤出部门编号是60、70、90信息 #3). max ()函数处理

SELECT department_id , MAX(salary) 
FROM t_employees 
GROUP BY department_id 
HAVING department_id in (60,70,90)

# group确定分组依据department-id 
#having过滤出60 70 90部门 
#select查看部门编号和max函数。

#或者
SELECT department_id,MAX(salary)
FROM t_employees
WHERE department_id IN (60,70,90)
GROUP BY department_id;
```

## 10.限定查询

> SELECT 列名 FROM 表名 LIMIT 起始行，查询行数;

| 关键字                        | 说明                         |
| ----------------------------- | ---------------------------- |
| LIMIT offset_start, row_count | 限定查询结果的起始行和总行数 |

### 10.1查询前5行记录

```mysql
#查询表中前五名员工的所有信息
SELECT * FROM t_employees LIMIT 0,5;
```

- 注意：起始行是从0开始，代表了第一行。第二个参数代表的是从指定行开始查询几行

### 10.2查询范围记录

```mysql
#查询表中从第四条开始，查询10行 
SELECT * FROM t_employees LIMIT 3,10;
```

### 10.3 LIMIT典型应用

```mysql
#分页查询：一页显示10条，一共查询三页
#思路：第一页是从0开始，显示10条 
SELECT * FROM LIMIT 0,10；

#第二页是从第10条开始，显示10条
SELECT * FROM LIMIT 10,10;

#第三页是从20条幵始，显示10条 
SELECT * FROM LIMIT 20,10;
```

- 经验：在分页应用场景中，起始行是变化的，但是一页显示的条数是不变的

## 11.查询总结

### 11.1 SQL语句编写顺序

> SELECT 列名 FROM 表名 WHERE 条件 GROUP BY 分组 HAVING过滤条件 ORDER BY排序列（asc|desc) LIMIT起始行，总条数

### 11.2 SQL语句执行顺序

1. FROM ：指定数据来源表
2. WHERE ：对查询数据做第一次过滤
3. GROUP BY ：分组
4. HAVING ：对分组后的数据第二次过滤
5. SELECT ：查询各字段的值
6. ORDER BY ：排序
7. LIMIT ：限定查询结果

# 进阶

## 12.子查询（作为条件判断）

> SELECT 列名 FROM 表名 Where 条件（子查询结果)

### 12.1查询工资大于Bruce的员工信息

```mysql
#1.先查询到B「uce的工资（一行一列）
SELECT SALARY FROM t_employees WHERE FIRST_NAME = 'Bruce';#工资是 6000

#2.查询工资大于Bruce的员工信息
SELECT * FROM t_employees WHERE SALARY > 6000;

#3.将1、2两条语句整合
SELECT * FROM t_employees WHERE SALARY > (SELECT SALARY FROM t_employees WHERE FIRST_NAME = 'Bruce');
```

- 注意：将子查询 ”**一行一列**“ 的结果作为外部查询的条件，做第二次查询
- 子查询得到一行一列的结果才能作为外部查询的 等值判断条件 或 不等值条件判断

## 13.子查询（作为枚举查询条件）

> SELECT 列名 FROM 表名 Where 列名 in (子查询结果);

### 13.1查询与名为’King’同一部门的员工信息

```mysql
#思路：
#1.先查询’King1所在的部门编号（多行单列） 
SELECT department_id FROM t_employees
WHERE last_name = 'King'  //部门编号：80、90 
#2.再查询80、90号部门的员工信息
SELECT employee_id , first_name , salary , department_id 
FROM t_employees
WHERE department_id IN (80,90);

#3.SQL:合并
SELECT employee_id , first_name , salary , department_id 
FROM t_employees
WHERE department_id in (SELECT department_id  FROM t_employees WHERE last_name = ’King’）;#N行一列
```

- 将子查询” **多行一列** “的结果作为外部查询的枚举查询条件，做第二次查询

### 13.2工资高于60部门所有人的信息

```mysql
#1.查询60部门所有人的工资（多行多列）
SELECT SALARY from t_employees WHERE DEPARTMENT_ID = 60;
#2.查询高于60部门所有人的工资的员工信息（高于所有）
select * from t_employees 
where SALARY > ALL(select SALARY from t_employees WHERE DEPARTMENT_ID=60);

#查询高于60部门的工资的员工信息（高于部分）
select * from t_employees 
where SALARY > ANY(select SALARY from t_employees WHERE DEPARTMENT_ID=60);
```

- 注意：当子查询结果集形式为多行单列时可以使用ANY或ALL关键字

## 14.子查询（作为一张表）

> SELECT 列名 FROM (子查询的结果集）WHERE 条件；

### 14.1查询员工表中工资排名前5名的员工信息

```mysql
#思路：
#1.先对所有员工的薪资进行排序（排序后的临时表） 
select employee_id , first_name , salary from t_employees order by salary desc
#2.再查询临时表中前5行员工信息 
select employee_id , first_name , salary from (临时表） limit 0,5;

#SQL:合并
select employee_id , first_name , salary
from (select employee_id , first_name , salary from t_employees order by salary desc) as temp limit 0,5;
```

- 将子査询”**多行多列**“的结果作为外部査询的一张表，做第二次查询。
- 注意：子查询作为临时表，为其赋予一个临时表

## 15.合并查询（了解) 结果合并

> • SELECT * FROM 表名 1 **UNION** SELECT * FROM 表名 2
>
> • SELECT * FROM 表名 1 **UNION ALL** SELECT * FROM 表名 2

### 15.1合并两张表的结果（去除重复记录）

```mysql
#合并两张表的结果，去除重复记录
SELECT * FROM tl UNION SELECT * FROM t2；
```

- 注意：合并结果的两张表，列数必须相同，列的数据类型可以不同
- 合并查询是将查询后的结果合并

### 15.2合并两张表的结果（保留重复记录）

```mysql
#合并两张表的结果，不去除重复记录（显示所有）
SELECT * FROM tl UNION ALL SELECT * FROM t2;
```

- 经验：使用UNION合并结果集，会去除掉两张表中重复的数据

## 16.表连接查询 ：表连接在一起查询

> SELECT 列名 FROM 表1 **连接方式** 表2 **ON** 连接条件

### 16.1 内连接查询（INNER JOIN ON)

```MYSQL
#1.查询所有有部门的员工信息（不包括没有部门的员工）SQL标准
SELECT * FROM t_employees 
INNER JOIN t_jobs 
ON t_employees.JOB_ID = t_jobs.JOB_ID ;

#2.查询所有有部门的员工信息（不包括没有部门的员工）  MYSQL专用的语句：
SELECT * FROM  t_employees,t_jobs WHERE t_employees.JOB_ID = t_jobs.JOB_ID;
```

- 经验：在MySql中，第二种方式也可以作为内连接查询，但是不符合SQL标准 •而第一种属于SQL标准，与其他关系型数据库通用

### 16.2三表连接查询

```mysql
#查询所有员工工号、名字、部门名称、部门所在国家ID

SELECT * FROM t_employees e
INNER JOIN t_departments d
on e.department_id = d.department_id
INNER JOIN t_locations l
ON d.location_id = l.location_id;

#只查询几列信息
SELECT EMPLOYEE_ID, e.DEPARTMENT_ID, d.LOCATION_ID FROM t_employees e
INNER JOIN t_departments d
on e.department_id = d.department_id
INNER JOIN t_locations l
ON d.location_id = l.location_id;
```

### 16.3 左外连接（LEFT JOIN ON)

```mysql
#查询所有员工信息，以及所对应的部门名称（没有部门的员工，也在查询结果中，部门名称以NULL填充）

SELECT e.employee_id , e.first_name , e.salary , d.department_name 
FROM t_employees e
LEFT JOIN t_departments d
ON e.department_id = d.department_id;
```

- 注意：左外连接，是以左表为主表，依次向右匹配，匹配到，返回结果
- 匹配不到，则返回NULL值填充

### 16.4 右外连接（RIGHT JOIN ON)

```mysql
#查询所有部门信息，以及此部门中的所有员工信息（没有员工的部门，也在查询结果中，员工信息以NULL填充） 

SELECT e.employee_id , e.first_name , e.salary , d.department_name 
FROM t_employees e 
RIGHT JOIN t_departments d 
ON e.department_id = d.department_id;
```

- 注意：右外连接，是以右表为主表，依次向左匹配，匹配到，返回结果
- 匹配不到，则返回NULL值填充