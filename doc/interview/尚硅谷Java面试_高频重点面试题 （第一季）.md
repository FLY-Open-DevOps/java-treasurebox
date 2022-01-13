                                                       尚硅谷Java面试_高频重点面试题 （第一季）
#### 01 自增变量

```java
inti=1;
i =i++;
int j = i++;
int k = i +++i* i++;
System. out. println("i=" + i);
System. out .print1n("j=" + j);
System. out. print1n("k=" + k);
```

解析：4，1，11

```java
inti=1;
i =i++;
把i的值压入操作数栈
i变量自增1
把操作数栈中的值赋值给i
```

```java
int j = i++;
把i的值压入操作数栈
i变量自增1
把操作数栈中的值赋值给j
```

```java
int k = i +++i* i++;
把i的值压入操作数栈
i变量自增1
把i的值压入操作数栈
把i的值压入操作数栈
i变量自增1
把操作数栈中前两个弹出求乘
积结果再压入栈
把操作数栈中的值弹出求和再
赋值给k
```

##### **小结:**

**赋值= ,最后计算**
**=右边的从左到右加载值依次压入操作数栈**
**实际先算哪个,看运算符优先级**
**自增、自减操作都是直接修改变量的值,不经过操作数栈**
**最后的赋值之前,临时结果也是存储在操作数栈中**



#### 02 单例设计模式

单例设计模式，即某个类在整个系统中只能有一个实例
对象可被获取和使用的代码模式。
例如:代表JVM运行环境的Runtime类

##### 要点

●一是某个类只能有一个实例;
	构造器私有化
●二是它必须自行创建这个实例;
	含有一个该类的静态变量来保存这个唯一的实例
●三是它必须自行向整个系统提供这个实例;
	对外提供获取该实例对象的方式:

##### 几种常见形式

###### 饿汉式:直接创建对象,不存在线程安全问题。在线程还没出现以前就已经实例化，不可能存在访问安全问题。

Spring 中 IOC 容器 ApplicationContext 本身就是典型的饿汉式单例。

###### 在类初始化时直接创建实例对象，不管你是否需要这个对象

* (1) 构造器私有化
* (2) 自行创建，并且用静态变量保存
* (3)向外提供这个实例
* (4) 强调这是一个单例，我们可以用final修改

●直接实例化饿汉式(简洁直观)

```java
public class Singleton1 {
    public static final Singleton1 INSTANCE = new Singleton1();
        private Singleton1(){
        }
}
```

●枚举法(最简洁)

```Java
/*
*枚举类型:表示该类型的对象是有限的几个
*我们可以限定为一个，就成了单例
*/
public enum Singleton2 {
	INSTANCE
}
```

●静态代码块饿汉式(适合复杂实例化)

```java
public class Singleton3 {
	public static final Singleton3 INSTANCE ;
    static{
    INSTANCE = new Singleton3();
    }
    private singleton3()k{
    }
}
//静态代码块用来加载文件很方便，可以防止硬编码
```

###### 懒汉式:延迟创建对象 

被外部应用类首次调用全局访问接口的时候内部类才会加载，这样对比饿汉式来说，只有被使用时才会占用堆内存空间。

​      ●  (1 )构造器私有化
​      ●  (2 )用一个静态变量保存这个唯一的实例
​      ●  (3 )提供- -个静态方法，获取这个实例对象

●线程不安全(适用于单线程)

```Java
public class Singleton4 {
    private static Singleton4 instance;
    private Singleton4(){
    }
    public static singleton4 getInstance(){
    if(instance == nu1l){
    instance = new Singleton4();
    }
    return instance;
    }
}
```

●线程安全(适用于多线程) 

DLC(Double Lock Check)双重检测锁模式

```
public class Singleton5 {
    private static Singleton5 instance;
    private Singleton5(){
    }
    public static Singleton5 getInstance(){
    	synchronized (Singleton5.class) {
    		if(instance == nu1l){
        		try {
        			Thread.sleep(100) ;
        		} catch (InterruptedException e) {
        			e. printStackTrace();
        		}
       	 		instance = new Singleton5();
        	}
    	}
    	return instance;
    }
}
```

●静态内部类形式(适用于多线程)

```java
/*
*在内部类被加载和初始化时，才创建INSTANCE实例对象
*静态内部类不会自动随着外部类的加载和初始化而初始化，它是要单独去加载和初始化的。
*因为是在内部类加载和初始化时， 创建的，因此是线程安全的
*/
public class Singleton6 {
    private Singleton6( ){
    }
    private static class Inner{
    private static final Singleton6 INSTANCE = new Singleton6();
    }
    public static Singleton6 getInstance(){
       return Inner.INSTANCE ;
    }
}
```

**小结**
**●如果是饿汉式,枚举形式最简单**
**●如果是懒汉式,静态内部类形式最简单**

#### 03 类初始化和实例初始化

```java
/*
*父类的初始化<clinit>:
*(1) j= method();
*(2)父类的静态代码块
*
*父类的实例化方法:
*(1) super()(最前)
*(2) i=test;
*(3)父类的非静态代码块
*(4)父类的无参构造(最后)
*
*非静态方法前面其实有一个默认的对象this
*this在构造器(或<init>)它表示的是正在创建的对象，因为这里是在创建Son对象，所以
*test( )执行的是子类重写的代码( 面向对象多态)
*
*这里i=test()执行的是子类重写的test()方法
*
*/
public class Father{
    private int i = test();
    private static int j = method(
    static{
    	System. out . print("(1)");
    }
    Father(){
    	System. out.print("(2)");
    }
    {
    	System.out.print("(3)");
    }
    public int test(){
   		System.out. print("(4)");
   		return 1;
   	}
    public static int method(){
   		System.out.print("(5)");
    	return 1;
    }
}
```

