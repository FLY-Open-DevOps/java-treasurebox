# 视图

## 1.概念

- 视图，虚拟表，从一个表或多个表中查询出来的表，作用和真实表一样，包含一系列带有行和列的数据。
- 视图中，用户可以使用 SELECT语句查询数据，也可以使用INSERT, UPDATE，DELETE修改记录，
- 视图可以使用户操作方便，并保障数据库系统安全。

## 2.视图特点

- 优点：
  - 简单化，数据所见即所得。
  - 安全性，用户只能查询或修改他们所能见到得到的数据。
  - 逻辑独立性，可以屏蔽真实表结构变化带来的影响。
- 缺点：
  - 性能相对较差，简单的查询也会变得稍显复杂。
  - 修改不方便，特变是复杂的聚合视图基本无法修改。

## 3.视图的创建

> 语法：CREATE VIEW 视图名 AS 查询数据源表语句;

### 3.1创建视图

```mysql
#创建t_empInfo的视图，其视图从t_employees表中查询到员工编号、员工姓名、员工邮箱、工资
CREATE VIEW t_empInfo AS
SELECT EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,SALARY from t_employees;
```

### 3.2使用视图

```mysql
#查询t_empInfo视图中编号为101的员工信息
SELECT * FROM t_empInfo where employee_id = '10' ;
```

## 4.视图的修改

- 方式一：CREATE OR REPLACE VIEW 视图名 AS 查询语句 (create or replace view)存在即更新，否则创建
- 方式二：ALTER VIEW 视图名 AS 查询语句

### 4.1修改视图

```mysql
#方式1:如果视图存在则逬行修改，反之，进行创建
CREATE OR REPLACE VIEW t_emplnfo AS
SELECT EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,SALARY,DEPARTMENT_ID from t_employees;

#方式2:直接对已存在的视图进行修改
ALTER VIEW t_emplnfo AS
SELECT EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,SALARY from t_employees;
```

## 5.视图的删除

> DROP VIEW 视图名

### 5.1删除视图

```mysql
#删除视图
DROP VIEW t_emplnfo;
```

- 注意：删除视图不会影响原表

## 6.视图的注意事项

- 视图不会独立存储数据，原表发生改变，视图也发生改变。没有优化任何查询性能。
- 如果视图包含以下结构中的一种，则视图不可更新：
  - 聚合函数的结果
  - DISTINCT去重后的结果
  - GROUP BY分组后的结果
  - HAVING筛选过滤后的结果
  - UNION、UNIONALL联合后的结果