# 一.准备工作

## 1.基本架构

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190925140503738.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NsaXNrcw==,size_16,color_FFFFFF,t_70)

## 2.在数据库中创建对应的表

**对应数据库代码如下：**

```mysql
CREATE DATABASE `smbms`;

USE `smbms`;

DROP TABLE IF EXISTS `smbms_address`;

CREATE TABLE `smbms_address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `contact` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人姓名',
  `addressDesc` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货地址明细',
  `postCode` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮编',
  `tel` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人电话',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '修改者',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '修改时间',
  `userId` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_address`(`id`,`contact`,`addressDesc`,`postCode`,`tel`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`,`userId`) VALUES (1,'王丽','北京市东城区东交民巷44号','100010','13678789999',1,'2016-04-13 00:00:00',NULL,NULL,1),(2,'张红丽','北京市海淀区丹棱街3号','100000','18567672312',1,'2016-04-13 00:00:00',NULL,NULL,1),(3,'任志强','北京市东城区美术馆后街23号','100021','13387906742',1,'2016-04-13 00:00:00',NULL,NULL,1),(4,'曹颖','北京市朝阳区朝阳门南大街14号','100053','13568902323',1,'2016-04-13 00:00:00',NULL,NULL,2),(5,'李慧','北京市西城区三里河路南三巷3号','100032','18032356666',1,'2016-04-13 00:00:00',NULL,NULL,3),(6,'王国强','北京市顺义区高丽营镇金马工业区18号','100061','13787882222',1,'2016-04-13 00:00:00',NULL,NULL,3);


DROP TABLE IF EXISTS `smbms_bill`;

CREATE TABLE `smbms_bill` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `billCode` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账单编码',
  `productName` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `productDesc` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品描述',
  `productUnit` VARCHAR(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品单位',
  `productCount` DECIMAL(20,2) DEFAULT NULL COMMENT '商品数量',
  `totalPrice` DECIMAL(20,2) DEFAULT NULL COMMENT '商品总额',
  `isPayment` INT(10) DEFAULT NULL COMMENT '是否支付（1：未支付 2：已支付）',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者（userId）',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '更新者（userId）',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  `providerId` BIGINT(20) DEFAULT NULL COMMENT '供应商ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_bill`(`id`,`billCode`,`productName`,`productDesc`,`productUnit`,`productCount`,`totalPrice`,`isPayment`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`,`providerId`) VALUES (2,'BILL2016_002','香皂、肥皂、药皂','日用品-皂类','块','1000.00','10000.00',2,1,'2016-03-23 04:20:40',NULL,NULL,13),(3,'BILL2016_003','大豆油','食品-食用油','斤','300.00','5890.00',2,1,'2014-12-14 13:02:03',NULL,NULL,6),(4,'BILL2016_004','橄榄油','食品-进口食用油','斤','200.00','9800.00',2,1,'2013-10-10 03:12:13',NULL,NULL,7),(5,'BILL2016_005','洗洁精','日用品-厨房清洁','瓶','500.00','7000.00',2,1,'2014-12-14 13:02:03',NULL,NULL,9),(6,'BILL2016_006','美国大杏仁','食品-坚果','袋','300.00','5000.00',2,1,'2016-04-14 06:08:09',NULL,NULL,4),(7,'BILL2016_007','沐浴液、精油','日用品-沐浴类','瓶','500.00','23000.00',1,1,'2016-07-22 10:10:22',NULL,NULL,14),(8,'BILL2016_008','不锈钢盘碗','日用品-厨房用具','个','600.00','6000.00',2,1,'2016-04-14 05:12:13',NULL,NULL,14),(9,'BILL2016_009','塑料杯','日用品-杯子','个','350.00','1750.00',2,1,'2016-02-04 11:40:20',NULL,NULL,14),(10,'BILL2016_010','豆瓣酱','食品-调料','瓶','200.00','2000.00',2,1,'2013-10-29 05:07:03',NULL,NULL,8),(11,'BILL2016_011','海之蓝','饮料-国酒','瓶','50.00','10000.00',1,1,'2016-04-14 16:16:00',NULL,NULL,1),(12,'BILL2016_012','芝华士','饮料-洋酒','瓶','20.00','6000.00',1,1,'2016-09-09 17:00:00',NULL,NULL,1),(13,'BILL2016_013','长城红葡萄酒','饮料-红酒','瓶','60.00','800.00',2,1,'2016-11-14 15:23:00',NULL,NULL,1),(14,'BILL2016_014','泰国香米','食品-大米','斤','400.00','5000.00',2,1,'2016-10-09 15:20:00',NULL,NULL,3),(15,'BILL2016_015','东北大米','食品-大米','斤','600.00','4000.00',2,1,'2016-11-14 14:00:00',NULL,NULL,3),(16,'BILL2016_016','可口可乐','饮料','瓶','2000.00','6000.00',2,1,'2012-03-27 13:03:01',NULL,NULL,2),(17,'BILL2016_017','脉动','饮料','瓶','1500.00','4500.00',2,1,'2016-05-10 12:00:00',NULL,NULL,2),(18,'BILL2016_018','哇哈哈','饮料','瓶','2000.00','4000.00',2,1,'2015-11-24 15:12:03',NULL,NULL,2);

