### 1.线程安全问题

- Servlet在访问之后，会执行实例化操作，创建一个Servlet对象。
- 而我们Tomcat容器可以同时多个线程并发访问同一个Servlet，如果在方法中对成员变量做修改操作，就会有线程安全的问题。

### 2.如何保证线程安全

- synchronized
  - 将存在线程安全问题的代码放到同步代码块中
- 实现 SingleThreadModel 接口
  - servlet实现SingleThreadModel接口后，每个线程都会创建servlet实例，这样每个客户端请求就不存在共享资源的问题，
  - 但是 servlet响应客户端请求的效率太低，所以已经淘汰。
- 尽可能使用局部变量

```java
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SafaServlet extends HttpServlet {
    //private String message = " ";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String message = " ";
        //假设
        // 1、接收参数
        //2、调用业务逻辑得到登录结果
        message = " 登录成功"; //登录失败!
        PrintWriter printWriter = resp. getWriter();
        printWriter. println (message) ;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
```