```java
/*
*子类的初始化<clinit>:
*(1) j = method();
*(2)子类的静态代码块
*先初始化父类（5）（1）
*初始化子类（10）（6）
*
*子类的实例化方法:
*(1) super()(最前)   (9) (3) (2)
*(2) i=test; 	(9)
*(3)子类的非静态代码块	(8)
*(4)子类的无参构造(最后)	(7)
*
*因为创建了两个Son对象，因此实例化方法<init>执行2次
*  (9) (3) (2) (9) (8) (7)
*/
public class Son extends Father{
    private int i = test();
    private static int i = method();
    static{
    	System . out . print("(6)");
    Son(){
        //super() 写或不写都在，在子类构造器中一定会调用父类的构造器
    	System. out . print("(7)");
    }
    {
    	System. out . print("(8)");
    public int test(){
    	System. out. print("(9)");
    	return 1;
    }
    public static int method(){
    	System . out . print("(10)");
    	return 1 ;
    }
    public static void main(String[] args) {
    	Son s1 = new Son();
    	System. out. println();
    	Son s2 = new Son();
    }
}
```

##### 类初始化过程

①一个类要创建实例需要先加载并初始化该类
	◆main方法所在的类需要先加载和初始化
②一个子类要初始化需要先初始化父类

③一个类初始化就是执行<clinit>()方法
	◆<clinit>()方法由静态类变量显示赋值代码和静态代码块组成
	◆类变量显示赋值代码和静态代码块代码从上到下顺序执行
	◆<clinit>()方法只执行一次



##### 实例初始化过程

①实例初始化就是执行<init>()方法
	◆<init>()方法可能重载有多个，有几个构造器就有几个<init>方法
	◆<init>()方法由非静态实例变量显示赋值代码和非静态代码块、对应构造器什
	码组成
	◆非静态实例变量显示赋值代码和非静态代码块代码从.上到下顺序执行，而对应
	构造器的代码最后执行
	◆每次创建实例对象，调用对应构造器，执行的就是对应的<init>方法
	◆<init>方法的首行是super ()或super (实参列表)，即对应父类的<init>方法



##### 方法的重写Override

①哪些方法不可以被重写
	◆final方法
	◆静态方法
	◆private等子类中不可见方法
②对象的多态性
	◆子类如果重写了父类的方法，通过子类对象调用的一定是子类重写过的代码
	◆非静态方法默认的调用对象是this
	◆this对象在构造器或者说<init>方法中就是正在创建的对象



##### Override重写的要求?

●方法名
●形参列表
●返回值类型
●抛出的异常列表
●修饰符



#### 04 方法的参数传递机制

```java
pub1ic class Exam4 {
    public static void main(String[] args) {
        inti=1;
        String str = "hello"; 
        Integer num = 200;
        int[] arr = {1,2,3,4,5};
        MyData my = new MyData();
        change(i,str, num, arr ,my);
        System. out.println("i =”+ i);
        System. out . println("str =”+ str);
        System. out . println("num = " + num);
        System. out. println("arr =”+ Arrays. toString(arr));
        System. out . println("my.a =”+ my.a);
    }
    public static void change(int j, String s, Integer n, int[] a,MyData m){
        j+=1;
        s += "world";
        n+=1;
        a[0] += 1;
        m.a += 1;
    }
}
class MyData{
	inta=10;
}
//i=1 str=hello num=200 arr=[2,2,3,4,5] my.a=11
                              
```

```java
inti=1;  //入栈
String str = "hello"; //hello放入常量池 栈中存放引用地址 注意：字符串常量池jdk7改为堆中
 Integer num = 200; //包装类的200存入堆中 
int[] arr = {1,2,3,4,5}; //数组元素存放于堆中 栈中存放引用地址
MyData my = new MyData(); //在堆中将对象属性a初始化存放 栈中存放引用地址
```

##### 方法的参数传递机制:

​	①形参是基本数据类型
​		◆传递数据值
​	②实参是引用数据类型
​		◆传递地址值
​		◆特殊的类型: String, 包装类等对象不可变性

```java
 change(i,str, num, arr ,my);
 public static void change(int j, String s, Integer n, int[] a,MyData m){
 //j直接获取i的值 s获取str的地址 n获取num的地址值 a获取arr的地址值 m获取my的a的地址值
        j+=1;  //变更j在栈中的值为2 
        s += "world"; //在常量池中创建world 并且拼接hello 最后s指向拼接完成的helloworld的地址
        n+=1; //会重新生成201这个对象 n指向201的地址
        a[0] += 1; //通过a[0]找到数组第一个元素+1
        m.a += 1; //通过m找到a对象 值加一
    }
```

#### 05 递归与迭代

##### 编程题:有n步台阶，一次只能上1步或2步，共有多少种走法? [可见力扣](https://leetcode-cn.com/problems/climbing-stairs/)



##### 1.递归 

●n=1			->一步										->f(1) = 1

●n=2			->(1)一步一步(2)直接2步		->f(2) = 2

●n=3			->(1)先到达f(1)，然后从f(1)直接跨2步		->f(3) = f(1) + f(2)
						 (2)先到达f(2)，然后从f(2)跨1步

