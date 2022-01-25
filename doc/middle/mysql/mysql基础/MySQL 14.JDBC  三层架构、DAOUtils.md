## 一、三层架构

### 1.1什么是三层

- 表示层：

  命名：XXXView

  职责：收集用户的数据和需求、展示数据。

- 业务逻辑层：

  命名：XXXServicelmpl

  职责：数据加工处理、调用DAO完成业务实现、控制事务。

- 数据访问层：

  命名：XXXDaoImpl

  职责：向业务层提供数据，将业务层加工后的数据同步到数据庳。

- 三层架构核流程：

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731220726152.jpg)

### 1.2三层架构项目搭建（按开发步骤）

- utils存放工具类（DBUtils)
- entity存放实体类（Person)
- dao 存放 DAO 接口（PersonDao)
  - impl 存放 DAO 接口 实现类（PersonDaoImpl)
- service 存放 service 接口 (PersonService)
  - impl 存放 service接口实现类（PersonServicelmpI〉
- view存放程序启动类（main)
- 程序设计时，考虑易修改、易扩展，为Service层和DAO层设计接口，便于未来更换实现类

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731220742510.jpg)

## 二、DaoUtils

- 在DAO层中，对数据库表的增、删、改、查操作存在代码冗余，
- 可对其逬行抽取封装DaoUtils工具类实现复用。

总代码：

