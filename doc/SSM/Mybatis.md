# MyBatis

## 一、引言

### 1.1什么是框架?

- 软件的半成品，解决了软件开发过程当中的普适性问题，从而简化了开发步骤，提供了开发的效率。

### 1.2什么是ORM框架?

- ORM (Object Relational Mapping) 对象关系映射，将程序中的**一个对象与表中的一行数据一一对应。**
  ORM框架提供了持久化类与表的映射关系，在运行时参照映射文件的信息，**把对象持久化到数据库中。**

### 1.3使用JDBC完成ORM操作的缺点?

- 存在大量的冗余代码。
- 手工创建Connection、 Statement等。
- 手工将结果集封装成实体对象。
- 查询效率低，没有对数据访问进行过优化(Not Cache)。

## 二、MyBatis框架

2.1 什么是Mybatis

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062316463790.png)

- MyBatis本是Apache软件基金会的一个开源项目iBatis, 2010年这个项目由apache software foundation迁移到了Google Code,并且改名为MyBatis。2013年11 月迁移到Github。
- MyBatis是一个**优秀的基于Java的持久层框架**， 支持自定义SQL, 存储过程和高级映射。
- MyBatis对**原有JDBC操作进行了封装**，几乎消除了所有JDBC代码，使开发者只需关注SQL本身。
- MyBatis可以使用简单的XML或Annotation来配置执行SQL, 并**自动完成ORM操作**，将执行结果返回。
- 它支持自定义 SQL、存储过程以及高级映射。MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。

### 2.2 持久化

数据持久化

- 持久化就是将程序的数据在持久状态和瞬时状态转化的过程
- 内存：**断电即失**
- 数据库（Jdbc）,io文件持久化。

**为什么要持久化？**

- 有一些对象，不能让他丢掉
- 内存太贵

### 2.3 持久层

Dao层、Service层、Controller层

- 完成持久化工作的代码块
- 层界限十分明显

### 2.4 为什么需要MyBatis

- 帮助程序员将数据存入到数据库中
- 方便
- 传统的JDBC代码太复杂了，简化，框架，自动化
- 不用MyBatis也可以，技术没有高低之分
- 优点：
  - 简单易学
  - 灵活
  - sql和代码的分离，提高了可维护性。
  - 提供映射标签，支持对象与数据库的orm字段关系映射
  - 提供对象关系映射标签，支持对象关系组建维护
  - 提供xml标签，支持编写动态sql



## 三、MyBatis环境搭建【重点】

### 3.1 pom.xml中引入MyBatis核心依赖

- 在pom.xml中引入相关依赖

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.zssea</groupId>
      <artifactId>mybatis_hello</artifactId>
      <version>1.0-SNAPSHOT</version>
  
      <!-- 导入依赖 -->
      <dependencies>
          <!--MyBatis核心依赖-->
          <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis</artifactId>
              <version>3.4.6</version>
          </dependency>
  
          <!--MySql驱动依赖-->
          <dependency>
              <groupId>mysql</groupId>
              <artifactId>mysql-connector-java</artifactId>
              <version>5.1.47</version>
          </dependency>
      </dependencies>
  
  </project>
  ```

### 4.2创建MyBatis配置文件

- 创建并配置mybatis-config.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <!-- 核心配置信息 -->
      <environments default="zssea_mysql_config">
  
          <!-- 数据库相关配置-->
          <!-- id 是为这个环境配置起一个名字标识，以便于上面引用-->
          <environment id="zssea_mysql_config">
              <!-- 事务控制类型-->
              <transactionManager type="jdbc"></transactionManager>
              <!-- 数据库连接参数,连接池-->
              <dataSource type="org.apache.ibatis.datasource.pooled.PooledDataSourceFactory">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <!-- & 转义 &amp;  -->
                  <property name="url" value="jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true &amp; characterEncoding=utf-8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="123456"/>
              </dataSource>
          </environment>
      </environments>
  </configuration>
  ```

- 注意: mapper.xml默认建议存放在resources中,路径不能以/开头

- MyBatis 可以配置成适应多种环境

  **不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境**

  学会使用配置多套运行环境！

  MyBatis默认的事务管理器就是JDBC ，连接池：POOLED

## 五、MyBatis开发步骤【重点】

### 5.1建表

```sql
create table t_user(
    id int primary key auto_increment,
    username varchar(50),
    password varchar(50),
    gender tinyint,
    regist_time datetime
)default charset =utf8;
```

### 5.2定义实体类

- 定义所需CURD操作的实体类

  ```java
  package com.zssea.entity;
  
  import java.util.Date;
  
  public class User {
      private Integer id;
      private String username;
      private String password;
      private Boolean gender;
      private Date registTime;
  
      public User(){}
      public User(Integer id, String username, String password, Boolean gender, Date registTime) {
          this.id = id;
          this.username = username;
          this.password = password;
          this.gender = gender;
          this.registTime = registTime;
      }
  
      @Override
      public String toString() {
          return "User{" +
                  "id=" + id +
                  ", username='" + username + '\'' +
                  ", password='" + password + '\'' +
                  ", gender=" + gender +
                  ", registTime=" + registTime +
                  '}';
      }
  
      public Integer getId() {
          return id;
      }
  
      public void setId(Integer id) {
          this.id = id;
      }
  
      public String getUsername() {
          return username;
      }
  
      public void setUsername(String username) {
          this.username = username;
      }
  
      public String getPassword() {
          return password;
      }
  
      public void setPassword(String password) {
          this.password = password;
      }
  
      public Boolean getGender() {
          return gender;
      }
  
      public void setGender(Boolean gender) {
          this.gender = gender;
      }
  
      public Date getRegistTime() {
          return registTime;
      }
  
      public void setRegistTime(Date registTime) {
          this.registTime = registTime;
      }
  }
  ```

### 5.3定义DAO接口

- 根据所需DAO定义接口、以及方法

  ```java
  package com.zssea.dao;
  import com.zssea.entity.User;
  public interface UserDAO {
      User queryUserById(Integer id);
  }
  ```

### 5.4编写Mapper.xml

- 在resources目录中创建Mapper.xml文件 (UserDAOMapper.xml)

- **这个Mapper.xml文件就代替了之前我们写的接口DAO的实现类，但要在mybatis-config.xml配置文件中注册之后，才能使用**

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <!--namespace = 所须实现的接口的全限定名，指明是哪个接口的mapper文件 -->
  <mapper namespace="com.zssea.dao.UserDAO">
      <!--描述接口中的方法-->
      <!-- id值为对应的方法名, #{arg0}代表的是方法中的参数 -->
      <select id="queryUserById" resultType="com.zssea.entity.User">
          select id,username,password,gender,regist_time as registTime
          from t_user
          where id=#{arg0}
      </select>
  </mapper>
  ```

### 5.5注册Mapper

- 将Mapper.xml注册到mybatis-config.xml中

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <!-- 核心配置信息 -->
      <environments default="zssea_mysql_config">
          <!-- 数据库相关配置-->
          <!-- id 是为这个环境配置起一个名字标识，以便于上面引用-->
          <environment id="zssea_mysql_config">
              <!-- 事务控制类型-->
              <transactionManager type="jdbc"></transactionManager>
              <!-- 数据库连接参数,连接池-->
              <dataSource type="org.apache.ibatis.datasource.pooled.PooledDataSourceFactory">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <!-- & 转义 &amp;  -->
                  <property name="url" value="jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&amp;characterEncoding=utf-8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="123456"/>
              </dataSource>
          </environment>
      </environments>
  
      <!-- 注册mapper文件-->
      <mappers>
          <mapper resource="UserDAOMapper.xml"></mapper>
      </mappers>
  </configuration>
  ```

### 5.6测试一

- MyBatis的API操作方式

  ```java
  package com.zssea.test;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.entity.User;
  import org.apache.ibatis.io.Resources;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;
  import org.apache.ibatis.session.SqlSessionFactoryBuilder;
  
  import java.io.IOException;
  import java.io.InputStream;
  
  public class TestMyBatis {
      public static void main(String[] args) throws IOException {
          //MyBatis API
          //1.加载配置文件
          InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
  
          //2.构建SqlSessionFactory
          SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  
          //3.通过SqlSessionFactory 创建 SqlSession
          SqlSession sqlSession = sqlSessionFactory.openSession();
  
          //4.通过SqlSession 获得 DAO实现类的对象
          UserDAO mapper = sqlSession.getMapper(UserDAO.class);//获取UserDAO对应的实现类对象
  
          //5.测试查询方法
          User user1 = mapper.queryUserById(1);
          User user2 = mapper.queryUserById(2);
          System.out.println(user1);
          System.out.println(user2);
  
      }
  }
  ```

### 5.7测试二[了解]

- iBatis传统操作方式

  ```java
  package com.zssea.test;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.entity.User;
  import org.apache.ibatis.io.Resources;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;
  import org.apache.ibatis.session.SqlSessionFactoryBuilder;
  
  import java.io.IOException;
  import java.io.InputStream;
  
  public class TestMyBatis {
      public static void main(String[] args) throws IOException {
          //MyBatis API
          //1.加载配置文件
          InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
  
          //2.构建SqlSessionFactory
          SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  
          //3.通过SqlSessionFactory 创建 SqlSession
          SqlSession sqlSession = sqlSessionFactory.openSession();
  
  
          Object o = sqlSession.selectOne("com.zssea.dao.UserDAO.queryUserById", 1);
          User user = (User) o;
          System.out.println(user);
  
  		/*
          //4.通过SqlSession 获得 DAO实现类的对象
          UserDAO mapper = sqlSession.getMapper(UserDAO.class);//获取UserDAO对应的实现类对象
  
          //5.测试查询方法
          User user1 = mapper.queryUserById(1);
          User user2 = mapper.queryUserById(2);
          System.out.println(user1);
          System.out.println(user2);
  		*/
      }
  }
  ```

## 六、细节补充

### 6.1解决mapper.xml存放在resources以外路径中的读取问题

- xxxMapper.xml文件放在com.zssea.dao中（为了结构清晰），会出现不会被编译的问题

- 在pom.xml文件最后追加< build >标签，以便可以将xmI文件复制到classes中，并在程序运行时正确读取。

- 同时mybatis-config.xml里面的注册mapper的路径要改

  ```xml
  <build>
          <!-- 更改maven编译规则,解决xxxMapper.xml存放在resources以外路径中的读取问题 -->
          <resources>
              <resource>
                  <!-- 资源目录 -->
                  <directory>src/main/java</directory>
                  <includes>
                      <include>*.xml</include><!-- 默认（新添加自定义则失效） -->
                      <include>**/*.xml</include><!-- 新添加 */代表1级目录 **/代表多级目录 -->
                  </includes>
                  <filtering>true</filtering>
              </resource>
          </resources>
  </build>
  ```

  ```xml
  <!-- 注册mapper文件-->
      <mappers>
          <!--<mapper resource="UserDAOMapper.xml"></mapper>-->
          <mapper resource="com/zssea/dao/UserDAOMapper.xml"/>
      </mappers>
  ```

- 这样改完运行测试，肯会出现了（Caused by: com.sun.org.apache.xerces.internal.impl.io.MalformedByteSequenceException: 2 字节的 UTF-8 序列的字节 2 无效）异常，这应该是因为改动位置之后，在编译时，UserDAOMapper.xml文件中的中文注释导致乱码异常，删除注释就好；或者重启idea，或者将文件重新保存utf-8格式

