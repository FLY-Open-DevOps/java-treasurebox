## 一、引言

### 1.1 JavaScript 简介

- JavaScript—种解释性脚本语言，是一种动态类型、弱类型、基于原型继承的语言，内置支持类型。
- 它的解释器被称为JavaScript引擎，作为浏览器的一部分，广泛用于客户端的脚本语言，用来给HTML网页增加动态功能。

#### 1.1.1 前端三要素

- HTML (结构) :超文本标记语言(Hyper Text Markup Language)，决定网页的结构和内容
- CSss (表现) :层叠样式表(Cascading Style Sheets)，设定网页的表现样式
- JavaScript (行为) :是一种弱类型脚本语言，其源代码不需经过编译,而是由浏览器解释运行，
  用于控制网页的行为

### 1.2 JavaScript组成部分

- ECMAScript 语法
- 文档对象模型（DOM Document Object Model)
- 浏览器对象模型(BOM Browser Object Model)

### 1.3 JavaScript 发展史

- 它是由Netscape公司的Brendan Eich用10天设计出来一门脚本语言，JavaScript是甲骨文公司的注册商标。完整的JavaScript实现包 含三个部分：ECMAScript，文档对象模型，浏览器对象模型。
- Netscape在最初将其脚本语言命名为LiveScript，后来Netscape在与Sun合作之后将其改名为JavaScript。JavaScript最初受Java启发 而幵始设计的，目的之一就是“看上去像Java”，因此语法上有类似之处，一些名称和命名规范也借自Java。但JavaScript的主要设计原则源自Self和Scheme。JavaScript与Java名称上的近似，是当时Netscape为了营销考虑与Sun微系统达成协议的结果。为了取得技术优势，微软推出了JSaipt来迎战JavaScript的脚本语言。两者都属于ECMAScript的实现，为了互用性，ECAM (欧洲计算机 制造商协会）创建了ECMA-262标准（ECMAScript)。ECMAScript最新版本是2015发布的 ECMAScript6 (ES6)。
- 发展初期，JavaScript的标准并未确定， 同期有Netscape的JavaScript, 微软的JScript和CEnvi的ScriptEase
  三足鼎立。1997年， 在ECMA (欧洲计算机制造商协会)的协调下，由Netscape、 Sun、 微软、Borland组
  成的工作组确定统一标准: ECMA-262。

### 1.4JavaScript环境搭建

- 使用HBuilder进行页面开发
- 新建工程：文件-> 新建->Web项目

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804194734925.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- 创建一个< script >< /script >标签
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804194754643.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)
- 运行

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804194811281.jpg)

- 还可以使用外部引用的形式：把原本的JavaScript代码写到一个文件里，之后再引用过来

```html
<html>
	<body>
		<script src="js/my.js"></script>
	</body>
</html>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo1 js导入方式</title>
	</head>
	<body>
		<script type="text/javascript">
			document.write("hello world");
		</script>
		
		<script type="text/javascript" src="demo1.js"></script>
	</body>
</html>

demo1.js文件：

document.write("hello js!");
```

## 二、JavaScript基本语法

### 严格检查模式

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--
        前提：IDEA 需要设置支持ES6语法
        'use strict';
        严格检查模式，预防Javascript随意性导致一些问题
    -->
    <script>
        'use strict';
        //默认全局变量
        i = 1;
    </script>
</head>
<body>


</body>
</html>
```

### 2.1变量声明

- 在JavaScript中，任何变量都用var关键字来声明，var是variable的缩写。
- var是声明关键字，a是变量名，语句以分号结尾。

```js
var a;
```

- 这里值得注意的是，JavaScript中的关键字，不可以作为变量名。就像在Java中你不可以写（“ int int = 1;”） 一样。

JavaScript的部分关键字:

```js
abstracts else、 instanceof、 super、 boolean、 enum、 int、 switch、 break、 export、 interface、 synchronized、 byte、 extends、 let、 this、 case、 false、 long、 throw、 catch、 final、 native、 throws、 char、 finally、 new、 transient、 class、 float、 null、 true、 const、 for、 package、 try、 continue、 function、 private、 typeof、 debugger、 goto、 protected、 var、 default、 if、 public、 void、 delete、 implements、 return、 volatile、 do、 import、 short、 while、 double、 in、 static、 with。
```

### 2.2基本类型

- 变量的基本类型有Number、String、Boolean、Undefined(未赋值)、Null五种。
- 来声明一个数字Number类型，如下：

```js
var a = 1;
```

- 来声明一个字符串String类型。 你可以使用：

```js
var a = "r";
```

- 来声明一个布尔Boolean类型。 你可以使用：

```js
var a = false;
```

- 在Java中，当一个变量未被初始化的时候，Java中是null或者基本数据类型的默认值。
- 在JavaScript中，当一个变量未被初始化的时候，它的值为undefined。
- 下面是演示undefined的情况：（当一个引用不存在时，它为Null。这个现象我们在之后的引用类型时再详细探讨)

```js
var a;
document.write(a);   
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804194845798.jpg)

### 2.3引用类型

- 在Java中需要类定义，然后再实例对象:

```java
public class Student{
    public int id; 
    public String name;
    public int age;
}

public class Test{
	public static void main(String [] args){ 
        Student student=new Student(); 
        student.id=1; 
        student.name="张三"; 
        student.age=18;
    }
}  
```

- Js中对象，{…}表示一个对象，键值对描述属性xxx:xxx，多属性之间使用逗号隔开，最后一个属性不加逗号。
- 在JavaScript中对象可以直接写出来：

```js
var student={id:1, name:"张三", age:18};  /*引用类型*/
document.write(student.id); 
document.write(student.name);
document.write(student.age);
```

- 事实上，student被赋值为了一个JSON, JSON就是我们在Java基础阶段学过的，全称是JavaScript Object Notation,叫做JavaScript对象 标记，也就是说，在JavaScript中，JSON是用于标记一个对象的。

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo2 变量声明、引用类型</title>
	</head>
	<body>
		<script type="text/javascript">
			var num = 100; /*声明变量，该变量并没有值*/
			document.write(num);
			
			var student={id:1,name:"xiaosan",age:10};/* 引用类型*/
			document.write(student.id);
			document.write(student.name);
			document.write(student.age);
		</script>
	</body>