DROP TABLE IF EXISTS `smbms_provider`;

CREATE TABLE `smbms_provider` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `proCode` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商编码',
  `proName` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商名称',
  `proDesc` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商详细描述',
  `proContact` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商联系人',
  `proPhone` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `proAddress` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `proFax` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '传真',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者（userId）',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '更新者（userId）',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_provider`(`id`,`proCode`,`proName`,`proDesc`,`proContact`,`proPhone`,`proAddress`,`proFax`,`createdBy`,`creationDate`,`modifyDate`,`modifyBy`) VALUES (1,'BJ_GYS001','北京三木堂商贸有限公司','长期合作伙伴，主营产品:茅台、五粮液、郎酒、酒鬼酒、泸州老窖、赖茅酒、法国红酒等','张国强','13566667777','北京市丰台区育芳园北路','010-58858787',1,'2013-03-21 16:52:07',NULL,NULL),(2,'HB_GYS001','石家庄帅益食品贸易有限公司','长期合作伙伴，主营产品:饮料、水饮料、植物蛋白饮料、休闲食品、果汁饮料、功能饮料等','王军','13309094212','河北省石家庄新华区','0311-67738876',1,'2016-04-13 04:20:40',NULL,NULL),(3,'GZ_GYS001','深圳市泰香米业有限公司','初次合作伙伴，主营产品：良记金轮米,龙轮香米等','郑程瀚','13402013312','广东省深圳市福田区深南大道6006华丰大厦','0755-67776212',1,'2014-03-21 16:56:07',NULL,NULL),(4,'GZ_GYS002','深圳市喜来客商贸有限公司','长期合作伙伴，主营产品：坚果炒货.果脯蜜饯.天然花茶.营养豆豆.特色美食.进口食品.海味零食.肉脯肉','林妮','18599897645','广东省深圳市福龙工业区B2栋3楼西','0755-67772341',1,'2013-03-22 16:52:07',NULL,NULL),(5,'JS_GYS001','兴化佳美调味品厂','长期合作伙伴，主营产品：天然香辛料、鸡精、复合调味料','徐国洋','13754444221','江苏省兴化市林湖工业区','0523-21299098',1,'2015-11-22 16:52:07',NULL,NULL),(6,'BJ_GYS002','北京纳福尔食用油有限公司','长期合作伙伴，主营产品：山茶油、大豆油、花生油、橄榄油等','马莺','13422235678','北京市朝阳区珠江帝景1号楼','010-588634233',1,'2012-03-21 17:52:07',NULL,NULL),(7,'BJ_GYS003','北京国粮食用油有限公司','初次合作伙伴，主营产品：花生油、大豆油、小磨油等','王驰','13344441135','北京大兴青云店开发区','010-588134111',1,'2016-04-13 00:00:00',NULL,NULL),(8,'ZJ_GYS001','慈溪市广和绿色食品厂','长期合作伙伴，主营产品：豆瓣酱、黄豆酱、甜面酱，辣椒，大蒜等农产品','薛圣丹','18099953223','浙江省宁波市慈溪周巷小安村','0574-34449090',1,'2013-11-21 06:02:07',NULL,NULL),(9,'GX_GYS001','优百商贸有限公司','长期合作伙伴，主营产品：日化产品','李立国','13323566543','广西南宁市秀厢大道42-1号','0771-98861134',1,'2013-03-21 19:52:07',NULL,NULL),(10,'JS_GYS002','南京火头军信息技术有限公司','长期合作伙伴，主营产品：不锈钢厨具等','陈女士','13098992113','江苏省南京市浦口区浦口大道1号新城总部大厦A座903室','025-86223345',1,'2013-03-25 16:52:07',NULL,NULL),(11,'GZ_GYS003','广州市白云区美星五金制品厂','长期合作伙伴，主营产品：海绵床垫、坐垫、靠垫、海绵枕头、头枕等','梁天','13562276775','广州市白云区钟落潭镇福龙路20号','020-85542231',1,'2016-12-21 06:12:17',NULL,NULL),(12,'BJ_GYS004','北京隆盛日化科技','长期合作伙伴，主营产品：日化环保清洗剂，家居洗涤专卖、洗涤用品网、墙体除霉剂、墙面霉菌清除剂等','孙欣','13689865678','北京市大兴区旧宫','010-35576786',1,'2014-11-21 12:51:11',NULL,NULL),(13,'SD_GYS001','山东豪克华光联合发展有限公司','长期合作伙伴，主营产品：洗衣皂、洗衣粉、洗衣液、洗洁精、消杀类、香皂等','吴洪转','13245468787','山东济阳济北工业区仁和街21号','0531-53362445',1,'2015-01-28 10:52:07',NULL,NULL),(14,'JS_GYS003','无锡喜源坤商行','长期合作伙伴，主营产品：日化品批销','周一清','18567674532','江苏无锡盛岸西路','0510-32274422',1,'2016-04-23 11:11:11',NULL,NULL),(15,'ZJ_GYS002','乐摆日用品厂','长期合作伙伴，主营产品：各种中、高档塑料杯，塑料乐扣水杯（密封杯）、保鲜杯（保鲜盒）、广告杯、礼品杯','王世杰','13212331567','浙江省金华市义乌市义东路','0579-34452321',1,'2016-08-22 10:01:30',NULL,NULL);


