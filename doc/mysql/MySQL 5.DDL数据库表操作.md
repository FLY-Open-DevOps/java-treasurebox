# DDL数据表操作

## 1.数据类型

> MySQL支持多种类型，大致可以分为三类：数值、日期/时间和字符串(字符)类型。对于我们约束数据的类型有很大的帮助

### 1.1数值类型

| 类型              | 大小                               | 范围（有符号）                                 | 范围{无符号）                | 用途            |
| ----------------- | ---------------------------------- | ---------------------------------------------- | ---------------------------- | --------------- |
| **INT**           | 4字节                              | (-2 147 483 648, 2 147 483 647)                | (0，4 294 967 295)           | 大整数值        |
| DOUBLE            | 8字节                              | (-1.797E+308,-2.22E-308)                       | (0,2.22E-308,1-797 E+308)    | 双精度浮点数 值 |
| **DOUBLE(M,D) **  | 8个字节，M表示长度，D表示小数 位数 | 同上，受M 和D的约束 DOUBLE(5,2)-999.99- 999.99 | 同上，受M和D的约束           | 双精度浮点数 值 |
| **DECIMAL(M,D) ** | DECIMAL(M,D)                       | 依赖于M和D的值，M最大值为65                    | 依赖于M和D的值，M最大值为 65 | 小数值          |

### 1.2日期类型

| 类型         | 大小 | 范围                                                         | 格式                 | 用途                      |
| ------------ | ---- | ------------------------------------------------------------ | -------------------- | ------------------------- |
| **DATE**     | 3    | 1000-01-01/9999-12-31                                        | YYYY-MM-DD           | 日期值                    |
| TIME         | 3    | '-838:59:59 '/ ‘838:59:59’                                   | HH:MM:SS             | 时间值或持续时间          |
| YEAR         | 1    | 1901/2155                                                    | YYYY                 | 年份值                    |
| **DATETIME** | 8    | 1000-01-01 00:00:00/9999-12-31 23:59:59                      | YYYY-MM- DD HH:MM:SS | 混合日期和时 间值         |
| TIMESTAMP    | 4    | 1970-01-01 00:00:00/2038 结束时间是第 2147483647 秒 北京时间 2038-1-1911:14:07，格林尼治时间2038年1月19日凌晨03:14:07 | YYYYMMDD HHMMSS      | 混合日期和时 间值，时间戳 |

### 1.3字符串类型

| 类型                       | 大小         | 用途                           |
| -------------------------- | ------------ | ------------------------------ |
| **CHAR **                  | 0-255字符    | 定长字符串char(10) 10个字符    |
| **VARCHAR **               | 0-65535 字节 | 变长字符串varchar(lO) 10个字符 |
| BLOB (binary large object) | 0-65535字节  | 二进制形式的长文本数据         |
| TEXT                       | 0-65535字节  | 长文本数据                     |

- CHAR和VARCHAR类型类似，但它们保存和检索的方式不同。它们的最大长度和是否尾部空格被保留等方面也不同。在存储或检索过程中不进行大小写转换。
- BLOB是一个二进制大对象，可以容纳可变数量的数据。有4种BLOB类型：TINYBLOB、BLOB、MEDIUMBLOB和LONGBLOB。它们只是可容纳值的最大长度不同。

## 2.数据表的创建（CREATE)

> CREATE TABLE 表名（
>
>  列名 数据类型[约束],
>
>  列名 数据类型[约束]，
>
>  …
>
>  列名 数据类型[约束]//最后一列的末尾不加逗号
>
> )[charset=utf8] //可根据需要指定表的字符编码集

### 2.1创建表

| 列名         | 数据类型     | 说明     |
| ------------ | ------------ | -------- |
| subjectld    | INT          | 课程编号 |
| subjectName  | VARCHAR (20) | 课程名称 |
| subjectHours | INT          | 课程时长 |

```mysql
#依据上述表格创建数据表，并向表中插入3条测试语句
CREATE TABLE subject( 
    subjectld INT, 
 	subjectName VARCHAR(20),
 	subjectHours  INT 
)charset=utf8;     

INSERT INTO subject(subjectld,subjectName,subjectHours) 
VALUES(1,'Java',40);

INSERT INTO subject(subjectld,subjectName,subjectHours) 
VALUES(2,'MYSQL',20); 
       
INSERT INTO subject(subjectld,subjectName,subjectHours) 
VALUES(3,'JavaScript',30);
```

## 3.数据表的修改（ALTER)

> ALTER TABLE 表名 操作；

### 3.1向现有表中添加列

```mysql
#在课程表基础上添加gradeld列
ALTER TABLE subject ADD gradeld int;
12
```

### 3.2修改表中的列

```mysql
#修改课程表中课程名称长度为10个字符
ALTER TABLE subject MODIFY subjectName VARCHAR(10);
12
```

- 注意：修改表中的某列时，也要写全列的名字，数据类型，约束(可选)
- modify 只是修改列的数据类型或者约束

### 3.3删除表中的列

```mysql
#删除课程表中gradeld列
ALTER TABLE subject DROP gradeld;
12
```

- 注意：删除列时，每次只能删除一列

### 3.4修改列名

```mysql
#修改课程表中subjectHours列为classHours
ALTER TABLE subject CHANGE subjectHours classHours int ;
12
```

- 注意：修改列名时，在给定列新名称时，要指定列的类型和约束
- change 修改的是列名

### 3.5修改表名

```mysql
#修改课程表的subject为sub 
ALTER TABLE subject rename sub;
12
```

## 4.数据表的删除（DROP)

> DROP TABLE 表名

### 4.1删除学生表

```mysql
#删除学生表
DROP TABLE sub;
```