●n=4			->(1)先到达f(2)，然后从f(2)直接跨2步		->f(4) = f(2) + f(3)
					 	(2)先到达f(3)，然后从f(3)跨1步

●n=x.		   ->(1)先到达f(x-2)， 然后从f(x-2)直接跨2步	->f(x) = f(x-2) + f(x-1)
						 (2)先到达f(x-1)，然后从f(x-1)跨1步

代码实现：

```java
public class TestStep{
    @Test
    public void test(){
    	System.out.println(f(4));
    }
    //实现f(n):求n步台阶，-共有几种走法
    public int f(int n){
        if(n<1){
       		throw new IllegalArgumentException(n + "不能小于1");
        }
        if(n==1 | n==2){
        	return n;
        }
        return f(n-2) + f(n-1);
	}
}	
```

##### 2 循环迭代  

one保存最后走一步

two保存最后走两步

●n=1			->一步										->f(1) = 1

●n=2			->(1)一步一步(2)直接2步		->f(2) = 2

●n=3			->(1)先到达f(1)，然后从f(1)直接跨2步		->f(3) =two+one
						 (2)先到达f(2)，然后从f(2)跨1步					f(3)=f(1)+f(2)
																									tow=f(1):one=f(2)
																			

●n=4			->(1)先到达f(2)，然后从f(2)直接跨2步		->f(4) = two + one
						 (2)先到达f(3)，然后从f(3)跨1步					f(4) = f(2) + f(3)
																									two=f(2),one=f(3)

●n=x.		   ->(1)先到达f(x-2)， 然后从f(x-2)直接跨2步	->f(x) = two +one
						 (2)先到达f(x-1)，然后从f(x-1)跨1步				f(x) = f(x-2) + f(x-1)
																										two=f(x-2);one=f(x-1)

代码实现：

```java
public class TestStep2 {
    @Test
    public void test(){
    	System.out.println(f(4));
    }
    public int loop(int n){
    	if(n<1){
   			 throw new IllegalArgumentException(n + "不能小于1");
    	}
    	if(n==1 | | n==2){
   			 return n;
    	}
    	int one = 2;//初始化为走到第二级台阶的走法
    	int two = 1;//初始化为走到第一级台阶的走法
    	int sum = 0;
    	for(int i=3; i<=n; i++){
    	//最后跨2步+最后跨1步的走法
    	sum = two + one;
    	two = one;
    	one = sum; 
        //n=4时是最后跨2步(以第2级开始跨，就是说
        //之前走到第2步的走法，所以two=one) +跨1步(从第3级跨，所one=sum)
    	}
    return sum;
    }
}
```

3  动态规划

![tujie](https://assets.leetcode-cn.com/solution-static/70/70_fig1.gif)

```java
class Solution {
    public int climbStairs(int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; ++i) {
            p = q; 
            q = r; 
            r = p + q;
        }
        return r;
    }
}
```

##### 小结

●方法调用自身称为递归，利用变量的原值推出新值称为迭代。

###### ●递归

​	●优点:大问题转化为小问题，可以减少代码量，同时代码精简，可读性好;
​	●缺点:递归调用浪费了空间，而且递归太深容易造成堆栈的溢出。

###### ●迭代

​	●优点:代码运行效率好，因为时间只因循环次数增加而增加，而且没有额外
​	的空间开销;
​	●缺点:代码不如递归简洁，可读性好



#### 06 成员变量和局部变量

```java
public class Exam5 {
    static int s;  //成员变量,类变量
    int i; //成员变量，实例变量
    int j; //成员变量，实例变量
    {
        int i=1; //非静态代码块中的局部变量i
        i++; //i的变量是int i=1(就近原则)
        j++;
        s++;
    }
    public void test(int j){ //形参，局部变量,j
       	j++; //j是int j传入
        i++; //这个是int i(作用域)
        s++;
    }
    public static void main(String[] args) { //形参，局部变量，args
        Exam5 obj1 = new Exam5(); //局部变量 obj1 ,实例化的对象obj成员变量int i和int j是存在于堆中 类变量s存在于方法区中（共享） int i 实例化后出栈
        Exam5 obj2 = new Exam5(); //局部变量 obj2,同obj1的实例化过程，但是类变量是共享的所以int s值为2
        obj1. test(10); //传入int j=10（并不会改变obj1的j的值），j=11,i++寻找obj1的成员变量i，i变为1，s++是类变量为共享所以s=3
        obj1. test(20);	//传入j=20，同样的改变类变量 i和类变量s
        obj2. test(30);
        System. out . println(obj1.i + "," + obj1.j + ","
        + obj1.s);
        System. out . println(obj2.i + "," :
        + obj2.j + ",”
        + obj2.s);
    }
}
//运行结果 2，1，5
//1，1，5
```

##### 局部变量与成员变量的区别:

##### ①声明的位置

​	●局部变量:方法体{}中，形参，代码块{}中
​	●成员变量:类中方法外
​	●类变量: 有static修饰
​	●实例变量:没有static修饰

##### ②修饰符

​	●局部变量: final
​	●成员变量: public、 protected、private、final、static、 volatile、 transient

##### ③值存储的位置

​	●局部变量:栈
​	●实例变量:堆
​	●类变量:方法区

> **堆(Heap)，此内存区域的唯--目的就是存放对象实例}几乎所有的对象实例都在这**
> **里分配内存。这一点在Java虛拟机规范中的描述是:所有的对象实例以及数组都要在**
> **堆上分配。**
> **通常所说的栈(Stack) ，是指虚拟机栈。虚拟机栈用于存储局部变量表等。局部变量**
> **表存放了编译期可知长度的各种基本数据类型(boolean、byte、char、short. int、 float、**
> **long、double) 、对象引用(reference 类型，它不等同于对象本身，是对象在堆内存的**
> **首地址)。方法执行完， 自动释放。**
> **方法区(Method Area)用于存储已被虚拟机加载的类信息、常量、静态变量、即时编**
> **译器编译后的代码等数据。注意：字符串常量池jdk7改为堆中**

