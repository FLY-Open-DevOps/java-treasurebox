## 一、引言

### 1.1C/S架构和B/S架构

- C/S和B/S是软件发展过程中出现的两种软件架构方式。

### 1.2 C/S架构（Client/Server 客户端/服务器）

- 特点：必须在客户端安装特定软件
- 优点：图形效果显示较好(如：3D游戏）
- 缺点：服务器的软件和功能进行升级，客户端也必须升级、不利于维护
- 常见的C/S程序：QQ、微信等

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192230426.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

### 1.2 B/S架构（Browser/Server 浏览器/服务器）

- 特点：无需安装客户端，任何浏览器都可直接访问
- 优点：涉及到功能的升级，只需要升级服务器端
- 缺点：图形显示效果不如C/S架构
- 需要通过HTTP协议访问

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192246948.jpg)

## 二、服务器

### 2.1概念

#### 2.1.1什么是Web

- Web(World Wide Web)称为万维网，简单理解就是网站，它用来表示Internet主机上供外界访问的资源。
- Internet上供外界访问的资源分为两大类：
  - 静态资源：指Web页面中供人们浏览的数据始终是不变的。（HTML、CSS)
  - 动态资源：指Web页面中供人们浏览的数据是甶程序产生的，不同时间点，甚至不同设备访问Web页面看到的内容各不相同。 (JSP/Servlet)
  - 在Java中，动态Web资源开发技术我们统称为Java Web。

#### 2.1.2什么是Web服务器

- Web服务器是运行及发布Web应用的容器，只有将开发的Web项目放置到该容器中，才能使网络中的所有用户通过浏览器进行访问。

### 2.2常见服务器

- 开源：OpenSource (1、开放源代码2、免费）
  - Tomcat(主流Web服务器之一，适合初学者）
  - jetty (淘宝，运行效率比Tomcat高）
  - resin (新浪，所有开源服务器软件中，运行效率最高的）
  - 三者的用法从代码角度完全相同，只有在开启、关闭服务器软件时对应的命令稍有区别。掌握一个即掌握所有
- 收费
  - WebLogic (Oracle)
  - WebSphere (IBM)
  - 提供相应的服务与支持，软件大，耗资源

### 2.3 Tomcat服务器

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192310851.jpg)

- Tomcat是Apache软件基金会（Apache Software Foundation)的Jakarta项目中的一个核心项目，免费开源、并支持 Servlet 和 JSP 规范。目前Tomcat最新版本为9.0。
- Tomcat技术先进、性能稳定，深受Java爱好者喜爱并得到了部分软件开发商的认可，成为目前比较流行的Web应用服务器。

### 2.4 Tomcat 安装

#### 2.4.1下载

- 官网下载（http://tomcat.apache.org/) Tomcat8.5解压缩版本

#### 2.4.2解压安装

- 将Tomcat解压到一个没有特殊符号的目录中（一般纯英文即可)
- 注意
  - 不建议将服务器软件放在磁盘层次很多的文件夹
  - 不建议放在中文路径下

#### 2.4.3 Tomcat目录结构

| 文件夹  | 说明                                                         | 备注                                                         |
| ------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| bin     | 该目录下存放的是二进制可执行文件                             | startup.bat启动Tomcat、shutdown.bat停止Tomcat                |
| conf    | 这是一个非常重要的目录，这个目录下有两个最为重要的文件server.xml和web.xml | server.xml:配置整个服务器信息。例如修改端口号，编码格式等。 web.xml:项目部署描述符文件，这个文件中注册了很多MIME类型，即文档类型。 |
| lib     | Tomcat的类库，里面存放Tomcat运行所需要的jar文件。            |                                                              |
| logs    | 存放日志文件，记录了Tomcat启动和关闭的信息，如果启动Tomcat时有错误，异常也会记录在日志文件中。 |                                                              |
| temp    | Tomcat的临时文件，这个目录下的东西在停止Tomcat后删除。       |                                                              |
| webapps | 存放web项目的目录，其中每个文件夹都是一个项目 ;其中ROOT是一个特殊的项目，在地址栏中没有给出项目目录时，对应的就是ROOT项目。 |                                                              |
| work    | 运行时生成的文件，最终运行的文件都在这里。                   | 当客户端用户访问一个JSP文件时，Tomcat会 通过JSP生成Java文件，然后再编译Java文件生成class文件，生成的java和class文件都会存放到这个目录下。 |

### 2.5 Tomcat启动和停止

#### 2.5.1启动

- 进入tomcat安装目录bin下，双击击startup.bat启动程序，出现如下界面

 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192327271.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

#### 2.5.2验证

打开浏览器，输入 http://localhost:8080 如果出现以下界面证明Tomcat启动成功。

 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192339723.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

#### 2.5.3停止

双击shutdown.bat即可关闭Tomcat启动窗口。

#### 2.5.4修改端口号

- Tomcat默认端口号为8080，可以通过conf/server.xml文件修改

```xml
<Connector port="8080" protocol = "HTTP/1.1" 
           connectionTimeout="20000" 
           redirectPort="8443"/>
123
```

- 注意：修改端口号需要重新启动Tomcat才能生效

### 2.6项目部署及访问静态资源

- Tomcat是Web服务器，我们的项目应用是部署在webapps下，然后通过特定的URL访问。

#### 2.6.1创建项目

- 在webapps中建立文件夹(项目应用)，比如: myweb

  - 创建WEB-INF文件夹， 用于存放项目的核心内容
    - 创建classes,用于存放.class文件
    - 创建lib,用于存放jar文件
    - 创建web.xml, 项目配置文件(到ROOT项目下的WEB-INF复制即可)
  - 把网页hello.html复制到myweb文件夹中， 与WEB-INF在同级目录

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192356586.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

#### 2.6.2 URL访问资源

- 浏览器地址中输入URL: http://localhost:8080/myweb/hello.html

- 经验：URL主要有4部分组成：协议、主机、端口、资源路径

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200808192409740.jpg)

#### 2.6.3Tomcat响应流程图

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080819242296.jpg)

### 2.7常见错误

#### 2.7.1 Tomcat控制台闪退

- 闪退问题是由于JAVA_ HOME配置导致的，检查JAVA_ HOME配置是否正确

#### 2.7.2 404

- 访问资源不存在，出现404错误

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080819243731.jpg)