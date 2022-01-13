## 一、DAO （Data Access Object)：数据访问对象

- DAO实现了业务逻辑与数据库访问相分离。（用 DAO 只做数据库访问）

  - 对同一张表的所有操作封装在XxxDaoImpl对象中。
  - 根据增删改查的不同功能实现具体的方法（insert、update、delete、select、selectAll)。

- DAO流程：

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731220242725.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

### 1.1创建数据库

- 创建一张表Person,有以下列：
  - id: int，主键，自动增长
  - name: varchar(20) 非空
  - age: int 非空
  - bornDate： Date
  - email:字符串
  - address:字符串

### 1.2封装实体类

- 创建entity实体类Person,编写属性私有化，构造方法，get/set方法。

### 1.3编写Daolmpl类

```java
package com.wlw.chapter1_JDBC.demo03;
// DB 工具类 实现  获得连接 与 关闭连接
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
public class DBUtils {
    private static final Properties properties = new Properties();
    static {
        InputStream is = DBUtils.class.getResourceAsStream("/db.properties");
        try {
            properties.load(is);
            Class.forName(properties.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;

    }

    public static void CloseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement !=null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


package com.wlw.chapter1_JDBC.demo03;
import java.util.Date;
//实体类 entity
public class Person {
    private int id;
    private String name;
    private int age;
    private Date bornDate;
    private String email;
    private String address;
    public Person() {}
    public Person(String name, int age, Date bornDate, String email, String address) 	{
        this.name = name;
        this.age = age;
        this.bornDate = bornDate;
        this.email = email;
        this.address = address;
    }

    public Person(int id, String name, int age, Date bornDate, String email, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bornDate = bornDate;
        this.email = email;
        this.address = address;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public Date getBornDate() {return bornDate;}
    public void setBornDate(Date bornDate) {this.bornDate = bornDate;}
    public String getEmail() {return email;}
    public void setEmail(String email) { this.email = email;}
    public String getAddress() { return address;}
    public void setAddress(String address) {this.address = address;}

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", bornDate=" + bornDate +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}



package com.wlw.chapter1_JDBC.demo03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 增，删，改，查单个，查所有
 * 只做数据库访问操作，不参与逻辑运算
 * 数据库一张表的访问的操作复用
 */
public class PersonDaoImpl {
    //新增
    public int insert(Person person){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "insert into Person(name,age,bornDate,email,address) values (?,?,?,?,?)";
        int result = 0;
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
             //preparedStatement.setDate(3,new java.sql.Date(person.getBornDate().getTime()));
            preparedStatement.setDate(3,DateUtils.utilToSql(person.getBornDate()));
            preparedStatement.setString(4,person.getEmail());
            preparedStatement.setString(5,person.getAddress());

            result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,null);
        }
        return 0;
    }

    //修改
    public int update(Person person){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "update Person set name=? ,age=?,bornDate=?,email=?,address=? where id=?";
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setDate(3,DateUtils.utilToSql(person.getBornDate()));
            preparedStatement.setString(4,person.getEmail());
            preparedStatement.setString(5,person.getAddress());
            preparedStatement.setInt(6,person.getId());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,null);
        }
        return 0;
    }

    //删除
    public int delete(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "delete from Person where id=?;";
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            int result = preparedStatement.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,null);
        }
        return 0;
    }

    //查询单个
    public Person select(int id){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Person person = null;
        String sql= "select * from Person where id=?;";
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int pid = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                Date bornDate = resultSet.getDate("bornDate");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                person = new Person(pid,name,age,bornDate,email,address);
            }
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,resultSet);
        }
        return null;
    }

    //查询全部
    public List<Person> selectAll(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Person person = null;
        List<Person> list = new ArrayList<Person>();
        String sql = "select * from Person";
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                int pid = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                Date bornDate = resultSet.getDate("bornDate");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                person = new Person(pid,name,age,bornDate,email,address);
                list.add(person);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,resultSet);
        }
        return null;
    }
}



package com.wlw.chapter1_JDBC.demo03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
public class TestPersonInsert {
    public static void main(String[] args) throws ParseException {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        PersonDaoImpl personDao = new PersonDaoImpl();
        //Person person = new Person("dadaming",27,sdf.parse("2000-02-02"),"dadaming@163.com","中国深圳");
        Person person = new Person("Marry",27,DateUtils.strToUtil("2020-03-09"),"Marry@163.com","中国深圳");
        int result = personDao.insert(person);
        if(result == 1){
            System.out.println("插入成功");
        }else {
            System.out.println("插入失败");
        }
    }
}


package com.wlw.chapter1_JDBC.demo03;

public class TestPersonUpdate {
    public static void main(String[] args) {
        PersonDaoImpl personDao = new PersonDaoImpl();
        //Person person = new Person(1,"xiaohong",15,null,"xiaohong@163.com","中国上海");
        Person person = new Person(1,"xiaohong",15,DateUtils.strToUtil("1998-02-16"),"xiaohong@163.com","中国上海");
        int result = personDao.update(person);
        if(result == 1){
            System.out.println("修改成功");
        }else {
            System.out.println("修改失败");
        }
    }
}


package com.wlw.chapter1_JDBC.demo03;
public class TestPersonDelete {
    public static void main(String[] args) {
        PersonDaoImpl personDao = new PersonDaoImpl();
        int result = personDao.delete(2);
        if(result == 1){
            System.out.println("删除成功");
        }else {
            System.out.println("删除失败");
        }
    }
}


package com.wlw.chapter1_JDBC.demo03;
public class TestPersonSelect {
    public static void main(String[] args) {
        PersonDaoImpl personDao = new PersonDaoImpl();
        Person person = personDao.select(1);
        if( person != null){
            System.out.println(person);
        }else {
            System.out.println("查询失败");
        }
    }
}



package com.wlw.chapter1_JDBC.demo03;

import java.util.List;
public class TestPersonSelectAll {
    public static void main(String[] args) {
        PersonDaoImpl personDao = new PersonDaoImpl();
        List<Person> list = personDao.selectAll();
        if(list != null){
            for(Person person : list){
                System.out.println(person);
            }
        }else {
            System.out.println("查询失败");
        }
    }
}


package com.wlw.chapter1_JDBC.demo03;
//日期转换 工具类（接着下面要学的）
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //1.将字符串转换为 util.Date
    public static java.util.Date strToUtil(String str){
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //2.将 util.Date 转换为 sql.Date
    public static java.sql.Date utilToSql(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
    //3.将util.Date 转换为 字符串
    public static String utilToStr(java.util.Date date){
        return sdf.format(date);
    }
}
```