##### 

##### ④作用域

​	●局部变量:从声明处开始，到所属的}结束
​	●实例变量:在当前类中“this. ”(有时this.可以缺省)，在其他类中“对象
​	名.”访问
​	●类变量:在当前类中“类名.”(有时类名.可以省略)，在其他类中“类名.”
​	或“对象名.”访问

##### ⑤生命周期

​	●局部变量: 每一个线程，每一次调用执行都是新的生命周期
​	●实例变量:随着对象的创建而初始化，随着对象的被回收而消亡，每一个对象
​	的实例变量是独立的
​	●类变量:随着类的初始化而初始化，随着类的卸载而消亡，该类的所有对象
​	的类变量是共享的

##### 当局部变量与xx变量重名时，如何区分

①局部变量与实例变量重名
	●在成员|变量前面加“this.”
②局部变量与类变量重名
	●在类变量前面加“类名.



#### 07 Spring Bean的作用域之间有什么区别

##### Bean的作用域

在Spring中，可以在<bean>元素的scope属性里设置bean的作用域，以决定这个bean
是单实例的还是多实例的。
默认情况下，Spring 只为每个在I0C容器里声明的bean创建唯一一个实例， 整个I0C
容器范围内都能共享该实例:所有后续的getBean()调用 和bean引用都将返回这个唯一的
bean实例。该作用域被称为singleton， 它是所有bean的默认作用域。

| 作用域      | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| singleton   | 在spring IoC容器仅存在一个Bean实例，Bean以单例方式存在，bean作用域范围的默认值。 |
| prototype   | 每次从容器中调用Bean时，都返回一个新的实例，即每次调用getBean()时，相当于执行newXxxBean()。 |
| request     | 每次HTTP请求都会创建一个新的Bean，该作用域仅适用于web的Spring WebApplicationContext环境。 |
| session     | 同一个HTTP Session共享一个Bean，不同Session使用不同的Bean。该作用域仅适用于web的Spring WebApplicationContext环境。 |
| application | 限定一个Bean的作用域为`ServletContext`的生命周期。该作用域仅适用于web的Spring WebApplicationContext环境。 |



#### 08 Spring支持的常用数据库事务传播属性和和事务隔离级别

事务属性

    1.propagation:用来设置事务传播行为
    
        事务传播行为:一个方法运行在一个开启了事务的方法中时,当前方法是使用原来的事务还是开启一个新的事务
    
        Propagation.REQUIRED: 默认值 使用原来的事务 
    
        Propagation.REQUIRES_NEW: 将原来的事务挂起 开启一个新的事务
    
    2.isolation: 用来设置事务的隔离级别
    
        Isolation.REPEATABLE_READ: 可重复读 MySQL默认隔离级别
    
        Isolation.READ_COMMITTED: 读已提交 Oracle默认隔离级别 开发时通常使用的隔离级别
事务的传播行为

当事务方法被另一一个事务方法调用时，必须指定事务应该如何传播。例如:方法可能继
续在现有事务中运行，也可能开启一一个新事务，并在自己的事务中运行。
事务的传播行为可以由传播属性指定。

Spring定义了7种类传播行为。

| 事务行为                  | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| PROPAGATION_REQUIRED      | 支持当前事务，假设当前没有事务。就新建一个事务               |
| PROPAGATION_SUPPORTS      | 支持当前事务，假设当前没有事务，就以非事务方式运行           |
| PROPAGATION_MANDATORY     | 支持当前事务，假设当前没有事务，就抛出异常                   |
| PROPAGATION_REQUIRES_NEW  | 新建事务，假设当前存在事务。把当前事务挂起                   |
| PROPAGATION_NOT_SUPPORTED | 以非事务方式运行操作。假设当前存在事务，就把当前事务挂起     |
| PROPAGATION_NEVER         | 以非事务方式运行，假设当前存在事务，则抛出异常               |
| PROPAGATION_NESTED        | 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。 |

事务传播属性可以在@Transactional注解的propagation属性中定义。。



数据库并发事务引起的问题以及如何避免

1.更新丢失——mysql所有事务隔离级别在数据库层面均可避免。 

        例: 一个事务的更新覆盖了另一个事务的更新。事务A：向银行卡存钱100元。事务B：向银行卡存钱200元。A和B同时读到银行卡的余额，分别更新余额，后提交的事务B覆盖了事务A的更新。更新丢失本质上是写操作的冲突，解决办法是一个一个地写。