```java
package com.wlw.chapter1_JDBC.demo04_person.utils;

import com.wlw.chapter1_JDBC.demo04_person.advanced.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//复用 增 删 改 查方法，提取公共部分
public class DaoUtils<T> {

    /**
     *复用 增 删 改，公共处理
     * @param sql 执行的sql语句
     * @param args 参数列表
     * @return 返回受影响的行数
     */
    public int commonsUpdate(String sql,Object... args){
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i+1,args[i]);
            }
            int result = preparedStatement.executeUpdate();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(null,preparedStatement,null);
        }
        return 0;
    }

    /**
     * 公共的查询方法（可查询任意一张表，可查询单个对象、也可查询多个对象）
     * @param sql 执行的sql语句
     * @param args 参数列表
     * @return 集合
     */
    public List<T> commonsSelect(String sql, RowMapper<T> rowMapper, Object... args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet =null;
        List<T> list = new ArrayList<>();
        try {
            connection = DBUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);

            if(args != null){
                for (int i = 0; i < args.length; i++) {
                    preparedStatement.setObject(i+1,args[i]);
                }
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                //如何根据查询结果来完成ORM，如何进行对象的创建与封装
                T t = rowMapper.getRow(resultSet);//回调--> 调用者提供一个封装方法
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.CloseAll(null,preparedStatement,resultSet);
        }
        return null;
    }
}


package com.wlw.chapter1_JDBC.demo04_person.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //1.将字符串转换为 util.Date
    public static Date strToUtil(String str){
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //2.将 util.Date 转换为 sql.Date
    public static java.sql.Date utilToSql(Date date){
        return new java.sql.Date(date.getTime());
    }

    //3.将util.Date 转换为 字符串
    public static String utilToStr(Date date){
        return sdf.format(date);
    }
}



package com.wlw.chapter1_JDBC.demo04_person.utils;

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


db.properties:
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/companydb?useUnicode=true&characterEncoding=utf8
username=root
password=123456

    
    
package com.wlw.chapter1_JDBC.demo04_person.entity;
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
    public int getId() { return id;}
    public void setId(int id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}
    public Date getBornDate() {return bornDate;}
    public void setBornDate(Date bornDate) {this.bornDate = bornDate;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getAddress() {return address;}
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


package com.wlw.chapter1_JDBC.demo04_person.dao;
import com.wlw.chapter1_JDBC.demo04_person.entity.Person;
import java.util.List;
public interface PersonDao {
    public int insert(Person person);
    public int update(Person person);
    public int delete(int id);
    public Person select(int id);
    public List<Person> selectAll();
}



package com.wlw.chapter1_JDBC.demo04_person.dao.impl;

import com.wlw.chapter1_JDBC.demo04_person.advanced.impl.PersonRowMapper;
import com.wlw.chapter1_JDBC.demo04_person.utils.DaoUtils;
import com.wlw.chapter1_JDBC.demo04_person.utils.DateUtils;
import com.wlw.chapter1_JDBC.demo04_person.entity.Person;
import com.wlw.chapter1_JDBC.demo04_person.dao.PersonDao;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    private DaoUtils<Person> daoUtils = new DaoUtils();
    //新增
    public int insert(Person person){
        String sql = "insert into Person(name,age,bornDate,email,address) values (?,?,?,?,?)";
        return daoUtils.commonsUpdate(sql, person.getName()
                , person.getAge(), DateUtils.utilToSql(person.getBornDate())
                , person.getEmail(), person.getAddress());
    }
    //修改
    public int update(Person person){
        String sql = "update Person set name=? ,age=?,bornDate=?,email=?,address=? where id=?";
        return daoUtils.commonsUpdate(sql,person.getName()
                , person.getAge(), DateUtils.utilToSql(person.getBornDate())
                , person.getEmail(), person.getAddress(),person.getId());
    }
    //删除
    public int delete(int id){
        String sql = "delete from Person where id=?;";
        return daoUtils.commonsUpdate(sql,id);
    }
    //查询单个
    public Person select(int id){
        String sql= "select * from Person where id=?;";
        List<Person> list = daoUtils.commonsSelect(sql,new PersonRowMapper(),id);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    //查询全部
    public List<Person> selectAll(){
        String sql = "select * from Person";
        List<Person> list = daoUtils.commonsSelect(sql, new PersonRowMapper(), null);
        return list;
    }
}


package com.wlw.chapter1_JDBC.demo04_person.service;
public interface PersonService {}
12
package com.wlw.chapter1_JDBC.demo04_person.service.impl;
import com.wlw.chapter1_JDBC.demo04_person.service.PersonService;
public class PersonServiceImpl implements PersonService {}
123
package com.wlw.chapter1_JDBC.demo04_person.advanced;
import java.sql.ResultSet;
/**
 * 约束封装对象的ORM
 */
public interface RowMapper<T> {
    public T getRow(ResultSet resultSet);
}
12345678
package com.wlw.chapter1_JDBC.demo04_person.advanced.impl;

import com.wlw.chapter1_JDBC.demo04_person.entity.Person;
import com.wlw.chapter1_JDBC.demo04_person.advanced.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person getRow(ResultSet resultSet) {
        Person person = null;
        try {
            int pid = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            Date bornDate = resultSet.getDate("bornDate");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            person = new Person(pid,name,age,bornDate,email,address);
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}



package com.wlw.chapter1_JDBC.demo04_person.view;

import com.wlw.chapter1_JDBC.demo04_person.dao.PersonDao;
import com.wlw.chapter1_JDBC.demo04_person.dao.impl.PersonDaoImpl;
import com.wlw.chapter1_JDBC.demo04_person.entity.Person;
import com.wlw.chapter1_JDBC.demo04_person.utils.DateUtils;

import java.util.List;

public class TestPerson {
    public static void main(String[] args) {
        PersonDao personDao = new PersonDaoImpl();
        //Person person = new Person("xiaoli",20, DateUtils.strToUtil("2000-03-03"),"xiaoli@163.com","中国杭州");
        //int result = personDao.insert(person);

        //Person person = new Person(9,"xiaoli",22, DateUtils.strToUtil("1998-03-03"),"xiaoli@163.com","中国杭州");
        //int result = personDao.update(person);

        //int result = personDao.delete(9);
        //System.out.println(result);

        //Person person = personDao.select(1);
        //System.out.println(person);

        List<Person> people = personDao.selectAll();
        for (Person person : people) {
            System.out.println(person);
        }

    }
}
```