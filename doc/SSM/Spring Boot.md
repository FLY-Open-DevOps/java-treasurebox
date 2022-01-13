# SpringBoot

![](https://img-blog.csdnimg.cn/20181117220632243.png)

SpringBoot 是用来简化Spring的，本质上和Spring是一致的， 核心点是自动装配

## 一、SpringBoot简介

### 1.1回顾什么是Spring

Spring是一个开源框架（就是一个容器，工厂），2003 年兴起的一个轻量级的Java 开发框架，作者：Rod Johnson。

**Spring是为了解决企业级应用开发的复杂性而创建的，简化开发。**

### 1.2Spring是如何简化Java开发的

为了降低Java开发的复杂性，Spring采用了以下4种关键策略：

1、基于POJO的轻量级和最小侵入性编程，所有东西都是bean；

2、通过IOC，依赖注入（DI）和面向接口实现松耦合；

3、基于切面（AOP）和惯例进行声明式编程；

4、通过切面和模版减少样式代码，RedisTemplate，xxxTemplate；

### 1.3什么是SpringBoot

学过javaweb的同学就知道，开发一个web应用，从最初开始接触Servlet结合Tomcat, 跑出一个Hello Wolrld程序，是要经历特别多的步骤；后来就用了框架Struts，再后来是SpringMVC，到了现在的SpringBoot，过一两年又会有其他web框架出现；你们有经历过框架不断的演进，然后自己开发项目所有的技术也在不断的变化、改造吗？建议都可以去经历一遍；

言归正传，什么是SpringBoot呢，就是一个javaweb的开发框架，和SpringMVC类似，对比其他javaweb框架的好处，**官方说是简化开发，约定大于配置（越是简化的东西，越是需要严格的规定）**， you can “just run”，能迅速的开发web应用，几行代码开发一个http接口。

所有的技术框架的发展似乎都遵循了一条主线规律：从一个复杂应用场景 衍生 一种规范框架，人们只需要进行各种配置而不需要自己去实现它，这时候强大的配置功能成了优点；发展到一定程度之后，人们根据实际生产应用情况，选取其中实用功能和设计精华，重构出一些轻量级的框架；之后为了提高开发效率，嫌弃原先的各类配置过于麻烦，于是开始提倡“约定大于配置”，进而衍生出一些一站式的解决方案。

是的，这就是Java企业级应用->J2EE->spring->springboot的过程。

随着 Spring 不断的发展，涉及的领域越来越多，项目整合开发需要配合各种各样的文件，慢慢变得不那么易用简单，违背了最初的理念，甚至人称配置地狱。Spring Boot 正是在这样的一个背景下被抽象出来的开发框架，目的为了让大家更容易的使用 Spring 、更容易的集成各种常用的中间件、开源软件；

Spring Boot 基于 Spring 开发，Spirng Boot 本身并不提供 Spring 框架的核心特性以及扩展功能，只是用于快速、敏捷地开发新一代基于 Spring 框架的应用程序。也就是说，它并不是用来替代 Spring 的解决方案，而是和 Spring 框架紧密结合用于提升 Spring 开发者体验的工具。Spring Boot 以**约定大于配置的核心思想**，默认帮我们进行了很多设置，多数 Spring Boot 应用只需要很少的 Spring 配置。同时它集成了大量常用的第三方库配置（例如 Redis、MongoDB、Jpa、RabbitMQ、Quartz 等等），Spring Boot 应用中这些第三方库几乎可以零配置的开箱即用。

简单来说就是SpringBoot其实不是什么新的框架，它默认配置了很多框架的使用方式，就像maven整合了所有的jar包，spring boot整合了所有的框架 。

Spring Boot 出生名门，从一开始就站在一个比较高的起点，又经过这几年的发展，生态足够完善，Spring Boot 已经当之无愧成为 Java 领域最热门的技术。

**Spring Boot的主要优点：**

- 为所有Spring开发者更快的入门
- **开箱即用**，提供各种默认配置来简化项目配置
- 内嵌式容器简化Web项目
- 没有冗余代码生成和XML配置的要求

真的很爽，我们快速去体验开发个接口的感觉吧！

## 二、微服务

### 2.1什么是微服务

- 微服务是一种架构风格，它要求我们在开发一个应用的时候，这个应用必须构建成一系列小服务的组合;可以通过http的方式进行互通。要说微服务架构，先得说说过去我们的单体应用架构。

### 2.2单体应用架构

- 所谓单体应用架构(all in one)是指，我们将一个应用的中的所有应用服务都封装在一个应用中。
- 无论是ERP、CRM或是其他什么系统，你都把数据库访问，web访问, 等等各个功能放到一个war包内。（目前我们写的项目就是如此，将一个系统整体打包成war包）
- 这样做的好处是，易于开发和测试；也十分方便部署；当需要扩展时，只需要将war复制多份，然后放到多个服务器上，再做个负载均衡就可以了。
- 单体应用架构的缺点是，哪怕我要修改一个非常小的地方，我都需要停掉整个服务，重新打包、部署这个应用war包。特别是对于一个大型应用，我们不可能把所有内容都放在一个应用里面，我们如何维护、如何分工合作都是问题。

### 2.3微服务架构

- all in one的架构方式，我们把所有的功能单元放在一个应用里面。然后我们把整个应用部署到服务器上。如果负载能力不行，我们将整个应用进行水平复制，进行扩展，然后在负载均衡。
- 所谓微服务架构，就是打破之前all in one的架构方式，把每个功能元素独立出来。把独立出来的功能元素的动态组合，需要的功能元素才去拿来组合，需要多一些时，可以整合多个功能元素。**所以微服务架构是对功能元素进行复制，而没有对整个应用进行复制。**
- 下这样做的好处是:
  - 节省了调用资源。
  - 每个功能元素的服务都是一个可替换的、可独立升级的软件代码。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025214737753.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- Martin Flower于2014年3月25日写的《Microservices》 ，详细的阐述了什么是微服务。
- 原文地址: http://martinfowler.com/articles/microservices.html
- 翻译: https://www.cnblogs.com/liuning8023/p/4493156.html

### 2.4如何构建微服务

- 一个大型系统的微服务架构，就像一个复杂交织的神经网络，每-个神经元就是一个功能元素， 它们各自完成自己的功能，然后通过http相互请求调用。比如一个电商系统，查缓存、连数据库、浏览页面、结账、支付等服务都是一个个独立的功能服务,都被微化了，它们作为一个个微服务共同构建了一个庞大的系统。如果修改其中的一个功能，只需要更新升级其中一个功能服务单元即可。
- 但是这种庞大的系统架构给部署和运维带来很大的难度。于是，spring为我们带来了构建大型分布式微服务的全套、全程产品:
  - 构建一个个功能独立的微服务应用单元，可以使用springboot, 可以帮我们快速构建一个应用；
  - 大型分布式网络服务的调用，这部分由spring cloud来完成，实现分布式；
  - 在分布式中间,进行流式数据计算、批处理，我们有spring cloud data flow。
  - spring为我们想清楚了整个从开始构建应用到大型分布式应用全流程方案。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102521480814.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

## 三、第一个SpringBoot程序

### 3.1准备工作

我们将学习如何快速的创建一个Spring Boot应用，并且实现一个简单的Http请求处理。通过这个例子对Spring Boot有一个初步的了解，并体验其结构简单、开发快速的特性。

我的环境准备：

- java version “1.8”
- Maven-3.6.1
- SpringBoot 2.x 最新版 

开发工具：

- IDEA

### 3.2创建基础项目说明

Spring官方提供了非常方便的工具让我们快速构建应用（提供了一个快速生成的网站）

Spring Initializr：https://start.spring.io/

**项目创建方式一：** 使用Spring Initializr 的 Web页面创建项目

1、打开 https://start.spring.io/

2、填写项目信息

3、点击”Generate Project“按钮生成项目；下载此项目

4、解压项目包，并用IDEA以Maven项目导入，一路下一步即可，直到项目导入完毕。

5、如果是第一次使用，可能速度会比较慢，包比较多、需要耐心等待一切就绪。

**项目创建方式二：** 使用 IDEA 直接创建项目（idea继承了上面说的那个网站）

1、创建一个新项目

2、选择spring initalizr ， 可以看到默认就是去官网的快速构建工具那里实现

3、填写项目信息

4、选择初始化的组件（初学勾选 Web 即可）

5、填写项目路径

6、等待项目构建成功

**项目结构分析：**

通过上面步骤完成了基础项目的创建。就会自动生成以下文件。

1、程序的主启动类

2、一个 application.properties 配置文件

3、一个 测试类

4、一个 pom.xml

### 3.3pom.xml 分析

打开pom.xml，看看Spring Boot项目的依赖：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <!--有一个父项目-->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.zssea</groupId>
    <artifactId>hello</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>hello</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!--web依赖： 集成tomcat，配置dispatcherServlet，xml....-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--所有的Springboot依赖都是使用这个前缀：spring-boot-starter-->

        <!--单元测试-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <!--打jar包插件-->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

- 如上所示，主要有四个部分:
  - 项目元数据信息：创建时候输入的Project Metadata部分，也就是Maven项目的基本元素，包括: groupld、 artifactld、 version、 name、 description等
  - parent：继承spring- boot -starter-parent的依赖管理，控制版本与打包等内容
  - dependencies： 项目具体依赖，这里包含了spring-boot-starter -web用于实现HTTP接口(该依赖中包含了Spring MVC)，官网对它的描述是:使用Spring MVC构建Web (包括RESTful)应用程序的入门者，使用T omcat作为默认嵌入式容器。; spring-boot-starter-test用于编写单元测试的依赖包。更多功能模块的使用我们将在后面逐步展开。
  - build: 构建配置部分。默认使用了spring-boot-maven-plugin,配合spring-boot-starter-parent就可以把Spring Boot应用打包成JAR来直接运行。

### 3.4编写一个http接口

1、在主程序的同级目录下，新建一个controller包，一定要在同级目录下，否则识别不到

2、在包中新建一个HelloController类

```java
package com.zssea.hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//自动装配 ： 这是原理！！！
//@Controller
@RestController
public class HelloController {
    // 接口： http://localhost:8080/hello
    @RequestMapping("/hello")
    public String hello(){
        //在此调用service  ，接收前端的参数
        return "hello,world!";
    }
}
```

3、编写完毕后，从主程序启动项目，浏览器发起请求，看页面返回；控制台输出了 Tomcat 访问的端口号！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025214900615.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

简单几步，就完成了一个web接口的开发，SpringBoot就是这么简单。所以我们常用它来建立我们的微服务项目！

### 3.5将项目打成jar包，点击 maven的 package

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025214914709.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 如果遇到以上错误，可以配置打包时 跳过项目运行测试用例

```xml
<!--
    在工作中,很多情况下我们打包是不想执行测试用例的
    可能是测试用例不完事,或是测试用例会影响数据库数据
    跳过测试用例执
    -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
        <!--跳过项目运行测试用例-->
        <skipTests>true</skipTests>
    </configuration>
</plugin>
```

- 如果打包成功，则会在target目录下生成一个 jar 包

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025214934717.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 打成了jar包后，就可以在任何地方运行了！OK

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025214946929.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 3.6小技巧

- 更改tomcat端口号 ( resources 目录下的application.properties 文件)

  ```xml
  #更改项目端口号
  server.port=8082
  ```
  
- 更改启动时显示的字符拼成的字母：到项目下的 resources 目录下新建一个banner.txt 即可，将图案放在这个文件即可

## 四、SpringBoo原理初探【重点】

- 我们之前写的HelloSpringBoot，到底是怎么运行的呢，Maven项目，我们一般从pom.xml文件探究起；

### 4.1pom.xml文件

#### 4.1.1父依赖

其中它主要是依赖一个父项目，主要是管理项目的资源过滤及插件！

```XML
	<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
```

点进去，发现还有一个父依赖

```XML
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-dependencies</artifactId>
    <version>2.3.3.RELEASE</version>
  </parent>
```

这里才是真正管理SpringBoot应用里面所有依赖版本的地方，SpringBoot的版本控制中心；

**以后我们导入依赖默认是不需要写版本（因为有这些版本仓库）；但是如果导入的包没有在依赖中管理着就需要手动配置版本了；**

#### 4.1.2启动器 spring-boot-starter

```XML
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

- **springboot-boot-starter-xxx**：就是spring-boot的场景启动器
- **spring-boot-starter-web**：帮我们导入了web模块正常运行所依赖的组件；
- SpringBoot将所有的功能场景都抽取出来，做成一个个的starter （启动器），如果我们想要使用什么样的功能，只需要在项目中引入相关的（启动器）starter即可，所有相关的依赖都会导入进来 ， 我们未来也可以自己自定义 starter；

### 4.2主启动类 （看注解）

- 分析完了 pom.xml 来看看这个启动类

#### 默认的主启动类

```java
//@SpringBootApplication 来标注一个主程序类
//说明这是一个Spring Boot应用
@SpringBootApplication
public class SpringbootApplication {
   public static void main(String[] args) {
     //以为是启动了一个方法，没想到启动了一个服务
      SpringApplication.run(SpringbootApplication.class, args);
   }
}
```

但是**一个简单的启动类并不简单！** 我们来分析一下这些注解都干了什么

#### 4.2.1@SpringBootApplication

- 作用：标注在某个类上说明这个类是SpringBoot的主配置类 ， SpringBoot就应该运行这个类的main方法来启动SpringBoot应用；
- 进入这个注解：可以看到上面还有很多其他注解！

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class})})
public @interface SpringBootApplication {
   // ......
}
```

##### 4.2.1.1@ComponentScan

- 这个注解在Spring中很重要 ,它对应XML配置中的元素。
- 作用：自动扫描并加载符合条件的组件或者bean ， 将这个bean定义加载到IOC容器中

##### 4.2.1.2@SpringBootConfiguration

- 作用：SpringBoot的配置类 ，标注在某个类上 ， 表示这是一个SpringBoot的配置类；
- 我们继续进去这个注解查看

```java
//点进去得到下面的 @Configuration
@Configuration
public @interface SpringBootConfiguration {}

// 点进去得到下面的 @Component
@Component
public @interface Configuration {}
```

- 这里的 @Configuration，说明这是一个配置类 ，配置类就是对应Spring的xml 配置文件；
- 里面的 @Component 这就说明，启动类本身也是Spring中的一个组件而已，负责启动应用！
- 我们回到 SpringBootApplication 注解中继续看。

##### 4.2.1.3@EnableAutoConfiguration

- @EnableAutoConfiguration ：**开启自动配置功能**

- 以前我们需要自己配置的东西，而现在SpringBoot可以自动帮我们配置 ；@EnableAutoConfiguration告诉SpringBoot开启自动配置功能，这样自动配置才能生效；

- 点进注解接续查看：

  ```java
  @AutoConfigurationPackage
  @Import(AutoConfigurationImportSelector.class)
  public @interface EnableAutoConfiguration {
  .....
  }
  ```

###### 4.2.1.3.1 @AutoConfigurationPackage ：自动配置包

点进去之后是：

```java
@Import(AutoConfigurationPackages.Registrar.class) //自动配置包注册
public @interface AutoConfigurationPackage {}
```

**@import** ：Spring底层注解@import ， 给容器中导入一个组件

Registrar.class 作用：将主启动类的所在包及包下面所有子包里面的所有组件扫描到Spring容器 ；

这个分析完了，退到上一步，继续看

###### 4.2.1.3.2 @Import({AutoConfigurationImportSelector.class}) ：给容器导入组件

- AutoConfigurationImportSelector ：自动配置导入选择器，那么它会导入哪些组件的选择器呢？我们点击去这个类看源码：

1、这个类中有一个这样的方法

```java
// 获得候选的配置
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    //这里的getSpringFactoriesLoaderFactoryClass（）方法
    //返回的就是我们最开始看的启动自动导入配置文件的注解类；EnableAutoConfiguration
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(this.getSpringFactoriesLoaderFactoryClass(), this.getBeanClassLoader());

    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you are using a custom packaging, make sure that file is correct.");
    return configurations;
}

protected Class<?> getSpringFactoriesLoaderFactoryClass() {
		return EnableAutoConfiguration.class;
}
```

2、这个方法又调用了 SpringFactoriesLoader 类的静态方法！我们进入SpringFactoriesLoader类loadFactoryNames() 方法

```java
public static List<String> loadFactoryNames(Class<?> factoryClass, @Nullable ClassLoader classLoader) {
    String factoryClassName = factoryClass.getName();

    //这里它又调用了 loadSpringFactories 方法
    return (List)loadSpringFactories(classLoader).getOrDefault(factoryClassName, Collections.emptyList());
}
```

3、我们继续点击查看 loadSpringFactories 方法

```java
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
    //获得classLoader ， 我们返回可以看到这里得到的就是EnableAutoConfiguration标注的类本身
    MultiValueMap<String, String> result = (MultiValueMap)cache.get(classLoader);
    if (result != null) {
        return result;
    } else {
        try {
            //去获取一个资源 "META-INF/spring.factories"
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources("META-INF/spring.factories") : ClassLoader.getSystemResources("META-INF/spring.factories");
            LinkedMultiValueMap result = new LinkedMultiValueMap();
            //将读取到的资源遍历，封装成为一个Properties （判断有没有更多的元素）
            while(urls.hasMoreElements()) {
                URL url = (URL)urls.nextElement();
                UrlResource resource = new UrlResource(url);
                Properties properties = PropertiesLoaderUtils.loadProperties(resource); //所有资源加载到配置类中
                Iterator var6 = properties.entrySet().iterator();
                while(var6.hasNext()) {
                    Entry<?, ?> entry = (Entry)var6.next();
                    String factoryClassName = ((String)entry.getKey()).trim();
                    String[] var9 = StringUtils.commaDelimitedListToStringArray((String)entry.getValue());
                    int var10 = var9.length;
                    for(int var11 = 0; var11 < var10; ++var11) {
                        String factoryName = var9[var11];
                        result.add(factoryClassName, factoryName.trim());
                    }
                }
            }
            cache.put(classLoader, result);
            return result;
        } catch (IOException var13) {
            throw new IllegalArgumentException("Unable to load factories from location [META-INF/spring.factories]", var13);
        }
    }
}
```

4、发现一个多次出现的文件：spring.factories，全局搜索它 （这是自动配置的核心文件）

- spring.factories

我们根据源头打开spring.factories ， 看到了很多自动配置的文件；这就是自动配置根源所在！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215028677.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- WebMvcAutoConfiguration

我们在上面的自动配置类随便找一个打开看看，比如 ：WebMvcAutoConfiguration

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215041259.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

可以看到这些一个个的都是JavaConfig配置类，而且都注入了一些Bean，可以找一些自己认识的类，看着熟悉一下！

所以，自动配置真正实现是从classpath中搜寻所有的META-INF/spring.factories配置文件 ，并将其中对应的 org.springframework.boot.autoconfigure. 包下的配置项，通过反射实例化为对应标注了 @Configuration的JavaConfig形式的IOC容器配置类 ， 然后将这些都汇总成为一个实例并加载到IOC容器中。

小结：SpringBoot所有自动配置都是在启动的时候扫描并加载：spring.factories配置文件 ，所有的自动配置类都在这里面，但是不一定生效，要判断条件是否成立，而所谓的条件就是只要导入了对应的strater，就有对应的启动器了，有了启动器，条件成立，自动配置就生效，然后就配置成功！

#### 4.2.2结论：

1. SpringBoot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值
2. 将这些值作为自动配置类导入容器 ， 自动配置类就生效 ，以前我们需要自己配置的东西，现在SpringBoot 帮我们进行自动配置工作；
3. 整个J2EE的整体解决方案和自动配置都在spring-boot-autoconfigure的jar包中，他会把所有需要导入的组件，以类名（全限定名）的方式返回，这些类名对应的组件就会被添加到容器里；
4. 容器中有很多的xxxAutoConfiguration的文件（@Bean）, 就是这些类给容器中导入这个场景需要的所有组件 ， 并自动配置，@Configuration ；
5. 有了自动配置类 ， 免去了我们手动编写配置注入功能组件等的工作；

**现在大家应该大概的了解了下，SpringBoot的运行原理，后面我们还会深化一次！**

### 4.3 SpringApplication（看类与方法）

**不简单的方法**

我最初以为就是运行了一个main方法，没想到却开启了一个服务；

```java
@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }
}
```

- SpringApplication.run分析
- 分析该方法主要分两部分，一部分是SpringApplication的实例化，二是run方法的执行；

#### 4.3.1 SpringApplication类

- 这个类主要做了以下四件事情：
  - 推断应用的类型是普通的项目还是Web项目
  - 查找并加载所有可用初始化器 ， 设置到initializers属性中
  - 找出所有的应用程序监听器，设置到listeners属性中
  - 推断并设置main方法的定义类，找到运行的主类

查看构造器：

```java
public SpringApplication(ResourceLoader resourceLoader, Class... primarySources) {

    // ......

    this.webApplicationType = WebApplicationType.deduceFromClasspath();

    this.setInitializers(this.getSpringFactoriesInstances());

    this.setListeners(this.getSpringFactoriesInstances(ApplicationListener.class));

    this.mainApplicationClass = this.deduceMainApplicationClass();

}
```

#### 4.3.2 run方法流程分析

跟着源码和这幅图就可以一探究竟了！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215135198.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

## 五、yaml语法学习

### 5.1 SpringBoot的全局配置文件

SpringBoot使用一个全局的配置文件 ， 配置文件名称是固定的

- 可以是 ：application.properties
- - 语法结构 ：key=value
- 或者是：application.yaml （推荐使用）
- - 语法结构 ：key：空格 value （**空格必须存在**）

**配置文件的作用 ：**修改SpringBoot自动配置的默认值，因为SpringBoot在底层都给我们自动配置好了；

比如我们可以在配置文件（application.properties）中修改Tomcat 默认启动的端口号！测试一下！

```xml
server.port=8081
```

或者 application.yaml :

```yaml
server:
  port: 8082
```

### 5.2 yaml概述

YAML是 “YAML Ain’t a Markup Language” （YAML不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是：“Yet Another Markup Language”（仍是一种标记语言）

**这种语言以数据作为中心，而不是以标记语言为重点！**

以前的配置文件，大多数都是使用xml来配置；比如一个简单的端口配置，我们来对比下yaml和xml

传统xml配置：

```xml
<server>    
    <port>8081<port>
</server>
```

yaml配置：

```yaml
server:  
 prot: 8080
```

### 5.3yaml基础语法

- 说明：语法要求严格！
  - 空格不能省略
  - 以缩进来控制层级关系，只要是左边对齐的一列数据都是同一个层级的。
  - 属性和值的大小写都是十分敏感的。
- **字面量：普通的值 [ 数字，布尔值，字符串 ]**
  - 字面量直接写在后面就可以 ， 字符串默认不用加上双引号或者单引号；

```yaml
#yaml格式（注意空格）
k: v
```

- 注意：
  - “ ” 双引号，不会转义字符串里面的特殊字符 ， 特殊字符会作为本身想表示的意思；比如 ：name: “zssea \n shen” 输出 ：zssea 换行 shen
  - ’ ’ 单引号，会转义特殊字符 ， 特殊字符最终会变成和普通字符一样输出比如 ：name: ‘zssea \n shen’ 输出 ：zssea \n shen
- **对象、Map（键值对）**

```yaml
#对象、Map格式
k:
 v1:    
 v2:
```

- 在下一行来写对象的属性和值得关系，注意缩进；比如：

```yaml
student:
 name: qinjiang    
 age: 3
```

- 行内写法

```yaml
student: {name: qinjiang,age: 3}
```

- 数组（ List、set ）
  - 用 - 值表示数组中的一个元素,比如：

```yaml
pets: 
 - cat 
 - dog 
 - pig
```

- 行内写法

```yaml
pets: [cat,dog,pig]
```

总：

```yaml
#yaml文件可以存储的东西，重点对于空格的要求很严格
# 普通的键值对
name: mengyuanshishabi

# 对象
student:
  name: mengyuan
  age: 3
#行内表示
user: {name: mengyuan,age: 3}

# 数组
pets:
  - cat
  - pig
  - dog
# 行内表示
pet: [cat,dog,pig]

# 而properties只能存键值对 key=value
# yaml格式文件的另一个强大之处，在于可以注入到我们的配置类中，下面来说：
```

### 5.4注入配置文件

- yaml文件更强大的地方在于，他可以给我们的实体类直接注入匹配值！（给实体类赋值）

#### 5.4.1yaml注入配置文件

1、在springboot项目中的resources目录下新建一个文件 application.yaml

2、编写一个实体类 Dog；

```java
package com.zssea.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component  //添加到spring 的组件里，注解生成<bean> ,在之前的开发中，也用注解来注入属性值
public class Dog {
    private String name;
    private Integer age;
    //有参无参构造、get、set方法、toString()方法
}
```

3、思考，我们原来是如何给bean注入属性值的！@Value，给狗狗类测试一下：

```java
@Component //注册bean
public class Dog {    
    @Value("旺财")
    private String name;
    @Value("3")
    private Integer age;
}
```

4、在SpringBoot的测试类下注入狗狗输出一下；

```java
package com.zssea;

import com.zssea.pojo.Dog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot02ConfigApplicationTests {

    @Autowired
    private Dog dog;

    @Test
    void contextLoads() {
        System.out.println(dog);
    }

}
```

结果成功输出，@Value注入成功，这是我们原来的办法对吧。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215157361.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

5、我们在编写一个复杂一点的实体类：Person 类

```java
package com.zssea.pojo;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
 //有参无参构造、get、set方法、toString()方法
}
```

6、我们来使用yaml配置的方式进行注入，大家写的时候注意区别和优势，我们编写一个yaml配置！

```yaml
person:
  name: qinjiang
  age: 3
  happy: false
  birth: 2000/01/01
  maps: {k1: v1,k2: v2}
  lists:
    - code
    - girl
    - music
  dog:
    name: 旺财
    age: 3
```

7、我们刚才已经把person这个对象的所有值都写好了，我们现在来注入到我们的类中！

```java
/*
@ConfigurationProperties作用：
将配置文件中配置的每一个属性的值，映射到这个组件中；
告诉SpringBoot将本类中的所有属性和配置文件中相关的配置进行绑定
参数 prefix = “person” : 将配置文件中的person下面的所有属性一一对应
*/
@Component //注册bean
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happy;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
}
```

8、IDEA 提示，springboot配置注解处理器没有找到，让我们看文档，我们可以查看文档，找到一个依赖！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215213169.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215222651.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

```xml
<!-- 导入配置文件处理器，配置文件进行绑定就会有提示，需要重启 -->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-configuration-processor</artifactId>
	<optional>true</optional>
</dependency>
```

9、确认以上配置都OK之后，我们去测试类中测试一下：

```java
package com.zssea;

import com.zssea.pojo.Dog;
import com.zssea.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot02ConfigApplicationTests {

    @Autowired
    private Person person;

    @Test
    void contextLoads() {
        System.out.println(person);
    }
}
```

结果：所有值全部注入成功！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215234538.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**yaml配置注入到实体类完全OK！**

课堂测试：

1、将配置文件的key 值 和 属性的值设置为不一样，则结果输出为null，注入失败

2、在配置一个person2，然后将 @ConfigurationProperties(prefix = “person2”) 指向我们的person2；

#### 5.4.2加载指定的配置文件

**@PropertySource ：** 加载指定的配置文件；

**@configurationProperties**：默认从全局配置文件中获取值；

1、我们去在resources目录下新建一个**person.properties**文件

```properties
name=mengyuan
```

2、然后在我们的代码中指定加载person.properties文件properties

```java
@PropertySource(value = "classpath:person.properties")
@Component //注册bean
public class Person {

    @Value("${name}")
    private String name;

    ......  
}
```

3、再次输出测试一下：指定配置文件绑定成功！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215258763.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 5.4.3配置文件占位符

配置文件还可以编写占位符生成随机数

```yaml
person:
    name: qinjiang${random.uuid} # 随机uuid
    age: ${random.int}  # 随机int
    happy: false
    birth: 2000/01/01
    maps: {k1: v1,k2: v2}
    lists:
      - code
      - girl
      - music
    dog:
      name: ${person.hello:other}_旺财
      age: 1
```

#### 5.4.4回顾properties配置

我们上面采用的yaml方法都是最简单的方式，开发中最常用的；也是springboot所推荐的！那我们来唠唠其他的实现方式，道理都是相同的；写还是那样写；配置文件除了yaml还有我们之前常用的properties ， 我们没有讲，我们来唠唠！

【注意】properties配置文件在写中文的时候，会有乱码 ， 我们需要去IDEA中设置编码格式为UTF-8；

settings–>FileEncodings 中配置；

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215311137.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**测试步骤：**

1、新建一个实体类User

```java
@Component //注册bean
public class User {
    private String name;
    private int age;
    private String sex;
}
```

2、编辑配置文件 user.properties

```xml
user.name=zssea
user.age=18
user.sex=男
```

3、我们在User类上使用@Value来进行注入！

```java
@Component //注册bean
@PropertySource(value = "classpath:user.properties")
public class User {
    //直接使用@value
    @Value("${user.name}") //从配置文件中取值
    private String name;
    @Value("#{9*2}")  // #{SPEL} Spring表达式
    private int age;
    @Value("男")  // 字面量
    private String sex;
}
```

4、Springboot测试

```java
@SpringBootTest
class DemoApplicationTests {

    @Autowired
    User user;

    @Test
    public void contextLoads() {
        System.out.println(user);
    }

}
/*
结果：
User(name=zssea,age=18,sex=男)
*/
```

#### 5.4.5对比小结

@Value这个使用起来并不友好！我们需要为每个属性单独注解赋值，比较麻烦；我们来看个功能对比图

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215335398.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

1、@ConfigurationProperties只需要写一次即可 ， @Value则需要每个字段都添加

2、松散绑定：这个什么意思呢? 比如我的yaml中写的last-name，这个和lastName是一样的， - 后面跟着的字母 默认是大写的。这就是松散绑定。可以测试一下 (yaml文件中写last-name，实体类中写lastName，是可以对 应起来的)

3、JSR303数据校验 ， 这个就是我们可以在字段是增加一层过滤器验证 ， 可以保证数据的合法性

4、复杂类型封装，yml中可以封装对象 ， 使用value就不支持

**结论：**

- 配置yaml和配置properties都可以获取到值 ， 强烈推荐 yaml；
- 如果我们在某个业务中，只需要获取配置文件中的某个值，可以使用一下 @value；
- 如果说，我们专门编写了一个JavaBean来和配置文件进行一一映射，就直接@configurationProperties，不要犹豫！

### 5.5JSR303数据校验

#### 5.5.1先看看如何使用

- Springboot中可以用@validated来校验数据，如果数据异常则会统一抛出异常，方便异常中心统一处理。我们这里来写个注解让我们的name只能支持Email格式；

```java
@Component //注册bean
@ConfigurationProperties(prefix = "person")
@Validated  //数据校验
public class Person {

    @Email(message="邮箱格式错误") //name必须是邮箱格式
    private String name;
}
```

运行结果 ：default message [不是一个合法的电子邮件地址]; 就会报错

**使用数据校验，可以保证数据的正确性；**

#### 5.5.2常见参数

```java
@NotNull(message="名字不能为空")
private String userName;
@Max(value=120,message="年龄最大不能查过120")
private int age;
@Email(message="邮箱格式错误")
private String email;

空检查
@Null       验证对象是否为null
@NotNull    验证对象是否不为null, 无法查检长度为0的字符串
@NotBlank   检查约束字符串是不是Null还有被Trim的长度是否大于0,只对字符串,且会去掉前后空格.
@NotEmpty   检查约束元素是否为NULL或者是EMPTY.
    
Booelan检查
@AssertTrue     验证 Boolean 对象是否为 true  
@AssertFalse    验证 Boolean 对象是否为 false  
    
长度检查
@Size(min=, max=) 验证对象（Array,Collection,Map,String）长度是否在给定的范围之内  
@Length(min=, max=) string is between min and max included.

日期检查
@Past       验证 Date 和 Calendar 对象是否在当前时间之前  
@Future     验证 Date 和 Calendar 对象是否在当前时间之后  
@Pattern    验证 String 对象是否符合正则表达式的规则

.......等等
除此以外，我们还可以自定义一些数据校验规则
```

## 六、多环境切换

- profile是Spring对不同环境提供不同配置功能的支持，可以通过激活不同的环境版本，实现快速切换环境；

#### 6.1多配置文件

我们在主配置文件编写的时候，文件名可以是 application-{profile}.properties/yaml , 用来指定多个环境版本；

**例如：**

application-test.properties 代表测试环境配置

application-dev.properties 代表开发环境配置

但是Springboot并不会直接启动这些配置文件，它**默认使用application.properties主配置文件**；

我们需要通过一个配置来选择需要激活的环境：

```properties
#比如在配置文件中指定使用dev环境，我们可以通过设置不同的端口号进行测试；
#我们启动SpringBoot，就可以看到已经切换到dev下的配置了；
spring.profiles.active=dev
```

#### 6.2yaml的多文档块

和properties配置文件中一样，但是使用yaml去实现不需要创建多个配置文件，更加方便了 !

```yaml
server:
  port: 8081
#选择要激活那个环境块
spring:
  profiles:
    active: prod

---
server:
  port: 8083
spring:
  profiles: dev #配置环境的名称

---

server:
  port: 8084
spring:
  profiles: prod  #配置环境的名称
```

**注意：如果yml和properties同时都配置了端口，并且没有激活其他环境 ， 默认会使用properties配置文件的！**

#### 6.3配置文件加载位置

**外部加载配置文件的方式十分多，我们选择最常用的即可，在开发的资源文件中进行配置！**

官方外部配置文件说明参考文档

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215417625.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

springboot 启动会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件：

```xml
优先级1：项目路径下的config文件夹配置文件
优先级2：项目路径下配置文件
优先级3：资源路径下的config文件夹配置文件
优先级4：资源路径下配置文件
```

优先级由高到底，高优先级的配置会覆盖低优先级的配置；

**SpringBoot会从这四个位置全部加载主配置文件；互补配置；**

我们在最低级的配置文件中设置一个项目访问路径的配置来测试互补问题；

```xml
#配置项目的访问路径
server.servlet.context-path=/zssea
```

#### 6.4拓展，运维小技巧

指定位置加载配置文件

我们还可以通过spring.config.location来改变默认的配置文件位置

项目打包好以后，我们可以使用命令行参数的形式，启动项目的时候来指定配置文件的新位置；这种情况，一般是后期运维做的多，相同配置，外部指定的配置文件优先级最高

```
java -jar spring-boot-config.jar --spring.config.location=F:/application.properti
```

## 七、自动配置原理【重点】

- 配置文件到底能写什么？怎么写？
- SpringBoot官方文档中有大量的配置，我们无法全部记住

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102521543190.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 7.1分析自动配置原理

从spring.factories中选择一个类进入，我们以**HttpEncodingAutoConfiguration（Http编码自动配置）** 为例解释自动配置原理；

```java
//表示这是一个配置类，和以前编写的配置文件一样，也可以给容器中添加组件；
@Configuration 

//启动指定类的ConfigurationProperties功能； （HttpProperties.class 这个就是自动配置类）
  //进入这个HttpProperties查看，将配置文件中对应的值和HttpProperties绑定起来；
  //并把HttpProperties加入到ioc容器中
//自动装配属性，装配好之后，HttpProperties.class这个里面的属性（要加上这个类注解绑定的前缀）就是我们在application.yaml配置文件中配置的键（key）
@EnableConfigurationProperties({HttpProperties.class}) 

//Spring底层@Conditional注解
  //根据不同的条件判断来判断当前配置或类是否生效，如果满足指定的条件，整个配置类里面的配置就会生效；
  //这里的意思就是判断当前应用是否是web应用，如果是，当前配置类生效
@ConditionalOnWebApplication(
    type = Type.SERVLET
)

//判断当前项目有没有这个类CharacterEncodingFilter；SpringMVC中进行乱码解决的过滤器；
@ConditionalOnClass({CharacterEncodingFilter.class})

//判断配置文件中是否存在某个配置：spring.http.encoding.enabled；
  //如果不存在，判断也是成立的
  //即使我们配置文件中不配置pring.http.encoding.enabled=true，也是默认生效的；
@ConditionalOnProperty(
    prefix = "spring.http.encoding",
    value = {"enabled"},
    matchIfMissing = true
)

public class HttpEncodingAutoConfiguration {
    //他已经和SpringBoot的配置文件映射了
    private final Encoding properties;
    //只有一个有参构造器的情况下，参数的值就会从容器中拿
    public HttpEncodingAutoConfiguration(HttpProperties properties) {
        this.properties = properties.getEncoding();
    }
    
    //给容器中添加一个组件，这个组件的某些值需要从properties中获取
    @Bean
    @ConditionalOnMissingBean //判断容器没有这个组件？
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpProperties.Encoding.Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(org.springframework.boot.autoconfigure.http.HttpProperties.Encoding.Type.RESPONSE));
        return filter;
    }
    //..........
}
```

**一句话总结 ：根据当前不同的条件判断，决定这个配置类是否生效！**

- 一但这个配置类生效；这个配置类就会给容器中添加各种组件；
- 这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件（application.yaml）绑定的；
- 所有在配置文件中能配置的属性都是在xxxxProperties类中封装着；
- 配置文件能配置什么就可以参照某个功能对应的这个属性类

```java
//从配置文件中获取指定的值和bean的属性进行绑定
@ConfigurationProperties(prefix = "spring.http") 
public class HttpProperties {
    // 众多属性.....
}
```

我们去配置文件里面试试前缀，看提示！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215452505.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**这就是自动装配的原理！**

#### 7.2精髓

1、SpringBoot启动会加载大量的自动配置类（xxxxAutoConfigurartion），每一个自动配置类中都有对应的 xxxxProperties，这些xxxxProperties中有着相关的属性与默认值，这些属性又是和配置文件（application.yaml）绑定的，所以我们可以通过配置文件来修改SpringBoot自动配置的默认值；

2、我们看我们需要的功能有没有在SpringBoot默认写好的自动配置类当中；

3、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件存在在其中，我们就不需要再手动配置了）

4、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们只需要在配置文件（application.yaml）中指定这些属性的值即可；

**xxxxAutoConfigurartion：** 给容器中添加组件

**xxxxProperties:封装配置文件中相关属性；** 自动配置类

#### 7.3了解：@Conditional

了解完自动装配的原理后，我们来关注一个细节问题，**自动配置类必须在一定的条件下才能生效；**

**@Conditional派生注解（Spring注解版原生的@Conditional作用）**

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置配里面的所有内容才生效；

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215513255.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**那么多的自动配置类，必须在一定的条件下才能生效；也就是说，我们加载了这么多的配置类，但不是所有的都生效了。**

我们怎么知道哪些自动配置类生效？

**我们可以通过启用 debug=true属性；来让控制台打印自动配置报告，这样我们就可以很方便的知道哪些自动配置类生效；**

```xml
#开启springboot的调试类
debug=true
```

输出的日志中大致分为一下几类：

- **Positive matches:（自动配置类启用的：正匹配）** 已经启用了，并且生效的
- **Negative matches:（没有启动，没有匹配成功的自动配置类：负匹配）**
- **Unconditional classes: （没有条件的类）**

## 八、SpringBoot Web 开发前准备

- 我们要解决的问题：
  - 导入静态资源，怎么样处理静态资源
  - 首页index.jsp/index.html
  - Jsp,模板引擎Thymeleaf
  - 装配扩展SpringMVC
  - 增删改查
  - 拦截器
  - 国际化

### 8.1处理静态资源

- WebMvcAutoConfiguration.java 源码中关于静态资源的说明：

  ```java
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      //1.如果我们在配置文件中自己定义了路径，下面默认的就会失效
      if (!this.resourceProperties.isAddMappings()) {
          logger.debug("Default resource handling disabled");
          return;
      }
      
      //2.导入一些静态jar包，如jquery，这些jar包的目录结构要求，如果我们访问http://localhost:8080/webjars/... 就可以访问到/webjars下面的资源
      Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
      CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
      if (!registry.hasMappingForPattern("/webjars/**")) {
          customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
                                               .addResourceLocations("classpath:/META-INF/resources/webjars/")
                                               .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
      }
      
      //3.源码中：staticPathPattern = "/**"  ，this.resourceProperties.getStaticLocations() = { "classpath:/META-INF/resources/",
  			"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
  //所以只要我们访问 http://localhost:8080/....  它就会匹配到上述的这些目录，去这些目录下寻找资源
      String staticPathPattern = this.mvcProperties.getStaticPathPattern();
      if (!registry.hasMappingForPattern(staticPathPattern)) {
          customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
                                               .addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
                                               .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
      }
  }
  ```
  
- 总结：

  - 如果我们在配置文件（application.properties）中自己定义了路径，SpringBoot默认的路径就会失效，一般不建议这样做，因为它会直接return，代码不会往下走

    ```properties
    spring.mvc.static-path-pattern=classpath:/zssea/
    ```
  
- http://localhost:8080/webjars/… 这个路径可以访问到/webjars下面的资源，一般会用于导入的依赖jar包的访问（这些依赖要去 webjars的官方网站：https://www.webjars.org 去找），要求这些jar包的目录结构要求如下：
  
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102521552853.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)
  
  ```xml
    <dependency>
        <groupId>org.webjars</groupId>
        <artifactId>jquery</artifactId>
        <version>3.5.1</version>
    </dependency>
  ```
  
  - 那我们项目中要是使用自己的静态资源该怎么导入呢？ 我们去找staticPathPattern发现第二种映射规则 ：/** , 访问当前的项目任意资源，它会去找 resourceProperties 这个类 ,这个类在里面定义了这些目录（{ “classpath:/META-INF/resources/”,“classpath:/resources/”, “classpath:/static/”, “classpath:/public/” }） 所以平常我们访问 http://localhost:8080/… ，它就会匹配到上述的目录，去这些目录下寻找资源， 优先级：resources > static > public ,我们通常就把静态资源放在这个三个目录下，至于怎样放，看个人习惯，很少使用/webjars

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215550148.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 8.2首页和图标如何定制

- 首页一般为index.html，它也可以算是一种静态资源，所以当我们把它放在resources目录下的 resources ，static 或 public任意一个文件夹下，直接访问项目(http://localhost:8080/)，就会跳转到这个index.html

- 这个操作是如何实施的呢，看一下源码，还是WebMvcAutoConfiguration.java 中的：

  ```java
  @Bean
  public WelcomePageHandlerMapping welcomePageHandlerMapping(ApplicationContext 	 applicationContext,FormattingConversionService mvcConversionService,   ResourceUrlProvider mvcResourceUrlProvider) {
      WelcomePageHandlerMapping welcomePageHandlerMapping = new WelcomePageHandlerMapping(
          new TemplateAvailabilityProviders(applicationContext), applicationContext, getWelcomePage(),
          this.mvcProperties.getStaticPathPattern());
      welcomePageHandlerMapping.setInterceptors(getInterceptors(mvcConversionService, mvcResourceUrlProvider));
      welcomePageHandlerMapping.setCorsConfigurations(getCorsConfigurations());
      return welcomePageHandlerMapping;
  }
  
  private Optional<Resource> getWelcomePage() {
      String[] locations = getResourceLocations(this.resourceProperties.getStaticLocations());
      return Arrays.stream(locations).map(this::getIndexHtml).filter(this::isReadable).findFirst();
  }
  
  private Resource getIndexHtml(String location) {
      return this.resourceLoader.getResource(location + "index.html");
  }
  ```
  
- 我们看到resources目录下还有一个名为templates的文件夹，这里也可以放页面文件，只不过这个里面的文件只能通过controller来跳转访问，这个功能实现也需要摸板引擎（依赖）的支持，后续会说

- 图标定制：与其他静态资源一样，Spring Boot在配置的静态内容位置中（还是那三个目录）查找 favicon.ico。如果存在这样的文件，它将自动用作应用程序的favicon。

  - 关闭SpringBoot默认图标

    ```properties
    #关闭默认图标
    spring.mvc.favicon.enabled=false
    ```
  
- 自己放一个图标在静态资源目录下，我放在 public 目录下
  
- 清除浏览器缓存！刷新网页，发现图标已经变成自己的了！

### 8.3Thymeleaf模板引擎

#### 8.3.1模板引擎

- 前端交给我们的页面，是html页面。如果是我们以前开发，我们需要把他们转成jsp页面，jsp好处就是当我们查出一些数据转发到JSP页面以后，我们可以用jsp轻松实现数据的显示，及交互等。
- jsp支持非常强大的功能，包括能写Java代码，但是呢，我们现在的这种情况，SpringBoot这个项目首先是以jar的方式，不是war，第二，我们用的还是嵌入式的Tomcat，所以呢，**他现在默认是不支持jsp的**。
- 那不支持jsp，如果我们直接用纯静态页面的方式，那给我们开发会带来非常大的麻烦，那怎么办呢？
- **SpringBoot推荐你可以来使用模板引擎：**
  - 模板引擎，我们其实大家听到很多，其实jsp就是一个模板引擎，还有用的比较多的freemarker，包括SpringBoot给我们推荐的Thymeleaf，模板引擎有非常多，但再多的模板引擎，他们的思想都是一样的，什么样一个思想呢我们来看一下这张图：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215604539.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

模板引擎的作用就是我们来写一个页面模板，比如有些值呢，是动态的，我们写一些表达式。而这些值，从哪来呢，就是我们在后台封装一些数据。然后把这个模板和这个数据交给我们模板引擎，模板引擎按照我们这个数据帮你把这表达式解析、填充到我们指定的位置，然后把这个数据最终生成一个我们想要的内容给我们写出去，这就是我们这个模板引擎，不管是jsp还是其他模板引擎，都是这个思想。只不过呢，就是说不同模板引擎之间，他们可能这个语法有点不一样。其他的我就不介绍了，我主要来介绍一下SpringBoot给我们推荐的Thymeleaf模板引擎，这模板引擎呢，是一个高级语言的模板引擎，他的这个语法更简单。而且呢，功能更强大。

我们呢，就来看一下这个模板引擎，那既然要看这个模板引擎。首先，我们来看SpringBoot里边怎么用。

#### 8.3.2引入Thymeleaf

- 怎么引入呢，对于springboot来说，什么事情不都是一个start的事情嘛，我们去在项目中引入一下。给大家三个网址：
  - Thymeleaf 官网：https://www.thymeleaf.org/
  - Thymeleaf 在Github 的主页：https://github.com/thymeleaf/thymeleaf
  - Spring官方文档：找到我们对应的版本https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#using-boot-starter
- 找到对应的pom依赖：可以适当点进源码看下本来的包！

```xml
		<!--thymeleaf-->
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf-spring5</artifactId>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-java8time</artifactId>
        </dependency>
```

- Maven会自动下载jar包，我们可以去看下下载的东西；

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215618937.png#pic_center)

#### 8.3.3Thymeleaf分析

- 前面呢，我们已经引入了Thymeleaf，那这个要怎么使用呢？
- 我们首先得按照SpringBoot的自动配置原理看一下我们这个Thymeleaf的自动配置规则，在按照那个规则，我们进行使用。我们去找一下Thymeleaf的自动配置类：ThymeleafProperties

```java
@ConfigurationProperties(
    prefix = "spring.thymeleaf"
)
public class ThymeleafProperties {
    private static final Charset DEFAULT_ENCODING;
    public static final String DEFAULT_PREFIX = "classpath:/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
    private boolean checkTemplate = true;
    private boolean checkTemplateLocation = true;
    private String prefix = "classpath:/templates/";
    private String suffix = ".html";
    private String mode = "HTML";
    private Charset encoding;
}
```

- 我们可以在其中看到默认的前缀和后缀！
- 我们只需要把我们的html页面放在类路径下的templates下，thymeleaf就可以帮我们自动渲染了。
- 使用thymeleaf什么都不需要配置，只需要将他放在指定的文件夹下即可！

#### 8.3.4测试

1、编写一个IndexController

```java
package com.zssea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
```

2、编写一个测试页面 index.html 放在 templates 目录下

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
    <h1>首页</h1>
</body>
</html>
```

3、启动项目请求测试

#### 8.3.5Thymeleaf 语法学习

- 要学习语法，还是参考官网文档最为准确，我们找到对应的版本看一下；
- Thymeleaf 官网：https://www.thymeleaf.org/ ， 简单看一下官网！我们去下载Thymeleaf的官方文档！
- **我们做个最简单的练习 ：我们需要查出一些数据，在页面中展示**

1、修改测试请求，增加数据传输；

```java
@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("msg","hello,SpeingBoot!");
        return "test";
    }
}
```

2、我们要使用thymeleaf，需要在html文件中导入命名空间的约束，方便提示。

我们可以去官方文档的#3中看一下命名空间拿来过来：

```html
<html lang="en" xml:th="http://www.thymeleaf.org" xmlns:th="http://www.w3.org/1999/xhtml">
<!--命名空间的约束: 前一个是官方给的，后一个是改错自己加入的-->
```

3、我们去编写下前端页面

```html
<!DOCTYPE html>
<html lang="en" xml:th="http://www.thymeleaf.org" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>test</title>
</head>
<body>

    <!--所有的html元素都可以被thymeleaf替换接管， th:元素名称 -->
    <div th:text="${msg}"></div>
</body>
</html>
```

4、启动测试！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215638847.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**OK，入门搞定，我们来认真研习一下Thymeleaf的使用语法！**

**1、我们可以使用任意的 th:attr 来替换Html中原生属性的值！**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215751361.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**2、我们能写哪些表达式呢？**

```html
Simple expressions:（表达式语法）
Variable Expressions: ${...}：获取普通变量值；OGNL；
    1）、获取对象的属性、调用方法
    2）、使用内置的基本对象：#18
         #ctx : the context object.
         #vars: the context variables.
         #locale : the context locale.
         #request : (only in Web Contexts) the HttpServletRequest object.
         #response : (only in Web Contexts) the HttpServletResponse object.
         #session : (only in Web Contexts) the HttpSession object.
         #servletContext : (only in Web Contexts) the ServletContext object.

    3）、内置的一些工具对象：
　　　　　　#execInfo : information about the template being processed.
　　　　　　#uris : methods for escaping parts of URLs/URIs
　　　　　　#conversions : methods for executing the configured conversion service (if any).
　　　　　　#dates : methods for java.util.Date objects: formatting, component extraction, etc.
　　　　　　#calendars : analogous to #dates , but for java.util.Calendar objects.
　　　　　　#numbers : methods for formatting numeric objects.
　　　　　　#strings : methods for String objects: contains, startsWith, prepending/appending, etc.
　　　　　　#objects : methods for objects in general.
　　　　　　#bools : methods for boolean evaluation.
　　　　　　#arrays : methods for arrays.
　　　　　　#lists : methods for lists.
　　　　　　#sets : methods for sets.
　　　　　　#maps : methods for maps.
　　　　　　#aggregates : methods for creating aggregates on arrays or collections.
==================================================================================

  Selection Variable Expressions: *{...}：选择表达式：和${}在功能上是一样；
  Message Expressions: #{...}：获取国际化内容
  Link URL Expressions: @{...}：定义URL；
  Fragment Expressions: ~{...}：片段引用表达式

Literals（字面量）
      Text literals: 'one text' , 'Another one!' ,…
      Number literals: 0 , 34 , 3.0 , 12.3 ,…
      Boolean literals: true , false
      Null literal: null
      Literal tokens: one , sometext , main ,…
      
Text operations:（文本操作）
    String concatenation: +
    Literal substitutions: |The name is ${name}|
    
Arithmetic operations:（数学运算）
    Binary operators: + , - , * , / , %
    Minus sign (unary operator): -
    
Boolean operations:（布尔运算）
    Binary operators: and , or
    Boolean negation (unary operator): ! , not
    
Comparisons and equality:（比较运算）
    Comparators: > , < , >= , <= ( gt , lt , ge , le )
    Equality operators: == , != ( eq , ne )
    
Conditional operators:条件运算（三元运算符）
    If-then: (if) ? (then)
    If-then-else: (if) ? (then) : (else)
    Default: (value) ?: (defaultvalue)
    
Special tokens:
    No-Operation: _
```

**练习测试：**

1、 我们编写一个Controller，放一些数据

```java
@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("msg1","<h1>hello,SpeingBoot!</h1>");
        model.addAttribute("msg2","<h1>hello,SpeingBoot!</h1>");
        model.addAttribute("users", Arrays.asList("zssea","hello"));
        return "test";
    }
}
```

2、测试页面取出数据

```html
<!DOCTYPE html>
<html lang="en" xml:th="http://www.thymeleaf.org" xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>test</title>
</head>
<body>

    <!--所有的html元素都可以被thymeleaf替换接管， th:元素名称 -->

    <div th:text="${msg1}"></div> <!--转义特殊字符-->
    <div th:utext="${msg2}"></div><!--不转义特殊字符-->

    <hr/>
    <h3 th:each="user:${users}" th:text="${user}"></h3>
    <li th:each="user:${users}">[[${user}]]</li>
</body>
</html>
```

3、启动项目测试！

**我们看完语法，很多样式，我们即使现在学习了，也会忘记，所以我们在学习过程中，需要使用什么，根据官方文档来查询，才是最重要的，要熟练使用官方文档！**

### 8.4SpringMVC自动配置原理

#### 8.4.1官网阅读

- 在进行项目编写前，我们还需要知道一个东西，就是SpringBoot对我们的SpringMVC还做了哪些配置，包括如何扩展，如何定制。
- 只有把这些都搞清楚了，我们在之后使用才会更加得心应手。途径一：源码分析，途径二：官方文档！
- 地址 ：https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-auto-configuration

```text
//Spring MVC自动配置
Spring MVC Auto-configuration

// Spring Boot为Spring MVC提供了自动配置，它可以很好地与大多数应用程序一起工作。
Spring Boot provides auto-configuration for Spring MVC that works well with most applications.

// 自动配置在Spring默认设置的基础上添加了以下功能：
The auto-configuration adds the following features on top of Spring’s defaults:

// 包含视图解析器
Inclusion of ContentNegotiatingViewResolver and BeanNameViewResolver beans.

// 支持静态资源文件夹的路径，以及webjars
Support for serving static resources, including support for WebJars 

// 自动注册了Converter： 类型转换器
// 转换器，这就是我们网页提交数据到后台自动封装成为对象的东西，比如把"1"字符串自动转换为int类型
// Formatter：【格式化器，比如页面给我们了一个2019-8-10，它会给我们自动格式化为Date对象】
Automatic registration of Converter, GenericConverter, and Formatter beans.

// HttpMessageConverters
// SpringMVC用来转换Http请求和响应的的，比如我们要把一个User对象转换为JSON字符串，可以去看官网文档解释；
Support for HttpMessageConverters (covered later in this document).

// 定义错误代码生成规则的
Automatic registration of MessageCodesResolver (covered later in this document).

// 首页定制
Static index.html support.

// 图标定制
Custom Favicon support (covered later in this document).

// 初始化数据绑定器：帮我们把请求数据绑定到JavaBean中！
Automatic use of a ConfigurableWebBindingInitializer bean (covered later in this document).

/*
如果您希望保留Spring Boot MVC功能，并且希望添加其他MVC配置（拦截器、格式化程序、视图控制器和其他功能），则可以添加自己的@configuration类，放在一个类型为webmvcconfiguer的类上，但不添加@EnableWebMvc。如果希望提供RequestMappingHandlerMapping、RequestMappingHandlerAdapter或ExceptionHandlerExceptionResolver的自定义实例，则可以声明WebMVCregistrationAdapter实例来提供此类组件。
*/
If you want to keep Spring Boot MVC features and you want to add additional MVC configuration 
(interceptors, formatters, view controllers, and other features), you can add your own 
@Configuration class of type WebMvcConfigurer but without @EnableWebMvc. If you wish to provide 
custom instances of RequestMappingHandlerMapping, RequestMappingHandlerAdapter, or 
ExceptionHandlerExceptionResolver, you can declare a WebMvcRegistrationsAdapter instance to provide such components.