### 6.2 properties配置文件

- 对于mybatis-configxml的核心配置中，如果存在需要频繁改动的数据内容，可以提取到jdbc.properties中。

  ```xml
  jdbc.driver=com.mysql.jdbc.Driver
  jdbc.url=jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&characterEncoding=utf-8
  jdbc.username=root
  jdbc.password=123456
  ```

- 修改mybatis-config.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <!--添加jdbc.properties配置文件路径(外部配置、动态替换)-->
      <properties resource="jdbc.properties"/>
  
      <!-- 核心配置信息 -->
      <environments default="zssea_mysql_config">
          <!-- 数据库相关配置-->
          <!-- id 是为这个环境配置起一个名字标识，以便于上面引用-->
          <environment id="zsseaw_mysql_config">
              <!-- 事务控制类型-->
              <transactionManager type="jdbc"></transactionManager>
              <!-- 数据库连接参数,连接池-->
              <dataSource type="org.apache.ibatis.datasource.pooled.PooledDataSourceFactory">
                  <property name="driver" value="${jdbc.driver}"/>
                  <!-- & 转义 &amp;  -->
                  <property name="url" value="${jdbc.url}"/>
                  <property name="username" value="${jdbc.username}"/>
                  <property name="password" value="${jdbc.password}"/>
              </dataSource>
          </environment>
      </environments>
  
      <!-- 注册mapper文件-->
      <mappers>
          <!--<mapper resource="UserDAOMapper.xml"></mapper>-->
          <mapper resource="com/zssea/dao/UserDAOMapper.xml"/>
      </mappers>
  </configuration>
  ```

  - 可以直接引入外部文件
  - 可以在其中增加一些属性配置
  - 如果两个文件有同一个字段，优先使用外部配置文件的



### 6.3类型别名

- 为实体类定义别名，提高书写效率。

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
  
      <!--添加properties配置文件路径(外部配置、动态替换)-->
      <properties resource="jdbc.properties"/>
  
      <!--别名-->
      <typeAliases>
          <!-- 第一种方式，具体指向每个实体类的别名-->
          <!--<typeAlias type="com.zssea.entity.User" alias="user"/>-->
  
          <!-- 第二种方式,定义实体类所在的package,每个实体类都会自动注册一个别名=类名,不区分大小写-->
          <package name="com.zssea.entity"/>
      </typeAliases>
  
  
      <!-- 核心配置信息 -->
      <environments default="zssea_mysql_config">
          <!-- 数据库相关配置-->
          <!-- id 是为这个环境配置起一个名字标识，以便于上面引用-->
          <environment id="zssea_mysql_config">
              <!-- 事务控制类型-->
              <transactionManager type="jdbc"></transactionManager>
              <!-- 数据库连接参数,连接池-->
              <dataSource type="org.apache.ibatis.datasource.pooled.PooledDataSourceFactory">
                  <property name="driver" value="${jdbc.driver}"/>
                  <!-- & 转义 &amp;  -->
                  <property name="url" value="${jdbc.url}"/>
                  <property name="username" value="${jdbc.username}"/>
                  <property name="password" value="${jdbc.password}"/>
              </dataSource>
          </environment>
      </environments>
  
      <!-- 注册mapper文件-->
      <mappers>
          <!--<mapper resource="UserDAOMapper.xml"></mapper>-->
          <mapper resource="com/zssea/dao/UserDAOMapper.xml"/>
      </mappers>
  </configuration>
  ```

  > 在实体类比较少的时候，使用第一种方式。
  >
  > 如果实体类十分多，建议用第二种扫描包的方式。
  >
  > 第一种可以DIY别名，第二种不行，如果非要改，需要在实体上增加注解。

  ```java
  @Alias("author")
  public class Author {
      ...
  }
  ```

### 6.4日志

如果一个数据库操作，出现了异常，我们需要排错，日志就是最好的助手！

曾经：sout、debug

现在：日志工厂

