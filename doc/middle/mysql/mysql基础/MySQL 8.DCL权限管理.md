# 权限管理

## 1.创建用户

> CREATE USER 用户名 IDENTIFIED BY 密码 (identified by)

### 1.1创建一个用户

```mysql
#创建一个zhangsan用户
CREATE USER 'zhangsan' IDENTIFIED BY '123';
```

## 2.授权

> GRANT ALL ON 数据库.表 TO 用户名；

### 2.1用户授权

```mysql
#将companyDB下的所有表的杈限都赋给zhangsan 
GRANT ALL ON companyDB.* TO 'zhangsan';
```

## 3.撤销权限

> REVOKE ALL ON 数据库.表名 FROM 用户名

- 注意：撤销权限后，账户要重新连接客户端才会生效

### 3.1撤销用户权限

```mysql
#将zhangsan的companyDB的权限撤销 
REVOKE ALL ON companyDB.* FROM zhangsan';
```

## 4.删除用户

> DROP USER 用户名

### 4.1删除用户

```mysql
#删除用户zhangsan 
DROP USER 'zhangsan';
```