2.脏读——READ-COMMITTED事务隔离级别以上可以避免。（未提交的事务修改的数据也能读取到而引起的错误数据，RC级别以上可避免）

           例: 一个事务读取了另一个事务未提交的数据。事务A：张三妻子给张三转账100元。事务B：张三查询余额。事务A转账后（还未提交），事务B查询多了100元。事务A由于某种问题，比如超时，进行回滚。事务B查询到的数据是假数据。脏读本质上是读写操作的冲突，解决办法是写完之后再读。

 3.不可重复读——REPETABLE-READ事务隔离级别以上可避免。（加锁后读取的数据会因为其他事务操作而变化，RR级别以上可避免）

        例: 一个事务两次读取同一个数据，两次读取的数据不一致。事务A：张三妻子给张三转账100元。事务B：张三两次查询余额。事务B第一次查询余额，事务A还没有转账，第二次查询余额，事务A已经转账了，导致一个事务中，两次读取同一个数据，读取的数据不一致。不可重复读本质上是读写操作的冲突，解决办法是读完再写。

 4.幻读——SERIALIZABLE事务隔离级别可避免。

        例: 一个事务两次读取一个范围的记录，两次读取的记录数不一致。事务A：张三妻子两次查询张三有几张银行卡。事务B：张三新办一张银行卡。事务A第一次查询银行卡数的时候，张三还没有新办银行卡，第二次查询银行卡数的时候，张三已经新办了一张银行卡，导致两次读取的银行卡数不一样。幻象读本质上是读写操作的冲突，解决办法是读完再写。

 




##### 事务隔离级别

　	事务隔离级别指的是多个并发事务之间的隔离程度

```
　　1）  ISOLATION_DEFAULT

　　　　此种隔离级别是事务管理默认配置的，使用数据库的默认隔离级别，上面的配置中就是使用此种级别，下面的四种级别与jdbc的相对应。

　　2）  ISOLATION_READ_UNCOMMITTED

　　　　未提交读。允许其他事务可以看到本事务未提交的数据，可造成脏读、幻读和不可重复读

　　3）  ISOLATION_READ_COMMITTED(工作常用)

　　　　提交读。其他事务只能读取到本事务提交后的数据，本事务不提交，其他事务无法读取到本事务数据，可防止脏读，可能出现幻读和不可重复读

　　4）  ISOLATION_REPEATABLE_READ

　　　　可重复读。保证事务不提交就不会读取到其数据，防止脏读和不可重复读，可能发生幻读。 (MySQL默认隔离级别)

　　5）  ISOLATION_SERIALIZABLE

　　　　可串行化。牺牲效率顺序执行事物，如果事物执行异常，其他事务阻塞。防止脏读，幻读和不可重复。
```

###### 各个隔离级别解决并发问题的能力

|                  | 脏读 | 不可重复读 | 幻读 |
| ---------------- | ---- | ---------- | ---- |
| READ_UNCOMMITTED | 有   | 有         | 有   |
| READ_COMMITTED   | 无   | 有         | 有   |
| REPEATABLE_READ  | 无   | 无         | 有   |
| SERIALIZABLE     | 无   | 无         | 无   |



###### 各种数据库产品对事务隔离级别的支持程度

|                  | Oracle  | Mysql   |
| ---------------- | ------- | ------- |
| READ_UNCOMMITTED | ×       | √       |
| READ_COMMITTED   | √（默认 | √       |
| REPEATABLE_READ  | ×       | √(默认) |
| SERIALIZABLE     | √       | √       |



#### 09  SpringMVC中如何解决POST请求中文乱码问题

解决Post请求中文乱码问题
在web.xml文件中配置字符编码过滤器。

```xml
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name> <!--强制编码-->
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>                              
```

##### **CharacterEncodingFilter过滤器类浅析**

###### 打开该类源码:

```java
@Nullable
private String encoding; 设置的字符集，-般设置UTF-8
private boolean forceRequestEncoding = false;强制请求编码
private boolean forceResponseEncoding = false ;是否强制设置response的编码为encoding

```

###### 主要方法:

```java
@override
protected void doFilterInternal(
	HttpServletRequest request, HttpServLetResponse response, FilterChain filterChain)
	throws ServLetException, I0Exception {

	string encouing=getEncoding(); //得到encoding==utf-8

	if (encoding != nu1l) {
		if (isForceRequestEncoding || request.getCharacterEncoding() == null) {
            //如果设置request强制编码或者request本身没有设置编码，则设置编码为encoding的值
			request.setCharacterEncoding(encoding);
        }
		if (isForceResponseEncoding()) {
            //如果设置request强制编码，则设置编码为encoding的值
			response.setCharacterEncoding(encoding);
        }
		filterChain. doFilter(request, response);
}
```

##### 解决GET请求中文乱码问题

修改tomcat中server.xml文件

```xml
<Connector URIEncoding="UTF-8" port="8080" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
```



#### 10 简单的谈一下SpringMVC的工作流程