// 如果您想完全控制Spring MVC，可以添加自己的@Configuration，并用@EnableWebMvc进行注释。
If you want to take complete control of Spring MVC, you can add your own @Configuration annotated with @EnableWebMvc.
```

我们来仔细对照，看一下它怎么实现的，它告诉我们SpringBoot已经帮我们自动配置好了SpringMVC，然后自动配置了哪些东西呢？

#### 8.4.2ContentNegotiatingViewResolver 内容协商视图解析器

自动配置了ViewResolver，就是我们之前学习的SpringMVC的视图解析器；

即根据方法的返回值取得视图对象（View），然后由视图对象决定如何渲染（转发，重定向）。

我们去看看这里的源码：我们找到 WebMvcAutoConfiguration ， 然后搜索ContentNegotiatingViewResolver。找到如下方法！

```java
@Bean
@ConditionalOnBean(ViewResolver.class)
@ConditionalOnMissingBean(name = "viewResolver", value = ContentNegotiatingViewResolver.class)
public ContentNegotiatingViewResolver viewResolver(BeanFactory beanFactory) {
    ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
    resolver.setContentNegotiationManager(beanFactory.getBean(ContentNegotiationManager.class));
    // ContentNegotiatingViewResolver使用所有其他视图解析器来定位视图，因此它应该具有较高的优先级
    resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return resolver;
}
```

我们可以点进这类看看！找到对应的解析视图的代码；

```java
@Nullable // 注解说明：@Nullable 即参数可为null
public View resolveViewName(String viewName, Locale locale) throws Exception {
    RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
    Assert.state(attrs instanceof ServletRequestAttributes, "No current ServletRequestAttributes");
    List<MediaType> requestedMediaTypes = this.getMediaTypes(((ServletRequestAttributes)attrs).getRequest());
    if (requestedMediaTypes != null) {
        // 获取候选的视图对象
        List<View> candidateViews = this.getCandidateViews(viewName, locale, requestedMediaTypes);
        // 选择一个最适合的视图对象，然后把这个对象返回
        View bestView = this.getBestView(candidateViews, requestedMediaTypes, attrs);
        if (bestView != null) {
            return bestView;
        }
    }
    // .....
}
```

我们继续点进去看，他是怎么获得候选的视图的呢？

getCandidateViews中看到他是把所有的视图解析器拿来，进行while循环，挨个解析！

```java
Iterator var5 = this.viewResolvers.iterator();
```

所以得出结论：**ContentNegotiatingViewResolver 这个视图解析器就是用来组合所有的视图解析器的**

我们再去研究下他的组合逻辑，看到有个属性viewResolvers，看看它是在哪里进行赋值的！

```java
protected void initServletContext(ServletContext servletContext) {
    // 这里它是从beanFactory工具中获取容器中的所有视图解析器
    // ViewRescolver.class 把所有的视图解析器来组合的
    Collection<ViewResolver> matchingBeans = BeanFactoryUtils.beansOfTypeIncludingAncestors(this.obtainApplicationContext(), ViewResolver.class).values();
    ViewResolver viewResolver;
    if (this.viewResolvers == null) {
        this.viewResolvers = new ArrayList(matchingBeans.size());
    }
    // ...............
}
```

既然它是在容器中去找视图解析器，我们是否可以猜想，我们就可以去实现一个视图解析器了呢？

我们可以自己给容器中去添加一个视图解析器；这个类就会帮我们自动的将它组合进来；**我们去实现一下**

1、我们在我们的主程序中去写一个视图解析器来试试；

```java
package com.zssea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

