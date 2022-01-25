# 约束

- 问题：在往已创建表中新增数据时，可不可以新增两行相同列值的数据?
- 如果可行，会有什么弊端？ 弊端就是数据重复，造成浪费

## 1.实体完整性约束

> 表中的一行数据代表一个实体（entity)，实体完整性的作用即是标识每一行数据不重复、实体唯一。

### 1.1主键约束

> PRIMARY KEY 唯一，标识表中的一行数据，此列的值不可重复，且不能为NULL

```mysql
#为表中适用主键的列添加主键约束
CREATE TABLE subject(
	subjectld INT PRIMARY KEY, #课程编号标识每一个课程的编号唯一，且不能为NULL 
    subjectName VARCHAR(20),
    subjectHours INT 
)charset=utf8;

INSERT INTO subject(subjectld,subjectName,subjectHours) 
VALUES(1,'Java',40);

INSERT INTO subject(subjectld,subjectName,subjectHours) 
VALUES(1,'Java',40);# error 主键1已存在
```

### 1.2唯一约束

> UNIQUE （unique）唯一，标识表中的一行数据，不可重复，可以为NULL

```mysql
#为表中列值不允许重复的列添加睢一约束
CREATE TABLE subject(
subjectld INT PRIMARY KEY,
subjectName VARCHAR(20) UNIQUE,#课程名称唯一！
subjectHours INT
)charset=utf8;

INSERT INTO subject(subjectld,subjectName,subjectHours)
VALUES(1,'Java',40);

INSERT INTO subject(subjectId,sub;jectName,subjectHours) 
VALUES(2,'Java' ,40); #error 课程名称已存在
```

### 1.3自动增长列

> AUTO_NCREMENT (auto_ncrement)自动增长，给主键数值列添加自动增长。从1开始，每次加1。不能单独使用，和主键配合。

```mysql
#为表中主键列添加自动增长，避免忘记主键ID序号
CREATE TABLE subject(
	subjectld INT PRIMARY KEY AUTO_INCREMENT,#课程编号主键且自动增长，会从1幵始根据添加数据的顺序依次加1 
    subjectName VARCHAR(20) UNIQUE, 
    subjectHours INT 
)charset=utf8;

INSERT INTO subject(subjectName,subjectHours) 
VALUES('Java',40);#课程编号自动从1增长 

INSERT INTO subject(subjectName,subjectHours) 
VALUES('JavaScript'，30);# 第二条编号为 2
```

## 2.域完整性约束

> 限制列 的 单元格的数据 正确性。

### 2.1非空约束

> NOT NULL非空，此列必须有值。

```mysql
#课程名称虽然添加了唯一约束，但是有NULL值存在的可能，要避免课程名称为NULL 
CREATE TABLE subject(
	subjectld INT PRIMARY KEY AUTO_INCREMENT, 
    subjectName VARCHAR(20) UNIQUE NOT NULL, 
    subjectHours INT
)charset=utf8;

INSERT INTO subject(subjectName, subjectHours) 
VALUES(NULL,40) ;#error,课程名称约束了非空
```

### 2.2默认值约束

> DEFAULT值为列赋予默认值，当新增数据不指定值时，书写DEFAULT,以指定的默认值进行填充。

```mysql
#当存储课程信息时，若课程时长没有指定值，则以默认课时20填充
CREATE TABLE subject(
	subjectld INT PRIMARY KEY AUTO_INCREMENT, 
    subjectName VARCHAR(20) UNIQUE NOT NULL,
    subjectHours INT DEFAULT 20 
)charset=utf8;

INSERT INTO subject(subjectName,sub;jectHours)
VALUES ('Java', DEFAULT);# 课程时长以默认值 20 填充 
```

### 2.3引用完整性约束（外键）

> 语法：CONSTRAINT 引用《自定义的》 FOREIGN KEY (自己表的列名）REFERENCES 被引用表名(列名）
>
>  constraint 引用名《自定义的》 foreign key(自己表的列名） references 被引用表名(列名）
>
> 详解：FOREIGN KEY引用外部表的某个列的值，新增数据时，约束此列的值必须是引用表中存在的值。
>
> 外键

```mysql
#创建专业表（主表）
CREATE TABLE Speciality(
	id INT PRIMARY KEY AUTO_INCREMENT,
	SpecialName VARCHAR(20) UNIQUE NOT NULL 
)CHARSET=utf8;

#创建课程表{课程表的Specialld 引用专业表的id) （从表）
CREATE TABLE subject(
	subjectld INT PRIMARY KEY AUTO_INCREMENT, 
    subjectName VARCHAR(20) UNIQUE NOT NULL,
    subjectHours INT DEFAULT 20, 
    specialld INT NOT NULL,
	CONSTRAINT fk_subject_specialld FOREIGN KEY(specialld) REFERENCES Speciality(id) #引用专业表里的 id 作为外键，新增课程 信息时，约束课程所属的专业。
)charset=utf8;

#专业表新增数据
INSERT INTO Speciality(SpecialName) VALUES('Java');
INSERT INTO Speciality(SpecialName) VALUES('HTML');

#课程信息表添加数据  #specialld为1 ，引用的是 Speciality表中的id 
INSERT INTO SUBJECT(subjectName, subjectHours,specialld) VALUES('Javase', 30,1);
#  #specialld为2 ，引用的是 Speciality表中的id 
INSERT INTO SUBJECT(subjectName, subjectHours,specialld) VALUES('css', 30,2);
```

- 注意：当两张表存在引用关系，要执行删除操作，一定要先删除从表（引用表《从表》），再删除主表（被引用表《主表》）

## 3.约束创建整合

 创建带有约束的表。

### 3.1创建表

| 列名      | 数据类型    | 约束           | 说明     |
| --------- | ----------- | -------------- | -------- |
| Gradeld   | INT         | 主键、自动增长 | 班级编号 |
| GradeName | VARCHAR(20) | 睢一、非空     | 班级名称 |

```mysql
CREATE TABLE grade(
	gradeid INT PRIMARY KEY AUTO_INCREMENT,
	gardeName VARCHAR(20) UNIQUE NOT NULL 
)CHARSET = utf8;
```

| 列名        | 数据类型    | 约束                                  | 说明     |
| ----------- | ----------- | ------------------------------------- | -------- |
| studentid   | VARCHAR(50) | 主键                                  | 学号     |
| StudentName | VARCHAR(50) | 非空                                  | 姓名     |
| sex         | CHAR(2)     | 默认填充’男’                          | 性别     |
| borndate    | DATE        | 非空                                  | 生日     |
| phone       | VARCHAR(11) | 无                                    | 电话     |
| Gradeld     | INT         | 非空，外键约束：引用班级表的gradeid。 | 班级编号 |

```mysql
 CREATE TABLE student(
	studentid VARCHAR(50) PRIMARY KEY ,
	studentname VARCHAR(50) NOT NULL,
	sex VARCHAR(2) DEFAULT '男',
	borndate DATE NOT NULL,
	phone VARCHAR(11),
	gradeid INT NOT NULL,
	CONSTRAINT fk_student_gradeid FOREIGN KEY (gradeid) REFERENCES grade(gradeid)
     # 引用 grade 表的 gradeid列的值 作为外键，插入时约束学生的班级编号必须存在
)CHARSET = utf8;
```

- 注意：创建关系表时，一定要先创建主表，再创建从表
- 删除关系表时，先删除从表，再删除主表。