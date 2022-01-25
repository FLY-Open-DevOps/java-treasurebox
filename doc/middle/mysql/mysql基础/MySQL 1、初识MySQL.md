## 1、初识MySQL

JavaEE ：企业级Java开发 Web

前端(页面：展示，数据!)

后台(连接点：连接数据库 JDBC,链接前端(控制，控制视图跳转,和给前端传递数据) )

数据库(存数据, Txt, Excel, word)

### 1.1、为什么学习数据库

1. 岗位需求
2. 现在的世界，大数据时代，得数据库者得天下。
3. 被迫需求:存数据
4. 数据库是所有软件体系中最核心的存在DBA

### 1.2、什么是数据库

- 数据库(DB, DataBase)
- 概念：数据仓库，软件,安装在操作系统(window ,linux, mac…）之上。SQL, 可以存储大量的数据。500万!
- 数据库是“按照数据结构来组织”，存储和管理数据的仓库，是一个可以长期存储在计算机内的、有组织的、有共享的、同一管理的数据集合。
- 作用：存储数据，管理数据

### 1.3、数据库分类

#### 网状结构数据库:

- 美国通用电气公司IDS (Integrated Data Store) ，以节点形式存储和访问。

#### 层次结构数据库:

- BM公司MS (nformation Management System)定向有序的树状结构实现存储和访问。

#### 关系型数据库: (SQL)

- MySQL, Oracle, Sql Server, DB2, SQLlite
- 通过表和表之间，行和列之间的关系进行数据的存储，以表格(Table) 存储，多表间建立关联关系，通过分类、合并、连接、选取等运算实现访问。

#### 非关系型数据库: (NoSQL) Not Only

- Redis, MongDB、ElastecSearch
- 非关系型数据库，对象存储，通过对象的自身的属性来决定。
- 数使用哈希表，表中以键值(key-value) 的方式实现特定的键和一个指针指向的特定数据。

### 1.4、DBMS(数据库管理系统)

- 概念：

  数据库管理系统(DataBase Management System, DBMS) :指一种操作和管理数据库的大型软件，用于建立、使用和维护数据库，对数据库进行统一管理和控制， 以保证数据库的安全性和完整性。用户通过数据库管理系统访问数据库中的数据。

- 常见数据库管理系统：

  Oracle： 被认为是业界目前比较成功的关系型数据库管理系统。Oracle数据库可以运行在UNIX、Windows等主流操作系统平台，完全支持所有的工业标准，并获得最高级别的IS0标准安全性认证。

  DB2：JIBM公司的产品， DB2数据库系统采用多进程多线索体系结构，其功能足以满足大中公司的需要，井可灵活地服务于中小型电子商务解决方案。

  SOL Server： Microsoft 公司推出的关系型数据库管理系统。具有使用方便可伸缩性好与相关软件集成程度高等优点。

  SoLLite:应用在手机端的数据库。

### 1.5、MySQL数据库

- MySQL是一个**[关系型数据库管理系统](https://baike.baidu.com/item/关系型数据库管理系统/696511)**，由瑞典MySQL AB 公司开发，属于 [Oracle](https://baike.baidu.com/item/Oracle) 旗下产品。MySQL 是最流行的[关系型数据库管理系统](https://baike.baidu.com/item/关系型数据库管理系统/696511)之一，在 WEB 应用方面，MySQL是最好的 [RDBMS](https://baike.baidu.com/item/RDBMS/1048260) (Relational Database Management System，关系数据库管理系统) 应用软件之一。
- MySQL是一种关系型数据库管理系统，关系数据库将数据保存在不同的表中，而不是将所有数据放在一个大仓库内，这样就增加了速度并提高了灵活性。
- MySQL所使用的 SQL 语言是用于访问[数据库](https://baike.baidu.com/item/数据库/103728)的最常用标准化语言。MySQL 软件采用了双授权政策，分为社区版和商业版，由于其体积小、速度快、总体拥有成本低，尤其是[开放源码](https://baike.baidu.com/item/开放源码/7176422)这一特点，一般中小型网站的开发都选择 MySQL 作为网站数据库。