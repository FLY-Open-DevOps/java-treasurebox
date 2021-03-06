# 事务【重点】

> 事务原则 ： ACID原则 原子性，一致性，隔离性，持久性 （脏读，幻读…）

**原子性**（Atomicity）

要么都成功，要么都失败

**一致性（Consistency）**

事务前后的数据完整性要保持一致

**持久性（Durability）**–事务提交

事务一旦提交就不可逆转，被持久化到数据库中

**隔离性**

事务产生多并发时，互不干扰



隔离产生的问题

### 脏读：

指一个事务读取了另外一个事务未提交的数据。

### 不可重复读：

在一个事务内读取表中的某一行数据，多次读取结果不同。（这个不一定是错误，只是某些场合不对）

### 虚读(幻读)

是指在一个事务内读取到了别的事务插入的数据，导致前后读取不一致。
（一般是行影响，多了一行）

## 1.模拟转账

生活当中转账是转账方账户扣钱，收账方账户加钱。我们用数据库操作来模拟现实转账。

### 1.1数据库模似转账

```mysql
CREATE TABLE account(
	id INT,
	money INT
)CHARSET = utf8;

#A账户转账给B账户1000元。
#A账户减1000元
UPDATE account SET MONEY = MOMEY-1000 WHERE id=1;

#B账户加1000元
UPDATE account SET MONEY = MONEY+1000 WHERE id=2; 
```

- 上述代码完成了两个账户之间转账的操作。

### 1.2模似转账错误

```mysql
#A账户转账给B账户1000元。
#A账户减1000元
UPDATE account SET MONEY = MONEY-1000 WHERE id=1;

#断电、异常、出错...
#B账户加1000元

UPDATE account SET MONEY = MONEY+1000 WHERE id=2;
```

- 上述代码在减操作后过程中出现了异常或加钱语句出锗，会发现，减钱仍旧是成功的，而加钱失败了!
- 注意：每条SQL语句都是一个独立的操作，一个操作执行完对数据库是永久性的影响。

## 2.事务的概念

- 事务是一个原子操作。是一个最小执行单元。可以甶一个或多个SQL语句组成
- 在同一个事务当中，所有的SQL语句都成功执行时，整 个事务成功，有一个SQL语句执行失败，整个事务都执行失败。

## 3.事务的边界

- 开始：连接到数据库，执行一条DML语句。上一个事务结束后，又输入了一条DML语句，即事务的开始

- 结束:

  - （1）提交：

    a. 显示提交：commit ;

    b. 隐式提交：一条创建、删除的语句，正常退出（客户端退出连接）;

  - 回滚:

    a. 显示回滚：rollback ;

    b. 隐式回滚：非正常退出（断电、宕机），执行了创建、删除的语句，但是失败了，会为这个无效的语句执行回滚。

## 4.事务的原理

- 数据库会为每一个客户端都维护一个空间独立的缓存区(回滚段)
- 一个事务中所有的增删改语句的执行结果都会缓存在回滚段中，只有当事务中所有SQL语句均正常结束（commit)，才会将回滚段中的数据同步到数据库。否则无论因为哪种原因失败，整个事务将回滚 (rollback)

## 5.事务的特性

- Atomicity(原子性）

  表示一个事务内的所有操作是一个整体，要么全部成功，要么全部失败

- Consistency(一致性）

  表示一个事务内有一个操作失败时，所有的更改过的数据都必须回滚到修改前状态

- Isolation (隔离性)

  事务查看数据操作时数据所处的状态，要么是另一并发事务修改它之前的状态，要么是另一事务修改它之后的状态，事务不会查看中间状态的数据。

- Durability (持久性）

  持久性事务完成之后，它对于系统的影响是永久性的。

## 6.事务应用

应用环境：基于增删改语句的操作结果（均返回操作后受影响的行数），可通过程序逻辑手动控制事务提交或回滚

### 6.1事务完成转账

```mysql
#A账户给B账户转账^
#1 .开启事务

START TRANSACTION; |setAutoCommit=0;#禁止自动提交 setAutoCommit=1;#开启自动提交 
#2.事务内数据操作语句
UPDATE ACCOUNT SET MONEY = MONEY-1000 WHERE ID = 1 ;
UPDATE ACCOUNT SET MONEY = MONEY+1000 WHERE ID = 2;

#3.事务内语句都成功了，执行COMMIT;
COMMIT;

#4.事务内如果出现锗误，执行ROLLBACK;
ROLLBACK;
```

- 注意：开启事务后，执行的语句均属于当前事务，成功再执行COMMIT,失败要进行ROLLBACK