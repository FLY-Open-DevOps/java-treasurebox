## 一、生命周期四个阶段

### 1.1实例化

- 当用户第一次访问Servlet时，由容器调用Servlet的构造器创建具体的Servlet对象。也可以在容器启动之后立刻创建实例。
- < load-on-startup>1</ load-on-startup>(如果是0或正整数，就是一启动tomcat就创建了，如果是负数或者不写，就是请求访问时才创建)
- 注意：只执行一次

### 1.2初始化

- 在初始化阶段，init()方法会被调用。这个方法在javax.servlet.Servlet接口中定义。其中，方法以一个ServletConfig类型的对象作为参数。
- 注意：init方法只被执行一次

### 1.3服务

- 当客户端有一个请求时，容器就会将请求ServletRequest与响应ServletResponse对象转给Servlet,以参数的形式传给service方法。
- 此方法会执行多次

### 1.4销毀

- 当Servlet容器停止或者重新启动都会引起销毁Servlet对象并调用destroy方法。
- destroy方法执行一次

## 二、Servlet执行流程

| Servlet执行流程                                              |
| ------------------------------------------------------------ |
| ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200929132852759.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center) |
|                                                              |

```java
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(value = "/ls")
public class LifeServlet implements Servlet {

    public LifeServlet() {
        System.out.println("1.实例化");
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("2.初始化");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("3.提供服务，接收请求，响应结果");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("4.销毁");
    }
}
```