@Configuration  //这个注解表明这是扩展的SpringMVC
public class MyMvcConfig implements WebMvcConfigurer {

    //ViewResolver 实现了视图解析器接口的类，我们就可以把它看作是视图解析器
    @Bean
    public ViewResolver MyViewResolver(){
        return MyViewResolver();
    }

    //自定义一个自己的视图解析器，MyViewResolver
    public static class MyViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}
```

2、怎么看我们自己写的视图解析器有没有起作用呢？

我们给 DispatcherServlet 中的 doDispatch方法 加个断点进行调试一下，因为所有的请求都会走到这个方法中

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102521581897.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

3、我们启动我们的项目，然后随便访问一个页面，看一下Debug信息；

找到this

![img](https://img-blog.csdnimg.cn/img_convert/0a431d2b9387aeea30f2ad959c7babb3.png)

找到视图解析器，我们看到我们自己定义的就在这里了；

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215833454.png#pic_center)

所以说，我们如果想要使用自己定制化的东西，我们只需要给容器中添加这个组件就好了！剩下的事情SpringBoot就会帮我们做了！

#### 8.4.3转换器和格式化器

- 在WebMvcAutoConfiguration.java中找到格式化转换器：

```java
@Bean
@Override
public FormattingConversionService mvcConversionService() {
    // 拿到配置文件中的格式化规则
    WebConversionService conversionService = 
        new WebConversionService(this.mvcProperties.getDateFormat());
    addFormatters(conversionService);
    return conversionService;
}
```

- 点进去：

```java
public String getDateFormat() {
    return this.dateFormat;
}

