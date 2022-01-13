

# Spring

![点击查看源网页](https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1604250454586&di=a02a678b24884527c1b4fbe1bec97dd3&imgtype=0&src=http%3A%2F%2Fwx4.sinaimg.cn%2Flarge%2F7cc829d3gy1fh9yazc2hwj20eg07pq2v.jpg)

## 一、引言

### 1.1原生web开发中存在哪些问题?

- 传统Web开发存在硬编码所造成的过度程序耦合(例如: Service中作为属性Dao对象) 。
- 部分Java EE API较为复杂，使用效率低(例如: JDBC开发步骤)。
- 侵入性强，移植性差(例如: DAO实现的更换，从Connection到SqlSession) 。

## 二、Spring框架

### 2.1概念

- Spring是一个项目管理框架，同时也是一 套Java EE解决方案。
- Spring是众多优秀设计模式的组合 (工厂、单例、代理、适配器、包装器、观察者、模板、策略)。
- Spring并未替代现有框架产品，而是将众多框架进行有机整合，简化企业级开发，俗称“胶水框架"。
- Spring的核心是**控制反转(IoC）\**和\**面向切面(AOP）**

### 2.2spring优点

- 方便解耦，简化开发 （高内聚低耦合）
  - Spring就是一个大工厂（容器），可以将所有对象创建和依赖关系维护，交给Spring管理
  - **spring工厂是用于生成bean**
- AOP编程的支持
  - Spring提供面向切面编程，可以方便的实现对程序进行权限拦截、运行监控等功能
- 声明式事务的支持
  - 只需要通过配置就可以完成对事务的管理，而无需手动编程
- 方便程序的测试
  - Spring对Junit4支持，可以通过注解方便的测试Spring程序
- 方便集成各种优秀框架
  - Spring不排斥各种优秀的开源框架，其内部提供了对各种优秀框架（如：Struts、Hibernate、MyBatis、Quartz等）的直接支持
- 降低JavaEE API的使用难度
  - Spring 对JavaEE开发中非常难用的一些API（JDBC、JavaMail、远程调用等），都提供了封装，使这些API应用难度大大降低

### 2.3访问与下载

- 官方网站: https://spring.io/
- 下载地址: http://repo.spring.io/release/org/springframework/spring/

## 三、Spring架构组成

Spring架构由诸多模块组成，可分类为：

- 核心技术：依赖注入，事件，资源，i18n, 验证，数据绑定，类型转换，SpEL， AOP。(利用工厂)

- 测试：模拟对象，TestContext框架， Spring MVC测试， WebTestClient.

- 数据访问：事务，DAO支持， JDBC, ORM，封送XML。

- Spring MVC和Spring WebFlux Web框架。

- 集成：远程处理，JMS，JCA, JMX， 电子邮件，任务，调度，缓存。

- 语言: Kotlin, Groovy, 动态语言。

  ![img](https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3920950184,4189685308&fm=26&gp=0.jpg)

  

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200905220012909.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

  

- **核心容器**：核心容器提供 Spring 框架的基本功能。核心容器的主要组件是 BeanFactory，它是工厂模式的实现。BeanFactory 使用*控制反转*（IOC） 模式将应用程序的配置和依赖性规范与实际的应用程序代码分开。
- **Spring 上下文**：Spring 上下文是一个配置文件，向 Spring 框架提供上下文信息。Spring 上下文包括企业服务，例如 JNDI、EJB、电子邮件、国际化、校验和调度功能。
- **Spring AOP**：通过配置管理特性，Spring AOP 模块直接将面向切面的编程功能 , 集成到了 Spring 框架中。所以，可以很容易地使 Spring 框架管理任何支持 AOP的对象。Spring AOP 模块为基于 Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖组件，就可以将声明性事务管理集成到应用程序中。
- **Spring DAO**：JDBC DAO 抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误消息。异常层次结构简化了错误处理，并且极大地降低了需要编写的异常代码数量（例如打开和关闭连接）。Spring DAO 的面向 JDBC 的异常遵从通用的 DAO 异常层次结构。
- **Spring ORM**：Spring 框架插入了若干个 ORM 框架，从而提供了 ORM 的对象关系工具，其中包括 JDO、Hibernate 和 iBatis SQL Map。所有这些都遵从 Spring 的通用事务和 DAO 异常层次结构。
- **Spring Web 模块**：Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文。所以，Spring 框架支持与 Jakarta Struts 的集成。Web 模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作。
- **Spring MVC 框架**：MVC 框架是一个全功能的构建 Web 应用程序的 MVC 实现。通过策略接口，MVC 框架变成为高度可配置的，MVC 容纳了大量视图技术，其中包括 JSP、Velocity、Tiles、iText 和 POI。

##### 拓展

- Spring Boot
  - 一个快速开发的脚手架
  - 基于SpringBoot可以快速的开发单个微服务
- Spring Cloud
  - Spring Cloud是基于SpringBoot实现的

## 四、自定义工厂

- spring核心就是利用工厂来实现众多操作，先熟悉一下什么是工厂

### 4.1配置文件

- bean.properties

```xml
userDAO=com.zssea.dao.UserDAOImpl
userService=com.zssea.service.UserServiceImpl
```

### 4.2工厂类

```java
package com.zssea.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//工厂
//1.加载配置文件
//2.生产配置中所记录的对象
public class MyFactory {

    private Properties properties = new Properties();

    public MyFactory(){}
    
    public MyFactory(String config) throws IOException {
        InputStream resourceAsStream = MyFactory.class.getResourceAsStream(config);
        //properties 读取配置文件中的信息
        properties.load(resourceAsStream);
    }

    public Object getBean(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1.通过name获取对应类型路径
        String classPath = properties.getProperty(name);
        //2.通过反射来创建实例
        Class aClass = Class.forName(classPath);
        return aClass.newInstance();
    }
}



package com.zssea.test;

import com.zssea.dao.UserDAO;
import com.zssea.factory.MyFactory;
import com.zssea.service.UserService;

import java.io.IOException;

public class TestFactory {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        //1.创建工厂对象
        MyFactory myFactory = new MyFactory("/bean.properties");
        //2.从工厂中获取对象
        UserDAO userDAO = (UserDAO)myFactory.getBean("userDAO");
        UserService userService = (UserService)myFactory.getBean("userService");
        userDAO.deleteUser(1);
        userService.deleteUser(1);
    }
}
```

## 五、Spring环境搭建

### 5.1 pom.xml中引入Spring常用依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.zssea</groupId>
    <artifactId>spring01</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <!-- spring常用依赖，传递性 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.1.6.RELEASE</version>
        </dependency> 
    </dependencies>
</project>
```

### 5.2创建Spring配置文件

- 命名无限制，约定俗成命名有: spring-context.xml、applicationContext.xml、beans.xml
- spring-context.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--这里面配置的是要工厂生产的对象，就如我们写的bean.properties，不过这个文件里又固定的标签-->
    <bean id="userDAO" class="com.zssea.dao.UserDAOImpl"></bean>
    <bean id="userService" class="com.zssea.service.UserServiceImpl"></bean>
</beans>
```

- TestSpringFactory

```java
package com.zssea.test;

import com.zssea.dao.UserDAO;
import com.zssea.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringFactory {

    @Test
    public void testSpringFactory(){
        //1.加载配置文件，启动工厂
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
        //2.获取对象
        UserDAO userDAO = (UserDAO) applicationContext.getBean("userDAO");
        UserService userService = (UserService)applicationContext.getBean("userService");

        userDAO.deleteUser(1);
        userService.deleteUser(1);
    }
}
/*执行结果：
delete user in DAO
delete user in Service
*/
```



## 六、Spring工厂编码

### 6.1定义目标Bean类型

```java
  package com.zssea.entity;
  public class MyClass{
      public void show(){
          System.out.println("Hello World");
      }
  }
```

### 6.2spring-context.xml中的< beans >内部配置bean标签

```xml
  <!--配置实例(id:"唯一 标识” class="需要被创建的目标对象全限定名") -->
  <bean id="mc" class="com.zssea.entity.MyClass"> </bean>
```

### 6.3调用Spring工厂API (ApplicationContext接口)

```java
  package com.zssea.test;
  
  import com.zssea.entity.MyClass;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class TestSpringFactory {
  
      @Test
      public void testSpringFactory(){
          //1.加载配置文件，启动工厂
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
          //2.获取对象
          MyClass myclass = (MyClass) applicationContext.getBean("mc");
  
  		myclass.show();
      }
  }
```

## 七、依赖与配置文件详解

- Spring框架包含多个模块，每个模块各司其职，可结合需求引入相关依赖Jar包实现功能。
  
### 7.1 Spring依赖关系

- 注意: Jar包彼此存在依赖，只需引入最外层Jar即可由Maven自动将相关依赖Jar引入到项目中。（传递性）
  

![在这里插入图片描述](https://img-blog.csdnimg.cn/202009052201411.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 7.2 schema

配置文件中的顶级标签中包含了语义化标签的相关信息：

- xmIns： 语义化标签所在的命名空间。
  - xmIns:xsi： XMLSchema-instance标签遵循Schema标签标准。
  - xsischemaLocation： xsd文件位置，用以描述标签语义、属性、取值范围等。
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <!--
      schema： 规范
      xxx.xsd ( xml schema definition xml规范定义)
   -->
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
  </beans>
```

## 八、loC (Inversion of Control)控制反转【重点】

- Inverse of Controll：控制反转
  - 反转了依赖关系的满足方式，由之前的自己创建依赖对象（new一个出来），变为由工厂推送（在spring-context.xml中的与实现类对应的bean中设置标签< property name="" ref="">< /property>）。(变主动为被动，即反转)
  - 解决了具有依赖关系的组件之间的强耦合，使得项目形态更加稳健
  - 依赖关系：为学习框架之前，项目结构都是MVC模式，就是Dao层–》Service层–》Servlet控制层，后者需要在自己的代码中new出来一个前者实例（后者依赖前者），如下：
  
### 8.1项目中强耦合问题

```java
  package com.zssea.dao;
  
  public class UserDAOImpl implements UserDAO{....}


  package com.zssea.service;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.dao.UserDAOImpl;
  
  public class UserServiceImpl implements UserService{
  
      //依赖关系：Service依赖DAO ，
      // 并且还是强耦合的关系：dao层要是发生变化，service层也要变化，servlet层也要变化
      //如果该service要更换dao，就要先创建一个daoImpl,之后再new一个实例
      private UserDAO userDAO = new UserDAOImpl();
  
      public void deleteUser(Integer id) {
          System.out.println("delete user in Service");
      }
  }
```

### 8.2解决方案

```java
  package com.zssea.service;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.dao.UserDAOImpl;
  
  public class UserServiceImpl implements UserService{
  
      //依赖关系：Service依赖DAO ，
      // 并且还是强耦合的关系：dao层要是发生变化，service层也要变化，servlet层也要变化
      //如果该service要更换dao，就要先创建一个daoImpl,之后再new一个实例
      //private UserDAO userDAO = new UserDAOImpl();
  
      //解决办法：
      //不引用任何一个具体的组件(实现类)，在需要其他组件的位置预留存取值入口( set/get)
      //不再耦合任何DA0实现!!!.消除不稳健因素!!
      //后面在spring-context.xml中 在UserServiceImpl对应的bean中，配置这个userDAO属性，具体指向的实现类
      private UserDAO userDAO;
  
      public UserDAO getUserDAO() {
          return userDAO;
      }
  
      public void setUserDAO(UserDAO userDAO) {
          this.userDAO = userDAO;
      }
  
      public void deleteUser(Integer id) {
          System.out.println("delete user in Service");
          userDAO.deleteUser(id);
      }
  }


  <?xml version="1.0" encoding="UTF-8"?>
  <!--
      schema： 规范
      xxx.xsd ( xml schema definition xml规范定义)
   -->
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--这里面配置的是要工厂生产的对象，就如我们写的bean.properties，不过这个文件里又固定的标签-->
      <bean id="userDAO" class="com.zssea.dao.UserDAOImpl"></bean>
      <bean id="userDAO2" class="com.zssea.dao.MyUserDAO"></bean>
      <bean id="userService" class="com.zssea.service.UserServiceImpl">
          <!--name="userDAO" 是指明为UserServiceImpl里的userDAO属性赋值,
              ref="userDAO" 指向的是id为userDAO bean标签，这就是赋值的内容-->
          <!--<property name="userDAO" ref="userDAO"></property>-->
  
          <!--这就是另一种为属性赋值的方式：DI依赖注入 IOC-->
  
          <!--更换实现类，只要把ref="" 的值换成对应的bean标签就行，不需要改动service层代码-->
          <property name="userDAO" ref="userDAO2"></property>
      </bean>
  </beans>
```

- 此时，如果需要更换其他UserDA0实现类，则UserServicelmpl不用任何改动！则此时的UserServicelmpl组件变得更加稳健!
  
```java
  package com.zssea.test;
  
  import com.zssea.service.UserService;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class TestSpringFactory {
  
      @Test
      public void testSpringFactory(){
          //1.加载配置文件，启动工厂
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
          //2.获取对象
          UserService userService = (UserService)applicationContext.getBean("userService");
         
          userService.deleteUser(1);
      }
  }
  /* 执行结果：
  delete user in Service
  hello world in dao 
  */
```

## 九、DI (Dependency Injection)依赖注入【重点】

### 9.1概念

- 在Spring创建对象的同时，为其属性赋值，称之为依赖注入。（与IOC实际上是一回事，IOC注重思想，DI注重操作）
  - IOC讲的是一种方式，就是将创建实例由new 转为配置文件实现；而DI就是IOC实现过程中的一步操作，为属性赋值，而依赖注入：看似我们注入的是属性值，但是是为了满足依赖关系而注入的。
  
### 9.2 Set注入

- 创建对象时，Spring工厂会通过Set方法为对象的属性赋值。
  
#### 9.2.1定义目标Bean类型

```java
  package com.zssea.entity;
  
  import org.springframework.stereotype.Component;
  
  import javax.annotation.PostConstruct;
  import javax.annotation.PreDestroy;
  import java.util.*;
  
  public class User {
      private Integer id;
      private String password;
      private String sex;
      private Integer age;
      private Date bornDate;
      private String[] hobbys;
      private Set<String> phones;
      private List<String> names;
      private Map<String,String> countries;
      private Properties files;
      // 自建类型(自己写的entity)
      private Address address;
      //此处省略了 Getters 和 Setters方法，代码要有
  }
```

#### 9.2.2基本类型+字符串类型+日期类型

```xml
  <?xml version="1.0" encoding="UTF-8"?>
  
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--Set注入，调用set方法-->
      <bean id="user" class="com.zssea.entity.User">
          <!--简单属性：jdk8中基本类型，String Date -->
          <property name="id"  value="10"/>
          <property name="password"  value="123456"/>
          <property name="sex"  value="male"/>
          <property name="age"  value="18"/>
          <property name="bornDate"  value="2020/12/12 12:12:12"/>
          <!--Date bornDate 我们输入特定格式的日期，会自动转换成Date类型 -->
      </bean>
  </beans>
```

#### 9.2.3容器类型

```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--Set注入-->
      <bean id="user" class="com.zssea.entity.User">
          <!--简单属性：jdk8中基本类型，String Date -->
          <property name="id"  value="10"/>
          <property name="password"  value="123456"/>
          <property name="sex"  value="male"/>
          <property name="age"  value="18"/>
          <property name="bornDate"  value="2020/12/12 12:12:12"/>
          <!--Date bornDate 我们输入特定格式的日期，会自动转换成Date类型 -->
  
          <!--数组-->
          <property name="hobbys">
              <array>
                  <value>football</value>
                  <value>basketball</value>
              </array>
          </property>
          <!--集合-->
          <property name="names">
              <list>
                  <value>tom</value>
                  <value>cat</value>
              </list>
          </property>
          <property name="phones">
              <set>
                  <value>12365478974</value>
                  <value>96857412344</value>
              </set>
          </property>
          <property name="countries">
              <map>
                  <entry key="ch" value="china"></entry>
                  <entry key="en" value="english"></entry>
              </map>
          </property>
          <property name="files">
              <props>
                  <prop key="url">jdbc:mysql:3306//</prop>
                  <prop key="username">root</prop>
                  <prop key="password">123456</prop>
              </props>
          </property>
      </bean>
  </beans>
```

#### 9.2.4自建类型

```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <bean id="addr" class="com.zssea.entity.Address">
          <property name="id" value="1"/>
          <property name="city" value="beijing"/>
      </bean>
  
      <!--Set注入-->
      <bean id="user" class="com.zssea.entity.User">
          <!--自建类型-->
          <property name="address" ref="addr"></property>
      </bean>
  </beans>
```

### 9.3构造注入[了解]

- 创建对象时，Spring工厂会通过构造方法为对象的属性赋值。
  
#### 9.3.1定义目标Bean类型

```java
  package com.zssea.entity;
  
  public class Student {
      private Integer id;
      private String name;
      private String sex;
      private Integer age;
  
      //Constructors
      public Student(Integer id , String name , String sex , Integer age){
          //System.out.println("set property");
          this.id = id;
          this.name = name;
          this.sex = sex;
          this.age = age;
      }
  }
```

#### 9.3.2注入

- spring-context.xml :
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
  
      <!--构造注入，调用构造方法-->
      <bean id="student" class="com.zssea.entity.Student">
          <constructor-arg name="id" value="1"/>
          <constructor-arg name="name" value="zssea"/>
          <constructor-arg name="sex" value="male"/>
          <constructor-arg name="age" value="18"/>
      </bean>
  </beans>
```

### 9.4自动注入[了解]

- 不用在配置中指定为哪个属性赋值，及赋什么值.
  - 由spring自动根据某个“原则"，在工厂中查找一个bean, 为属性注入属性值
  
```java
  package com.zssea.service;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.dao.UserDAOImpl;
  
  public class UserServiceImpl implements UserService{
  
      private UserDAO userDAO;
  
      public UserDAO getUserDAO() {
          return userDAO;
      }
  
      public void setUserDAO(UserDAO userDAO) {
          this.userDAO = userDAO;
      }
  
      public void deleteUser(Integer id) {
          System.out.println("delete user in Service");
          userDAO.deleteUser(id);
      }
  }
  


  <?xml version="1.0" encoding="UTF-8"?>
  
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <!--这里面配置的是要工厂生产的对象，就如我们写的bean.properties，不过这个文件里又固定的标签-->
      <bean id="userDAO" class="com.zssea.dao.UserDAOImpl"></bean>
      <bean id="userDAO2" class="com.zssea.dao.MyUserDAO"></bean>
      
       <!--为UserServiceImpl中的属性基于名字自动注入值 autowire="byName" ,
  			UserServiceImpl中有一个名为userDAO 的属性，所以就查看当前配置文件中有没有一个id
  			为userDAO的bean，有的话就自动注入
  	-->
      <bean id="userService" class="com.zssea.service.UserServiceImpl" autowire="byName">
      </bean>
  
      <!--为UserServiceImp1中的属性基于类型自动注入值 autowire="byType"
  		UserServiceImpl中有一个类型为UserDAO 的属性，所以就查看当前配置文件中有没有类型为UserDAO			的bean，有的话就自动注入，注意只能同时匹配一个，如果有多个会报错
  	-->
      <!--<bean id="userService" class="com.zssea.service.UserServiceImpl" autowire="byType"> </bean> -->
  </beans>
```

- 测试
  
```java
  package com.zssea.test;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.entity.Student;
  import com.zssea.entity.User;
  import com.zssea.service.UserService;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class TestSpringFactory {
  
      @Test
      public void testSpringFactory(){
          //1.加载配置文件，启动工厂
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
          //2.获取对象
          //UserDAO userDAO = (UserDAO) applicationContext.getBean("userDAO");
          UserService userService = (UserService)applicationContext.getBean("userService");
  
          //userDAO.deleteUser(1);
          userService.deleteUser(1);
      }
  }
  /*结果：
  delete user in Service
  delete user in DAO
  */
```

## 十、Bean细节

### 10.1控制简单对象的单例、多例模式

- 配置< bean scope=“singleton | prototype” /> （默认是单例）
  
```xml
  <!--
  singleton (默认) :每次调用工厂，得到的都是同一个对象。
  prototype:每次调用工厂，都会创建新的对象。
  -->
  <bean id="user" class="com.zssea.entity.User" scope="prototype"></bean>
```

- 注意：需要根据场景决定对象的单例、多例模式。
  - 可以共用: Service、DAO、SqlSessionFactory (或者是所有的工厂)。
  - 不可共用: Connection、 SqlSession、 ShoppingCart。
  
### 10.2 FactoryBean创建复杂对象[了解]

- 一般的简单对象的创建：是通过反射，调用构造方法来创建
  - FactoryBean创建复杂对象，作用：让Spring可以创建复杂对象、或者无法直接通过反射创建的对象。
  

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020090522021068.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 10.2.1实现FactoryBean接口

- 注意: isSingleton方法的返回值，需根据所创建对象的特点决定返回true/false。（返回单例还是多例）
  - 例如: Connection 不应该被多个用户共享，返回false。
  - 例如: SqlSessionFactory 重量级资源，不该过多创建，返回true。
  

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200905220219718.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 11.2.2配置spring-context.xml

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020090522023345.png#pic_center)

#### 11.2.3特例

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200905220324559.png#pic_center)

- 总：
  
- 先导入数据库依赖 pom.xml:
  
  ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.zssea</groupId>
        <artifactId>spring01</artifactId>
        <version>1.0-SNAPSHOT</version>
    
        <dependencies>
            <!-- spring常用依赖，传递性 -->
            <dependency>
                <groupId>org.springframework</groupId>
                <artifactId>spring-context</artifactId>
                <version>5.1.6.RELEASE</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
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
  
- MyConnectionFactoryBean：
  
  ```java
    package com.zssea.factorybean;
    
    import org.springframework.beans.factory.FactoryBean;
    
    import java.sql.Connection;
    import java.sql.DriverManager;
    
    public class MyConnectionFactoryBean implements FactoryBean<Connection> {
        public Connection getObject() throws Exception {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis_zssea","root","123456");
        }
    
        public Class<?> getObjectType() {
            return Connection.class;
        }
    
        public boolean isSingleton() {
            return false;
        }
    }
  ```
  
- spring-context.xml：
  
  ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
                               http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!--上面都是一些简单对象的创建，是直接通过反射调用构造方法来创建 ，
          下面说一下复杂对象的创建（无法通过反射来创建的对象）-->
        <!--如：数据库连接对象 Connection  SqlSessionFactory 要用FactoryBean-->
        <!--当从工厂中索要一个bean时，如果是FactoryBean，实际返回的是该工厂bean中的getObject方法的返回值-->
        <bean id="conn" class="com.zssea.factorybean.MyConnectionFactoryBean"></bean>
    </beans>
  ```
  
- TestSpringFactory：
  
  ```java
    package com.zssea.test;
    
    import com.zssea.factorybean.MyConnectionFactoryBean;
    import org.junit.Test;
    import org.springframework.context.ApplicationContext;
    import org.springframework.context.support.ClassPathXmlApplicationContext;
    
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    
    public class TestSpringFactory {
    
    
        @Test
        public void testFactoryBean() throws SQLException {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
            Connection conn = (Connection) applicationContext.getBean("conn"); //这是通过FactoryBean创建出 Connection
            System.out.println(conn);
    
            MyConnectionFactoryBean bean = (MyConnectionFactoryBean) applicationContext.getBean("&conn");//这是获取到工厂Bean本身
            System.out.println(bean);
    
            PreparedStatement preparedStatement = conn.prepareStatement("select * from t_user");
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getInt("id"));
        }
    }
    /*
    com.mysql.jdbc.JDBC4Connection@5d20e46
    com.zssea.factorybean.MyConnectionFactoryBean@709ba3fb
    7
    */
  ```
  
## 十一、Spring工厂 特性

### 11.1饿汉式创建优势

- 工厂创建之后，会将Spring配置文件中的所有对象都创建完成 (饿汉式,创建单例时) 。
  - 提高程序运行效率。避免多次IO，减少对象创建时间。(概念接近连接池，一次性创建好，使用时直接获取)
  
### 11.2生命周期方法

- 自定义初始化方法：添加"init-method"属性， Spring则会在创建对象之后，调用此方法。
  - 自定义销毁方法：添加"dstroy-method"属性， Spring则会在销毁对象之前，调用此方法。
  - 销毁：工厂的close(方法被调用之后，Spring会毁掉所有已创建的单例对象。
  - 分类：Singleton对象由Spring容器销毁、Prototype对象由JVM销毁。
  
### 11.3生命周期演示

```java
  package com.zssea.entity;
  
  public class Address {
      private Integer id;
      private String city;
  
      public Address(){
          System.out.println("Addres 构造方法");
      }
      public Integer getId() {
          return id;
      }
  
      public void setId(Integer id) {
          System.out.println("Address SetId");
          this.id = id;
      }
  
      public String getCity() {
          return city;
      }
  
      public void setCity(String city) {
          this.city = city;
      }
  
      public void init_zssea(){
          System.out.println("Address 初始化");
      }
  
      public void destroy_zssea(){
          System.out.println("Address 销毁");
      }
  }
```

- spring-context2.xml
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd">
  
  
      <!--测试看一下下面这个bean 的构造方法，set方法 初始化方法 销毁方法-->
      <bean id="addr" class="com.zssea.entity.Address" init-method="init_zssea" destroy-method="destroy_zssea">
          <property name="id" value="1"/>
          <property name="city" value="beijing"/>
      </bean>
  </beans>


  package com.zssea.test;
  public class TestSpringFactory {
  
      @Test
      public void testLife(){
          //先演示单例,Spring创建单例模式时是饿汉式创建，当加载完配置文件，启动工厂之后，配置文件中的bean 就已经被创建好
          //加载配置文件，启动工厂
          ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context2.xml");
          //运行完上面这一句  构造方法，set方法 初始化方法 就已经运行了
  
          //关闭工厂 这个方式是ClassPathXmlApplicationContext 这个子类的方法 ，ApplicationContext 父类不包含
          applicationContext.close(); //这时调用销毁方法
          
          /*
          Addres 构造方法
          Address SetId
          Address 初始化
          Address 销毁
          */
          
          // //多例 scope="prototype"，多例是使用时才被创建,懒汉式
          //加载配置文件，启动工厂
          ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context2.xml");
  
          applicationContext.getBean("addr");//此时才被创建
  
          applicationContext.close(); //此时不调用销毁方法
          
          /*
          Addres 构造方法
          Address SetId
          Address 初始化
          */
      }
  }
```

### 11.4生命周期阶段

- 单例bean：singleton
    随工厂启动创建==》构造方法 ==》set方法(注入值) ==》init(初始化) 》构建完成》随工厂关闭销毁
  - 多例bean：prototype
    被使用时创建==》构造方法==》set方法(注入值) ==》init(初始化) ==》构建完成 ==》JVM垃圾回收销毁
  
## 十二、代理设计模式

### 12.1概念

- 将核心功能与辅助功能(事务、日志、性能监控代码)分离，达到核心业务功能更纯粹、辅助业务功能可复用。
  

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200905220402844.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 12.2静态代理设计模式

- 通过代理类的对象，为原始类的对象(目标类的对象)添加辅助功能，更容易更换代理实现类、利于维护。
  - 代理类 = 实现原始类相同接口 + 添加辅助功能 + 调用原始类的业务方法。
  - 静态代理的问题：
    - 代理类数量过多，不利于项目的管理。
    - 多个代理类的辅助功能代码冗余，修改时，维护性差。
  

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200905220414922.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- FangDongService
  
  ```java
    package com.zssea.fangdong;
    
    public interface FangDongService {
        public void zufang();
    }
  ```
  
- FangDongServiceImpl
  
  ```java
    package com.zssea.fangdong;
    
    // 原始业务类
    public class FangDongServiceImpl implements FangDongService{
    
        public void zufang() {
            // 核心功能
            System.out.println("签合同");
            System.out.println("收房租");
        }
    }
  ```
  
- FangDongProxy
  
  ```java
    package com.zssea.fangdong;
    
    // 静态代理类
    public class FangDongProxy  implements FangDongService{
    
        private FangDongService fangDongService = new FangDongServiceImpl();
    
        public void zufang() {
            // 辅助功能、额外功能
            System.out.println("发布租房信息");
            System.out.println("带租客看房");
    
            // 核心功能 = 原始业务类
            fangDongService.zufang();
        }
    }
  ```
  
- ProxyTest
  
  ```java
    package com.zssea.test;
    
    import com.zssea.fangdong.FangDongProxy;
    import org.junit.Test;
    
    public class ProxyTest {
    
        @Test
        public void test01(){
            FangDongProxy fangDongProxy = new FangDongProxy();
            fangDongProxy.zufang();
        }
    }
  ```
  
### 12.3动态代理设计模式

- 动态创建代理类的对象，为原始类的对象添加辅助功能。
  
#### 12.3.1 JDK动态代理实现(基于接口)

```java
  package com.zssea.test;
  
  import com.zssea.fangdong.FangDongService;
  import com.zssea.fangdong.FangDongServiceImpl;
  import org.junit.Test;
  import org.springframework.cglib.proxy.Enhancer;
  
  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.lang.reflect.Proxy;
  
  public class DynamicProxyTest {
  
      //JDK动态代理实现(基于接口)
      @Test
      public void testJDK(){
  
          //目标
          final FangDongService fangDongService = new FangDongServiceImpl();
  
          //额外功能
          InvocationHandler invocationHandler = new InvocationHandler() {
              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                  // 辅助功能、额外功能
                  System.out.println("发布租房信息1");
                  System.out.println("带租客看房1");
  
                  //核心功能
                  fangDongService.zufang();
                  return null;
              }
          };
  
          //动态生成 代理类
          FangDongService proxy = (FangDongService)Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(),
                  fangDongService.getClass().getInterfaces(),
                  invocationHandler);
  
          proxy.zufang();
      }
  
  }
```

#### 12.3.2 CGlib动态代理实现(基于继承)

```java
  package com.zssea.test;
  
  import com.zssea.fangdong.FangDongService;
  import com.zssea.fangdong.FangDongServiceImpl;
  import org.junit.Test;
  import org.springframework.cglib.proxy.Enhancer;
  
  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.lang.reflect.Proxy;
  
  public class DynamicProxyTest {
  
     
      // CGlib动态代理实现(基于继承)
      @Test
      public void testCglib(){
          //目标
          final FangDongService fangDongService = new FangDongServiceImpl();
  
          Enhancer enhancer = new Enhancer(); ;//1.创建字节码曾强对象
          enhancer.setSuperclass(FangDongServiceImpl.class);//2.设置父类(等价于实现原始类接口)
          enhancer.setCallback(new org.springframework.cglib.proxy.InvocationHandler() {
              public Object invoke(Object o, Method method, Object[] objects) throws Throwable {//3.设置回调函数(额外功能代码)
                  // 辅助功能、额外功能
                  System.out.println("发布租房信息2");
                  System.out.println("带租客看房2");
  
                  //核心功能
                  fangDongService.zufang();
                  return null;
              }
          });
  
          //动态生成 代理类
          FangDongServiceImpl proxy = (FangDongServiceImpl)enhancer.create();
          proxy.zufang();
      }
  }
```

## 十三、面向切面编程【重点】

### 13.1概念

- AOP (Aspect Oriented Programming)，即面向切面编程，利用一种称为“横切”的技术，剖开封装的对象内部，并将那些影响了多个类的公共行为封装到一个可重用模块，并将其命名为"Aspect"，即切面。
  - 所谓"切面"，简单说就是那些与业务无关，却为业务模块所共同调用的逻辑或责任封装起来，便于减少系统的重复代码，降低模块之间的耦合度，并有利于未来的可操作性和可维护性。
  
### 13.2 AOP开发术语

- 连接点(Joinpoint)：连接点是程序类中客观存在的方法，可被Spring拦 截并切入内容。（业务中的那些方法都可以被称为连接点）
  - 切入点(Pointcut)： 被Spring切入连接点。（真正被我们采用了的方法）
  - 通知、增强(Advice)： 可以为切入点添加额外功能，分为:前置通知、后置通知、异常通知、环绕通知等。
  - 目标对象(Target)： 代理的目标对象
  - 引介(Introduction)： 一种特殊的增强，可在运行期为类动态添加Field和Method。
  - 织入(Weaving)： 把通知应用到具体的类，进而创建新的代理类的过程。
  - 代理(Proxy)： 被AOP织入通知后，产生的结果类。
  - 切面(Aspect)： 由切点和通知组成，将横切逻辑织入切面所指定的连接点中。
  
### 13.3作用

- Spring的AOP编程即是通过 **动态代理类 **为原始类的方法添加辅助功能。
  
### 13.4环境搭建

- 引入AOP相关依赖
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.zssea</groupId>
      <artifactId>spring02</artifactId>
      <version>1.0-SNAPSHOT</version>
  
      <dependencies>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-context</artifactId>
              <version>5.1.6.RELEASE</version>
          </dependency>
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.12</version>
          </dependency>
  
          <!--AOP依赖-->
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-aspects</artifactId>
              <version>5.1.6.RELEASE</version>
          </dependency>
      </dependencies>
  
  </project>
```

- spring-context.xml引入AOP命名空间
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/aop
                             http://www.springframework.org/schema/aop/spring-aop.xsd">
  
  
  </beans>
```

### 13.5开发流程

- 定义原始类
  
```java
  package com.zssea.service;
  import com.zssea.entity.User;
  import java.util.List;
  public interface UserService {
  
      public List<User> queryUsers();
      public Integer updateUser(User user);
      public Integer saveUser(User user);
      public Integer deleteUser(Integer id);
  }
  
 

  package com.zssea.service;
  
  import com.zssea.entity.User;
  
  import java.util.ArrayList;
  import java.util.List;
  
  public class UserServiceImpl implements UserService{
  
      public List<User> queryUsers() {
  
          //额外功能
  //        System.out.println("事务控制");
  //        System.out.println("日志打印");
          //核心功能
          System.out.println("queryUsers");
          return new ArrayList<User>();
      }
  
      public Integer updateUser(User user) {
  //        System.out.println("事务控制");
  //        System.out.println("日志打印");
          System.out.println("updateUser");
          return 1;
      }
  
      public Integer saveUser(User user) {
  //        System.out.println("事务控制");
  //        System.out.println("日志打印");
          System.out.println("saveUser");
          return 1;
      }
  
      public Integer deleteUser(Integer id) {
  //        System.out.println("事务控制");
  //        System.out.println("日志打印");
          System.out.println("deleteUser");
          return 1;
      }
  }
```

- 定义通知类（添加额外功能）,切面也就是通知类
  
```java
  package com.zssea.advice;
  
  import org.springframework.aop.MethodBeforeAdvice;
  import java.lang.reflect.Method;
  
  // 前置通知类
  public class MyBeforeAdvice implements MethodBeforeAdvice {
  
      public void before(Method method, Object[] objects, Object o) throws Throwable {
          // 额外功能
          System.out.println("事务控制2");
          System.out.println("日志打印2");
      }
  }
```

- 定义bean标签
  - 定义切入点(PointCut)，形成切面(Aspect)
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/aop
                             http://www.springframework.org/schema/aop/spring-aop.xsd">
  
  
      <!-- 目标：原始业务-->
      <bean id="userService" class="com.zssea.service.UserServiceImpl"></bean>
  
      <!-- 通知：额外功能 ,也称切面类-->
      <bean id="before" class="com.zssea.advice.MyBeforeAdvice"></bean>
      <bean id="after" class="com.zssea.advice.MyAfterAdvice"></bean>
      <bean id="throws" class="com.zssea.advice.MyThrowsAdvice"></bean>
      <bean id="mi" class="com.zssea.advice.MyMethodInterceptor"></bean>
  
      <!-- 编织 -->
      <aop:config>
          <!-- 切入点(就是原始业务中的一个方法)，核心功能
            切入点表达式 execution(* com.zssea.service.*.*(..))
            【修饰符  返回值  包.类  方法名  参数表 】-->
          <aop:pointcut id="pc_zssea" expression="execution(* queryUsers())"/>
          <aop:pointcut id="pc_zssea2" expression="execution(* deleteUser())"/>
          <aop:pointcut id="pc_zssea3" expression="execution(* updateUser())"/>
          <aop:pointcut id="pc_zssea4" expression="execution(* saveUser())"/>
  
          <!-- 组装 切面（只有一个通知和一个切入点，就是将额外功能与核心功能组装到一起，也可以理解为 为原始类的核心功能添加辅助功能）：
           advice-ref 通知引用 pointcut-ref 切入点引用-->
          <aop:advisor advice-ref="before" pointcut-ref="pc_zssea"/>
          <aop:advisor advice-ref="after" pointcut-ref="pc_zssea2"/>
          <aop:advisor advice-ref="throws" pointcut-ref="pc_zssea3"/>
          <aop:advisor advice-ref="mi" pointcut-ref="pc_zssea4"/>
  
      </aop:config>
  
  </beans>
```

- 测试
  
```java
  package com.zssea.test;
  
  import com.zssea.entity.User;
  import com.zssea.fangdong.FangDongService;
  import com.zssea.fangdong.FangDongServiceImpl;
  import com.zssea.service.UserService;
  import org.junit.Test;
  import org.springframework.cglib.proxy.Enhancer;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  import java.lang.reflect.InvocationHandler;
  import java.lang.reflect.Method;
  import java.lang.reflect.Proxy;
  
  public class DynamicProxyTest {
  
  
      @Test
      public void testSpringAop(){
          //加载配置文件，启动工厂
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring-context.xml");
  
          //通过目标的bean id，获得代理对象
          UserService userService = (UserService) applicationContext.getBean("userService");
  
          System.out.println(userService.getClass());//class com.sun.proxy.$Proxy7 
          userService.queryUsers();
      }
  }
  /*
  class com.sun.proxy.$Proxy7  (这就是代理对象)
  事务控制2
  日志打印2
  queryUsers
  */
```

- **要明白一点：Spring–AOP中配置完之后返回的代理对象的生成时刻，动态代理的生成时刻是在目标Bean的后处理过程中去额外生成的一个代理**
  
### 13.6 AOP小结

- 通过AOP提供的编码流程，更便利的定制切面，更方便的定制了动态代理。
  - 进而彻底解决了辅助功能冗余的问题;
  - 业务类中职责单一性得到更好保障;
  - 辅助功能也有很好的复用性。
  
### 13.7通知类[可选]

- 定义通知类，达到通知效果
  
```java
  前置通知: MethodBeforeAdvice
  后置通知: AfterAdvice
  后置通知: AfterReturningAdvice //有异常不执行， 方法会因异常而结束，无返回值
  异常通知: ThrowsAdvice
  环绕通知: MethodInterceptor
```

### 13.8通配切入点（切入点表达式）

- 根据表达式通配切入点
  
```xml
  <aop:config>
  		<!--切入点表达式-->
          <!--表达式含义： 参数为User 的方法， 此处包括updateUser 和 saveUser方法-->
          <aop:pointcut id="pc01" expression="execution(* *(com.zssea.entity.User))"/>
          <!--无参的方法-->
          <aop:pointcut id="pc02" expression="execution(* *())"/>
          <!-- 方法名为saveUser，参数任意-->
          <aop:pointcut id="pc03" expression="execution(* saveUser(..))"/>
          <!-- 返回值为User 的方法，方法名任意，参数任意-->
          <aop:pointcut id="pc04" expression="execution(com.zssea.entity.User *(..))"/>
          <!-- com.zssea.service.UserServiceImpl 这个类里的方法-->
          <aop:pointcut id="pc05" expression="execution(* com.zssea.service.UserServiceImpl.*(..))"/>
          <!-- com.zssea.service 这个包下的所有类的方法-->
          <aop:pointcut id="pc06" expression="execution(* com.zssea.service.*.*(..))"/>
          <!-- com 这个包下及其子包下的所有类的方法-->
          <aop:pointcut id="pc07" expression="execution(* com..*.*(..))"/>
          <aop:advisor advice-ref="before" pointcut-ref="pc07"/>
      </aop:config>
```

### 13.9 JDK和CGLIB选择

- spring底层， 包含了jdk代理和glib代理两种动态代理生成机制
  - 基本规则是：目标业务类如果有接口则用JDK代理，没有接口则用CGLib代理
  
```java
  public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {
      public DefaultAopProxyFactory() {
      }
  
      public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
          if (!config.isOptimize() && !config.isProxyTargetClass() && !this.hasNoUserSuppliedProxyInterfaces(config)) {
              return new JdkDynamicAopProxy(config);
          } else {
              Class<?> targetClass = config.getTargetClass();
              if (targetClass == null) {
                  throw new AopConfigException("TargetSource cannot determine target class: Either an interface or a target is required for proxy creation.");
              } else {
                  return (AopProxy)(!targetClass.isInterface() && !Proxy.isProxyClass(targetClass) ? new ObjenesisCglibAopProxy(config) : new JdkDynamicAopProxy(config));
              }
          }
      }
   ...................................   
  }
```

### 13.10后处理器

- spring中定义了 很多后处理器;
  - 每个bean在创建完成之前， 都会有一个后处理过程，即再加工，对bean做出相关改变和调整;
  - spring-AOP中， 就有一个专门的后处理器，负责通过原始业务组件(ervice),，再加工得到一个代理组件。
  

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200905220443251.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 13.10.1后处理器定义

```java
  package com.zssea.processor;
  
  import org.springframework.beans.BeansException;
  import org.springframework.beans.factory.config.BeanPostProcessor;
  import org.springframework.lang.Nullable;
  
   // bean 周期： 构造  、 set、xxx、 init、 xxx、 destroy
  // 后处理器
  public class MyBeanPostProcessor implements BeanPostProcessor {
  
     //在bean的init方法之前执行
      public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
          System.out.println("后处理1");
          System.out.println("后处理1:"+bean+"  :"+beanName);
          return bean;
      }
  
       //在bean的init方法之后执行
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
          System.out.println("后处理2");
          System.out.println("后处理2:"+bean+"  :"+beanName);
          return bean;
      }
  }