![](https://gitee.com/zssea/picture-bed/raw/master/img/wyuygdaygv.png)

```java
@Controller
public class SpringMVCHandler {
	
	public static final String SUCCESS="success";

	//1.简单的谈一下SpringMVC的工作流程
	
	//处理模型数据方式一：将方法的返回值设置为ModelAndView
	@RequestMapping("/testModelAndView")
	public ModelAndView testModelAndView() {
		//1.创建ModelAndView对象
		ModelAndView mav = new ModelAndView();
		//2.设置模型数据，最终会放到request域中
		mav.addObject("user", "admin");
		//3.设置视图
		mav.setViewName("success");
		return mav;
	}
	/*
	 * ★处理模型数据方式二：方法的返回值仍是String类型，在方法的入参中传入Map、Model或者ModelMap
	 * 	不管将处理器方法的返回值设置为ModelAndView还是在方法的入参中传入Map、Model或者ModelMap，
	 *  SpringMVC都会转换为一个ModelAndView对象
	 */
	@RequestMapping("/testMap")
	public String testMap(Map<String , Object> map) {
		//向Map中添加模型数据，最终会自动放到request域中
		map.put("user", new Employee(1, "韩总", "hybing@atguigu.com", new Department(101, "教学部")));
		return SUCCESS;
	}
	
}
```



#### 11 MyBatis中当实体类中的属性名和表中的字段名不一样，怎么办?



##### 方法一：写SQL语句时起别名

```xml
	<select id="getEmployeeById" resultType="com.atguigu.mybatis.entities.Employee">
		select id,first_name firstName,email,salary,dept_id deptID from employees where id = #{id}
	</select>
```

##### 方法二：在MyBatis的全局配置文件中开启驼峰命名规则

```xml
mapUnderscoreToCamelCase：true/false 
<!--是否启用下划线与驼峰式命名规则的映射（如first_name => firstName）-->
<configuration>  
    <settings>  
        <setting name="mapUnderscoreToCamelCase" value="true" />  
    </settings>  
</configuration>
```

##### 方法三：在Mapper映射文件中使用resultMap来自定义映射规则

```xml
	<select id="getEmployeeById" resultMap="myMap">
		select * from employees where id = #{id}
	</select>
	
	<!-- 自定义高级映射 -->
    <resultMap type="com.atguigu.mybatis.entities.Employee" id="myMap">
    	<!-- 映射主键 -->
    	<id column="id" property="id"/>
    	<!-- 映射其他列 -->
    	<result column="last_name" property="lastName"/>
    	<result column="email" property="email"/>
    	<result column="salary" property="salary"/>
    	<result column="dept_id" property="deptId"/>
    </resultMap>
```



#### 12 Linux常用服务类相关命令



##### service(centos6)

●注册在系统中的标准化层序

●有方便统一的管理方式（常用的方法）

​	●service 服务名 start
​	●service 服务名 stop
​	●service 服务名 restart
​	●service 服务名 reload
​	●service 服务名 status

●查看服务的方法 /etc/init.d/服务名

●通过chkconfig 命令设置自启动

​	●查看服务 chkconfig --list|grep xxx

​	●chkconfig --level 5 服务名 on

​		(on 是启动，off是关闭)

###### 运行级别(centos6)

开机→BIOS→/boot→init进程→运行级别→运行级对应的服务

查看默认级别: vi /etc/inittab

Linux系统有7种运行级别(runlevel):常用的是级别3和5
●运行级别0:系统停机状态，系统默认运行级别不能设为0，否则不能正
	常启动.
●运行级别1:单用户工作状态，root权限，用于系统维护，禁止远程登
	陆
●运行级别2:多用户状态(没有NFS),不支持网络
●运行级别3:完全的多用户状态(有NFS),登陆后进入控制台命令行模式
●运行级别4:系统未使用，保留
●运行级别5: X1 1控制台,登陆后进入图形GUI模式
●运行级别6:系统正常关闭并重启,默认运行级别不能设为6,否则不能
	正常启动

常用3,5级别

##### systemctl(centos7)

●注册在系统中的标准化程序

●有方便统一的管理方式（常用的方法）
	●systemctl start 服务名(xxxx.service)
	●systemctl restart 服务名(xxxx.service)
	●systemctl stop 服务名(xxxx.service)
	●systemctl reload 服务名(xxxx.service)
	●systemctl status 服务名(xxxx.service)

●查看服务的方法 /usr/lib/systemd/system

●查看服务的命令
	●systemctl list-unit-files
	●systemctl --type service

●通过systemctl命令设置自启动
	●自启动systemctl enable service_name
	●不自启动systemctl disable serivice_name 



#### 13 Git分支相关命令，实际应用

##### ●创建分支

​	●git branch <分支名>
​	●git branch -v 查看分支

##### ●切换分支

​	●git checkout <分支名>

●一步完成：git checkout -b <分支> （常用的是这个创建分支的命令）

#####  ●合并分支

​	●先切换到主干 git checkout master
​	●git merge <分支名>

##### ●删除分支

​	●先切换到主干 git checkout master
​	●git branch -D <分支名>

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210604171906.png)

#### 14 Redis持久化有几种类型，他们的区别

##### RDB

在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里

###### 备份是如何指向的？

Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能。如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式要比AOF方式更加高效。RDB的缺点是最后一次持久化后的数据可能丢失

######  RDB的备份

先通过config get dir 查询rdb文件的目录

将*.rdb的文件拷贝到别的地方

######  RDB的恢复

关闭Redis

先把备份的文件拷贝到工作目录下

启动Redis，备份数据会直接加载

######  RDB的优点

1）节省磁盘空间

2）恢复速度快

######  RDB的缺点

1）虽然Redis在fork时使用了写时拷贝技术，但是如果数据庞大时还是比较消耗性能

2）在备份周期在一定间隔时间做一次备份，所以如果Redis意外down调的话，就会丢失最后一次快照后的所有修改

 

##### AOF

以日志的形式来记录每个写操作，将Redis执行过的所有写指令记录下来（读操作不记录），只许追加文件但不可以改写文件，Redis启动之初会读取该文件重新构建数据，换言之，Redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作

######  AOF的优点

1）丢失数据概率更低

2）可读的日志文本，通过操作AOF文件，可以处理误操作

######  AOF的缺点

1）比起RDB占用更多的磁盘空间

2）恢复备份速度要慢

3）每次读写都同步的话，有一定的性能压力

4）存在个别bug，造成恢复不能



#### 15  Mysql什么时候创建索引

###### 什么是索引（本质：数据结构）

　　索引是帮助MySQL高效获取数据的数据结构。

###### 优势：

　　1、提高数据检索的效率，降低数据库IO成本