DROP TABLE IF EXISTS `smbms_role`;

CREATE TABLE `smbms_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `roleCode` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色编码',
  `roleName` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '修改者',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_role`(`id`,`roleCode`,`roleName`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`) VALUES (1,'SMBMS_ADMIN','系统管理员',1,'2016-04-13 00:00:00',NULL,NULL),(2,'SMBMS_MANAGER','经理',1,'2016-04-13 00:00:00',NULL,NULL),(3,'SMBMS_EMPLOYEE','普通员工',1,'2016-04-13 00:00:00',NULL,NULL);


DROP TABLE IF EXISTS `smbms_user`;

CREATE TABLE `smbms_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `userCode` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户编码',
  `userName` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `userPassword` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `gender` INT(10) DEFAULT NULL COMMENT '性别（1:女、 2:男）',
  `birthday` DATE DEFAULT NULL COMMENT '出生日期',
  `phone` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机',
  `address` VARCHAR(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `userRole` BIGINT(20) DEFAULT NULL COMMENT '用户角色（取自角色表-角色id）',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者（userId）',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '更新者（userId）',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT  INTO `smbms_user`(`id`,`userCode`,`userName`,`userPassword`,`gender`,`birthday`,`phone`,`address`,`userRole`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`) VALUES (1,'admin','系统管理员','1234567',1,'1983-10-10','13688889999','北京市海淀区成府路207号',1,1,'2013-03-21 16:52:07',NULL,NULL),(2,'liming','李明','0000000',2,'1983-12-10','13688884457','北京市东城区前门东大街9号',2,1,'2014-12-31 19:52:09',NULL,NULL),(5,'hanlubiao','韩路彪','0000000',2,'1984-06-05','18567542321','北京市朝阳区北辰中心12号',2,1,'2014-12-31 19:52:09',NULL,NULL),(6,'zhanghua','张华','0000000',1,'1983-06-15','13544561111','北京市海淀区学院路61号',3,1,'2013-02-11 10:51:17',NULL,NULL),(7,'wangyang','王洋','0000000',2,'1982-12-31','13444561124','北京市海淀区西二旗辉煌国际16层',3,1,'2014-06-11 19:09:07',NULL,NULL),(8,'zhaoyan','赵燕','0000000',1,'1986-03-07','18098764545','北京市海淀区回龙观小区10号楼',3,1,'2016-04-21 13:54:07',NULL,NULL),(10,'sunlei','孙磊','0000000',2,'1981-01-04','13387676765','北京市朝阳区管庄新月小区12楼',3,1,'2015-05-06 10:52:07',NULL,NULL),(11,'sunxing','孙兴','0000000',2,'1978-03-12','13367890900','北京市朝阳区建国门南大街10号',3,1,'2016-11-09 16:51:17',NULL,NULL),(12,'zhangchen','张晨','0000000',1,'1986-03-28','18098765434','朝阳区管庄路口北柏林爱乐三期13号楼',3,1,'2016-08-09 05:52:37',1,'2016-04-14 14:15:36'),(13,'dengchao','邓超','0000000',2,'1981-11-04','13689674534','北京市海淀区北航家属院10号楼',3,1,'2016-07-11 08:02:47',NULL,NULL),(14,'yangguo','杨过','0000000',2,'1980-01-01','13388886623','北京市朝阳区北苑家园茉莉园20号楼',3,1,'2015-02-01 03:52:07',NULL,NULL),(15,'zhaomin','赵敏','0000000',1,'1987-12-04','18099897657','北京市昌平区天通苑3区12号楼',2,1,'2015-09-12 12:02:12',NULL,NULL);
```

## 3.项目搭建

**（1）搭建一个maven web项目**

**（2）配置Tomcat并测试运行**

**（3）导入jar包；**

```xml
<dependencies>
       <!--Servlet 依赖-->
       <dependency>
           <groupId>javax.servlet</groupId>
           <artifactId>servlet-api</artifactId>
           <version>2.5</version>
       </dependency>
       <!--JSP依赖-->
       <dependency>
           <groupId>javax.servlet.jsp</groupId>
           <artifactId>javax.servlet.jsp-api</artifactId>
           <version>2.3.3</version>
       </dependency>
       <!-- JSTL表达式的依赖 -->
       <dependency>
           <groupId>javax.servlet.jsp.jstl</groupId>
           <artifactId>jstl-api</artifactId>
           <version>1.2</version>
       </dependency>
       <!-- standard标签库 -->
       <dependency>
           <groupId>taglibs</groupId>
           <artifactId>standard</artifactId>
           <version>1.1.2</version>
       </dependency>

   </dependencies>
```

**（4） 创建项目包结构**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190925142704700.png)
**（5）编写实体类**
**User类**

```java
import java.util.Date;
public class User {
   private Integer id; //id 
   private String userCode; //用户编码
   private String userName; //用户名称
   private String userPassword; //用户密码
   private Integer gender;  //性别
   private Date birthday;  //出生日期
   private String phone;   //电话
   private String address; //地址
   private Integer userRole;    //用户角色
   private Integer createdBy;   //创建者
   private Date creationDate; //创建时间
   private Integer modifyBy;     //更新者
   private Date modifyDate;   //更新时间
   
   private Integer age;//年龄
   private String userRoleName;    //用户角色名称


   public String getUserRoleName() {
   	return userRoleName;
   }
   public void setUserRoleName(String userRoleName) {
   	this.userRoleName = userRoleName;
   }
   public Integer getAge() {
   	Date date = new Date();
   	Integer age = date.getYear()-birthday.getYear();
   	return age;
   }
   public Integer getId() {
   	return id;
   }
   public void setId(Integer id) {
   	this.id = id;
   }
   public String getUserCode() {
   	return userCode;
   }
   public void setUserCode(String userCode) {
   	this.userCode = userCode;
   }
   public String getUserName() {
   	return userName;
   }
   public void setUserName(String userName) {
   	this.userName = userName;
   }
   public String getUserPassword() {
   	return userPassword;
   }
   public void setUserPassword(String userPassword) {
   	this.userPassword = userPassword;
   }
   public Integer getGender() {
   	return gender;
   }
   public void setGender(Integer gender) {
   	this.gender = gender;
   }
   public Date getBirthday() {
   	return birthday;
   }
   public void setBirthday(Date birthday) {
   	this.birthday = birthday;
   }
   public String getPhone() {
   	return phone;
   }
   public void setPhone(String phone) {
   	this.phone = phone;
   }
   public String getAddress() {
   	return address;
   }
   public void setAddress(String address) {
   	this.address = address;
   }
   public Integer getUserRole() {
   	return userRole;
   }
   public void setUserRole(Integer userRole) {
   	this.userRole = userRole;
   }
   public Integer getCreatedBy() {
   	return createdBy;
   }
   public void setCreatedBy(Integer createdBy) {
   	this.createdBy = createdBy;
   }
   public Date getCreationDate() {
   	return creationDate;
   }
   public void setCreationDate(Date creationDate) {
   	this.creationDate = creationDate;
   }
   public Integer getModifyBy() {
   	return modifyBy;
   }
   public void setModifyBy(Integer modifyBy) {
   	this.modifyBy = modifyBy;
   }
   public Date getModifyDate() {
   	return modifyDate;
   }
   public void setModifyDate(Date modifyDate) {
   	this.modifyDate = modifyDate;
   }

}
```

**Bill类**

```java
import java.math.BigDecimal;
import java.util.Date;
public class Bill {
   private Integer id;   //id 
   private String billCode; //账单编码 
   private String productName; //商品名称 
   private String productDesc; //商品描述 
   private String productUnit; //商品单位
   private BigDecimal productCount; //商品数量 
   private BigDecimal totalPrice; //总金额
   private Integer isPayment; //是否支付 
   private Integer providerId; //供应商ID 
   private Integer createdBy; //创建者
   private Date creationDate; //创建时间
   private Integer modifyBy; //更新者
   private Date modifyDate;//更新时间
   
   private String providerName;//供应商名称
   
   
   public String getProviderName() {
   	return providerName;
   }
   public void setProviderName(String providerName) {
   	this.providerName = providerName;
   }
   public Integer getId() {
   	return id;
   }
   public void setId(Integer id) {
   	this.id = id;
   }
   public String getBillCode() {
   	return billCode;
   }
   public void setBillCode(String billCode) {
   	this.billCode = billCode;
   }
   public String getProductName() {
   	return productName;
   }
   public void setProductName(String productName) {
   	this.productName = productName;
   }
   public String getProductDesc() {
   	return productDesc;
   }
   public void setProductDesc(String productDesc) {
   	this.productDesc = productDesc;
   }
   public String getProductUnit() {
   	return productUnit;
   }
   public void setProductUnit(String productUnit) {
   	this.productUnit = productUnit;
   }
   public BigDecimal getProductCount() {
   	return productCount;
   }
   public void setProductCount(BigDecimal productCount) {
   	this.productCount = productCount;
   }
   public BigDecimal getTotalPrice() {
   	return totalPrice;
   }
   public void setTotalPrice(BigDecimal totalPrice) {
   	this.totalPrice = totalPrice;
   }
   public Integer getIsPayment() {
   	return isPayment;
   }
   public void setIsPayment(Integer isPayment) {
   	this.isPayment = isPayment;
   }
   
   public Integer getProviderId() {
   	return providerId;
   }
   public void setProviderId(Integer providerId) {
   	this.providerId = providerId;
   }
   public Integer getCreatedBy() {
   	return createdBy;
   }
   public void setCreatedBy(Integer createdBy) {
   	this.createdBy = createdBy;
   }
   public Date getCreationDate() {
   	return creationDate;
   }
   public void setCreationDate(Date creationDate) {
   	this.creationDate = creationDate;
   }
   public Integer getModifyBy() {
   	return modifyBy;
   }
   public void setModifyBy(Integer modifyBy) {
   	this.modifyBy = modifyBy;
   }
   public Date getModifyDate() {
   	return modifyDate;
   }
   public void setModifyDate(Date modifyDate) {
   	this.modifyDate = modifyDate;
   }
}
```

**Role类**

```java
import java.util.Date;
public class Role {	
   private Integer id;   //id
   private String roleCode; //角色编码
   private String roleName; //角色名称
   private Integer createdBy; //创建者
   private Date creationDate; //创建时间
   private Integer modifyBy; //更新者
   private Date modifyDate;//更新时间
   
   public Integer getId() {
   	return id;
   }
   public void setId(Integer id) {
   	this.id = id;
   }
   public String getRoleCode() {
   	return roleCode;
   }
   public void setRoleCode(String roleCode) {
   	this.roleCode = roleCode;
   }
   public String getRoleName() {
   	return roleName;
   }
   public void setRoleName(String roleName) {
   	this.roleName = roleName;
   }
   public Integer getCreatedBy() {
   	return createdBy;
   }
   public void setCreatedBy(Integer createdBy) {
   	this.createdBy = createdBy;
   }
   public Date getCreationDate() {
   	return creationDate;
   }
   public void setCreationDate(Date creationDate) {
   	this.creationDate = creationDate;
   }
   public Integer getModifyBy() {
   	return modifyBy;
   }
   public void setModifyBy(Integer modifyBy) {
   	this.modifyBy = modifyBy;
   }
   public Date getModifyDate() {
   	return modifyDate;
   }
   public void setModifyDate(Date modifyDate) {
   	this.modifyDate = modifyDate;
   }
}
```

**Provider类**

```java
import java.util.Date;
public class Provider {
   private Integer id;   //id
   private String proCode; //供应商编码
   private String proName; //供应商名称
   private String proDesc; //供应商描述
   private String proContact; //供应商联系人
   private String proPhone; //供应商电话
   private String proAddress; //供应商地址
   private String proFax; //供应商传真
   private Integer createdBy; //创建者
   private Date creationDate; //创建时间
   private Integer modifyBy; //更新者
   private Date modifyDate;//更新时间

   public Integer getId() {
   	return id;
   }
   public void setId(Integer id) {
   	this.id = id;
   }
   public String getProCode() {
   	return proCode;
   }
   public void setProCode(String proCode) {
   	this.proCode = proCode;
   }
   public String getProName() {
   	return proName;
   }
   public void setProName(String proName) {
   	this.proName = proName;
   }
   public String getProDesc() {
   	return proDesc;
   }
   public void setProDesc(String proDesc) {
   	this.proDesc = proDesc;
   }
   public String getProContact() {
   	return proContact;
   }
   public void setProContact(String proContact) {
   	this.proContact = proContact;
   }
   public String getProPhone() {
   	return proPhone;
   }
   public void setProPhone(String proPhone) {
   	this.proPhone = proPhone;
   }
   public String getProAddress() {
   	return proAddress;
   }
   public void setProAddress(String proAddress) {
   	this.proAddress = proAddress;
   }
   public String getProFax() {
   	return proFax;
   }
   public void setProFax(String proFax) {
   	this.proFax = proFax;
   }
   public Integer getCreatedBy() {
   	return createdBy;
   }
   public void setCreatedBy(Integer createdBy) {
   	this.createdBy = createdBy;
   }
   public Date getCreationDate() {
   	return creationDate;
   }
   public void setCreationDate(Date creationDate) {
   	this.creationDate = creationDate;
   }
   public Integer getModifyBy() {
   	return modifyBy;
   }
   public void setModifyBy(Integer modifyBy) {
   	this.modifyBy = modifyBy;
   }
   public Date getModifyDate() {
   	return modifyDate;
   }
   public void setModifyDate(Date modifyDate) {
   	this.modifyDate = modifyDate;
   }
}
```

**（6）编写基础公共类**

- 1.**数据库配置文件**

```properties
      driver=com.mysql.jdbc.Driver
      url=jdbc:mysql://localhost:3306?useUnicode=true&characterEncoding=utf-8
      username=root
      password=123456
```

- 2.**编写数据库的公共类**

> 在dao包建立BaseDao类

```java
  //操作数据库的公共类
     public class BaseDao {     
         private static String driver;
         private static String url;
         private static String username;
         private static String password;
     
         //静态代码块，类加载的时候就初始化了
         static {
             Properties properties = new Properties();
             //通过类加载器读取对应的资源
             InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
     
             try {
                 properties.load(is);
             } catch (IOException e) {
                 e.printStackTrace();
             }
     
             driver = properties.getProperty("driver");
             url = properties.getProperty("url");
             username = properties.getProperty("username");
             password = properties.getProperty("password");
         }
     
         //获取数据库的链接
         public static Connection getConnection(){
             Connection connection = null;
             try {
                 Class.forName(driver);
                 connection = DriverManager.getConnection(url, username, password);
             } catch (Exception e) {
                 e.printStackTrace();
             }
             return connection;
         }
     
         //编写查询公共方法
         public static ResultSet execute(Connection connection,String sql,Object[] params,ResultSet resultSet,PreparedStatement preparedStatement) throws SQLException {
             //预编译的sql，在后面直接执行就可以了
             preparedStatement = connection.prepareStatement(sql);
     
             for (int i = 0; i < params.length; i++) {
                 //setObject,占位符从1开始，但是我们的数组是从0开始！
                 preparedStatement.setObject(i+1,params[i]);
             }
     
             resultSet = preparedStatement.executeQuery();
             return resultSet;
         }
     
     
         //编写增删改公共方法
         public static int execute(Connection connection,String sql,Object[] params,PreparedStatement preparedStatement) throws SQLException {
             preparedStatement = connection.prepareStatement(sql);
     
             for (int i = 0; i < params.length; i++) {
                 //setObject,占位符从1开始，但是我们的数组是从0开始！
                 preparedStatement.setObject(i+1,params[i]);
             }
     
             int updateRows = preparedStatement.executeUpdate();
             return updateRows;
         }
     
     
         //释放资源
         public static boolean closeResource(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
             boolean flag = true;
     
             if (resultSet!=null){
                 try {
                     resultSet.close();
                     //GC回收
                     resultSet = null;
                 } catch (SQLException e) {
                     e.printStackTrace();
                     flag = false;
                 }
             }
     
             if (preparedStatement!=null){
                 try {
                     preparedStatement.close();
                     //GC回收
                     preparedStatement = null;
                 } catch (SQLException e) {
                     e.printStackTrace();
                     flag = false;
                 }
             }
     
             if (connection!=null){
                 try {
                     connection.close();
                     //GC回收
                     connection = null;
                 } catch (SQLException e) {
                     e.printStackTrace();
                     flag = false;
                 }
             }
             return flag;
         }
     }
```

- 3.**编写字符编码过滤器**

> 在filter中建立CharacterEncodingFilter类

```java
public class CharacterEncodingFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        chain.doFilter(request,response);
    }
    public void destroy() {

    }
}
```

**在web.xml中注册**

```xml
<!--字符编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>com.kuang.filter.CharacterEncodingFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```

**（7）导入静态资源**

## 二.登录注销实现

### 1.登录

#### （1）编写前端代码

```html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <script type="text/javascript">
    </script>