</html>
```

JavaScript中的所有

1、对象赋值

```javascript
person.name = 'xxxxxx'
"xxxxxx"
```

2、获取一个不存在的对象属性，不会报错！undefined

```javascript
person.haha
undefined
```

3、动态的删减属性，通过delete删除对象属性

```javascript
delete person.name
true
```

4、动态添加，直接给新的属性添加值即可

```javascript
person.haha = 'haha';
'haha'
```

5、判断属性是否在这个对象中！xxx in xxx

```javascript
'age' in person
true
//继承
'toString' in person
true
```

6、判断一个属性是否是这个对象自身拥有的：hasOwnProperty()

```javascript
person.hasOwnProperty('toString')
false
person.hasOwnProperty('age')
true
```

### 2.4数组类型

- 数组就是和我们之前理解的数组概念一致，而在JavaScript中成为Array类型。
- 我们说JSON可以标记一个对象，那么它同样可以标记一个数组，就是Java基础时我们学过的JSONArray。

```js
var a=[1,2,3.4];
```

- 上述代码，我们说a是一个数组，在a中角标为0的元素是1。可以说这很简单。
- 接下来我们来尝试把之前的JSON放入数组中：

```html
//我是注释 
/*我也是注释*/
var students =[
  {id：1, name: "张三",age: 18},  
  {id：2, name: "李四",age: 18},  
  {id：3, name: "王五", age: 19}  
];
document.write(students[0].id);
document.write(students[0].name);
document.write(students[0].age);
document.write("<br>"）;  //这个是html的换行的意思
document.write(students[1].id);
document.write(students[1].name);
document.write(students[1].age);
document.write( "<br>");
document.write(students[2].id);
document.write(students[2].name);
document.write(students[2].age);
```

- 访问students这个数组，第0个，第1个，第2个元素，都可以。

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo3 数组类型</title>
	</head>
	<body>
		<script type="text/javascript">
			/*js中数组中元素类型不需要统一*/
			var n=[100,true,"hello",{name:"lisi",age:20}]; /*数组类型中嵌套引用类型*/
			//alert(n.length);//弹出框
			//alert(n[1]);
			//alert(n[n.length-1]);
			alert(n[n.length-1].name);
		</script>
	</body>
</html>
```

代码:

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo3 数组类型</title>
	</head>
	<body>
		<script type="text/javascript">
			/*js中数组中元素类型不需要统一*/
			var n=[100,true,"hello",{name:"lisi",age:20}];
			//alert(n.length);//弹出框
			//alert(n[1]);
			//alert(n[n.length-1]);
			alert(n[n.length-1].name);
		</script>
	</body>
</html>
```

### 2.5运算符

#### 逻辑运算

| 名称 | 运算符 | 描述                                                       |
| ---- | ------ | ---------------------------------------------------------- |
| 与   | &&     | 要求表达式左右两边的表达式同为true,整体结果才为true        |
| 或   | II     | 要求表达式左右两边的表达式只要有一个为true,整体结果就为tme |
| 非   | !      | 将布尔值取反操作                                           |

```js
var a =false;
var b=true;

//非的逻辑 
//!a->true; 
//!b->false;

//与的逻辑 
//a&&a->false; 
//a&&b->false; 
//b&&a->false;
//b&&b->true; 

//或的逻辑 
//a||a->false; 
//a||b->true; 
//b||a->true; 
//b||b->true;
```

#### 关系运算

| 名称         | 运算符 |
| ------------ | ------ |
| 等于         | =      |
| 小于         | <      |
| 小于或等于   | <=     |
| 大于         | >      |
| 大于或等于   | >=     |
| 不等于       | 1=     |
| 值和类型相同 | ===    |

```js
var a=1;
var b=2;

//a==a->true
//a==b->false
//a<b->false
//a<=b->false
//a>b->true
//a>=b->true
//a!=b->true
//a===b->false

//这里三个等于和两个等于区别：
//前者相当于比较两个引用，后者相当于比较两个值。 
//当比较两个值得时候，不考虑数据类型。
//也就是说1=="1"是true的。
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo4 逻辑，关系运算符</title>
	</head>
	<body>
		<script type="text/javascript">
			//逻辑运算符
			var left_num=true;
			var right_num=true;
			document.write(left_num&&right_num);
			document.write("<hr>");
			left_num=false;
			right_num=false;
			document.write(left_num||right_num);
			document.write("<hr>");
			document.write(!left_num);
			document.write("<hr>");
			
			var num1=20,num2=30;
			document.write(num1>num2);document.write("<hr>");
			document.write(num1<num2);document.write("<hr>");
			document.write(num1>=num2);document.write("<hr>");
			document.write(num1<=num2);document.write("<hr>");
			document.write(num1==num2);document.write("<hr>");
			document.write(num1!=num2);document.write("<hr>");
			
			var n1="100",n2=100;
			document.write(n1==n2);//比较值,不考虑类型
			document.write("<hr>");
			document.write(n1===n2);//类型和值都要求相等
		</script>
	</body>
</html>
```

#### 单目运算：自增自减

| 名称 | 运算符 | 描述                       |
| ---- | ------ | -------------------------- |
| 自增 | ++     | 变量的值每次加1,再赋给变量 |
| 自减 | –      | 变量的值每次减1,再赋给变量 |

```js
var a=1;
a++;//自增 
a—;//自减 
++a;//自增 
--a;//自减 
//上述规则和Java—样。
```

#### 双目运算符

| 名称   | 运算符 |
| ------ | ------ |
| 加     | +      |
| 减     | -      |
| 乘     | *      |
| 除     | /      |
| 求余   | %      |
| 赋值   | =      |
| 加等   | +=     |
| 减等   | -=     |
| 除等   | /=     |
| 乘等   | *=     |
| 求余等 | %=     |

```js
var a=1;
var b=2;   
a+b    //相加   
a-b    //相减  
a*b    //相乘   
a/b    //相除    
a%b    //求余   
a=b    //赋值  
a+=b;  a= a+b; //相加后赋值 
a-=b;//相减后赋值 
a/=b;//相除后赋值 
a*=b;//相乘后赋值 
a%=b;//求余后赋值 
//上述规则和Java—样。
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo5 单目，双目运算符</title>
	</head>
	<body>
		<script type="text/javascript">
			var n=1;
			//++n;//n++;
			//document.write(n++);//先使用变量原来的值，再执行加1操作
			document.write(++n);//先执行加1操作，再在语句中使用
			document.write("<br>");
			document.write(n);
			document.write("<br>");
			var m=4;
			
			document.write(m+n);//+
			document.write("<br>");
			m+=n;//相当于m=m+n; 6
			document.write(m);//+=
			document.write("<br>");
			document.write(n/m);///
			document.write("<br>");
			m/=n;
			document.write(m);///=
			document.write("<br>");
			document.write(m%n);//%
		</script>
	</body>
</html>
```

#### 三目运算符：？:

```js
var kk = 100;
document.write(kk>100?true:false);
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo6 三目运算符</title>
	</head>
	<body>
		<script type="text/javascript">
			var kk="helloworld";
			var res=kk=="hello"; //用res接收 kk=="hello" 的结果
			document.write(res?true:false);
		</script>
	</body>
</html>
```

### 2.6条件分支结构

- if-else 分支

```js
var a=1 ;
var b=1 ; 
if(a==b){
	document.write("相等"）;
}else{
	document.write ("不相等");
}

//很明显，运行结果是相等
//这就是if-else的结构，和Java语言是一样的。
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo7 条件分支结构</title>
	</head>
	<body>
	<script type="text/javascript">
		var n1,n2;
		//if
		n1=10;
		n2=20;
		if(n1<n2){
			//alert("大于");
		}
		//if-else
		if(n1>n2){
			//alert("大于");
		}else{//else中不加条件表达式，它的默认条件就是if以外的其它情况
			//alert("小于等于");
		}
		//if-else嵌套
		if(n1>n2){
			//alert("大于");
		}else{//else中不加条件表达式，它的默认条件就是if以外的其它情况
			if(n1==n2){
				alert("等于");
			}else{
				alert("小于");
			}
		}
	</script>
	</body>
</html>
```

- switch分支

```js
var a=2; 
switch(a){
    case1:
    	document.write("值为1"); 
        break; 
    case 2:
    	document.write ("值为2");
        break;
    case 3:
    	document.write("值为3"); 
        break; 
    default:
    document.write('值不是3也不是2也不是1");
}
```

代码:

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo8 switch分支</title>
	</head>
	<body>
		<script type="text/javascript">
			//每个case的break不省略
			//default的执行顺序：不受定义顺序影响(与break有关) ；定义顺序：可以任意定义；
			var n=11;
			switch(n){
				case 1:
					document.write(n);
					break;
				case 2:
					document.write(n);
					break;
				case 3:
					document.write(n);
					break;
				default:
					document.write(n);
			}
		</script>
	</body>
</html>
```

### 2.7循环结构

- for循环

```js
var a=0;
for(var i=1;i<=100;i++){ 
    a+=i;
}
document.write(a);

//上述代码是对1〜100求和。
```

- while循环

```js
var a=0; 
var i=1; 
while(i<=100){ 
    a+=i;
    i++；
}
document.write(a);

//上述代码是对1〜100求和。
```

- do-while 循环

```js
var a=0; 
var i=1; 
do{
    a+=i;
    i++;
}while(i<=100); 
document.write(a);

//上述代码是对1〜100求和。
```

- break 与continue 关键字
  - break用于结束循环 (结束整个循环)
  - continue用于结束本次循环

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo9 循环</title>
	</head>
	<body>
		<script type="text/javascript">
			//for
			for(var k=1;k<=10;k+=2){
				document.write(k+"<br>");
			}
			document.write("<hr>");
			//while
			k=1;
			while(k<=10){
				document.write(k+"<br>");
				k++;
			}
			document.write("<hr>");
			//do-while
			k=100;
			do{
				document.write(k+"<br>");
				k--;
			}while(k>=90);
			document.write("<hr>");
			//for-while相互转换
			//初始值
			//条件
			//循环体
			//迭代变量
			k=1;
			while(k<=10){
				document.write(k+"<br>");
				k+=2;
			}
			document.write("<hr>");
			k=101;
			while(k>=91){
				k--;
				if(k==95)
					break;
					//continue;
				document.write(k+"<br>");
				
			}
		</script>
	</body>
</html>
```

### 2.8函数【重点】

- 函数定义：用function关键字来声明，后面是方法名字，参数列表里不写var。整个方法不写返回值类型。

```js
function functionName(parameters){
//执行的代码
}
```

- 方法的定义与调用举例:

```js
function add(a,b){
 return a+b;
}
var c=1; 
var d=2; 
var e=add(1,2); 
document.write(e);

//上述代码运行结果是3
//这里定义了一个add方法，参数是两个，与Java不同，参数的数据类型并没有。
//因为就算是写，全都是var，为了保证语法的简洁性，全写var索性就设计成全都不用写了。
//返回值也是同样的道理，区别是，如果你写了返回值，那么有返回值，如果没写return，就没有返回值。
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo10 函数</title>
	</head>
	<body>
		<script type="text/javascript">
			//定义函数
			function fuc(){
				document.write("hello");
				document.write("<br>");
				document.write("world");
			}
			//调用函数
			//1.直接调用
			fuc();
			
			//2.结合事件调用函数?
			
			//3.定时器调用函数?
			//定义函数的不同形式：参数/返回值
			//入参数和返回值
			function fuc2(num1,num2){
				//类型
				return num1-num2;
			}
			var res=fuc2(10,"hello");
			alert(res);
		</script>
	</body>
</html>
```

### 变量的作用域

在Javascript中，var定义变量实际是有作用域的。

假设在函数体中声明，则在函数体外不可以使用~（如果想要实现，则需要`闭包`）

```javascript
function abc() {
    var x= 1;
    x = x + 1;
}
x = x + 2 ;//Uncaught ReferenceError: x is not defined
```

如果两个函数使用相同变量名，只要在函数内部，就不冲突

```javascript
function abc1() {
    var x= 1;
    x = x + 1;
}

function abc2() {
    var x= 2;
    x = x + 1;
}
```

内部函数可以询问外部函数的成员，反之则不行

```javascript
    function abc3() {
        var x= 1;
        //内部函数可以询问外部函数的成员，反之则不行
        function abc3() {
            var y = x + 1;//2
        }
        var z = y + 1;//Uncaught ReferenceError: x is not defined
    }
```

假设：内部函数变量与外部函数变量重名

```javascript
function abc5() {
    var x = 1;
    function abc6() {
        var x = A;
        console.log('inner' + x);//outer1
    }
    console.log('outer'+x);//innerA
    abc6();
}
```

假设在Javscript中函数查找变量是从自身函数开始，由 内 向 外 查找。假设外部存在这个同名的函数变量，则内部函数会屏蔽外部的变量。

> 提升变量作用域

```javascript
function abc8() {
    var x = 'x' + y;
    console.log(x);
    var y = 'y';
}
```

结果：undefined

说明：js执行引擎，自动提升了y的声明，但是不会提升y的赋值。

```javascript
function abc8() {
    var y;
    var x = 'x' + y;
    console.log(x);
    y = 'y';
}
```

这是在Javascript简历之初就存在的特性。养成规范：所有的变量定义都放在函数头部，不要乱放，便于代码维护；

```javascript
function abc(){
    var x = 1,
        y = x + 1,
        z,i,a;//undefined
}
```

> 全局函数

```javascript
//全局变量
x = 1;
function f(){
    console.log(x);
}
f();
console.log(x);
```

全局对象：window

```javascript
var x = 'xxx';
alert(x);
alert(windws.x);
```

alert()这个函数本身也是一个`window`变量：

```javascript
var x = 'xxx';
window.alert(x);
var old_alert = window.alert;

// old_alert(x);

window.alert = function () {
};

//发现alert()失效了
window.alert(123);

//恢复
window.alert = old_alert;
window.alert(456);
```

Javascript实际上只有一个全局作用域，任何变量（函数也可以视为变量），假设没有在函数作用范围，就会向外查找，如果在全局作用域都没有找到，报错`RefrenceError`

> 规范

由于我们所有的全局变量都会绑定到我们的window上。如果不同的js文件，使用了相同的全局变量，冲突->如何能够减少冲突？

```javascript
//唯一全局变量
var LiusxApp = {};

//定义全局变量
LiusxApp.name = 'liusuixing';
LiusxApp.add = function (a,b) {
    return a + b;
}
```

把自己的代码全部放入自己定义的唯一空间名字中，降低全局命名冲突的问题。（jQuery）

> 局部作用域：let

```javascript
function aaa(){
    for(var i = 0; i < 100 ;i++){
        console.log(i);
    }
    console.log(i+1);//问题，i 出了这个作用域还可以使用
}
```

ES6：`let`关键字，解决局部作用域冲突问题！

```javascript
function aaa(){
    for(let i = 0; i < 100 ;i++){
        console.log(i);
    }
    console.log(i+1);//问题，i 出了这个作用域还可以使用
}
```

建议大家都用`let`取定义局部作用域的变量

> 常量：const

**ES6之前，怎么定义常量：只有用全部大写字母命名的变量就是常量：建议不要修改这样的值。**

在ES6引入了常量关键字：`const`

```javascript
const PI = '3.14';//只读效果
console.log(PI);
PI = '123';//Uncaught TypeError: Assignment to constant variable.
console.log(PI);
```

### 方法

> 定义方法（方法就是把函数放在对象里面，对象中只有2个东西：属性和方法）

```javascript
var liusx = {
    name: 'Sam',
    bitrh: 2020,
    //方法
    age: function () {
        var now = new Date().getFullYear();
        return now-this.bitrh;
    }
}
//属性
liusx.name;
//方法，一定要带()
liusx.age();
12345678910111213
function getAge () {
    var now = new Date().getFullYear();
    return now-this.bitrh;
}

var liusx = {
    name: 'Sam',
    bitrh: 2020,
    //方法
    age: getAge
}

//liusx.age()    =>ok
//getAge()     =>NaN
```

 **this是无法指向的，是默认指向调用它的那个对象**

> apply：在Javascript中可以控制this的指向（所有对象都由apply）

```javascript
var liusx = {
    name: 'Sam',
    bitrh: 2020,
    //方法
    age: getAge
}
//liusx.age()    =>ok
//getAge()     =>NaN
getAge.apply(liusx,[])//this，指向了liusx，参数为空
```

### 2.9常见弹窗函数

- alert弹框：这是一个只能点击确定按纽的弹窗
- alert方法没有返回值，也就是说如果用一个变量去接受返回值，将会得到undefined。无论你点击“确定”还是右上角的那个关闭。

```js
alert("你好");
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804194938299.jpg)

- confirm弹框：这是一个你可以点击确定或者取消的弹窗
- confirm方法与alert不同，他的返回值是boolean,当你点击“确定”时，返回true,无论你点击“取消”还是右上角的那个‘X“关闭， 都返回false。

```js
confirm("你好");
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804194959503.jpg)

- prompt弹框：这是一个你可以输入文本内容的弹窗
- 第一个参数是提示信息，第二个参数是用户输入的默认值。

•当你点击确定的时候，返回用户输入的内容。当你点击取消或者关闭的时候，返回null。

```js
prompt("你喜欢学习吗","喜欢");
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804195014425.jpg)

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo11 弹窗函数</title>
	</head>
	<body>
		<script type="text/javascript">
			//alert

			alert("加入购物车成功！");
			
			//confirm
			//确定/取消
			var res=confirm("确定删除吗？");//返回一个布尔值
			if(res){
				alert("执行删除");
			}else{
				alert("撤消删除");
			}
			
			//prompt
			var num=prompt("请输入一个数");
			if(num>0){
				alert("正数");
			}else{
				aler("非正数(0/负数)");
			}
		</script>
	</body>
</html>
```

### 2.10事件

是和html标签元素一起结合使用的

| 事件名称    | 描述                         |
| ----------- | ---------------------------- |
| onchange    | HTML元素内容改变             |
| onclick     | 用户点击HTML元素             |
| onmouseover | 用户将鼠标移入一个HTML元素中 |
| onmousemove | 用户在一个HTML元素上移动鼠标 |
| onmouseout  | 用户从一个HTML元素上移开鼠标 |
| onkeyup     | 键盘                         |
| onkeydown   | 用户按下键盘按键             |
| onload      | 浏览器已完成页面的加载       |
| onsubmit    | 表单提交                     |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo12 事件</title>
		<style type="text/css">
			#dd{width:100px; height:50px; background-color:#f00;}
		</style>
	</head>
	<body>
		<script type="text/javascript">
			function ff(){
				//document.write();
				//alert();
				//prompt();
				confirm("确定删除？");
			}
			
			function ff2(){
				alert('mouse is in....');
			}
		</script>
		
		<div>
			<!--事件关联JS代码-->
			<button onclick="alert('hello');">普通按钮</button>
			<!--事件关联函数-->
			<button onclick="ff()">触发函数</button>
		</div>
		<div>
			<div id="dd" onmouseover="ff2()">	
			</div>
		</div>
	</body>
</html>
```

### 2.11正则表达式

- 正则表达式是描述字符模式的对象。
- 正则表达式用于对字符串模式匹配及检索替换，是对字符串执行模式匹配的强大工具。
- 语法：
  - var patt=new RegExp(pattem,modifiers);
  - var patt=/pattern/modifiers;

```js
var re = new RegExp("\\w+"); 
var re = /\w+/;
```

- 修饰符：用于执行区分大小写和全局匹配:

| 修饰符 | 描述                                                     |
| ------ | -------------------------------------------------------- |
| i      | 执行对大小写不敏感的匹配。                               |
| g      | 执行全局匹配（查找所有匹配而非在找到第一个匹配后停止）。 |
| m      | 执行多行匹配。                                           |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo13 正则表达式</title>
	</head>
	<body>
		<script type="text/javascript">
            //str 中是否包含字符a,后面的mg是修饰符
			var reg=/a/mg;
			var str="Ahel\nalAoaa";
			//alert(reg.test(str));
			alert(str.match(reg));
		</script>
	</body>
</html>
```

- 方括号：用于查找某个范围内的字符

| 表达式             | 描述                           |
| ------------------ | ------------------------------ |
| [abc]              | 查找方括号之间的任何字符。     |
| [^abc]             | 查找任何不在方括号之间的字符。 |
| [0-9]              | 查找任何从0至9的数字。         |
| [a-z]              | 查找任何从小写a到小写z的字符。 |
| [A-Z]              | 查找任何从大写A到大写Z的字符。 |
| [A-z]              | 查找任何从大写A到小写z的字符。 |
| [adgk]             | 查找给定集合内的任何字符。     |
| [^adgk]            | 查找给定集合外的任何字符。     |
| (red\|blue\|green) | 查找任何指定的选项。           |

- 元字符（Metacharacter):是拥有特殊含义的字符:

| 元字符 | 描述                                    |
| ------ | --------------------------------------- |
| •      | 查找单个字符，除了换行和行结束符。      |
| \w     | 查找单词字符。                          |
| \W     | 查找非单词字符。                        |
| \d     | 查找数字。                              |
| \D     | 查找非数字字符。                        |
| \s     | 查找空白字符。                          |
| \S     | 查找非空白字符。                        |
| \b     | 匹配单词边界。                          |
| \B     | 匹配非单词边界。                        |
| \0     | 查找NULL字符。                          |
| \n     | 查找换行符。                            |
| \f     | 查找换页符。                            |
| \r     | 查找回车符。                            |
| \t     | 查找制表符。                            |
| \v     | 查找垂直制表符。                        |
| \xxx   | 查找以八进制数)cxx规定的字符。          |
| \xdd   | 查找以十六进制数dd规定的字符。          |
| \uxxxx | 查找以十六进制数xxxx规定的Unicode字符。 |

- 量词：用于表示重复次数的含义

| 量词   | 描述                                                         |
| ------ | ------------------------------------------------------------ |
| n+     | 匹配任何包含至少一个n的字符串。例如，/a+/匹配"candy’中的"a", "caaaaaaandyn中所有的a”。 |
| n*     | 匹配任何包含零个或多个n的字符串。例如，/bo*/匹配“A ghostbooooed"中的”boooo”, “Abirdwarb“中的"b”,但是不匹配"A goat grunted"。 |
| n?     | 匹配任何包含零个或一个n的字符串。例如，/e?le?/匹配"angel”中的"el”，"angle"中的”le”。 |
| n{X}   | 匹配包含X个n的序列的字符串。例如，/a{2}/不匹配"candy,“中的"a”，但是匹配"caandy，”中的两个"a"，且匹配”caaandy"1 中的前两个"a"。 |
| n{X,}  | X是一个正整数。前面的模式n连续出现至少X次时匹配。例如，/a{2,}/不匹配"candy”中的”a”，但是匹配”caandy"和 “caaaaaaandy"中所有的"a”。 |
| n{X,Y} | X和Y为正整数。前面的模式n连续出现至少X次，至多Y次时匹配。例如，/a{l,3}/不匹配"cndy”，匹配"candy,”中的 “a”, “caandy"中的两个"a”，匹配ncaaaaaaandyn中的前面三个”a“。注意，当匹配”caaaaaaandy“时，即使原始字符串拥有更多的"a", 匹配项也是"aaa"。 |
| n{X}   | 前面的模式n连续出现X次时匹配                                 |
| n$     | 匹配任何结尾为n的字符串。                                    |
| ^n     | 匹配任何幵头为n的字符串。                                    |
| ?=n    | 匹配任何其后紧接指定字符串n的字符串。                        |
| ?!n    | 匹配任何其后没有紧接指定字符串n的字符串。                    |

- RegExp对象方法

| 方法    | 描述                                               |
| ------- | -------------------------------------------------- |
| compile | 编译正则表达式。                                   |
| exec    | 检索字符串中指定的值。返回找到的值，并确定其位置。 |
| test    | 检索字符串中指定的值。返回true或false。            |

- 支持正则表达式的String对象的方法

| 方法    | 描述                             |
| ------- | -------------------------------- |
| search  | 检索与正则表达式相匹配的值。     |
| match   | 找到一个或多个正则表达式的匹配。 |
| replace | 替换与正则表达式匹配的子串。     |
| split   | 把字符串分割为字符串数组。       |

- 正则表达式的使用
  - test方法：搜索字符串指定的值，根据结果并返回真或假
  - exec()方法：检索字符串中的指定值。返回值是被找到的值。如果没有发现匹配，则返回null。

```js
var patt1=new RegExp("e");  
document.write(patt1.test("The  best things in life are free"）;
                          
var patt2=new RegExp("e"); 
document.write(patt2.exec("The best things in life are  free"));  
```

### 2.12Map 和 Set

> ES6的新特性

- Map

```javascript
//ES6
//学成的成绩，学生的名字
var names = ['tom','jack','sam'];
var scores = [100,90,80];

var map = new Map([['tom',100],['jack',90],['sam',80]])
var name = map.get('tom');
console.log(name);//
map.set('admin',123456);//新增元素
console.log(map);//Map(4){"tom" => 100, "jack" => 90, "sam" => 80, "admin" => 123456}
map.delete('tom');//删除元素
console.log(map);//Map(3){"jack" => 90, "sam" => 80, "admin" => 123456}
```

- Set：无需不重复的集合

```javascript
//set可以去重
var set = new Set([3,1,1,1,1,2]);
set.add(4);//添加
console.log(set);//Set(4) {3, 1, 2, 4}
set.delete(1);//删除
console.log(set);//Set(3) {3, 2, 4}
console.log(set.has(3));//是否包含某个元素
```

### 2.13.Iterator

- 遍历数组

```javascript
//通过for of / for in 下标
var arr = [3,4,5];
for(var x of arr){
    console.log(x)
}
//存在一个bug
arr.name = 123;
for(var x of arr){
    console.log(x)
}
输出:
1
2
3
name
```

- 遍历map

```javascript
 //遍历map
for(let x of map){
    console.log(x);
}
```

- 遍历set

```javascript
//遍历set
for(let x of set){
    console.log(x);
}
```

## 三 、内部对象标准对象

标准对象

```javascript
typeof 123
"number"
typeof '123'
"string"
typeof true
"boolean"
typeof NaN
"number"
typeof []
"object"
typeof {}
"object"
typeof Math.abs
"function"
typeof undefined
"undefined"
```

### 3.1.Date

**基本使用**

```javascript
var now = new Date();//
now.getFullYear();//年
now.getMonth();//月
now.getDate();//日
now.getDay();//星期几
now.getHours();//时
now.getMinutes();//分
now.getSeconds();//秒

now.getTime();//时间戳 全世界统一 1970 1.1 0:00:00

console.log(new Date(1589096655409));//时间戳转时间
```

**转换**

```javascript
//本地时间
now.toLocaleString();
"2020/5/10 下午3:44:15"
//东八区
now.toGMTString();
"Sun, 10 May 2020 07:44:15 GMT"
```

### 3.2.JSON

> JSON是什么

早期，所有数据传输习惯使用XML

- [JSON](https://baike.baidu.com/item/JSON)([JavaScript](https://baike.baidu.com/item/JavaScript) Object Notation, JS 对象简谱) 是一种轻量级的数据交换格式
- 简洁和清晰的**层次结构**使得 JSON 成为理想的数据交换语言。
- 易于人阅读和编写，同时也易于机器解析和生成，并有效地提升网络传输效率。

在Javascript一切结尾对象，任何js 支持的类型都可以用JSON来表示：number，string

格式：

- 对象都用 {}
- 数组都用 []
- 所有的键值对 都是用 key:value

JSON 字符串和 JS 对象的转换

```javascript
var user = {
    name: 'liusx',
    age: 3,
    sex: '男'
}
//对象转换json字符串:{"name":"liusx","age":3,"sex":"男"}
var jsonuUer = JSON.stringify(user);
console.log(jsonuUer);

//json  字符串转换为对象，参数为json字符串
var obj = JSON.parse('{"name":"liusx","age":3,"sex":"男"}');
console.log(obj);
```

JSON 和 JS 对象的区别：

```javascript
var obj = {"name":"liusx","age":3,"sex":"男"};
var json = '{"name":"liusx","age":3,"sex":"男"}';
```

### 3.3.Ajax

- 原生的Javascript写法，xhr异步请求
- jQuery封装好的方法
- axios请求

## 四、面向对象编程原型

Javascript、Java、C#…面向对象：Javascript有些区别

- 类：模板
- 对象：具体实例

在Javascript这个需要大家换一下思维

原型：

```javascript
//面向对象
var Student = {
    name: 'liusx',
    age: 3,
    run:function () {
        console.log(this.name+"run.....")
    }
}

var xiaoming = {
    name: '小明'
}
//小明的原型 是Student
xiaoming.__proto__ = Student;
xiaoming.run();

var Bird = {
    fly: function () {
        console.log(this.name + "fly......")
    }
}
//小明的原型 是Bird
xiaoming.__proto__ = Bird;


//class继承
function Student(name) {
    this.name = name;
}
//给student新增一个方法
Student.prototype.hello = function () {
    alert('hello');
}
```

> class继承：`class`关键字，ES6引入

1、定义一个类，属性，方法

```javascript
//ES6 之后======
class Student{
    constructor(name){
        this.name = name;
    }

    hello(){
        alert('hello');
    }
}

var xiaoming = new Student('小明');
var xiaoming = new Student('小红');
xiaoming.hello();
```

2、继承

```javascript
//ES6 之后======
class Student{
    constructor(name){
        this.name = name;
    }

    hello(){
        alert('hello');
    }
}

class XiaoStudent extends Student {
    constructor(name,grade){
        super(name);
        this.grade = grade;
    }
    myGrade(){
        alert('我是一名小学生');
    }
}

var xiaoming = new Student('小明');
xiaoming.hello();
var xiaohong = new XiaoStudent('小红',1);
xiaohong.myGrade();
```

> 原型链

```
__proto__
```

## 五、JavaScript的DOM对象【重点】

### 5.1概述

- 通过HTML DOM,可访问JavaScript HTML文档的所有元素。
- 当网页被加载时，浏览器会创建页面的文档对象模型DOM（Document Object Model)。
- HTMLDOM模型被构造为对象的树：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804195055801.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- 通过可编程的对象模型(DOM)，JavaScript获得了足够的能力来创建动态的HTML。
  - JavaScript能够改变页面中的所有HTML元素。
  - JavaScript能够改变页面中的所有HTML属性。
  - JavaScript能够改变页面中的所有CSS样式。
  - JavaScript能够对页面中的所有事件做出反应。

### 5.2查找HTML元素

- 通常，通过JavaScript，您需要操作HTML元素。

- 为了做到这件事情，您必须首先找到该元素。有三种方法来做这件事：

  - 通过id找到HTML元素

    - 在DOM中查找HTML元素的最简单的方法，是通过使用元素的id。
    - 方法：document.getElementByld(“id属性值”)；
    - 如果找到该元素，则该方法将以对象（在x中）的形式返回该元素。
    - 如果未找到该元素，则x将包含null。

  - 通过标签名找到HTML元素方法：

    - geElementsByTagName(“合法的元素名”)；

  - 通过类名找到HTML元素

    - 方法：geElementsByClassName(“class属性的值”)；

      

```html
<div id="father">  
    <h1>标题一</h1>  
    <p id="p1">p1</p>
    <p class="p2">p2</p>
</div>  
<script>    
    var h1 = document.getElementsByTagName("h1");     
    var p1 = document.getElementById("p1")     
    var p2 = document.getElementsByClassName("p2");     
    var father = document.getElementById("father");
//获取父节点下所有的子节点      
var childrens = father.children;    
//father.firstChild;    
//father.lastChild; 
</script>
```


### 5.3 改变HTML

- 改变HTML输出流：document.write()可用于直接向HTML输出流写内容

```html
<!DOCTYPE html> 
<html>
    <body>
        <script>
            document.write("Hello world,I'm JavaScript");
        </script>
    </body>
</html>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080419511214.jpg)

- 改变HTML内容：使用innerHTML属性

```html
<html>
    <body>
        <p id="p1"> Hello World!</p>
        <script>
            document.getElementById("p1").innerHTML="abcd";      
        </script>
    </body>
</html>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804195127792.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- 改变HTML属性：document.getElementByld(id).attribute=新属性值
- 将attribute替换成真实的属性名

```html
<html>
    <body>
        <img id="image" src="1.gif"/>
        <script>
            document.getElementById("image").src="2.gif";
        </script>
    </body>
</html>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo14 改变HTML</title>
		<style type="text/css">
			#dd{width: 200px; height:200px; background-color: yellow;}
		</style>
	</head>
	<body>
		<div id="dd"></div>
		
		<script type="text/javascript">
			//获得对象
			var dd=document.getElementById("dd");
			//改变HTML内容
			dd.innerHTML="内容已被改变 已被改变 已被改变";
            dd.innerText='456';//修改文本的值
			//改变HTML属性
			dd.align="center";
		</script>
	</body>
</html>
```

### 5.4 CSS变化

- 对象.style.property=新样式
- 将property替 换成真实的css属性名

```html
<!DOCTYPE html>
<html>
    <head>
    	<meta charset="utf-8">
    </head>
    <body>
        <p id="p1">He11o World!</p>
        <p 1d="p2">Hello World!</p>
        <script>
            document.getElementById("p2").style.color=" blue";
            document.getElementById("p2").style.fontFamily="Arial";
            document.getElementById("p2").style.fontSize="larger";
        </script>
        <p>以上段落通过脚本修改。</p>
    </body>
</html>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo15 css变化 </title>
		<style type="text/css">
			#dd{width:200px; height: 200px; background-color: yellow;}
		</style>
	</head>
	<body>
		<div id="dd" class="dd"></div>
		<script type="text/javascript">
			//获得对象
			var dds = document.getElementsByClassName("dd");//数组（类选择器名字）
			//修改CSS属性
			dds[0].style.width="400px";
			dds[0].style.backgroundColor="blue";
			dds[0].style.height=250+"px";
		</script>
	</body>
</html>
```

### 5.5 DOM事件

- HTML DOM允许我们通过触发事件来执行代码
- 比如以下事件:
  - 元素被点击。
  - 页面加载完成。
  - 输入框被修改。
- 本例改变了id=“id1” 的HTML元素的样式，当用户点击按钮时

```html
<!DOCTYPE html>
<html>
	<body>
        <h1 id="id1" >myH1</h1>
        <button type= "button" 					  											οnclick="document.getElementById('id1').style.color='red'">button</button>
    </body>
</htm1>
```

点击前

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804195144420.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

点击后

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804195157890.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- HTML DOM使JavaScript有能力对HTML事件做出反应。
- 在本例中，当用户在h1元素上点击时，会改变其内容

```html
<!D0CTYPE html>
<html>
    <body>
    	<h1 onclick="this.innerHTML='Ooops'">点击文本！</h1> 
    </body>
</html>
```

- 本例从事件处理器调用一个函数：

```html
<!D0CTYPE html>
<html>
    <head>
   	 	<script>
            function changetext(id){
                id.innerHTML="Ooops!";
            }
        </script>
    </head>
    <body>
    	<h1 onclick="changetext(this)">点击文本！</h1>
    </body>
</html>
```

- 如需向HTML元素分配事件，您可以使用事件属性。

```html
<button onclick="displayDate()">点这里</button>
```

- HTML DOM允许您使用JavaScript来向HTML元素分配事件

```html
<script>
	document.getElementById("MmyBtn").onclick=function(){(displayDate()}; 
</script>
```

代码:

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo16 DOM事件</title>
		<style type="text/css">
			#focus{width: 200px; height: 200px; background-color: red;}
		</style>
	</head>
	<body>
		<script type="text/javascript">
			function ff(){
				//通过该函数设置div层的属性
				//1.获得层
				var fc=document.getElementById("focus");
				//2.设置属性
				var x,y,z;
				x=Math.ceil((Math.random()*255));//随机颜色
				y=Math.ceil((Math.random()*255));//
				z=Math.ceil((Math.random()*255));//
				fc.style.backgroundColor="rgb("+x+","+y+","+z+")";//rgb(100,123,255)
			}
		</script>
		
		<div id="focus"></div>
		<div>
			<button onclick="ff()">change focus</button>
		</div>
	</body>
</html>
```

### 5.6 EventListener

- addEventListener()方法
- 在用户点击按钮时触发监听事件

```js
document.getElementById("myBtn").addEventListener( "click", displayDate); //(事件，函数)
1
```

- addEventListener()方法用于向指定元素添加事件句柄。
- addEventListener()方法添加的事件句柄不会覆盖已存在的事件句柄。
- 你可以向一个元素添加多个事件句柄。
- 你可以向同个元素添加多个同类型的事件句柄，如：两个"click”事件。
- 你可以向任何DOM对象添加事件监听，不仅仅是HTML元素。如：window对象。
- addEventListenerO方法可以更简单的控制事件（冒泡与捕获）。
- 当你使用addEventListener()方法时，JavaScript从HTML标记中分离开来，可读性更强，在没有控制HTML标记时也可以添加事件 监听。
- 你可以使用removeEventListener（）方法来移除事件的监听。

------

#### 添加事件的参数：

```js
 element.addEventListener(event,function,useCapture);                            
```

添加事件的参数（三个）：

| 参数名     | 描述                                       |
| ---------- | ------------------------------------------ |
| event      | 事件的类型（如"click"或"mousedown")        |
| function   | 事件触发后调用的函数                       |
| useCapture | 用于描述事件是冒泡还是捕获。该参数是可选的 |

- 当用户点击元素时弹出"Hello World!":

```js
element.addEventListener("click", myFunction);//定义事件

function myFunction() {
	alert ("Hello World!");
}
```

- addEventListener()方法允许向同个元素添加多个事件，且不会覆盖已存在的事件:

```js
element.addEventListener("click", myFunction); 
element.addEventListener("click", mySecondFunction);
```

- 你可以向同个元素添加不同类型的事件:

```js
element.addEventListener("mouseover", myFunction);
element.addEventListener("click", mySecondFunction); 
element.addEventListener("mouseout"， myThirdFunction);
```

- addEventListener（）方法允许你在HTML DOM对象添加事件监听，HTML DOM对象如：HTML元素，HTML文裆，window对象。或者 其他支出的事件对象如:xmlHttpRequest对象。
- 当用户重置窗口大小时添加事件监听：

```js
window.addEventListener("resize", function(){
	document.getElementById("demo").innerHTML = sometext;
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo17 eventlistener事件监听器1 </title>
		<style type="text/css">
			#d1{ width:200px; height: 100px; background-color: #ccf;}
			#d2{ width:200px; height: 100px; background-color: #Fcf;}
		</style>
	</head>
	<body>
		<div id="d1"></div>
		<div id="d2"></div>
		<script type="text/javascript">
			//添加事件监听
			var d1=document.getElementById("d1");//先获取元素
			d1.addEventListener("mouseover",ff);//为元素添加事件监听器
			function ff(){
				alert(this+"----事件监听机制^_^");
			}
			//同一元素绑定多个相同事件
			d1.addEventListener("mouseover",function (){
				alert("事件监听机制^_^^_^");
			})
			//同一元素绑定多个不同事件
			d1.addEventListener("mousemove",function(){
				alert("事件监听机制^_^move");
			})
			var d2=document.getElementById("d2");
			d2.addEventListener("mouseover",ff);	
		</script>	
	</body>
</html>
```

------

#### 事件传递的两种方式

- 事件传递有两种方式：冒泡与捕获。
- 事件传递定义了元素事件触发的顺序。如果你将 P 元素插入到 div 元素中，用户点击p元素，哪个元素的"click”事件先被触发呢 ？
  - 在”冒泡“中，内部元素的事件会先被触发，然后再触发外部元素，即：P元素的点击事件先触发，然后会触发div元素的点击事件。
  - 在”捕获“中，外部元素的事件会先被触发，然后才会触发内部元素的事件，即: div元素的点击事件先触发，然后再触发p 元素的点击事件。
- addEventListener（）方法可以指定"useCapture"参数来设置传递类型：

```js
addEventListener(event, function, useCapture);
```

- 默认值为false,即冒泡传递，当值为true时,事件使用捕获传递。

```js
document.getElementById("myDiv").addEventListener("click", myFunction, true);
```

- removeEventListener()方法移除由addEventListener()方法添加的事件句柄：

```js
element.removeEventListener("mousemove", myFunction);
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo18 eventlistener事件监听器2</title>
		<style type="text/css">
			#div{width:200px; height: 200px; background-color: #ccc;}
			#p{width:100px; height: 100px; background-color: #ff0;}
		</style>
	</head>
	<body>
		<div id="div">
			<p id="p"></p>
		</div>
		<script type="text/javascript">
			//获得对象
			var div=document.getElementById("div");
			var p=document.getElementById("p");
			//添加监听
			div.addEventListener("mouseover",function(){
				alert("div event listener");
			},true);
			p.addEventListener("mouseover",function(){
				alert("p event listener");
			});
			/*注意，在添加第三个元素（传递方式）时，要设置为捕获（true）,要加在外层元素上或者全都加
			 只加在内层元素上是不起作用的*/
		</script>
	</body>
</html>
```

### 5.7操作元素

- 如需向HTML DOM添加新元素，您必须首先创建该元素（元素节点），然后向一个已存在的元素追加该元素。
  - 创建元素：document.createElement() 。
  - 追加元素：appendChild()

```html
<div id="div1" >
	<p id="p1">这是一个段落。</p> 
    <p id="p2">这是另一个段落。</p> 
</div>

<script>
    var para=document.createElement("p");
    var node=document.createTextNode("这是一个新段落");
    para.appendChild(node);
    
    var element=document.getElementById("divl"); 
    element.appendChild(para);
</script>
```

- 删除已有的HTML元素
- 使用方法：removeChild()

```html
<div id="div1" >
	<p id="p1">这是一个段落。</p> 
    <p id="p2">这是另一个段落。</p> 
</div>

<script>
    var parent = document.getElementById("div1");
    var child = document.getElementById("p1");
	parent.removeChild(child);
</script>
```

代码：

```html
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>demo19 DOM操作元素</title>
	</head>

	<body>
		<table id="tab" border="1" cellspacing="0" width="400">
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>内容</th>
				<th>日期</th>
				<th>备注</th>
			</tr>
		</table>
		<button id="btn">留言</button>
		<button id="btn2">删除留言</button>
		<script type="text/javascript">
			var btn = document.getElementById("btn");
			//获得对象
			var tab = document.getElementById("tab");
			btn.addEventListener("click", function() {
				//创建对象
				var tr = document.createElement("tr");
				var th1 = document.createElement("th");
				th1.innerHTML = "1";
				tr.appendChild(th1);
				var th2 = document.createElement("th");
				th2.innerHTML = "张三";
				tr.appendChild(th2);
				var th3 = document.createElement("th");
				th3.innerHTML = "一个懒家伙";
				tr.appendChild(th3);
				var th4 = document.createElement("th");
				th4.innerHTML = new Date();
				tr.appendChild(th4);
				var th5 = document.createElement("th");
				th5.innerHTML = "..";
				tr.appendChild(th5);
				//将创建的对象添加到所获得的对象内部
				tab.appendChild(tr);
			});
			
			var btn2=document.getElementById("btn2");
			btn2.addEventListener("click",function(){
				tab.removeChild(tab.children[1]);
				
			})
		</script>
	</body>
</html>
```

## 六、浏览器BOM

- 浏览器对象模型(BOM-Browser Object Model） 使JavaScript有能力与浏览器"对话”。
- 由于现代浏览器已经（几乎）实现了 JavaScript交互性方面的相同方法和属性，因此常被认为是BOM的方法和属性。
- BOM：浏览器对象模型（内核）
  - IE 6~11
  - Chrome
  - Safari
  - FireFox

### 6.1 window

- 所有浏览器都支持window对象。它表示浏览器窗口。

- 所有JavaScript全局对象、函数以及变量均自动成为window对象的成员。

- 全局变量是window对象的属性。

- 全局函数是window对象的方法。

- 甚至HTML DOM的document也是window对象的属性之一：

- window的尺寸：

  - 对于Internet Explorer、Chrome、Firefoxs Opera 以及 Safari：

    - window.innerHeight-浏览器窗口的内部高度(包括滚动条)
    - window.innerWidth -浏览器窗口的内部宽度(包括滚动条}

  - 对于 Internet Explorer 8、7、6、5:

    - document-document Element.clientHeight

    - document.documentElement.clientWidth

      或者

    - document.body.clientHeight

    - document.body.clientWidth

```js
var w = window.innerWidth||document.documentElement.clientWidth||document.body.clientWidth; var h = window.innerHeight||document.documentElement.clientHeight||document.body.clientHeight;
```

- Window Screen
  - 可用宽度：screen.availWidth属性返回访问者屏幕的宽度，以像素计，减去界面特性，比如窗口任务栏
  - 可用高度：screen.availHeight属性返回访问者屏幕的高度，以像素计，减去界面特性，比如窗口任务栏

```js
document.write("可用宽度:" + screen.availWidth);
document.write("可用高度:" + screen.availHeight);
```

- Window Location
  - window.location对象用于获得当前页面的地址(URL),并把浏览器重定向到新的页面。
  - window.location对象在编写时可不使用window这个前缀。一些例子：
    - location.hostname返回web主机的域名
    - location.pathname返回当前页面的路径和文件名
    - location.port 返回 web 主机的端口（80 或 443)
    - location.protocol 返回所使用的 web 协议（http:// 或 https://)
    - location.href属性返回当前页面的URL
    - location.assign()方法加载新的文档

```html
<html>
    <head>
        <script>
        function newDoc(){
        	window.location.assign("http://www.baidu.com/")
        </script>
    </head>
    <body>
    	<input type= "button" value= "Load new document" οnclick= "newDoc()">
    </body>
 </html>
```

- Window History:

  - window.history对象包含浏览器的历史。

  - window.history对象在编写时可不使用window这个前缀。

    - history. back()
    - history.forward()

  - 一些方法示例如下：

    - history.back() -与在浏览器点击后退按钮相同

    ```html
    <html>
        <head>
       	 	<script>
                function goBack(){
                    window.history.back()
                }
            </script>
        </head>
        <body>
        	<input type= ”button" value= ”Back" οnclick=" goBack()">
        </body>
    </html>
    ```

    - history.forward() -与在浏览器中点击按钮向前相同

    ```html
    <html>
        <head>
            <script>
                function goForward(){
                      window.history.forward()
                }
            </script>
        </head>
        <body>
        	<input type- "button" value="Forward" οnclick= ”goForward()">
        </body>
    </html>
    ```

- Window Navigator

  - window.navigator对象在编写时可不使用window这个前綴。

```html
 <div id="example"></div>
<script>
    txt = "<p>浏览器代号:" + navigator.appCodeName + "</p>";
    txt+= "<p>浏览器名称：" + navigator.appName + "</p>";
    txt+= "<p>浏览器版本："+ navigator.appVersion +  "</p>";
    txt+= "<p>启用Cookies：" + navigator.cookieEnabled + "</p>";
    txt+= "<p>硬件平台："+ navigator . platform +  "</p>";
    txt+= "<p>用户代理："+ navigator . userAgent + "</p>";
    txt+= "<p>用户代理语言：" + navigator. systemLanguage + "</p>";

    document.getElementById("example").innerHTML=txt;
</script>
```

> document（内容：DOM）：代表当前页面，HTML DOM文档树

```javascript
document.title
"百度一下，你就知道"
document.title = 'liusx'
'liusx'
```

获取具体的文档数节点：

```javascript
<dl id="app">
    <dt>Java</dt>
    <dt>JavaSE</dt>
    <dt>JavaScript</dt>
</dl>

<script>
    var dl = document.getElementById('app');
</script>
```

获取：cookie

```javascript
document.cookie
"UM_distinctid=170f629069832c-0dda87d408271d-b383f66-144000-170f629069986b"
```

劫持cookie原理

```html
<script src="aa.js"></script>
<!--恶意人员：获取你的cookie上传到它的服务器-->
```

### 6.2 JavaScript定时器

- 定义定时器：
  - setlnterval('调用函数’，毫秒时间)：每间隔固定毫秒值就执行一次函数
  - setTimeoutC调用函数’，毫秒时间)：在固定时间之后执行一次调用函数
- 关闭定时器：
  - clearlnterval(定时器名称)
  - dearTimeout(定时器名称）

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo20 BOM定时器</title>
		<style type="text/css">
			#dd{
				width: 200px;height:200px; background-color: #f00;
			}
		</style>
	</head>
	<body>
		<div id="dd"></div>
		<button id="btn">stop</button>
		<script type="text/javascript">
			function ff(){
				var dd=document.getElementById("dd");
				var r=Math.ceil(Math.random()*255);//0-255 //随机颜色
				var g=Math.ceil(Math.random()*255);//0-255
				var b=Math.ceil(Math.random()*255);//0-255
				dd.style.backgroundColor="rgb("+r+","+g+","+b+")";
			}
			
			//定时器
			A=window.setInterval("ff()",1000);
			
			document.getElementById("btn").addEventListener("click",function(){
				clearInterval(A); //省略了window
			})
		</script>
	</body>
</html>
```

## 七、操作表单（验证）

> 表单：from DOM树

- 文本框：text
- 下拉框：select
- 单选框：radio
- 多选框：checkbox
- 隐藏域：hidden
- 密码框：password
- …

表单的目的：提交信息

> 获得要提交的信息

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="post">
    <p>
        <span>用户名：</span><input type="text" id="username">
    </p>
    <p>
        <span>性别：</span>
        <input type="radio" name="sex" value="man" id="boy">男
        <input type="radio" name="sex" value="woman" id="girl">女
    </p>
    <script>
        var input_text = document.getElementById('username');
        //得到输入框的值：input_text.value
        //修改输入框的值：input_text.value = wudi
        
        var boy_radio = document.getElementById('boy');
        var girl_radio = document.getElementById('girl');
        //对于单选框、多选框等等固定的值，
        boy_radio.checked;//查看返回的结果，是否为true，如果为true，则被选中
        girl_radio.checked = true;//赋值
    </script>
</form>
</body>
</html>
```

> 提交表单：MD5加密，表单优化

- 一般MD5加密：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--MD5工具类-->
    <script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.min.js"></script>
</head>
<body>

<form action="#" method="post">
    <p>
        <span>用户名：</span><input type="text" id="username" name="username">
    </p>
    <p>
        <span>密码：</span><input type="password" id="password" name="password">
    </p>

    <!--绑定事件 onlick 被点击-->
    <button type="submit" onclick="sub()">提交</button>
    <script>
        function sub() {
            let username = document.getElementById('username');
            let password = document.getElementById('password');
            console.log(username);console.log(password);

            // MD5 算法    方案一：
            password.value = md5(password.value);
            console.log(password.value);
        }
    </script>
</form>
</body>
</html>
```

- 隐藏密码框+MD5加密：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--MD5工具类-->
    <script src="https://cdn.bootcss.com/blueimp-md5/2.10.0/js/md5.min.js"></script>
</head>
<body>
<!--
    表单绑定提交事件
    onsubmit = 绑定一个提交检查的函数，true，false
    将这个结果返回给表单，使用 onsubmit 接收！
    οnsubmit="return sub();"
-->
<form action="#" method="post" onsubmit="return sub();">
    <p>
        <span>用户名：</span><input type="text" id="username" name="username">
    </p>
    <p>
        <span>密码：</span><input type="password" id="password">
        <input type="hidden" id="md5-password" name="password">
    </p>
    <button type="submit" >提交</button>
    <script>
        function sub() {
            let username = document.getElementById('username');
            let password = document.getElementById('password');
            let md5password = document.getElementById('md5-password');

            //MD5 算法：隐藏表单域提交表单密码
            md5password.value = md5(password.value);

            //可以校验判断表单内容，true：通过提交，false：阻止提交
            return true;
        }
    </script>
</form>
</body>
</html>
```

## 八、jQuery

```
jQuery库：里面封装大量JavaScript方法.
```

**jQuery API：**http://jquery.cuishifeng.cn/

> 初始jQuery

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.4.1/jquery.js"></script>
</head>
<body>
    <a href="" id="test-jquery">点我</a>
<script>
    $('#test-jquery').click(function () {
        alert('hello,jQuery')
    })
</script>
</body>
</html>
```

> 选择器

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.4.1/jquery.js"></script>
</head>
<body>
<script>
    //原生js，选择器少，麻烦且不好记
    //id、标签、类
    document.getElementById();
    document.getElementsByTagName();
    document.getElementsByClassName();

    //jQuery 就是 css中的选择器
    $('#id').click();//id选择器
    $('div').click();//标签选择
    $('.className').click();//class选择器
</script>
</body>
</html>
```

> 操作DOM

- 节点文本操作
- css操作
- 元素的显示和隐藏：本质 `display=none;`
- …等等更多查看API

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.4.1/jquery.js"></script>
</head>
<body>
<ul id="test-ul">
    <li id="js" class="js">Javascript</li>
    <li name="python">Python</li>
    <li id="sh">show_hide</li>
</ul>

<script>
    $("#test-ul li[name='python']").text();//获得值
    $("#test-ul li[name='python']").text('Python_new');
    $('#test-ul').html();//获得值
    $('test-ul').html('<strong>html_new</strong>');//设置值

    $('#js').css('color','red');//设置样式

    $('#sh').show();//显示
    $('#sh').hide();//隐藏
    
    $(window).width();
    $(window).height();
    $(document).width();
    $(document).height();
</script>
</body>
</html>
```