/**
* Date format to use. For instance, `dd/MM/yyyy`. 默认的
 */
private String dateFormat;
```

- 可以看到在我们的Properties文件中，我们可以进行自动配置它！
- 如果配置了自己的格式化方式，就会注册到Bean中生效，我们可以在配置文件中配置日期格式化的规则：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215846122.png#pic_center)

- 其余的就不一一举例了，大家可以下去多研究探讨即可！

#### 8.4.4修改SpringBoot的默认配置

##### 方式一：

- 这么多的自动配置，原理都是一样的，通过这个WebMVC的自动配置原理分析，我们要学会一种学习方式，通过源码探究，得出结论；这个结论一定是属于自己的，而且一通百通。
- SpringBoot的底层，大量用到了这些设计细节思想，所以，没事需要多阅读源码！得出结论；
- SpringBoot在自动配置很多组件的时候，先看容器中有没有用户自己配置的（如果用户自己配置@bean），如果有就用用户配置的，如果没有就用自动配置的；
- 如果有些组件可以存在多个，比如我们的视图解析器，就将用户配置的和自己默认的组合起来！

##### **扩展使用SpringMVC**

官方文档如下：

If you want to keep Spring Boot MVC features and you want to add additional MVC configuration (interceptors, formatters, view controllers, and other features), you can add your own @Configuration class of type WebMvcConfigurer but without @EnableWebMvc. If you wish to provide custom instances of RequestMappingHandlerMapping, RequestMappingHandlerAdapter, or ExceptionHandlerExceptionResolver, you can declare a WebMvcRegistrationsAdapter instance to provide such components.

我们要做的就是编写一个@Configuration注解类，并且类型要为WebMvcConfigurer，还不能标注@EnableWebMvc注解；我们去自己写一个；我们新建一个包叫config，写一个类MyMvcConfig；

```java
package com.zssea.config;

//应为类型要求为WebMvcConfigurer，所以我们实现其接口
//可以使用自定义类扩展MVC的功能
@Configuration  //这个注解表明这是扩展的SpringMVC
public class MyMvcConfig implements WebMvcConfigurer {

     @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/zssea").setViewName("index");
        //只需要访问 http://localhost:8080/zssea  就可以跳转到index.html
    }
}
```

- 我们去浏览器访问一下：

- **确实也跳转过来了！所以说，我们要扩展SpringMVC，官方就推荐我们这么去使用，既保SpringBoot留所有的自动配置，也能用我们扩展的配置！**

我们可以去分析一下原理：

1、WebMvcAutoConfiguration 是 SpringMVC的自动配置类，里面有一个类WebMvcAutoConfigurationAdapter

2、这个类上有一个注解，在做其他自动配置时会导入：@Import(EnableWebMvcConfiguration.class)

3、我们点进EnableWebMvcConfiguration这个类看一下，它继承了一个父类：DelegatingWebMvcConfiguration

这个父类中有这样一段代码：

```java
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
    private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();
    
  // 从容器中获取所有的webmvcConfigurer
    @Autowired(required = false)
    public void setConfigurers(List<WebMvcConfigurer> configurers) {
        if (!CollectionUtils.isEmpty(configurers)) {
            this.configurers.addWebMvcConfigurers(configurers);
        }
    }
}
```

4、我们可以在这个类中去寻找一个我们刚才设置的viewController当做参考，发现它调用了一个

```java
protected void addViewControllers(ViewControllerRegistry registry) {
    this.configurers.addViewControllers(registry);
}
```

5、我们点进去看一下

```java
public void addViewControllers(ViewControllerRegistry registry) {
    Iterator var2 = this.delegates.iterator();

    while(var2.hasNext()) {
        // 将所有的WebMvcConfigurer相关配置来一起调用！包括我们自己配置的和Spring给我们配置的
        WebMvcConfigurer delegate = (WebMvcConfigurer)var2.next();
        delegate.addViewControllers(registry);
    }

}
```

所以得出结论：所有的WebMvcConfiguration都会被作用，不止Spring自己的配置类，我们自己的配置类当然也会被调用；

#### 8.4.5全面接管SpringMVC

- 官方文档：

```java
If you want to take complete control of Spring MVCyou can add your own @Configuration annotated with @EnableWebMvc.
```

- 全面接管即：SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己去配置！
- 只需在我们的配置类中要加一个@EnableWebMvc。
- 我们看下如果我们全面接管了SpringMVC了，我们之前SpringBoot给我们配置的静态资源映射一定会无效，我们可以去测试一下；
- 不加注解之前，访问首页：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215914481.png#pic_center)

- 给配置类加上注解：@EnableWebMvc

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102521592439.png#pic_center)

- 我们发现所有的SpringMVC自动配置都失效了！回归到了最初的样子；
- **当然，我们开发中，不推荐使用全面接管SpringMVC**
- 思考问题？为什么加了一个注解，自动配置就失效了！我们看下源码：

1、这里发现它是导入了一个类：DelegatingWebMvcConfiguration （从容器中获取所有的webmvcconfig），我们可以继续进去看 (ctrl+左键点击这个注解@EnableWebMvc)

```java
@Import({DelegatingWebMvcConfiguration.class})
public @interface EnableWebMvc {
}
```

2、它继承了一个父类 WebMvcConfigurationSupport

```
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
  // ......
}
```

3、我们来回顾一下Webmvc自动配置类 (WebMvcAutoConfiguration 第五行代码)

```java
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class })
// 这个注解的意思就是：容器中没有这个组件的时候，这个自动配置类才生效
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,
    ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {
    
}
```

- 总结一句话：@EnableWebMvc将WebMvcConfigurationSupport组件导入进来了；而导入的WebMvcConfigurationSupport只是SpringMVC最基本的功能！，这个WebMvcConfigurationSupport进来之后，就使WebMvcAutoConfiguration这个类不满足条件，导致自动配置类失效！

**在SpringBoot中会有非常多的扩展配置，只要看见了这个，我们就应该多留心注意~**

## 九、一个简单的员工管理系统

- 这个系统不是完整的，它只是为了看一下，使用SpringBoot开发，我们要进行哪些操作！

### 9.1准备工作

- 导入lombok 依赖

- Lombok项目是一个Java库，它会自动插入编辑器和构建工具中，Lombok提供了一组有用的注释，用来消除Java类中的大量样板代码。仅五个字符(@Data)就可以替换数百行代码从而产生干净，简洁且易于维护的Java类。用在实体类中，用注解生成构造方法，get/set，等方法

- @Data： 注解在类，生成setter/getter、equals、canEqual、hashCode、toString方法，如为final属性，则不会为该属性生成setter方法。

- 导入lombok需要安装插件：可以参考这个文章：https://www.freesion.com/article/5093806462/

- ```xml
   			<!--lombok-->
          <dependency>
              <groupId>org.projectlombok</groupId>
              <artifactId>lombok</artifactId>
              <optional>true</optional>
              <version>1.18.2</version>
          </dependency>
  ```
  
- 之后编写实体类Department ，Employee，以及对应的dao ，DepartmentDao，EmployeeDao

### 9.2首页实现

- 首页配置：注意点：所有页面的静态资源导入都需要使用Thymeleaf接管，步骤为导入命名空间，之后将静态资源的链接变为 th:href="@{/css/bootstrap.min.css}" ,这个地方的链接开头一定要是/ ，如果我们在application.properties 配置文件中，重新定义了项目的访问路径，springboot会帮我们在链接前加上新的访问路径

- index.html 看第2，10，12行，

  ```html
  <!DOCTYPE html>
  <html lang="en" xml:th="http://www.thymeleaf.org" xmlns:th="http://www.w3.org/1999/xhtml">
  	<head>
  		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  		<meta name="description" content="">
  		<meta name="author" content="">
  		<title>Signin Template for Bootstrap</title>
  		<!-- Bootstrap core CSS -->
  		<link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
  		<!-- Custom styles for this template -->
  		<link th:href="@{/css/signin.css}" rel="stylesheet">
  	</head>
  
  	<body class="text-center">...</body>
  </html>
  ```
  
- application.properties

  ```properties
  #关闭模板引擎缓存
  spring.thymeleaf.cache=false
  
  # 新的访问路径
  server.servlet.context-path=/zssea
  ```
  
- 重新定义了项目的访问路径,之后重新访问，并查看源代码

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025215946824.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 9.3页面国际化

- 有的时候，我们的网站会去涉及中英文甚至多语言的切换，这时候我们就需要学习国际化了！

#### 9.3.1准备工作

- 先在IDEA中统一设置properties的编码问题！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220002256.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 9.3.2配置文件编写

- 编写国际化配置文件，抽取页面需要显示的国际化页面消息。我们可以去登录页面查看一下，哪些内容我们需要编写国际化的配置！

  - 1、我们在resources资源文件下新建一个i18n目录，存放国际化配置文件
  - 2、建立一个login.properties文件，还有一个login_zh_CN.properties；发现IDEA自动识别了我们要做国际化操作；文件夹变了！

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220027599.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

  - 3、我们可以在这上面去新建一个文件； 弹出如下页面：我们再添加一个英文的；

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220043100.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220053485.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)
    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220101813.jpg#pic_center)

  - **4、接下来，我们就来编写配置，我们可以看到idea下面有另外一个视图；**

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220113840.jpg#pic_center)

    - 这个视图我们点击 + 号就可以直接添加属性了；我们新建一个login.tip，可以看到边上有三个文件框可以输入；

      ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220126667.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

    - 我们添加一下首页的内容！

      ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220138523.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

    - 然后依次添加其他页面内容即可！

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220149598.jpg#pic_center)

    - 然后去查看我们的配置文件；

      ```properties
      # login.properties
      
      login.password=密码
      login.remember=记住我
      login.tip=请登录
      login.username=用户名
      loign.btn=登录
      ```
      
```properties
      #login_zh_CN.properties
      login.password=密码
      login.remember=记住我
      login.tip=请登录
      login.username=用户名
      loign.btn=登录
```

      ```properties
#login_en_US.properties
      login.password=Password
      login.remember=Remember me
      login.tip=Please sign in
      login.username=Username
      loign.btn=Sign in
      ```
      
      - OK，配置文件步骤搞定！

#### 9.3.3配置文件生效探究

- 我们去看一下SpringBoot对国际化的自动配置！这里又涉及到一个类：MessageSourceAutoConfiguration

- 里面有一个方法，这里发现SpringBoot已经自动配置好了管理我们国际化资源文件(就是在i8n文件夹下的文件)的组件 ResourceBundleMessageSource； （这个东西是识别国际化配置信息文件里的信息，而下面的那个LocaleResolver 是判断我们是否自己写了国家化配置信息文件）

  ```java
  // 获取 properties 传递过来的值进行判断
  @Bean
  public MessageSource messageSource(MessageSourceProperties properties) {
      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
      if (StringUtils.hasText(properties.getBasename())) {
          // 设置国际化文件的基础名（去掉语言国家代码的）
          messageSource.setBasenames(
              StringUtils.commaDelimitedListToStringArray(
                                         StringUtils.trimAllWhitespace(properties.getBasename())));
      }
      if (properties.getEncoding() != null) {
          messageSource.setDefaultEncoding(properties.getEncoding().name());
      }
      messageSource.setFallbackToSystemLocale(properties.isFallbackToSystemLocale());
      Duration cacheDuration = properties.getCacheDuration();
      if (cacheDuration != null) {
          messageSource.setCacheMillis(cacheDuration.toMillis());
      }
      messageSource.setAlwaysUseMessageFormat(properties.isAlwaysUseMessageFormat());
      messageSource.setUseCodeAsDefaultMessage(properties.isUseCodeAsDefaultMessage());
      return messageSource;
  }
  ```
  
- 我们国际化的配置文件的真实位置是放在了i18n目录下，所以我们要去配置这个messages的路径；

  ```properties
  spring.messages.basename=i18n.login
  ```

#### 9.3.4配置页面国际化值

- 去页面获取国际化的值，查看Thymeleaf的文档，找到message取值操作为：#{…}。我们去页面测试下：

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220206150.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 我们可以去启动项目，访问一下，发现已经自动识别为中文的了！

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220216842.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- **但是我们想要更好！可以根据按钮自动切换中文英文！**

#### 9.3.5配置国际化解析

- 在Spring中有一个国际化的Locale （区域信息对象）；里面有一个叫做LocaleResolver （获取区域信息对象）的解析器！

- 我们去我们webmvc（WebMvcAutoConfiguration.java）自动配置文件，寻找一下！看到SpringBoot默认配置：

  ```java
  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
  public LocaleResolver localeResolver() {
      // 容器中没有就自己配，有的话就用用户配置的（在application.properties配置文件中 spring.messages.basename=i18n.login 这一句就是绑定用户自己配置的国家化信息）
      if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
          return new FixedLocaleResolver(this.mvcProperties.getLocale());
      }
      // 接收头国际化分解
      AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
      localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
      return localeResolver;
  }
  ```
  
- AcceptHeaderLocaleResolver 这个类中有一个方法 （这是源码给的一个国际化（地区）解析器，我们也可以自己写一个）

  ```java
  public class AcceptHeaderLocaleResolver implements LocaleResolver{
      ...
      public Locale resolveLocale(HttpServletRequest request) {
          Locale defaultLocale = this.getDefaultLocale();
          // 默认的就是根据请求头带来的区域信息获取Locale进行国际化
          if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
              return defaultLocale;
          } else {
              Locale requestLocale = request.getLocale();
              List<Locale> supportedLocales = this.getSupportedLocales();
              if (!supportedLocales.isEmpty() && !supportedLocales.contains(requestLocale)) {
                  Locale supportedLocale = this.findSupportedLocale(request, supportedLocales);
                  if (supportedLocale != null) {
                      return supportedLocale;
                  } else {
                      return defaultLocale != null ? defaultLocale : requestLocale;
                  }
              } else {
                  return requestLocale;
              }
          }
      }
      ...
  }
  ```
  
- 那假如我们现在想点击链接让我们的国际化资源生效，就需要让我们自己的Locale生效！我们去自己写一个自己的LocaleResolver，可以在链接上携带区域信息！修改一下前端页面的跳转连接：

  ```html
  <!-- 这里传入参数不需要使用 ？使用 （key=value）-->
  <a class="btn btn-sm" th:href="@{/index.html(language='zh_CN')}">中文</a>
  <a class="btn btn-sm" th:href="@{/index.html(language='en_US')}">English</a>
  ```
  
- 我们去写一个处理的组件类！

  ```java
  package com.zssea.config;
  
  
  import org.springframework.util.StringUtils;
  import org.springframework.web.servlet.LocaleResolver;
  
  import javax.servlet.http.HttpServletRequest;
  import javax.servlet.http.HttpServletResponse;
  import java.util.Locale;
  
  //自定义 国际化解析器
  public class MyLocalResolver implements LocaleResolver  {
  
      //解析请求
      @Override
      public Locale resolveLocale(HttpServletRequest request) {
          //获取请求参数
          String language = request.getParameter("language");
          Locale locale = Locale.getDefault();//如果没有就使用默认的
          //如果请求的链接中携带了国际化的参数，就获取，拆分
          if(!StringUtils.isEmpty(language)){
              String[] split = language.split("_"); //zh_CN
              //国家，地区
              locale = new Locale(split[0],split[1]);
          }
  
          return locale;
      }
  
      @Override
      public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
  
      }
  }
  ```
  
- 为了让我们的区域化信息能够生效，我们需要再配置一下这个组件！在我们自己的MvcConfig下添加bean；

  ```java
      //自定义的国家化组件就生效了
      @Bean //注入组件
      public LocaleResolver localeResolver(){
          return new MyLocalResolver();
      }
  ```
  
- **我们重启项目，来访问一下，发现点击按钮可以实现成功切换！搞定收工！**

#### 9.3.6总结

- 页面国家化步骤：
  - 我们需要配置i18n文件，并在application.properties配置文件中绑定，之后在对应的html中用thymeleaf获取（获取国家化信息使用#{} ，链接url是@{} ，变量${}）
  - 如果我们需要在项目中进行按钮自动切换，我们需要自己定义一个组件MyLocalResolver继承LocaleResolver
  - 记得将自己写的组件配置到spring容器中，@Bean

### 9.4登录功能与登录拦截器

- LoginController

```java
package com.zssea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session){
        //具体的业务: 这里就不写了

        if(!StringUtils.isEmpty(username) && "123456".equals(password)){//登录成功（理解过程）

            session.setAttribute("loginUser",username); //保存登录信息，用于拦截器
            return "redirect:/main.html"; //重定向
        }else { //失败
            model.addAttribute("msg","用户名或密码错误"); //返回到index的提示信息
            return "index";
        }
    }
}
```

- LoginHnadlerInterceptor

```java
package com.zssea.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginHnadlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //登录成功之后应有 用户的session
        if(session.getAttribute("loginUser") == null){ //登录失败
            request.setAttribute("msg","没有权限,请先登录！");
            request.getRequestDispatcher("/index.html").forward(request,response);//转发到index.html 显示提示信息
            return false;
        }else { //登录成功
            return true;
        }
    }
}
```

- MyMvcConfig

```java
package com.zssea.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