</head>
<body class="login_bg">
    <section class="loginBox">
        <header class="loginHeader">
            <h1>超市订单管理系统</h1>
        </header>
        <section class="loginCont">
	        <form class="loginForm" action="${pageContext.request.contextPath }/login.do"  name="actionForm" id="actionForm"  method="post" >
				<div class="info">${error}</div>
				<div class="inputbox">
                    <label>用户名：</label>
					<input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名" required/>
				</div>	
				<div class="inputbox">
                    <label>密码：</label>
                    <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码" required/>
                </div>	
				<div class="subBtn">
					
                    <input type="submit" value="登录"/>
                    <input type="reset" value="重置"/>
                </div>	
			</form>
        </section>
    </section>
</body>
</html>
```

#### （2）将前端页面设置为首页

> 在web-inf中的web.xml加入以下代码即可实现设置首页功能

```xml
   <!--设置首页-->
    <welcome-file-list>
        <welcome-file>login.jsp</welcome-file>
    </welcome-file-list>
```

#### （3）编写dao层用户登录的接口UserDao

> 在dao中建立一个user包，在包中建一个UserDao接口，在接口中写

```java
public User getLoginUser(Connection connection,String userCode,String userPassword) throws SQLException;
```

#### （4）编写UserDao接口的实现类UserDaoImpl

```java
public class UserDaoImpl implements UserDao {
    //得到要登录的用户
    public User getLoginUser(Connection connection, String userCode,String userPassword) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;

