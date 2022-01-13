什么是微服务架构：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597213385700.png)

SpringCloud 是微服务一站式服务解决方案，微服务全家桶。它是微服务开发的主流技术栈。它采用了名称，而非数字版本号。

springCloud 和 springCloud Alibaba 目前是最主流的微服务框架组合。

版本选择：

> 选用 springboot 和 springCloud 版本有约束，不按照它的约束会有冲突。
>

本次学习的各种软件的版本：

> cloud  Hoxton.SR1
> boot    2.2.2. RELEASE
> cloud alibaba  2.1.0.RELEASE
> Java  Java8
> Maven 3.5及以上
> Mysql  5.7及以上

## Cloud简介

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597213783265.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/63967rjdy290j.png)

参考资料，尽量去官网

> https://cloud.spring.io/spring-cloud-static/Hoxton.SR1/reference/htmlsingle/

# 工程建造

写一个下图的Hello World 

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597225177689.png)

构建父工程，后面的项目模块都在此工程中：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597214225785.png)

设置编码：Settings -> File Encodings

注解激活：![](https://gitee.com/zssea/picture-bed/raw/master/img/1597214602636.png)

Java版本确定：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597214699619.png)

## 父工程pom配置

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.dkf.cloud</groupId>
  <artifactId>cloud2020</artifactId>
  <version>1.0-SNAPSHOT</version>
    <!-- 第一步 -->
  <packaging>pom</packaging>

  <!-- 统一管理 jar 包版本 -->
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <log4j.version>1.2.17</log4j.version>
    <lombok.version>1.16.18</lombok.version>
    <mysql.version>5.1.47</mysql.version>
    <druid.version>1.1.16</druid.version>
    <mybatis.spring.boot.version>1.3.0</mybatis.spring.boot.version>
  </properties>

  <!-- 子块基础之后，提供作用：锁定版本 + 子module不用写 groupId 和 version -->
  <dependencyManagement>
    <dependencies>
        <dependency>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.0.0</version>
        </dependency>

        <!-- 下面三个基本是微服务架构的标配 -->
        <!--spring boot 2.2.2-->
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-dependencies</artifactId>
          <version>2.2.2.RELEASE</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
        <!--spring cloud Hoxton.SR1-->
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-dependencies</artifactId>
          <version>Hoxton.SR1</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
        <!--spring cloud 阿里巴巴-->
        <dependency>
          <groupId>com.alibaba.cloud</groupId>
          <artifactId>spring-cloud-alibaba-dependencies</artifactId>
          <version>2.1.0.RELEASE</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>

        <!--mysql-->
        <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <version>${mysql.version}</version>
          <scope>runtime</scope>
        </dependency>
        <!-- druid-->
        <dependency>
          <groupId>com.alibaba</groupId>
          <artifactId>druid</artifactId>
          <version>${druid.version}</version>
        </dependency>
        <dependency>
          <groupId>org.mybatis.spring.boot</groupId>
          <artifactId>mybatis-spring-boot-starter</artifactId>
          <version>${mybatis.spring.boot.version}</version>
        </dependency>
        <!--junit-->
        <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>${junit.version}</version>
        </dependency>
        <!--log4j-->
        <dependency>
          <groupId>log4j</groupId>
          <artifactId>log4j</artifactId>
          <version>${log4j.version}</version>
        </dependency>
    </dependencies>
  </dependencyManagement>

  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <fork>true</fork>
          <addResources>true</addResources>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
```

上面配置的解释：

> 首先要加  <packaging>pom</packaging>  这个。
>
> 聚合版本依赖，dependencyManagement 只声明依赖，并不实现引入，所以子项目还需要写要引入的依赖。
>
> 如果不在子项目中声明依赖，不会从父项目中继承下来的;只有在子项目中写了该依赖项,并且没有指定具体版本,
> 才会从父项目中继承该项,并且version和scope都读取自父pom;
> 如果子项目中指定了版本号,那么会使用子项目中指定的jar版本。

> dependencyManagement
> Maven使用dependencyManagement元素来提供了一种管理依赖版本号的方式。
> 通常会在一个组织或者 项目的最顶层的父POM中看到dependencyManagement 元素。
> 使用pom.xml中的dependencyManagement元素能让所有在子项目中引用一个依赖而不用显式的列出版本号。
> Maven会沿着父子层次向上走，直到找到一个拥有dependencyManagement 元素的项目,然后它就会使用这个
> dependencyManagement元頑中批定的版本号。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597215639355.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597215666979.png)

## 第一个微服务架构

> 1. 建模块 module
> 2. 改 pom
> 3. 写yml
> 4. 主启动
> 5. 业务类

### 提供者

cloud-provider-payment8001 子工程的pom文件：

> 这里面的 lombok 这个包，引入以后，实体类不用再写set 和 get
>
> 可以如下写实体类：
>
> ```java
> import lombok.AllArgsConstructor;
> import lombok.Data;
> import lombok.NoArgsConstructor;
> import java.io.Serializable;
> 
> @Data
> @AllArgsConstructor
> @NoArgsConstructor
> public class Payment implements Serializable {
>     private Integer id;
>     private String serial;
> }
> ```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2020</artifactId>
        <groupId>com.dkf.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-provider-payment8001</artifactId>
    <dependencies>
        <!--eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.atguigu.springcloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

cloud-provider-payment8001 子工程的yml文件：

```yml
server:
  port: 8001

spring:
  application:
    name: cloud-provider-payment8001
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/cloud2020?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.dkf.springcloud.entities  # 所有Entity 别名类所在包
```

cloud-provider-payment8001 子工程的主启动类：

```java
package com.dkf.springcloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentMain8001 {
    public static void main(String[] args){
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
```

下面的常规操作：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597217986370.png)

```sql
CREATE TABLE `cloud2020`.`payment`( 
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `serial` VARCHAR(200), 
    PRIMARY KEY (`id`) 
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci AUTO_INCREMENT=1; 
```

mybatis的mapper文件和service层代码不写了，下面记录一个特殊的Entity类，和Controller

CommonResult:

```java
package com.dkf.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 如果前后端分离，这个是提供给前端信息和数据的类
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;
    private String messgae;
    private T data;

    /**
     * 查询为空的时候使用的构造器
     * @param code
     * @param messgae
     */
    public CommonResult(Integer code, String messgae){
        this(code, messgae, null);
    }
}
```

Controller：

```java
package com.dkf.springcloud.controller;


import com.dkf.springcloud.entities.CommonResult;
import com.dkf.springcloud.entities.Payment;
import com.dkf.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController   //必须是这个注解，因为是模拟前后端分离的restful风格的请求，要求每个方法返回 json
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    //	    注意这里的 @RequestBody  是必须要写的，虽然 MVC可以自动封装参数成为对象，
    //      但是当消费者项目调用，它传参是 payment 整个实例对象传过来的， 即Json数据，因此需要写这个注解
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("****插入结果：" + result);
        if(result > 0){
            return new CommonResult(200, "插入数据库成功", result);
        }
        return new CommonResult(444, "插入数据库失败", null);
    }

    @GetMapping(value = "/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("****查询结果：" + result);
        if(result != null){
            return new CommonResult(200, "查询成功", result);
        }
        return new CommonResult(444, "没有对应id的记录", null);
    }
}
```

不但编译有个别地方会报错，启动也会报错，但是测试两个接口都是没问题的，推测启动报错是因为引入了下面才会引入的jar包，目前不影响。

### 热部署配置 

1. 具体模块里添加Jar包到工程中，上面的pom文件已经添加上了

```xml
<dependency>    
    <groupId>org.springframework.boot</groupId>    
    <artifactId>spring-boot-devtools</artifactId>    
    <scope>runtime</scope>    
    <optional>true</optional>
</dependency>
```

2. 添加plus到父工程的pom文件中：上i按也已经添加好了

```xml
<build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
        <configuration>
          <fork>true</fork>
          <addResources>true</addResources>
        </configuration>
      </plugin>
    </plugins>
  </build>
```

3. ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597222225568.png)

4. shift + ctrl + alt + / 四个按键一块按，选择Reg项：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597222341651.png)

### 消费者

> 消费者现在只模拟调用提供者的Controller方法，没有持久层配置，只有Controller和实体类
>
> 当然也要配置主启动类和启动端口

pom文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2020</artifactId>
        <groupId>com.dkf.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-customer-order80</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <!--<dependency>&lt;!&ndash; 引入自己定义的api通用包，可以使用Payment支付Entity &ndash;&gt;-->
            <!--<groupId>com.atguigu.springcloud</groupId>-->
            <!--<artifactId>cloud-api-commons</artifactId>-->
            <!--<version>${project.version}</version>-->
        <!--</dependency>-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

把CommonResult 和 Payment 两个 实体类也创建出来

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597224961560.png)

ApplicationContextConfig 内容：

```java
package com.dkf.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

Controller ：

```java
package com.dkf.springcloud.controller;

import com.dkf.springcloud.entities.CommonResult;
import com.dkf.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    //远程调用的 地址
    public static final String PAYMENY_URL = "http://localhost:8001";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("customer/payment/create")
    public CommonResult<Payment> create (Payment payment){
        /**
        param1 请求地址，param2 请求参数， param3 返回类型
        */
        return restTemplate.postForObject(PAYMENY_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("customer/payment/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        return restTemplate.getForObject(PAYMENY_URL + "/payment/" + id, CommonResult.class);
    }
}
```

如果 runDashboard 控制台没有出来，右上角搜索 即可

### 工程重构

> 上面 两个子项目，有多次重复的 导入 jar，和重复的 Entity 实体类。可以把 多余的部分，加入到一个独立的模块中，将这个模块打包，并提供给需要使用的 module

1. 新建一个 cloud-dkf-commons 子模块
2. 将 entities 包里面的实体类放到这个子模块中，也将 pom 文件中，重复导入的 jar包放到这个新建的 模块的 pom 文件中。如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>cloud2020</artifactId>
        <groupId>com.dkf.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-api-commons</artifactId>
    
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- 这个是新添加的，之前没用到，后面会用到 -->
    <dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>5.1.0</version>
    </dependency>
    <!-- 
 		关于这个hutool 是个功能强大的工具包，官网：https://hutool.cn/
	-->
</dependencies>

</project>
```

将此项目打包 install 到 maven仓库。

3. 将 提供者 和 消费者 两个项目中的 entities 包删除，并删除掉加入到 cloud-api-commons 模块的 依赖配置。
4. 将 打包到 maven 仓库的 cloud-api-commons 模块，引入到 提供者 和 消费者的 pom 文件中，如下所示

```xml
<dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
    <groupId>com.dkf.cloud</groupId>
    <artifactId>cloud-api-commons</artifactId>
    <version>${project.version}</version>
</dependency>
```

完成！

# 服务注册中心

> 如果是上面只有两个微服务，通过 RestTemplate ，是可以相互调用的，但是当微服务项目的数量增大，就需要服务注册中心。目前没有学习服务调用相关技术，使用 SpringCloud 自带的 RestTemplate 来实现RPC

## Eureka

> 官方停更不停用，以后可能用的越来越少。

### 概念和理论

> 它是用来服务治理，以及服务注册和发现，服务注册如下图：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597291856582.png)

Eureka采用了CS的设计架构，Eureka Server作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务,使用Eureka的客户端连接到Eureka Servet并维持心]
跳连接。这样系统的维护人员就可以通过Eureka Server来监控系统中各个微服务是否正常运行。
这点和zookeeper很相似
在服务注册与发现中，有一个注册中心。 当服务器启动的时候，会把当前自己服务器的信息比如服务地址通讯地址等以别名方式注册到注册中心上。另-方(消费者|服务提供
者)，以该别名的方式去注册中心上获取到实际的服务通讯地址,然后再实现本地RPC调用RPC远程调用框架核心设计思想:在于注册中心，因为使用注册中心管理每个服务与
服务之间的一个依赖关系(服务治理概念)。在任何rpc远程框架中，都会有一个注册中心(存放服务地址相关信息 (接口地址))

Eureka包含两个组件: Eureka Server和Eureka Client
Eureka Server提供服务注册服务
各个微服务节点通过配置启动后，会在EurekaServer中进行注册,这样EurekaServer中的服务注册表中将会存储所有可用服务节点的
信息，服务节点的信息可以在界面中直观看到。
EurekaClient通过注册中心进行访问
是一个Java客户端,用于简化Eureka Server的交互，客户端同时也具备一个内置的、 使用轮询(round-robin)负载算法的负载均衡器
。在应用启动后,将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到某个节点的心
跳，EurekaServer将 会从服务注册表中把这个服务节点移除(默认90秒)

版本说明：

```xml
1.X和2.X的对比说明
以前的老版本(当前使用2018)
<dependency>
<groupId>org. springfr amework . cloud</ groupId>
<artifactId>spring- cloud-starter-eureka</ artifactId>
</dependency>
现在新版本(当前使用2020.2)
<dependency>
<groupId>org. springframework. cloud</ groupId>
<artifactId>spring-cloud- starter- netflix- eureka-serpver</artifactId>
</dependency>
```

### Server模块

> server 模块使用 7001端口，下面是pom文件需要的依赖：
>
> ```xml
> 	<artifactId>cloud-eureka-server7001</artifactId>
> 
>     <dependencies>
>         <dependency>
>             <groupId>org.springframework.cloud</groupId>
>             <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
>         </dependency>
>         <dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter-web</artifactId>
>         </dependency>
>         <dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter-actuator</artifactId>
>         </dependency>
>         <dependency>
>             <groupId>org.mybatis.spring.boot</groupId>
>             <artifactId>mybatis-spring-boot-starter</artifactId>
>         </dependency>
>         <dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-devtools</artifactId>
>             <scope>runtime</scope>
>             <optional>true</optional>
>         </dependency>
>         <dependency>
>             <groupId>org.springframework.boot</groupId>
>             <artifactId>spring-boot-starter-test</artifactId>
>             <scope>test</scope>
>         </dependency>
>         <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
>             <groupId>com.dkf.cloud</groupId>
>             <artifactId>cloud-api-commons</artifactId>
>             <version>${project.version}</version>
>         </dependency>
>     </dependencies>
> ```

> 下面配置 yml 文件：
>
> ```yml
> server:
>     port: 7001
> 
> 
>   eureka:
>      instance:
>     hostname: eureka7001.com #eureka服务端的实例名称
>     client:
>        register-with-eureka: false     #false表示不向注册中心注册自己。
>        fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
>        service-url:
>          # 设置与 Eureka Server 交互的地址，查询服务 和 注册服务都依赖这个地址
>    ```
>    
>    最后写主启动类，如果启动报错，说没有配置 DataSource ，就在 主启动类的注解加上 这样的配置：
> 
>```java
> // exclude ：启动时不启用 DataSource的自动配置检查
>@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
> @EnableEurekaServer   // 表示它是服务注册中心
> public class EurekaServerMain7001 {
>  public static void main(String[] args){
>      SpringApplication.run(EurekaServerMain7001.class, args);
>  }
>    }
>    ```

启动测试，访问 7001 端口

### 提供者

> 这里的提供者，还是使用 上面的 cloud-provider-payment8001 模块，做如下修改：

1. 在 pom 文件的基础上引入 eureka 的client包，pom 的全部依赖如下所示：

```xml
	<artifactId>cloud-provider-payment8001</artifactId>
    <dependencies>
        <!--eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

2. 主启动类 加上注解 ： @EnableEurekaClient
3. yml 文件添加关于 Eureka 的配置：

```yml
eureka:
  client:
	# 注册进 Eureka 的服务中心
    register-with-eureka: true
    # 检索 服务中心 的其它服务
    fetch-registry: true
    service-url:
      # 设置与 Eureka Server 交互的地址
      defaultZone: http://localhost:7001/eureka/
```

应用名称：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597294710656.png)

### 消费者

> 这里的消费者 也是上面 的 cloud-customer-order80 模块

1. 修改 pom 文件，加入Eureka 的有关依赖， 全部 pom 依赖如下：

```xml
	<artifactId>cloud-customer-order80</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

2. 主启动类 加上注解 ： @EnableEurekaClient
3. yml 文件必须添加的内容：

```yml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://localhost:7001/eureka/
spring:
  application:
    name: cloud-order-service
```

### Eureka 集群

> Eureka 集群的原理，就是 相互注册，互相守望
>
> 模拟多个 Eureka Server 在不同机器上 ： 进入C:\Windows\System32\drivers\etc\hosts  添加如下：
>
> 127.0.0.1 eureka7001.com
>
> 127.0.0.1 eureka 7002.com

现在创建 cloud-eureka-server7002 ，也就是第二个 Eureka 服务注册中心，pom 文件和 主启动类，与第一个Server一致。

现在修改这两个 Server 的 yml 配置：

7001 端口的Server yml文件：

```yml
server:
  port: 7001

eureka:
  instance:
    hostname: eureka7001.com  # eureka 服务器的实例地址

  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
    ## 一定要注意这里的地址，这是搭建集群的关键
      defaultZone: http://eureka7002.com:7002/eureka/
```

7002 端口的Server yml文件：

```yml
server:
  port: 7002

eureka:
  instance:
    hostname: eureka7002.com  # eureka 服务器的实例地址

  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
    ## 一定要注意这里的地址 这是搭建集群的关键
      defaultZone: http://eureka7001.com:7001/eureka/
```

> eureka.instance.hostname 才是启动以后 本 Server 的注册地址，而 service-url  是 map 类型，只要保证 key:value 格式就行，它代表 本Server 指向了那些 其它Server 。利用这个，就可以实现Eureka Server 相互之间的注册，从而实现集群的搭建。

将 提供者 和 消费者 注册进两个Eureka Server 中，下面是 消费者和提供者的 yml 文件关于Eureka的配置：

```yml
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
```

> 从这里可以看出，也可以使用列表形式进行Server之间的关联注册。

### 提供者集群

> 为提供者，即 cloud-provider-payment8001 模块创建集群，新建模块为 cloud-provider-payment8002
>
> 最终实现：
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597301105015.png)
>
> 注意在 Controller 返回不同的消息，从而区分者两个提供者的工作状态。

其余配置都一致，需要配置集群的配置如下：

配置区别：只要保证消费者项目对服务注册中心提供的名称一致，即完成集群。

```yml
server:
  port: 8001  # 端口号不一样