//如果你想diy一些定制化的功能，只要写这个组件，然后把它交给SpringBoot，SpringBoot就会帮我们自动装配
@Configuration  //这个注解表明这是扩展的SpringMVC
public class MyMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/main.html").setViewName("dashboard");

    }

    //自定义的国际化组件就生效了
    @Bean //注入组件
    public LocaleResolver localeResolver(){
        return new MyLocalResolver();
    }

    //拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHnadlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html","/","/user/login","/css/**","/js/**","img/**");
    }
}
```

### 9.5展现员工列表

- 这一部分主要是对前端页面的学习，学习thymeleaf的应用，主要还是要通过Thymeleaf文档来学习，在这官网如果进不去，可以参考https://blog.csdn.net/liuminglei1987/article/details/106692004 ，这是个一篇Thymeleaf参考手册的博客，感谢博主总结！

- 要知道thymeleaf ，获取普通变量值 . . . ； 选 择 表 达 式 ∗ . . . ： 和 {...} ； 选择表达式 *{...}：和...；选择表达式∗...：和{}在功能上是一样；获取国际化内容 #{…}
  ；定义URL @{…} ； 片段引用表达式 ~{…}

- 抽取 重复的前端代码： 在第一层标签中加上 th:fragment=“templatename” ，之后在需要这段代码的位置使用属性 **th:insert** 或 **th:replace** 属性，表达式是 “~{templatename::fragmentname}”，如图

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220248490.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 如果要传递参数，可以直接使用() 传参，此处做了一个选中高亮显示

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220301669.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 列表循环展示

  ```html
  <table class="table table-striped table-sm">
  							<thead>
  								<tr>
  									<th>id</th>
  									<th>lastName</th>
  									<th>email</th>
  									<th>gender</th>
  									<th>birth</th>
  									<th>department</th>
  									<th>操作</th>
  								</tr>
  							</thead>
  							<tbody>
  								<tr th:each="emp:${emps}" >
  									<td th:text="${emp.getId()}"></td>
  									<td th:text="${emp.getLastName()}"></td>
  									<td th:text="${emp.getEmail()}"></td>
  									<td th:text="${emp.getGender()==0?'女':'男'}"></td>
  									<td th:text="${#dates.format(emp.getBirth(),'yyyy-MM-dd HH:mm:ss')}"></td>
  									<td th:text="${emp.department.getDepartmentName()}"></td>
  									<td>
  										<button class="btn btn-sm btn-primary">编辑</button>
  										<button class="btn btn-sm btn-danger">删除</button>
  									</td>
  								</tr>
  							</tbody>
  </table>
  ```

### 9.6添加，修改与删除员工

- 主要是一个逻辑操作，和之前的操作差不多，新东西还是thymeleaf在各个标签的用法
- 看代码吧
- 项目为：springboot_03_web

### 9.7错误处理

- 很简单，只要在templates文件夹下建立一个error文件夹，并且里面的html文件是以404或500这样的错误代码命名的，当出现错误时，会自动的寻找对应的错误显示页面

## 十、整合JDBC

### 10.1SpringData简介

- 对于数据访问层，无论是 SQL(关系型数据库) 还是 NOSQL(非关系型数据库)，Spring Boot 底层都是采用 Spring Data 的方式进行统一处理。
- Spring Boot 底层都是采用 Spring Data 的方式进行统一处理各种数据库，Spring Data 也是 Spring 中与 Spring Boot、Spring Cloud 等齐名的知名项目。
- Sping Data 官网：https://spring.io/projects/spring-data
- 数据库相关的启动器 ：可以参考官方文档：https://docs.spring.io/spring-boot/docs/2.2.5.RELEASE/reference/htmlsingle/#using-boot-starter

### 10.2整合JDBC

#### 10.2.1创建测试项目测试数据源

- 1、我去新建一个项目测试：springboot-data-jdbc ; 引入相应的模块！基础模块

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102522034359.png#pic_center)

- 2、项目建好之后，发现自动帮我们导入了如下的启动器：

```xml
 		<!--JDBC-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--MySql-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
```

- 3、编写yaml配置文件连接数据库；(application.yml)

```yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306/mybatis_wlw?serverTimezone=UTC&userUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

- 4、配置完这一些东西后，我们就可以直接去使用了，因为SpringBoot已经默认帮我们进行了自动配置；去测试类测试一下

```java
package com.zssea;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class Springboot04DataApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        //查看一下默认数据源
        System.out.println(dataSource.getClass()); 
        //class com.zaxxer.hikari.HikariDataSource

        //获得连接，并查看
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        //关闭连接
        connection.close();
    }

}
```

- 结果：我们可以看到他默认给我们配置的数据源为 : class com.zaxxer.hikari.HikariDataSource ， 我们并没有手动配置
- 我们来全局搜索一下，找到数据源的所有自动配置都在 ：DataSourceAutoConfiguration文件：

```java
	@Import({ DataSourceConfiguration.Hikari.class, DataSourceConfiguration.Tomcat.class,
			DataSourceConfiguration.Dbcp2.class, DataSourceConfiguration.Generic.class,
			DataSourceJmxConfiguration.class })
	protected static class PooledDataSourceConfiguration {

	}
```

- 这里导入的类都在 DataSourceConfiguration 配置类下，可以看出 Spring Boot 2.2.5 默认使用HikariDataSource 数据源，而以前版本，如 Spring Boot 1.5 默认使用 org.apache.tomcat.jdbc.pool.DataSource 作为数据源；
- **HikariDataSource 号称 Java WEB 当前速度最快的数据源，相比于传统的 C3P0 、DBCP、Tomcat jdbc 等连接池更加优秀；**
- **可以使用 spring.datasource.type 指定自定义的数据源类型，值为要使用的连接池实现的完全限定名。**
- 关于数据源我们并不做介绍，有了数据库连接，显然就可以 CRUD 操作数据库了。但是我们需要先了解一个对象 JdbcTemplate（其实springboot里面为我们提供了很多xxxxTemplate,这些都是已经配置好的模板bean，拿来急用）

#### 10.2.2JDBCTemplate

- 1、有了数据源(com.zaxxer.hikari.HikariDataSource)，然后可以拿到数据库连接(java.sql.Connection)，有了连接，就可以使用原生的 JDBC 语句来操作数据库；
- 2、即使不使用第三方第数据库操作框架，如 MyBatis等，Spring 本身也对原生的JDBC 做了轻量级的封装，即JdbcTemplate。
- 3、数据库操作的所有 CRUD 方法都在 JdbcTemplate 中。
- 4、Spring Boot 不仅提供了默认的数据源，同时默认已经配置好了 JdbcTemplate 放在了容器中，程序员只需自己注入即可使用
- 5、JdbcTemplate 的自动配置是依赖 org.springframework.boot.autoconfigure.jdbc 包下的 JdbcTemplateConfiguration 类
- **JdbcTemplate主要提供以下几类方法：**
  - execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句；
  - update方法及batchUpdate方法：update方法用于执行新增、修改、删除等语句；batchUpdate方法用于执行批处理相关语句；
  - query方法及queryForXXX方法：用于执行查询相关语句；
  - call方法：用于执行存储过程、函数相关语句。

### 10.2.3测试

- 编写一个Controller，注入 jdbcTemplate，编写测试方法进行访问测试；

```java
package com.zssea.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jdbc")
public class JdbcController {

    /**
     * Spring Boot 默认提供了数据源，默认提供了 org.springframework.jdbc.core.JdbcTemplate
     * JdbcTemplate 中会自己注入数据源，用于简化 JDBC操作
     * 还能避免一些常见的错误,使用起来也不用再自己来关闭数据库连接
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查询t_user表中所有数据 ,没有实体类对应，数据库中的东西怎么获取，用map
    //List 中的1个 Map 对应数据库的 1行数据
    //Map 中的 key 对应数据库的字段名，value 对应数据库的字段值
    @GetMapping("/userlist")
    public List<Map<String,Object>> userList(){
        String sql = "select * from t_user";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        return maps;
    }

    @GetMapping("/addUser")
    public String addUser(){
        String sql = "insert into t_user(username,password,gender,regist_time) values ('mengyuan','123456',0,'"+new Date().toLocaleString() +"')";
        jdbcTemplate.update(sql);
        return "addOK";
    }

    @GetMapping("/updateUser/{id}")
    public String updateUser(@PathVariable("id") int id){
        String sql = "update t_subjects set name=?,grade=? where id="+id;
        //数据
        Object[] objects = new Object[2];
        objects[0] = "js";
        objects[1] = "3";

        jdbcTemplate.update(sql,objects);
        return "updateOK";
    }

    @GetMapping("/delUser/{id}")
    public String delUser(@PathVariable("id") int id){
        String sql = "delete from t_subjects where id=?";
        jdbcTemplate.update(sql,id);
        return "delOK";
    }
}
```

- 测试请求，结果正常；到此，CURD的基本操作，使用 JDBC 就搞定了。

## 十一、整合Druid

### 11.1Druid简介

- Java程序很大一部分要操作数据库，为了提高性能操作数据库的时候，又不得不使用数据库连接池。
- Druid 是阿里巴巴开源平台上一个数据库连接池实现，结合了 C3P0、DBCP 等 DB 池的优点，同时加入了日志监控。
- Druid 可以很好的监控 DB 池连接和 SQL 的执行情况，天生就是针对监控而生的 DB 连接池。
- Druid已经在阿里巴巴部署了超过600个应用，经过一年多生产环境大规模部署的严苛考验。
- Spring Boot 2.0 以上默认使用 Hikari 数据源，可以说 Hikari 与 Driud 都是当前 Java Web 上最优秀的数据源，我们来重点介绍 Spring Boot 如何集成 Druid 数据源，如何实现数据库监控。
- Github地址：https://github.com/alibaba/druid/
- **com.alibaba.druid.pool.DruidDataSource 基本配置参数如下：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220414637.png#pic_center)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220427809.png#pic_center)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220437512.png#pic_center)

### 11.2配置数据源

- 1、添加上 Druid 数据源依赖。

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
```

- 2、切换数据源；之前已经说过 Spring Boot 2.0 以上默认使用 com.zaxxer.hikari.HikariDataSource 数据源，但可以 通过 spring.datasource.type 指定数据源。

```yaml
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://localhost:3306//mybatis_wlw?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource # 自定义数据源
```

- 3、数据源切换之后，在测试类中注入 DataSource，然后获取到它，输出一看便知是否成功切换；

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220451585.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 4、切换成功！既然切换成功，就可以设置数据源连接初始化大小、最大连接数、等待时间、最小连接数 等设置项；可以查看源码

```yaml
spring:
  datasource:
    username: root
    password: 123456
    #?serverTimezone=UTC解决时区的报错
    url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource

    #Spring Boot 默认是不注入这些属性值的，需要自己绑定
    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters，stat:监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许时报错  java.lang.ClassNotFoundException: org.apache.log4j.Priority
    #则导入 log4j 依赖即可，Maven 地址：https://mvnrepository.com/artifact/log4j/log4j
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
```

- 5、导入Log4j 的依赖

```xml
<!-- https://mvnrepository.com/artifact/log4j/log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

- 6、现在需要程序员自己为 DruidDataSource 绑定全局配置文件中的参数，再添加到容器中，而不再使用 Spring Boot 的自动生成了；我们需要 自己添加 DruidDataSource 组件到容器中，并绑定属性； 自己定义一个DruidConfig 类

```java
package com.zssea.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

//这是一个配置类 需要加@Configuration 这个注解
@Configuration
public class DruidConfig {

    /*
      将 自定义的 Druid数据源 添加到容器中，不再让 Spring Boot 自动创建(自动创建的是HikariDataSource)
      绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效，绑定操作就是加上以下注解
      @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
      前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
    */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
}
```

- 7、去测试类中测试一下；看是否成功！

```java
package com.zssea;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class Springboot04DataApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        //查看一下默认数据源
        System.out.println(dataSource.getClass());
        //class com.zaxxer.hikari.HikariDataSource  (springboot默认的数据源)
        //class com.alibaba.druid.pool.DruidDataSource （我自己引进的druid数据源）

        //获得连接，并查看
        Connection connection = dataSource.getConnection();
        System.out.println(connection);

        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源最大连接数：" + druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数：" + druidDataSource.getInitialSize());

        //关闭连接
        connection.close();
    }

}
/*
输出结果 ：可见配置参数已经生效！
com.alibaba.druid.proxy.jdbc.ConnectionProxyImpl@6fa0450e
druidDataSource 数据源最大连接数：20
druidDataSource 数据源初始化连接数：5
*/
```

### 11.3配置Druid数据源监控

- Druid 数据源具有监控的功能，并提供了一个 web 界面方便用户查看，类似安装 路由器 时，人家也提供了一个默认的 web 页面。
- 所以第一步需要设置 Druid 的后台管理页面，比如 登录账号、密码 等；配置后台管理；
- 是在我们刚刚写的DruidConfig类中写的

```java
package com.zssea.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//这是一个配置类 需要加@Configuration 这个注解
@Configuration
public class DruidConfig {

    /*
      将 自定义的 Druid数据源 添加到容器中，不再让 Spring Boot 自动创建(自动创建的是HikariDataSource)
      绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效，绑定操作就是加上以下注解
      @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
      前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
    */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //配置 Druid 监控管理后台的Servlet；
    //因为springboot内置了 Servlet 容器，所以没有了web.xml文件， 替代的方法就是 ServletRegistrationBean （Spring Boot的注册 Servlet方式）
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        // 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到
        //后台需要有人登录，账号密码的配置
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin"); //后台管理界面的登录账号
        initParams.put("loginPassword", "123456"); //后台管理界面的登录密码

        //后台允许谁可以访问
        //initParams.put("allow", "localhost")：表示只有本机可以访问
        //initParams.put("allow", "")：为空或者为null时，表示允许所有访问
        initParams.put("allow", "");
        //deny：Druid 后台拒绝谁访问 initParams.put("kuangshen", "192.168.1.20");表示禁止此ip访问

        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }

}
```

- 配置完毕后，我们可以选择访问 ：http://localhost:8080/druid/login.html

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220509460.png#pic_center)

- 进入之后

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102522052119.png#pic_center)

- **配置 Druid web 监控 filter 过滤器**

```java
package com.zssea.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//这是一个配置类 需要加@Configuration 这个注解
@Configuration
public class DruidConfig {

    /*
      将 自定义的 Druid数据源 添加到容器中，不再让 Spring Boot 自动创建(自动创建的是HikariDataSource)
      绑定全局配置文件中的 druid 数据源属性到 com.alibaba.druid.pool.DruidDataSource从而让它们生效，绑定操作就是加上以下注解
      @ConfigurationProperties(prefix = "spring.datasource")：作用就是将 全局配置文件中
      前缀为 spring.datasource的属性值注入到 com.alibaba.druid.pool.DruidDataSource 的同名参数中
    */
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //配置 Druid 监控管理后台的Servlet；
    //因为springboot内置了 Servlet 容器，所以没有了web.xml文件， 替代的方法就是 ServletRegistrationBean （Spring Boot的注册 Servlet方式）
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        // 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到
        //后台需要有人登录，账号密码的配置
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", "admin"); //后台管理界面的登录账号
        initParams.put("loginPassword", "123456"); //后台管理界面的登录密码

        //后台允许谁可以访问
        //initParams.put("allow", "localhost")：表示只有本机可以访问
        //initParams.put("allow", "")：为空或者为null时，表示允许所有访问
        initParams.put("allow", "");
        //deny：Druid 后台拒绝谁访问 initParams.put("kuangshen", "192.168.1.20");表示禁止此ip访问

        //设置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }

    //配置 Druid 监控 之  web 监控的 filter  （在之前是在web.xml中配置过滤器）
    //WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        //exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*,/jdbc/*");  //这些东西不进行统计
        bean.setInitParameters(initParams);

        // "/*" 表示过滤所有请求
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
```

- 平时在工作中，按需求进行配置即可，主要用作监控！

## 十二、整合MyBatis

- 官方文档：http://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
- Maven仓库地址：https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter/2.1.1

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220535975.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 12.1整合测试

- 1、导入 MyBatis 所需要的依赖

```xml
<!--mybatis-spring-boot整合-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
```

- 2、配置数据库连接信息（不变）

```yaml
spring:
  datasource:
    username: root
    password: 123456
    #?serverTimezone=UTC解决时区的报错
    url: jdbc:mysql://localhost:3306/springboot?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource

    #Spring Boot 默认是不注入这些属性值的，需要自己绑定
    #druid 数据源专有配置
    initialSize: 5
    minIdle: 5
    maxActive: 20
    maxWait: 60000
    timeBetweenEvictionRunsMillis: 60000
    minEvictableIdleTimeMillis: 300000
    validationQuery: SELECT 1 FROM DUAL
    testWhileIdle: true
    testOnBorrow: false
    testOnReturn: false
    poolPreparedStatements: true

    #配置监控统计拦截的filters，stat:监控统计、log4j：日志记录、wall：防御sql注入
    #如果允许时报错  java.lang.ClassNotFoundException: org.apache.log4j.Priority
    #则导入 log4j 依赖即可，Maven 地址：https://mvnrepository.com/artifact/log4j/log4j
    filters: stat,wall,log4j
    maxPoolPreparedStatementPerConnectionSize: 20
    useGlobalDataSourceStat: true
    connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
#整合mybatis(在mybatis中是在配置文件中写的，在SSM中是在spring的配置文件中写的)
mybatis:
  type-aliases-package: com.zssea.pojo  #别名
  mapper-locations: classpath:mybatis/mapper/*.xml  #注册mapper文件    
```

- 3、测试数据库是否连接成功！

- 4、创建实体类，导入 Lombok依赖！

  User.java

```java
package com.zssea.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Boolean gender;
    private Date regist_time;
}
```

- 5、创建mapper目录以及对应的 Mapper 接口

  UserMapper.java

```java
package com.zssea.mapper;

import com.zssea.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//也可以使用@MapperScan(com.zssea.mapper) 在 Springboot04DataApplication类上使用
@Mapper //表示本类是一个 MyBatis 的Mapper类 (类似之前的UserDao接口)
@Repository // 注解生成bean，dao层专用
public interface UserMapper {
    List<User> queryUserList();
    User queryUserById(int id);
    int addUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
}
```