![img](https://img-blog.csdnimg.cn/20200623164920502.png)

- SLF4J
- LOG4J 【掌握】
- LOG4J2
- JDK_LOGGING
- COMMONS_LOGGING
- STDOUT_LOGGING 【掌握】
- NO_LOGGING

在MyBatis中具体使用哪一个日志实现，在设置中设定

**STDOUT_LOGGING**

```xml
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

### 6.5 Log4j

什么是Log4j？

- Log4j是[Apache](https://baike.baidu.com/item/Apache/8512995)的一个开源项目，通过使用Log4j，我们可以控制日志信息输送的目的地是[控制台](https://baike.baidu.com/item/控制台/2438626)、文件、[GUI](https://baike.baidu.com/item/GUI)组件；
- 我们也可以控制每一条日志的输出格式；
- 通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程；
- 最令人感兴趣的就是，这些可以通过一个[配置文件](https://baike.baidu.com/item/配置文件/286550)来灵活地进行配置，而不需要修改应用的代码。

- pom.xml添加log4j依赖

  ```xml
          <!-- 日志依赖：Log4J -->
          <dependency>
              <groupId>log4j</groupId>
              <artifactId>log4j</artifactId>
              <version>1.2.17</version>
          </dependency>
  ```

- 创建并配置log4j.properties

  ```xml
  #将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
  log4j.rootLogger=DEBUG,console,file
  ​
  #控制台输出的相关设置
  log4j.appender.console = org.apache.log4j.ConsoleAppender
  log4j.appender.console.Target = System.out
  log4j.appender.console.Threshold=DEBUG
  log4j.appender.console.layout = org.apache.log4j.PatternLayout
  log4j.appender.console.layout.ConversionPattern=[%c]-%m%n
  #文件输出的相关设置
  log4j.appender.file = org.apache.log4j.RollingFileAppender
  log4j.appender.file.File=./log/rzp.log
  log4j.appender.file.MaxFileSize=10mb
  log4j.appender.file.Threshold=DEBUG
  log4j.appender.file.layout=org.apache.log4j.PatternLayout
  log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n
  #日志输出级别
  log4j.logger.org.mybatis=DEBUG
  log4j.logger.java.sql=DEBUG
  log4j.logger.java.sql.Statement=DEBUG
  log4j.logger.java.sql.ResultSet=DEBUG
  log4j.logger.java.sq1.PreparedStatement=DEBUG# Global logging configuration
  log4j.rootLogger=DEBUG, stdout
  # MyBatis logging configuration...
  log4j.logger.org.mybatis.example.BlogMapper=TRACE
  # Console output...
  log4j.appender.stdout=org.apache.log4j.ConsoleAppender
  log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
  log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
  
  ```

| 级别      | 描述                                                         |
| --------- | ------------------------------------------------------------ |
| ALL LEVEL | 打开所有日志记录开关；是最低等级的，用于打开所有日志记录。   |
| DEBUG     | 输出调试信息；指出细粒度信息事件对调试应用程序是非常有帮助的。 |
| INFO      | 输出提示信息；消息在粗粒度级别上突出强调应用程序的运行过程。 |
| WARN      | 输出警告信息；表明会出现潜在错误的情形。                     |
| ERROR     | 输出错误信息；指出虽然发生错误事件，但仍然不影响系统的继续运行。 |
| FATAL     | 输出致命错误；指出每个严重的错误事件将会导致应用程序的退出。 |
| OFF LEVEL | 关闭所有日志记录开关；是最高等级的，用于关闭所有日志记录。   |

**Log4j简单使用**

1. 在要使用Log4j的类中，导入包 import org.apache.log4j.Logger;

2. 日志对象，参数为当前类的class对象

   ```java
   Logger logger = Logger.getLogger(UserDaoTest.class);
   ```

## 七、MyBatis的CRUD操作【重点】

### 7.1查询

- 标签: < select id="" resultType="">

#### 7.1.1序号参数绑定

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserDAO {
    User queryUserById(Integer id);
    User queryUserByIdAndUsername(Integer id, String username);
}

    <!-- id值为对应的方法名, #{arg0}或者 #{param1}代表的是方法中的参数；arg从0开始，param从1开始 -->
    <select id="queryUserById" resultType="User">
        select id,username,password,gender,regist_time as registTime
        from t_user
        where id=#{arg0}
    </select>

    <select id="queryUserByIdAndUsername" resultType="User">
        select id,username,password,gender,regist_time as registTime
        from t_user
        where id=#{param1} and username=#{param2}
    </select>
```

#### 7.1.2注解参数绑定[推荐]

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserDAO {
    User queryUserByIdAndPassword(@Param("id") Integer id, @Param("password") String password);

}


    <!-- 在UserDAO中方法参数前加上@Param("id") 注解，用其中的标识来指向这个参数-->
    <select id="queryUserByIdAndPassword" resultType="User">
        select id,username,password,gender,regist_time as registTime
        from t_user
        where id=#{id} and password=#{password}
    </select>
```

#### 7.1.3 Map参数绑定

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserDAO {
    User queryUserByIdAndPassword2(Map map);
}



    <!-- 当UserDAO中方法的参数是map集合时，（所需查询的具体条件参数很多时，就把参数放到map集合中）
        ，在这写的是map中的key， map集合是在使用该方法时才创建-->
    <select id="queryUserByIdAndPassword2" resultType="User">
        select id,username,password,gender,regist_time as registTime
        from t_user
        where id=#{id} and password=#{password} <!--通过key获取value-->
    </select>
        
        
        HashMap map = new HashMap();//测试类创建map
        map.put("id",1); //自定义key ，查询语句通过这个key来接收参数
        map.put("password","123");
        User user5 = mapper.queryUserByIdAndPassword2(map);
        System.out.println(user5);
        System.out.println("-----------------");
```

#### 7.1.4对象参数绑定

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserDAO {
    User queryUserByIdAndPassword3(User user);
}



<!-- 当UserDAO中方法的参数是user(实体类对象)时，（把所需查询的具体条件参数放到实体类对象中）
        ，在这写的是实体类对象中的属性名， user(实体类对象)是在使用该方法时才创建-->
    <select id="queryUserByIdAndPassword3" resultType="User">
        select id,username,password,gender,regist_time as registTime
        from t_user
        where id=#{id} and password=#{password}  
        <!-- #{id}取User对象的id属性值、#{password}同理 -->
    </select>
            
            
		User user = new User();
        user.setId(2);
        user.setPassword("1234");
        User user6 = mapper.queryUserByIdAndPassword3(user);
        System.out.println(user6);
```

#### 7.1.5模糊查询

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface UserDAO {
    List<User> queryUserByUsername(@Param("username") String username);
}


    <!-- 模糊查询 用concat拼接-->
    <select id="queryUserByUsername" resultType="User">
        select id,username,password,gender,regist_time as registTime
        from t_user
        where username like concat('%',#{username},'%')
    </select>
    
    
 System.out.println("---------模糊查询--------");
        List<User> users = mapper.queryUserByUsername("sh");
        for (User user7 : users) {
            System.out.println(user7);
        }
```

### 7.2删除

**增删改是要控制事务的，不然表中的数据是不会发生改变的**

事务提交：sqlSession.commit();

事务回滚：sqlSession.rollback();

标签: < delete id="" parameterType="">

```xml
    <!--删除-->
    <delete id="deleteUser" parameterType="int">
        delete from t_user where id = #{id}
    </delete>
```

### 7.3修改

标签: < update id="" parameterType="">

```xml
    <!--修改-->
    <update id="updateUser" parameterType="User">
        update t_user
        set username=#{username},password=#{password},gender=#{gender},regist_time=#{registTime}
        where id=#{id}
    </update>
```

### 7.4添加

标签: < insert id="" parameterType="">

```xml
	<!--新增-->
    <insert id="insertUser" parameterType="User">
        insert into t_user values(#{id},#{username},#{password},#{gender},#{registTime})
    </insert>
```

### 7.5主键回填

当新增数据时，因为id这一列是主键，可以不填，让数据库自动生成，但是在代码中我们希望看到数据库新增的这个主键，这就需要主键回填

标签: < selectKey id="" parameterType="" order=“AFTERIBEFORE”>

#### 7.5.1通过last_insert_id()查询主键

- 自增的主键类型为int
- UserDAOMapper.xml：

```xml
    <!--新增-->
    <insert id="insertUser" parameterType="User">
        <!--主键回填，将新数据的id，存入java对象的和主键相对应的属性中-->
        <!-- order执行顺序（在插入之后执行），resultType结果类型，keyProperty回填属性，指明回填到实体类对象的哪个属性 -->
        <!--last_insert_id() mysql函数，获取刚刚插入的id -->
        <selectKey order="AFTER" resultType="int" keyProperty="id">
            select last_insert_id()
        </selectKey>
        insert into t_user values(#{id},#{username},#{password},#{gender},#{registTime})
    </insert>


 		//新增
        //id,主键自动增长
        User user = new User(null,"zssea_2","123456",false,new Date());
        mapper.insertUser(user);
        //查看回填效果
        System.out.println(user);
        //6.事务提交
        sqlSession.commit();
/*执行结果：
User{id=4, username='zssea_2', password='123456', gender=false, registTime=Tue Sep 01 12:34:49 CST 2020}
*/
```

#### 7.5.2通过uuid()查询主键

- 主键类型不是int,采用varchar
- StudentDAOMapper.xml：

```java
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.StudentDAO">

    <insert id="insertStudent" parameterType="Student">
        <!--非整数类型的主键自增过程-->
        <!--order要在插入之前生成id，keyProperty对应的是实体类的主键属性 -->
        <selectKey order="BEFORE" keyProperty="id" resultType="string">
            select replace(uuid(),'-','')
        </selectKey>
        insert into t_student values(#{id},#{name},#{gender})
    </insert>

</mapper>
    
package com.zssea.test;

import com.zssea.dao.StudentDAO;
import com.zsseazssea.dao.UserDAO;
import com.zssea.entity.Student;
import com.zssea.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TestMyBatis {
    public static void main(String[] args) throws IOException {
        //MyBatis API
        //1.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");

        //2.构建SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //3.通过SqlSessionFactory 创建 SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4.通过SqlSession 获得 DAO实现类的对象
        UserDAO mapper = sqlSession.getMapper(UserDAO.class);//获取UserDAO对应id的实现类对象

        StudentDAO studentMapper = sqlSession.getMapper(StudentDAO.class);
        //5.测试查询等方法
        //id设为null，是为了使用uuid自动生成的id
        Student student = new Student(null, "zssea", false);
        studentMapper.insertStudent(student);
        //查看回填效果
        System.out.println(student);

        //6.事务提交
        sqlSession.commit();
        //事务回滚
        //sqlSession.rollback();
    }
}

/*执行结果
Student{id='b7b2493eec1011ea901a54e1ad756985', name='zssea', gender=false}
*/
```

## 八、MyBatis工具类【重点】

### 8.1封装工具类

- Resource：用于获得读取配置文件的IO对象，耗费资源，建议通过IO一次性读取所有所需要的数据。
- SqlSessionFactory： SqlSession工厂类，内存占用多，耗费资源，建议每个应用只创建一个对象。
- SqlSession： 相当于Connection, 可控制事务，应为线程私有，不被多线程共享。
  - SqlSession 的实例不是线程安全的，因此是不能被共享的。
  - SqlSession每次使用完成后需要正确关闭，这个关闭操作是必须的
- 将获得连接、关闭连接、提交事务、回滚事务、获得接口实现类等方法进行封装。

```java
package com.zssea.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
/*
    1.加载配置文件
    2.构建SqlSessionFactory
    3.通过SqlSessionFactory 创建 SqlSession
    4.事务管理
    5.通过SqlSession 获得 DAO实现类的对象，Mapper获取
*/
public class MyBatisUtil {
    private static SqlSessionFactory sqlSessionFactory;
    //创建ThreadLocal绑定当前线程中的SqlSession对象
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
    static {//加载配置信息
        try {
            //1.加载配置文件
            InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            //2.构建SqlSessionFactory
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //SqlSession 相当于 Connection ，要保证线程唯一，全局不唯一，需要用到ThreadLoacl
    // 3.通过SqlSessionFactory 创建 SqlSession
    public static SqlSession openSession(){
        SqlSession sqlSession = threadLocal.get(); //先获取线程中的，如果没有则创建
        if(sqlSession == null){
            sqlSession = sqlSessionFactory.openSession();
            threadLocal.set(sqlSession);
        }
        return sqlSession;
    }

    //事务提交
    public static void commit(){
        SqlSession sqlSession = openSession();//拿到当前线程绑定的sqlSession
        sqlSession.commit();
        closeSession();
    }

    //事务回滚
    public static void rollback(){
        SqlSession sqlSession = openSession();
        sqlSession.rollback();
        closeSession();
    }

    //事务释放,关闭
    public static void closeSession(){
        SqlSession sqlSession =  threadLocal.get();
        sqlSession.close();
    }

    //5.通过SqlSession 获得 DAO实现类的对象
    public static <T> T getMapper(Class<T> tClass){
        SqlSession sqlSession = openSession();
        return sqlSession.getMapper(tClass);
    }
}
```

### 8.2测试工具类

- 调用MyBatistis中的封装方法。

```java
package com.zssea.test;

import com.zsseazssea.dao.StudentDAO;
import com.zssea.dao.UserDAO;
import com.zssea.entity.Student;
import com.zssea.entity.User;
import com.zssea.util.MyBatisUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TestMyBatis {
    public static void main(String[] args) throws IOException {
        //用工具类编写
        StudentDAO StudentMapper = MyBatisUtil.getMapper(StudentDAO.class);
        Student student = new Student(null, "zssea_2_test", false);
        Integer integer = StudentMapper.insertStudent(student);
        System.out.println(integer);
        MyBatisUtil.commit();

    }
}
```

## 九、ORM映射【重点】

### 9.1 MyBatis自动ORM失效

- MyBatis只能自动维护库表“列名“与"属性名“相同时的一一对应关系，二者不同时，无法自动ORM。

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200903143649380.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 9.2方案一:列的别名

- 在SQL中使用as为查询字段添加列别名，以匹配属性名。

  ```xml
     <!--查询语句中regist_time是数据库表中的列名，registTime是实体类中的属性名 -->
  	<select id="queryUserById" resultType="User">
          select id,username,password,gender,regist_time as registTime
          from t_user
          where id=#{arg0}
      </select>
  ```

### 9.3方案二:结果映射(ResultMap- 查询结果的封装规则)

- 通过< resultMap id="" type="" >映射，匹配列名与属性名。

  ```xml
   	<!-- id=""定义标识，便于引用  type="User"定义对应的实体类-->
  	<resultMap id="user_resultMap" type="User">
        <!--定义更加复杂的 映射规则-->
          <!-- column="数据库中表的列名(如果sql语句中有别名，要写别名)" property="实体类中的属性名"
              id标签是描述主键列的，其他普通列是用result标签描述-->
          <id column="id" property="id"></id>
          <result column="username" property="username"></result>
          <result column="password" property="password"></result>
          <result column="gender" property="gender"></result>
          <result column="regist_time" property="registTime"></result>
      </resultMap>
  
   	<select id="queryUserById" resultMap="user_resultMap">
          select id,username,password,gender,regist_time
          from t_user
          where id=#{arg0}
      </select>
  ```

## 十、MyBatis处理关联关系—多表连接【重点】

实体间的关系:关联关系(拥有has、属于belong)：

- OneToOne：一对一关系(Passenger-- Passport)
- OneToMany： 一对多关系(Employee – Department)
- ManyToMany：多对多关系(Student – Subject)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200903143701345.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 10.1 OneToOne

#### 10.1.1单向

- SQL语句：

  ```sql
  create table t_passengers(
      id int primary key auto_increment,
      name varchar(50),
      sex varchar(1),
      birthday date
  )default charset =utf8;
  
  create table t_passports(
      id int primary key auto_increment,
      nationality varchar(50),
      expire date,
      passenger_id int unique,
      foreign key (passenger_id) references t_passengers(id)
  )default charset =utf8;
  
  insert into t_passengers values(null,'shine_01','f','2018-11-11');
  insert into t_passengers values(null,'shine_02','m','2019-12-12');
  
  insert into t_passports values(null,'China','2030-12-12',1);
  insert into t_passports values(null,'America','2035-12-12',2);
  
  select t_passengers.id,t_passengers.name,t_passengers.sex,t_passengers.birthday,
         t_passports.id as passId,t_passports.nationality,t_passports.expire
  from t_passengers join t_passports
   on t_passengers.id = t_passports.passenger_id
  where t_passengers.id=1
  ```

- PassengerDAO

  ```java
  package com.zssea.dao;
  import com.zssea.entity.Passenger;
  import org.apache.ibatis.annotations.Param;
  
  public interface PassengerDAO {
      //通过旅客id，查询旅客信息及其护照信息
      //这是一个关联查询（及联查询）
      Passenger queryPassengerById(@Param("id") Integer id);
  }
  ```
  
- PassengerDAOMapper.xml

- 注意:指定“一方”关系时(对象) , 使用< association property="" javaType="" >

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.PassengerDAO">
  
      <resultMap id="passenger_passsport" type="Passenger">
          <id column="id" property="id"></id>
          <result column="name" property="name"></result>
          <result column="sex" property="sex"></result>
          <result column="birthday" property="birthday"></result>
  
          <!--association这个标签定义 一对一  的关联关系
              property属性是描述在主表（对应主表的实体类）中对应的从表的属性
              javaType描述的是从表对应的实体类-->
          <association property="passport" javaType="Passport">
              <!-- 下面这些是描述从表对应的实体类中的映射-->
              <id column="passId" property="id"></id>
              <result column="nationality" property="nationality"></result>
              <result column="expire" property="expire"></result>
          </association>
      </resultMap>
  
      <select id="queryPassengerById" resultMap="passenger_passsport">
          select t_passengers.id,t_passengers.name,t_passengers.sex,t_passengers.birthday,
                  t_passports.id as passId,t_passports.nationality,t_passports.expire
          from t_passengers join t_passports
          on t_passengers.id = t_passports.passenger_id
          where t_passengers.id=#{id}
      </select>
  </mapper>
  ```
  
```xml
      <!--mybatis-config.xml 中的配置-->
  	<!-- 注册mapper文件-->
      <mappers>
          <mapper resource="com/zssea/dao/PassengerDAOMapper.xml"/>
      </mappers>
```

  ```java
public class TestMyBatis {
      public static void main(String[] args) throws IOException {
         //测试PassengerDAO 中 一对一关系sql
          PassengerDAO passengerMapper = MyBatisUtil.getMapper(PassengerDAO.class);
          Passenger passenger = passengerMapper.queryPassengerById(1);
          System.out.println("----------------------------");
          System.out.println(passenger);
          System.out.println(passenger.getPassport());
      }
  }
  /* 执行结果：
  ----------------------------
  Passenger{id=1, name='shine_01', sex=false, birthday=Sun Nov 11 00:00:00 CST 2018}
  Passport{id=1, nationality='China', expire=Thu Dec 12 00:00:00 CST 2030}
  */
  ```

#### 10.1.2双向

- 在一对一的双向关系中，实现方式就是在双方的xxMapper.xml中都要定义相关映射关系

- 以下是护照的Mapper文件（PassportDAOMapper.xml）

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.PassportDAO">
  
      <resultMap id="passport_passenger" type="Passport">
          <id column="id" property="id"/>
          <result column="nationality" property="nationality"/>
          <result column="expire" property="expire"/>
  
          <association property="passenger" javaType="Passenger">
              <id column="passenger_id" property="id"/>
              <result column="name" property="name"/>
              <result column="sex" property="sex"/>
              <result column="birthday" property="birthday"/>
          </association>
      </resultMap>
  
      <select id="queryPassportById" resultMap="passport_passenger">
          select t_passports.id,t_passports.nationality,t_passports.expire,
                 t_passengers.id as passenger_id,t_passengers.name,t_passengers.sex,t_passengers.birthday
          from t_passports join t_passengers
          on t_passengers.id = t_passports.passenger_id
          where t_passports.id=#{id}
      </select>
  </mapper>
  ```
  
- PassportDAO

  ```java
  package com.zssea.dao;
  import com.zssea.entity.Passport;
  import org.apache.ibatis.annotations.Param;
  
  public interface PassportDAO {
      Passport queryPassportById(@Param("id") Integer id);
  }
  ```

### 10.2 OneToMany

- 在One的那一方要用List< T>来定义Many的一方

- ```java
  import java.util.List;
  import lombok.Data;
  @Data
  public class Department{
      private Integer id;
      private String name;
      private String locations;
      
      //存储部门中所有员工信息
      private List<Employee> employees;
  }
  
  
  import java.util.List;
  import lombok.Data;
  @Data
  public class department{
      private Integer id;
      private String name;
      private Double salary;
      
      //员工从属的部门信息
      private Department department;
  ```

- SQL语句

  ```sql
  create table t_departments(
        id int primary key auto_increment,
        name varchar(50),
        location varchar(100)
  )default charset =utf8;
  
  create table t_employees(
      id int primary key auto_increment,
      name varchar(50),
      salary double,
      dept_id int,
      foreign key (dept_id) references t_departments(id)
  )default charset =utf8;
  
  insert into t_departments values(1,'教学部','北京'),(2,'研发部','上海');
  insert into t_employees values(1,'shine01',10000.5,1),(2,'shine02',20000.5,1),
                                (3,'张三',9000.5,2),(4,'李四',8000.5,2);
  ```
  
- DepartmentDAOMapper.xml

- 注意:指定“多方"关系时(集合)，使用< collectionon property=”“ ofType="" >

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.DepartmentDAO">
  
      <resultMap id="dept_emp" type="Department">
          <id column="id" property="id"/>
          <result column="name" property="name"/>
          <result column="location" property="location"/>
  
          <!-- collection这个标签定义 一对多（Department 对 Employee） 的关联关系
               property属性是描述在主表（对应主表的实体类 Department）中对应的从表的属性
               ofType属性描述的是集合里的泛型类型，因为多个员工是存放在了一个list集合中-->
          <collection property="employees" ofType="Employee">
              <id column="emp_id" property="id"/>
              <result column="emp_name" property="name"/>
              <result column="salary" property="salary"/>
          </collection>
      </resultMap>
  
      <select id="queryDepartmentById" resultMap="dept_emp">
          <!-- 查询部门，及其所有员工信息-->
          select t_departments.id,t_departments.name,t_departments.location,
                 t_employees.id as emp_id,t_employees.name as emp_name,t_employees.salary
          from t_departments join t_employees
              on t_departments.id = t_employees.dept_id
          where t_departments.id = #{id}
      </select>
  </mapper>
  <!--之后在mybatis-config.xml文件中注册该DepartmentDAOMapper.xml文件，之后测试-->
  ```
  
- EmployeeDAOMapper.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.EmployeeDAO">
  
      <resultMap id="emp_dept" type="Employee">
          <id column="id" property="id"/>
          <result column="name" property="name"/>
          <result column="salary" property="salary"/>
  
          <!--因为在Employee实体类中Department的属性信息直接就是一个对象，不是用集合来存储
              ，所以用association标签， 而在DepartmentDAOMapper.xml中就要用collection标签来关联放在集合中的mployee-->
          <association property="department" javaType="Department">
              <id column="deptId" property="id"/>
              <result column="deptName" property="name"/>
              <result column="location" property="location"/>
          </association>
      </resultMap>
  
      <select id="queryEmployeeById" resultMap="emp_dept">
          <!--查询员工信息 并且 查到对应的部门信息-->
          select t_employees.id,t_employees.name,t_employees.salary,
                  t_departments.id as deptId,t_departments.name as deptName,t_departments.location
          from t_employees join t_departments
          on t_employees.dept_id = t_departments.id
          where t_employees.id = #{id}
      </select>
  </mapper>
  <!--之后在mybatis-config.xml文件中注册该EmployeeDAOMapper.xml文件，之后测试-->
  ```

### 10.3 ManyToMany

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020090314374089.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- SQL语句：

  ```sql
  create table t_students(
      id int primary key auto_increment,
      name varchar(50),
      sex varchar(1)
  )default charset =utf8;
  create table t_subjects(
       id int primary key auto_increment,
       name varchar(50),
       grade int
  )default charset =utf8;
  create table t_stu_sub(
      student_id int,
      subject_id int,
      foreign key (student_id) references t_students(id),
      foreign key (subject_id) references t_subjects(id),
      primary key (student_id,subject_id)
  )default charset =utf8;
  
  insert into t_students values (1,'shine','f'),(2,'zssea','m');
  insert into t_subjects values (1001,'JavaSE',1),(1002,'JavaWeb',2);
  insert into t_stu_sub values (1,1001),(1,1002),(2,1001),(2,1002);
  ```
  
- SubjectDAOMapper.xml

- 注意:指定“多方"关系时(集合)，使用< collection property=”“ ofType="">

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.SubjectDAO">
  
      <resultMap id="sub_stu" type="Subject">
          <id column="id" property="id"/>
          <result column="name" property="name"/>
          <result column="grade" property="grade"/>
  
          <!--因为Subject实体类中 描述Student2时是用list集合来关联的-->
          <collection property="students" ofType="Student2">
              <id column="stuId" property="id"/>
              <result column="stuName" property="name"/>
              <result column="sex" property="sex"/>
          </collection>
      </resultMap>
  
      <select id="querySubjectById" resultMap="sub_stu">
          select t_subjects.id,t_subjects.name,t_subjects.grade,
              t_students.id as stuId,t_students.name as stuName,t_students.sex
          from t_subjects join t_stu_sub
          on t_subjects.id = t_stu_sub.subject_id
          join t_students
          on t_stu_sub.student_id = t_students.id
          where t_subjects.id = #{id};
      </select>
  </mapper>
  ```
  
- Student2DAOMapper.xml

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.zssea.dao.Student2DAO">
  
      <resultMap id="stu_sub" type="Student2">
          <id column="id" property="id"/>
          <result column="name" property="name"/>
          <result column="sex" property="sex"/>
  
          <!--因为 Student2实体类中 描述Subject时是用list集合来关联的-->
          <collection property="subjects" ofType="Subject">
              <id column="subId" property="id"/>
              <result column="subName" property="name"/>
              <result column="grade" property="grade"/>
          </collection>
      </resultMap>
  
      <select id="queryStudent2ById" resultMap="stu_sub">
          select t_students.id,t_students.name,t_students.sex,
                 t_subjects.id as subId,t_subjects.name subName,t_subjects.grade
          from t_students join t_stu_sub
          on t_students.id = t_stu_sub.student_id
          join t_subjects
          on t_stu_sub.subject_id = t_subjects.id
          where t_students.id = #{id}
      </select>
  </mapper>
  ```
  
- mybatis-config.xml

  ```xml
      <!-- 注册mapper文件-->
      <mappers>
          <mapper resource="com/zssea/dao/SubjectDAOMapper.xml"/>
          <mapper resource="com/zssea/dao/Student2DAOMapper.xml"/>
      </mappers>
  ```
  
- TestMyBatis

  ```java
  public class TestMyBatis {
      public static void main(String[] args) throws IOException {
         
          //查询多对多 一个课程对应的选择这门课程的学生
          /*SubjectDAO subjectMapper = MyBatisUtil.getMapper(SubjectDAO.class);
          Subject subject = subjectMapper.querySubjectById(1001);
          System.out.println(subject);
          List<Student2> students = subject.getStudents();
          for (Student2 student : students) {
              System.out.println(student);
          }*/
  
          //查询一个学生及这个学生选择的课程
          Student2DAO student2Mapper = MyBatisUtil.getMapper(Student2DAO.class);
          Student2 student2 = student2Mapper.queryStudent2ById(1);
          System.out.println(student2);
          List<Subject> subjects = student2.getSubjects();
          for (Subject subject : subjects) {
              System.out.println(subject);
          }
      }
  }
  ```

### 10.4关系总结

- 一方，添加集合；多方，添加对象。
- 双方均可建立关系属性，建立关系属性后，对应的Mapper文件 中需使用< ResultMap >完成多表映射。
- 持有对象关系属性，使用< association property=“dept” javaType=“department” >
- 持有集合关系属性，使用< collection property=“emps” ofType=“employee” >

### 10.5 小结

1. 关联 - association 【多对一】
2. 集合 - collection 【一对多】
3. javaType & ofType
   1. JavaType用来指定实体类中的类型
   2. ofType用来指定映射到List或者集合中的pojo类型，泛型中的约束类型



## 十一、动态SQL 【重点】

- MyBatis的映射文件中支持在基础SQL上添加一些逻辑操作，并动态拼接成完整的SQL之后再执行，以达到SQL复用、简化编程的效果。

### 11.1< sql>

- 就是将sql语句的共同部分提取出来，方便引用与修改

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">

    <!-- 抽取重复sql片段 -->
    <sql id="user_field">
        select id,username,password,gender,regist_time as registTime
        from t_user
    </sql>

    <select id="queryUsers" resultType="User">
         <!-- 引用sql片段 -->
        <include refid="user_field"></include>
    </select>
   
    <select id="queryUserById" resultType="User">
        <include refid="user_field"></include>
        where id = #{id}
    </select>

    <select id="queryUserByUsername" resultType="User">
        <include refid="user_field"></include>
        where username = #{username}
    </select>
</mapper>
```

### 11.2 < where>

- 判断查询条件，有哪个用哪个,可用来合并几个近似的sql语句
- where标签：1.补充where关键字，2.识别where子句中如果 以or，and开头，会将or，and去除

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">
	
    <!-- 抽取重复sql片段 -->
    <sql id="user_field">
        select id,username,password,gender,regist_time as registTime
        from t_user
    </sql>
    
    <!--<select id="queryUserById" resultType="User">
        <include refid="user_field"></include>
        where id = #{id}
    </select>

    <select id="queryUserByUsername" resultType="User">
        <include refid="user_field"></include>
        where username = #{username}
    </select>-->

    <!--
        select id,username,password,gender,regist_time as registTime
        from t_user
        where  id = #{id}

        select id,username,password,gender,regist_time as registTime
        from t_user
        where  username = #{username}
    -->
    <select id="queryUser" resultType="User">
        <include refid="user_field"></include>
        where
        <if test="id != null">
            id = #{id}
        </if>
        <if test="username != null">
            username = #{username}
        </if>
    </select>
    
    
    <!--
    	select id,username,password,gender,regist_time as registTime
        from t_user
        where  username = #{username} or gender = #{gender}
    -->
    <select id="queryUser2" resultType="User">
        <include refid="user_field"></include>
         <!-- where标签：
             1. 补充where关键字
             2. 识别where子句中如果 以or，and开头，会将or，and去除
         -->
        <where>
            <if test="username != null">
                username = #{username}
            </if>
            <if test="gender != null">
                or gender = #{gender}
            </if>
        </where>
    </select>
</mapper>
```

### 11.3< set>

- 主要针对更新
- set标签：1.补充set ， 2. 自动将set子句的最后的逗号去除

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">
    
    <update id="updateUser" parameterType="User">
        update t_user
         <!--
               1. 补充set
               2. 自动将set子句的最后的逗号去除
        -->
        <set>
            <if test="username != null">
                username=#{username},
            </if>
            <if test="password != null">
                password=#{password},
            </if>
            <if test="gender != null">
                gender=#{gender},
            </if>
            <if test="registTime != null">
                regist_time=#{registTime}
            </if>
        </set>
        where id = #{id}
    </update>
</mapper>
```

### 11.4< trim>

- < trim prefix=“前缀” suffix=“后缀” prefixOverrides=“去除多余的关键字” suffixOverrides="" > 代替< where>、< set>

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.UserDAO">
  
      <sql id="user_field">
          select id,username,password,gender,regist_time as registTime
          from t_user
      </sql>
  
      <!--
      select id,username,password,gender,regist_time as registTime
          from t_user
          where  username = #{username} or gender = #{gender}
      -->
      <select id="queryUser2" resultType="User">
          <include refid="user_field"></include>
          <!--<where>
              <if test="username != null">
                  username = #{username}
              </if>
              <if test="gender != null">
                  or gender = #{gender}
              </if>
          </where>-->
          
          <!--  prefix="where" 补充where关键字
                prefixOverrides="or|and"  where子句中如果以or或and开头，会被覆盖
          -->
          <trim prefix="where" prefixOverrides="or|and">
              <if test="username != null">
                  username = #{username}
              </if>
              <if test="gender != null">
                  or gender = #{gender}
              </if>
          </trim>
      </select>
  
      
      <update id="updateUser" parameterType="User">
          update t_user
          <!--<set>
              <if test="username != null">
                  username=#{username},
              </if>
              <if test="password != null">
                  password=#{password},
              </if>
              <if test="gender != null">
                  gender=#{gender},
              </if>
              <if test="registTime != null">
                  regist_time=#{registTime}
              </if>
          </set>-->
          
          <!-- prefix="set" 补充一个set
               suffixOverrides=","    自动将set子句的最后的逗号去除
           -->
          <trim prefix="set" suffixOverrides=",">
              <if test="username != null">
                  username=#{username},
              </if>
              <if test="password != null">
                  password=#{password},
              </if>
              <if test="gender != null">
                  gender=#{gender},
              </if>
              <if test="registTime != null">
                  regist_time=#{registTime}
              </if>
          </trim>
          where id = #{id}
      </update>
  </mapper>
  ```

### 11.5< foreach>

- 批量操作

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">

    <delete id="deleteManyUser" parameterType="java.util.List">
        <!-- delete from t_user where id in(x,x,x,x) -->
        delete from t_user where id in
        <foreach collection="list" open="(" close=")" separator="," item="id9">
            #{id9}
        </foreach>
    </delete>

    <insert id="insertManyUser" parameterType="java.util.List">
        <!--insert into t_user values (null,x,x,x,x),(null,x,x,x,x),(null,x,x,x,x)-->
        insert into t_user values
        <foreach collection="list" item="user9" separator=",">
            (null,#{user9.username},#{user9.password},#{user9.gender},#{user9.registTime})
        </foreach>
    </insert>
</mapper>
```

| 参数       | 描述     | 取值                                     |
| ---------- | -------- | ---------------------------------------- |
| collection | 容器类型 | list、 array、 map                       |
| open       | 起始符   | (                                        |
| close      | 结束符   | ）                                       |
| separator  | 分隔符   | ，                                       |
| index      | 下标号   | 从0开始，依次递增                        |
| item       | 当前项   | 任意名称(循环中通过#任意名称}表达式访问) |

总：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">

    <sql id="user_field">
        select id,username,password,gender,regist_time as registTime
        from t_user
    </sql>

    <select id="queryUsers" resultType="User">
        <include refid="user_field"></include>
    </select>
    
    <!--<select id="queryUserById" resultType="User">
        <include refid="user_field"></include>
        where id = #{id}
    </select>

    <select id="queryUserByUsername" resultType="User">
        <include refid="user_field"></include>
        where username = #{username}
    </select>-->

    <!--
        select id,username,password,gender,regist_time as registTime
        from t_user
        where  id = #{id}

        select id,username,password,gender,regist_time as registTime
        from t_user
        where  username = #{username}
    -->
    <select id="queryUser" resultType="User">
        <include refid="user_field"></include>
        where
        <if test="id != null">
            id = #{id}
        </if>
        <if test="username != null">
            username = #{username}
        </if>

    </select>

    <!--
    select id,username,password,gender,regist_time as registTime
        from t_user
        where  username = #{username} or gender = #{gender}
    -->
    <select id="queryUser2" resultType="User">
        <include refid="user_field"></include>
        <!--<where>
            <if test="username != null">
                username = #{username}
            </if>
            <if test="gender != null">
                or gender = #{gender}
            </if>
        </where>-->
        <trim prefix="where" prefixOverrides="or|and">
            <if test="username != null">
                username = #{username}
            </if>
            <if test="gender != null">
                or gender = #{gender}
            </if>
        </trim>
    </select>

    <delete id="deleteUser" parameterType="int">
        delete from t_user where id = #{id}
    </delete>
    
    <update id="updateUser" parameterType="User">
        update t_user
        <!--<set>
            <if test="username != null">
                username=#{username},
            </if>
            <if test="password != null">
                password=#{password},
            </if>
            <if test="gender != null">
                gender=#{gender},
            </if>
            <if test="registTime != null">
                regist_time=#{registTime}
            </if>
        </set>-->
        <trim prefix="set" suffixOverrides=",">
            <if test="username != null">
                username=#{username},
            </if>
            <if test="password != null">
                password=#{password},
            </if>
            <if test="gender != null">
                gender=#{gender},
            </if>
            <if test="registTime != null">
                regist_time=#{registTime}
            </if>
        </trim>
        where id = #{id}
    </update>

    <insert id="insertUser" parameterType="User">

        <selectKey order="AFTER" resultType="int" keyProperty="id">
            select last_insert_id()
        </selectKey>
        insert into t_user values (#{id},#{username},#{password},#{gender},#{registTime});
    </insert>


    <delete id="deleteManyUser" parameterType="java.util.List">
        <!-- delete from t_user where id in(x,x,x,x) -->
        delete from t_user where id in
        <foreach collection="list" open="(" close=")" separator="," item="id9">
            #{id9}
        </foreach>
    </delete>

    <insert id="insertManyUser" parameterType="java.util.List">
        <!--insert into t_user values (null,x,x,x,x),(null,x,x,x,x),(null,x,x,x,x)-->
        insert into t_user values
        <foreach collection="list" item="user9" separator=",">
            (null,#{user9.username},#{user9.password},#{user9.gender},#{user9.registTime})
        </foreach>
    </insert>
</mapper>



package com.zssea.test;

import com.zssea.dao.UserDAO;
import com.zssea.entity.User;
import com.zssea.util.MyBatisUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class TestMyBatis {

    @Test
    public void test1(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);
       /* User user = userMapper.queryUserById(1);
        System.out.println(user);*/
        System.out.println("----------------------");
        List<User> users = userMapper.queryUsers();
        for (User user1 : users) {
            System.out.println(user1);
        }

    }

    @Test
    public void test2(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);
        User user = new User();
        //user.setId(3);
        user.setUsername("zssea_2");
        User user1 = userMapper.queryUser(user);
        System.out.println(user1);

    }
    @Test
    public void test3(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);

        User user = new User();
        user.setUsername("zssea");
        user.setGender(false);
        List<User> users = userMapper.queryUser2(user);
        for (User user1 : users) {
            System.out.println(user1);
        }

    }

    @Test
    public void test4(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);

        User user = new User(1,"shine","1234578",true,null);
        Integer integer = userMapper.updateUser(user);
        System.out.println(integer);
        MyBatisUtil.commit();

    }

    @Test
    public void test5(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);
        List<Integer> ids = Arrays.asList(1, 3, 4);
        userMapper.deleteManyUser(ids);
        MyBatisUtil.commit();

    }
    @Test
    public void test6(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);
        List<User> users = Arrays.asList(new User(null, "shine", "12396", true, new Date()),
                new User(null, "zssea", "789456", true, new Date()));
        userMapper.insertManyUser(users);
        MyBatisUtil.commit();


    }
}
```

## 十二、缓存(Cache) 【重点】

- 内存中的一块存储空间，服务于某个应用程序，旨在将频繁读取的数据临时保存在内存中，便于二次快速访问。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020090314381054.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 12.1 一级缓存

- SqlSession级别的缓存，同一个SqlSession的发起多次同构查询，会将数据保存在一级缓存中。

- 注意:无需任何配置，默认开启一级缓存。

  ```java
  @Test
      public void test7(){
          //查看一级缓存
          SqlSession sqlSession = MyBatisUtil.openSession();
          UserDAO userMapper = sqlSession.getMapper(UserDAO.class);
          List<User> users = userMapper.queryUsers();
          System.out.println("=============================");
          List<User> user2 = userMapper.queryUsers();
          //上面的输出结果只有一次sql查询
  
          System.out.println("=============================");
          SqlSession sqlSession2 = MyBatisUtil.getSession(); //另外一个SqlSession
          UserDAO userMapper2 = sqlSession2.getMapper(UserDAO.class);
          List<User> users1 = userMapper2.queryUsers();
          //这次会再次出现一次查询
  
      }
  ```

### 12.2二级缓存

- SqlSessionFactory级别的缓存，同一个SqlSessionFactory构建的SqlSession发起的多次同构查询，会将数据保存在二级缓存中。（全局缓存）
- 注意: 在sqlSession.commit()或者sqlSession.close()之后生效。

#### 12.2.1开启全局缓存

- < setings >是MyBatis中极为重要的调整设置，他们会改变MyBatis的运行行为， 其他详细配置可参考官方文档。
- mybatis-config.xml：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!--configuration 中的子标签的使用是有顺序的，顺序如下：
        (properties?, settings?, typeAliases?, typeHandlers?,
         objectFactory?, objectWrapperFactory?, reflectorFactory?,
         plugins?, environments?, databaseIdProvider?, mappers?)
     -->

    <!--添加properties配置文件路径(外部配置、动态替换)-->
    <properties resource="jdbc.properties"/>

    <!-- 注意标签的使用顺序-->
    <!-- 二级缓存，默认是开启的-->
    <settings>
        <setting name="cacheEnabled" value="true"/>
    </settings>

    <!--别名-->
    <typeAliases>
        <!-- 第二种方式,定义实体类所在的package,每个实体类都会自动注册一个别名=类名,不区分大小写-->
        <package name="com.zssea.entity"/>
    </typeAliases>


    <!-- 核心配置信息 -->
    <environments default="zssea_mysql_config">
        ...
    </environments>

    <!-- 注册mapper文件-->
    <mappers>
       ...
    </mappers>
</configuration>
```

#### 12.2.2指定Mapper缓存

- 在相应的xxxMapper.xml文件中： （UserDAOMapper.xml）

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">
    <!-- 虽然二级缓存是默认开启，但并不是所有的查询结果都会放到二级缓存中
		用下面这个标签，这个文件对应的查询方法所得到的结果才会放到二级缓存中-->
    <cache/>
    
    <sql id="user_field">
        select id,username,password,gender,regist_time as registTime
        from t_user
    </sql>

    <select id="queryUsers" resultType="User">
        <include refid="user_field"></include>
    </select>
    
    <!--<select id="queryUserById" resultType="User">
        <include refid="user_field"></include>
        where id = #{id}
    </select>

    <select id="queryUserByUsername" resultType="User">
        <include refid="user_field"></include>
        where username = #{username}
    </select>-->

    <!--
        select id,username,password,gender,regist_time as registTime
        from t_user
        where  id = #{id}

        select id,username,password,gender,regist_time as registTime
        from t_user
        where  username = #{username}
    -->
    <select id="queryUser" resultType="User">
        <include refid="user_field"></include>
        where
        <if test="id != null">
            id = #{id}
        </if>
        <if test="username != null">
            username = #{username}
        </if>

    </select>

    <!--
    select id,username,password,gender,regist_time as registTime
        from t_user
        where  username = #{username} or gender = #{gender}
    -->
    <select id="queryUser2" resultType="User">
        <include refid="user_field"></include>
        <!--<where>
            <if test="username != null">
                username = #{username}
            </if>
            <if test="gender != null">
                or gender = #{gender}
            </if>
        </where>-->
        <trim prefix="where" prefixOverrides="or|and">
            <if test="username != null">
                username = #{username}
            </if>
            <if test="gender != null">
                or gender = #{gender}
            </if>
        </trim>
    </select>

    <delete id="deleteUser" parameterType="int">
        delete from t_user where id = #{id}
    </delete>
    
    <update id="updateUser" parameterType="User">
        update t_user
        <!--<set>
            <if test="username != null">
                username=#{username},
            </if>
            <if test="password != null">
                password=#{password},
            </if>
            <if test="gender != null">
                gender=#{gender},
            </if>
            <if test="registTime != null">
                regist_time=#{registTime}
            </if>
        </set>-->
        <trim prefix="set" suffixOverrides=",">
            <if test="username != null">
                username=#{username},
            </if>
            <if test="password != null">
                password=#{password},
            </if>
            <if test="gender != null">
                gender=#{gender},
            </if>
            <if test="registTime != null">
                regist_time=#{registTime}
            </if>
        </trim>
        where id = #{id}
    </update>

    <insert id="insertUser" parameterType="User">

        <selectKey order="AFTER" resultType="int" keyProperty="id">
            select last_insert_id()
        </selectKey>
        insert into t_user values (#{id},#{username},#{password},#{gender},#{registTime});
    </insert>


    <delete id="deleteManyUser" parameterType="java.util.List">
        <!-- delete from t_user where id in(x,x,x,x) -->
        delete from t_user where id in
        <foreach collection="list" open="(" close=")" separator="," item="id9">
            #{id9}
        </foreach>
    </delete>

    <insert id="insertManyUser" parameterType="java.util.List">
        <!--insert into t_user values (null,x,x,x,x),(null,x,x,x,x),(null,x,x,x,x)-->
        insert into t_user values
        <foreach collection="list" item="user9" separator=",">
            (null,#{user9.username},#{user9.password},#{user9.gender},#{user9.registTime})
        </foreach>
    </insert>
</mapper>
```

- 测试 TestMyBatis：

```java
@Test
    public void test8(){
        //查看二级缓存
        //通过相同的SqlSessionFactory获取多个sqlsession
        SqlSession sqlsession1 = MyBatisUtil.getSession();
        SqlSession sqlsession2 = MyBatisUtil.getSession();
        SqlSession sqlsession3 = MyBatisUtil.getSession();

        UserDAO mapper1 = sqlsession1.getMapper(UserDAO.class);
        UserDAO mapper2 = sqlsession2.getMapper(UserDAO.class);
        UserDAO mapper3 = sqlsession3.getMapper(UserDAO.class);

        List<User> users1 = mapper1.queryUsers();
        sqlsession1.close();//只有当这个sqlsession关闭之后，mybatis才会把所查到的数据放到二级缓存中
        System.out.println("======================");
        List<User> users2 = mapper2.queryUsers();
        sqlsession2.close();
        System.out.println("======================");
        List<User> users3 = mapper3.queryUsers();
        sqlsession3.close();

    }
/*
DEBUG [main] - Created connection 673186785.
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - ==>  Preparing: select id,username,password,gender,regist_time as registTime from t_user 
DEBUG [main] - ==> Parameters: 
DEBUG [main] - <==      Total: 5
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Returned connection 673186785 to pool.
======================
DEBUG [main] - Cache Hit Ratio [com.zssea.dao.UserDAO]: 0.5 （命中率）
======================
DEBUG [main] - Cache Hit Ratio [com.zssea.dao.UserDAO]: 0.6666666666666666
*/
```

- 注意点：所有的缓存都是临时数据，具体数据还是要以数据中的数据为准，所有当数据库中的数据发生增删改之后，缓存中的相关数据也就成了脏数据，但Mybatis会帮我们删除这些脏数据

#### 12.2.3缓存清空并重新缓存

```java
	 @Test
    public void test8(){
        //查看二级缓存
        //通过相同的SqlSessionFactory获取多个sqlsession
        SqlSession sqlsession1 = MyBatisUtil.getSession();
        SqlSession sqlsession2 = MyBatisUtil.getSession();
        SqlSession sqlsession3 = MyBatisUtil.getSession();

        UserDAO mapper1 = sqlsession1.getMapper(UserDAO.class);
        UserDAO mapper2 = sqlsession2.getMapper(UserDAO.class);
        UserDAO mapper3 = sqlsession3.getMapper(UserDAO.class);

        List<User> users1 = mapper1.queryUsers();
        sqlsession1.close();//只有当这个sqlsession关闭之后，mybatis才会把所查到的数据放到二级缓存中

        //修改，修改之后，相关的缓存就被清除
        SqlSession sqlsession4 = MyBatisUtil.getSession();
        UserDAO mapper4 = sqlsession4.getMapper(UserDAO.class);
        mapper4.deleteUser(5);
        sqlsession4.commit(); //数据修改成功，相关的缓存被清除
        sqlsession4.close();

        System.out.println("======================");
        List<User> users2 = mapper2.queryUsers();
        sqlsession2.close();
        System.out.println("======================");
        List<User> users3 = mapper3.queryUsers();
        sqlsession3.close();

    }

/*
DEBUG [main] - Created connection 673186785.
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - ==>  Preparing: select id,username,password,gender,regist_time as registTime from t_user 
DEBUG [main] - ==> Parameters: 
DEBUG [main] - <==      Total: 5
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Returned connection 673186785 to pool.
DEBUG [main] - Opening JDBC Connection
DEBUG [main] - Checked out connection 673186785 from pool.
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - ==>  Preparing: delete from t_user where id = ? 
DEBUG [main] - ==> Parameters: 5(Integer)
DEBUG [main] - <==    Updates: 1
DEBUG [main] - Committing JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Returned connection 673186785 to pool.
======================
DEBUG [main] - Cache Hit Ratio [com.zssea.dao.UserDAO]: 0.0
DEBUG [main] - Opening JDBC Connection
DEBUG [main] - Checked out connection 673186785 from pool.
DEBUG [main] - Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - ==>  Preparing: select id,username,password,gender,regist_time as registTime from t_user 
DEBUG [main] - ==> Parameters: 
DEBUG [main] - <==      Total: 4
DEBUG [main] - Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@282003e1]
DEBUG [main] - Returned connection 673186785 to pool.
======================
DEBUG [main] - Cache Hit Ratio [com.zssea.dao.UserDAO]: 0.3333333333333333

Process finished with exit code 0

*/
```

## 十三、Druid连接池

### 13.1概念

- Druid是阿里巴巴开源平台上的一个项目，整个项目由数据库连接池、插件框架和SQL解析器组成。该项目主要是为了扩展JDBC的一些限制，可以让程序员实现一些特殊的需求，比如向密钥服务请求凭证、统计SQL信息、SQL 性能收集、SQL 注入检查、SQL翻译等，程序员可以通过定制来实现自己需要的功能。

### 13.2不同连接池对比

- 测试执行申请归还连接100.0000 (一百万)次总耗时性能对比。

#### 13.2.1测试环境

| 环境 | 版本                  |
| ---- | --------------------- |
| OS   | OSX 10.8.2            |
| CPU  | Intel i7 2GHz4 Core   |
| JVM  | Java Version 1.7.0 05 |

#### 13.2.2基准测试结果对比

| JDBC Conn Pool   | 1 Thread | 2 threads | 5 threads  | 10 threads | 20 threads | 50 threads  |
| ---------------- | -------- | --------- | ---------- | ---------- | ---------- | ----------- |
| Druid            | 898      | 1,191     | 1,324      | 1,362      | 1,325      | 1,459       |
| tomcat. jdbc     | 1,269    | 1,378     | 2,029      | 2,103      | 1,879      | 2,025       |
| DBCP             | 2,324    | 5,055     | 5,446      | 5,471      | 5,524      | 5,415       |
| BoneCP           | 3,738    | 3,150     | 3,194      | 5,681      | 11,018     | 23,125      |
| jboss-datasource | 4,377    | 2,988     | 3,680      | 3,980      | 32,708     | 37,742      |
| C3P0             | 10,841   | 13,637    | 10,682     | 11,055     | 14,497     | 20,351      |
| Proxool          | 16,337   | 16,187    | 18,310(Ex) | 25,945     | 33,706(Ex) | 39,501 (Ex) |

#### 13.2.3测试结论

- Druid是性能最好的数据库连接池，tomcat-jdbc和druid性能接近。
- Proxool 在激烈并发时会抛异常，不适用。
- C3P0 和Proxool都相当慢，影响sql执行效率。
- BoneCP 性能并不优越，采用LinkedTransferQueue并没有能够获得性能提升。
- 除了bonecp, 其他的在JDK7. 上跑得比JDK6上快。
- jboss-datasource虽然稳定，但性能很糟糕。

### 13.3配置pom.xml

- 引入Druid依赖

  ```xml
  <dependencies> 
  <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
              <groupId>com.alibaba</groupId>
              <artifactId>druid</artifactId>
              <version>1.1.16</version>
          </dependency>
  </dependencies>
  ```

### 13.4创建DruidDataSourceFactory

- MyDruidDataSourceFactory并继承PooledDataSourceFactory,并替换数据源。

  ```java
  package com.zssea.datasource;
  
  import com.alibaba.druid.pool.DruidDataSource;
  import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
  
  import javax.sql.DataSource;
  
  /**
   * 连接池 工厂
   */
  public class MyDruidDataSourceFactory extends PooledDataSourceFactory {
  
      public MyDruidDataSourceFactory() {
          this.dataSource = new DruidDataSource();//替换数据源
      }
  }
  ```

### 13.5修改mybatis-config.xml

- mybatis-config.xml中连接池相关配置。

  ```xml
  <environments default="zssea_mysql_config">
          <!-- 数据库相关配置-->
          <!-- id 是为这个环境配置起一个名字标识，以便于上面引用-->
          <environment id="zssea_mysql_config">
              <!-- 事务控制类型-->
              <transactionManager type="jdbc"></transactionManager>
              <!-- 数据库连接参数,连接池-->
              <dataSource type="com.zssea.datasource.MyDruidDataSourceFactory">
                  <property name="driverClass" value="${jdbc.driver}"/>
                  <!-- & 转义 &amp;  -->
                  <property name="jdbcUrl" value="${jdbc.url}"/>
                  <property name="username" value="${jdbc.username}"/>
                  <property name="password" value="${jdbc.password}"/>
              </dataSource>
          </environment>
      </environments>
  ```
  
- 注意: < property name=“属性名” />属性名必须与com.alibaba.druid.pool.DruidAbstractDataSource中一致。

## 十四、PageHelper

### 14.1概念

- PageHelper是适用于MyBatis框架的一个分页插件，使用方式极为便捷，支持任何复杂的单表、多表分页查询操作。

### 14.2访问与下载

- 官方网站: https://pagehelper.github.io/
- 下载地址: https://github.com/pagehelper/Mybatis-PageHelper

### 14.3开发步骤

- PageHelper中提供了多个分页操作的静态方法入口。

#### 14.3.1引入依赖

- pom.xml中引入PageHelper依赖。

  ```xml
  <dependencies> 
  		<dependency>
              <groupId>com.github.pagehelper</groupId>
              <artifactId>pagehelper</artifactId>
              <version>5.1.10</version>
          </dependency>
  </dependencies>
  ```

#### 14.3.2配置MyBatis- confg.xml

- 在MyBatis-config.xml中添加< plugins>。

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <!--configuration 中的子标签的使用是有顺序的，顺序如下：
          (properties?, settings?, typeAliases?, typeHandlers?,
           objectFactory?, objectWrapperFactory?, reflectorFactory?,
           plugins?, environments?, databaseIdProvider?, mappers?)
       -->
  
      <!--添加properties配置文件路径(外部配置、动态替换)-->
      <properties resource="jdbc.properties"/>
  
      <!-- 注意标签的使用顺序-->
      <!-- 二级缓存，默认是开启的-->
      <settings>
          <setting name="cacheEnabled" value="true"/>
      </settings>
  
      <!--别名-->
      <typeAliases>
          <!-- 第一种方式，具体指向每个实体类的别名-->
          <!--<typeAlias type="com.zssea.entity.User" alias="user"/>-->
  
          <!-- 第二种方式,定义实体类所在的package,每个实体类都会自动注册一个别名=类名,不区分大小写-->
          <package name="com.zssea.entity"/>
      </typeAliases>
  
      <plugins>
          <!-- com.github.pagehelper为PageHelper类所在包名,这是分页-->
          <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
      </plugins>
  
      <!-- 核心配置信息 -->
      <environments default="zssea_mysql_config">
          <!-- 数据库相关配置-->
          <!-- id 是为这个环境配置起一个名字标识，以便于上面引用-->
          <environment id="zssea_mysql_config">
              <!-- 事务控制类型-->
              <transactionManager type="jdbc"></transactionManager>
              <!-- 数据库连接参数,连接池-->
              <dataSource type="com.zssea.datasource.MyDruidDataSourceFactory">
                  <property name="driverClass" value="${jdbc.driver}"/>
                  <!-- & 转义 &amp;  -->
                  <property name="jdbcUrl" value="${jdbc.url}"/>
                  <property name="username" value="${jdbc.username}"/>
                  <property name="password" value="${jdbc.password}"/>
              </dataSource>
          </environment>
      </environments>
  
      <!-- 注册mapper文件-->
      <mappers>
          <mapper resource="com/zssea/dao/UserDAOMapper.xml"/>
      </mappers>
  </configuration>
  ```

#### 14.3.3 PageHelper应用方式

- 使用PageHelper提供的静态方法设置分页查询条件。

  ```java
   @Test
      public void testPage(){
          UserDAO mapper = MyBatisUtil.getMapper(UserDAO.class);
          //在查询前，设置分页  查询第一页，每页2条数据
          // PageHelper 对其之后的第一个查询，进行分页功能追加
          PageHelper.startPage(2, 2);
          List<User> users = mapper.queryUsers();
          for (User user : users) {
              System.out.println(user);
          }
          // 将查询结果 封装到 PageInfo对象中
          PageInfo<User> pageInfo = new PageInfo(users);
          System.out.println("==================================");
      }
  ```

### 14.4 PageInfo对象

- PageInfo对象中包含了分页操作中的所有相关数据。

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200903143845309.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 14.4.1 PageInfo应用方式

- 使用Pagelnfo保存分页查询结果。

  ```java
   @Test
      public void testPage(){
        UserDAO mapper = MyBatisUtil.getMapper(UserDAO.class);
          //在查询前，设置分页  查询第一页，每页2条数据
          // PageHelper 对其之后的第一个查询，进行分页功能追加
          PageHelper.startPage(2, 2);
          List<User> users = mapper.queryUsers();
          for (User user : users) {
              System.out.println(user);
          }
          // 将查询结果 封装到 PageInfo对象中
          PageInfo<User> pageInfo = new PageInfo(users);
          System.out.println(pageInfo);
          System.out.println("==================================");
      }
  ```

#### 14.4.2注意事项

- 只有在PageHelper.startPage()方法之后的**第一个查询会有执行分页。**
- 分页插件**不支持带有"for update"的查询语句。**
- 分页插件不支持**“嵌套查询”**，由于嵌套结果方式会导致结果集被折叠，所以无法保证分页结果数量正确。

#### 14.4.3分页练习

- 使用Servlet+JSP+MyBatis+分页插件，完成分页查询功能。
- 项目为： D:\Program Files\IDEAworkspace\projects\JavaWeb_high\mybatis_page

## 十五、补充[了解]

以下内容并非必备知识，了解即可。

### 15.1 MyBatis注解操作

**关于@Param( )注解**

- 基本类型的参数或者String类型，需要加上
- 引用类型不需要加
- 如果只有一个基本类型的话，可以忽略，但是建议大家都加上
- 我们在SQL中引用的就是我们这里的@Param()中设定的属性名

**关于使用注解开发**

- 通过在接口中直接添加MyBatis注解，完成CRUD。(不使用xxxMapper.xml文件)

- 注意:接口注解定义完毕后，需将接口全限定名注册到mybatis-config.xml的< mappers >中。

- 经验:注解模式属于硬编码到.java文件中，失去了使用配置文件外部修改的优势，可结合需求选用。

  ```java
  package com.zssea.dao;
  import com.zssea.entity.User;
  import org.apache.ibatis.annotations.*;
  
  import java.util.List;
  
    public interface UserDAO {
         @Select(" select id,username,password,gender,regist_time from t_user")
   		 List<User> queryUsers();
   }
  ```
  
```xml
  <!-- 注册mapper文件-->
        <mappers>
            <!--<mapper resource="com/zssea/dao/UserDAOMapper.xml"/>-->
            <mapper class="com.zssea.dao.UserDAO"/> <!--使用注解的配置 class="接口全限定名"-->
        </mappers>
```

#### 15.1.1查询

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserDAO {

    @Select(" select id,username,password,gender,regist_time from t_user")
    List<User> queryUsers();

    @Select("select id,username,password,gender,regist_time as registTime from t_user where id=#{id}")
    User queryUserById(@Param("id") Integer id);
}
```

#### 15.1.2删除

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserDAO {
    @Delete("delete from t_user where id = #{id}")
    Integer deleteUser(@Param("id") Integer id);
}
```

#### 15.1.3修改

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserDAO {
    @Update("update t_user set  username=#{username}, password=#{password},\n " +
            " gender=#{gender}, regist_time=#{registTime} where id = #{id}")
    Integer updateUser(User user);
}
```

#### 15.1.4插入

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

public interface UserDAO {
    @Options(useGeneratedKeys = true , keyProperty = "id") // 自增key，主键为id,主键回填
    @Insert("insert into t_user values(#{id},#{username},#{password},#{gender},#{registTime})")
    Integer insertUser(User user);
}
```

### 15.2 $符号的应用场景

- ${attribute}属于字符串拼接SQL，而非预编译占位符，会有注入攻击问题，不建议在常规SQL中使用，**常用于可解决动态生降序问题。**
- ${} 与#{} 表达式的区别：
  - 表 示 的 是 当 前 方 法 参 数 的 属 性 ， 如 果 方 法 的 参 数 是 零 散 的 ， 要 想 使 用 {} 表示的是当前方法参数的属性，如果方法的参数是零散的，要想使用表示的是当前方法参数的属性，如果方法的参数是零散的，要想使用{}，是必须要加@Parm()注解的
  - 如果参数是String类型，需 要 用 单 引 号 ， 如 s e l e c t ∗ f r o m t u s e r w h e r e u s e r n a m e = ′ {}需要用单引号，如select * from t_user where username = '需要用单引号，如*s**e**l**e**c**t*∗*f**r**o**m**t**u**s**e**r**w**h**e**r**e**u**s**e**r**n**a**m**e*=′{username}’;
  - 两者生成的sql语句，${} 是把参数直接拼接在sql语句中，在控制台可以看到完整的参数；而#{}是用占位符（？）来表示参数
- ${} , #{}使用原则：
  - 能用#{} 尽量用#{}，如果这个位置并不是为某个列的值做一些相关操作，仅仅只是在某些sql片段上动态填充（比如添加 降序desc/升序asc）,就要用${}
  - 

#### 15.2.1 $符号参数绑定

```java
package com.zssea.dao;

import com.zssea.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserDAO {

    List<User> queryUsers(@Param("rule") String rule);//排序查询

    User queryUserById(@Param("id") Integer id);// #{arg0}  ${arg0}
    User queryUserById2(@Param("id") Integer id);// #{arg0}  ${arg0}

    List<User> queryUserByUsername(@Param("username") String username);

    List<User> queryUserByUsernameorId(User user);// ${username}  ${id}
}



<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.dao.UserDAO">

    <!--#{}  ${} -->
    <select id="queryUsers" resultType="User">
        select id,username,password,gender,regist_time
        from t_user
        order by id ${rule}
         <!--order by id #{rule}==>error  -->
    </select>

    <select id="queryUserById" resultType="User">
        select id,username,password,gender,regist_time
        from t_user
        where id=#{id}
    </select>

    <select id="queryUserById2" resultType="User">
        select id,username,password,gender,regist_time
        from t_user
        where id=${id}
    </select>

    <select id="queryUserByUsername" resultType="User">
        select id,username,password,gender,regist_time
        from t_user
        where username='${username}'
    </select>

    <!--
        String username = "shine";
        String sql = "select xxx from t_user where username='"+username+"'";
     -->
    <select id="queryUserByUsernameorId" resultType="User">
        select id,username,password,gender,regist_time
        from t_user
        where username='${username}' or id=${id}
        <!--  where username=#{username} or id=#{id}  -->
    </select>
</mapper>
```

#### 15.2.2 $符号注入攻击

```java
package com.zssea.test;

import com.zssea.dao.UserDAO;
import com.zssea.entity.User;
import com.zssea.util.MyBatisUtil;
import org.junit.Test;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class MyBatisTest {

    @Test
    public void test1(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);
       /* List<User> users = userMapper.queryUsers();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println("======================");
        User user = userMapper.queryUserById(6);
        System.out.println(user);*/
       /*userMapper.deleteUser(6);
       userMapper.insertUser(new User(null,"new_user","123456",true,new Date()));
       userMapper.updateUser(new User(7,"update_user","1111",false,new Date()));
       MyBatisUtil.commit();*/
    }

    @Test
    public void test1_1(){
        UserDAO userMapper = MyBatisUtil.getMapper(UserDAO.class);
        /*User user = userMapper.queryUserById(7);
        System.out.println(user);*/

        User user1 = new User();
        user1.setUsername("zssea");
        user1.setId(10);
        List<User> users = userMapper.queryUserByUsernameorId(user1);
        for (User user2 : users) {
            System.out.println(user2);
        }
    }

    /**  #{}
     * 1.占位符：优势：规避sql注入风险
     * 2.劣势：要和列相关位置才可以使用,不能实现升序或降序查询
     * 原则：填充的数据，要和列相关
     * select * from t_user where id=?
     * insert into t_user values(?,?,?)
     * update t_user set username=?,password=?
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void test2() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&characterEncoding=utf-8","root","123456");

        //要填充的数据
        String username = "zssea' or '1'='1";
        String rule = "desc";
        String sql = "select * from t_user where username=?";
        String sql2 = "select * from t_user order by id ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql2);
        // 在占位符上 填充desc
        preparedStatement.setString(1,rule);

        ResultSet resultSet = preparedStatement.executeQuery(); //会报异常，因为占位符填充的数据要和列相关
        while(resultSet.next()){
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("username"));
        }
    }

    /** ${}
     * 1. 劣势：有sql注入风险
     * 2. 优势：可以随意拼接，可以实现升序或降序查询
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void test3() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&characterEncoding=utf-8","root","123456");

        //要填充的数据
        String username = "wa' or '1'='1";
        String rule = "desc";
        //sq拼接填充数据  select * from t_user where username='wa'
        //sq拼接填充数据  select * from t_user where username='wa' or '1'='1'
        // 当拼接sql片段，有sql注入风险，外界参数改变原有sql的语义
        String sql = "select * from t_user where username='"+username+"'";
        String sql2 = "select * from t_user order by id "+rule;

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql2);
        while(resultSet.next()){
            System.out.println(resultSet.getInt("id"));
            System.out.println(resultSet.getString("username"));

        }
    }

    @Test
    public void test5(){
        UserDAO mapper = MyBatisUtil.getMapper(UserDAO.class);
        List<User> users = mapper.queryUserByUsername("shine_xxxx' or '1'='1");
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test6(){
        UserDAO mapper = MyBatisUtil.getMapper(UserDAO.class);
        Integer sig=0; // 0 desc 1  asc
        if(sig==0){
            mapper.queryUsers("desc");//${}可以实现排序  #{}不可以实现排序
        }else {
            mapper.queryUsers("asc");
        }
    }
}
```

### 15.3 MyBatis处理关联关系嵌套查询[了解]

思路:查询部门信息时，及联查询所属的员工信息。

- DepartmentDao接口中定义queryDepartmentById, 并实现Mapper。
- EmployeeDao接口中定义queryEmployeeByDeptId, 并实现Mapper,
- 当queryDepartmentById被执行时， 通过< collection >调用queryEmployeeByDeptId方法，并传入条件参数。

#### 15.3.1主表查询

- 定义queryEmployeeByDeptId,并书写Mapper,实现根据部门ID查询员工信息

  ```java
  package com.zssea.dao;
  import com.zssea.entity.Employee;
  import org.apache.ibatis.annotations.Param;
  import java.util.List;
  
    public interface EmployeeDAO {
         // 查询员工信息 并且 查到对应的部门信息
        Employee queryEmployeeById(@Param("id") Integer id);
  
        // 查询某个部门下的所有员工
        List<Employee> queryEmployeeByDeptId(@Param("deptId") Integer deptId);
    }
  ```
  
```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    
    <mapper namespace="com.zssea.dao.EmployeeDAO">
    
        <resultMap id="emp_dept" type="Employee">
            <id column="id" property="id"></id>
            <result column="name" property="name"></result>
            <result column="salary" property="salary"></result>
    
            <!--<association property="department" javaType="Department">
                <id column="deptId" property="id"></id>
                <result column="deptName" property="name"></result>
                <result column="location" property="location"></result>
            </association>-->
    
            <association property="department" javaType="Department"
                         select="com.zssea.dao.DepartmentDAO.queryDepartmentById"
                         column="dept_id"/>
        </resultMap>
    
        <select id="queryEmployeeById" resultMap="emp_dept">
            select id,name,salary,dept_id
            from t_employees
            where id=#{id}
            <!--
                select t_employees.id,t_employees.name,t_employees.salary,
                    t_departments.id as deptId,t_departments.name as deptName,t_departments.location
                from t_employees join t_departments
                on t_employees.dept_id = t_departments.id
                where t_employees.id = #{id}
            -->
        </select>
    
        <select id="queryEmployeeByDeptId" resultType="Employee">
            select id,name,salary
            from t_employees
            where dept_id=#{deptId}
        </select>
    </mapper>
  ```

#### 15.3.2及联调用

- 定义queryDepartmentById,井书写Mapper,实现根据部门ID查询部门信息，井及联查询该部门员工信息

  ```java
  package com.zssea.dao;
  
  import com.zssea.entity.Department;
  import org.apache.ibatis.annotations.Param;
  
  public interface DepartmentDAO {
  
      // 查询部门，及其所有员工信息
      Department queryDepartmentById(@Param("id") Integer id);
  }
  ```
  
  ```xml
<?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  
  <mapper namespace="com.zssea.dao.DepartmentDAO">
  
  
      <resultMap id="dept_emp" type="Department">
          <id column="id" property="id"></id>
          <result column="name" property="name"></result>
          <result column="location" property="location"></result>
  
          <!-- emp_id  emp_name  salary    employees -->
          <!--<collection property="employees" ofType="Employee">
              <id column="emp_id" property="id"></id>
              <result column="emp_name" property="name"></result>
              <result column="salary" property="salary"></result>
          </collection>-->
  
          <!-- 嵌套查询，select属性是调用EmployeeDAOMapper.xml中的名为queryEmployeeByDeptId的方法
              这个方法需要一个where dept_id=#{deptId} 这样的参数，而column="id" 就为它提供了这个参数
              并且这个column="id"的值就是我们查询部门（queryDepartmentById）所查询到的id
              -->
          <collection property="employees" ofType="Employee"
                      select="com.zssea.dao.EmployeeDAO.queryEmployeeByDeptId"
                      column="id">
          </collection>
  
      </resultMap>
      <select id="queryDepartmentById" resultMap="dept_emp">
          select id ,name,location
          from t_departments
          where id=#{id}
  
          <!--
              select t_departments.id,t_departments.name,t_departments.location,
                 t_employees.id as emp_id,t_employees.name as emp_name,t_employees.salary
              from t_departments join t_employees
              on t_departments.id = t_employees.dept_id
              where t_departments.id = #{id}
          -->
      </select>
  </mapper>
  ```
  
  ```xml
  <!-- 注册mapper文件-->
    <mappers>
          <mapper resource="com/zssea/dao/DepartmentDAOMapper.xml"/>
          <mapper resource="com/zssea/dao/EmployeeDAOMapper.xml"/>
      </mappers>
  ```

#### 15.3.3延迟加载

- mybatis-config.xml中开启延迟加载

  ```xml
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <!-- 注意标签的使用顺序-->
      <settings>
          <!-- 二级缓存，默认是开启的,但如果真的要把查询结果放到二级缓存中需要在相应的xxxMapper文件中配置<cache/> -->
          <setting name="cacheEnabled" value="true"/>
  
          <!-- 当使用嵌套查询时，如果查询中只用到了一方的数据，则不会触发另一方的查询 -->
          <setting name="lazyLoadingEnabled" value="true"/> <!-- 开启延迟加载（默认false） -->
      </settings>
  </configuration>
  ```
  
```java
  package com.zssea.test;
  
  import com.zssea.dao.DepartmentDAO;
  import com.zssea.dao.EmployeeDAO;
  import com.zssea.entity.Department;
  import com.zssea.entity.Employee;
  import com.zssea.entity.User;
  import com.zssea.util.MyBatisUtil;
  import org.junit.Test;
  
  import java.io.IOException;
  import java.util.List;
  
  public class MyBatisTest2 {
      @Test
      public void test1(){
          /*DepartmentDAO mapper = MyBatisUtil.getMapper(DepartmentDAO.class);
          Department department = mapper.queryDepartmentById(1);
          System.out.println(department);
          List<Employee> employees = department.getEmployees();
          for (Employee employee : employees) {
              System.out.println(employee);
          }*/
  
          EmployeeDAO mapper = MyBatisUtil.getMapper(EmployeeDAO.class);
          Employee employee = mapper.queryEmployeeById(1);
          System.out.println(employee); //开启了延迟加载 如果不执行这一句，是不会执行queryDepartmentById
          System.out.println(employee.getDepartment());//开启了延迟加载 如果不执行这一句，是不会执行queryEmployeeByDeptId
      }
  }
  ```
  
- 注意：开启延迟加载后，如果不使用及联数据，则不会触发及联查询操作，有利于加快查询速度、节省内存资源。

### 15.4 Lombok

Lombok项目是一个Java库，它会自动插入编辑器和构建工具中，Lombok提供了一组有用的注释，用来消除Java类中的大量样板代码。仅五个字符(@Data)就可以替换数百行代码从而产生干净，简洁且易于维护的Java类。

使用步骤：

1. 在IDEA中安装Lombok插件

2. 在项目中导入lombok的jar包

   ```xml
   <dependency>
       <groupId>org.projectlombok</groupId>
       <artifactId>lombok</artifactId>
       <version>1.18.10</version>
       <scope>provided</scope>
   </dependency>
   ```

3. 在程序上加注解

```java
@Getter and @Setter
@FieldNameConstants
@ToString
@EqualsAndHashCode
@AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
@Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
@Data
@Builder
@SuperBuilder
@Singular
@Delegate
@Value
@Accessors
@Wither
@With
@SneakyThrows
@val
```

说明：

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String password;
}
```