　　2、通过索引对数据进行排序，降低数据排序的成本，降低了CPU的消耗

###### 劣势：

　　降低更新表的速度，如对表进行update 、delete、insert等操作时，MySQL不急要保存数据，还要保存一下索引文件每次添加了索引列的字段，都会调整因为更新带来的键值变化后的索引信息。

###### 适合创建索引条件

　　1.、主键自动建立唯一索引

　　2、频繁作为查询条件的字段应该建立索引

　　3、查询中与其他表关联的字段，外键关系建立索引

　　4、单键/组合索引的选择问题，组合索引性价比更高

　　5、查询中排序的字段，排序字段若通过索引去访问将大大提高排序效率

　　6、查询中统计或者分组字段

###### 不适合创建索引条件

　　1、表记录少的

　　2、经常增删改的表或者字段

　　3、where条件里用不到的字段不创建索引

　　4、过滤性不好的不适合建索引



#### 16 JVM垃圾回收机制，GC发生在JVM哪部分， 有几种GC，它们的算法是什么

###### JVM体系结构概览（GC发生在堆中）

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210604213310.png)



##### CC是什么(分代收集算法)

次数.上频繁收集Young区   Minor GC
次数上较少收集Old区     Full GC o
基本不动Perm区

###### 引用计数算法(已淘汰)

算法分析

引用计数时垃圾收集器中的早期策略。在这种方法中，堆中每个对象实例都有一个引用计数。当一个对象被创建时，就将该对象实例分配给一个变量，该变量计数设置为1。当任何其他变量被赋值为这个对象的引用时，计数加1（a=b，则b引用的对象实例的计数器+1），但当一个对象实例的某个引用超过了生命周期或者被设置为一个新值时，对象实例的引用计数器减1。任何引用计数器为0的对象实例可以被当作垃圾收集。当一个对象实例被垃圾收集时，它引用的任何对象实例的引用计数器减1。

优缺点

优点：引用计数收集器可以很快的执行，交织在程序运行中。对程序需要不被长时间打断的实时环境比较有利。

缺点：无法检测出循环引用。如父对象有一个对一对象的引用，子对象反过来引用父对象。这样他们的引用计数永远不可能为0。每次对对象赋值时均要维护引用计数器，且计数器本身也有一-定的消耗 。



年轻代中使用的是Minor GC，这种GC算法采用的是复制算法(Copying)

###### 复制算法

从根集合(GC Root)开始,通过Tracing从From中找到存活对象,拷贝到To中;
From、To交换身份，下次内存分配从To开始;