## 二、Date工具类

- 现有问题：数据库存储的数据类型为java.sql.Date。
- 而我们Java应用层存储日期类型为java.util.Date。
- 当我们用Java应用程序插入带有 日期 的数据到数据库中时，需要进行转换。

### 2.1 java.util.Date

- Java语言常规应用层面的日期类型，可以通过字符串创建对应的时间对象。
- 无法直接通过JDBC插入到数据库。

### 2.2 java.sql.Date

- 不可以通过字符串创建对应的时间对象，只能通过毫秒值创建对象(1970年至今的毫秒值)。
- 可以直接通过JDBC插入到数据库。

### 2.3 SimpleDateFormat

> 格式化和解析日期的具体类。允许进行格式化（日期-> 文本）、解析（文本-> 日期）和规范化。

#### 2.3.1 SimpleDateFormat 应用

```java
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd11);//指定日期格式
java.util.Date date = sdf.parse(String dateStr);//将字符串解析成日期类型（java.util .Date)
String dates = sdf.format(date);//将日期格式化成字符串
                                            
package com.wlw.chapter1_JDBC.demo03;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTime {
    public static void main(String[] args) throws Exception{
        System.out.println(new java.util.Date());
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//指定日期格式

        //1.  sdf.format()  将java.util.Date 转成字符串
        String format_str_date = sdf.format(new java.util.Date());
        System.out.println(format_str_date);

        //2.  sdf.parse()  将字符串转换成java.util.Date
        //字符串定义时间
        String datastr = "1999-09-09";
        Date parse_date = sdf.parse(datastr);
        System.out.println(parse_date);

        //java.util.Date转成 java.sql.Date
        //sql.Date不支持字符串转换，只支持毫米值 ，
        // 可以通过util.Date的getTime()拿到指定日期的毫秒值
        java.sql.Date sql_date = new java.sql.Date(parse_date.getTime());
        java.sql.Date sql_date2 = new java.sql.Date(sdf.parse("2020-02-02").getTime());
        System.out.println(sql_date);
    }
}
```