- 6、对应的Mapper映射文件

  UserMapper.xml.xml (src/main/resources/mybatis/mapper/UserMapper.xml)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zssea.mapper.UserMapper">

    <select id="queryUserList" resultType="User">
        select * from t_user;
    </select>

    <select id="queryUserById" resultType="User">
        select * from t_user where id = #{id}
    </select>

    <insert id="addUser" parameterType="User">
        insert into t_user values (#{id},#{username},#{password},#{gender},#{regist_time});
    </insert>

    <update id="updateUser" parameterType="User">
        update t_user set  username=#{username}, password=#{password},gender=#{gender},regist_time=#{regist_time}
    </update>

    <delete id="deleteUser" parameterType="int">
        delete from t_user where id = #{id}
    </delete>
</mapper>
```

- 7、maven配置资源过滤问题

```xml
 	<build>
        <!-- 更改maven编译规则,解决xxxMapper.xml存放在resources以外路径中的读取问题
 		如果xxxMapper.xml 还是存放在/resources/mybatis/mapper/目录下就可以不用配-->
        <resources>
            <resource>
                <!-- 资源目录 -->
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include><!-- 新添加 */代表1级目录 **/代表多级目录-->
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```

- 8、编写部门的 UserController 进行测试！

```java
package com.zssea.controller;

import com.zssea.mapper.UserMapper;
import com.zssea.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/userList")
    public List<User> queryUserList(){
        List<User> users = userMapper.queryUserList();
        for (User user : users) {
            System.out.println(user);
        }
        return users;
    }

    @GetMapping("/addUser")
    public String addUser(){
        int i = userMapper.addUser(new User(6,"mengyuan","123",true,null));
        System.out.println("add--"+i);
        return "ok";
    }

    @GetMapping("/updateUser")
    public String updateUser(){
        int i = userMapper.updateUser(new User(6,"mengyuan","852963",true,null));
        System.out.println("update--"+i);
        return "ok";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(){
        int i = userMapper.deleteUser(6);
        System.out.println("delete--"+i);
        return "ok";
    }
}
```

- 启动项目访问进行测试！

## 十三、SpringSecurity（安全） Shiro

### 13.1安全简介

在 Web 开发中，安全一直是非常重要的一个方面。安全虽然属于应用的非功能性需求，但是应该在应用开发的初期就考虑进来。如果在应用开发的后期才考虑安全的问题，就可能陷入一个两难的境地：一方面，应用存在严重的安全漏洞，无法满足用户的要求，并可能造成用户的隐私数据被攻击者窃取；另一方面，应用的基本架构已经确定，要修复安全漏洞，可能需要对系统的架构做出比较重大的调整，因而需要更多的开发时间，影响应用的发布进程。因此，从应用开发的第一天就应该把安全相关的因素考虑进来，并在整个应用的开发过程中。

市面上存在比较有名的框架：Shiro，Spring Security ！

这里需要阐述一下的是，每一个框架的出现都是为了解决某一问题而产生了，那么Spring Security框架的出现是为了解决什么问题呢？

首先我们看下它的官网介绍：Spring Security官网地址 https://spring.io/projects/spring-security

Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications.

Spring Security is a framework that focuses on providing both authentication and authorization to Java applications. Like all Spring projects, the real power of Spring Security is found in how easily it can be extended to meet custom requirements

Spring Security是一个功能强大且高度可定制的身份验证和访问控制框架。它实际上是保护基于spring的应用程序的标准。

Spring Security是一个框架，侧重于为Java应用程序提供身份验证和授权。与所有Spring项目一样，Spring安全性的真正强大之处在于它可以轻松地扩展以满足定制需求

从官网的介绍中可以知道这是一个权限框架。想我们之前做项目是没有使用框架是怎么控制权限的？对于权限 一般会细分为功能权限，访问权限，和菜单权限。代码会写的非常的繁琐，冗余。

怎么解决之前写权限代码繁琐，冗余的问题，一些主流框架就应运而生而Spring Scecurity就是其中的一种。

Spring 是一个非常流行和成功的 Java 应用开发框架。Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方案。一般来说，Web 应用的安全性包括用户认证（Authentication）和用户授权（Authorization）两个部分。用户认证指的是验证某个用户是否为系统中的合法主体，也就是说用户能否访问该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认证过程。用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以进行修改。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的权限。

对于上面提到的两种应用情景，Spring Security 框架都有很好的支持。在用户认证方面，Spring Security 框架支持主流的认证方式，包括 HTTP 基本认证、HTTP 表单验证、HTTP 摘要认证、OpenID 和 LDAP 等。在用户授权方面，Spring Security 提供了基于角色的访问控制和访问控制列表（Access Control List，ACL），可以对应用中的领域对象进行细粒度的控制。

### 13.2实战测试

#### 13.2.1实验环境搭建

- 1、新建一个初始的springboot项目web模块，thymeleaf模块
- 2、导入静态资源
- 3、controller跳转！

```java
package com.zssea.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController {

    @RequestMapping({"/index","/"})
    public String index(){
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "views/login";
    }

    @RequestMapping("/level1/{id}")
    public String level1(@PathVariable("id") int id){
        return "views/level1/"+id;
    }

    @RequestMapping("/level2/{id}")
    public String level2(@PathVariable("id") int id){
        return "views/level2/"+id;
    }

    @RequestMapping("/level3/{id}")
    public String level3(@PathVariable("id") int id){
        return "views/level3/"+id;
    }
}
```

- 4、测试实验环境是否OK！

#### 13.2.2认识SpringSecurity

Spring Security 是针对Spring项目的安全框架，也是Spring Boot底层安全模块默认的技术选型，他可以实现强大的Web安全控制，对于安全控制，我们仅需要引入 spring-boot-starter-security 模块，进行少量的配置，即可实现强大的安全管理！

记住几个类：

- WebSecurityConfigurerAdapter：自定义Security策略
- AuthenticationManagerBuilder：自定义认证策略
- @EnableWebSecurity：开启WebSecurity模式，（@Enablexxxx 开启某个功能）

Spring Security的两个主要目标是 “认证” 和 “授权”（访问控制）。

- **“认证”（Authentication）**

  身份验证是关于验证您的凭据，如用户名/用户ID和密码，以验证您的身份。

  身份验证通常通过用户名和密码完成，有时与身份验证因素结合使用。

- **“授权” （Authorization）**

  授权发生在系统成功验证您的身份后，最终会授予您访问资源（如信息，文件，数据库，资金，位置，几乎任何内容）的完全权限。

  这个概念是通用的，而不是只在Spring Security 中存在。

#### 13.2.3认证和授权

目前，我们的测试环境，是谁都可以访问的，我们使用 Spring Security 增加上认证和授权的功能

- 1、引入 Spring Security 模块（依赖）

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

- 2、编写 Spring Security 配置类

  参考官网：https://spring.io/projects/spring-security

  查看我们自己项目中的版本，找到对应的帮助文档：https://docs.spring.io/spring-security/site/docs/5.3.0.RELEASE/reference/html5 #servlet-applications 8.16.4

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220607504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 3、编写基础配置类

```java
package com.zssea.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity // 开启WebSecurity模式
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       
  }
}
```

- 4、定制请求的授权规则

```java
 //定义授权，指明那个角色可以访问那个功能
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人都能访问，功能页只有对应权限的人才能访问
        //设置请求授权的规则（设置权限）
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/level1/**").hasRole("vip1")
                .mvcMatchers("/level2/**").hasRole("vip2")
                .mvcMatchers("/level3/**").hasRole("vip3");
    }
```

- 5、测试一下：发现除了首页都进不去了！因为我们目前没有登录的角色，因为请求需要登录的角色拥有对应的权限才可以！
- 6、在configure()方法中加入以下配置，开启自动配置的登录功能！

```java
// 没有权限,默认跳转到登录页面
// /login 请求来到登录页
// /login?error 重定向到这里表示登录失败
http.formLogin();
```

- 7、测试一下：发现，没有权限的时候，会跳转到登录的页面！

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220623621.png#pic_center)

- 8、查看刚才登录页的注释信息；

  我们可以定义认证规则，重写configure(AuthenticationManagerBuilder auth)方法

```java
 //定义认证规则，指明哪么用户可以被认证(为用户认证为哪个角色)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中定义（下面写的就是在内存中定义），也可以在jdbc中去拿....
        auth.inMemoryAuthentication()
                .withUser("zssea").password("123456").roles("vip2","vip3")
                .and()
                .withUser("root").password("123456").roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password("123456").roles("vip1","vip2");
        //上面这些用户名和密码 正常来说应该从数据库中获取
    }
```

- 9、测试，我们可以使用这些账号登录进行测试！发现会报错！

  There is no PasswordEncoder mapped for the id “null”

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220659551.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 10、原因，我们要将前端传过来的密码进行某种方式加密，否则就无法登录，修改代码

```java
//定义认证规则
@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   //在内存中定义，也可以在jdbc中去拿....
   //Spring security 5.0中新增了多种加密方式，也改变了密码的格式。
   //要想我们的项目还能够正常登陆，需要修改一下configure中的代码。我们要将前端传过来的密码进行某种方式加密
   //spring security 官方推荐的是使用bcrypt加密方式。
   
   auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
          .withUser("kuangshen").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
          .and()
          .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
          .and()
          .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2");
}
```

- 11、测试，发现，登录成功，并且每个角色只能访问自己认证下的规则！搞定

#### 13.2.4权限控制和注销

- 1、开启自动配置的注销的功能

```java
//定制请求的授权规则
@Override
protected void configure(HttpSecurity http) throws Exception {
   //....
   //开启自动配置的注销的功能
      // /logout 注销请求
   http.logout();
}
```

- 2、我们在前端，增加一个注销的按钮，index.html 导航栏中

```html
<a class="item" th:href="@{/logout}">
   <i class="sign-out icon"></i> 注销
</a>
```

- 3、我们可以去测试一下，登录成功后点击注销，发现注销完毕会跳转到登录页面！
- 4、但是，我们想让他注销成功后，依旧可以跳转到首页，该怎么处理呢？

```java
// .logoutSuccessUrl("/"); 注销成功来到首页
http.logout().logoutSuccessUrl("/");
```

- 5、测试，注销完毕后，发现跳转到首页OK

- 6、我们现在又来一个需求：用户没有登录的时候，导航栏上只显示登录按钮，用户登录之后，导航栏可以显示登录的用户信息及注销按钮！还有就是，比如zssea这个用户，它只有 vip2，vip3功能，那么登录则只显示这两个功能，而vip1的功能菜单不显示！这个就是真实的网站情况了！该如何做呢？

  我们需要结合thymeleaf中的一些功能

  sec：authorize=“isAuthenticated()”:是否认证登录！来显示不同的页面

  Maven依赖：

```xml
<!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity4 -->
<dependency>
   <groupId>org.thymeleaf.extras</groupId>
   <artifactId>thymeleaf-extras-springsecurity5</artifactId>
   <version>3.0.4.RELEASE</version>
</dependency>
```

- 7、修改我们的 前端页面

  导入命名空间

```html
xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5"
```

 修改导航栏，增加认证判断

```html
<!--登录注销-->
<div class="right menu">

   <!--如果未登录-->
   <div sec:authorize="!isAuthenticated()">
       <a class="item" th:href="@{/login}">
           <i class="address card icon"></i> 登录
       </a>
   </div>

   <!--如果已登录-->
   <div sec:authorize="isAuthenticated()">
       <a class="item">
           <i class="address card icon"></i>
          用户名：<span sec:authentication="principal.username"></span>
          角色：<span sec:authentication="principal.authorities"></span>
       </a>
   </div>

   <div sec:authorize="isAuthenticated()">
       <a class="item" th:href="@{/logout}">
           <i class="address card icon"></i> 注销
       </a>
   </div>
</div>
```

- 8、重启测试，我们可以登录试试看，登录成功后确实，显示了我们想要的页面；
- 9、如果注销404了，就是因为它默认防止csrf跨站请求伪造，因为会产生安全问题，我们可以将请求改为post表单提交，或者在spring security中关闭csrf功能；我们试试：在 配置中增加 `http.csrf().disable();`

```java
http.csrf().disable();//关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
http.logout().logoutSuccessUrl("/");
```

- 10、我们继续将下面的角色功能块认证完成！

```html
<!-- sec:authorize="hasRole('vip1')" -->
<div class="column" sec:authorize="hasRole('vip1')">
   <div class="ui raised segment">
       <div class="ui">
           <div class="content">
               <h5 class="content">Level 1</h5>
               <hr>
               <div><a th:href="@{/level1/1}"><i class="bullhorn icon"></i> Level-1-1</a></div>
               <div><a th:href="@{/level1/2}"><i class="bullhorn icon"></i> Level-1-2</a></div>
               <div><a th:href="@{/level1/3}"><i class="bullhorn icon"></i> Level-1-3</a></div>
           </div>
       </div>
   </div>
</div>

<div class="column" sec:authorize="hasRole('vip2')">
   <div class="ui raised segment">
       <div class="ui">
           <div class="content">
               <h5 class="content">Level 2</h5>
               <hr>
               <div><a th:href="@{/level2/1}"><i class="bullhorn icon"></i> Level-2-1</a></div>
               <div><a th:href="@{/level2/2}"><i class="bullhorn icon"></i> Level-2-2</a></div>
               <div><a th:href="@{/level2/3}"><i class="bullhorn icon"></i> Level-2-3</a></div>
           </div>
       </div>
   </div>
</div>

<div class="column" sec:authorize="hasRole('vip3')">
   <div class="ui raised segment">
       <div class="ui">
           <div class="content">
               <h5 class="content">Level 3</h5>
               <hr>
               <div><a th:href="@{/level3/1}"><i class="bullhorn icon"></i> Level-3-1</a></div>
               <div><a th:href="@{/level3/2}"><i class="bullhorn icon"></i> Level-3-2</a></div>
               <div><a th:href="@{/level3/3}"><i class="bullhorn icon"></i> Level-3-3</a></div>
           </div>
       </div>
   </div>
</div>
```

11、测试一下！

12、权限控制和注销搞定！

#### 13.2.5记住我

现在的情况，我们只要登录之后，关闭浏览器，再登录，就会让我们重新登录，但是很多网站的情况，就是有一个记住密码的功能，这个该如何实现呢？很简单

- 1、开启记住我功能

```java
//定制请求的授权规则
@Override
protected void configure(HttpSecurity http) throws Exception {
	//........
   //开启 记住我 功能
   http.rememberMe();
}
```

- 2、我们再次启动项目测试一下，发现登录页多了一个记住我功能，我们登录之后关闭 浏览器，然后重新打开浏览器访问，发现用户依旧存在！

  思考：如何实现的呢？其实非常简单

  我们可以查看浏览器的cookie

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220718310.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 3、我们点击注销的时候，可以发现，spring security 帮我们自动删除了这个 cookie

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102522072922.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- 4、结论：登录成功后，将cookie发送给浏览器保存，以后登录带上这个cookie，只要通过检查就可以免登录了。如果点击注销，则会删除这个cookie，具体的原理我们在JavaWeb阶段都讲过了，这里就不在多说了

#### 13.2.6定制登录页

现在这个登录页面都是spring security 默认的，怎么样可以使用我们自己写的Login界面呢？

- 1、在刚才的登录页配置后面指定 loginpage

```java
http.formLogin().loginPage("/toLogin");
```

- 2、然后前端也需要指向我们自己定义的 login请求

```html
<a class="item" th:href="@{/toLogin}">
   <i class="address card icon"></i> 登录
</a>
```

- 3、我们登录，需要将这些信息发送到哪里，我们也需要配置，login.html 配置提交请求及方式，方式必须为post:

  在 loginPage()源码中的注释上有写明：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220741217.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

```html
<form th:action="@{/login}" method="post">
   <div class="field">
       <label>Username</label>
       <div class="ui left icon input">
           <input type="text" placeholder="Username" name="username">
           <i class="user icon"></i>
       </div>
   </div>
   <div class="field">
       <label>Password</label>
       <div class="ui left icon input">
           <input type="password" name="password">
           <i class="lock icon"></i>
       </div>
   </div>
   <input type="submit" class="ui blue submit button"/>
</form>
```

- 4、这个请求提交上来，我们还需要验证处理，怎么做呢？我们可以查看formLogin()方法的源码！我们配置接收登录的用户名和密码的参数！

```java
http.formLogin()
  .usernameParameter("username")
  .passwordParameter("password")
  .loginPage("/toLogin")
  .loginProcessingUrl("/login"); // 登陆表单提交请求
```

- 5、在登录页增加记住我的多选框

```html
<input type="checkbox" name="remember"> 记住我
```

- 6、后端验证处理！

```java
//定制记住我的参数！
http.rememberMe().rememberMeParameter("remember");
```

- 7、测试，OK

#### 13.2.7完整配置代码

```java
package com.zssea.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //定义授权，指明那个角色可以访问那个功能
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人都能访问，功能页只有对应权限的人才能访问
        //设置请求授权的规则（设置权限）
        http.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/level1/**").hasRole("vip1")
                .mvcMatchers("/level2/**").hasRole("vip2")
                .mvcMatchers("/level3/**").hasRole("vip3");

        //没有权限，默认跳转到登录页面，跳转路径是 /login(这个是spring security 默认的 /login?error 重定向到这里表示登录失败
        //.loginPage("/toLogin") 让他跳转到我们自己写的登录页面,
        // 跳转到登录页面之后，提交表单，看表单的action是什么，此处是/login ，
        // 默认的提交参数name为username 和 password ，如果换成其他的登录不成功， 除非在此处指定自己设置的name属性值
        http.formLogin().loginPage("/toLogin")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login");

        //注销,注销成功来到首页
        http.logout().logoutSuccessUrl("/");
        //http.csrf().disable();//关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求 (登录，登出失败的可能原因)
        
        //开启 记住我 功能，http.rememberMe() 这是默认的
        // 当登录页面换成自己写的之后， <input type="checkbox" name="remember"> 记住我 ，将name属性值传过来
        http.rememberMe().rememberMeParameter("remember");
    }
    
    //定义认证规则，指明哪么用户可以被认证(为用户认证为哪个角色)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //在内存中定义（下面写的就是在内存中定义），也可以在jdbc中去拿....
        //Spring security 5.0中新增了多种加密方式，也改变了密码的格式。
        //要想我们的项目还能够正常登陆，需要修改一下configure中的代码。我们要将前端传过来的密码进行某种方式加密
        // spring security 官方推荐的是使用bcrypt加密方式。
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("zssea").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2");
        //上面这些用户名和密码 正常来说应该从数据库中获取
    }
}
```

### 13.3Shiro3

- Subject 用户
- SecurityManager 管理用户
- Realm 连接数据
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200716234128649.png)
  **简单实验：**

1. 导入依赖

   ```xml
   <dependency>
       <groupId>org.apache.shiro</groupId>
       <artifactId>shiro-spring</artifactId>
       <version>1.5.3</version>
   </dependency>
   ```

2. 编写Shiro配置类

   ```java
   @Configuration
   public class ShrioConfig {
       //ShiroFilterFactoryBean : Step3
       @Bean
       public ShiroFilterFactoryBean getShrioFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
           ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
           //设置安全管理器
           bean.setSecurityManager(defaultWebSecurityManager);
           return bean;
       }
       
       //DefaultWebSecurityManager : Step2
       @Bean("securityManager")
       public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
           DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
           //关联userRealm
           securityManager.setRealm(userRealm);
           return securityManager;
       }
       
       //创建 realm 对象, 需要自定义类：Step1
       @Bean
       public UserRealm userRealm(){
           return new UserRealm();
       }
   }
   ```

3. 自定义UserRealm

   ```java
   //自定义的 UserRealm
   public class UserRealm extends AuthorizingRealm {
   
       //授权
       @Override
       protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
           return null;
       }
   
       //认证
       @Override
       protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
           return null;
       }
   }
   ```

**一个小Demo:**

1. 导入依赖

   - springboot-mybatis整合
   - shiro-thymelea整合

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.3.1.RELEASE</version>
           <relativePath/> <!-- lookup parent from repository -->
       </parent>
       <groupId>com.zssea</groupId>
       <artifactId>shiro-springboot</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>shiro-springboot</name>
       <description>Demo project for Spring Boot</description>
   
       <properties>
           <java.version>1.8</java.version>
       </properties>
   
       <dependencies>
           <!--shiro-thymeleaf整合-->
           <dependency>
               <groupId>com.github.theborakompanioni</groupId>
               <artifactId>thymeleaf-extras-shiro</artifactId>
               <version>2.0.0</version>
           </dependency>
   
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.20</version>
           </dependency>
           <!-- https://mvnrepository.com/artifact/log4j/log4j -->
           <dependency>
               <groupId>log4j</groupId>
               <artifactId>log4j</artifactId>
               <version>1.2.17</version>
           </dependency>
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid</artifactId>
               <version>1.1.22</version>
           </dependency>
           <!--引入mybatis,这是Mybatis官方提供的适配SpringBoot的，而不是SpringBoot自己的-->
           <dependency>
               <groupId>org.mybatis.spring.boot</groupId>
               <artifactId>mybatis-spring-boot-starter</artifactId>
               <version>2.1.1</version>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>1.18.12</version>
           </dependency>
           <dependency>
               <groupId>org.apache.shiro</groupId>
               <artifactId>shiro-spring</artifactId>
               <version>1.5.3</version>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-thymeleaf</artifactId>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
   
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
               <exclusions>
                   <exclusion>
                       <groupId>org.junit.vintage</groupId>
                       <artifactId>junit-vintage-engine</artifactId>
                   </exclusion>
               </exclusions>
           </dependency>
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   
   </project>
   ```

2. 整合MyBatis

   - 编写实体类
   - 编写mapper接口、mapper.xml、application.yml配置mybatis（别名，mapper.xml文件位置)
   - 编写service接口，serviceImpl类
     ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200716234155680.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0RERERlbmdf,size_16,color_FFFFFF,t_70)

3. 编写Shiro配置类

   ```java
   @Configuration
   public class ShrioConfig {
   
       //ShiroFilterFactoryBean : Step3
       @Bean
       public ShiroFilterFactoryBean getShrioFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
           ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
           //设置安全管理器
           bean.setSecurityManager(defaultWebSecurityManager);
   
           //添加shiro的内置过滤器
           /*
               anno: 无需认证就可以访问
               authc: 必须认证了才可以访问
               user: 必须拥有 记住我 功能才能用
               perms: 拥有对某个资源的权限才能访问
               role: 拥有某个角色权限才能访问
           */
   
           Map<String, String> filterMap = new LinkedHashMap<>();
   
           //用户授权,正常情况下没有授权会跳转到授权页面
           filterMap.put("/user/add","perms[user:add]");
           filterMap.put("/user/update","perms[user:update]");
   
           //拦截
           //filterMap.put("/user/add","authc");
           //filterMap.put("/user/update","authc");
           filterMap.put("/user/*","authc");
   
           //设置登录请求
           bean.setLoginUrl("/toLogin");
           //设置未授权页面
           bean.setUnauthorizedUrl("/noauth");
           bean.setFilterChainDefinitionMap(filterMap);
           return bean;
       }
   
       //DefaultWebSecurityManager : Step2
       @Bean("securityManager")
       public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
           DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
           //关联userRealm
           securityManager.setRealm(userRealm);
           return securityManager;
       }
   
       //创建 realm 对象, 需要自定义类：Step1
       @Bean
       public UserRealm userRealm(){
           return new UserRealm();
       }
   
       //整合 ShiroDialect:用来整合shiro thymeleaf
       @Bean
       public ShiroDialect getShiroDialect(){
           return new ShiroDialect();
       }
   }
   ```

4. 自定义UserRealm

   ```java
   //自定义的 UserRealm
   public class UserRealm extends AuthorizingRealm {
   
       @Autowired
       UserServiceImpl userService;
   
       //授权
       @Override
       protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
           System.out.println("执行了 => doGetAuthorizationInfo");
   
           SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
           // 拿到当前登录的这个对象
           Subject subject = SecurityUtils.getSubject();
           // 拿到User对象
           User currentUser = (User) subject.getPrincipal();
           // 设置当前用户的权限
           System.out.println(currentUser.getName() + "的权限为 " + currentUser.getPerms());
           info.addStringPermission(currentUser.getPerms());
   
           return info;
       }
   
       //认证
       @Override
       protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
           System.out.println("执行了 => 认证AuthenticationToken");
   
           UsernamePasswordToken userToken = (UsernamePasswordToken) authenticationToken;
           //连接真实的数据库
           User user = userService.queryUserByName(userToken.getUsername());
           if(user == null){
               //没有这个人
               return null; //抛出异常 UnknownAccountException
           }
   
           // 登录成功 将用户信息存入session//在前端通过th:if="session.loginUser==null"可以判断用户登录
           Subject currentSubject = SecurityUtils.getSubject();
           Session session = currentSubject.getSession();
           session.setAttribute("loginUser",user.getName());
   
           // 密码认证，shiro做
           return new SimpleAuthenticationInfo(user,user.getPassword(),"");
       }
   }
   ```

## 十四、项目集成Swagger

**学习目标：**

- 了解Swagger的概念及作用
- 巩固前后端分离
- 掌握在项目中集成Swagger自动生成API文档

### 14.1Swagger简介

- **前后端分离时代：**
  - 前端 -> 前端控制层、视图层【前端团队】
    - 前端工程化，伪造后端数据（json数据），不需要后端，前端工程依然能跑起来，只不过数据不准确而已，而在一个真正的项目中，前端的数据要从后端来获取。
  - 后端 -> 后端控制层、服务层、数据访问层【后端团队】
  - 前后端通过API进行交互
  - 前后端相对独立且松耦合
  - 前后端甚至可以部署在不同的服务器上
- **产生的问题**
  - 前后端集成，前端人员和后端人员无法做到 “及时协商，尽早解决”，最终导致问题集中爆发
- **解决方案**
  - 首先定义schema [ 计划的提纲 ]，并实时跟踪最新的API（后端改变，前端会立即发现），降低集成风险
  - 早些年：指定word计划文档来解决问题
  - 前后端分离：
    - 前端需要测试后端提供的接口：（早期：postman来测试）
    - 后端提供接口，需要实时更新最新的消息以及改动 （Swagger应运而生）
- **Swagger**
  - 号称世界上最流行的API框架
  - Restful Api 文档在线自动生成器 => **API 文档 与API 定义同步更新**（方法与文档同步更新）
  - 直接运行，可以在线测试API接口
  - 支持多种语言 （如：Java，PHP等）
  - 官网：https://swagger.io/

### 14.2SpringBoot集成Swagger

- **SpringBoot集成Swagger** => **springfox**，两个jar包

  - **Springfox-swagger2**
  - swagger-springmvc

- **使用Swagger** ，要求：jdk 1.8 + 否则swagger2无法运行 ，步骤：

  1、新建一个SpringBoot-web项目

  2、添加Maven依赖

```xml
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
   <groupId>io.springfox</groupId>
   <artifactId>springfox-swagger2</artifactId>
   <version>2.9.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
   <groupId>io.springfox</groupId>
   <artifactId>springfox-swagger-ui</artifactId>
   <version>2.9.2</version>