```

#### 13.10.2配置后处理器

```xml
  	<!--配置后处理器，将对工厂中所有的bean声明周期进行干预-->
      <bean class="com.zssea.processor.MyBeanPostProcessor"></bean>
```

#### 13.10.3 bean生命周期

- 构造》注入属性 满足依赖》后处理器前置过程》初始化》后处理器 后置过程》返回》销毁
  
#### 13.10.4动态代理源码(了解)

```java
  // AbstractAutoProxyCreator 是AspectJAwareAdvisor AutoProxyCreator的父类
  //该后处理器类中的wrapIfNecessary方 法即动态代理生成过程
  AbstractAutoProxyCreator#postProcessAfter Initialization(0bject bean, String beanName){
      if (!this.earlyProxyReferences.contains(cacheKey)) {
      	//开始动态定制代理
      	return wrapIfNecessary( bean, beanName, cacheKey);
  	}
  }
```

- **要明白一点：Spring–AOP中配置完之后返回的代理对象的生成时刻，动态代理的生成时刻是在目标Bean的后处理过程中去额外生成的一个代理 **
  
## 十四、Spring + MyBatis [重点]

### 14.1配置数据源

- 将 数据源 配置到项目中
  
#### 14.1.1引入jdbc.properties配置文件

```properties
  jdbc.driverClass=com.mysql.jdbc.Driver
  jdbc.url=jdbc:mysql://localhost:3306/mybatis_zssea?useUnicode=true&characterEncoding=UTF-8
  jdbc.username=root
  jdbc.password=123456