### 2.4封装DateUtils工具类

```java
package com.wlw.chapter1_JDBC.demo03;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //1.将字符串转换为 util.Date
    public static java.util.Date strToUtil(String str){
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    //2.将 util.Date 转换为 sql.Date
    public static java.sql.Date utilToSql(java.util.Date date){
        return new java.sql.Date(date.getTime());
    }
    //3.将util.Date 转换为 字符串
    public static String utilToStr(java.util.Date date){
        return sdf.format(date);
    }
}
```

## 三、Service层（业务逻辑层）

### 3.1什么是业务？

- 代表用户完成的一个业务功能，可以由一个或多个DAO的调用组成。（软件所提供的一个功能都叫业务）。

- service层核心思想：

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020073122032990.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

### 3.2 Service开发流程

- service开发流程

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731220351254.jpg)

#### 3.2.1注册功能

```java
package com.wlw.chapter1_JDBC.demo03;

public class PersonServiceImpl {
    //注册，一个业务功能由多个DAO的访问操作组成
    public static void regist(Person person){
        PersonDaoImpl personDao = new PersonDaoImpl();
        //1.查询用户是否存在（根据用户名字来查询） (在PersonDaoImpl 加了重载方法)
        Person person1 = personDao.select(person.getName());
        //2.不存在新增
        if(person1 == null){
            personDao.insert(person);
            System.out.println("注册成功");
        }else {
            //3.存在，做出提示
            System.out.println("该用户已存在");
        }
    }
}



package com.wlw.chapter1_JDBC.demo03;

public class TestRedist {
    public static void main(String[] args) {
        PersonServiceImpl personService = new PersonServiceImpl();
        Person person = new Person("Dajia",20,DateUtils.strToUtil("2000-05-09"),"Dajia@163.com","中国西藏");
        personService.regist(person);
    }
}
/* 注册成功*/

```

#### 3.2.2编写service实现转账功能

```java
package com.wlw.chapter1_JDBC.demo03_account;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl {
    public int insert(Account account){return 0;}
    public int delete(String cordNo){return 0;}
    //修改
    public int update(Account account){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "update account set password=?,name=?,balance=? where cardNo=?;";
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getPassword());
            preparedStatement.setString(2,account.getName());
            preparedStatement.setDouble(3,account.getBalance());
            preparedStatement.setString(4,account.getCordNo());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(connection,preparedStatement,null);
        }
        return 0;
    }

    //查询
    public Account select(String cardNo){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = null;
        String sql = "select * from account where cardNo=?;";
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cardNo);//传参

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account = new Account(resultSet.getString("cardNo")
                        ,resultSet.getString("password")
                        ,resultSet.getString("name")
                        ,resultSet.getDouble("balance"));
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.CloseAll(connection,preparedStatement,resultSet);
        }
        return null;
    }
}



package com.wlw.chapter1_JDBC.demo03_account;

public class AccountServiceImpl {
    //转账
    public void transfer(String fromCardNo,String pwd,String toCardNo,double money){ //1.收集参数
        AccountDaoImpl accountDao = new AccountDaoImpl();
        //2.完善功能
        //2.1 fromCardNo 是否存在
        try {
            Account from_account = accountDao.select(fromCardNo);
            if(from_account == null){
                throw new RuntimeException("卡号不存在");
            }
            //2.2 验证密码是否正确
            if(!from_account.getPassword().equals(pwd)){
                throw new RuntimeException("密码错误");
            }
            //2.3验证对方账号toCardNo是否存在
            Account to_account = accountDao.select(toCardNo);
            if(to_account == null){
                throw new RuntimeException("对方卡号不存在");
            }
            //2.4 扣钱
            from_account.setBalance(from_account.getBalance() - money);
            accountDao.update(from_account);
            //2.5加钱
            to_account.setBalance(to_account.getBalance() + money);
            accountDao.update(to_account);
            System.out.println("转账成功");
        } catch (RuntimeException e) {
            System.out.println("转账失败");
            e.printStackTrace();
        }
    }
}



package com.wlw.chapter1_JDBC.demo03_account;

public class TestAccount {
    public static void main(String[] args) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.transfer("6002","1234","6003",1000);
    }
}



package com.wlw.chapter1_JDBC.demo03_account;

public class Account {
    private String cordNo;
    private String password;
    private String name;
    private double balance;

    public Account() {}
    public Account(String cordNo, String password, String name, double balance) {
        this.cordNo = cordNo;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }
    @Override
    public String toString() {
        return "Account{" +
                "cordNo='" + cordNo + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
    public String getCordNo() {return cordNo;}
    public void setCordNo(String cordNo) { this.cordNo = cordNo;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
}

//最后还有DBUtils 工具类，和上面一样，就不写了
```