![img](https://img2018.cnblogs.com/blog/1484488/201809/1484488-20180917233839786-1447062630.jpg)

优缺点

优点：没有标记和清除的过程,效率高。没有内存碎片,可以利用bump -the-pointer实现快速内存分配。

缺点：需要双倍空间



老年代一般是由标记清除或者是标记清除与标记整理的混合实现



###### 标记—清除算法

1.标记(Mark):
从根集合开始扫描,对存活的对象进行标记。
2.清除(Sweep):
扫描整个内存空间,回收未被标记的对象,使用free-list记录可以区域。

![img](https://img2018.cnblogs.com/blog/1484488/201809/1484488-20180917233643508-893259891.jpg)

优缺点

优点：不需要额外空间

缺点：两次扫描，耗时严重。会产生内存碎片。



######  标记—整理算法

1.标记(Mark):
与标记-清除一样。
2.压缩(Compac):
再次扫描,并往一端滑动存活对象。

![img](https://img2018.cnblogs.com/blog/1484488/201809/1484488-20180917233910420-1672466377.jpg)

优缺点

优点：没有内存碎片,可以利用bump-the-pointer

缺点： 需要移动对象的成本



###### 分代收集算法

分代收集算法是目前大部分JVM的垃圾收集器采用的算法。它的核心思想是根据对象存活的生命周期将内存划分为若干个不同的区域。一般情况下将堆区划分为老年代（Tenured Generation）和新生代（Young Generation），在堆区之外还有一个代就是永久代（Permanet Generation）。老年代的特点是每次垃圾收集时只有少量对象需要被回收，而新生代的特点是每次垃圾回收时都有大量的对象需要被回收，那么就可以根据不同代的特点采取最适合的收集算法。
　　在新生代中，每次垃圾收集时都发现有大批对象死去，只有少量存活，那就选用复制算法，只需要付出少量存活对象的复制成本就可以完成收集。而老年代中因为对象存活率高、没有额外空间对它进行分配担保，就必须使用“标记-清理”或“标记-整理”算法来进行回收。方法区永久代，回收方法同老年代。

![img](https://img2018.cnblogs.com/blog/1484488/201809/1484488-20180917234347621-447754783.jpg)

引用文章（[JVM垃圾回收机制 - 、、、、、、、 - 博客园 (cnblogs.com)](https://www.cnblogs.com/yfzhou/p/9665708.html)



#### 17 Redis在项目中的使用场景

| 数据类型 | 使用场景                                                     |
| -------- | ------------------------------------------------------------ |
| String   | 比如：我想知道什么时候封锁一个IP（某一个IP地址在某一段时间内访问的特别频繁，那有可能这个IP可能存在风险，所以对它进行封锁），使用Incrby命令记录当前IP访问次数 |
| Hash     | 存储用户信息【id,name,age】 存储用户信息【id,name,age】存储方式：Hset(key,filed,value)hset(userKey,id,101)hset(userKey,name,"admin")hset(userKey,age,23)                                   这样存储的好处：当修改用户信息某一项属性值时，直接通过filed取值，并修改值-----修改案例------hget(userKey,id)hset(userKey,id,102)                                                                                                                                    为什么不使用String存储？获取方式：get(userKey)会把参数为userKey对应的用户信息字符串全部进行反序列号，而用户信息字符串包含了用户所有的信息如果我只修改用户的ID，那反序列化的其他信息其实是没有任何意义的序列化与反序列化是由IO进行操作的，使用String类型存储增加了IO的使用次数，降低了程序的性能对值为某类信息时不建议使用String类型存储 |
| List     | 存储方式：set(userKey,用户信息字符串)实现最新消息的排行，还可以利用List的push命令，将任务存在list集合中，同时使用另一个pop命令将任务从集合中取出。                                                                                                             Redis- -list 数据类型来模拟消息队列。电商中的秒杀就可以采用这种方式来完成一个秒杀活动 |
| set      | 特殊之处：可以自动排重。比如微博中将每个人的好友存在集合（Set）。中如果求两个人的共同好友的操作，我们只需要求交集即可。（交/并集命令） |
| Zset     | 有序集合(sorted set)，以某一个条件为权重，进行排序。比如：京东看商品详情时，都会有一个综合排名，还有可以安装价格、销量进行排名 |



#### 18 Elasticsearch和Solr的区别

背景：它们都是基于Luence搜索服务器基础之上开发的一款优秀高性能的企业级搜索服务器。也都是基于分词技术构建的倒排索引的方式进行查询

开发语言：java

诞生时间：

solr ：2004年

es ：2010年

#####  区别 

1、当实时建立索引的时候，solr会产生io阻塞，而es不会，es查询性能要高于solor。

2、在不断动态添加数据的时候，solr的检索效率会变的低下，而es则没有什么变化。

3、solr利用zookeeper进行分布式管理，而es自身带有分布式系统管理功能。solr一般都要部署到web服务器上，比如tomcat，启动tomcat时需要配置tomcat与solr的关联。solr本质是一个动态web项目。

4、solr支持更多的格式数据（xml、json、csv等）。而es仅支持json文件格式。

5、solr是传统搜索应用的有力解决方案，但是es更适用于新兴的实例搜索应用。

   单纯的对已有数据进行检索的时候，solr的效率更好，高于es

6、solr官网提供的功能更多，而es本身更注重与核心功能，高级功能多用第三方插件。



![](https://gitee.com/zssea/picture-bed/raw/master/img/image-20210605095653620.png)

SolrCloud集群图

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605100120.png)

Elasticsearch:集群图

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605100229.png)



#### 19 单点登录(SingleSignOn，*SSO*)实现过程

单点登录：一处登录多处使用！

前提：单点登录多使用在分布式系统中。

  

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605103304.png)

Demo：

参观动物园流程：

检票员=认证中心模块

1. 我直接带着大家进动物园，则会被检票员拦住【看我们是否有门票】，没有[售票处买票]

登录=买票

1. 我去买票【带着票，带着大家一起准备进入动物园】检票员check【有票】

Token=piao

1. 我们手中有票就可以任意观赏动物的每处景点。

京东：单点登录，是将token放入到cookie中的。

​    案例：将浏览器的cookie禁用，则在登录京东则失败！无论如何登录不了！

对于SSO单点登录的详细了解，请看[单点登录原理与简单实现 - ywlaker - 博客园 (cnblogs.com)](https://www.cnblogs.com/ywlaker/p/6113927.html)



#### 20 购物车实现过程

1. ###### 购物车跟用户的关系?

   1. 一个用户必须对应一个购物车【一个用户不管买多少商品，都会存在属于自己的购物车中。】
   2. 单点登录一定在购物车之前。

2. ###### 跟购物车有关的操作有哪些?

   1. 添加购物车
      1. 用户未登录状态
         1. 添加到什么地方?未登录将数据保存到什么地方?
            1. Redis? --- 京东
            2. ***\*Cookie\****? --- 自己开发项目的时候【如果浏览器禁用cookie】
      2. 用户登录状态
         1. Redis 缓存中 【读写速度快】
            1. Hash ：hset(key,field,value)
               1. Key:user:userId:cart
               2. Hset(key,skuId,value);
         2. 存在数据库中【oracle，mysql】
   2. 展示购物车
      1. 未登录状态展示
         1. 直接从cookie 中取得数据展示即可
      2. 登录状态
         1. 用户一旦登录：必须显示数据库【redis】+cookie 中的购物车的数据
            1. Cookie 中有三条记录
            2. Redis中有五条记录
            3. 真正展示的时候应该是八条记录



#### 21 消息队列在项目中的使用

存在背景：在分布式系统中是如何使用高并发的

　　由于在高并发的环境下，来不及同步处理用户发送的请求，则会导致请求发生阻塞。比如说，大量的insert、update之类的请求同时到达数据库MySQL，直接导致部署的行锁、表锁，甚至会导致请求堆积过多，从而触发too many connections 错误，使消息队列可以解决【异步通讯】

1、消息队列——异步

写数据库请求并发送消息队列，发送消息队列成功可返回给用户结果 

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605134321.png)

2、消息队列——并行

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605134548.png)

 3、消息队列——排队

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605134705.png)



###### 消息队列在电商中的使用场景

![](https://gitee.com/zssea/picture-bed/raw/master/img/20210605134820.png)



消息队列弊端：

　　消息的不确定性：延迟队列，轮询技术来解决该问题即可