spring:
  application:
    name: cloud-provider-service  # 这次重点是这里，两个要写的一样，这是这个集群的关键
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: org.gjt.mm.mysql.Driver
    url: jdbc:mysql://localhost:3306/cloud2020?useUnicode=true&characterEncoding=utf-8&useSSL=false
    username: root
    password: 123456

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.dkf.springcloud.entities  

eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
```

消费者的配置

> 就是消费者如何访问 由这两个提供者组成的集群？

Eureka Server 上的提供者的服务名称如下：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597299306196.png)

```java
@RestController
@Slf4j
public class OrderController {
							    // 重点是这里，改成 提供者在Eureka 上的名称，而且无需写端口号	
    public static final String PAYMENY_URL = "http://CLOUD-PROVIDER-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("customer/payment/create")
    public CommonResult<Payment> create (Payment payment){
        return restTemplate.postForObject(PAYMENY_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("customer/payment/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id")Long id){
        return restTemplate.getForObject(PAYMENY_URL + "/payment/" + id, CommonResult.class);
    }

}
```

还有，消费者里面对RestTemplate配置的config文件，需要更改成如下：（就是加一个注解 @LoadBalanced）

```java
package com.dkf.springcloud.config;


import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced  //这个注解，就赋予了RestTemplate 负载均衡的能力
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

测试，完成！

### actuator信息配置

修改 在Eureka 注册中心显示的 主机名：

```yaml
eureka :
  client:
#表示是否将自己注册进EurekaServer默认为true.
    register-with-eureka: true
#是否从Eurekaserver抓取已有的注册信息，默认为true。单节点无所谓，集群必
    fetchRegistry: true
    service-url :
#defaul tZone: http://localhost: 7001/eureka
    defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
instance:
  instance- id: payment8002
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597301299700.png)

显示微服务所在 的主机地址：

```yaml
eureka :
  client:
#表示是否将自己注册进EurekaServer默认为true.
    register-with-eureka: true
#是否从Eurekaserver抓取已有的注册信息，默认为true。单节点无所谓，集群必
    fetchRegistry: true
    service-url :
#defaul tZone: http://localhost: 7001/eureka
    defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
instance:
  instance-id: payment8002
  prefer-ip-address: true
#访问路径可以显示IP地址|
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597301396971.png)

### 服务发现Discovery

> 对于注册进eureka里面的微服务，可以通过服务发现来获得该服务的信息

1. 在主启动类上添加注解：@EnableDiscoveryClient
2. 在 Controller 里面打印信息：

```java
   @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/payment/discovery")
    public Object discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element: "+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {                                                  log.info(instance.getServiceId()+"\t"+                                                       instance.getHost()+"\t"+
          instance.getPort()+"\t"+                                                                     instance.getUri());
        }
        return this.discoveryClient;
    }
```

### Eureka 自我保护机制

一句话:某时刻某一个微服务不可用了，Eureka不会立刻清理， 依旧会对该微服务的信息进行保存+
属于CAP里面的AP分支

概述
保护模式主要用于一组客户端和Eureka Server之间存在网络分区场景下的保护。一旦进入保护模式,
Eureka Server将会尝试保护其服务注册表中的信息，不再删除服务注册表中的数据，也就是不会注销任何微服务。
如果在Eureka Server的首页看到以下这段提示，则说明Eureka进入 了保护模式:

```
EMERGENCY! EUREKA MAY BE INCORRECTLY CLAIMING INSTANCES ARE UP WHEN THEY'RE NOT.
RENEWALS ARE LESSER THAN THRESHOLD AND HENCE THE INSTANCES ARE NOT BEING EXPIRED JUST TO BE SAFE
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597302449686.png)

禁止自我保护:

> 在 Eureka Server 的模块中的 yml 文件进行配置：

```yaml
eureka :
  instance:
    hostname: eureka7001.com #eureka服务端的实例名称
  client:
    register-with-eureka: false
    #false表示不向注册中心注册自
    fetch-registry: false
    #false.表示自己端就是注册中心
    service-url:
      #集群指向其它eureka
      #defaultZone: http://eureka7002.com:7002/eureka/
      #单机就是自己
      defaultZone: http://eureka7001.com:7001/eureka/
  server :
  #关闭自我保护机制，保证不可用服务被及时踢除
    enable-self-preservation: false
    eviction- interval-timer-in-ms: 2000 
```

修改 Eureka Client 模块的 心跳间隔时间：

```yml
eureka:
  client:
    #表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      #单机版
      defaultZone: http://localhost:7001/eureka
      # 集群版
      #defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
  instance:
      instance-id: payment8001
      #访问路径可以显示IP地址
      prefer-ip-address: true
      #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认是30秒)
      #lease-renewal-interval-in-seconds: 1
      #Eureka服务端在收到最后一次心跳后等待时间上限，单位为秒(默认是90秒)，超时将剔除服务
      #lease-expiration-duration-in-seconds: 2
```

## Zookeeper

> springCloud 整合 zookeeper 
>
> zookeeper是一个分布式协调工具,可以实现注册中心功能
> 关闭Linux服务器防火墙后忠动zookeeper服务器
> zookeeper服务器取代Eureka服务器，zk作为服务注册中心



使用docker启动Zookeeper：

```shell
#拉取Zookeeper镜像
docker pull zookeeper

#启动Zookeeper
docker run --name zk01 -p 2181:2181 --restart always -d zookeeper
```

### 提供者

> 创建一个提供者，和之前的一样即可，使用 8004端口

pom文件如下：

```xml
	<artifactId>cloud-provider-payment8004</artifactId>

    <dependencies>
        <!--springcloud 整合 zookeeper 组件-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.apache.zookeeper</groupId>
                    <artifactId>zookeeper</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.9</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

主启动类：

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient	
public class PaymentMain8004 {
    public static void main(String[] args){
        SpringApplication.run(PaymentMain8004.class, args);
    }
}
```

Controller 打印信息：

```java
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/payment/zk")
    public String paymentzk(){
        return "springcloud with zookeeper :" + serverPort + "\t" + UUID.randomUUID().toString();
    }
}
```

***如果 zookeeper 的版本和导入的jar包版本不一致，启动就会报错，由jar包冲突的问题。

解决这种冲突，需要在 pom 文件中，排除掉引起冲突的jar包，添加和服务器zookeeper版本一致的 jar 包，

但是新导入的 zookeeper jar包 又有 slf4j 冲突问题，于是再次排除引起冲突的jar包

```xml
<!--springcloud 整合 zookeeper 组件-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    <!-- 排除与zookeeper版本不一致到导致 冲突的 jar包 -->
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 添加对应版本的jar包 -->
<dependency>
    <groupId>org.apache.zookeeper</groupId>
    <artifactId>zookeeper</artifactId>
    <version>3.4.9</version>
    <!-- 排除和 slf4j 冲突的 jar包 -->
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

yml文件：

```yml
server:
  port: 8004

spring:
  application:
    name: cloud-provider-service
  cloud:
    zookeeper:
      connect-string: 192.168.40.100:2181
```

启动测试：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597306855233.png)

### 消费者

> 创建测试zookeeper作为服务注册中心的 消费者 模块 cloud-customerzk-order80
>
> 主启动类、pom文件、yml文件和提供者的类似

config类，注入 RestTemplate

```java
@SpringBootConfiguration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getTemplate(){
        return new RestTemplate();
    }
}
```

controller层也是和之前类似：

```java
@RestController
@Slf4j
public class CustomerZkController {

    public static final String INVOKE_URL="http://cloud-provider-service";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/customer/payment/zk")
    public String paymentInfo(){
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/zk",String.class);
        return result;
    }
}
```

关于 zookeeper 的集群搭建，目前使用较少，而且在 yml 文件中的配置也是类似，以列表形式写入 zookeeper 的多个地址即可，而且zookeeper 集群，在 hadoop的笔记中也有记录。总而言之，只要配合zookeeper集群，以及yml文件的配置就能完成集群搭建

## Consul

简介

**Consul是一种服务网格解决方案，提供具有服务发现，配置和分段功能的全功能控制平面。这些功能中的每一个都可以根据需要单独使用，也可以一起使用以构建完整的服务网格。Consul需要一个数据平面，并支持代理和本机集成模型。Consul附带了一个简单的内置代理，因此一切都可以直接使用，还支持Envoy等第三方代理集成。**

> consul也是服务注册中心的一个实现，是由go语言写的。官网地址： https://www.consul.io/intro 
>
> 中文地址： https://www.springcloud.cc/spring-cloud-consul.html
>
> ### 主要特点
>
> - 服务发现：Consul的客户端可以注册服务，例如 api或mysql，其他客户端可以使用Consul来发现给定服务的提供者。使用DNS或HTTP，应用程序可以轻松找到它们依赖的服务。
> - 健康检测：领事客户端可以提供任意数量的运行状况检查，这些检查可以与给定服务（“ Web服务器是否返回200 OK”）或本地节点（“内存利用率低于90％”）相关。操作员可以使用此信息来监视群集的运行状况，服务发现组件可以使用此信息将流量从不正常的主机发送出去。
> - KV存储：应用程序可以将Consul的分层键/值存储用于多种目的，包括动态配置，功能标记，协调，领导者选举等。简单的HTTP API使其易于使用。
> - 安全的服务通信：领事可以为服务生成并分发TLS证书，以建立相互TLS连接。 意图 可用于定义允许哪些服务进行通信。可以使用可以实时更改的意图轻松管理服务分段，而不必使用复杂的网络拓扑和静态防火墙规则。
> - 多数据中心：Consul开箱即用地支持多个数据中心。这意味着Consul的用户不必担心会构建其他抽象层以扩展到多个区域。

### 安装并运行

>  下载地址：https://www.consul.io/downloads.html
>
>  打开下载的压缩包，只有一个exe文件，实际上是不用安装的，在exe文件所在目录打开dos窗口使用即可。
>
>  使用开发模式启动：consul agent -dev
>
>  访问8500端口，即可访问首页
>
>  在docker上安装启动consul
>
>  ```shell
>  #拉取consul镜像
>  docker pull consul
>  
>  #启动consul
>  docker run -d  -p 8500:8500/tcp --name myConsul  consul agent -server -ui -bootstrap-expect=1 -client=0.0.0.0
>  ```

### 提供者

> 新建提供者模块：cloud-providerconsul-service8006 

pom 文件：

```xml
	<artifactId>cloud-providerconsul-service8006</artifactId>
    <dependencies>
        <!--springcloud consul server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>

        <!-- springboot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 日常通用jar包 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml 文件：

```yml
server:
  port: 8006
spring:
  application:
    name: consul-provider-service
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:    # 指定注册对外暴露的服务名称
        service-name: ${spring.application.name}
```

主启动类：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulProviderMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(ConsulProviderMain8006.class,args);
    }
}
```

controller也是简单的写一下就行。

### 消费者

> 新建 一个 在82端口的 消费者模块。pom和yml和提供者的类似，主启动类不用说，记得注入RestTemplate

controller层：

```java
@RestController
public class CustomerConsulController {

    public static final String INVOKE_URL="http://consul-provider-service";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/customer/payment/consul")
    public String paymentInfo(){
        String result = restTemplate.getForObject(INVOKE_URL + "/payment/consul",String.class);
        return result;
    }
}
```

### 总结

### CAP：（只能二选一） A：可用性 C：一致性 P：分区容错性（微服务架构必须保证有P）

### CAP理论关注粒度是数据，而不是整体系统设计的策略

![](https://gitee.com/zssea/picture-bed/raw/master/img/ayufio3267 8902hs8712ijk.png)

![](https://img-blog.csdnimg.cn/20200606172402942.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

#### 最多只能同时较好的满足两个。CAP理论的核心是: -个分布式系统不可能同时很好的满足一致性, 可用性和分区容错性这三个需求,
因此，根据CAP原理将NoSQL数据库分成了满足CA原则、满足CP原则和满足AP原则三大类:
CA-单点集群，满足一致性，可用性的系统，通常在可扩展性不太强大。
CP -满足一致性，分区容忍必的系统，通常性能不是特别高。
AP -满足可用性,分区容忍性的系统,通常可能对一致性要求低一些。


AP:

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597384508291.png)

CP：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597384554249.png)

# 服务调用

> 都是使用在 client端，即有 ”消费者“ 需求的模块中。

## Ribbon

> 我们这里提前启动好之前在搭建的 eureka Server 集群（5个模块）
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597384873709.png)

### 简介

Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
简单的说，Ribbon是Netflix发布的开源项目 ，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon客户端组件提供- 系列
完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer (简称LB) 后面所有的机器，Ribbon会自动的帮
助你基于某种规则(如简单轮询，随机连接等)去连接这些机器。我们很容易使用Ribbon实现自定义的负载均衡算法。

LB负载均衡(Load Balance)是什么简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA (高可用)。
常见的负载均衡有软件Nginx, LVS,硬件F5等。

Ribbon本地负载均衡客户端VS Nginx服务端负载均衡区别：
Nginx是服务器负载均衡，客户端所有请求都会交给nginx,然后由nginx实现转发请求。即负载均衡是由服务端实现的。

Ribbon本地负载均衡，在调用微服务接口时候，会在注册中心上获取注册信息服务列表之后缓存到JVM本地，从而在本地实现RPC远程服务调用技术。





![](https://gitee.com/zssea/picture-bed/raw/master/img/1597385250463.png)

Ribbon在工作时分成两步
第一步先选择EurekaServer ,它优先选择在同一个区域内负载较少的server.
第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。
其中Ribbon提供了多种策略:比如轮询、随机和根据响应时间加权。

上面在eureka时，确实实现了负载均衡机制，那是因为 eureka-client包里面自带着ribbon：

> 一句话，Ribbon 就是 负载均衡 + RestTemplate 调用。实际上不止eureka的jar包有，zookeeper的jar包，还有consul的jar包都包含了他，就是上面使用的服务调用。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597385486515.png)

如果自己添加，在 模块的 pom 文件中引入：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

对于RestTemplate 的一些说明：

> 有两种请求方式：post和get ,还有两种返回类型：object 和 Entity 

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597385762892.png)

RestTemplate 的 ForEntity 相比 ForObject特殊的地方:

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597385959115.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597385892918.png)

就是 如果使用 ForObject 得到的就是提供者返回的对象，而如果要使用 ForEntity 得到时 ResponstEntity对象，使用getBody()才能得到提供者返回的数据。

```java
	//使用forEnriry示例：
	@GetMapping("customer/payment/forEntity/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id")Long id){
        
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENY_URL + "/payment/" + id, CommonResult.class);
        
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult<>(444, "操作失败");
        }
    }
```

### 负载均衡

Ribbon 负载均衡规则类型：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597386516205.png)

配置负载均衡规则：

> 官方文档明确给出了警告:
> 这个自定义配置类不能放在@ComponentScan所扫描的当前包下以及子包下，
> 否则我们自定义的这个配置类就会被所有的Ribbon客户端所共享,达不到特殊化定制的目的了。
>
> 注意上面说的，而Springboot主启动类上的 @SpringBootApplication 注解，相当于加了@ComponentScan注解，会自动扫描当前包及子包，所以注意不要放在SpringBoot主启动类的包内。

创建包：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597386816245.png)

在这个包下新建 MySelfRule类：

```java
package com.dkf.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {
    @Bean
    public IRule myrule(){
        return new RandomRule(); //负载均衡规则定义为随机
    }
}
```

然后在主启动类上添加如下注解 @RibbonClient：

```java
package com.dkf.springcloud;

import com.dkf.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
	   //指定该负载均衡规则对哪个提供者服务使用    加载自定义规则的配置类
@RibbonClient(name="CLOUD-PROVIDER-SERVICE", configuration = MySelfRule.class)
public class OrderMain80 {

    public static void main(String[] args){
        SpringApplication.run(OrderMain80.class, args);
    }
}

```

### 轮询算法原理

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597387609476.png)

### RoundRobinRule源码

RoundRobinRule的核心为choose方法：

```java
public class RoundRobinRule extends AbstractLoadBalancerRule {
	//AtomicInteger原子整形类
    private AtomicInteger nextServerCyclicCounter;
	...
    public RoundRobinRule() {
    	//此时nextServerCyclicCounter是一个原子整形类，并且value为0
        nextServerCyclicCounter = new AtomicInteger(0);
    }
	...
	//ILoadBalancer选择的负载均衡机制，这里lb为轮询
    public Server choose(ILoadBalancer lb, Object key) {
    	//如果传入的lb没有负载均衡，为空
        if (lb == null) {
            log.warn("no load balancer");
            return null;
        }

        Server server = null;
        int count = 0;
        //还没选到执行的server，并且选择的次数没超过10次，进行选择server
        while (server == null && count++ < 10) {
        	//lb.getReachableServers获取所有状态是up的服务实例
            List<Server> reachableServers = lb.getReachableServers();
            //lb.getAllServers获取所有服务实例
            List<Server> allServers = lb.getAllServers();
            //状态为up的服务实例的数量
            int upCount = reachableServers.size();
            //所有服务实例的数量
            int serverCount = allServers.size();
			
			//如果up的服务实例数量为0或者服务实例为0，打印日志log.warn并返回server=null
            if ((upCount == 0) || (serverCount == 0)) {
                log.warn("No up servers available from load balancer: " + lb);
                return null;
            }
			
			//获取到接下来server的下标
            int nextServerIndex = incrementAndGetModulo(serverCount);
            //获取下一个server
            server = allServers.get(nextServerIndex);

			//如果
            if (server == null) {
                //线程让步，线程会让出CPU执行权，让自己或者其它的线程运行。（让步后，CPU的执行权也有可能又是当前线程）
                Thread.yield();
                //进入下次循环
                continue;
            }
			
			//获取的server还活着并且还能工作，则返回该server
            if (server.isAlive() && (server.isReadyToServe())) {
                return (server);
            }

            //否则server改为空
            server = null;
        }

		//选择次数超过10次，打印日志log.warn并返回server=null
        if (count >= 10) {
            log.warn("No available alive servers after 10 tries from load balancer: "
                    + lb);
        }
        return server;
    }


    private int incrementAndGetModulo(int modulo) {
    	//CAS加自旋锁
    	//CAS（Conmpare And Swap）：是用于实现多线程同步的原子指令。CAS机制当中使用了3个基本操作数：内存地址V，旧的预期值A，要修改的新值B。更新一个变量的时候，只有当变量的预期值A和内存地址V当中的实际值相同时，才会将内存地址V对应的值修改为B。
    	//自旋锁：是指当一个线程在获取锁的时候，如果锁已经被其它线程获取，那么该线程将循环等待，然后不断的判断锁是否能够被成功获取，直到获取到锁才会退出循环。 
        for (;;) {
        	//获取value，即0
            int current = nextServerCyclicCounter.get();
            //取余，为1
            int next = (current + 1) % modulo;
            //进行CAS判断，如果此时在value的内存地址中，如果value和current相同，则为true，返回next的值，否则就一直循环，直到结果为true
            if (nextServerCyclicCounter.compareAndSet(current, next))
                return next;
        }
    }
    ...
}
```