## 四、事务

- 在JDBC中，获得Connection对象开始事务 --提交或回滚 --关闭连接。其事务策略是：
  - connection.setAutoCommit(false);//true 等价于 1，false 等价于 0 ,设置是否自动提交
  - connection.commit();//手动提交事务
  - connection.rollback();//手动回滚事务

### 4.1service层控制事务

```java
package com.wlw.chapter1_JDBC.demo03_account;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountServiceImpl {
    //转账
    public void transfer(String fromCardNo,String pwd,String toCardNo,double money){ //1.收集参数
        AccountDaoImpl accountDao = new AccountDaoImpl();
        //2.完善功能
        //2.1 fromCardNo 是否存在
        
        Connection connection = null; //---------注意点
        try {
            //获取连接对象
            connection = DBUtils.getConnection();//---------注意点
            //设置当前事务的自动提交为手动提交，开始事务
            connection.setAutoCommit(false);//---------注意点

            Account from_account = accountDao.select(fromCardNo);
            if(from_account == null){
                throw new RuntimeException("卡号不存在");
            }
            //2.2 验证密码是否正确
            if(!from_account.getPassword().equals(pwd)){
                throw new RuntimeException("密码错误");
            }
            //2.3验证对方账号toCardNo是否存在
            Account to_account = accountDao.select(toCardNo);
            if(to_account == null){
                throw new RuntimeException("对方卡号不存在");
            }
            //2.4 扣钱
            from_account.setBalance(from_account.getBalance() - money);
            accountDao.update(from_account);
            
            //手动加异常
            //int a = 10/0; //模拟转账出现异常
            
            //2.5加钱
            to_account.setBalance(to_account.getBalance() + money);
            accountDao.update(to_account);
            System.out.println("转账成功");

            connection.commit(); //转账成功，则整个事务提交 //---------注意点
        } catch (RuntimeException e) {
            System.out.println("转账失败");
            try {
                //如果程序出现异常，转账失败，则整个事务回滚
                connection.rollback();//---------注意点
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.CloseAll(connection,null,null);//关闭
        }
    }
}
```

### 4.2问题

- ### **问题：当转账程序出现异常，事务控制成功了吗?**

对于上面4.1的代码中，第38行（//int a = 10/0; //模拟转账出现异常），虽然我们加了事务，但是最终执行结果依然是错误的，只减少金额，没有添加。

这是因为在这个AccountServiceImpl代码中（包括调用的AccountDaoImpl中的方法），我们获得了多个Connection,而我们只对其中一个Connection加了事务

```java
AccountServiceImpl:com.mysql.jdbc.JDBC4Connection@446cdf90
AccountDaoImpl-select:com.mysql.jdbc.JDBC4Connection@22d8cfe0
AccountDaoImpl-select:com.mysql.jdbc.JDBC4Connection@6d5380c2
AccountDaoImpl-update:com.mysql.jdbc.JDBC4Connection@108c4c35
转账失败
java.lang.ArithmeticException: / by zero
	at com.wlw.chapter1_JDBC.demo03_account.AccountServiceImpl.transfer(AccountServiceImpl.java:38)
	at com.wlw.chapter1_JDBC.demo03_account.TestAccount.main(TestAccount.java:6)
// 可以看到 多次获取的Connection对象是不一样的    
```

### 4.3解决问题

#### 4.3.1解决方案1:传递Connection