</dependency>
<!-- swagger3.0 只需要一个依赖-->
 <dependency>
     <groupId>io.springfox</groupId>
     <artifactId>springfox-boot-starter</artifactId>
     <version>3.0.0</version>
  </dependency>

```

 3、编写HelloController，测试确保运行成功！

 4、要使用Swagger，我们需要编写一个配置类SwaggerConfig来配置 Swagger

```java
package com.zssea.config;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
//swagger3.0为 @Configuration @EnableOpenApi
public class SwaggerConfig {  
}
```

5、访问测试 ：http://localhost:8080/swagger-ui.html ，可以看到swagger的界面；

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220814137.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

### 14.3配置Swagger

- 在Swagger的配置类SwaggerConfig中配置
- 1、Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger。

```java
@Bean //配置docket以配置Swagger具体参数
public Docket docket() {
   return new Docket(DocumentationType.SWAGGER_2);
}
```

- 2、可以通过apiInfo()属性配置文档信息

```java
 @Bean //配置docket以配置Swagger具体参数
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
    }

//配置文档信息
private ApiInfo apiInfo() {
   Contact contact = new Contact("联系人名字", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
   return new ApiInfo(
           "Swagger学习", // 标题
           "学习演示如何配置Swagger", // 描述
           "v1.0", // 版本
           "http://terms.service.url/组织链接", // 组织链接
           contact, // 联系人信息
           "Apach 2.0 许可", // 许可
           "许可链接", // 许可连接
           new ArrayList<>()// 扩展
  );
}
```

- 3、Docket 实例关联上 apiInfo()

```java
@Bean
public Docket docket() {
   return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
}
```

- 4、重启项目，访问测试 http://localhost:8080/swagger-ui.html 看下效果；

### 14.4配置扫描接口

- 1、构建Docket时通过select()方法配置怎么扫描接口。

```java
@Bean
public Docket docket() {
   return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()// 通过.select()方法，去配置要扫描接口的方式,RequestHandlerSelectors配置如何扫描接口
      .apis(RequestHandlerSelectors.basePackage("com.zssea.controller"))
      .build();
}
```

- 2、重启项目测试，由于我们配置根据包的路径扫描接口，所以我们只能看到一个类
- 3、除了通过包路径配置扫描接口外，还可以通过配置其他方式扫描接口，这里注释一下所有的配置方式：

```java
any() // 扫描所有，项目中的所有接口都会被扫描到
none() // 不扫描接口
// 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
withMethodAnnotation(final Class<? extends Annotation> annotation)
// 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有controller注解的类中的接口
withClassAnnotation(final Class<? extends Annotation> annotation)
basePackage(final String basePackage) // 根据包路径扫描接口
```

- 4、除此之外，我们还可以配置接口扫描过滤：

```java
@Bean
public Docket docket() {
   return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
      .apis(RequestHandlerSelectors.basePackage("com.zssea.controller"))
       // 配置如何通过path过滤,即这里只扫描请求以/zssea开头的接口
      .paths(PathSelectors.ant("/zssea/**"))
      .build();
}
```

- 5、paths（PathSelectors.）这里的可选值还有

```java
any() // 任何请求都扫描
none() // 任何请求都不扫描
regex(final String pathRegex) // 通过正则表达式控制
ant(final String antPattern) // 通过ant()控制
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025220839408.png#pic_center)

### 14.5配置Swagger开关

- 1、通过enable()方法配置是否启用swagger，如果是false，swagger将不能在浏览器中访问了

```java
@Bean
public Docket docket() {
   return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .enable(false) //配置是否启用Swagger，如果是false，在浏览器将无法访问
      .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
      .apis(RequestHandlerSelectors.basePackage("com.zssea.swagger.controller"))
       // 配置如何通过path过滤,即这里只扫描请求以/zssea开头的接口
      .paths(PathSelectors.ant("/zssea/**"))
      .build();
}
```

- 2、如何动态配置当项目处于test、dev环境时显示swagger，处于prod时不显示？(我只希望我的Swagger在生产环境中使用，在发布的时候不使用?)

```java
@Bean
public Docket docket(Environment environment) {
   // 设置要显示swagger的环境
   Profiles of = Profiles.of("dev", "test");
   // 判断当前是否处于该环境
   // 通过 enable() 接收此参数判断是否要显示
   boolean b = environment.acceptsProfiles(of);
   
   return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .enable(b) //配置是否启用Swagger，如果是false，在浏览器将无法访问
      .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
      .apis(RequestHandlerSelectors.basePackage("com.zssea.swagger.controller"))
       // 配置如何通过path过滤,即这里只扫描请求以/zssea开头的接口
      .paths(PathSelectors.ant("/zssea/**"))
      .build();
}
```

- 3、可以在项目中增加一个dev的配置文件查看效果！

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020102522100752.png#pic_center)

### 14.6配置API分组

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221017331.png#pic_center)

- 1、如果没有配置分组，默认是default。通过groupName()方法即可配置分组：

```java
@Bean
public Docket docket(Environment environment) {
   return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
      .groupName("hello") // 配置分组
       // 省略配置....
}
```

- 2、重启项目查看分组
- 3、如何配置多个分组？配置多个分组只需要配置多个docket即可：

```java
@Bean
public Docket docket1(){
   return new Docket(DocumentationType.SWAGGER_2).groupName("group1");
}
@Bean
public Docket docket2(){
   return new Docket(DocumentationType.SWAGGER_2).groupName("group2");
}
@Bean
public Docket docket3(){
   return new Docket(DocumentationType.SWAGGER_2).groupName("group3");
}
```

- 4、重启项目查看即可

### 14.7实体配置

- 1、新建一个实体类

```java
package com.zssea.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

//@Api(注释)
@ApiModel("用户实体")
public class User {
    @ApiModelProperty("用户名")
    public String username;
    @ApiModelProperty("密码")
    public String password;
}
```

- 2、只要这个实体在**请求接口**的返回值上（即使是泛型），都能映射到实体项中：

```java
//只要我们的接口中，返回值中存在实体类，实体类就会被扫描到swagger中
@PostMapping("/user")
public User user(){
    return new User();
}
```

- 3、重启查看测试

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221029641.png#pic_center)

注：并不是因为@ApiModel这个注解让实体显示在这里了，而是只要出现在接口方法的返回值上的实体都会显示在这里，而@ApiModel和@ApiModelProperty这两个注解只是为实体添加注释的。

@ApiModel为类添加注释

@ApiModelProperty为类属性添加注释

### 14.8常用注解

Swagger的所有注解定义在io.swagger.annotations包下

下面列一些经常用到的，未列举出来的可以另行查阅说明：

| Swagger注解                                            | 简单说明                                             |
| ------------------------------------------------------ | ---------------------------------------------------- |
| @Api(tags = “xxx模块说明”)                             | 作用在模块类上                                       |
| @ApiOperation(“xxx接口说明”)                           | 作用在接口方法上                                     |
| @ApiModel(“xxxPOJO说明”)                               | 作用在模型类上：如VO、BO                             |
| @ApiModelProperty(value = “xxx属性说明”,hidden = true) | 作用在类方法和属性上，hidden设置为true可以隐藏该属性 |
| @ApiParam(“xxx参数说明”)                               | 作用在参数、方法和字段上，类似@ApiModelProperty      |

我们也可以给请求的接口配置一些注释

```java
@ApiOperation("狂神的接口")
@PostMapping("/zssea")
@ResponseBody
public String zssea(@ApiParam("这个名字会被返回")String username){
   return username;
}
```

这样的话，可以给一些比较难理解的属性或者接口，增加一些配置信息，让人更容易阅读！

相较于传统的Postman或Curl方式测试接口，使用swagger简直就是傻瓜式操作，不需要额外说明文档(写得好本身就是文档)而且更不容易出错，只需要录入数据然后点击Execute，如果再配合自动化框架，可以说基本就不需要人为操作了。

Swagger是个优秀的工具，现在国内已经有很多的中小型互联网公司都在使用它，相较于传统的要先出Word接口文档再测试的方式，显然这样也更符合现在的快速迭代开发行情。当然了，提醒下大家在正式环境要记得关闭Swagger，一来出于安全考虑二来也可以节省运行时内存。

### 14.9拓展：其他皮肤

我们可以导入不同的包实现不同的皮肤定义：

- 1、默认的 **访问 http://localhost:8080/swagger-ui.html**

```xml
<dependency>
   <groupId>io.springfox</groupId>
   <artifactId>springfox-swagger-ui</artifactId>
   <version>2.9.2</version>
</dependency>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221043932.png#pic_center)

- 2、bootstrap-ui **访问 http://localhost:8080/doc.html**

```xml
<!-- 引入swagger-bootstrap-ui包 /doc.html-->
<dependency>
   <groupId>com.github.xiaoymin</groupId>
   <artifactId>swagger-bootstrap-ui</artifactId>
   <version>1.9.1</version>
</dependency>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221054734.png#pic_center)

- 3、Layui-ui **访问 http://localhost:8080/docs.html**

```xml
<!-- 引入swagger-ui-layer包 /docs.html-->
<dependency>
   <groupId>com.github.caspar-chen</groupId>
   <artifactId>swagger-ui-layer</artifactId>
   <version>1.1.3</version>
</dependency>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221105202.png#pic_center)

- 4、mg-ui **访问 http://localhost:8080/document.html**

```xml
<!-- 引入swagger-ui-layer包 /document.html-->
<dependency>
   <groupId>com.zyplayer</groupId>
   <artifactId>swagger-mg-ui</artifactId>
   <version>1.0.6</version>
</dependency>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221115152.png#pic_center)

## 十五、异步、邮件、定时任务

在我们的工作中，常常会用到异步处理任务，比如我们在网站上发送邮件，后台会去发送邮件，此时前台会造成响应不动，直到邮件发送完毕，响应才会成功，所以我们一般会采用多线程的方式去处理这些任务。还有一些定时任务，比如需要在每天凌晨的时候，分析一次前一天的日志信息。还有就是邮件的发送，微信的前身也是邮件服务呢？这些东西都是怎么实现的呢？其实SpringBoot都给我们提供了对应的支持，我们上手使用十分的简单，只需要开启一些注解支持，配置一些配置文件即可！

### 15.1异步任务

1、创建一个service包

2、创建一个类AsyncService

- 异步处理还是非常常用的，比如我们在网站上发送邮件，后台会去发送邮件，此时前台会造成响应不动，直到邮件发送完毕，响应才会成功，所以我们一般会采用多线程的方式去处理这些任务。
- 编写方法，假装正在处理数据，使用线程设置一些延时，模拟同步等待的情况；

```java
package com.zssea.service;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据处理中........");
    }
}
```

3、编写controller包

4、编写AsyncController类

- 我们去写一个Controller测试一下

```java
package com.zssea.controller;

import com.zssea.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @RequestMapping("/hello")
    public String hello(){
        asyncService.hello(); //休眠3秒
        return "ok";
    }
}
```

5、访问http://localhost:8080/hello进行测试，3秒后出现ok，这是同步等待的情况。

- 问题：我们如果想让用户直接得到消息，就在后台使用多线程的方式进行处理即可，但是每次都需要自己手动去编写多线程的实现的话，太麻烦了，我们只需要用一个简单的办法，在我们的方法上加一个简单的注解即可，如下：

6、给hello方法添加@Async注解；

```java
package com.zssea.service;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async 告诉Spring这是一个异步方法
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据处理中........");
    }
}
```

- SpringBoot就会自己开一个线程池，进行调用！但是要让这个注解生效，我们还需要在主程序上添加一个注解@EnableAsync ，开启异步注解功能；

```java
package com.zssea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync//开启异步注解功能
@SpringBootApplication
public class Springboot07TaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot07TaskApplication.class, args);
    }
}
```

7、重启测试，网页瞬间响应，后台代码依旧会等3秒之后再执行！

### 15.2邮件任务

邮件发送，在我们的日常开发中，也非常的多，Springboot也帮我们做了支持

- 邮件发送需要引入spring-boot-start-mail ，依赖
- SpringBoot 自动配置MailSenderAutoConfiguration
- 定义MailProperties内容，配置在application.yml中
- 自动装配JavaMailSender
- 测试邮件发送

**测试：**

1、引入pom依赖

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

看它引入的依赖，可以看到 jakarta.mail

```xml
<dependency>
   <groupId>com.sun.mail</groupId>
   <artifactId>jakarta.mail</artifactId>
   <version>1.6.4</version>
   <scope>compile</scope>
</dependency>
```

2、查看自动配置类：MailSenderAutoConfiguration

![img](https://img-blog.csdnimg.cn/img_convert/c26e019a825425359b2b2a3a8cd0c3ee.png)

这个类中存在bean，JavaMailSenderImpl

![img](https://img-blog.csdnimg.cn/img_convert/a776df9417d04d52f2b86efa1bd68900.png)

然后我们去看下配置文件

```java
@ConfigurationProperties(
   prefix = "spring.mail"
)
public class MailProperties {
   private static final Charset DEFAULT_CHARSET;
   private String host;
   private Integer port;
   private String username;
   private String password;
   private String protocol = "smtp";
   private Charset defaultEncoding;
   private Map<String, String> properties;
   private String jndiName;
}
```

3、配置文件：

```properties
spring.mail.username=24736743@qq.com
spring.mail.password=你的qq授权码
spring.mail.host=smtp.qq.com
# qq需要配置ssl
spring.mail.properties.mail.smtp.ssl.enable=true
```

获取授权码：在QQ邮箱中的设置->账户->开启pop3和smtp服务

![img](https://img-blog.csdnimg.cn/img_convert/4ed2a63c42ada3cbaab2321cadb93cf3.png)

4、Spring单元测试

```java
package com.zssea;

@SpringBootTest
class Springboot07TaskApplicationTests {    
	@Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
       //邮件设置1：一个简单的邮件
       SimpleMailMessage message = new SimpleMailMessage();
       message.setSubject("通知-明天来狂神这听课");
       message.setText("今晚7:30开会");

       message.setTo("24736743@qq.com");
       message.setFrom("24736743@qq.com");
       mailSender.send(message);
    }

    @Test
    public void contextLoads2() throws MessagingException {
       //邮件设置2：一个复杂的邮件
       MimeMessage mimeMessage = mailSender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

       helper.setSubject("通知-明天来狂神这听课");
       helper.setText("<b style='color:red'>今天 7:30来开会</b>",true);

       //发送附件
       helper.addAttachment("1.jpg",new File(""));
       helper.addAttachment("2.jpg",new File(""));

       helper.setTo("24736743@qq.com");
       helper.setFrom("24736743@qq.com");

       mailSender.send(mimeMessage);
    }
}    
```

查看邮箱，邮件接收成功！

我们只需要使用Thymeleaf进行前后端结合即可开发自己网站邮件收发功能了！

### 15.3定时任务

项目开发中经常需要执行一些定时任务，比如需要在每天凌晨的时候，分析一次前一天的日志信息，Spring为我们提供了异步执行任务调度的方式，提供了两个接口。

- TaskExecutor接口 (任务执行者)
- TaskScheduler接口 (任务调度者)

两个注解：

- @EnableScheduling （开启定时功能的注解）
- @Scheduled （定义定时功能）

**cron表达式：**

![img](https://img-blog.csdnimg.cn/img_convert/dfe9fa35779ac265d034f974b3223a84.png)

![img](https://img-blog.csdnimg.cn/img_convert/ed82c431d3aff58edce8d73659d1a139.png)

**测试步骤：**

1、创建一个ScheduledService

我们里面存在一个hello方法，他需要定时执行，怎么处理呢？

```java
@Service
public class ScheduledService {
   
   //秒   分   时     日   月   周几
   //0 * * * * MON-FRI
   //注意cron表达式的用法；
   @Scheduled(cron = "0 * * * * 0-7")
   public void hello(){
       System.out.println("hello.....");
  }
}
```

2、这里写完定时任务之后，我们需要在主程序上增加@EnableScheduling 开启定时任务功能

```java
@EnableAsync //开启异步注解功能
@EnableScheduling //开启基于注解的定时任务
@SpringBootApplication
public class SpringbootTaskApplication {

   public static void main(String[] args) {
       SpringApplication.run(SpringbootTaskApplication.class, args);
  }

}
```

3、我们来详细了解下cron表达式；

http://www.bejson.com/othertools/cron/

4、常用的表达式

```text
（1）0/2 * * * * ?   表示每2秒 执行任务
（1）0 0/2 * * * ?   表示每2分钟 执行任务
（1）0 0 2 1 * ?   表示在每月的1日的凌晨2点调整任务
（2）0 15 10 ? * MON-FRI   表示周一到周五每天上午10:15执行作业
（3）0 15 10 ? 6L 2002-2006   表示2002-2006年的每个月的最后一个星期五上午10:15执行作
（4）0 0 10,14,16 * * ?   每天上午10点，下午2点，4点
（5）0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
（6）0 0 12 ? * WED   表示每个星期三中午12点
（7）0 0 12 * * ?   每天中午12点触发
（8）0 15 10 ? * *   每天上午10:15触发
（9）0 15 10 * * ?     每天上午10:15触发
（10）0 15 10 * * ?   每天上午10:15触发
（11）0 15 10 * * ? 2005   2005年的每天上午10:15触发
（12）0 * 14 * * ?     在每天下午2点到下午2:59期间的每1分钟触发
（13）0 0/5 14 * * ?   在每天下午2点到下午2:55期间的每5分钟触发
（14）0 0/5 14,18 * * ?     在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发
（15）0 0-5 14 * * ?   在每天下午2点到下午2:05期间的每1分钟触发
（16）0 10,44 14 ? 3 WED   每年三月的星期三的下午2:10和2:44触发
（17）0 15 10 ? * MON-FRI   周一至周五的上午10:15触发
（18）0 15 10 15 * ?   每月15日上午10:15触发
（19）0 15 10 L * ?   每月最后一日的上午10:15触发
（20）0 15 10 ? * 6L   每月的最后一个星期五上午10:15触发
（21）0 15 10 ? * 6L 2002-2005   2002年至2005年的每月的最后一个星期五上午10:15触发
（22）0 15 10 ? * 6#3   每月的第三个星期五上午10:15触发
```

## 十六、分布式 Dubbo + Zookeeper + SpringBoot

### 16.1分布式理论

#### 16.1.1什么是分布式系统？

在《分布式系统原理与范型》一书中有如下定义：“分布式系统是若干独立计算机的集合，这些计算机对于用户来说就像单个相关系统”；

分布式系统是由一组通过网络进行通信、为了完成共同的任务而协调工作的计算机节点组成的系统。分布式系统的出现是为了用廉价的、普通的机器完成单个计算机无法完成的计算、存储任务。其目的是**利用更多的机器，处理更多的数据**。

分布式系统（distributed system）是建立在网络之上的软件系统。

首先需要明确的是，只有当单个节点的处理能力无法满足日益增长的计算、存储任务的时候，且硬件的提升（加内存、加磁盘、使用更好的CPU）高昂到得不偿失的时候，应用程序也不能进一步优化的时候，我们才需要考虑分布式系统。因为，分布式系统要解决的问题本身就是和单机系统一样的，而由于分布式系统多节点、通过网络通信的拓扑结构，会引入很多单机系统没有的问题，为了解决这些问题又会引入更多的机制、协议，带来更多的问题。

#### 16.1.2Dubbo文档

随着互联网的发展，网站应用的规模不断扩大，常规的垂直应用架构已无法应对，分布式服务架构以及流动计算架构势在必行，急需**一个治理系统**确保架构有条不紊的演进。

在Dubbo的官网文档有这样一张图

![img](https://img-blog.csdnimg.cn/img_convert/90d8839b7de5d414d00d8d18d4fc38fe.png)

#### 16.1.3单一应用架构

当网站流量很小时，只需一个应用，将所有功能都部署在一起，以减少部署节点和成本。此时，用于简化增删改查工作量的数据访问框架(ORM)是关键。

![img](https://img-blog.csdnimg.cn/img_convert/e0f7ef8cd79e4dceffdc1941f18c706c.png)

适用于小型网站，小型管理系统，将所有功能都部署到一个功能里，简单易用。

**缺点：**

1、性能扩展比较难

2、协同开发问题

3、不利于升级维护

#### 16.1.4垂直应用架构

当访问量逐渐增大，单一应用增加机器带来的加速度越来越小，将应用拆成互不相干的几个应用，以提升效率。此时，用于加速前端页面开发的Web框架(MVC)是关键。

![img](https://img-blog.csdnimg.cn/img_convert/bc39a5e6921475cd13ca10488b42b144.png)

通过切分业务来实现各个模块独立部署，降低了维护和部署的难度，团队各司其职更易管理，性能扩展也更方便，更有针对性。

缺点：公用模块无法重复利用，开发性的浪费

#### 16.1.5分布式服务架构

当垂直应用越来越多，应用之间交互不可避免，将核心业务抽取出来，作为独立的服务，逐渐形成稳定的服务中心，使前端应用能更快速的响应多变的市场需求。此时，用于提高业务复用及整合的**分布式服务框架(RPC)**是关键。

![img](https://img-blog.csdnimg.cn/img_convert/5a4e26c4d14e425c8645ec708738bb37.png)

#### 16.1.6流动计算架构

当服务越来越多，容量的评估，小服务资源的浪费等问题逐渐显现，此时需增加一个调度中心基于访问压力实时管理集群容量，提高集群利用率。此时，用于**提高机器利用率的资源调度和治理中心**(SOA)[ Service Oriented Architecture]是关键。

![img](https://img-blog.csdnimg.cn/img_convert/b0bb1896f8e726fbd2e0f4e879b6db41.png)

### 16.2什么是RPC

RPC【Remote Procedure Call】是指远程过程调用，是一种进程间通信方式，他是一种技术的思想，而不是规范。它允许程序调用另一个地址空间（通常是共享网络的另一台机器上）的过程或函数，而不用程序员显式编码这个远程调用的细节。即程序员无论是调用本地的还是远程的函数，本质上编写的调用代码基本相同。

也就是说两台服务器A，B，一个应用部署在A服务器上，想要调用B服务器上应用提供的函数/方法，由于不在一个内存空间，不能直接调用，需要通过网络来表达调用的语义和传达调用的数据。为什么要用RPC呢？就是无法在一个进程内，甚至一个计算机内通过本地调用的方式完成的需求，比如不同的系统间的通讯，甚至不同的组织间的通讯，由于计算能力需要横向扩展，需要在多台机器组成的集群上部署应用。RPC就是要像调用本地的函数一样去调远程函数；

推荐阅读文章：https://www.jianshu.com/p/2accc2840a1b

#### 16.2.1RPC基本原理

![img](https://img-blog.csdnimg.cn/img_convert/b5432ce40a1b313f4659dcd51590e32a.png)

#### 16.2.2步骤解析

![img](https://img-blog.csdnimg.cn/img_convert/500733b9451c866b0e896645b55336af.png)

- RPC两个核心模块：通讯，序列化：就是为了对象的传输。
- RPC要解决的两个问题：
  - 解决分布式系统中，服务之间的调用问题。
  - 远程调用时，要能够像本地调用一样方便，让调用者感知不到远程调用的逻辑。

### 16.3测试环境搭建

#### 16.3.1什么是Dubbo

- Apache Dubbo 是一款高性能、轻量级的开源Java RPC框架（一个很牛的通信框架，但是dubbo或者说分布式最大的问题是网络的稳定性），它提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。
- Dubbo 采用全 Spring 配置方式，透明化接入应用，对应用没有任何 API 侵入，只需用 Spring 加载 Dubbo 的配置即可，Dubbo 基于 [Spring 的 Schema 扩展](https://docs.spring.io/spring/docs/4.2.x/spring-framework-reference/html/xsd-configuration.html) 进行加载。
- dubbo官网 http://dubbo.apache.org/zh-cn/index.html

1.了解Dubbo的特性

2.查看官方文档

- **dubbo基本概念**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221333931.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

- **服务提供者**（Provider）：暴露服务的服务提供方，服务提供者在启动时，向注册中心注册自己提供的服务。
- **服务消费者**（Consumer）：调用远程服务的服务消费方，服务消费者在启动时，向注册中心订阅自己所需的服务，服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
- **注册中心**（Registry）：注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者
- **监控中心**（Monitor）：服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心
- **调用关系说明**
  - 服务容器负责启动，加载，运行服务提供者。
  - 服务提供者在启动时，向注册中心注册自己提供的服务。
  - 服务消费者在启动时，向注册中心订阅自己所需的服务。
  - 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
  - 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
  - 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

#### 16.3.2Dubbo环境搭建

- 点进dubbo官方文档，推荐我们使用Zookeeper注册中心（可在Dubbo官网文档中查看注册中心参考手册）
- 什么是zookeeper呢？可以查看官方文档

#### 16.3.3 Window下安装zookeeper

1、下载zookeeper ：http://archive.apache.org/dist/zookeeper/zookeeper-3.4.14/

2、运行/bin/zkServer.cmd ，初次运行会报错，没有zoo.cfg配置文件；

可能遇到问题：闪退 !

解决方案：编辑zkServer.cmd文件末尾添加pause 。这样运行出错就不会退出，会提示错误信息，方便找到原因。

![img](https://img-blog.csdnimg.cn/img_convert/960a8ee26a9d6040e742f574023dd510.png)

![img](https://img-blog.csdnimg.cn/img_convert/eeef1fd2feb693721b9fb682ce7b091f.png)

3、修改zoo.cfg配置文件

将conf文件夹下面的zoo_sample.cfg复制一份改名为zoo.cfg即可。

注意几个重要位置：

dataDir=./ 临时数据存储的目录（可写相对路径）

clientPort=2181 zookeeper的端口号

修改完成后再次启动zookeeper

![img](https://img-blog.csdnimg.cn/img_convert/0c27e87df21d5201a9f8c57fd6ea1ae2.png)

4、使用zkCli.cmd测试

ls /：列出zookeeper根下保存的所有节点

```
[zk: 127.0.0.1:2181(CONNECTED) 4] ls /
[zookeeper]
```

create –e /kuangshen 123：创建一个kuangshen节点，值为123

![img](https://img-blog.csdnimg.cn/img_convert/22075364e903a47de8d86499d32cdd47.png)

get /kuangshen：获取/kuangshen节点的值

![img](https://img-blog.csdnimg.cn/img_convert/30b931d54c89d7bee80bffa26e653799.png)

我们再来查看一下节点

![img](https://img-blog.csdnimg.cn/img_convert/f4187dede1283cd5f4f737c36f954a09.png)

#### 16.3.4window下安装dubbo-admin

dubbo本身并不是一个服务软件。它其实就是一个jar包，能够帮你的java程序连接到zookeeper，并利用zookeeper消费、提供服务。

但是为了让用户更好的管理监控众多的dubbo服务，官方提供了一个可视化的监控程序dubbo-admin（就是一个网站，是一个监控管理后台，查看我们注册了哪些服务，哪些服务被消费了），不过这个监控即使不装也不影响使用。

我们这里来安装一下：

**1、下载dubbo-admin**

地址 ：https://github.com/apache/dubbo-admin/tree/master

**2、解压进入目录**

修改 dubbo-admin\src\main\resources \application.properties 指定zookeeper地址

```properties
server.port=7001
spring.velocity.cache=false
spring.velocity.charset=UTF-8
spring.velocity.layout-url=/templates/default.vm
spring.messages.fallback-to-system-locale=false
spring.messages.basename=i18n/message
spring.root.password=root
spring.guest.password=guest

dubbo.registry.address=zookeeper://127.0.0.1:2181
```

**3、在项目目录下**打包dubbo-admin

```
mvn clean package -Dmaven.test.skip=true
```

**第一次打包的过程有点慢，需要耐心等待！直到成功！**

![img](https://img-blog.csdnimg.cn/img_convert/160b132b2149f252fe741ac49555350d.png)

4、执行 dubbo-admin\target 下的dubbo-admin-0.0.1-SNAPSHOT.jar

```
java -jar dubbo-admin-0.0.1-SNAPSHOT.jar
```

【注意：zookeeper的服务一定要打开！】

执行完毕，我们去访问一下 http://localhost:7001/ ， 这时候我们需要输入登录账户和密码，我们都是默认的root-root；

登录成功后，查看界面

![img](https://img-blog.csdnimg.cn/img_convert/fe8eaf5354b68eee2a6485f8aebe65c1.png)

安装完成！

### 16.4SpringBoot + Dubbo + zookeeper

#### 16.4.1框架搭建

**1. 启动zookeeper ！**

**2. IDEA创建一个空项目；**

**3.创建一个模块，实现服务提供者：provider， 选择web依赖即可**

**4.项目创建完毕，我们写一个服务，比如卖票的服务；**

编写接口

```java
package com.zssea.service;

public interface TicketService {
    public String getTicket();
}
```

编写实现类

```java
package com.zssea.service;

public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "provider提供的买票服务";
    }
}
```

**5.创建一个模块，实现服务消费者：consumer ， 选择web依赖即可**

**6.项目创建完毕，我们写一个服务，比如用户的服务；**

编写service

```java
package com.zssea.service;

public class UserService {
   //我们需要去拿去注册中心的服务
}
```

**需求：现在我们的用户想使用买票的服务，这要怎么弄呢 ？**

#### 16.4.2服务提供者

**1、将服务提供者注册到注册中心，我们需要整合Dubbo和zookeeper，所以需要导包**

**我们从dubbo官网进入github，看下方的帮助文档，找到dubbo-springboot，找到依赖包**

```xml
<!-- Dubbo Spring Boot Starter -->
<dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-spring-boot-starter</artifactId>
   <version>2.7.3</version>
</dependency>   
```

**zookeeper的包我们去maven仓库下载，zkclient；**

```xml
<!-- https://mvnrepository.com/artifact/com.github.sgroschupf/zkclient -->
<dependency>
   <groupId>com.github.sgroschupf</groupId>
   <artifactId>zkclient</artifactId>
   <version>0.1</version>
</dependency>
```

**【新版的坑】zookeeper及其依赖包，解决日志冲突，还需要剔除日志依赖；**

```xml
<!-- 引入zookeeper -->
<dependency>
   <groupId>org.apache.curator</groupId>
   <artifactId>curator-framework</artifactId>
   <version>2.12.0</version>
</dependency>
<dependency>
   <groupId>org.apache.curator</groupId>
   <artifactId>curator-recipes</artifactId>
   <version>2.12.0</version>
</dependency>
<dependency>
   <groupId>org.apache.zookeeper</groupId>
   <artifactId>zookeeper</artifactId>
   <version>3.4.14</version>
   <!--排除这个slf4j-log4j12-->
   <exclusions>
       <exclusion>
           <groupId>org.slf4j</groupId>
           <artifactId>slf4j-log4j12</artifactId>
       </exclusion>
   </exclusions>
</dependency>
```

**2、在springboot配置文件中配置dubbo相关属性！**

```properties
server.port=8082

#当前应用名字
dubbo.application.name=provider
#注册中心地址
dubbo.registry.address=zookeeper://127.0.0.1:2181
#扫描指定包下服务
dubbo.scan.base-packages=com.zssea.service
```

**3、在service的实现类中配置服务注解，发布服务！注意导包问题**

```java
package com.zssea.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;


@Service //这个@Service注解是dubbo下的（不要使用springframework包下的），它表明可以被扫描到，在项目一启动时就自动加载到注册中心，（把服务发布出去）
@Component  //这个注解是springframework包下的，放在容器中，使用了Dubbo后尽量不要用springframework包下的@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "provider提供的买票服务";
    }
}
```

**逻辑理解 ：应用启动起来，dubbo就会扫描指定的包下带有@component注解的服务，将它发布在指定的注册中心中！**

#### 16.4.3服务消费者

**1、导入依赖，和之前的依赖一样；**

```xml
<!--dubbo-->
<!-- Dubbo Spring Boot Starter -->
<dependency>
   <groupId>org.apache.dubbo</groupId>
   <artifactId>dubbo-spring-boot-starter</artifactId>
   <version>2.7.3</version>
</dependency>
<!--zookeeper-->
<!-- https://mvnrepository.com/artifact/com.github.sgroschupf/zkclient -->
<dependency>
   <groupId>com.github.sgroschupf</groupId>
   <artifactId>zkclient</artifactId>
   <version>0.1</version>
</dependency>
<!-- 引入zookeeper -->
<dependency>
   <groupId>org.apache.curator</groupId>
   <artifactId>curator-framework</artifactId>
   <version>2.12.0</version>
</dependency>
<dependency>
   <groupId>org.apache.curator</groupId>
   <artifactId>curator-recipes</artifactId>
   <version>2.12.0</version>
</dependency>
<dependency>
   <groupId>org.apache.zookeeper</groupId>
   <artifactId>zookeeper</artifactId>
   <version>3.4.14</version>
   <!--排除这个slf4j-log4j12-->
   <exclusions>
       <exclusion>
           <groupId>org.slf4j</groupId>
           <artifactId>slf4j-log4j12</artifactId>
       </exclusion>
   </exclusions>
</dependency>
```

2、**配置参数**

```properties
server.port=8083

#消费者要去注册中心拿服务
#当前应用名字
dubbo.application.name=consumer
#注册中心地址
dubbo.registry.address=zookeeper://127.0.0.1:2181
```

**3. 本来正常步骤是需要将服务提供者的接口打包，然后用pom文件导入，我们这里使用简单的方式，直接将服务的接口拿过来，路径必须保证正确，即和服务提供者相同；**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221428733.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**4. 完善消费者的服务类**

```java
package com.zssea.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service //注入到容器中，这是springframework包下的
public class UserService {

   @Reference //远程引用指定的服务，他会按照全类名进行匹配，看谁给注册中心注册了这个全类名
   TicketService ticketService;

   public void bugTicket(){
       String ticket = ticketService.getTicket();
       System.out.println("在注册中心拿到了=》"+ticket);
  }

}
```

**5. 测试类编写；**

```java
package com.zssea;
import com.zssea.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.buyTicket();
    }
}
```

#### 16.4.4启动测试

**1. 开启zookeeper**

**2. 打开dubbo-admin实现监控【可以不用做】**

**3. 开启服务者**

**4. 消费者消费测试，结果：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20201025221443868.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

**ok , 这就是SpingBoot + dubbo + zookeeper实现分布式开发的应用，其实就是一个服务拆分的思想；**

#### 16.4.5过程总结

步骤：

- 前提：zookeeper服务已开启
- 提供者提供服务：
  - 导入依赖
  - 在application.properties中配置注册中心的地址，以及服务发现名，和要扫描的包
  - 在想要被注册的服务上增加一个注解：@Service （这个@Service注解是dubbo下的，它表明可以被扫描到，在项目一启动时就自动加载到注册中心）
- 消费者如何消费
  - 导入依赖
  - 在application.properties中配置注册中心的地址，以及服务发现名
  - 从远程注入服务，在具体的类中用@Reference注解来远程引用指定的服务，正常步骤是需要将服务提供者的接口打包，然后用pom文件导入，我们这里使用简单的方式，直接将服务的接口拿过来，路径必须保证正确，即和服务提供者相同