        if (connection!=null){
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};
           //System.out.println(userPassword);
            rs = BaseDao.execute(connection, pstm, rs, sql, params);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null,pstm,rs);
           if (!user.getUserPassword().equals(userPassword))
              user=null;
        }
        return user;
    }
}
```

#### （4）编写业务层接口

> 在service下建立user包，建立UserService接口

```java
public interface UserService {
    //用户登录
    public User login(String userCode,String password);
}
```

#### （5）编写业务层接口的实现类

> 在service的user包中建立UserServiceImpl类

```java
public class UserServiceImpl implements UserService {

    //业务层都会调用dao层，所以我们要引入Dao层；
    private UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }


    public User login(String userCode, String password) {
        Connection connection = null;
        User user = null;

        try {
            connection = BaseDao.getConnection();
            //通过业务层调用对应的具体的数据库操作
            user = userDao.getLoginUser(connection, userCode,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

}
```

#### （6）编写Servlet类

> 在Servlet包中创建user包，在user包中建立LoginServlet类

```java
public class LoginServlet extends HttpServlet {
    //Servlet:控制层，调用业务层代码

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("LoginServlet--start....");
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库中的密码进行对比，调用业务层；
        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);  //这里已经把登录的人给查出来了
        System.out.println(userCode);
        System.out.println(userPassword);
        if (user!=null){ //查有此人，可以登录
            //将用户的信息放到Session中;
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到主页
            resp.sendRedirect("jsp/frame.jsp");
        }else {//查无此人，无法登录
            //转发回登录页面，顺带提示它，用户名或者密码错误；
            req.setAttribute("error","用户名或者密码不正确");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

**在web.xml中注册**

```xml
<servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>com.kuang.servlet.user.LoginServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login.do</url-pattern>
</servlet-mapping>
```

### 2.注销

> 在Servlet包的user包中建立LogoutServlett类

```java
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //移除用户的Constants.USER_SESSION
        req.getSession().removeAttribute(Constants.USER_SESSION);
        resp.sendRedirect("/login.jsp");//返回登录页面
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

**在web.xml中注册**

```xml
    <servlet>
        <servlet-name>LogoutServlet</servlet-name>
        <servlet-class>com.kuang.servlet.user.LogoutServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>LogoutServlet</servlet-name>
        <url-pattern>/jsp/logout.do</url-pattern>
    </servlet-mapping>
```

#### 设置自动注销

> 设置30分后session自动失效

```xml
<session-config>
   <session-timeout>30</session-timeout>
</session-config>
```

### 3.登录拦截

> 在filter中建立SysFilter类

```java
public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //过滤器，从Session中获取用户，
        User user = (User) request.getSession().getAttribute(Constants.USER_SESSION);

        if (user==null){ //已经被移除或者注销了，或者未登录
            response.sendRedirect("/error.jsp");
        }else {
            chain.doFilter(req,resp);
        }
    }

    public void destroy() {

    }
}
```

**在web.xml中注册**

```xml
<!--用户登录过滤器-->
    <filter>
        <filter-name>SysFilter</filter-name>
        <filter-class>com.kuang.filter.SysFilter</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>SysFilter</filter-name>
        <url-pattern>/jsp/*</url-pattern>
    </filter-mapping>
```

## 二.密码修改的实现

### 1.编写dao层用户修改密码的接口UserDao

> UserDao接口写如下代码

```java
//修改当前用户密码
public int updatePwd(Connection connection, int id, String password) throws SQLException;
```

### 2.编写UserDao 接口实现类UserDaoImpl

```java
 public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int execute = 0;
        if (connection!=null){
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object params[] = {password,id};
            execute = BaseDao.execute(connection, sql, params, pstm);
            BaseDao.closeResource(null,pstm,null);
        }

        return execute;
    }
```

### 3.编写业务层接口

> 在service的user包中的UserService接口中添加如下代码

```java
//根据用户ID修改密码
public boolean updatePwd(int id, String pwd);
```

### 4.编写业务层接口实现类

> 在service的user包中的UserServiceImpl类中添加以下代码

```java
public boolean updatePwd(int id, String pwd) {
        Connection connection = null;
        boolean flag = false;
        //修改密码
        try {
            connection = BaseDao.getConnection();
            if (userDao.updatePwd(connection,id,pwd)>0){
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }
```

### 5.编写Servlet类

> 在Servlet包中的user包中建立UserServlet类

```java
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")&&method!=null){
            this.updatePwd(req,resp);
        }else if (method.equals("pwdmodify")){
            this.pwdModify(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        //从Session里面拿ID;
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);

        String newpassword = req.getParameter("newpassword");

        System.out.println("UserServlet:"+newpassword);

        boolean flag = false;

        System.out.println(o!=null);
        System.out.println(StringUtils.isNullOrEmpty(newpassword));

        if (o!=null && newpassword!=null){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if (flag){
                req.setAttribute("message","修改密码成功，请退出，使用新密码登录");
                //密码修改成功，移除当前Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
            }else {
                req.setAttribute("message","密码修改失败");
                //密码修改成功，移除当前Session
            }
        }else {
            req.setAttribute("message","新密码有问题");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //验证旧密码,session中有用户的密码
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从Session里面拿ID;

        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        System.out.println(oldpassword);
        //万能的Map : 结果集
        Map<String, String> resultMap = new HashMap<String,String>();

        if (o==null){ //Session失效了，session过期了
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){ //输入的密码为空
            resultMap.put("result","error");
        }else {
            String userPassword = ((User) o).getUserPassword(); //Session中用户的密码
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }


        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray 阿里巴巴的JSON工具类, 转换格式
            /*
            resultMap = ["result","sessionerror","result","error"]
            Json格式 = {key：value}
             */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```

**在web.xml中注册**

```xml
    <servlet>
        <servlet-name>updatePwd</servlet-name>
        <servlet-class>com.kuang.servlet.user.UserServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>updatePwd</servlet-name>
        <url-pattern>/jsp/user.do</url-pattern>
    </servlet-mapping>
```

## 三.用户管理实现

### 1.获取用户数量

#### （1）编写查询总人数的接口UserDao

> UserDao接口写如下代码

```java
//查询用户总数
public int getUserCount(Connection connection,String username ,int userRole)throws SQLException;
```

#### （2）UserDao接口实现类UserDaoImpl

> 向实现类中添加以下代码

```java
public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        //根据用户名或者角色查询用户总数

        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();//存放我们的参数

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%"); //index:0
            }

            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole); //index:1
            }

            //怎么把List转换为数组
            Object[] params = list.toArray();

            System.out.println("UserDaoImpl->getUserCount:"+sql.toString()); //输出最后完整的SQL语句


            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);

            if (rs.next()){
                count = rs.getInt("count"); //从结果集中获取最终的数量
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }
```

#### （3）编写业务层接口

> 在service的user包中的UserService接口中添加如下代码

```java
//查询记录数
public int getUserCount(String username,int userRole);
```

#### （4）编写业务层接口实现类

> 在service的user包中的UserServiceImpl类中添加以下代码

```java
public int getUserCount(String username, int userRole) {
            Connection connection = null;
            int count = 0;
            try {
                connection = BaseDao.getConnection();
                count = userDao.getUserCount(connection, username, userRole);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                BaseDao.closeResource(connection,null,null);
            }

            return count;
        }