- 为了解决线程中Connection对象不同步的问题，可以将Connection对象（作为参数）通过service传递给各个DAO方法吗？

##### 传递的问题

- 如果使用传递Connection，容易造成接口污染（BadSmell)。（复用性降低）
- 定义接口是为了更容易更换实现，而将Connection定义在接口中，会造成污染当前接口。

#### 4.3.2 解决方案2: ThreadLocal

- 可以将整个线程中（单线程）中，存储一个共享值。
- 线程拥有一个类似Map的属性，键值对结构<ThreadLocal对象，值>。

### 4.4ThreadLocal应用

- 一个线程共享同一个ThreadLocal，在整个流程中任一环节可以存值或取值。

- ThreadLocal核心流程：

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731220531919.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

#### 4.4.1参数绑定

```java
package com.wlw.chapter1_JDBC.demo03_account;
//加了ThreadLocal之后的工具类
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
    private static final Properties properties = new Properties();
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();//注意点

    static {
        InputStream is = DBUtils.class.getResourceAsStream("/db.properties");
        try {
            properties.load(is);
            Class.forName(properties.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = threadLocal.get();//将当前线程中绑定的Connection对象，赋值给connection //----------注意点
        try {
            if(connection == null){
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
                threadLocal.set(connection);//把第一次获得的连接 存在当前线程中//-----注意点
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    public static void CloseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement !=null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

此时模拟转账出错：

```java
AccountServiceImpl:com.mysql.jdbc.JDBC4Connection@446cdf90
AccountDaoImpl-select:com.mysql.jdbc.JDBC4Connection@446cdf90
AccountDaoImpl-select:com.mysql.jdbc.JDBC4Connection@446cdf90
AccountDaoImpl-update:com.mysql.jdbc.JDBC4Connection@446cdf90
转账失败
java.lang.ArithmeticException: / by zero
	at com.wlw.chapter1_JDBC.demo03_account.AccountServiceImpl.transfer(AccountServiceImpl.java:38)
	at com.wlw.chapter1_JDBC.demo03_account.TestAccount.main(TestAccount.java:6)