```

#### 14.1.2整合Spring配置文件和properties配置文件

- applicationContext.xml：
  
```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xmlns:tx="http://www.springframework.org/schema/tx"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
                             http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/aop
                             http://www.springframework.org/schema/aop/spring-aop.xsd
                             http://www.springframework.org/schema/tx
                             http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
  
  
      <!-- 导入文件-->
      <context:property-placeholder location="classpath:jdbc.properties"/>
      
      <!--与PooledDataSource集成 (二选一) -->
      <bean id="dataSource" class="org.apache.ibatis.datasource.pooled. PooledDataSource" >
          <property name="driver" value= "${driverClass}"/>
          <property name="url" value="${ur1}"/>
          <property name="username" value=" ${username}"/>
          <property name="password" value= "${password}" />
      < /bean>
  
     
      <!--创建数据源-->
      <!-- 与DruidDataSource集成(二选一) -->
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
          <!--基本配置-->
          <property name="driverClassName" value="${jdbc.driverClass}"/>
          <property name="url" value="${jdbc.url}"/>
          <property name="username" value="${jdbc.username}"/>
          <property name="password" value="${jdbc.password}"/>
      </bean>
  </beans>
```

#### 14.1.3 Druid连接池可选参数

```xml
  <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
          <!--基本配置-->
          <property name="driverClassName" value="${jdbc.driverClass}"/>
          <property name="url" value="${jdbc.url}"/>
          <property name="username" value="${jdbc.username}"/>
          <property name="password" value="${jdbc.password}"/>
  
          <!-- 配置初始化大小、最小、最大 -->
          <property name="initialSize" value="${jdbc.init}"/>
          <property name="minIdle" value="${jdbc.minIdle}"/>
          <property name="maxActive" value="${jdbc.maxActive}"/>
  
          <!-- 配置获取连接等待超时的时间 ms-->
          <property name="maxWait" value="60000"/>
  
          <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
          <property name="timeBetweenEvictionRunsMillis" value="60000"/>
  
          <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
          <property name="minEvictableIdleTimeMillis" value="300000"/>
      </bean>
