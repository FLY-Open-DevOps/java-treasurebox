## 一、引言

### 1.1HTML 概念

- 网页，是网站中的一个页面，通常来说网页是构成网站的基本元素，是承载各种网站应用的平台。
- 通俗的说，网站就是甶网页组成的。
- 通常我们看到的网页都是以htm或html后缀结尾的文件，俗称HTML文件。

## 二、HTML简介

### 2.1什么是HTML

- HTML全称：Hyper Text Markup Language(超文本标记语言）
  - 超文本：页面内可以包含图片、链接，甚至音乐、程序等非文字元素
  - 标记：标签，不同的标签实现不同的功能
  - 语言：人与计算机的交互工具

### 2.2 HTML能做什么

- HTML使用标记标签来描述**网页**，展示信息给用户

### 2.3 HTML书写规范

- HTML标签（多数）是以尖括号包围的关键字
- HTML标签通常是成对出现的，有开始就有结束
- HTML通常都有属性，格式：属性=‘属性值’ (多个属性之间空格隔开)
- HTML标签不区分大小写，建议全小写

## 三、HTML基本标签

### 3.1结构标签

```html
<html>:根标签
	<head>:网页头标签
		<title></title> :页面的标题 
    </head>
	<body></body>:网页正文 
</html>
123456
```

| 属性名     | 代码                               | 描述                         |
| ---------- | ---------------------------------- | ---------------------------- |
| text       | < body text="#f00"></ body>        | 设置网页正文中所有文字的颜色 |
| bgcolor    | < body bgcolor="#00f"></ body>     | 设置网页的背景色             |
| background | < body background=“1.png”></ body> | 设置网页的背景图             |

- 颜色的表示方式：
  - 第一种方式：用表示颜色的英文单词，例，red green blue
  - 第二种方式：用16进制表示颜色，例，#000000 #325687 #377405

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo1</title>
	</head>
	<body text="red" bgcolor="#0f0" background="picture/logo.png">  ## RedGreenBlue      
		hello world!
	</body>
</html>

1234567891011
```

### 3.2排版标签

```html
可用于实现简单的页面布局
注释标签：<!-- 注释 -->
换行标签：<br>
段落标签：<p>文本文字</p>
	特点：段与段之间有空行
	属性：align对齐方式(left、center、right)

水平线标签:<hr/>
	属性：
		width:水平线的长度(两种：第一种:像素表示; 第二种，百分比表示) 
		size：水平线的粗细(像素表示，例如：10px)
		color：水平线的颜色
		align：水平线的对齐方式
12345678910111213
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo2排版标签</title>
	</head>
	<body>
		<!--换行-->
		换<br>行
		<!--段落-->
		<p>段落1</p>	
		<p align="center">段落2</p>
		<p align="right">段落3</p>
		
		<!--水平线-->
        <hr width="50%" align="left" color="#f00" size="7"/>
	</body>
</html>

12345678910111213141516171819
```

### 3.3块标签

- 使用 CSS + DIV 是现下流行的一种布局方式

| 标签 | 代码            | 描述                           |
| ---- | --------------- | ------------------------------ |
| div  | < div> </ div>  | 行级块标签，独占一行，换行     |
| span | < span></ span> | 行内块标签，所有内容都在同一行 |

### 3.4基本文字标签

- < font></ font> 标签处理网页中文字的显示方式

| 属性名 | 代码                         | 描述                                 |
| ------ | ---------------------------- | ------------------------------------ |
| size   | < font size=“7”></ font>     | 用于设置字体的大小，最小1号，最大7号 |
| color  | < font color="#f00"></ font> | 用于设置字体的颜色                   |
| face   | < font face='宋体"></ font>  | 用于设置字体的样式                   |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo3块标签  基本文字标签</title>
	</head>
	<body>
		<div>div标签1 div标签1 <font size="5" color="red"> div标签1</font> div标签1 div标签1 div标签1</div>
		<div>div标签2</div>
		<div>div标签3</div>
		<span>span标签1</span>
		<span>span标签2 登录</span>
		<span>span标签3</span>
	</body>
</html>

12345678910111213141516
```

### 3.5文本格式化标签

- 使用标签 实现标签的样式处理

| 标签   | 代码                | 描述     |
| ------ | ------------------- | -------- |
| b      | < b></ b>           | 粗体标签 |
| strong | < strong></ strong> | 加粗     |
| em     | < em></ em>         | 强调字体 |
| i      | < i></ i>           | 斜体     |
| small  | < small></ small>   | 小号字体 |
| big    | < big></ big>       | 大号字体 |
| sub    | < sub></ sub>       | 上标标签 |
| sup    | < sup></ sup>       | 下标标签 |
| del    | < del></ del>       | 删除线   |

### 3.6标题标签

- 随着数字增大文字逐渐变小，字体是加粗的，内置字号，默认占据一行

| 标签 | 代码        | 描述               |
| ---- | ----------- | ------------------ |
| h1   | < h1></ h1> | 一号标题，最大字号 |
| h2   | < h2></ h2> | 二号标题           |
| h3   | < h3></ h3> | 三号标题           |
| h4   | < h4></ h4> | 四号标题           |
| h5   | < h5></ h5> | 五号标题           |
| h6   | < h6></ h6> | 六号标题，最小字号 |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo4 文本格式化标签 标题标签 </title>
	</head>
	<body>
		<b>粗体标签  </b> <strong>粗体标签  </strong>
		<br/>  
		<i>斜体 </i> <em>斜体 </em>
		<br/>  
		<small> 小号字体   </small>    <big>大号字体</big>
		<br/>  
		 5m <sup>2</sup>上标标签     H<sub> 2</sub>O下标标签  
	    <br/>  
     	<del>删除线  </del>
	    <br/>  
	     
        <h1>1</h1>  
		<h2>2</h2>
		<h3>3</h3>
		<h4>4</h4>
		<h5>5</h5>
		<h6>6</h6>
		
	</body>
</html>

12345678910111213141516171819202122232425262728
```

### 3.7列表标签(清单标签）

- 无序列表：使用一组无序的符号定义，< ul></ ul>

```html
<ul type="circle">
    <li></li>
</ul>
123
```

| 属性值 | 描述     | 用法举例                  |
| ------ | -------- | ------------------------- |
| circle | 空心圆   | < ul type=“circle”>< /ul> |
| disc   | 实心圆   | < ul type=“disc”>< /ul>   |
| square | 黑色方块 | < ul type=“square”></ ul> |

- 有序列表：使用一组有序的符号定义，< ol></ ol>

```html
<ol type="a" start="1">
    <li></li>
</ol>
123
```

| 属性值 | 描述         | 用法举例             |
| ------ | ------------ | -------------------- |
| 1      | 数字类型     | < ol type=“1”></ ol> |
| A      | 大写字母类型 | < ol type=“A”></ ol> |
| a      | 小写字母类型 | < ol type=“a”></ ol> |

- 列表嵌套：无序列表与有序列表相互嵌套使用

代码举例：

```html
<ul type="circle">
			<li>无序1 </li>
			<li>无序2 </li>
			<li>
				<ol type="A" start="3">
					<li>有序1</li>
					<li>有序2</li>
					<li>有序3</li>
				</ol> 
			</li>
</ul>
1234567891011
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo5 列表标签(清单标签）</title>
	</head>
	<body>
		无序列表<br />
		<ul type="circle">
			<li>无序1 </li>
			<li>无序2 </li>
		</ul>
		
		
		有序列表<br />
		<ol type="A" start="3">
			<li>有序1</li>
			<li>有序2</li>
			<li>有序3</li>
		</ol>
		
		
		嵌套列表
		<ul type="circle">
			<li>无序1 </li>
			<li>无序2 </li>
			<li>
				<ol type="A" start="3">
					<li>有序1</li>
					<li>有序2</li>
					<li>有序3</li>
				</ol> 
			</li>
		</ul>
	</body>
</html>

12345678910111213141516171819202122232425262728293031323334353637
```

### 3.8图形标签

- 在页面指定位置处中引入一幅图片，< img/>

| 属性名 | 描述                 |
| ------ | -------------------- |
| src    | 引入图片的地址       |
| width  | 图片的宽度           |
| height | 图片的高度           |
| border | 图片的边框           |
| align  | 与图片对齐显示方式   |
| alt    | 提示信息             |
| hspace | 在图片左右设定空白   |
| vspace | 在图片的上下设定空白 |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo6 图形标签</title>
	</head>
	<body>
		<p align="center">
		前一行<br />	
		前面的文字<img src="picture/logo.png" width="300" align="middle" border="10" vspace="50" hspace="50"/>后面的文字<br/>

		
		后一行<br />
		</p>
	</body>
</html>

1234567891011121314151617
```

### 3.9链接标签

- 在页面中使用链接标签跳转到另一页面
- 标签：< a href=" ">< /a>
- 属性：href：跳转页面的地址(跳转到外网需要添加协议)
- 设置跳转页面时的页面打开方式，target属性：
  - _blank在新窗口中打开
  - _self在原窗口中打开
- 指向同一页面中指定位置（锚点）
- 使用方式：
  - 定义位置：< a name= "名称“>< /a>
  - 指向：< a href= ”#名称" >< /a>

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo7 链接标签</title>
	</head>
	<body>
		首页
		<a name="top"><font size="4" color="red">顶部</font></a><br />
		<a href="demo6.html" target="_blank">跳转到另一个页面</a>
		<br /><br /><br /><br /><br /><br /><br />
		<br /><br /><br /><br /><br /><br /><br />
		<br /><br /><br /><br /><br /><br /><br />
		<br /><br /><br /><br /><br /><br /><br />
		<br /><br /><br /><br /><br /><br />
		<br /><br /><br /><br /><br /><br /><br />
		<br /><br /><br /><br /><br /><br /><br /><br />
		<a href="#top">回到顶部</a>
		
	</body>
</html>

12345678910111213141516171819202122
```

### 3.10表格标签

- 普通表格(tabletr,td)

```html
<table>
	<tr>
		<td></td>
	</tr>
</table>
12345
```

- 表格的列标签(th)：内容有加粗和居中效果

```html
<table>
	<tr>
		<th></th>
	</tr>
</table>
12345
```

- 表格的列合并属性(colspan)：在同一行内同时合并多个列

```html
<table>
	<tr>
		<td colspan=""></td>
	</tr>
</table>
12345
```

- 表格的行合并属性(rowspan)：在同一列跨多行合并

```html
<table>
	<tr >
		<td rowspan=""></td>
	</tr>
</table>
12345
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo8 表格标签</title>
	</head>
	<body>
		<!--
        	作者：1903202403@qq.com
        	时间：2020-08-01
        	描述：表格
        -->
		<table border="1" width="400" height="200">
			<!--行-->
			<tr >
				<!-- 列 -->
				<td rowspan="2">周一</td>
				<td>周二</td>
				<td>周三</td>
			</tr>
			<!--行-->
			<tr>
				<!-- 列 -->
				<td colspan="2"></td>
			</tr>
		</table>
	</body>
</html>

1234567891011121314151617181920212223242526272829
```

## 四、HTML表单标签

- html表单用于收集不同类型的用户输入数据

### 4.1 form元素常用属性

- action表示动作，值为服务器的地址，把表单的数据提交到该地址上处理
- method:请求方式：get 和post
- get
  - 地址栏，请求参数都在地址后拼接path?name=n张三"&password="123456n
  - 不安全
  - 效率高
  - get请求大小有限制，不同浏览器有不同，但是大约是2KB;—般情况用于查询数据
- post：
  - 地址栏：请求参数单独处理。
  - 安全可靠些
  - 效率低
  - post请求大小理论上无限；一般用于插入删除修改等操作
- enctype:表示是表单提交的类型
  - 默认值：application/x-www-form-urlencoded 普通表单
  - multipart/form-data多部分表单(一般用于文件上传）

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo9 表单</title>
	</head>
	<body>
		<!--表单基本属性 ： action  method  enctype-->
		<form action="demo8.html" method="get">
			用户名：<input type="text" name="uname" /><br>
			密&nbsp;&nbsp;&nbsp;码：<input type="password" name="upass"><br>
			<Input type="submit" value="提交">
		</form>
		
	</body>
</html>

1234567891011121314151617
```

### 4.2 input元素

- 作为表单中的重要元素，可根据不同type值呈现为不同状态

| 属性值   | 描述         | 代码                               |
| -------- | ------------ | ---------------------------------- |
| text     | 单行文本框   | < input type=" text"></ input>     |
| readonly | 只读         | <input readonly="readonly">        |
| disabled | 禁用         | <input disabled="disabled">        |
| password | 密码框       | < input type="password "></ input> |
| redio    | 单选按钮     | < input type="redio "></ input>    |
| checkbox | 复选框       | < input type="checkbox "></ input> |
| date     | 日期框       | < input type="date "></ input>     |
| time     | 时间框       | < input type="time "></ input>     |
| datetime | 日期和时间框 | < input type="datetime "></ input> |
| email    | 电子邮件输入 | < input type="email "></ input>    |
| number   | 数值输入     | < input type=“number”></ input>    |
| file     | 文件上传     | < input type=" file"></ input>     |
| hidden   | 隐藏域       | < input type="hidden "></ input>   |
| range    | 取值范围     | < input type=" range"></ input>    |
| color    | 取色按钮     | < input type="color "></ input>    |
| submit   | 表单提交按钮 | < input type="submit "></ input>   |
| button   | 普通按钮     | < input type="button "></ input>   |
| reset    | 重置按钮     | < input type="reset "></ input>    |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo10 input元素</title>
	</head>
	<body>
		<form action="success.html" method="get">
			用户名：<input type="text" value="zhangsa" name=""><br>
			密码：<input type="password" name=""><br>
			单选：<input type="radio" name="sexy"><input type="radio" name="sexy"><br>
			多选：<input type="checkbox" name=""><input type="checkbox" name=""><input type="checkbox" name=""><br>
			生日：<input type="date" name=""><br>
			薪资：<input type="number" name=""><br>
			电话：<input type="tel" name=""><br>
			照片：<input type="file" name=""><br>
			喜欢的颜色：<input type="color" name=""><br>
			年龄范围：<input type="range" name=""><br>
			隐藏域：<input type="hidden" name=""><br>
			<hr>
			表单提交：<input type="submit" name=""><br>
			数据重置：<input type="reset" name=""><br>
			图片提交按钮：<input type="image" src="" name=""><br>
			普通按钮：<input type="button" value="普通" name="">
		</form>
	</body>
</html>
123456789101112131415161718192021222324252627
```

### 4.3select元素(下拉列表）

- 单选下拉列表：< select>< /select>
- 默认选中属性：selected=“selected”

```html
<select>
    <option selected="selected"></option>
    ...
     <option ></option>
</select>
12345
```

- 多选下拉列表属性：< select></ select>
- 多选列表：multiple = “multiple”

```html
<select multiple = "multiple">
    <option ></option>
    ...
     <option ></option>
</select>
12345
```

### 4.4 textarea元素(文本域）

- 多行文本框：< textarea cols= “列” rows=“行”>< /textarea>

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo12 select, textarea元素</title>
	</head>
	<body>
		<form action="success.html" method="get">
			学历：
			<select name="edu">
				<option value="1">研究生</option>
				<option value="2">本科</option>
				<option value="3">大专</option>
				<option value="4" selected="selected">高职</option>
			</select>
			<br>
			爱好：
			<select name="hobby" multiple="multiple" size="2">
				<option value="1">编程</option>
				<option value="2">游戏</option>
				<option value="3">抖音</option>
				<option value="4">快手</option>
			</select><br>
			<textarea name="tt" cols="10" rows="5">....</textarea>
			<input type="submit">
		</form>
	</body>
</html>
```

### 4.5页面结构分析

> 页面结构分析

| 元素名  | 描述                                               |
| ------- | -------------------------------------------------- |
| header  | 标题头部区域的内容（用于页面或页面中的一块区域）   |
| footer  | 标记脚部区域的内容（用于整个页面或页面的一块区域） |
| section | Web网页中的一块独立区域                            |
| article | 独立的文章内容                                     |
| aside   | 相关内容或应用                                     |
| nav     | 导航类辅助内容                                     |

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>页面结构分析</title>
</head>
<body>

    <header>
        <h2>页面头部</h2>
    </header>

    <section>
        <h2>页面主体</h2>
    </section>

    <footer>
        <h2>页面底部</h2>
    </footer>

</body>
</html>
```

## 五、HTML框架标签

- 通过使用框架，你可以在同一个浏览器窗口中显示不止一个页面。每份HTML文档称为一个框架，并且每个框架都独立于其他的 框架。
- 使用框架的缺点：
  - 开发人员必须同时跟踪更多的HTML文档
  - 很难打印整张页面

### 5.1框架结构标签frameset

- 框架结构标签（< frameset></ frarneset>)用于定义如何将窗口分割为框架
- 每个frameset定义了一系列行或列
- rows/columns的值规定了每行或每列占据屏幕的面积
  - < frameset rows=""></ frameset>
  - < frameset cols="">< /frameset>

### 5.2框架标签frame

- 每个frame引入一个htm顷面

```html
<frameset cols= ’*,*">
 	<frame src="info1.html"/>
    <frame src="info2.html"/>          
</frameset>
1234
```

### iframe内联框架

> iframe内联框架

```html
<iframe src="path" name="mainFrame"></iframe>
path:引用网页地址
mainFrame:框架标识名
123
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>内联框架</title>
</head>
<body>
    <!--iframe内联框架
    src：地址
    w-h：宽度高度
    -->
    <iframe src="http://baidu.com" frameborder="0" width="300px" height="300px">
    </iframe>

    <iframe src="" name="hello" frameborder="0"></iframe>
    <a href="1.我的第一个HTML.html" target="hello">点击跳转</a>


    <iframe src="//player.bilibili.com/player.html?aid=55631961&bvid=BV1x4411V75C&cid=97257627&page=10" 
            scrolling="no" border="0" 
            frameborder="no" framespacing="0" allowfullscreen="true">
    </iframe>
</body>
</html>
```



### 5.3基本的注意事项

- 不能将< body>< /body>标签与< frameset>< /frameset>标签同时使用
- 假如一个框架有可见边框，用户可以拖动边框来改变它的大小。为了避免这种情况发生，可以在< frame>标签中加入: noresize=“noresize”

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo13 框架集</title>
	</head>
	
	<frameset cols="*,*,*">
		<frame src="demo1.html" />
		<frame src="demo2.html" noresize="noresize" />
		<frameset rows="10%,*">
			<frame src="demo3.html" />
			<frame src="demo4.html" />
		</frameset>
	</frameset>
</html>
```

### 六.表单初级验证

> 表单初级验证

- 常用方式
  - placeholder：提示信息
  - required：非空判断
  - pattern：正则表达式

```html
<p>名称：<input type="text" name="username" placeholder="请输入用户名"></p>
<p>名称：<input type="text" name="username" required></p>
<!--常用的正则：https://www.jb51.net/tools/regexsc.htm-->
<p>自定义邮箱：
    <input type="text" 
           name="diy" 
           pattern="/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/">
</p>
```

## 七、HTML的其它标签和特殊字符

### 7.1其他标签

```html
<meta http-equiv="keywords" content="keyword1f, keyword2,keyword3"> 
<!--该网页的描述-->
<meta http-equiv="description" content="this is my page">

<!--该网页的编码-->
<meta http-equiv = "content-type" content= "text/html; charset=UTF-8"> 

<!-- href:引入css文件的地址-->
<link rel="stylesheet" type="text/css" href="./styles.css">

<!--src: js的文件地址-->
<script type= "text/javascript" src="" ></script>
123456789101112
```

### 7.2特殊字符

占位符：空格

```
&nbsp;
```