### 手写一个轮询自定义配置类

##### 8001和8002微服务改造

在8001和8002的PaymentController中加上这个方法，用于测试我们的自定义轮询：

```java
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
```

##### 80订单微服务改造

1. 去掉ApplicationContextConfig里restTemplate方法上的@LoadBalanced注解。

2. 在springcloud包下新建lb.ILoadBalancer接口（自定义负载均衡机制（面向接口））

   ```java
   public interface ILoadBalancer {
   
       //传入具体实例的集合，返回选中的实例
       ServiceInstance instances(List<ServiceInstance> serviceInstance);
   
   }
   ```

3. 在lb包下新建自定义ILoadBalancer接口的实现类

   ```java
   @Component  //加入容器
   public class MyLB implements ILoadBalancer {
   
       //新建一个原子整形类
       private AtomicInteger atomicInteger = new AtomicInteger(0);
   
       //
       public final int getAndIncrement(){
           int current;
           int next;
           do{
               current = this.atomicInteger.get();
               //如果current是最大值，重新计算，否则加1（防止越界）
               next = current >= Integer.MAX_VALUE ? 0 : current + 1;
   
           //进行CAS判断，如果不为true，进行自旋
           }while (!this.atomicInteger.compareAndSet(current, next));
           System.out.println("****第几次访问，次数next：" + next);
   
           return next;
       }
   
       @Override
       public ServiceInstance instances(List<ServiceInstance> serviceInstance) {
           //非空判断
           if(serviceInstance.size() <= 0){
               return null;
           }
           //进行取余
           int index = getAndIncrement() % serviceInstance.size();
           //返回选中的服务实例
           return serviceInstance.get(index);
       }
   }
   ```

4. 在OrderController添加：

   ```java
       @Resource
       private ILoadBalancer iLoadBalancer;
       @Resource
       private DiscoveryClient discoveryClient;
   	
       @GetMapping("/consumer/payment/lb")
       public String getPaymentLB(){
           //获取CLOUD-PAYMENT-SERVICE服务的所有具体实例
           List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
           if(instances == null || instances.size() <= 0){
               return null;
           }
   
           ServiceInstance serviceInstance = iLoadBalancer.instances(instances);
           URI uri = serviceInstance.getUri();
           System.out.println(uri);
   
           return restTemplate.getForObject(uri + "/payment/lb", String.class);
       }
   ```

## OpenFeign

### 概述

> 这里和之前学的dubbo很像，例如消费者的controller 可以调用提供者的 service层方法，但是不一样，它貌似只能调用提供者的 controller，即写一个提供者项目的controller的接口，消费者来调用这个接口方法，就还是相当于是调用提供者的 controller ，和RestTemplate 没有本质区别

Feign能干什么
Feign旨在使编写Java Http客户端变得更容易。
前面在使用Ribbon+ RestTemplate时,利用RestTemplate对http请求的封装处理,形成了一套模版化的调用方法。 但是在实际开发中，由于对服务依赖的调用可能不止一处,往往-个接口会被多处调用，所以通常都会针对每个微服务自行封装些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装， 由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它(以前是Dao接口 上面标注Mapper注解,现在是一个微服务接口. 上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。

Feign集成了Ribbon
利用Ribbon维护了Payment的服务列表信息，且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是,通过feign只需要定义
服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597389321006.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597389522738.png)

### 使用

> 新建一个消费者募模块。<font color=red>feign自带负载均衡配置</font>，所以不用手动配置

pom ：

```xml
	<dependencies>
        <!-- Open Feign -->
         <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!-- eureka Client -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

主启动类：

```java
@SpringBootApplication
@EnableFeignClients   //关键注解
public class CustomerFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run(CustomerFeignMain80.class, args);
    }
}
```

新建一个service

> 这个service还是 customer 模块的接口，和提供者没有任何关系，不需要包类名一致。它使用起来就相当于是普通的service。
>
> 
>
> 推测大致原理，对于这个service 接口，读取它某个方法的注解（GET或者POST注解不写报错），知道了请求方式和请求地址，而抽象方法，只是对于我们来讲，调用该方法时，可以进行传参等。

```java
@Component
@FeignClient(value = "CLOUD-PROVIDER-SERVICE")  //服务名称，要和eureka上面的一致才行
public interface PaymentFeignService {

    //这个就是provider 的controller层的方法定义。
    @GetMapping(value = "/payment/{id}")
    public CommonResult getPaymentById(@PathVariable("id")Long id);

}
```

Controller层：

```java
//使用起来就相当于是普通的service。
@RestController
public class CustomerFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("customer/feign/payment/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }
}

```

### 超时控制

> Openfeign默认超时等待为一秒，在消费者里面配置超时时间

```yml
#没提示不管它，可以设置
ribbon:
  #指的是建立连接后从服务器读取到可用资源所用的时间
  ReadTimeout: 5000
  #指的是建立连接使用的时间，适用于网络状况正常的情况下，两端连接所用的时间
  ConnectTimeout: 5000
```

### 开启日志打印

日志级别

NONE:  默认的,不显示任何日志;
BASIC:  仅记录请求方法、URL、 响应状态码及执行时间;
HEADERS:  除了BASIC中定义的信息之外，还有请求和响应的头信息;
FULL:   除了HEADERS中定义的信息之外，还有请求和响应的正文及元数据。

在80的springcloud包下新建config.FeignConfig

```java
import feign.Logger;	

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        //打印最详细的日志
        return Logger.Level.FULL;
    }
}
```

然后在80的yml文件中开启日志打印配置：

```yml
#开启日志的feign客户端
logging:
  level:
    #feign日志以什么级别监控哪个接口
    com.dkf.cloud.service.PaymentFeignService: debug	#写你们自己的包名
```

#### 中级部分

> 主要是服务降级、服务熔断、服务限流的开发思想和框架实现

# Hystrix 断路器

> 官方地址：https://github.com/Netflix/Hystrix/wiki/How-To-Use

## 概述

服务雪崩
多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C,微服务B和微服务C又调用其它的微服务,这就是所谓的”扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用,对微服务A的调用就会占用越来越多的系统资源,进而引|起系统崩溃,所谓的“雪崩效应”.

对于高流量的应用来说，单-的后端依赖可能会导致所有服务 器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。

Hystrix是一个用于处理分布式系统的延迟和容错的开源库， 在分布式系统里,许多依赖不可避免的会调用失败，比如超时、异常等,Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败,避免级联故障,以提高分布式系统的弹性。

“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控(类似熔断保险丝)，向调用方返回一个符合预期的、可处理的备选响应(FallBack) ，而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。



服务降级：

> 服务器忙碌或者网络拥堵时，不让客户端等待并立刻返回一个友好提示，fallback
>
> 发生的情况：程序运行异常
>                          超时
>                         服务熔断触发服务降级
>                        线程池/信号量打满也会导致服务降级

服务熔断：

> 类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法并返回友好提示
> 就是保险丝

服务限流：

> 秒杀高并发等操作，严禁-窝蜂的过来拥挤，大家排队，-秒钟N个，有序进行
>
> 可见，上面的技术不论是消费者还是提供者，根据真实环境都是可以加入配置的。

## 案例

> 首先构建一个eureka作为服务中心的单机版微服务架构 ，这里使用之前eureka Server 7001模块，作为服务中心

新建 提供者  cloud-provider-hystrix-payment8001 模块：

pom 文件：

```xml
	<dependencies>
        <!-- hystrix -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
        <!--eureka-client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

下面主启动类、service、和controller代码都很简单普通。

主启动类：

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class,args);
    }
}
```

service层：

```java
@Service
public class PaymentService {

    /**
     * 可以正常访问的方法
     * @param id
     * @return
     */
    public String paymentinfo_Ok(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "--paymentInfo_OK，id:" + id;
    }

    /**
    超时访问的方法
    */
    public String paymentinfo_Timeout(Integer id){
        int interTime = 3;
        try{
            TimeUnit.SECONDS.sleep(interTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "--paymentInfo_Timeout，id:" + id + "耗时" + interTime + "秒钟--";
    }
}
```

controller层：

```java
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}") //spring的@Value注解
    private String serverPort;

    @GetMapping("/payment/hystrix/{id}")
    public String paymentInfo_OK(@PathVariable("id")Integer id){
        log.info("paymentInfo_OKKKKOKKK");
        return paymentService.paymentinfo_Ok(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id")Integer id){
        log.info("paymentInfo_timeout");
        return paymentService.paymentinfo_Timeout(id);
    }
}
```

### 模拟高并发

> 这里使用一个新东西 JMeter 压力测试器
>
> 下载压缩包，解压，双击 /bin/ 下的 jmeter.bat 即可启动
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597471683432.png)
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597471750363.png)
>
> ctrl + S 保存。
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597472029967.png)

从测试可以看出，当模拟的超长请求被高并发以后，访问普通的小请求速率也会被拉低。

新建消费者 cloud-customer-feign-hystrix-order80 模块：

1. pom

   ```xml
   <dependencies>
       <!-- openfeign -->
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-openfeign</artifactId>
       </dependency>
       <!--   hystrix     -->
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
       </dependency>
       <!--eureka client-->
       <dependency>
           <groupId>org.springframework.cloud</groupId>
           <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
       </dependency>
       <!-- 引用自己定义的api通用包，可以使用Payment支付Entity -->
       <dependency>
           <groupId>com.dkf.cloud</groupId>
           <artifactId>cloud-api-commons</artifactId>
           <version>${project.version}</version>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-actuator</artifactId>
       </dependency>
       <!--热部署-->
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-devtools</artifactId>
           <scope>runtime</scope>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.projectlombok</groupId>
           <artifactId>lombok</artifactId>
           <optional>true</optional>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-test</artifactId>
           <scope>test</scope>
       </dependency>
   </dependencies>
   ```

2. yml

   ```yml
   server:
     port: 80
   
   
   eureka:
     client:
       register-with-eureka: false
       service-url:
         defaultZone: http://localhost:7001/eureka
   
   #需要加上，否则会报错
   ribbon:
     ReadTimeout: 4000
     ConnectTimeout: 4000
   ```

3. 主启动类

   ```java
   @EnableEurekaClient
   @EnableFeignClients
   @SpringBootApplication
   public class OrderHystrixMain80 {
   
       public static void main(String[] args) {
           SpringApplication.run(OrderHystrixMain80.class, args);
       }
   
   }
   ```

4. service

   ```java
   @Component
   @FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT")
   public interface PaymentHystrixService {
   
       @GetMapping("/payment/hystrix/ok/{id}")
       public String paymentInfo_OK(@PathVariable("id") Integer id);
       
       @GetMapping("/payment/hystrix/timeout/{id}")
       public String paymentInfo_TimeOut(@PathVariable("id") Integer id);
   }
   ```

5. controller

   ```java
   @Slf4j
   @RestController
   public class OrderHystrixController {
   
       @Resource
       private PaymentHystrixService paymentHystrixService;
   
   
       @GetMapping("/consumer/payment/hystrix/ok/{id}")
       public String paymentInfo_OK(@PathVariable("id") Integer id){
           String result = paymentHystrixService.paymentInfo_OK(id);
           return result;
       }
   
       @GetMapping("/consumer/payment/hystrix/timeout/{id}")
       public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
           String result = paymentHystrixService.paymentInfo_TimeOut(id);
           return result;
       }
   
   }
   ```

测试可见，当启动高并发测试时，消费者访问也会变得很慢，甚至出现超时报错。

解决思路：
对方服务(8001)超时了，调用者(80)不能一直卡死等待， 必须有服务降级
对方服务(8001)down机了，调用者(80)不能一-直 卡死等待，必须有服务降级
对方服务(8001)OK，调用者(80)自己出故障或有自我要求(自己的等待时间小于服务提供者），自己处理降级

### 服务降级

> 一般服务降级放在客户端，即 消费者端 ，但是提供者端一样能使用。
>
> 首先提供者，即8001 先从自身找问题，设置自身调用超时的峰值，峰值内正常运行，超出峰值需要有兜底的方法处理，作服务降级fallback

首先 对 8001 的service进行配置（对容易超时的方法进行配置) :

```java
	@HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler", commandProperties = {
            //设置峰值，超过 3 秒，就会调用兜底方法，这个时间也可以由feign控制
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentinfo_Timeout(Integer id){
         int timeNumber = 5;
        //int i = 1 / 0;
        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() +
                "\tpaymentInfo_TimeOut，id：" + id + "，耗时：" + timeNumber + "秒";
    }

	//兜底方法，根据上述配置，程序内发生异常、或者运行超时，都会执行该兜底方法
    public String paymentInfo_timeoutHandler(Integer id){
        return "8001提供者，线程池：" + Thread.currentThread().getName() + 
                "\tpaymentInfo_TimeOutHandler系统繁忙，请稍后再试，id：" + id;
    }
}
```

 主启动类添加注解： @EnableCircuitBreaker

然后对 80 进行服务降级：很明显 service 层是接口，所以我们对消费者，在它的 controller 层进行降级

```java
	 @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler", commandProperties = {
            //设置峰值，超过 3 秒，就会调用兜底方法
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @GetMapping("/customer/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id")Integer id){
        log.info("paymentInfo_timeout");
        return orderService.paymentInfo_Timeout(id);
    }

	//兜底方法，注意，兜底方法参数随意
    public String paymentInfo_timeoutHandler(@PathVariable("id")Integer id){
        log.info("paymentInfo_timeout--handler");
        return "访问 payment 失败----人工报错";
    }
```

主启动类添加注解： @EnableCircuitBreaker

完成测试！ 注意，消费者降级设置的超时时间和提供者的没有任何关系，就算提供者峰值是 5 秒，而消费者峰值是 3秒，那么消费者依然报错。就是每个模块在服务降级上，都是独立的。

### 全局服务降级

> 上面的降级策略，很明显造成了代码的杂乱，提升了耦合度，而且按照这样，每个方法都需要配置一个兜底方法，很繁琐。现在将降级处理方法（兜底方法）做一个全局的配置，设置共有的兜底方法和独享的兜底方法。

问题-每个方法配置一个，解决：

```java
@RestController
@Slf4j
//全局配置降级方法的注解
@DefaultProperties(defaultFallback = "paymentInfo_timeoutHandler")
public class OrderController {
    .....
    // 不写自己的 fallbackMethod 属性，就使用全局默认的
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @GetMapping("/customer/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id")Integer id){
        ......
    }

    //兜底方法
    public String paymentInfo_timeoutHandler(){
        log.info("paymentInfo_timeout--handler");
        return "访问 payment 失败----人工报错";
    }
}
```

问题-跟业务逻辑混合，解决（解耦）：

> 在这种方式一般是在客户端，即消费者端，首先上面再controller中添加的  @HystrixCommand 和 @DefaultProperties 两个注解去掉。就是保持原来的controller

1. yml文件配置

```yml
server:
  port: 80
spring:
  application:
    name: cloud-customer-feign-hystrix-service
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/

# 用于服务降级 在注解@FeignClient 中添加 fallback 属性值
feign:
  hystrix:
    enabled: true  # 在feign中开启 hystrix
```

2. 修改service 接口：

```java
@Component											// 这里是重点
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT", fallback = OrderFallbackService.class)
public interface OrderService {

    @GetMapping("/payment/hystrix/{id}")
    public String paymentInfo_OK(@PathVariable("id")Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_Timeout(@PathVariable("id")Integer id);

}
```

3. fallback 指向的类：

```java
package com.dkf.springcloud.service;

import org.springframework.stereotype.Component;

@Component						//注意这里，它实现了service接口
public class OrderFallbackService implements  OrderService{


    @Override
    public String paymentInfo_OK(Integer id) {
        return "OrderFallbackService --发生异常";
    }