```

### 2.获取用户列表

#### （1）编写UserDao

> UserDao接口写如下代码

```java
//通过条件查询-userList
public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws Exception;
```

#### （2）UserDao接口实现类UserDaoImpl

> 向实现类中添加以下代码

```java
 public List<User> getUserList(Connection connection, String userName,int userRole,int currentPageNo, int pageSize)
            throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while(rs.next()){
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return userList;
    }
```

#### （3）编写业务层接口

> 在service的user包中的UserService接口中添加如下代码

```java
 //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
```

#### （4）编写业务层接口实现类

> 在service的user包中的UserServiceImpl类中添加以下代码

```java
 public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }
```

### 3.获取角色操作

#### （1）编写接口RoleDao

```java
public interface RoleDao {
    //获取角色列表
    public List<Role> getRoleList(Connection connection) throws SQLException;
}
```

#### （2）RoleDao接口实现类RoleDaoImpl

> 向实现类中添加以下代码

```java
public class RoleDaoImpl implements RoleDao {
    //获取角色列表
    public List<Role> getRoleList(Connection connection) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        ArrayList<Role> roleList = new ArrayList<Role>();

        if (connection!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            resultSet = BaseDao.execute(connection, pstm, resultSet, sql, params);

            while (resultSet.next()){
                Role _role = new Role();
                _role.setId(resultSet.getInt("id"));
                _role.setRoleCode(resultSet.getString("roleCode"));
                _role.setRoleName(resultSet.getString("roleName"));
                roleList.add(_role);
            }
            BaseDao.closeResource(null,pstm,resultSet);
        }
        return roleList;
    }
}
```

#### （3）编写业务层接口

> 在service建立role包，然后建立RoleService接口，添加如下代码

```java
 //获取角色列表
