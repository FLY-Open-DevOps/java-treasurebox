## 1.索引定义

MySQL官方对索引的定义为：**索引（Index）是帮助MySQL高效获取数据的数据结构。**

提取句子主干，就可以得到索引的本质：索引是数据结构。

## 2.索引的作用

- 提高查询速度
- 确保数据的唯一性
- 可以加速表和表之间的连接 , 实现表与表之间的参照完整性
- 使用分组和排序子句进行数据检索时 , 可以显著减少分组和排序的时间
- 全文检索字段进行搜索优化.

## 3.索引的分类

> 在一个表中，主键索引只能有一个，唯一索引可以有多个

- 主键索引(PRIMARY KEY )

  - 唯一的标识，主键不可重复，只能有一一个列作为主键

- 唯一索引(UNIQUE KEY)

  - 避免重复的列出现，唯-索引可以重复，多个列都可以标识位唯一索引

    ```mysql
    CREATE TABLE `Grade`(
        `GradeID` INT(11) AUTO_INCREMENT PRIMARYKEY,
        `GradeName` VARCHAR(32) NOT NULL UNIQUE
        -- 或 UNIQUE KEY `GradeID` (`GradeID`)
    )
    ```

- 常规索引 (KEY/INDEX)

  - 快速定位特定数据

  - 默认的，index, key 关键字来设置

  - 应加在查询找条件的字段

  - 不宜添加太多常规索引,影响数据的插入,删除和修改操作

    ```mysql
    CREATE TABLE `result`(
        -- 省略一些代码
        INDEX/KEY `ind` (`studentNo`,`subjectNo`) -- 创建表时添加
    )
    ```

    ```mysql
    -- 创建后添加
    ALTER TABLE `result` ADD INDEX `ind`(`studentNo`,`subjectNo`);
    ```

- 全文索引 (FullText)

  - 在特定的数据库引擎下才有，MyISAM
  - 快速定位数据

基础语法：

```mysql
-- 索引的使用

-- 1.在创建表的时候给字段增加索引
-- 2、创建完毕后，增加索引

-- 显示所有的索引信息
SHOW INDEX FROM student


-- 增加一个全文索引 索引名 (列名)  ,括号里的是列名，前面是索引名
ALTER TABLE schodl.student ADD FULLTEXT INDEX  'studentname' ( studentName ) ;

-- EXPLAIN 分析sq1执行的状况
EXPLAIN SELECT * FROM student; -- 非全文索引，没有加全文索引的查询
EXPLAIN SELECT * FROM student WHERE MATCH(studentname) AGAINST('刘');
```

## 4.创建索引与基础语法

```mysql
/*
#方法一：创建表时
    　　CREATE TABLE 表名 (
                字段名1  数据类型 [完整性约束条件…],
                字段名2  数据类型 [完整性约束条件…],
                [UNIQUE | FULLTEXT | SPATIAL ]   INDEX | KEY
                [索引名]  (字段名[(长度)]  [ASC |DESC])
                );
#方法二：CREATE在已存在的表上创建索引
        CREATE  [UNIQUE | FULLTEXT | SPATIAL ]  INDEX  索引名
                     ON 表名 (字段名[(长度)]  [ASC |DESC]) ;
#方法三：ALTER TABLE在已存在的表上创建索引
        ALTER TABLE 表名 ADD  [UNIQUE | FULLTEXT | SPATIAL ] INDEX
                             索引名 (字段名[(长度)]  [ASC |DESC]) ;
                            
                            
#删除索引：DROP INDEX 索引名 ON 表名字;
#删除主键索引: ALTER TABLE 表名 DROP PRIMARY KEY;
#显示索引信息: SHOW INDEX FROM student;
*/
 
/*增加全文索引*/
ALTER TABLE `school`.`student` ADD FULLTEXT INDEX `studentname` (`StudentName`);
 
/*EXPLAIN : 分析SQL语句执行性能*/
EXPLAIN SELECT * FROM student WHERE studentno='1000';
 
/*使用全文索引*/
-- 全文搜索通过 MATCH() 函数完成。
-- 搜索字符串作为 against() 的参数被给定。搜索以忽略字母大小写的方式执行。对于表中的每个记录行，MATCH() 返回一个相关性值。即，在搜索字符串与记录行在 MATCH() 列表中指定的列的文本之间的相似性尺度。
EXPLAIN SELECT *FROM student WHERE MATCH(studentname) AGAINST('love');
 
/*
开始之前，先说一下全文索引的版本、存储引擎、数据类型的支持情况
MySQL 5.6 以前的版本，只有 MyISAM 存储引擎支持全文索引；
MySQL 5.6 及以后的版本，MyISAM 和 InnoDB 存储引擎均支持全文索引;
只有字段的数据类型为 char、varchar、text 及其系列才可以建全文索引。
测试或使用全文索引时，要先看一下自己的 MySQL 版本、存储引擎和数据类型是否支持全文索引。
*/
```