    @Override
    public String paymentInfo_Timeout(Integer id) {
        return "OrderFallbackService --发生异常--paymentInfo_Timeout";
    }
}
```

新问题，这样配置如何设置超时时间？

> 首先要知道 下面两个 yml 配置项：
>
> ```properties
> hystrix.command.default.execution.timeout.enable=true    ## 默认值
> 
> ## 为false则超时控制有ribbon控制，为true则hystrix超时和ribbon超时都是用，但是谁小谁生效，默认为true
> 
> hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds=1000  ## 默认值
> 
> ## 熔断器的超时时长默认1秒，最常修改的参数
> ```
>
> 看懂以后，所以：
>
> 只需要在yml配置里面配置 Ribbon 的 超时时长即可。注意：hystrix 默认自带 ribbon包。
>
> ```yml
> ribbon:
> 	ReadTimeout: xxxx
> 	ConnectTimeout: xxx
> ```

### 服务熔断

> 实际上服务熔断 和 服务降级 没有任何关系，就像 java 和 javaScript
>
> 服务熔断，有点自我恢复的味道

熔断机制概述
熔断机制是应对雪崩效应的一种微服务链路保护机制。当扇出链路的某个微服务出错不可用或者响应时间太长时，会进行服务的降级，进而熔断该节点微服务的调用，快速返回错误的响应信息。当检测到该节点微服务调用响应正常后，恢复调用链路。

在Spring Cloud框架里,熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况，
当失败的调用到一定阈值，缺省是5秒内20次调用失败,就会启动熔断机制。熔断机制的注解是@HystrixCommand.

> 熔断打开
> 请求不再进行调用当前服务，内部设置时钟-般为MTTR (平均故障处理时间)，当打开时长达到所设时钟则进入半熔断状态
>
> 熔断关闭
> 熔断关闭不会对服务进行熔断
>
> 熔断半开
> 部分请求根据规则调用当前服务，如果请求成功且符合规则则认为当前服务恢复正常，关闭熔断

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597555381295.png)

以 8001 项目为示例：

service层的方法设置服务熔断:

```java
 	//=====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled", value="true"),  // 是否开启断路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),  //请求次数
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="10000"), // 时间窗口期
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60"),  // 失败率达到多少后跳闸
            //整体意思：10秒内 10次请求，有6次失败，就跳闸
    })
    public String paymentCircuitBreaker(Integer id){
        //模拟发生异常
        if(id < 0){
            throw new RuntimeException("*****id，不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功，流水号：" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id){
        return "id 不能为负数，请稍后再试....";
    }
```

controller:

```java
	//====服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        return paymentService.paymentCircuitBreaker(id);
    }
```

关于解耦以后的全局配置说明：

例如上面提到的全局服务降级，并且是feign+hystrix整合，即 service 实现类的方式，如何做全局配置？

> 上面有 做全局配置时，设置超时时间的方式，我们可以从中获得灵感，即在yml文件中 进行熔断配置：
>
> ```yml
> hystrix:
>   command:
>     default:
>       circuitBreaker:
>         enabled: true
>         requestVolumeThreshold: 10
>         sleepWindowInMilliseconds: 10000
>         errorThresholdPercentage: 60
> ```

## Hystrix DashBoard

除了隔离依赖服务的调用以外, Hystrix还提供 了准实时的调用监控(Hystrix Dashboard)，Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，拟统计报表和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。Spring Cloud也提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。

新建模块 cloud-hystrix-dashboard9001 ：

pom 文件：

```xml
	<dependencies>
        <!-- hystrix Dashboard-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        </dependency>
        <!-- 常规 jar 包 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <dependency>
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml文件只需要配置端口号，主启动类加上这样注解：@EnableHystrixDashboard

启动测试：访问  http://ocalhost:9001/hystrix

### 监控实战

> 下面使用上面 9001 Hystrix Dashboard 项目，来监控 8001 项目 Hystrix 的实时情况：

```java
/**
    * 此配置是为了服务监控而配置，与服务容错本身无关，springcloud升级后的坑
    * ServletRegistrationBean因为SpringBoot的默认路径不是 “/hystrix.stream"
    * 只要在自己的项目里配置上下的servlet就可以了
    */
   @Bean
   public ServletRegistrationBean getServlet() {
       HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet() ;
       ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
       registrationBean.setLoadOnStartup(1);
       registrationBean.addUrlMappings("/hystrix.stream");
       registrationBean.setName("HystrixMetricsStreamServlet");
       return  registrationBean;
   }
```

![](https://img-blog.csdnimg.cn/20200609101801470.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597558486510.png)

![](https://img-blog.csdnimg.cn/20200609103023793.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

![](https://img-blog.csdnimg.cn/20200609103129409.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

# 服务网关

## Gateway

> 内容过多，开发可参考 https://docs.spring.io/  官网文档

### 简介

SpringCloud Gateway是Spring Cloud的一个全新项目， 基纡Spring 5.0+ Spring Boot 2.0和Project Reactor等技术开发的网关,它旨在为微服务架构提供一种简单有效的统一 的API路由管理方式。
SpringCloud Gateway作为Spring Cloud生态系统中的网关，目标是替代Zuul,在Spring Cloud 2.0以上版本中,没有对新版本的Zuul 2.0以上最新高性能版本进行集成，仍然还是使用的Zuul 1.x非Reactor模式的老版本。为了提升网关的性能，SpringCloud Gateway是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty.
Spring Cloud Gateway的目标提供统- -的路由方式且基于Filter链的方式提供了网关基本的功能，例如:安全，监控/指标,和限流。

-方面因为Zuul1.0已经进入了维护阶段,而且Gateway是SpringCloud团队研发的， 儿子产品,值得信赖。
而且很多功能Zuul都没有用起来也非常的简单便捷。
Gateway是基于异步非阻塞模型上进行开发的，性能方面不需要担心。虽然Netflix早就发布 了最新的Zuul 2.x,
但Spring Cloud貌似没有整合计划。而且Netflix相关组件 都宣布进入维护期;不知前景如何?
多方面综合考虑Gateway是很理想的网关选择。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597559787064.png)

> Spring Cloud Gateway具有如下特性:
> 基于Spring Framework 5, Project Reactor和Spring Boot 2.0进行构建;
>
> 动态路由:能够匹配任何请求属性;
>
> 可以对路由指定Predicate (断言)和Filter (过滤器) ;
>
> 集成Hystrix的断路器功能;
>
> 集成Spring Cloud服务发现功能;
>
> 易于编写的Predicate (断言) 和Filter (过滤器) ;
>
> 请求限流功能;
>
> 支持路径重写。



Spring Cloud Gateway与Zuul的区别
在SpringCloud Finchley正式版之前，Spring Cloud推荐的网关是Netflix提供的Zuul:
1、Zuul 1.x,是一个基于阻塞I/ 0的API Gateway

2、Zuul 1.x基于Servlet 2.5使用阻塞架构它不支持任何长连接(如WebSocket) Zuul的设计模式和Nginx较像，每I/ O操作都是从工作线程中选择-个执行，请求线程被阻塞到工作线程完成，但是差别是Nginx 用C++实现，Zuul 用Java实现，而JVM本身会有第一次加载较慢的情况，使得Zuul的性能相对较差。

3、Zuul 2.x理念更先进,想基于Netty非阻塞和支持长连接,但SpringCloud目 前还没有整合。
Zuul 2.x的性能较Zuul 1.x有较大提升在性能方面，根据官方提供的基准测试，Spring Cloud Gateway的RPS (每秒请求数)是Zuul的1.6倍。

4、Spring Cloud Gateway建立在Spring Framework 5、Project Reactor和Spring Boot2之上，使用非阻塞API。

5、 Spring Cloud Gateway 还支持WebSocket， 齟与Spring紧密集成拥有更好的开发体验



servlet是一个简单的网络IO模型， 当请求进入servlet container时, servlet container就会为其绑定一个线程, 在并发不高的场景下这种模型是适用的。但是一旦高并发(此如抽风用jemeter压),线程数量就会.上涨，而线程资源代价是昂贵的(上线文切换，内存消耗大)严重影响请求的处理时间。
在- -些简单业务场景下，不希望为每个request分配一个线程， 只需要1个或几个线程就能应对极大并发的请求，这种业务场景下servlet模型没有优势

所以Zuul 1.X是基于servlet之上的一个阻塞式处理模型，即spring实现了处理所有request请求的一个servlet (DispatcherServlet) 并抽该servlet阻塞式处理处理。所以Springcloud Zuul无法摆脱servlet模型的弊端

![preview](https://pic3.zhimg.com/v2-5adf98fdc6a1a404dd65c2bb20c19a2e_r.jpg)

传统的Web框架，比如说: struts2, springmvc等都是基 于Servlet API与Servlet容器基础之上运行的。
但是在Servlet3.1之后有了异步非阻塞的支持。而WebFlux是一个典型非阻塞 异步的框架,它的核心是基于Reactor的相关API实现的。相对于传统的web框架来说，它可以运行在诸如Netty, Undertow及 支持Servlet3.1的容器上。非阻塞式+函数式编程(Spring5必须让你使用java8)

Spring WebFlux是Spring 5.0引入的新的响应式框架,区别于Spring MVC,它不需要依赖Servlet API,它是完全异步非阻塞的，并且基盱Reactor实现响应式流规范。



##### 三大核心概念：

Route(路由)
路由是构建网关的基本模块，它由ID, 目标URI, - -系列的断言和过滤器组成，如果断言为true则匹配该路由
参考的是Java8的java.util.function.Predicate

Predicate(断言)
开发人员可以匹配HTTP请求中的所有内容(例如请求头或请求参数)，如果请求与断言相匹配则进行路由

Filter(过滤)
指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597560156913.png)

## Gateway工作流程

官网总结

![](https://img-blog.csdnimg.cn/20200617094752770.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

客户端向Spring Cloud Gateway发出请求。然后在Gateway Handler Mapping中找到与请求相匹配的路由，将其发送到Gateway Web Handler。

Handler再通过指定的过滤器链来将请求发送到我们实际的服务执行业务逻辑,然后返回。
过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前( "pre" )或之后( "post" )执行业务逻辑。

Filter在"pre" 类型的过滤器可以做参数校验、权限校验、流量监控、日志输出、协议转换等,
在"post"类型的过滤器中可以做响应内容、响应头的修改,日志的输出,流量监控等有着非常重要的作用。

### 入门配置

新建模块 cloud-gateway-gateway9527

> 现在实现，通过Gateway (网关) 来访问其它项目，这里选择之前8001项目，要求注册进Eureka Server 。其它没要求。

pom文件：

```xml
<dependencies>
        <!--gateway-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <!--eureka-client gateWay作为网关，也要注册进服务中心-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    <!-- gateway和web不能同时存在，即web相关jar包不能导入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml文件：

```yml
server:
  port: 9527
spring:
  application:
    name: cloud-gateway
  ## GateWay配置
  cloud:
    gateway:
      routes:
        - id: payment_routh  # 路由ID ， 没有固定的规则但要求唯一，建议配合服务名
          uri: http://localhost:8001  # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/get/**  # 断言，路径相匹配的进行路由

        - id: payment_routh2  # 路由ID ， 没有固定的规则但要求唯一，建议配合服务名
          uri: http://localhost:8001  # 匹配后提供服务的路由地址
          predicates:
            - Path=/payment/lb/**  # 断言，路径相匹配的进行路由

# 注册进 eureka Server
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
    register-with-eureka: true
    fetch-registry: true
```

主启动类，很普通，没有特殊的配置：

```java
@SpringBootApplication
@EnableEurekaClient
public class GatewayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GatewayMain9527.class,args);
    }
}
```

访问测试：1 启动eureka Server，2 启动 8001 项目，3 启动9527（Gateway项目）

> 可见，当我们访问 http://localhost:9527/payment/get/1 时，即访问网关地址时，会给我们转发到 8001 项目的请求地址，以此作出响应。
>
> 加入网关前：http://localhost:8001/payment/get/1
>
> 加入网关后：http://localhost:9527/payment/get/1

上面是以 yml 文件配置的路由，也有使用config类配置的方式：

新建config.GatewayConfig

```java
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();

        routes.route("path_route_angenin",  //id
                r -> r.path("/guonei")  //访问 http://localhost:9527/guonei
                        .uri("http://news.baidu.com/guonei"));  //就会转发到 http://news.baidu.com/guonei

        routes.route("path_route_angenin2",  //id
                r -> r.path("/guoji")  //访问 http://localhost:9527/guoji
                        .uri("http://news.baidu.com/guoji"));  //就会转发到 http://news.baidu.com/guonji

        return routes.build();
    }
```

### 动态配置

> 这里所谓的动态配置就是利用服务注册中心，来实现 负载均衡 的调用 多个微服务。
>
> 注意，这是GateWay 的负载均衡

对yml进行配置：

```yml
spring:
  application:
    name: cloud-gateway
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true # 开启从注册中心动态创建路由的功能，利用微服务名进行路由
      routes:
        - id: payment_routh  # 路由ID ， 没有固定的规则但要求唯一，建议配合服务名
         # uri: http://localhost:8001  # 匹配后提供服务的路由地址
          uri: lb://CLOUD-PROVIDER-SERVICE
          predicates:
            - Path=/payment/get/**  # 断言，路径相匹配的进行路由

        - id: payment_routh2  # 路由ID ， 没有固定的规则但要求唯一，建议配合服务名
        #  uri: http://localhost:8001  # 匹配后提供服务的路由地址
          uri: lb://CLOUD-PROVIDER-SERVICE
          predicates:
            - Path=/payment/lb/**  # 断言，路径相匹配的进行路由

# uri: lb://CLOUD-PROVIDER-SERVICE  解释：lb 属于GateWay 的关键字，代表是动态uri，即代表使用的是服务注册中心的微服务名，它默认开启使用负载均衡机制 
```

> 下面可以开启 8002 模块，并将它与8001同微服务名，注册到 Eureka Server 进行测试。

### Predicate

Spring Cloud Gateway将路由匹配作为Spring WebFlux HandlerMapping基础架构的一部分。
Spring Cloud Gateway包括许多内置的Route Predicate工厂。所有这些Predicate都与HTTP请求的不同属性匹配。多个Route Predicate厂可以进行组合

Spring Cloud Gateway创建Route对象时，使用RoutePredicateFactory创建Predicate对象, Predicate 对象可以赋值给
Route。Spring Cloud Gateway包含许多内置的Route Predicate Factories。

所有这些谓词都匹配HTTP请求的不同属性。多种谓词工厂可以组合,并通过逻辑and.

> 注意到上面yml配置中，有个predicates 属性值。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597563769828.png)

具体使用：

### After/Before/Between

新建测试类T2

```java
public class T2 {

    public static void main(String[] args) {
    	//获取当前时间串
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        //2020-06-17T11:53:40.325+08:00[Asia/Shanghai]
    }

}
```

然后在yml中的`predicates:`加上

```yml
			#指定时间后才能访问（After）时间往后写一小时
            - After=2020-06-17T12:53:40.325+08:00[Asia/Shanghai]
```

### Cookie

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617152542645.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

Cookie Route Predicate需要两个参数,一个是Cookie name ,一个年则表达式。
路由规则会通过获取对应的Cookie name值和正则表达式去匹配，如果匹配上就会执行路由,如果没有匹配上则不执行

在yml中的`predicates:`加上（记得把after的时间改成已经过去的时间，时间没到访问不了）

```yml
          - Cookie=username,angenin   #带Cookie，并且username的值为angenin
```

1. 不带cookie访问
   打开终端，输入`curl http://localhost:9527/payment/lb`（直接访问失败）
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617154450395.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)
2. 带cookie访问
   输入`curl http://localhost:9527/payment/lb --cookie "username=angenin"`
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617155016820.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### Header

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617155221124.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617155209547.png)
注释掉其他两个，加上Header

```yml
#            - After=2020-06-17T12:53:40.325+08:00[Asia/Shanghai]
#            - Cookie=username,angenin   #带Cookie，username的值为angenin
            - Header=X-Request-Id, \d+   #请求头要有 X-Request-Id属性并且值为整数的正则表达式
```

重启9527，然后在终端输入`http://localhost:9527/payment/lb -H "X-Request-Id:123"`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617155839384.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### Host

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617161726732.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)
加上：

```yml
            - Host=**.angenin.com	#Host: xxx.angenin.com 请求是Host必须有**.angenin.com
```

重启9527
`http://localhost:9527/payment/lb -H "Host: www.angenin.com"`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617162116639.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### Method

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617163007922.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

```yml
            - Method=GET	#只允许get请求访问
```

### Path

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617160128510.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

```yml
			#访问的url地址有 /payment/lb/ 才能访问
			- Path=/payment/lb/**	
```

已经用过了，这里不进行演示。

### Query

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617163131624.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

```yml
            - Query=username, \d+   #url请求地址必须带上username参数，并且值必须为整数
```

`http://localhost:9527/payment/lb?username=110`
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617163931254.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617164126851.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200617164140278.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### 总结

说白了，Predicate就是为了实现一-组匹配规则,让请求过来找到对应的Route进行处理。

> 放爬虫思路，前后端分离的话，只限定前端项目主机访问，这样可以屏蔽大量爬虫。
>
> 例如我加上： - Host=localhost:**       ** 代表允许任何端口
>
> 就只能是主机来访

配置错误页面:

> 注意，springboot默认/static/error/ 下错误代码命名的页面为错误页面，即 404.html
>
> 而且不需要导入额外的包，Gateway 里面都有。

### Filter

> 主要是配置全局自定义过滤器，其它的小配置具体看官网吧]

路由过滤器可用于修改进入的HTTP请求和返回的HTTP响应,路由过滤器只能指定路由进行使用。
Spring Cloud Gateway内置了多种路由过滤器，他们都由GatewayFilter的工厂 类来产生

GatewayFilter（31种）
Global Filter（10种）

这里以`AddRequestParameter`为代表。

自定义全局过滤器配置类：

```java
@Component
public class GateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("------come in MyGlobalFilter : "+ new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        //合法性检验
        if(uname == null){
            System.out.println("----用户名为null,非法用户，请求不被接受");
            //设置 response 状态码   因为在请求之前过滤的，so就算是返回NOT_FOUND 也不会返回错误页面
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            //完成请求调用
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    //返回值是加载顺序，一般全局的都是第一位加载
    @Override
    public int getOrder() {
        return 0;
    }
}
```

# 服务配置

## Config

> SpringCloud Config 分布式配置中心

概述
微服务意味着要将单体应用中的业务拆分成一个个子服务, 每个服务的粒度相对较小，因此系统中会出现大量的服务。于每个服务都需要必要的配置信息才能运行，所以一套集中式的、 动态的配置管理设施是必不可少的。

SpringCloud提供了ConfigServer来解决这个问题，我们每一个微服务自 己带着一个application.yml, 上百个配置文件的管理... .


![](https://img-blog.csdnimg.cn/20200617205312367.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

是什么
SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置。

怎么玩
SpringCloud Config分为服务端和客户端两部分。
服务端也称为分布式配置中心，它是个独立的微服务应用，用来连接配置服务器并为客户端提供获取配置信息,加密/解密信息等访问接口
客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容,并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具方便的管理和访问配置内容

能干嘛
集中管理配置文件
不同环境不同配置，动态化的配置更新,分环境部署比如dev/test/ prod/beta/release
运行期间动态调整配置，不再需要在每个服务部署的机器上编写配置文件,服务会向配置中心统一拉取配置自 己的信息
当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置
将配置信息以REST接口的形式暴露.

### 服务端配置

> 首先在github上新建一个仓库 springcloud-config
>
> 然后使用git命令克隆到本地，命令：git clone https://github.com/LZXYF/springcloud-config
>
> 注意上面的操作不是必须的，只要github上有就可以，克隆到本地只是修改文件。

新建 cloud-config-center3344 模块：

pom文件：

```xml
	<dependencies>
        <!-- config Server -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <!--eureka-client config Server也要注册进服务中心-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml 配置：

```yml
server:
  port: 3344
spring:
  application:
    name: cloud-config-center  # 注册进eureka Server 的微服务名
  cloud:
    config:
      server:
        git:
          uri: https://github.com/LZXYF/springcloud-config # github 仓库位置 码云也可以
          ## 搜索目录
          search-paths:
            - springcloud-config
          # 读取的分支
          label: master
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
```

主启动类：

```java
@SpringBootApplication
@EnableConfigServer   //关键注解
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class,args);
    }
}
```

添加模拟映射：【C:\Windows\System32\drivers\etc\hosts】文件中添加：  127.0.0.1 config-3344.com 

启动微服务3344，访问http://config-3344.com:3344/master/config-dev.yml 文件（注意，要提前在git上弄一个这文件）

文件命名和访问的规则：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597646186970.png)

不加分支名默认是master:

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597646308915.png)

### 客户端配置

> 这里的客户端指的是，使用 Config Server 统一配置文件的项目。既有之前说的消费者，又有提供者

新建 cloud-config-client-3355 模块：

pom文件：

```xml
	<dependencies>
        <!-- config Client 和 服务端的依赖不一样 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <!--eureka-client config Server也要注册进服务中心-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

 bootstrap.yml文件

是什么
applicaiton. yml是用户级的资源配置项
bootstrap. yml是系统级的，优先级更加高

Spring Cloud会创建一个“ Bootstrap Context"， 作为Spring应用的Application Context的父上下文。初始化的时候，Bootstrap Context负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的Environment。

Bootstrap属性有高优先级,默认情况下，它们不会被本地配置覆盖。 Bootstrap context和Application Context有着不同的约定，所以新增了一 个bootstrap.ymI文件, 保证Bootstrap Context和Application Context配置的分离。

要将Client模块下的application.yml文件改为bootstrap.yml,这是很关键的,
因为bootstrap.ym是比application.yml先加载的。bootstrap.yml优先级高于application.yml

内容：

```yml
server:
  port: 3355

spring:
  application:
    name: config-client
  cloud:
    # config 客户端配置
    config:
      label: master # 分支名称
      name: config # 配置文件名称，文件也可以是client-config-dev.yml这种格式的，这里就写 client-config 
      profile: dev # 使用配置环境
      uri: http://config-3344.com:3344  # config Server 地址
      # 综合上面四个 即读取配置文件地址为： http://config-3344.com:3344/master/config-dev.yml

eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
```

主启动类，极其普通：

```java
@SpringBootApplication
@EnableEurekaClient
public class ConfigClientMain3355 
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientMain3355.class, args);
    }
}
```

controller层，测试读取配置信息

```java
package com.dkf.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
```

启动测试完成！如果报错，注意github上的 yml 格式有没有写错！ 

### 动态刷新

问题：

Linux运维修改GitHub.上的配置文件内容做调整
刷新3344,发现ConfigServer配置中心立刻响应
刷新3355，发现ConfigClient客户端没有任何响应
3355没有变化除非自己重启或者重新加载
难到每次运维修改配置文件，客户端都需要重启? ?噩梦

> 就是github上面配置更新了，config Server 项目上是动态更新的，但是，client端的项目中的配置，目前还是之前的，它不能动态更新，必须重启才行。

解决：

1. client端一定要有如下依赖：

    ```xml
     <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
     </dependency>
    ```

2. client 端增加 yml 配置如下，即在 bootstrap.yml 文件中：

```yml
# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

3. 在ConfigClientController类上加上`@RefreshScope`注解

> 到此为止，配置已经完成，但是测试仍然不能动态刷新，需要下一步。

4. 向 client 端发送一个 POST 请求

> 如 curl -X POST "http://localhost:3355/actuator/refresh"
>
> 两个必须：1.必须是 POST 请求，2.请求地址：http://localhost:3355/actuator/refresh

成功！

但是又有一个问题，就是要向每个微服务发送一次POST请求，当微服务数量庞大，又是一个新的问题。

就有下面的消息总线！

# 消息总线

## Bus

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597651086179.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/adwyiastydoi26901e5aj.png)

什么是总线
在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题， 并让系统中所有微服务实例都连接上来。由于该注题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播-些需要让其他连接在该主题上的实例都知道的消息。

基本原理
ConfigClient实例都监听MQ中同一个topic(默认是springCloudBus)。当-个服务刷新数据的时候，它会把这个信息放入到Topic中，这样其它监听同一Topic的服务就能得到通知，然后去更新自身的配置。

### 安装RabbitMQ

> 在windows 上安装RabbitMQ

1. 安装RabbitMQ的依赖环境 Erlang  下载地址： http://erlang.org/download/otp_win64_21.3.exe 
2. 安装RabbitMQ   下载地址： http://dl.bintray.com/rabbitmq/all/rabbitmq-server/3.7.14/rabbitmq-server-3.7.14.exe 
3. 进入 rabbitMQ安装目录的sbin目录下，打开cmd窗口，执行 【rabbitmq-plugins enable rabbitmq_management】
4. 访问【http://localhost:15672/】，输入密码和账号：默认都为 guest

#### Docker安装RabbitMQ

[Docker基础入门学习笔记](https://blog.csdn.net/qq_36903261/article/details/105870268)，有兴趣的可以看一下。

在linux的docker里拉取RabbitMQ镜像`docker pull rabbitmq:3.8.3-management`（management是带web的管理界面）。
5672是客户端和RabbitMQ进行通信的端口。
15672是管理界面访问web页面的端口。

运行RabbitMQ

```shell
docker run -d -p 5672:5672 -p 15672:15672 --name myRabbitMQ 容器id
```

### 广播式刷新配置

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597723463350.png)

还是按照之前的 3344（config Server）和  3355（config client）两个项目来增进。

首先给 config Server 和 config client 都添加如下依赖：

```xml
	<!-- 添加rabbitMQ的消息总线支持包 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
```

config Server 的yml文件增加如下配置：

```yml
# rabbitMq的相关配置  这里的都单独写 
rabbitmq:
  host: localhost
  port: 5672  # 这里没错，虽然rabbitMQ网页是 15672
  username: guest
  password: guest
# rabbitmq 的相关配置2 暴露bus刷新配置的端点
management:
  endpoints:
    web:
      exposure:
        include: 'bus-refresh'
```

config Client 的yml文件修改成如下配置：（注意对齐方式，和config Server不一样）

```yml
spring:
  application:
    name: config-client
  cloud:
    # config 客户端配置
    config:
      label: master         # 分支名称
      name: client-config       # 配置文件名称
      profile: test      # 使用配置环境
      uri: http://config-3344.com:3344  # config Server 地址
  # 综合上面四个 即读取配置文件地址为： http://config-3344.com:3344/master/config-dev.yml
  # rabbitMq的相关配置
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

出现spring cloud rabbitmq An unexpected connection driver error occured错误的解决办法：

https://blog.csdn.net/luo609630199/article/details/100603185

可在github上修改yml文件进行测试，修改完文件，向 config server 发送 请求：

【curl -X POST "http://localhost:3344/actuator/bus-refresh"】

> 注意，之前是向config client 一个个发送请求，但是这次是向 config Server 发送请求，而所有的config client 的配置也都全部更新。

### 定点通知

指定具体某- - -个实例生效而不是全部

公式:    `http://localhost:配置 中心的端口号/ actuator/bus-refresh/ {destination}`

/bus/refresh请求不再发送到具体的服务实例上,而是发给config server并
通过destination参数类指定需要更新配置的服务或实例

我们这里以刷新运行在3355端口上的config-client为例.

`curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"` 

总结

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200618145606746.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

# 消息驱动

## Stream

### 概述

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200618151201404.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

屏蔽底层消息中间件的差异,降低切换成本，统一消息的编程模型

> 就像 JDBC 形成一种规范，统一不同数据库的接口

什么是SpringCloudStream
官方定义Spring Cloud Stream是一个构建消息驱动微服务的框架。

应用程序通过inputs或者outputs来与Spring Cloud Stream中binder对象交互。
通过我们配置来binding(绑定)，而Spring Cloud Stream的binder对象负责与消息中间件交互。
所以，我们只需要搞清楚如何与Spring Cloud Stream交互就可以方便使用消息驱动的方式。

通过使用Spring Integration来连接消息代理中间件以实现消息事件驱动。
Spring Cloud Stream为-些供应商的消息 中间件产品提供了个性化的自动化配置实现，引用了发布-订阅、消费组、分区的三个核心概念。

目前仅支持RabbitMQ、Kafka.

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597726446212.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597730581088.png)

### 消息生产者

新建模块 cloud-stream-rabbitmq-provider8801 

pom依赖：

```xml
<!-- stream-rabbit -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
    </dependency>
    <!--eureka-client 目前，这个不是必须的-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
        <groupId>com.dkf.cloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
```

yml 配置：

```yml
server:
  port: 8801
spring:
  application:
    name: cloud-stream-provider
  cloud:
    stream:
      binders: # 在次配置要绑定的rabbitMQ的服务信息
        defaultRabbit: # 表示定义的名称，用于和binding整合
          type: rabbit  # 消息组件类型
          environment:  # 设置rabbitmq的相关环境配置
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings:  # 服务的整合处理
        output:   # 表示是生产者，向rabbitMQ发送消息
          destination: studyExchange  # 表示要使用的Exchange名称
          content-type: application/json  # 设置消息类型，本次是json，文本是 "text/plain"
          binder: defaultRabbit  # 设置要绑定的消息服务的具体配置
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
  instance:
    lease-renewal-interval-in-seconds: 2 # 设置心跳时间，默认是30秒
    lease-expiration-duration-in-seconds: 5 # 最大心跳间隔不能超过5秒,默认90秒
    instance-id: send-8801.com # 在信息列表显示主机名称
    prefer-ip-address: true # 访问路径变为ip地址
```

主启动类没什么特殊的注解。

业务类：（此业务类不是以前的service，而实负责推送消息的服务类）

```java
package com.dkf.springcloud.service;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class)  // 不是和controller打交道的service,而是发送消息的推送服务类
public class IMessageProviderImpl implements IMessageProvider {
									     //上面是自定义的接口
    @Resource
    private MessageChannel output;

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("******serial: " + serial);
        return null;
    }
}
```

controller:

```java
@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider messageProvider;

    @GetMapping("/sendMessage")
    public String sendMessage(){
        return messageProvider.send();
    }
}
```

> 启动Eureka Server 7001，再启动8801，进行测试，看是否rabbitMQ中有我们发送的消息。
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597730112088.png)

### 消息消费者

新建模块 cloud-stream-rabbitmq-consumer8802

pom依赖和生产者一样。

yml配置: 在 stream的配置上，和生产者只有一处不同的地方，output 改成 input

```yml
server:
  port: 8802
spring:
  application:
    name: cloud-stream-consumer
  cloud:
    stream:
      binders: # 在次配置要绑定的rabbitMQ的服务信息
        defaultRabbit: # 表示定义的名称，用于和binding整合
          type: rabbit  # 消息组件类型
          environment:  # 设置rabbitmq的相关环境配置
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
                vhost: / #出现连接失败 加此行配置
      bindings:  # 服务的整合处理
        input:   # 表示是消费者，这里是唯一和生产者不同的地方，向rabbitMQ发送消息
          destination: studyExchange  # 表示要使用的Exchange名称
          content-type: application/json  # 设置消息类型，本次是json，文本是 "text/plain"
          binder: defaultRabbit  # 设置要绑定的消息服务的具体配置
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
  instance:
    lease-renewal-interval-in-seconds: 2 # 设置心跳时间，默认是30秒
    lease-expiration-duration-in-seconds: 5 # 最大心跳间隔不能超过5秒,默认90秒
    instance-id: receive-8802.com # 在信息列表显示主机名称
    prefer-ip-address: true # 访问路径变为ip地址
```

接收消息的业务类：

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
public class ConsumerController {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("消费者1号，serverport: " + serverPort + "，接受到的消息：" + message.getPayload());
    }
}
```

### 配置分组消费

新建 cloud-stream-rabbitmq-consumer8802 模块：

> 8803 就是 8802 clone出来的。

当运行时，会有两个问题。

第一个问题，两个消费者都接收到了消息，这属于重复消费。例如，消费者进行订单创建，这样就创建了两份订单，会造成系统错误。

注意在Stream中处于同一个group中的多个消费者是竞争关系,就能够保证消息只会被其中一个应用消费- -次。
不同组是可以全面消费的(重复消费),

> Stream默认不同的微服务是不同的组

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597731630685dgawk.png)

对于重复消费这种问题，导致的原因是默认每个微服务是不同的group，组流水号不一样，所以被认为是不同组，两个都可以消费。

解决的办法就是自定义配置分组：

消费者 yml 文件配置：

```yml
	# 8802 的消费者
	bindings:
        input:   
          destination: studyExchange  
          content-type: application/json  
          binder: defaultRabbit  
          group: dkfA  # 自定义分组配置
    # 8803 的消费者
	bindings:
        input:   
          destination: studyExchange  
          content-type: application/json  
          binder: defaultRabbit  
          group: dkfB  # 自定义分组配置
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597732035990.png)

当两个消费者配置的 group 都为 dkfA 时，就属于同一组，就不会被重复消费。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597732238270.png)

### 消息持久化

> 加上group配置，就已经实现了消息的持久化。

# 分布式请求链路追踪

> 分布式请求链路跟踪，超大型系统。需要在微服务模块极其多的情况下，比如80调用8001的，8001调用8002的，这样就形成了一个链路，如果链路中某环节出现了故障，我们可以使用Sleuth进行链路跟踪，从而找到出现故障的环节。

## 概述

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597732562780.png)

> sleuth 负责跟踪，而zipkin负责展示。
>
> zipkin 下载地址： http://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/2.12.9/zipkin-server-2.12.9-exec.jar
>
> 使用 【java -jar】 命令运行下载的jar包，访问地址：【 http://localhost:9411/zipkin/ 】

### 原理

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200619164047621.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200619164248683.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

Trace: 类似于树结构的Span集合，表示一条调用链路，存在唯一标识
span: 表示调用链路来源，通俗的理解span就是一次请求信息

## 案例

> 使用之前的 提供者8001 和 消费者80

分别给他们引入依赖：

```xml
	<!-- 引入sleuth + zipkin -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
```

yml增加配置：

```yml
spring:
  zipkin:
    base-url: http://localhost:9411  # zipkin 地址
  sleuth:
    sampler:
      # 采样率值 介于0-1之间 ，1表示全部采集
      probability: 1
```

在PaymentController中添加：

```java
   @GetMapping("/payment/zipkin")
   public String paymentZipkin(){
       return "paymentZipkin...";
   }
```

在OrderController中添加：

```java
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        String result = restTemplate.getForObject("http://localhost:8001" + "/payment/zipkin", String.class);
        return result;
    }
```

### 高级部分

# SpringCloud Alibaba

> alibaba 的 github上有中文文档

## 大简介

主要功能
●服务限流降级:默认支持WebServlet、 WebFlux, OpenFeign、 RestTemplate、 Spring Cloud Gateway, Zuul, Dubbo和
RocketMQ限流降级功能的接入，可以在运行时通过控制台实时修改限流降级规则，还支持查看限流降级Metrics监控。
●服务注册与发现:适配Spring Cloud服务注册与发现标准，默认集成了Ribbon的支持。
●分布式配置管理:支持分布式系统中的外部化配置，配置更改时自动刷新。
●消息驱动能力:基于Spring Cloud Stream为微服务应用构建消息驱动能力。
●分布式事务:使用@GlobalTransactional注解，高效并 且对业务零侵入地解决分布式事务问题。。
●阿里云对象存储:阿里云提供的海量、安全、低成本、高可靠的云存储服务。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据。
●分布式任务调度:提供秒级、精准、高可靠、高可用的定时(基于Cron表达式)任务调度服务。同时提供分布式的任务执行
模型，如网格任务。网格任务支持海量子任务均匀分配到所有Worker (schedulerx-client). 上执行。
●阿里云短信服务:覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。

组件
Sentinel:把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。
Nacos:一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
RocketMQ: -款开源的分布式消息系统，基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务。
Dubbo: Apache DubboTM是- -款高性能Java RPC框架。
Seata:阿里巴巴开源产品，一个易于使用的高性能微服务分布式事务解决方案。
Alibaba Cloud ACM:-款在分布 式架构环境中对应用配置进行集中管理和推送的应用配置中心产品。
Alibaba Cloud OSS:阿里云对象存储服务(Object Storage Service，简称OSS)，是阿里云提供的海量、安全、低成本、高可
靠的云存储服务。您可以在任何应用、任何时间、任何地点存储和访问任意类型的数据。
Alibaba Cloud SchedulerX:阿里中间件团队开发的一款分布式任务调度产品，提供秒级、精准、高可靠、高可用的定时(基于Cron表达式)任务调度服务。
Alibaba Cloud SMS:覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。
更多组件请参考Roadmap.

##  Nacos

> Nacos = Eureka + Config + Bus

> github地址：  https://github.com/alibaba/Nacos 
>
> Nacos 地址：  https://nacos.io/zh-cn/ 

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597755893534.png)

> nacos可以切换 AP 和 CP ,可使用如下命令切换成CP模式：
>
> curl -X PUT 'SNACOS SERVER:8848/nacos/v1/ns/operator/switches?entry= serverMode&value=CP '

### 下载 

> 下载地址：  https://github.com/alibaba/nacos/releases/tag/1.1.4 
>
> 直接下载网址： https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.zip
>
> 下载压缩包以后解压，进入bin目录，打开dos窗口，执行startup命令启动它。
>
> 可访问 ： 【 http://localhost:8848/nacos/index.html】地址，默认账号密码都是nacos
>
> ## 安装并运行nacos
>
> 在docker上安装nacos
>
> 拉取nacos镜像：
>
> ```shell
> docker pull nacos/nacos-server
> ```
>
> 运行nacos：
>
> ```shell
> docker run --env MODE=standalone --name nacos -d -p 8848:8848 nacos/nacos-server
> ```
>
> 在浏览器输入：`http://10.211.55.17:8848/nacos/`（10.211.55.17是我linux的IP地址）
> 账号和密码都是`nacos`。

### 服务中心

#### 提供者

新建模块 cloudalibaba-provider-payment9001

pom依赖：

```xml
	<dependencies>
        <!-- springcloud alibaba nacos 依赖 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- springboot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 日常通用jar包 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml 配置：

```yml
server:
  port: 9001
  
spring:
  application:
    name: nacos-provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848

management:
  endpoints:
    web:
      exposure:
        include: '*'
```

​      主启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
public class PaymentMain9001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9001.class, args);
    }

}
```

  PaymentController

```java
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;


    @GetMapping("/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id){
        return "nacos registry, serverPort: " + serverPort + "\t id: " + id;
    }

}
```

<font color=red>Nacos 自带负载均衡机制</font>，下面创建第二个提供者。

新建 cloudalibaba-provider-payment9003 提供者模块，clone 9001 就可以

#### 消费者

新建消费者 模块： cloudalibaba-customer-order80

pom依赖和主启动类没有好说的，和提供者一致，yml依赖也是类似配置，作为消费者注册进nacos服务中心。

yml

```yml
server:
  port: 83


spring:
  application:
    name: nacos-order-consumer
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848  #配置的Nacos地址（本机的写localhost:8848，服务器的写IP地址）


#消费者要访问的微服务名称（成功注册进nacos的服务提供者）
service-url:
  nacos-user-service: http://nacos-payment-provider
```

nacos底层也是ribbon，注入ReatTemplate

```java
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

controller :

```java
@RestController
public class OrderController {

    //在yml里面写的提供者服务路径，  值为：http://nacos-provider
    @Value("${service-url.nacos-user-service}")
    private String nacos_user_service;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("customer/nacos/{id}")
    public String orderId(@PathVariable("id")Integer id){
        return restTemplate.getForObject(nacos_user_service + "/payment/nacos/" + id, String.class);
    }
}
```

### 整合Feign

在消费者83

1. 在pom中导入

   ```xml
   <!-- openfeign -->
   <dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-openfeign</artifactId>
   </dependency>
   ```

2. 在主启动类上加上`@EnableFeignClients`，激活feign。

3. 注释掉config配置类的`@Configuration`注解，不使用RestTemplate。

4. 新建service.PaymentFeignService接口

   ```java
   @Component
   @FeignClient(value = "nacos-payment-provider")
   public interface PaymentFeignService {
   
       @GetMapping("/payment/nacos/{id}")
       public String getPayment(@PathVariable("id") Integer id);
   
   }
   ```

5. 注释掉OrderNacosController中的restTemplate对象和paymentInfo方法。

6. 在OrderNacosController中添加

   ```java
   @Resource
   private PaymentFeignService paymentFeignService;
   
       @GetMapping("/consumer/payment/feign/nacos/{id}")
       public String paymentInfo2(@PathVariable("id") Long id){
           return restTemplate.getForObject(serverURL + "/payment/feign/nacos/" + id, String.class);
       }
   ```

![](https://gitee.com/zssea/picture-bed/raw/master/img/waysiuytadisdawdf.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/adwusaioyu2.png)

Nacos支持AP和CP模式的切换

C是所有节点在同一时间看到的数据是一致的; 而A的定义是所有的请求都会收到响应。

何时选择使用何种模式?
-般来说,
如果不需要存储服务级别的信息且服务实例是通过nacos-client注册，并能够保持心跳上报，那么就可以选择AP模式。当前主流的服务如Spring cloud和Dubbo服务，都适用于AP模式，AP模式为了服务的可能性而减弱了一致性,因此AP模式下只支持注册临时实例。
如果需要在服务级别编辑或者存储配置信息，那么CP是必须, K8S服务和DNS服务则适用于CP模式。
CP模式下则支持注册持久化实例，此时则是以Raft协议为集群运行模式，该模式下注册实例之前必须先注册服务,如果服务不存在,则会返回错误。

`curl -X PUT '$NACOS_ SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP'`

### 配置中心

> nacos 还可以作为服务配置中心，下面是案例，创建一个模块，从nacos上读取配置信息。
>
> nacos 作为配置中心，不需要像springcloud config 一样做一个Server端模块。

新建模块 cloudalibaba-nacos-config3377

pom依赖：

```xml
 <dependencies>
        <!-- 以 nacos 做服务配置中心的依赖 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
        </dependency>
        <!-- springcloud alibaba nacos 依赖 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- springboot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 日常通用jar包 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

主启动类也是极其普通：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class CloudAlibabaConfigMain3377 {
    public static void main(String[] args) {
        SpringApplication.run(CloudAlibabaConfigMain3377.class,args);
    }
}
```

why配置两个
Nacos同springcloud-config-样,在项目初始化时,要保证先从配置中心进行配置拉取,
拉取配置之后，才能保证项目的正常启动。

springboot中配置文件的加载是存在优先级顺序的，bootstrap优先级高 于application

***bootstrap.yml 配置：

```yml
server:
  port: 3377
spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848  # nacos作为服务注册中心
      config:
        server-addr: localhost:8848 # nacos作为服务配置中心
        file-extension: yaml # 指定yaml 格式的配置
```

controller 层进行读取配置测试：

```java
@RestController
@RefreshScope  //支持Nacos的动态刷新
public class ConfigClientController {

    @Value("${config.info}")
    private String configInfo;

    @GetMapping("configclient/getconfiginfo")
    public String getConfigInfo(){
        return configInfo;
    }
}
```

> 下面在 Nacos 中添加配置文件，需要遵循如下规则：



> 从上面可以看到重要的一点，配置文件的名称第二项，spring.profiles.active 是依据当前环境的profile属性值的，也就是这个值如果是 dev，即开发环境，它就会读取 dev 的配置信息，如果是test，测试环境，它就会读取test的配置信息，就是从 spring.profile.active 值获取当前应该读取哪个环境下的配置信息。

所以要配置spring.profiles.active，新建application.yml文件，添加如下配置：

```yaml
spring:
  profiles:
    active: dev # 表示开发环境
```

说明：之所以需要配置 `spring.application.name` ，是因为它是构成 Nacos 配置管理 `dataId`字段的一部分。

在 Nacos Spring Cloud 中，`dataId` 的完整格式如下：

```plain
${prefix}-${spring.profiles.active}.${file-extension}
```

- `prefix` 默认为 `spring.application.name` 的值，也可以通过配置项 `spring.cloud.nacos.config.prefix`来配置。
- `spring.profiles.active` 即为当前环境对应的 profile，详情可以参考 [Spring Boot文档](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-profiles.html#boot-features-profiles)。 **注意：当 `spring.profiles.active` 为空时，对应的连接符 `-` 也将不存在，dataId 的拼接格式变成 `${prefix}.${file-extension}`**
- `file-exetension` 为配置内容的数据格式，可以通过配置项 `spring.cloud.nacos.config.file-extension` 来配置。目前只支持 `properties` 和 `yaml` 类型。

`最后公式:`
`${spring. application.name}- ${spring.profiles active}.${spring.cloud.nacos.config.file- extension}`

1. 通过 Spring Cloud 原生注解 `@RefreshScope` 实现配置自动更新：

```
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}
```

综合以上说明，和下面的截图，Nacos 的dataid（类似文件名）应为： nacos-config-client-dev.yaml  (必须是yaml)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597757775084.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597757747480.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597758227367.png)

当修改配置值，会发现 3377 上也已经修改，Nacos自带自动刷新功能！

其它说明：

> Nacos 的 Group ,默认创建的配置文件，都是在DEFAULT_GROUP中，可以在创建配置文件时，给文件指定分组。
>
> yml 配置如下，当修改开发环境时，只会从同一group中进行切换。
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597807821441.png)
>
> Nacos 的namespace ,默认的命名空间是public ,这个是不允许删除的，可以创建一个新的命名空间，会自动给创建的命名空间一个流水号。
>
> 在yml配置中，指定命名空间：
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597808181104.png)
>
> 最后，dataid、group、namespace 三者关系如下：（不同的dataid，是相互独立的，不同的group是相互隔离的，不同的namespace也是相互独立的）
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597808385154.png)

### Nacos持久化

> 上面只是小打小闹，下面才是真正的高级操作。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597808678658.png)

> 搭建集群必须持久化，不然多台机器上的nacos的配置信息不同，造成系统错乱。它不同于单个springcloud config，没有集群一说，而且数据保存在github上，也不同于eureka，配置集群就完事了，没有需要保存的配置信息。
>
> Nacos默认使用它自带的嵌入式数据库derby:
>
> ```xml
> <dependency>
> <groupId>org.apache.derby</groupId>
> <artifactId>derby</artifactd>
> </dependency>
> ```
>
> 说明
> 默认Nacos使用嵌入式数据库实现数据的存储。所以,如果启动多个默认配置下的Nacos节点,数据存储是存在一致性问题的。
> 为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署，目前只支持MySQL的存储。
>
> Nacos支持三种部署模式
>
>  ●单机模式-用于测试和单机试用。
> ●集群模式-用于生产环境，确保高可用。
> ●多集群模式-用于多数据中心场景。
>
> 单机模式下运行Nacos
> Linux/Unix/Mac
> ●Standalone means it is non-cluster Mode. * sh startup.sh -m standalone
> Windows
> cmd startup.cmd或者双击startup.cmd文件
>
> ### 单机模式支持mysql
>
> 在0.7版本之前，在单机模式时nacos使用嵌入式数据库实现数据的存储，不方便观察数据存储的基本情况。0.7版本增加了支持mysql数据源能力，具体的操作步骤：
>
> - 1.安装数据库，版本要求：5.6.5+
> - 2.初始化mysql数据库，数据库初始化文件：nacos-mysql.sql
> - 3.修改conf/application.properties文件，增加支持mysql数据源配置（目前只支持mysql），添加mysql数据源的url、用户名和密码。
>
> ```yml
> spring.datasource.platform=mysql
> 
> db.num=1
> db.url.0=jdbc:mysql://localhost:3306/nacos_config?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
> db.user=nacos
> db.password=youdontknow
> ```
>
> 重启nacos。
>
> nacos单机的持久化看这篇文章：[在Docker上用Nacos1.3容器连接MySQL5.6和8.0.18容器进入持久化的具体操作](https://blog.csdn.net/qq_36903261/article/details/106919191)

### 集群架构

> 现在进行企业中真正需要的nacos集群配置，而不是上面的单机模式，需要准备如下：
>
> 一台linux虚拟机：nginx服务器，3个nacos服务，一个mysql数据库。
>
> nginx的安装参考之前学，使用 ContOs7 至少需要安装gcc库，不然无法编译安装【yum install gcc】
>
> nacos下载linux版本的 tar.gz 包：https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.tar.gz
>
> mysql root用户密码为 Dkf!!2020
>
> docker配置nocos集群，请看做篇文章[在Docker上用3个Nacos1.3容器+一个MySQL5和8容器+一个Nginx容器进行集群的具体操作（Nacos集群版）](https://blog.csdn.net/qq_36903261/article/details/106835279)



Nacos集群配置

1. 首先对 nacos 进行持久化操作，操作如上面一致。

2. 修改 

3. nacos/conf 下的cluster文件，最好先复制一份，添加如下内容:

   ```shell
   #这个IP不能写127.0.0.1，必须是Linux命令hostname -i能够识别的IP
   192.168.40.100：3333
   192.168.40.100：4444
   192.168.40.100：5555
   ```

4. 模拟三台nacos服务，编辑nacos的startup启动脚本，使他能够支持不同的端口启动多次。

    集群启动，我们希望可以类似其它软件的shell命令，传递不同的端口号启动不同的nacos实例。
   命令: ./startup.sh -p 3333表示启动端口号为3333的nacos服务器实例，和上一步的cluster.conf配置的一致。	

   ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597812799242.png)

   ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597813020494.png)

   ![1597815675706](images\1597815675706.png)

   ```shell
   #执行方式
   ./startup.sh - p 3333
   ./startup.sh - p 4444
   ./startup.sh - p 5555
   ```

5. nginx配置负载均衡：nginx/conf

   ​	![](https://gitee.com/zssea/picture-bed/raw/master/img/1597813917440.png)

   ```shell
   #按照指令启动 记得放行nginx的1111端口
   ./nginx - C /usr/local/nginx/conf/nginx.conf
   ```

6. 测试通过nginx访问nacos

   `http://192.168.40.100:1111/nacos/#/login` 

   新建一个配置测试，linux服务器的mysql插入一条记录

   修改9002的yml文件

   ```yml
   # server-addr: localhost:8848  #配置的Nacos地址（本机的写localhost:8848，服务器的写IP地址）
   #改为下面这个，填自己linux的IP地址
   server-addr: 192.168.40.100:1111  #nginx的地址
   ```

   使用 9002 模块注册进Nacos Server 并获取它上面配置文件的信息，进行测试。
   
7. 总结

    ![](https://gitee.com/zssea/picture-bed/raw/master/img/awydjg6y728923iyd.png)

## Sentinel

> sentinel在 springcloud Alibaba 中的作用是实现熔断和限流

Sentinel具有以下特征:

●丰富的应用场景: Sentinel承接了阿里巴巴近10年的双十一大促流量的核心场景，例如秒杀(即突发流量控制在系统容
量可以承受的范围)、 消息削峰填谷、集群流量控制、实时熔断下游不可用应用等。
●完备的实时监控: Sentinel同时提供实时的监控功能。您可以在控制台中看到接入应用的单台机器秒级数据，甚至500
台以下规模的集群的汇总运行情况。
●广泛的开源生态: Sentinel提供开箱即用的与其它开源框架/库的整合模块，例如与Spring Cloud、 Dubbo、 gRPC的整
合。您只需要引入相应的依赖并进行简单的配置即可快速地接入Sentinel。
●完善的SPI扩展点: Sentinel提供简单易用、完善的SPI扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如
定制规则管理、适配动态数据源等。

### 下载

> Sentinel分为两个部分:
>        ●核心库(Java客户端)不依赖任何框架/库，能够运行于所有Java运行时环境，同时对Dubbo /
>        Spring Cloud等框架也有较好的支持。
>        ●控制台(Dashboard) 基于Spring Boot开发，打包后可以直接运行，不需要额外的Tomcat等应用
>        容器。
>
> 下载地址： https://github.com/alibaba/Sentinel/releases/download/1.7.1/sentinel-dashboard-1.7.1.jar
>
> 下载jar包以后，使用【java -jar】命令启动即可。
>
> 它使用 8080 端口，用户名和密码都为 ： sentinel
>
> #### docker
>
> ```shell
> #拉取sentinel镜像
> docker pull bladex/sentinel-dashboard
> 
> #运行sentinel（docker里的sentinel是8858端口）
> docker run --name sentinel -d -p 8858:8858 bladex/sentinel-dashboard
> 
> #把nacos和mysql也启动起来
> ```

![](https://user-images.githubusercontent.com/9434884/50505538-2c484880-0aaf-11e9-9ffc-cbaaef20be2b.png)

### Demo

> 新建模块 cloudalibaba-sentinel-service8401 ，使用nacos作为服务注册中心，来测试Sentinel的功能。

pom依赖：

```xml
	<dependencies>
        <!-- 后续做Sentinel的持久化会用到的依赖 -->
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
        <!-- sentinel  -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <!-- springcloud alibaba nacos 依赖,Nacos Server 服务注册中心 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <!-- springboot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 日常通用jar包 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml 配置：

```yml
server:
  port: 8401
spring:
  application:
    name: cloudalibaba-sentinel-service
  cloud:
    nacos:
      discovery:
        # 服务注册中心
        server-addr: localhost:8848
    sentinel:
      transport:
        # 配置 Sentinel Dashboard 的地址
        dashboard: localhost:8080
        # 默认8719 ，如果端口被占用，端口号会自动 +1，提供给 sentinel 的监控端口
        port: 8719
management:
  endpoints:
    web:
      exposure:
        include: '*'
```

主启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
public class MainApp8401 {

    public static void main(String[] args) {
        SpringApplication.run(MainApp8401.class, args);
    }
}
```

controller

```java
@RestController
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "----testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "----testB";
    }

}
```

### 流控规则

●资源名:唯一名称，默认请求路径
●针对来源: Sentinel可以针对调用者进行限流，填写微服务名，默认default (不区分来源)阈值类型/单机阈值:
      QPS (每秒钟的请求数量) :当调用该api的QPS达到阈值的时候， 进行限流
      线程数:当调用该api的线程数达到阈值的时候，进行限流
●是否集群:不需要集群，暂不研究
●流控模式:
       直接: api达到限流条件时，直接限流
       关联:当关联的资源达到阈值时，就限流自己
       链路:只记录指定链路上的流量(指定资源从入口资源进来的流量，如果达到阈值，就进行限流) [api级
别的针对来源]
●流控效果:
        快速失败:直接失败，抛异常
         Warm Up:根据codeFactor (冷加载因子，默认3)的值，从阈值/codeFactor, 经过预热时长，才达到设置
 的QPS阈值
         排队等待:匀速排队，让请求以匀速的速度通过，阈值类型必须设置为QPS，否则无效

阈值类型
QPS与线程数的区别
阈值类型/单机阈值:
        。QPS (每秒钟的请求数量) :当调用该api的QPS达到阈值的时候，进行限流
        。线程数:当调用该api的线程数达到阈值的时候，进行限流

流控模式--直接：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597819546992.png)

> 限流表现：当超过阀值，就会被降级。
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597819613273.png)

流控模式--关联：

当关联的资源达到阈值时，就限流自己
当与A关联的资源B达到阀值后,就限流A自己
B惹事，A挂了

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597820015308.png)

流控效果--预热：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597820393038.png)

默认coldFactor为3,即请求QPS从(threshold / 3)开始，经多少预热时长才逐渐升至设定的QPS阈值。
案例，阀值为10+预热时长设置5秒。
系统初始化的阀值为10/ 3约等于3,即阀值刚开始为3;然后过了5秒后阀值才慢慢升高恢复到10

![](https://gitee.com/zssea/picture-bed/raw/master/img/15978205341682.png)

流控效果--排队等待：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597820662461.png)

匀速排队，让请求以均匀的速度通过，阀值类型必须设成QPS,否则无效。
设置含义: /testA每秒1次请求，超过的话就排队等待，等待的超时时间为20000毫秒。

![](https://gitee.com/zssea/picture-bed/raw/master/img/awhsjktyfd2693hd.png)

### 熔断降级

![](https://gitee.com/zssea/picture-bed/raw/master/img/aytsfhgasy.png)

RT (平均响应时间，秒级)
平均响应时间超出阈值 且在时间窗口内通过的请求>=5, 两个条件同时满足后触发降级
窗口期过后关闭断路器
RT最大4900 (更大的需要通过-Dcsp.sentinel.statistic.max.rt= XXX才能生效)

异常比列(秒级)
QPS >= 5且异常比例(秒级统计)超过阈值时,触发降级;时间窗口结束后，关闭降级

Sentinel熔断降级会在调用链路中某个资源出现不稳定状态时(例如调用超时或异常比例升高)，对这个资源的调用进行限制,
让请求快速失败，避免影响到其它的资源而导致级联错误。
当资源被降级后,在接下来的降级时间窗口之内，对该资源的调用都自动熔断(默认行为是抛出DegradeException)。

Sentinel在1.8.0版本中更新了半开状态

降级策略--RT:

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597821156100.png)

降级策略--异常比例：

●异常比例( DEGRADE _GRADE_ EXCEPTION_ RATIO): 当资源的每秒请求量>= 5,并且每秒异常总数占通过量的比值超过阈值(DegradeRule中的count )之后,资源进入降级状态,即在接下的时间窗口( DegradeRule中的timeWindow,以s为单位)之内，对这个方法的调用都会自动地返回。异常比率的阈值范围是[0.0， 1.0]，代表0%- 100%。

降级测录--异常数：

●异常数(DEGRADE GRADE_ EXCEPTION _COUNT): 当资源近1分钟的异常数目超过阈值之后会进行熔断。注意由于统计时间窗口是分钟级别的,若timeWindow 小于60s,则结束熔断状态后的可能再进入熔断状态。

时间窗口一定要大于等于60秒。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597821548056.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597821618735.png)

### 热点Key限流

何为热点?热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的Top K数据，并对其访问进行限制。
比如:
商品ID为参数,统计-段时间内最常购买的商品ID并进行限制
商品ID为参数,针对一-段时间内频繁访问的用户ID进行限制
热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值与模式,对包含热点参数的资源调用进行限流。热点参数限
流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。

controller层写一个demo:

```java
	@GetMapping("/testhotkey")
    @SentinelResource(value = "testhotkey", blockHandler = "deal_testhotkey")
    //这个value是随意的值，并不和请求路径必须一致
    //在填写热点限流的 资源名 这一项时，可以填 /testhotkey 或者是 @SentinelResource的value的值
    public String testHotKey(
            @RequestParam(value="p1", required = false) String p1,
            @RequestParam(value = "p2", required = false) String p2
    ){
        return "testHotKey__success";
    }

	//类似Hystrix 的兜底方法
    public String deal_testhotkey(String p1, String p2, BlockException e){
        return "testhotkey__fail"; 
    }
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597821618735.png)

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597822772165.png)

说明：

@SentinelResource
处理的是Sentine1控制台配置的违规情况，有blockHandler方法配置的兜底处理;

RuntimeException
int age = 10/0, 这个是java运行时报出的运行时异常RunTimeException，@SentinelResource不管

总结
@SentinelResource主管配置出错，运行出错该走异常走异常

### 系统规则

> 一般配置在网关或者入口应用中，但是这个东西有点危险，不但值不合适，就相当于系统瘫痪。

Sentinel 系统自适应限流从整体维度对应用入口流量进行控制，结合应用的 Load、CPU 使用率、总体平均 RT、入口 QPS 和并发线程数等几个维度的监控指标，通过自适应的流控策略，让系统的入口流量和系统的负载达到一个平衡，让系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性。

系统保护规则是从应用级别的入口流量进行控制，从单台机器的load、CPU使用率、平均RT、入口QPS和并发线程数等几
个维度监控应用指标，让系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性。
系统保护规则是应用整体维度的，而不是资源维度的，并且仅对入口流量生效。入口流量指的是进入应用的流量
( EntryType.IN)，比如Web服务或Dubbo服务端接收的请求，都属于入口流量。
系统规则支持以下的模式:
●Load自适应(仅对Linux/Unix-like机器生效) :系统的load1作为启发指标，进行自适应系统保护。当系统load1 超过
设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护(BBR 阶段)。系统容量由系统的
maxQps * minRt估算得出。设定参考值一般是CPU cores * 2.5。
●CPU usage (1.5.0+版本) :当系统CPU使用率超过阈值即触发系统保护(取值范围0.0-1.0) ，比较灵敏。
●平均RT:当单台机器上所有入口流量的平均RT达到阈值即触发系统保护，单位是毫秒。
●并发线程数:当单台机器上所有入口流量的并发线程数达到阈值即触发系统保护。
●入口QPS:当单台机器上所有入口流量的QPS达到阈值即触发系统保护。

### @SentinelResource配置

> @SentinelResource 注解，主要是指定资源名（也可以用请求路径作为资源名），和指定降级处理方法的。

 按资源名称限流+后续处理

资源名称

在pom添加

```xml
 <!--换成你们直接的包名-->
 <!-- 引用自己定义的api通用包，可以使用Payment支付Entity -->
 <dependency>
     <groupId>com.dkf.springcloud</groupId>
     <artifactId>cloud-api-commons</artifactId>
     <version>${project.version}</version>
 </dependency>
```

新建RateLimitController：

```java
@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource() {
        return  new CommonResult(200,"按照资源名称限流测试",new Payment(2020L,"serial001"));
    }

	//兜底方法
    public CommonResult handleException(BlockException exception) {
        return  new CommonResult(444,exception.getClass().getCanonicalName() + "\t 服务不可用");
    }
}
```

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597901945492.png)

很明显，上面虽然自定义了兜底方法，但是耦合度太高，下面要解决这个问题。

按照URL地址限流+后续处理

在RateLimitController中添加：

```java
@GetMapping("/rateLimit/byUrl")
@SentinelResource(value = "byUrl")	//没有兜底方法，系统就用默认的
public CommonResult byUrl() {
    return  new CommonResult(200,"按照byUrl限流测试",new Payment(2020L,"serial002"));
}
```
![](https://gitee.com/zssea/picture-bed/raw/master/img/15823hshj.png)

上面兜底方案面临的问题
1 系统默认的，没有体现我们自己的业务要求。
2 依照现有条件,我们自定义的处理方法又和业务代码耦合在-块，不直观。
3 每个业务方法都添加一个兜底的，那代码膨胀加剧。
4 全局统一的处理方法没有体现。

###### 自定义全局BlockHandler处理类

写一个 CustomerBlockHandler 自定义限流处理类：

```java
public class CustomerBlockHandler {

    public static CommonResult handlerException(BlockException exception) {
        return  new CommonResult(444,"按照客户自定义限流测试，Glogal handlerException ---- 1");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return  new CommonResult(444,"按照客户自定义限流测试，Glogal handlerException ---- 2");
    }
}
```
在RateLimitController中添加：

```java
//CustomerBlockHandler
@GetMapping("/rateLimit/customerBlockHandler")
@SentinelResource(value = "customerBlockHandler",
        blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handlerException2")
public CommonResult customerBlockHandler() {
    return  new CommonResult(200,"按照客户自定义限流测试",new Payment(2020L,"serial003"));
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628174002805.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### 整合 Ribbon服务降级

#### 新建提供者

新建模块cloudalibaba-provider-payment9003

pom

```xml
<dependencies>
    <!-- SpringCloud ailibaba nacos-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- SpringCloud ailibaba sentinel-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <!-- 引用自己定义的api通用包，可以使用Payment支付Entity -->
    <dependency>
        <groupId>com.angenin.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--监控-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--热部署-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

yml

```yaml
server:
  port: 9003

spring:
  application:
    name: nacos-payment-provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848  #nacos

management:
  endpoints:
    web:
      exposure:
        include: '*'
```

主启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain9003 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain9003.class,args);
    }
}
```

controller

```java
@RestController
public class PaymentController {

    @Value("${server.port}")    //spring的注解
    private  String serverPort;

    public static HashMap<Long, Payment> map = new HashMap<>();
    static {
        map.put(1L,new Payment(1L,"1111"));
        map.put(2L,new Payment(2L,"2222"));
        map.put(3L,new Payment(3L,"3333"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = map.get(id);
        CommonResult<Payment> result = new CommonResult<>(200,"from mysql,serverPort: " + serverPort,payment);
        return result;
    }
}
```

按照9003新建9004

#### 新建消费者

新建模块cloudalibaba-consumer-nacos-order84

pom

```xml
<dependencies>
    <!-- SpringCloud ailibaba nacos-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    <!-- SpringCloud ailibaba sentinel-->
    <dependency>
        <groupId>com.alibaba.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
    </dependency>
    <!-- 引用自己定义的api通用包，可以使用Payment支付Entity -->
    <dependency>
        <groupId>com.angenin.springcloud</groupId>
        <artifactId>cloud-api-commons</artifactId>
        <version>${project.version}</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!--监控-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!--热部署-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

yml

```yaml
server:
  port: 84

spring:
  application:
    name: nacos-order-consumer
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848  #nacos
    sentinel:
      transport:
        dashboard: localhost:8858    #sentinel
        port: 8719

#消费者将去访问的微服务名称
server-url:
  nacos-user-service: http://nacos-payment-provider

#激活Sentinel对Feign的支持
feign:
  sentinel:
    enabled: true
```

主启动类

```java
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class OrderMain84 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain84.class,args);
    }
}
```

config

```java
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```

controller

```java
@RestController
@Slf4j
public class CircleBreakerController {

    public static  final  String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback")   //没有配置
    public CommonResult<Payment> fallback(@PathVariable Long id) {
        CommonResult<Payment> result = restTemplate.getForObject(
                SERVICE_URL + "/paymentSQL/" + id,CommonResult.class,id);

        if(id == 4){
            throw new IllegalArgumentException("IllegalArgument,非法参数异常...");
        }else if(result.getData() == null) {
            throw new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }

        return  result;
    }
    
}
```

启动9003，9004，84
`http://localhost:84/consumer/fallback/1`

`http://localhost:84/consumer/fallback/4`

`http://localhost:84/consumer/fallback/5`

#### 只配置fallback

修改84的CircleBreakerController类的fallback方法中的@SentinelResource注解，并在类中添加

```java
@SentinelResource(value = "fallback",fallback ="handlerFallback")   //只配置fallback（只负责业务异常）
```


```java
//fallback兜底
public CommonResult handlerFallback(@PathVariable Long id,Throwable e) {
    Payment payment = new Payment(id,"null");
    return new CommonResult(444,"异常handlerFallback，exception内容： " + e.getMessage(), payment);
}
```
#### 只配置blockHandler

修改@SentinelResource注解，并在类中添加

```java
    @SentinelResource(value = "fallback", blockHandler = "blockHandler")	//只配置blockHandler（只负责sentinel控制台配置违规）


    //blockHandler兜底
    public CommonResult blockHandler(@PathVariable Long id,BlockException e) {
        Payment payment = new Payment(id,"null");
        return new CommonResult(444,"blockHandler-sentinel 限流，BlockException： " + e.getMessage(), payment);
    }
```

重启项目
访问`http://localhost:84/consumer/fallback/1`，然后在sentinel后台进行配置。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628212019453.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

http://localhost:84/consumer/fallback/5

因为没配置指定fallback兜底方法，所以会直接显示错误页面，配置了blockHandler兜底方法，所以当sentinel配置违规会执行blockHandler兜底方法。

#### 配置fallback和blockHandler

修改@SentinelResource注解

```java
 @SentinelResource(value = "fallback", fallback ="handlerFallback", blockHandler = "blockHandler")
```

重启项目，输入`http://localhost:84/consumer/fallback/1`，然后到后台配置。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628213022729.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

`http://localhost:84/consumer/fallback/1`多次刷新执行blockHandler兜底方法。

`http://localhost:84/consumer/fallback/5`执行fallback兜底方法。

当@SentinelResource注解fallback和blockHandler都指定后，然后同时符合，优先执行blockHandler兜底方法。

#### 忽略属性

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628213538558.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

修改@SentinelResource注解：

```java
@SentinelResource(value = "fallback", 
        fallback ="handlerFallback", 
        blockHandler = "blockHandler", 
        exceptionsToIgnore = {IllegalArgumentException.class})
//如果出现exceptionsToIgnore中的异常，不运行fallback兜底方法。
```
### 整合 openfeign 服务降级

> 上面是单个进行 fallback 和 blockhandler 的测试，下面是整合 openfeign 实现把降级方法解耦。和Hystrix 几乎一摸一样！

还是使用上面 84 这个消费者做测试：

1. 先添加open-feign依赖：

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

2. yml 追加如下配置：

```yaml
# 激活Sentinel对Feign的支持
feign:
  sentinel:
    enabled: true
```

3. 主启动类添加注解 ： @EnableFeignClients  激活open-feign
4. service : 

```java
@FeignClient(value = "nacos-payment-provider", fallback = PaymentServiceImpl.class)
public interface PaymentService {

    @GetMapping("/payment/get/{id}")
    public CommonResult paymentSql(@PathVariable("id")Long id);
}
```

5. service 实现类：

```java
@Component
public class PaymentServiceImpl implements PaymentService {

    @Override
    public CommonResult paymentSql(Long id) {
        return new CommonResult(414, "open-feign 整合 sentinel 实现的全局服务降级策略",null);
    }
}
```

6. controller 层代码没什么特殊的，和普通调用service 一样即可。

   ```java
       //======= OpenFeign
       @Resource
       private PaymentService paymentService;
   
       @GetMapping(value = "/consumer/paymentSQL/{id}")
       public CommonResult< Payment > paymentSQL(@PathVariable("id") Long id){
           return paymentService.paymentSQL(id);
       }
   ```

7. 测试，关闭提供者的项目，会触发 service 实现类的方法。

8. 总结: 这种全局熔断，是针对 “访问提供者” 这个过程的，只有访问提供者过程中发生异常才会触发降级，也就是这些降级，是给service接口上这些提供者的方法加的，以保证在远程调用时能顺利进行。而且这明显是 fallback ，而不是 blockHandler，注意区分。

> fallback 和 blockHandler 肤浅的区别：
>
> F ： 不需要指定规则，程序内部异常均可触发（超时异常需要配置超时时间）
>
> B :  配上也没用，必须去 Sentinel 上指定规则才会被触发。 

### 异常忽略

> 这是 @SentinelResource 注解的一个值：
>
> ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628213538558.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### 熔断限流框架对比

![](https://gitee.com/zssea/picture-bed/raw/master/img/asgksdj256ij.png)

### 持久化

> 目前的sentinel 当重启以后，数据都会丢失，和 nacos 类似原理。需要持久化。它可以被持久化到 nacos 的数据库中。

1. pom依赖：

```xml
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

2. yml配置：

```yaml
spring:
  cloud:
    sentinel:
      datasource:
        ds1:  
          nacos:
            server-addr: localhost:8848
            dataId: ${spring.application.name}
            group: DEFAULT_GROUP
            data-type: json
            rule-type: flow
            
feign:
  sentinel:
    enabled: true #激活Sentinel 对Feign的支持   
```

3. [去nacos上创建一个dataid ,名字和yml配置的一致，json格式，内容如下：]()

```json
[
    {
        "resource": "/testA",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
```

- resource:资源名称;
  limitApp:来源应用;
  grade:阈值类型，0表示线程数，1表示QPS; .
  count:单机阈值;
  strategy:流控模式，0表示直接，1表示关联，2表示链路;
  controlBehavior:流控效果, 0表示快速失败, 1表示Warm Up，2表示排队等待;
  clusterMode:是否集群。

4. 启动应用，发现存在 关于 /testA 请求路径的流控规则。
5. 总结: 就是在 sentinel 启动的时候，去 nacos 上读取相关规则配置信息，实际上它规则的持久化，就是第三步，粘贴到nacos上保存下来，就算以后在 sentinel 上面修改了，重启应用以后也是无效的。

## Seata 

> Seata是一款开源的分布式事务解决方案，致力于在微服务架构下提供高性能和简单易用的分布式事务服务。
>
> 微服务模块，连接多个数据库，多个数据源，而数据库之间的数据一致性需要被保证。
>
> 官网：  http://seata.io/zh-cn/ 

单体应用被拆分成微服务应用,原来的三个模块被拆分成三个独立的应用，分别使用3三个独立的数据源,
业务操作需要调用三个服务来完成。此时每个服务内部的数据一致性由本地事务来保证, 但是全局的数据一致性问题没法保证 。

一句话：`一次业务操作需要跨多个数据源或需要跨多个系统进行远程调用，就会产生分布式事务问题`

Seata术语： 一 + 三 （分布式事务处理过程的一ID+三组件模型）

Transaction ID XID 全局唯一的事务ID

Transaction Coordinator (TC)：TC -事务协调者
维护全局和分支事务的状态，驱动全局事务提交或回滚。

Transaction Manager (TM)：TM-事务管理器
定义全局事务的范围:开始全局事务、提交或回滚全局事务。

Resource Manager (RM)：RM-资源管理器
管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚

处理过程
1. TM向TC申请开启一个全局事务，全局事务创建成功并生成一个全局唯一-的XID;
2. XID在微服务调用链路的上下文中传播;
3. RM向TC注册分支事务，将其纳入XID对应全局事务的管辖;
4. TM向TC发起针对XID的全局提交或回滚决议;
5. TC 调度XID下管辖的全部分支事务完成提交或回滚请求。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062912445482.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)



### 下载安装

> 下载地址 ： https://github.com/seata/seata/releases/download/v1.0.0/seata-server-1.0.0.zip

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200629124435961.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

SEATA的分布式交易解决方案:全局@GlobalTransactional



初始化操作

1. 修改 conf/file.conf 文件：

> 主要修改自定义事务组名称 + 事务日志存储模式为db + 数据库连接信息
>
> ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597990431346.png)

2. 创建名和 file.conf 指定一致的数据库。

3. 在新建的数据库里面创建数据表，db_store.sql文件在 conf 目录下（1.0.0有坑，没有sql文件，下载0.9.0的，使用它的sql文件即可）

4. 修改 conf/registry.conf 文件内容：

   ![](https://gitee.com/zssea/picture-bed/raw/master/img/1597986227251.png)

5. 先启动 nacos Server 服务，再启动seata Server 。

6. 启动 Seata Server 报错，在bin目录创建 /logs/seata_gc.log 文件。再次双击 bat文件启动。

如果seata运行闪退 请看这篇文章：https://blog.csdn.net/xyf13920745534/article/details/106458342

#### docker下载安装

mysql5.6：

```bash
#启动数据库容器（注意，我这里数据库暴露的是3305端口）
docker start 数据库容器ID
#docker run -p 3305:3306 --name mysql5.6 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.6

#进入mysql5.6容器
docker exec -it 容器ID /bin/bash

#进入mysql
mysql -uroot -p123456  --default-character-set=utf8

#创建seata数据库
create database seata character set utf8;
use seata;

#创建seata数据库需要的表（三张表）
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME(6),
    `gmt_modified`      DATETIME(6),
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(96),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

#因为之前已经弄过了nacos的持久化，已建了nacos_config数据库了，所以这里就不再赘述。

#退出数据库
exit

#退出容器
exit
```

nacos1.3：

```bash
#启动nacos
docker start nacos容器ID
#docker run --env MODE=standalone --name mynacos -d -p 8848:8848 -e MYSQL_SERVICE_HOST=10.211.55.26  -e MYSQL_SERVICE_PORT=3305  -e MYSQL_SERVICE_DB_NAME=nacos_config  -e MYSQL_SERVICE_USER=root  -e MYSQL_SERVICE_PASSWORD=123456  -e SPRING_DATASOURCE_PLATFORM=mysql  -e MYSQL_DATABASE_NUM=1 nacos/nacos-server
```

seata：

```bash
#拉取seata镜像（此时最新版为1.2）
docker pull seataio/seata-server

#运行seata
docker run --name myseata -d -h 10.211.55.26 -p 8091:8091 seataio/seata-server

#进入seata容器
docker exec -it 容器ID /bin/bash
cd resources
#因为容器没有装vim，所以我们要先安装vim
apt-get update
apt-get install vim
#备份文件
cp file.conf file.conf.bk
cp registry.conf registry.conf.bk

#修改file.conf文件（看下图）
vim file.conf
#seata1.2的file.conf里没有service模块，store的mode支持了redis
#mysql8的同学需要修改file.conf的驱动配置store.db.driver-class-name；并lib目录下删除mysql5驱动，添加mysql8驱动。
#按esc键然后:wq!退出

#修改文件（看下图）
vim registry.conf
#按esc键然后:wq!退出

#退出容器
exit

#重启容器
docker restart seata容器ID
```

file.conf

```xml
#service {
#  vgroupMapping.my_test_tx_group = "fsp_tx_group"
#  default.grouplist = "10.211.55.26:8091"
#  enableDegrade = false
#  disableGlobalTransaction = false
#}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200629222122110.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200629114507957.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200630091108446.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

```xml
jdbc:mysql://10.211.55.26:3305/seata_order?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8

```

registry.conf

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200629124027771.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)



`http://10.211.55.26:8848/nacos`，到nacos后台看seata是否成功注册进nacos。

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020063009152936.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

查看注册进nacos的seata信息是否正确。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200630091607905.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2OTAzMjYx,size_16,color_FFFFFF,t_70)

### 案例

#### 数据库准备

这里我们会创建三个服务, -个订单服务-个库存服务, -个账户服务。

当用户下单时,会在订单服务中创建一个订单 ，然后通过远程调用库存服务来扣减下单商品的库存,
再通过远程调用账户服务来扣减用户账户里面的余额,
最后在订单服务中修改订单状态为已完成。

该操作跨越三个数据库，有两次远程调用，很明显会有分布式事务问题。

创建三个数据库：

 seata_ order: 存储订单的数据库;
seata_ storage: 存储库存的数据库;
seata_ account: 存储账户信息的数据库。

每个数据库创建数据表：

order 库： 

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597988061545.png)

account 库： 

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597988768251.png)

storage 库：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597988262441.png)

三个数据库都创建一个回滚日志表，seata/conf/ 有相应的sql文件（1.0.0没有，依然使用0.9.0中的）。

最终效果：

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597989003349.png)

docker创建数据库

```bash
#进入mysql5.6容器
docker exec -it 容器ID /bin/bash

#进入mysql
mysql -uroot -p123456  --default-character-set=utf8

#创建业务数据库和对应的业务表

#order
create database seata_order;

use seata_order;

CREATE TABLE t_order(
`id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
`user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
`product_id` BIGINT(11)DEFAULT NULL COMMENT '产品id',
`count` INT(11) DEFAULT NULL COMMENT '数量',
`money` DECIMAL(11,0) DEFAULT NULL COMMENT '金额',
`status` INT(1) DEFAULT NULL COMMENT '订单状态: 0:创建中; 1:已完结'
)ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

select * from t_order;

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';


#storage
create database seata_storage;

use seata_storage;

CREATE TABLE t_storage(
`id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
`product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
`total` INT(11) DEFAULT NULL COMMENT '总库存',
`used` INT(11) DEFAULT NULL COMMENT '已用库存',
`residue` INT(11) DEFAULT NULL COMMENT '剩余库存'
)ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO t_storage(`id`,`product_id`,`total`,`used`,`residue`)VALUES('1','1','100','0','100');

SELECT * FROM t_storage;

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';


#account
create database seata_account;

use seata_account;

CREATE TABLE t_account(
`id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
`user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
`total` DECIMAL(10,0) DEFAULT NULL COMMENT '总额度',
`used` DECIMAL(10,0) DEFAULT NULL COMMENT '已用余额',
`residue` DECIMAL(10,0) DEFAULT '0' COMMENT '剩余可用额度'
)ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO t_account(`id`,`user_id`,`total`,`used`,`residue`)VALUES('1','1','1000','0','1000');

SELECT * FROM t_account;

CREATE TABLE IF NOT EXISTS `undo_log`
(
    `branch_id`     BIGINT(20)   NOT NULL COMMENT 'branch transaction id',
    `xid`           VARCHAR(100) NOT NULL COMMENT 'global transaction id',
    `context`       VARCHAR(128) NOT NULL COMMENT 'undo_log context,such as serialization',
    `rollback_info` LONGBLOB     NOT NULL COMMENT 'rollback info',
    `log_status`    INT(11)      NOT NULL COMMENT '0:normal status,1:defense status',
    `log_created`   DATETIME(6)  NOT NULL COMMENT 'create datetime',
    `log_modified`  DATETIME(6)  NOT NULL COMMENT 'modify datetime',
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='AT transaction mode undo table';


#退出mysql
exit

#退出容器
exit
```



#### 开发  

> 实现 下订单-> 减库存 -> 扣余额 -> 改（订单）状态
>
> 需要注意的是，下面做了 seata 与 mybatis 的整合，所以注意一下，和以往的mybatis的使用不太一样。

重复代码省略

##### 订单模块

新建模块 cloudalibaba-seata-order2001 ：

pom依赖：

```xml
	<dependencies>
        <!-- seata -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
            <exclusions>
                <exclusion>
                    <artifactId>seata-all</artifactId>
                    <groupId>io.seata</groupId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>io.seata</groupId>
            <artifactId>seata-all</artifactId>
            <version>1.0.0</version>
        </dependency>
        <!-- springcloud alibaba nacos 依赖,Nacos Server 服务注册中心 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>

        <!-- open feign 服务调用 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <!-- springboot整合Web组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!-- 持久层支持 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.10</version>
        </dependency>
        <!--mysql-connector-java-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jdbc-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>

        <!-- 日常通用jar包 -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency><!-- 引入自己定义的api通用包，可以使用Payment支付Entity -->
            <groupId>com.dkf.cloud</groupId>
            <artifactId>cloud-api-commons</artifactId>
            <version>${project.version}</version>
        </dependency>
    </dependencies>
```

yml配置：

```yaml
server:
  port: 2001
spring:
  application:
    name: seata-order-service
  cloud:
    alibaba:
      seata:
        # 自定义事务组，需要和当时在 seata/conf/file.conf 中的一致
        tx-service-group: dkf_tx_group
    nacos:
      discovery:
        server-addr: localhost:8848
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/seata_order
    username: root
    password: 123456
    
feign:
  hystrix:
    enabled: false

# 注意，这是自定义的，原来的是mapper_locations
mybatis:
  mapperLocations: classpath:mapper/*.xml

logging:
  level:
    io:
      seata: info
```

将 seata/conf/ 下的 file.conf 和 registry.cong 两个文件拷贝到 resource 目录下。

创建 domain 实体类 ： Order 和 CommonResult<T> 两个实体类。

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
```

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer count;

    private BigDecimal money;

    private Integer status; // 订单状态 0：创建中 1：已完结
}
```

dao :

```java
package com.dkf.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import com.dkf.springcloud.domain.Order;
import org.apache.ibatis.annotations.Param;

@Mapper
public class OrderDao {

    //创建订单
    public void create(Order order);

    //修改订单状态
    public void update(@Param("userId") Long userId, @Param("status") Integer status);

}
```

Mapper文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >

<mapper namespace="com.dkf.springcloud.dao.OrderDao">

    <!-- 以备后面会用到 -->
    <resultMap id="BaseResultMap" type="com.dkf.springcloud.domain.Order">
        <id column="id" property="id" jdbcType="BIGINT"></id>
        <result column="user_id" property="userId" jdbcType="BIGINT"></result>
        <result column="product_id" property="productId" jdbcType="BIGINT"></result>
        <result column="count" property="count" jdbcType="INTEGER"></result>
        <result column="money" property="money" jdbcType="DECIMAL"></result>
        <result column="status" property="status" jdbcType="INTEGER"></result>
    </resultMap>

    <insert id="create">
        insert into t_order(id, user_id, product_id, count, money, status)
        values (null, #{userId},#{productId},#{count},#{money},0)
    </insert>

    <update id="update">
        update t_order set status = 1 where user_id=#{userId} and status=#{status}
    </update>

</mapper>
```

创建service ： 

> 注意，AccountService和StrogeService是通过 open-feign 远程调用微服务的service

StorageService

```java
@FeignClient(value = "seata-storage-service")
public interface StorageService {

    //减库存
    @PostMapping(value = "/storage/decrease")
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
```

AccountService

```java
@FeignClient(value = "seata-account-service")
public interface AccountService {

    @PostMapping(value = "/account/decrease")
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
```

OrderService

```java
public interface OrderService {
    void create(Order order);
}
```

serviceImpl : 

```java
@Service
@Slf4j
public class OrderServiceImpl  implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    public void create(Order order) {
        log.info("--------》 开始创建订单");
        orderDao.create(order);
        log.info("--------》 订单微服务开始调用库存，做扣减---Count-");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("--------》 订单微服务开始调用库存，库存扣减完成！！");
        log.info("--------》 订单微服务开始调用账户，账户扣减---money-");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("--------》 订单微服务开始调用账户，账户扣减完成!!");
        //修改订单状态，从0到1
        log.info("--------》 订单微服务修改订单状态，start");
        orderDao.update(order.getUserId(),0);
        log.info("--------》 订单微服务修改订单状态，end");

        log.info("--订单结束--");
    }

    @Override
    public void update(Long userId, Integer status) {

    }
}
```

config （特殊点）:

```java
//下面是两个配置类，这个是和mybatis整合需要的配置
@Configuration
@MapperScan({"com.dkf.springcloud.alibaba.dao"})
public class MybatisConfig {
}


//这个是配置使用 seata 管理数据源，所以必须配置
package com.dkf.springcloud.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class DataSourceProxyConfig {

    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource){
        return new DataSourceProxy(dataSource);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }
}
```

主启动类：

```java
//这里必须排除数据源自动配置，因为写了配置类，让 seata 管理数据源
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableDiscoveryClient
public class SeataOrderMain2001 {

    public static void main(String[] args) {
        SpringApplication.run(SeataOrderMain2001.class,args);
    }
}
```

##### 库存模块

新建模块seata-storage-service2002

Storage

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage {

    private Long id;

    private Long productId;

    private Integer total;

    private Integer used;

    private Integer residue;
}
```

dao

```java
@Mapper
public interface StorageDao {

    void decrease(@Param("productId") Long productId, @Param("count") Integer count);

}
```

mapper
StorageMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.angenin.springcloud.dao.StorageDao">

    <resultMap id="BaseResultMap" type="com.angenin.springcloud.domain.Storage">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="product_id" property="productId" jdbcType="BIGINT"/>
        <result column="total" property="total" jdbcType="INTEGER"/>
        <result column="used" property="used" jdbcType="INTEGER"/>
        <result column="residue" property="residue" jdbcType="INTEGER"/>
    </resultMap>
    
    <update id="decrease">
        update t_storage
        set used = used + #{count}, residue = residue - #{count}
        where product_id= #{productId};
    </update>
</mapper>
```

service
StorageService

```java
public interface StorageService {

    void decrease(Long productId, Integer count);

}
```

impl
StorageServiceImpl

```java
@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        LOGGER.info("----> StorageService中扣减库存");
        storageDao.decrease(productId, count);
        LOGGER.info("----> StorageService中扣减库存完成");
    }
}
```

controller
StorageController

```java
@RestController
public class StorageController {

    @Resource
    private StorageService storageService;

	@RequestMapping("/storage/decrease")
	public CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count){
        storageService.decrease(productId, count);
        return new CommonResult(200, "扣减库存成功!");
    }

}
```

##### 账户模块

新建模块seata-account-service2003

Account

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    private Long id;

    private Long userId;

    private BigDecimal total;

    private BigDecimal used;

    private BigDecimal  residue;
}
```

dao
AccountDao

```java
@Mapper
public interface AccountDao {
    void decrease(@Param("userId") Long userId, @Param("money") BigDecimal money);
}
```

mapper
AccountMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.angenin.springcloud.dao.AccountDao">

    <resultMap id="BaseResultMap" type="com.angenin.springcloud.domain.Account">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <result column="user_id" property="userId" jdbcType="BIGINT"/>
        <result column="total" property="total" jdbcType="DECIMAL"/>
        <result column="used" property="used" jdbcType="DECIMAL"/>
        <result column="residue" property="residue" jdbcType="DECIMAL"/>
    </resultMap>

    <update id="decrease">
        update t_account
        set used = used + #{money}, residue = residue - #{money}
        where user_id = #{userId};
    </update>
</mapper>
```

service
AccountService

```java
public interface AccountService {
    void decrease(Long userId, BigDecimal money);
}
```

impl
AccountServiceImpl

```java
@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        LOGGER.info("---> AccountService中扣减账户余额");
        accountDao.decrease(userId, money);
        LOGGER.info("---> AccountService中扣减账户余额完成");
    }
}
```

controller
AccountController

```java
@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("/account/decrease")
    public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        accountService.decrease(userId, money);
        return new CommonResult(200, "扣减库存成功!");
    }

}
```

##### 正常下单

启动2001，2002，2003

在浏览器输入：`http://localhost:2001/order/create?userId=1&productId=1&count=10&money=10`

##### 超时异常

停止2003。

在2003的AccountServiceImpl里的decrease中添加

```java
//模拟超时异常，暂停20秒
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```

重新启动2003。

刷新页面`http://localhost:2001/order/create?userId=1&productId=1&count=10&money=10`

超时异常后，order添加了订单，而且storage的库存和account的余额都发生了变化。
因为feign调用时间默认是1秒，超过1秒就不等待，直接返回超时异常，但是account在20秒后还是会去扣余额，而且没有回滚，所以order添加了订单，storage的库存也发生了变化。
而且feign有超时重试机制，所以可能会多次扣款。

停止2001。

在2001的OderServiceImpl里的create方法上加上：

```java
    //name随便命名，只要不重复即可
 	//rollbackFor = Exception.class表示出现所有异常都回滚
    //rollbackFor表示哪些需要回滚
    //noRollbackFor表示哪些不需要回滚
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
```

重启2001。

刷新页面`http://localhost:2001/order/create?userId=1&productId=1&count=10&money=10`

订单没有添加，storage和account也没变化，回滚成功。

### Seata补充

2019年1月份蚂蚁金服和阿里巴巴共同开源的分布式事务解决方案

Simple Extensible Autonomous Transaction Architecture,简单可扩展自治事务框架

2020起始，参加工作后用1.0以后的版本

#### TC/TM/RM三组件

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597998982271.png)

原理三个阶段：

一阶段加载
在一阶段, Seata会拦截“业务SQL"
1解析SQL语义，找到“业务SQL"要更新的业务数据,在业务数据被更新前，将其保存成"before image”,
2执行“业务SQL"更新业务数据，在业务数据更新之后,
3其保存成"after image” ,后生成行锁。
以上操作全部在一个数据库事务内完成，这样保证了一阶段操作的原子性。

![](https://gitee.com/zssea/picture-bed/raw/master/img/1597999156669.png)

二阶段提交
二阶段如是顺利提交的话,
因为“业务SQL"在一阶段已经提交至数据库,所以Seata框架只需将一阶段保存的快照数据和行锁删掉, 完成数据清理即可.

![](https://gitee.com/zssea/picture-bed/raw/master/img/awygaskjty289o.png)

二阶段回滚:
二阶段如果是回滚的话，Seata 就需要回滚-阶段已经执行的“业务SQL"，还原业务数据。
回滚方式便是用"before image”还原业务数据;但在还原前要首先要校验脏写，对比”数据库当前业务数据”和"after image”,
如果两份数据完全一致就说明没有脏写， 可以还原业务数据,如果不一致就说明有脏写，出现脏写就需要转人工处理。

![](https://gitee.com/zssea/picture-bed/raw/master/img/agvsjd2689n.png)

补充

![](https://gitee.com/zssea/picture-bed/raw/master/img/ahckj2678sh.png)