// 可以看到 多次获取的Connection对象是一样的       
```

### 4.5事务的封装

- 在4.1 service层控制事务中，我们是在service中调用了Connection，而之前我们说的对数据库的操作不应该出现在service层，所以我们要对事物的开启，提交，回滚做一个封装（在DBUtils中）
- 将事务的开启、提交、回滚都封装在工具类中，业务层调用即可。

```java
package com.wlw.chapter1_JDBC.demo03_account;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
    private static final Properties properties = new Properties();
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        InputStream is = DBUtils.class.getResourceAsStream("/db.properties");
        try {
            properties.load(is);
            Class.forName(properties.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = threadLocal.get();//将当前线程中绑定的Connection对象，赋值给connection
        try {
            if(connection == null){
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
                threadLocal.set(connection);//把第一次获得的连接 存在当前线程中
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //开启事务
    public static void begin(){
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);//设置手动提交
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //提交事务
    public static void commit(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseAll(connection,null,null);//关闭这个唯一连接
        }
    }
    //回滚事务
    public static void rollback(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseAll(connection,null,null);//关闭这个唯一连接
        }
    }

    public static void CloseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement !=null){
                statement.close();
            }
            if(connection != null){
                connection.close();
                threadLocal.remove();//关闭连接后，移除已关闭的Connection对象 //----注意点
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

## 五、转账功能代码汇总

```java
package com.wlw.chapter1_JDBC.demo03_account;

public class Account {
    private String cordNo;
    private String password;
    private String name;
    private double balance;

    public Account() {}
    public Account(String cordNo, String password, String name, double balance) {
        this.cordNo = cordNo;
        this.password = password;
        this.name = name;
        this.balance = balance;
    }
    @Override
    public String toString() {
        return "Account{" +
                "cordNo='" + cordNo + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
    public String getCordNo() {return cordNo;}
    public void setCordNo(String cordNo) { this.cordNo = cordNo;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public double getBalance() {return balance;}
    public void setBalance(double balance) {this.balance = balance;}
}



package com.wlw.chapter1_JDBC.demo03_account;
//Dao层
//增删改查的方法，注意在确定只有一个Connection对象之后，方法中的关闭连接，不要加入Connection对象，要在事务结束（提交或者回滚）才结束Connection
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl {
    public int insert(Account account){return 0;}
    public int delete(String cordNo){return 0;}
    //修改
    public int update(Account account){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String sql = "update account set password=?,name=?,balance=? where cardNo=?;";
        try {
            connection = DBUtils.getConnection();
            System.out.println("AccountDaoImpl-update:"+connection);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,account.getPassword());
            preparedStatement.setString(2,account.getName());
            preparedStatement.setDouble(3,account.getBalance());
            preparedStatement.setString(4,account.getCordNo());

            int result = preparedStatement.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(null,preparedStatement,null);//---注意点
        }
        return 0;
    }

    //查询
    public Account select(String cardNo){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Account account = null;
        String sql = "select * from account where cardNo=?;";
        try {
            connection = DBUtils.getConnection();
            System.out.println("AccountDaoImpl-select:"+connection);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,cardNo);//传参

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account = new Account(resultSet.getString("cardNo")
                        ,resultSet.getString("password")
                        ,resultSet.getString("name")
                        ,resultSet.getDouble("balance"));
            }
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.CloseAll(null,preparedStatement,resultSet);//---注意点
        }
        return null;
    }
}



package com.wlw.chapter1_JDBC.demo03_account;
//业务逻辑层 service层
import java.sql.Connection;
import java.sql.SQLException;

public class AccountServiceImpl {
    //转账
    public void transfer(String fromCardNo,String pwd,String toCardNo,double money){ //1.收集参数
        AccountDaoImpl accountDao = new AccountDaoImpl();
        //2.完善功能
        //2.1 fromCardNo 是否存在

        try {
            //调用DBUtils的方法  开启事务
            DBUtils.begin();

            Account from_account = accountDao.select(fromCardNo);
            if(from_account == null){
                throw new RuntimeException("卡号不存在");
            }
            //2.2 验证密码是否正确
            if(!from_account.getPassword().equals(pwd)){
                throw new RuntimeException("密码错误");
            }
            //2.3验证对方账号toCardNo是否存在
            Account to_account = accountDao.select(toCardNo);
            if(to_account == null){
                throw new RuntimeException("对方卡号不存在");
            }
            //2.4 扣钱
            from_account.setBalance(from_account.getBalance() - money);
            accountDao.update(from_account);

            //手动加异常
            //int a = 10/0; //模拟转账出现异常

            //2.5加钱
            to_account.setBalance(to_account.getBalance() + money);
            accountDao.update(to_account);
            System.out.println("转账成功");

            //转账成功，则整个事务提交
            DBUtils.commit();
            
        } catch (RuntimeException e) {
            System.out.println("转账失败");
            
            //如果程序出现异常，转账失败，则整个事务回滚
            DBUtils.rollback();
            e.printStackTrace();
        }
    }
}




package com.wlw.chapter1_JDBC.demo03_account;

public class TestAccount {
    public static void main(String[] args) {
        AccountServiceImpl accountService = new AccountServiceImpl();
        accountService.transfer("6002","1234","6003",1000);
    }
}



package com.wlw.chapter1_JDBC.demo03_account;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
    private static final Properties properties = new Properties();
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        InputStream is = DBUtils.class.getResourceAsStream("/db.properties");
        try {
            properties.load(is);
            Class.forName(properties.getProperty("driver"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = threadLocal.get();//将当前线程中绑定的Connection对象，赋值给connection
        try {
            if(connection == null){
                connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
                threadLocal.set(connection);//把第一次获得的连接 存在当前线程中
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //开启事务
    public static void begin(){
        try {
            Connection connection = getConnection();
            connection.setAutoCommit(false);//设置手动提交
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //提交事务
    public static void commit(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseAll(connection,null,null);
        }
    }
    //回滚事务
    public static void rollback(){
        Connection connection = null;
        try {
            connection = getConnection();
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            CloseAll(connection,null,null);
        }
    }

    public static void CloseAll(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(resultSet != null){
                resultSet.close();
            }
            if(statement !=null){
                statement.close();
            }
            if(connection != null){
                connection.close();
                threadLocal.remove();//关闭连接后，移除已关闭的Connection对象
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```