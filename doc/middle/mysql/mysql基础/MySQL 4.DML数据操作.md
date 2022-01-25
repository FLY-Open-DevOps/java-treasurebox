## DML操作【重点】

## 1.新增（INSERT)

> INSERT INTO 表名（列 1，列 2，列 3…) VALUES (值 1，值 2,值 3……)；
>
> INTERT INTO 表名 VALUES (列值1, 列值2,…) 没有给出要插入的列，那么表示插入所有列。值的个数必须是该表列的个数。值的顺序，必须与表创建时给出的列的顺序相同。

### 1.1添加一条信息

```mysql
#添加一条工作岗位信息
INSERT INTO t_jobs(JOB_ID,JOB_TITLE,MIN_SALARY,MAX_SALARY) 
VALUES
('JAVA.Le','JAVA_Lecturer',2500,9000)；

#添加一条员工信息
INSERT INTO 't_employees'
(EMPLOYEE_ID,FIRST_NAME,LAST_NAME,EMAIL,PHONE_NUMBER,HIRE_DATE,JOB_ID,SALARY,COMMISSION_PCT,MANAGER_ID,DEPARTMENT_ID)
VALUES
('194', 'Samuel','McCain','SMCCAIN', '1650.501.3876', '1998-07-01', 'SH_CLERK', ,'3200', NULL, '123', '50');
```

- 注意：表名后的列名和VALUES里的值要一一对应（个数、顺序、类型）

## 2. 修改（UPDATE〉

> UPDATE 表名 SET 列1=新值1 ,列2=新值2,… WHERE 条件;

### 2.1修改一条信息

```mysql
#修改编号为100的员工的工资为25000
UPDATE t_employees SET SALARY = 25000 WHERE EMPLOYEE_ID = 100 ;

#修改编号为135的员工信息岗位编号为ST_MAN，工资为3500
UPDATE t_employees SET JOB_ID = 'ST_MAN' , SALARY = 3500 WHERE EMPLOYEE_ID = 135;
```

- 注意：SET后多个列名=值，绝大多数情况下都要加WHERE条件，指定修改，否则为整表更新

## 3. 删除（DELETE)

> DELETE FROM 表名 WHERE 条件；

### 3.1删除一条信息

```mysql
#删除编号为135的员工
DELETE FROM t_einployees WHERE EMPLOYEE_ID=' 135'；

#删除姓Peter,并且名为Hall的员工
DELETE FROM t_employees WHERE FIRST_NAME = 'Peter' AND LAST_NAME='Hall';
```

- 注意：删除时，如若不加WHERE条件，删除的是整张表的数据 (仅仅只是数据，对表的结构并没有影响)

## 4.清空整表数据（TRUNCATE)

> TRUNCATE TABLE 表名； truncate table 表名;

### 4.1清空整张表

```mysql
#清空t_countries整张表 
TRUNCATE TABLE t_countries;
```

- 注意：与DELETE 不加 WHERE删除整表数据不同，
- TRUNCATE是把表销毁，再按照原表的格式创建一张新表(对表做删除操作)