public List<Role> getRoleList();
12
```

#### （4）编写业务层接口实现类

> 在service的role包中的RoleServiceImpl类中添加以下代码

```java
public class RoleServiceImpl implements RoleService {

    //引入Dao
    private RoleDao roleDao;
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    public List<Role> getRoleList() {

        Connection connection = null;
        List<Role> roleList = null;
        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }
}
```

### 4.编写Servlet类

> 对Servlet包中的user包中UserServlet类进行如下添加

```java
//添加一个if判断
@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")&&method!=null){
            this.updatePwd(req,resp);
        }else if (method.equals("pwdmodify")&&method!=null){
            this.pwdModify(req, resp);
        }else if (method.equals("query")&&method!=null){
            this.query(req, resp);

        }
    }

    //query方法
    public void query(HttpServletRequest req, HttpServletResponse resp){

        //查询用户列表

        //从前端获取数据；
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = null;

        //第一次走这个请求，一定是第一页，页面大小固定的；
        int pageSize = 5; //可以把这个些到配置文件中，方便后期修改；
        int currentPageNo = 1;

        if (queryUserName ==null){
            queryUserName = "";
        }
        if (temp!=null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);  //给查询赋值！0,1,2,3
        }
        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总数 (分页：  上一页，下一页的情况)
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = ((int)(totalCount/pageSize))+1;

        //控制首页和尾页
        //如果页面要小于1了，就显示第一页的东西
        if (currentPageNo<1){
            currentPageNo = 1;
        }else if (currentPageNo>totalPageCount){ //当前页面大于了最后一页；
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList",userList);

        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);


        //返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```



# 源码

SMBMS项目源码链接（转载自：https://blog.csdn.net/bell_love/article/details/106157413?utm_medium=distribute.pc_relevant.none-task-blog-title-3&spm=1001.2101.3001.4242）
[Eclipse版（手打版，已排错，可运行）
https://www.lanzoux.com/iGLJTdusuyf](https://www.lanzoux.com/iGLJTdusuyf)