## 5.索引原则

- 索引不是越多越好
- 不要对经常变动的数据加索引
- 小数据量的表建议不要加索引
- 索引一般应加在查找条件的字段

## 6.索引的数据结构

```mysql
-- 我们可以在创建上述索引的时候，为其指定索引类型，分两类
hash类型的索引：查询单条快，范围查询慢
btree类型的索引：b+树，层数越多，数据量指数级增长（我们就用它，因为innodb默认支持它）
 
-- 不同的存储引擎支持的索引类型也不一样
InnoDB 支持事务，支持行级别锁定，支持 B-tree、Full-text 等索引，不支持 Hash 索引；
MyISAM 不支持事务，支持表级别锁定，支持 B-tree、Full-text 等索引，不支持 Hash 索引；
Memory 不支持事务，支持表级别锁定，支持 B-tree、Hash 等索引，不支持 Full-text 索引；
NDB 支持事务，支持行级别锁定，支持 Hash 索引，不支持 B-tree、Full-text 等索引；
Archive 不支持事务，支持表级别锁定，不支持 B-tree、Hash、Full-text 等索引；
```

# 案例

## 1.建表app_user

```mysql
CREATE TABLE `app_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT '' COMMENT '用户昵称',
  `email` varchar(50) NOT NULL COMMENT '用户邮箱',
  `phone` varchar(20) DEFAULT '' COMMENT '手机号',
  `gender` tinyint(4) unsigned DEFAULT '0' COMMENT '性别（0:男；1：女）',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `age` tinyint(4) DEFAULT '0' COMMENT '年龄',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='app用户表' 
```

## 2.批量插入数据：100w

```mysql
DROP FUNCTION IF EXISTS mock_data;
DELIMITER $$
CREATE FUNCTION mock_data()
RETURNS INT
BEGIN
  DECLARE num INT DEFAULT 1000000;
  DECLARE i INT DEFAULT 0;
  WHILE i < num DO
   INSERT INTO app_user(`name`, `email`, `phone`, `gender`, `password`, `age`)
    VALUES(CONCAT('用户', i), '24736743@qq.com', CONCAT('18', FLOOR(RAND()*(999999999-100000000)+100000000)),FLOOR(RAND()*2),UUID(), FLOOR(RAND()*100));
   SET i = i + 1;
  END WHILE;
  RETURN i;
END;
SELECT mock_data();
```

## 3.索引效率测试

### 无索引

```mysql
SELECT * FROM app_user WHERE name = '用户9999'; -- 查看耗时 0.993 sec
SELECT * FROM app_user WHERE name = '用户9999'; -- 1.098 sec
SELECT * FROM app_user WHERE name = '用户9999'; -- 0.798 sec
 
mysql> EXPLAIN SELECT * FROM app_user WHERE name = '用户9999'\G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: app_user
   partitions: NULL
         type: ALL
possible_keys: NULL
          key: NULL
      key_len: NULL
          ref: NULL
         rows: 992759
     filtered: 10.00
        Extra: Using where
1 row in set, 1 warning (0.00 sec)
```

### 创建索引

```mysql
CREATE INDEX idx_app_user_name ON app_user(name);  -- 常规(普通)索引
```

### 测试常规(普通)索引

```mysql
mysql> EXPLAIN SELECT * FROM app_user WHERE name = '用户9999'\G
*************************** 1. row ***************************
           id: 1
  select_type: SIMPLE
        table: app_user
   partitions: NULL
         type: ref
possible_keys: idx_app_user_name
          key: idx_app_user_name
      key_len: 203
          ref: const
         rows: 1
     filtered: 100.00
        Extra: NULL
1 row in set, 1 warning (0.00 sec)
 
mysql> SELECT * FROM app_user WHERE name = '用户9999'; -- 0.001 sec
1 row in set (0.00 sec)
 
mysql> SELECT * FROM app_user WHERE name = '用户9999';
1 row in set (0.00 sec)
 
mysql> SELECT * FROM app_user WHERE name = '用户9999';
1 row in set (0.00 sec)
```