```

#### 14.1.4 Druid监控中心

```xml
  <!--web.xml-->
  <servlet>
      <servlet-name>DruidStatView</servlet-name>
      <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
  </servlet>
  <servlet-mapping>
      <servlet-name>DruidStatView</ servlet-name>
      <url-pattern>/druid/*</ur1-pattern>
  </servlet-mapping>
```

#### 14.1.5测试监控中心

- 配置tomcat，并访问protocol://ip:port/project/druid/index.xml
  
### 14.2整合MyBatis

- 将SqlSessionFactory、DAO、 Service 配置到项目中
  - MyBatis的配置文件就不用写了，它里面的内容都在Spring的配置文件（applicationContext.xml）中写了
  
#### 14.2.1导入依赖

```xml
   		<dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-jdbc</artifactId>
              <version>5.1.6.RELEASE</version>
          </dependency>
  
          <!-- Spring整合mybatis -->
          <dependency>
              <groupId>org.mybatis</groupId>
              <artifactId>mybatis-spring</artifactId>
              <version>1.3.1</version>
          </dependency>
```

#### 14.2.2配置SqlSessionFactory

```xml
  <!-- 生产：SqlSessionFactory -->
      <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
          <!-- 注入连接池 -->
          <property name="dataSource" ref="dataSource"></property>
          <!-- 注入dao-mapper文件信息(声明Mapper文件的位置) ,如果映射文件和dao接口 同包且同名，则此配置可省略-->
          <property name="mapperLocations">
              <list>
                  <value>classpath:com/zssea/dao/*.xml</value>
              </list>
          </property>
          <!-- 为 dao-mapper文件中的实体 定义缺省包路径 （别名）
              如：<select id="queryAll" resultType="User"> 中 User类可以不定义包
          -->
          <property name="typeAliasesPackage" value="com.qf.entity"></property>
      </bean>
```

#### 14.2.3配置MapperScannerConfigurer

- 管理DAO实现类的创建，并创建DAO对象，存入工厂管理：
    - 扫描所有DAO接口,去构建DAO实现
    - 将DAO实现存入工厂管理
    - DAO实现对象在工厂中的id是：“首字母小写的接口的类名"，
      例如: UserDAO==>（bean 标签的id：）userDAO ，OrderDAO==>orderDAO

```xml
   <!-- mapperScannerConfigurer  管理DAO实现类的创建，并创建DAO对象，存入工厂管理
          当执行完这个配置之后，在工厂中就会有一个id为userDAO 的bean，而这个bean就是UserDAO实现类的对象 -->
      <bean id="mapperScannerConfigurer9" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
          <!-- dao接口所在的包  如果有多个包，可以用逗号或分号分隔
             <property name="basePackage" value="com.a.dao,com.b.dao"></property>
          -->
          <property name="basePackage" value="com.zssea.dao"></property>
          <!-- 如果工厂中只有一个SqlSessionFactory的bean，此配置可省略 -->
          <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"></property>
      </bean>
```

#### 14.2.4配置Service

```xml
  	 <!-- Service -->
      <bean id="userService" class="com.zssea.service.UserServiceImpl">
          <!-- ref="userDAO" 中的userDAO 是由mapperScannerConfigurer 创建出来的 -->
          <property name="userDAO" ref="userDAO"/>
      </bean>
```

## 十五、事务【重点】

### 15.1配置DataSourceTransactionManager

- 事务管理器，其中持有DataSource, 可以控制事务功能(commit,rollback等) 。
  
  ```xml
     <!-- 1. 引入一个事务管理器，其中依赖DataSource,借以获得连接，进而控制事务逻辑 -->
        <bean id="tx" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
            <property name="dataSource" ref="dataSource"></property>
        </bean>
  ```
  
- 注意: DataSourceTransactionManager 和SqlSessionFactoryBean要注入同一个DataSource的Bean,否则事务控制失败!!
  
### 15.2配置事务通知

- 基于事务管理器，进一步定制， 生成一个额外功能: Advice。
  - 此Advice可以切入任何需要事务的方法，通过事务管理器为方法控制事务。
  - (事务可以理解为一个环绕型的通知，额外功能)
  
```xml
   <!-- 事务通知 -->
      <!--transaction-manager="tx" 是事物管理器的id-->
      <tx:advice id="txManager" transaction-manager="tx">
          <!--事务属性-->
          <tx:attributes>
              <!-- name="queryUser" 是方法名-->
              <tx:method name="queryUser" isolation="DEFAULT" propagation="SUPPORTS" read-only="true" timeout="-1" rollback-for="Exception"/>
              <tx:method name="queryUsers"  propagation="SUPPORTS"/>
               <!--以User结尾的方法，切入此方法时，采用对应事务实行-->
              <tx:method name="*User" rollback-for="Exception"/>
              <!--以query开头的方法，切入此方法时，采用对应事务实行-->
              <tx:method name="query*" propagation="SUPPORTS"/>
              <!--剩余所有方法-->
              <tx:method name="*"/>
          </tx:attributes>
      </tx:advice>
```

### 15.3事务属性

#### 15.3.1隔高级别

##### 15.3.1.1 概念

- isolation 隔离级别
  
| 名称            | 描述                                     |
| --------------- | ---------------------------------------- |
| default         | (默认值) (采用数据库的默认的设置) (建议) |
| read-uncommited | 读未提交                                 |
| read-commited   | 读提交 (Oracle数据库默认的隔离级别)      |
| repeatable-read | 可重复读 (MySQL数据库默认的隔离级别)     |
| serialized-read | 序列化读                                 |

- 隔离级别由低到高为: read-uncommited < read-commited < repeatable-read < serialized-read
  
##### 15.3.1.2特性

- 安全性：级别越高，多事务并发时，越安全。因为共享的数据越来越少，事务间彼此干扰减少。
  - 并发性：级别越高，多事务并发时，井发越差。因为共享的数据越来越少，事务间阻塞情况增多。
  
##### 15.3.1.3并发问题

- 事务并发时的安全问题
  
| 问题       | 描述                                                         |
| ---------- | ------------------------------------------------------------ |
| 脏读       | 一个事务读取到另一个事务还未提交的数据。大于等于read-commited可防止 |
| 不可重复读 | 一个事务内多次读取一行数据的相同内容，其结果不一 致。 大于等于repeatable read可防止（一般更新时会遇见） |
| 幻影读     | 一个事务内多次读取一张表中的相同内容，其结果不一致。 serialized read可防止（一般插入玉与删除表数据时会遇见） |

#### 15.3.2传播行为

- propagation 传播行为
  - 当涉及到事务嵌套(Service调用Service，Service层中是是事务操作的) 时，可以设置:
    - SUPPORTS=不存在外部事务，则不开启新事务；存在外部事务，则合并到外部事务中。 (适合查询）
    - REQUIRED=不存在外部事务， 则开启新事务；存在外部事务，则合并到外部事务中。(默认值) (适合增删改)
  
#### 15.3.3读写性

- readonly 读写性
    - true: 只读，可提高查询效率。(适合查询)
    - false: 可读可写。(默认值) (适合增删改)

#### 15.3.4事务超时

- timeout 事务超时时间
  - 当前事务所需操作的数据被其他事务占用，则等待。
    - 100: 自定义等待时间100 (秒)，
    - -1: 由数据库指定等待时间，默认值。(建议)
  
#### 15.3.5事务回滚

- rollback-for 回滚属性
    - 如果事务中抛出RuntimeException,则自动回滚
    - 如果事务中抛出CheckException(非运行时异常Exception),不会自动回滚，而是默认提交事务
    - 处理方案:将CheckException转换成RuntimException上抛， 或设置rollback-for=“Exception”（遇见异常就回滚）

### 15.4编织

- 将事务管理的Advice切入需要事务的业务方法中
  
```xml
   <!-- 编织 声明式事务控制 -->
      <aop:config>
          <aop:pointcut id="pc_tx" expression="execution(* com.zssea.service.UserServiceImpl.*(..))"/>
          <aop:advisor advice-ref="txManager" pointcut-ref="pc_tx"/>
      </aop:config>
```

## 十六、注解开发

### 16.1声明bean

- 用于替换自建类型组件的<bean…>标签；可以更快速的声明bean
  - @Service 业务类专用
  - @Repository dao实现类专用
  - @Controller web层专用
  - @Component 通用
  - @Scope 用户控制bean的创建模式
  
```java
  //@Service说明此类是一 个业务类，需要将此类纳入工厂  等价于// <bean id="userServiceImpl" class="xx.x.x.UserServiceImpl">
  // @Service默认beanId ==首字母小写的类名“userServiceImpl"
  @Service("userService") //自定义id 等价于 <bean id="userService" class="xx.x.x.UserServiceImpl">
  //@Scope("prototype") // 声明创建模式为多例模式bean ，service不需要多例
  public class UserServiceImpl implements UserService{
      ........
  }
```

### 16.2注入(DI)

- 用于完成bean中属性值的注入
  - @Autowired 基于类型自动注入
  - @Resource 基于名称自动注入
  - @Qulifier(“userDA0”) 限定要自动注入的bean的id,一 般和@Autowired联用
  - @Value 注入简单类型数据(jdk8种+String)
  
```java
  @Service
  public class UserServiceImpl implements UserService{
      @Autowired //注入类型为UserDA0的bean
      @Qualifier("userDA02") //如果有 多个类型为UserDA0的bean,可以用此注解从中挑选一个
      private UserDAO userDAO;
  }
  123456
  @Service
  public class UserServiceImpl implements UserService {
      @Resource("userDA03") //注入id="userDA03"的bean
      private UserDA0 userDAO:
      @Resource //注入id="userDA0"的bean
      private UserDAO userDAO;
  }
  1234567
  public class XX{
      @Value("100") //注入数字
      private Integer id;
      @Value("shine") //注入String
      private String name;
  }
```

### 16.3事务控制

- 用于控制事务切入 (业务层)
  - @Transactional
  - 工厂配置中的 <tx:advice… 和 <ap:confg… 可以省略!!
  
```java
  //类中的每个方法都切入事务(有自己的事务控制的方法除外)
  @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,timeout = -1,readOnly = false,rollbackFor = Exception.class)
  public class UserServiceImpl implements UserService{
      ...
          
      //该方法自己的事务控制，仅对此方法有效
      @Transactional(propagation=Propagation.SUPPORTS)
      public List<User> queryAll() {
      	return userDao.queryAll();
      }
      public void save(User user){
      	userDao.save(user);
      }
  
  }
```

### 16.4注解所需配置

```xml
      <!--扫描注解 告知spring注解位置 base-package="com.zssea"这个包下有很多类使用了注解-->
      <context:component-scan base-package="com.zssea"></context:component-scan>
  
      <!-- 只针对 这个@Transactional 注解 ，告知spring，@Transactional在定制事务时， 基于id为tx的事务管理器 -->
      <tx:annotation-driven transaction-manager="tx"/>
```

### 16.5 AOP开发

#### 16.5.1注解使用

```java
  package com.zssea.aspect;
  
  import org.aspectj.lang.JoinPoint;
  import org.aspectj.lang.ProceedingJoinPoint;
  import org.aspectj.lang.annotation.*;
  import org.springframework.stereotype.Component;
  
  @Aspect // 声明此类是一个切面类：会包含切入点(pointcut)和通知(advice)
  @Component //声明组件，进入工厂
  public class MyAspect {
      // 定义切入点
      @Pointcut("execution(* com.zssea.service.UserServiceImpl.*(..))")
      public void pc(){} //定义这个方法是为了引入切入点
  
      @Before("pc()") // 前置通知
      public void mybefore(JoinPoint a) {
          System.out.println("target:"+a.getTarget());
          System.out.println("args:"+a.getArgs());
          System.out.println("method's name:"+a.getSignature().getName());
          System.out.println("before~~~~");
      }
  
      @AfterReturning(value="pc()",returning="ret") // 后置通知 ，ret是核心方法的返回值
      public void myAfterReturning(JoinPoint a,Object ret){
          System.out.println("after~~~~:"+ret);
      }
  
      @Around("pc()") // 环绕通知
      public Object myInterceptor(ProceedingJoinPoint p) throws Throwable {
          System.out.println("interceptor1~~~~");
          Object ret = p.proceed();
          System.out.println("interceptor2~~~~");
          return ret;
      }
      @AfterThrowing(value="pc()",throwing="ex") // 异常通知
      public void myThrows(JoinPoint jp,Exception ex){
          System.out.println("throws");
          System.out.println("===="+ex.getMessage());
      }
  }
```

### 16.5.2配置

```xml
      <!--扫描注解 告知spring注解位置 base-package="com.zssea"这个包下有很多类使用了注解-->
      <context:component-scan base-package="com.zssea"></context:component-scan> 
  	
  	<!-- 添加如下配置,启用aop注解 -->
   	<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
```

## 十七、集成JUnit

### 17.1导入依赖

```xml
  		<dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-test</artifactId>
              <version>5.1.6.RELEASE</version>
          </dependency>
  		<!-- Junit -->
          <dependency>
              <groupId>junit</groupId>
              <artifactId>junit</artifactId>
              <version>4.12</versi
```

### 17.2编码

- 可以免去工厂的创建过程;
  - 可以直接将要测试的组件注入到测试类。
  
```java
  package com.zssea.test;
  
  import com.zssea.dao.UserDAO;
  import com.zssea.entity.User;
  import com.zssea.service.UserService;
  import org.apache.ibatis.session.SqlSession;
  import org.apache.ibatis.session.SqlSessionFactory;
  import org.junit.Test;
  import org.junit.runner.RunWith;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.beans.factory.annotation.Qualifier;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  import org.springframework.test.context.ContextConfiguration;
  import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
  
  import java.lang.annotation.Retention;
  import java.security.PublicKey;
  import java.sql.SQLException;
  import java.util.Date;
  import java.util.List;
  
  //测试类启动时，会帮我们启动spring工厂，加载配置文件，并且当前测试类也会被工厂生产
  //省去了我们自己要写加载配置文件的代码
  @RunWith(SpringJUnit4ClassRunner.class)
  @ContextConfiguration(locations = "classpath:applicationContext.xml")
  public class TestSpringMyBatis {
  
      @Autowired //依赖类型注入要测试的组件
      @Qualifier("userService2")
      private UserService userService;
  
      @Autowired
      private SqlSessionFactory sqlSessionFactory;
  
      @Test
      public void testSpringJunit() {
          /*List<User> users = userService.queryUsers();
          for (User user : users) {
              System.out.println(user);
          }*/
          SqlSession sqlSession = sqlSessionFactory.openSession();
          UserDAO userMapper = sqlSession.getMapper(UserDAO.class);
          List<User> users = userMapper.queryUsers();
          for (User user : users) {
              System.out.println(user);
          }
      }
       @Test
      public void test() {
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/applicationContext.xml");
          SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
          SqlSession sqlSession = sqlSessionFactory.openSession();
          UserDAO userMapper = sqlSession.getMapper(UserDAO.class);
          List<User> users = userMapper.queryUsers();
          for (User user : users) {
              System.out.println(user);
          }
      }
  
  }
```

