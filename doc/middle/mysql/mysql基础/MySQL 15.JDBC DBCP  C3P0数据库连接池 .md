#### 数据库连接池

**数据库连接–执行完毕–释放 十分浪费系统资源**

**池化技术:准备一些预先的资源,过来就连接预先准备好的**

**最小连接数: 10**

**最大连接数:20**

**等待超时:100ms**

**编写连接池,实现一个接口 DataSource**

**开源数据源实现**

DBCP

C3P0

Druid:阿里巴巴

**使用了这些数据库连接池之后,我们在项目开发中就不需要编写连接数据库的代码了!**

#### **DBCP**

需要用到的jar包都可以在阿里云仓库找到 commons-dbcp-1.4.jar commons-pool-1.6.jar

**DBCP连接池配置**

```
\#连接设置 driverClassName=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/jdbcstudy?userUnicode=true&characterEncoding=utf8&uesSSL=true 
username=root 
password=123456 
#<!-- 初始化连接 --> initialSize=10 

#最大连接数量 maxActive=50 

#<!-- 最大空闲连接 --> maxIdle=20

#<!-- 最小空闲连接 --> minIdle=5

#<!-- 超时等待时间以毫秒为单位 6000毫秒/1000等于60秒 --> maxWait=60000 

#JDBC驱动建立连接时附带的连接属性属性的格式必须为这样：【属性名=property;】 #注意："user" 与 "password" 两个属性会被明确地传递，因此这里不需要包含他们。

connectionProperties=useUnicode=true;characterEncoding=utf8

#指定由连接池所创建的连接的自动提交（auto-commit）状态。 defaultAutoCommit=true 

#driver default 指定由连接池所创建的连接的只读（read-only）状态。

#如果没有设置该值，则“setReadOnly”方法将不被调用。（某些驱动并不支持只读模式，如：Informix） defaultReadOnly= 

#driver default 指定由连接池所创建的连接的事务级别（TransactionIsolation）。

#可用值为下列之一：（详情可见javadoc。）NONE,READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE defaultTransactionIsolation=READ_COMMITTED
```



**从数据源中获取连接**

```java
import org.apache.commons.dbcp.BasicDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class jdbcutils_dbcp {

   private static   DataSource source=null;
    static {
        try {

            InputStream in =jdbcutils_dbcp.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties properties = new Properties();
            properties.load(in);
          //创建数据源 工厂模式--> 创建对象
             source = BasicDataSourceFactory.createDataSource(properties);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 获取连接
    public static Connection getConnection() throws SQLException {
        //从数据源中获取连接
        return source.getConnection();
    }
    //释放连接资源
    public static void  release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(conn!=null) conn.close();

        if(st!=null) st.close();

        if(rs!=null) rs.close();
    }
}
```

**测试连接和查询**

```java
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testdbcp {
    public static void main(String[] args) throws SQLException {
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            //获取连接
            connection = jdbcutils_dbcp.getConnection();
            //sql
            String sql="SELECT * from users WHERE id>?";
            //预编译sql
            statement= connection.prepareStatement(sql);
            //设置参数
            statement.setObject(1,1);
            //执行sql
            rs=statement.executeQuery();
            //遍历结果
            while (rs.next()){
                System.out.println(rs.getObject("NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbcutils_dbcp.release(connection,statement,rs);
        }
    }
}
```

#### C3P0

**导入jar包**   c3p0-0.9.1.2.jar    mchange-commons-java-0.2.9.jar

```xml
<?xml version="1.0" encoding="UTF-8"?>
<c3p0-config>
    <!--
    c3p0的缺省（默认）配置
    如果在代码中"ComboPooledDataSource ds=new ComboPooledDataSource();"这样写就表示使用的是c3p0的缺省（默认）-->
    <default-config>
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/jdbcstudy?userUnicode=true&amp;characterEncoding=utf8&amp;uesSSL=true&amp;serverTimezone=UTC</property>
        <property name="user">root</property>
        <property name="password">123456</property>

        <property name="acquiredIncrement">5</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">5</property>
        <property name="maxPoolSize">20</property>

    </default-config>

    <!--
    c3p0的命名配置
    如果在代码中"ComboPooledDataSource ds=new ComboPooledDataSource("MySQL");"这样写就表示使用的是mysql的缺省（默认）-->
    <named-config name="MySQL">
        <property name="driverClass">com.mysql.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/jdbcstudy?userUnicode=true&amp;characterEncoding=utf8&amp;uesSSL=true&amp;serverTimezone=UTC</property>
        <property name="user">root</property>
        <property name="password">123456</property>

        <property name="acquiredIncrement">5</property>
        <property name="initialPoolSize">10</property>
        <property name="minPoolSize">5</property>
        <property name="maxPoolSize">20</property>
    </named-config>
</c3p0-config>
```

**c3p0连接数据库**

```java
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class jdbcutils_c3p0 {

   private static ComboPooledDataSource source=null;
    static {
        try {
             source= new ComboPooledDataSource("MySQL");
          //创建数据源 工厂模式--> 创建对象
            

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 获取连接
    public static Connection getConnection() throws SQLException {
        //从数据源中获取连接
        return source.getConnection();
    }
    //释放连接资源
    public static void  release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(conn!=null) conn.close();

        if(st!=null) st.close();

        if(rs!=null) rs.close();
    }
}
```

**无论使用什么数据源 本质还是一样 ,DataSource 接口不会变**

 xml中&有其他含义 在xml配置中连接的&换成&amp;

数据库配置中注意时区 使用serverTimezone=UTC解决

使用MySQL 8.0及以上JDBC驱动的 导入驱动应该为com.mysql.cj.jdbc.Drive

```
 <default-config>
        <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
        <property name="jdbcUrl">jdbc:mysql://localhost:3306/jdbcstudy?useSSL=true&amp;serverTimezone=UTC&amp;useUnicode=true&amp;characterEncoding=UTF-8</property>
        <property name="user">root</property>
        <property name="password">270030</property>

        <property name="initialPoolSize">10</property>
        <property name="maxIdleTime">30</property>
        <property name="maxPoolSize">100</property>
        <property name="minPoolSize">10</property>
   
</default-config>

<!--
c3p0的命名配置
如果在代码中"ComboPooledDataSource ds=new ComboPooledDataSource("MySQL");"这样写就表示使用的是mysql的缺省（默认）-->
<named-config name="MySQL">
    <property name="driverClass">com.mysql.jdbc.Driver</property>
    <property name="jdbcUrl">jdbc:mysql://localhost:3306/jdbcstudy?userUnicode=true&amp;characterEncoding=utf8&amp;uesSSL=true&amp;serverTimezone=UTC</property>
    <property name="user">root</property>
    <property name="password">123456</property>

​       <property name="initialPoolSize">10</property>
        <property name="maxIdleTime">30</property>
        <property name="maxPoolSize">100</property>
        <property name="minPoolSize">10</property>
</named-config>
```

\```

**c3p0连接数据库**

```java
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class jdbcutils_c3p0 {

   private static ComboPooledDataSource source=null;
    static {
        try {
             source= new ComboPooledDataSource("MySQL");
          //创建数据源 工厂模式--> 创建对象
            

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 获取连接
    public static Connection getConnection() throws SQLException {
        //从数据源中获取连接
        return source.getConnection();
    }
    //释放连接资源
    public static void  release(Connection conn, Statement st, ResultSet rs) throws SQLException {
        if(conn!=null) conn.close();

        if(st!=null) st.close();

        if(rs!=null) rs.close();
    }
}
```

