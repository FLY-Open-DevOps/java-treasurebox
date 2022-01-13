## 一、引言

### 1.1CSS概念

- 层叠样式表(英文全称：Cascading Style Sheets)是一种用来表现HTML (标准通用标记语言的一个应用）或XML (标准通用标记语言的一 个子集）等文件样式的计算机语言。
- CSS不仅可以静态地修饰网页，还可以配合各种脚本语言动态地对网页各元素进行格式化。

## 二、CSS简介

### 2.1什么是CSS

- CSS:全称：Cascading Style Sheets层叠样式表，定义如何显示HTML元素
- 多个样式可以层层覆盖叠加，如果不同的css样式对同一 html标签进行修饰，样式有冲突的应用优先级高的，不冲突的共同作用

### 2.2 CSS能干什么

- 修饰美化html网页。
- 外部样式表可以提高代码复用性从而提高工作效率。
- html内容与样式表现分离，便于后期维护。

### 2.3 CSS书写规范

- CSS规则由两个主要的部分构成：选择器，以及一条或多条声明：
  - 选择器通常是您需要改变样式的HTML元素。
  - 每条声明由一个属性和一个值组成。

### 2.4基础语法：选择器{属性：值; 属性:值…}

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080209452861.jpg)

- 注意事项：
  - 请使用花括号来包围声明
  - 如果值为若干单词，则要给值加引号
  - 多个声明之间使用分号; 分开
  - css对大小不敏感，如果涉及到与html文档一起使用时，class与id名称对大小写敏感

## 三、CSS导入方式

### 3.1内嵌方式(内联方式）

- 把CSS样式嵌入到html标签当中，类似属性的用法

```html
<div style="color:blue;font-size:50px"> This is my HTML page</div>
```

### 3.2内部方式

- 在head标签中使用style标签引入css

```html
<head>
    <style type="text/css"> //告诉浏览器使用css解析器去解析 
		div{color:red;font-size:50px} //此处 div 为选择器，代码中的div块都会应用
	</style>
</head>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo1 CSS导入方式1和2</title>
		
		<!--CSS导入方式--2.内部方式 --> 
		<style type="text/css">
			/*class选择器*/
			.p{color:#0f0;}
			/*元素选择器*/
			p{color:#ff0;}
			/*id选择器*/
			#p{font-size: 25px;}
			
		</style>
	</head>
	<body>
		<div style="color:#f00;font-size: 20px;"> CSS导入方式-- 1.内嵌方式(内联方式)</div>
		<div> 对比 CSS导入方式-- 1.内嵌方式(内联方式)</div>
		
		<!--标签选择器、id选择器、class选择器-->
		<p>CSS导入方式--2.内部方式（并使用了元素选择器）</p>
		<p id="p" class="p">2.内部方式（并使用了id选择器，class选择器）</p>
		<p>2.内部方式</p>
		<p>2.内部方式</p>
	
	</body>
</html>
```

### 3.3外部方式

- 将css样式抽成一个单独文件，使用者直接引用

```html
创建单独文件 div.css
内容示例：div{color: green;font-size: 50px>
引用语句写在head标签内部
	<head>
        <link rel="stylesheet" type="text/css" href="div.css"></link> 	
	</head>
	rel：代表当前页面与href所指定文档的关系 
	type：文件类型，告诉浏览器使用css解析器去解析 
	href：css文件地址

```

### 3.4 @import方式

- 在页面中引入一个独立的单独文件

```html
  <head>
  	<style type="text/css">
  		@import url("div.css")
  	</style>
  </head>
```

- 该内容放在head标签中
- linked和@import方式的区别：
  - link所有浏览器都支持，@import某些版本低的IE不支持
  - @import是等待html加载完成才加载
  - @import不支持js动态修改

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo2 CSS导入方式3和4</title>
		<!--CSS导入方式--3.外部方式 --> 
		<!--<link type="text/css" rel="stylesheet" href="demo2.css" />-->
		
		<!--CSS导入方式--4.@import方式-->
		<style type="text/css">
			@import url("demo2_2.css");
		</style>
	</head>
	<body>
		<!--class选择器：允许class重名； id选择器：id值必须唯一-->
		<p class="pd">段落标签</p> 
		<div class="pd">div标签</div>
	</body>
</html>


demo2.css:
/*全部是合法的css语法*/
/*
 * 选择器{属性名:值;}
 */
.pd{
	color:#f00;
}


demo2_2.css:

.pd{font-size: 30px;}
```

## 四、CSS选择器

- 主要用于选择需要添加样式的html元素

### 4.1基本选择器

#### 4.1.1 元素选择器：

- 在head中使用style标签引入在其中声明 元素选择器:html标签{属性:属性值},

```html
<head>
    <style type="text/css"> 
		span{color: red;font-size:100px} //span标签都会应用
	</style> 
</head>
```

#### 4.1.2 id选择器：

- 给需要修改样式的html元素添加id属性标识，在head中使用style标签引入在其中声明id选择器: #id值{属性:属性值}，具体实例如下：

```html
创建id选择器：
<style type="text/css">
    #s1{color: red;font-size: 100px} 
    #s2{color: green;font-size: 100px} 
    #s3{color: blue;font-size : 100px} 
</style>

根据id选择器进行html文件修饰
<div id="s1"> hello,everyone!</div> 
<div id="s2"> hello,everyone!</div> 
<div id="s3"> hello,everyone!</div> 
```

#### 4.1.3 class选择器：

- 给需要修改样式的html元素添加class属性标识，在head中使用style标签引入在其中声明class选择器: .class名{属性:属性 值}，具体实例如下：

```html
创建class选择器：

<style type="text/css">
	.s1{color: purple;font-size: 100px}
	.s2{color: pink;font-size: 100px}
	.s3{color: yellow;font-size: 100px}
</style> 

根据id选择器进行html文件修饰：
<div class= "s1"> hello, everyone! </div>
<div class= "s2"> hello,everyone!</div>
<div class= "s3"> hello,everyone!</div>
```

- 备注：以上基本选择器的优先级从高到低：id选择器，class选择器，元素选择器

### 4.2属性选择器

- 根据元素的属性及属性值来选择元素。在head中使用style标签引入在其中声明
- 格式为：
  - htm标签[属性= ‘属性值’]{css属性:css属性值;}
  - html标签[属性]{css属性:css属性值;}

```html
body内容:
    <form name="login" action="#" method="get">
        <font size = "3">用户名：</font>
        <input type="text" name= "username" value="zhangsan"><br>
        <font size= "3">密码：</font>
        <input type= "password" name="password" value="123456"><br/> 
        <input type="submit" value="登录">
    </form> 
                                  
head中书写：

<style type="text/css"> 
    input[type='text'] {
        background-color: pink;
    }

    input[type='password'] {
    	background-color: yellow;
    }

    font[size] {
    	color: green
    }
                      
    a[href] {
    	color: blue;
    }
</style>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo3 属性选择器</title>
		<style type="text/css">
			div{width:200px; height: 50px; border:solid 1px #f00;}
			div[class]{font-size: 50px;} /*代表 div标签如果使用了class选择器，就采用这个样式*/
			div[class="d2"]{color: gray;}
		</style>
	</head>
	<body>
		<div class="d1">一</div>
		<div>二</div>
		<div class="d2">三</div>
		<div>四</div>
	</body>
</html>
```

### 4.3伪元素选择器

- 主要是针对a标签
- 语法：
  - 静止状态a:link{css属性}
  - 悬浮状态a:hover{css属性}
  - 触发状态a:active{css属性}
  - 完成状态a:visited{css属性}

```html
代码：
	<a href ="https://hao.360.cn/">点我吧</a> 
样式：
<head>
    <style type="text/css">
        <!—静止状态--> 
        a:link {color: red;}
        
        <!—悬浮状态
        a:hover {color: green;}
        
        <!--触发状态--> 
        aractive {color: yellow;}
        
        <!--完成状态--> 
        a:visited {color: blue;}
	</style>
</head>
```

### 4.4层级选择器

- 父级选择器 子级选择器…，具体示例如下:

```html
<head>
    <style type="text/css">
        #div1 .divl1{color:red;}
        #div1 .div12{color:purple;}
        .div2 #div22{color:green;}
        .div2 #div23{color:blue;}
	</style>
</head>

<body>
    <div id="div1">
        <div class="div11">
            <span>span1-1</span>
        </div>
        <div class="divl2">
            <span>span1-2</span>
        </div>
    </div>

    <div class="div2">
        <div id="div22">
       	 <span>span2-1</span>
        </div>
        <div id="div23">
        	<span>span2-2</span>
        </div>
    </div>
</body>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo4 伪元素选择器  层级选择器</title>
		<style type="text/css">
			/*伪元素选择器*/
			a:link{color:#00f;}
			a:hover{font-size: 20px;}
			a:active{color:#ccc;}
			a:visited{font-size: 12px; color: #ff0;}
			
			/*层级选择器*/
			/*先进入 d1 层的id选择器，然后再进入div 的元素选择器*/
			#d1 div{width: 100px; height: 100px; background-color: #f00;}
			#d1 div:hover{width:500px;}
		</style>
	</head>
	<body>
		<a href="http://www.baidu.com">百度</a><br /><br /><br />
		<div id="d1">
			<div>包含层级关系div</div>	
		</div>
		
		<div>独立div</div>
	</body>
</html>
```



## 五、css属性

### 标准文档流

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020051623505262.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2MjU5MTQz,size_16,color_FFFFFF,t_70)

块级元素：独占一行 h1-h6 p div 列表…

行内元素：不独占一行 span a img strong

> 注：行内元素可以被包含在 块级元素中，反之，则不可以

### 5.1文字属性

| 属性名      | 取值                       | 描述         |
| ----------- | -------------------------- | ------------ |
| font-size   | 数值                       | 设置字体大小 |
| font-family | 默体、宋体、楷体等         | 设置字体样式 |
| font-style  | normal正常；italic斜体；   | 设置斜体样式 |
| font-weight | 100 〜900数值;bold;bolder; | 粗体样式     |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo5 文字属性</title>
		
		<style type="text/css">
			/*class选择器{属性名：值}*/
			.d{font-size: 25px; font-family:隶书; font-style: italic; font-weight: bold;}
		</style>
		
	</head>
	<body>
		<div id="d" class="d">
			加油
		</div>
	</body>
</html>
```

### 5.2文本属性

| 属性名          | 取值                                                   | 描述                 |
| --------------- | ------------------------------------------------------ | -------------------- |
| color           | 十六逬制;表示颜色的英文单词；                          | 设置文本颜色         |
| text-indent     | 5px缩进5像素;20%缩进父容器宽度的百分之二十；           | 缩进元素中文本的首行 |
| text-decoration | none;underline;overline;blink;                         | 文本的装饰线         |
| text-align      | left;right;center                                      | 文本水平对齐方式     |
| word-spacing    | normal;固定值；                                        | 单词之间的间隔       |
| line-height     | normal;固定值；                                        | 设置文本的行高       |
| text-shadow     | 四个取值依次是：水平偏移；垂直偏移；模糊值；阴影颜色； | 设置阴影及模糊效果   |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo6 文本属性</title>
		
		<style type="text/css">
			/*id选择器*/
			#div{width: 400px;height:400px;border:solid 1px #000;
				color:rgb(100,200,255);
				text-indent: 10px; /*缩进*/
				text-decoration: line-through; /*文本的装饰线*/
				text-align: center; /*文本水平对齐方式*/ 
				word-spacing: 50px; /*单词之间的间隔*/
				line-height: 30px; /*设置文本的行高*/
			}
			#div2{width: 400px;height:40px;border:solid 1px #000;
				line-height: 40px; /*行高*/
				text-shadow: 10px 5px 2px #f00 ;/*阴影*/
			}
		</style>
		
	</head>
	<body>
		<div id="div">
			hello world
			hello world
			hello world
			hello world
			hello world
			hello world<br>
			
			hello world<br>
			
			hello world
		</div>
		<div id="div2">hello world</div>
	</body>
</html>
```

### 5.3背景属性

| 属性名              | 取值                                | 描述                   |
| ------------------- | ----------------------------------- | ---------------------- |
| background-color    | 16进制;用于表示颜色的英语单词；     | 设置背景色             |
| background-image    | url(图片地址)                       | 设置背景图片           |
| background-repeat   | repeat-y;repeat-x;repeat;no-repeat; | 设置背景图的平铺方向   |
| background-position | top;bottom;left;right; center;      | 改变图像在背景中的位置 |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo7 背景属性</title>
		<style type="text/css">
			body{
				background-color: lightgray;
				background-image: url(bk.png);
				background-repeat: no-repeat; /*平铺*/
				background-size: 1000px 500px;
			}
		</style>
	</head>
	<body>
	</body>
</html>
```

### 5.4列表属性

| 属性名              | 取值           | 描述                             |
| ------------------- | -------------- | -------------------------------- |
| list-style-type     | disc 等        | 改变列表的标识类型               |
| list-style-image    | url(图片地址)  | 用图像表示标识                   |
| list-style-position | inside;outside | 标识出现在列表项内容之外还是内部 |

### 5.5尺寸属性（一定要加单位）

- width:设置元素的宽度
- height:设置元素的高度

### 5.6显示属性

- 显示属性display,以下是常用取值:
  - none：不显示
  - block:块级显示
  - inline:行级昆示

代码:

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo8 列表属性</title>
		<style type="text/css">
			#ol{list-style-type: circle; list-style-image: url(bk.png); list-style-position: outside;}
			
			#size{width:100px; height: 50px; border: solid 1px #f00;}
			
			.d{width:100px; height: 50px; border: solid 1px #00f;
			display: inline-block;}
		</style>
	</head>
	<body>
		<div class="d">111</div>
		<div class="d">222222</div>
		<div class="d">33333333</div>
		<hr>
		<div id="size"></div>
		<hr>
		<ol id="ol">
			<li>hello</li>
			<li>hello</li>
			<li>hello</li>	
			<li>hello</li>
			<li>hello</li>
			<li>hello</li>
		</ol>
	</body>
</html>
```

### 5.7轮廓属性

- 绘制于元素周围的一条线，位于边框边緣的外围，可起到突出元素的作用。常用属性:

| 属性名        | 取值                                       | 描述           |
| ------------- | ------------------------------------------ | -------------- |
| outline-style | solid (实线)/dotted (虚线)/dashed(虚线 )等 | 设置轮廓的样式 |
| outline-color | 16进制;用于表示颜色的英文                  | 设置轮廓的颜色 |
| outline-width | 数值                                       | 设置轮廓的宽度 |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo9 轮廓属性</title>
		
		<style type="text/css">
			div{
				width:200px;
				height: 200px;
				border:solid 1px #f00;
				/*outline-style: dotted;
				outline-color: #00f;
				outline-width: 3px;*/
				outline: dotted 3px #0f0;
				outline-offset: 5px; /*偏移*/
			}
		</style>
	</head>
	<body>
		<div>边框外围轮廓</div>
	</body>
</html>
```

### 5.8浮动属性float

- 浮动的框可以向左或向右移动，直到它的外边缘碰到包含框或另一个浮动框的边框为止。
- 由于浮动框不在文档的普通流中，所以文档的普通流中的块框表现得就像浮动框不存在一样。
- 请看下图，当把框1向右浮动时，它脱离文档流并且向右移动，直到它的右边缘碰到包含框的右边缘：

![在这里插入图片描述](https://img-blog.csdnimg.cn/2020080209444860.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- 再请看下图，当框1向左浮动时，它脱离文档流并且向左移动，直到它的左边缘碰到包含框的左边缘。因为它不再处于文档流 中，所以它不占据空间，实际上覆盖住了框2,使框2从视图中消失。
- 如果把所有三个框都向左移动，那么框1向左浮动直到碰到包含框，另外两个框向左浮动直到碰到前一个浮动框。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200802094427280.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)



- 如下图所示，如果包含框太窄，无法容纳水平排列的三个浮动元素，那么其它浮动块向下移动，直到有足够的空间。如果浮动元素的 高度不同，那么当它们向下移动时可能被其它浮动元素“卡住”：

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200802094415831.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- clear属性：规定元素的哪一侧不允许其他浮动元素。它的取值如下:

| 取值    | 描述                                |
| ------- | ----------------------------------- |
| left    | 在左侧不允许浮动元素。              |
| right   | 在右侧不允许浮动元素。              |
| both    | 在左右两侧均不允许浮动元素。        |
| none    | 默认值。允许浮动元素出现在两侧。    |
| inherit | 规定应该从父元素继承clear属性的值。 |

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo10 浮动属性</title>
		
		<style type="text/css">
			.dd{width:100px; height:100px;}
			.d1{ background-color: #f00; height:120px; float: left;}
			.d2{ background-color: #0f0; float: left;}
			.d3{ background-color: #00f; float: left;}
		</style>
	</head>
	<body>
		<!--<div style="height:110px"><div class="dd d1"></div></div>-->
		<div style=" width:299px;">
			<div class="dd d1"></div>
			<div class="dd d2"></div>
			<div class="dd d3"></div>	
		</div>
		
	</body>
</html>
```

### 5.9定位属性

- 相对定位(relative):元素框偏移某个距离，元素仍保持其未定位前的形状，它原本所占的空间仍保留。
- 示例代码：

```html
<head>
    <style type="text/css">
        h2 .pos_left{
            position:relative;/*相对定位*/
            left:-20px;
        }
        h2 .pos_right{
            position:relative;
            left:20px;
        }
    </style>
</head>
<body>
    <h2> 这是位于正常位置的标题 </h2>
    <h2 class="pos_left"> 这个标题相对于其正常位置向左移动 </h2>
    <h2 class="pos_right"> 这个标题相对于其正常位置向右移动</h2>
    <p> 相对定位会按照元素的原始位置对该元素进行移动。</p>
    <p>样式"left:-20px"从元素的原始左侧位置减去20像素。</p>
    <p>样式"left:20px"向元素的原始左侧位置增加20像素。</p>
</body>			
```

- 绝对定位(absolute):元素框从文档流完全删除，并相对于其包含块逬行定位。包含块可能是文档中的另一个元素或者是初始包含块。 元素原先在正常文档流中所占的空间会关闭，就好像元素原来不存在一样。元素定位后生成一个块级框。(脱离原来的文档流)

```html
<html>
	<head>
		<meta charset="utf-8" />
		<style type="text/css">
            h2 .pos_abs {
                position: absolute; /*绝对定位*/
                left: 1G0px; 
                top: 150px
			}
            
        </style>
    </head>
    <body>
        <h2 class="pos_abs ">这是带有绝对定位的标题</h2>
        <p>
            通过绝对定位，元素可以放置到页面上的任何位置。下面的标题距离页面左侧100px，距离页面顶部				150px。
        </p>
    </body>
```

- 固定定位(fixed)：元素框的表现类似于将 position 设置为absolute ,不过其包含块是视窗本身。

```html
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<style type="text/css">
            #left {
                width: 200px;
                height: 200px;
                background-color: red;
                position: fixed ;
                1eft: Opx;
                bottom: 0px;
            }
            #right {
                width: 200px;
                height: 200px;
                background-color: green;
                position: fixed;
                right: 0px;
                bottom: 0px;
            }
            #middle{
                width: 200px;
                height: 200px;
                background-color: blue;
                position: fixed ;
                left: 0px;
                bottom: 50%;
            }
        </sty1e>
	</head>
    <body>
        <div id="left">左 下</div>
        <div id="right">右 下</div>
        <div id=" middle" >中间</div>
    </body>
< /html>
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo11 定位属性</title>
		
		<style type="text/css">
			/*绝对定位 absolute  */
			/* #abs{width:500px; height:400px; border:solid 1px #f00; position: absolute; top: 10px; left: 10px;}*/
			/*固定定位 fixed  */
			#abs{width:500px; height:400px; border:solid 1px #f00; position: fixed; top: 10px; left: 10px;}
			/*相对定位  relative*/
			.rel{width:50px;height:50px; border:#f00 solid 5px; position: relative;}
			.r1{top:190px;}
		</style>
	</head>
	<body>
		<div id="abs">
			<div class="rel r1">r1</div>
			<div class="rel r2">r2</div>
			<div class="rel r3">r3</div>
		</div>
		
		<div style=" width: 50px; height: 50px; background-color: #ccc;"></div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	</body>
</html>
```



### 5.10父级边框塌陷问题

clear

```css
/*
clear：right；右侧不允许有浮动元素
clear：left； 左侧不允许有浮动元素
clear：both； 两侧不允许有浮动元素
clear：none； 
 */
.layer04{
    border: 1px #666 dashed;
    font-size: 12px;
    line-height: 23px;
    display: inline-block;
    float: right;
    clear: left;
}
```

解决方案

1、增加父级元素的高度

```css
#father{
    border: 1px #000 solid;
    height: 800px;
}
```

2、增加一个空的div（class=“clear”）标签，清除浮动

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="css/style.css" type="text/css">
</head>
<body>
<div id="father">
    <div class="layer01"><img src="images/1.png" alt=""></div>
    <div class="layer02"><img src="images/2.png" alt=""></div>
    <div class="layer03"><img src="images/3.png" alt=""></div>
    <div class="layer04">
        浮动的盒子可以向左浮动，也可以向右浮动，知道它的外边缘碰到包含或另一个浮动盒子为止
    </div>
    <div class="clear"></div>
</div>
</body>
</html>
12345678910111213141516171819
div{
    margin: 10px;
    padding: 5px;
}
#father{
    border: 1px #000 solid;
    height: 800px;
}
.layer01{
    border: 1px #F00 dashed;
    display: inline-block;
    float: left;/*向左浮动*/
}
.layer02{
    border: 1px #00F dashed;
    display: inline-block;
    float: left;
}
.layer03{
    border: 1px #060 dashed;
    display: inline-block;
    float: right;
}
/*
clear：right；右侧不允许有浮动元素
clear：left； 左侧不允许有浮动元素
clear：both； 两侧不允许有浮动元素
clear：none；
 */
.layer04{
    border: 1px #666 dashed;
    font-size: 12px;
    line-height: 23px;
    display: inline-block;
    float: right;
    clear: left;
}
.clear{
    clear: both;
    margin: 0;
    padding: 0;
}

```

3、overflow

```
在父级元素中增加一个	
	overflow: hidden;
	overflow: scroll;
123
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <style>
        #content{
            width: 200px;
            height: 150px;
            overflow: scroll;
        }
    </style>
</head>
<body>

<div id="content">
    <img src="images/1.png" alt="">
    <p>
        某雌性生物醉倒在草地上，路人对其上下其手，并在草地上翻滚，一番折腾后某雌性生物迷迷糊糊醒来步履蹒跚地离开了
    </p>
</div>
</body>
</html>
```

4、父类添加一个伪类：after

```
#father:after{
    content: '';
    display: block;
    clear: both;
}
12345
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
    	div{
            margin: 10px;
            padding: 5px;
        }
        #father{
            border: 1px #000 solid;
        }
        #father:after{
            content: '';
            display: block;
            clear: both;
        }
        .layer01{
            border: 1px #F00 dashed;
            display: inline-block;
            float: left;/*向左浮动*/
        }
        .layer02{
            border: 1px #00F dashed;
            display: inline-block;
            float: left;
        }
        .layer03{
            border: 1px #060 dashed;
            display: inline-block;
            float: right;
        }
        /*
        clear：right；右侧不允许有浮动元素
        clear：left； 左侧不允许有浮动元素
        clear：both； 两侧不允许有浮动元素
        clear：none；
         */
        .layer04{
            border: 1px #666 dashed;
            font-size: 12px;
            line-height: 23px;
            display: inline-block;
            float: right;
        }
    </style>
</head>
<body>
<div id="father">
    <div class="layer01"><img src="../lesson06/images/1.png" alt=""></div>
    <div class="layer02"><img src="images/2.png" alt=""></div>
    <div class="layer03"><img src="images/3.png" alt=""></div>
    <div class="layer04">
        浮动的盒子可以向左浮动，也可以向右浮动，知道它的外边缘碰到包含或另一个浮动盒子为止
    </div>
    <div class="clear"></div>
</div>
</body>
</html>
```

**小结：**

1. 浮动元后面增加空div

   简单，代码中尽量避免空div

2. 设置父元素的高度

   简单，元素假设有了固定的高度，就会被限制

3. overflow

   简单，下拉的一些场景避免使用

4. 父类添加一个伪类：after

   写法稍微复杂一点，但是没有副作用，**推荐使用！**

   ### 5.11.z-index

   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200516235239580.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM2MjU5MTQz,size_16,color_FFFFFF,t_70)

   z-index：默认时0，最高无限：999

   ```html
   <!DOCTYPE html>
   <html lang="en">
   <head>
       <meta charset="UTF-8">
       <title>Title</title>
       <style>
           #content{
               width: 333px;
               padding: 0px;
               margin: 0px;
               overflow: hidden;
               font-size: 12px;
               line-height: 25px;
               border: 1px #000 solid;
   
           }
           ul,li{
               padding: 0px;
               margin: 0px;
               list-style-type: none;
           }
           /*父级元素相对定位*/
           #content ul{
               position: relative;
           }
           .tipText,.tipBg{
               position: absolute;
               width: 333px;
               height: 25px;
               top: 320px;
           }
           .tipText{
               color: white;
               /*z-index: 0;*/
           }
           .tipBg{
               background: black;
               opacity: 0.5;   /*背景透明度*/
           }
       </style>
   </head>
   <body>
   
   <ul id="content">
       <li><img src="images/bg.png" alt=""></li>
       <li class="tipText">学习微服务，找狂神</li>
       <li class="tipBg"></li>
       <li>时间：2099-01-01</li>
       <li>地点：月球一号基地</li>
   </ul>
   </body>
   </html>
   ```

### 5.12.display与float对比

- **display**：方向不可以控制
- **float**：浮动起来会脱离标准文档流，所以要解决父级边框塌陷的问题

## 六、CSS盒子模型

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200802094342261.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

### 6.1边框相关属性

| 属性名       | 取值                          | 描述           |
| ------------ | ----------------------------- | -------------- |
| border-style | solid;double;dashed;dotted 等 | 设置边框的样式 |
| border-color | 16进制;用于表示颜色的英文；   | 设置边框的颜色 |
| border-width | 数值                          | 设置边框的粗   |

### 6.2外边距相关属性

- margin：外间距，边框和边框外层的元素的距离

| 属性名        | 取值                  | 描述           |
| ------------- | --------------------- | -------------- |
| margin        | top;right;bottom;left | 四个方向的距离 |
| margin-top    | 数值                  | 上间距         |
| margin-bottom | 数值                  | 下间距         |
| margin-left   | 数值                  | 左间距         |
| margin-right  | 数值                  | 右间距         |

### 6.3内边距相关属性

- padding：内间距，元素内容和边框之间的距离((top right bottom left))

属性值 描述

| padding-left   |      |
| -------------- | ---- |
| padding-right  |      |
| padding-top    |      |
| padding-bottom |      |

代码 :

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo12 CSS盒模型</title>
		
		<style type="text/css">
			.d1{width:400px; height: 400px; background-color: #f0f;}
			.d2{width:180px; height: 200px; border: solid 1px #f00; margin-left: 50px; margin-top: 50px; padding-left: 20px;}
		</style>
	</head>
	<body>
		<div class="d1">
			<div class="d2">hello world</div>
			<div class="d3"></div>
			<div class="d4"></div>
			<div class="d5"></div>
		</div>
	</body>
</html>
```

### 6.4圆角边框

4个角

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--
    左上 右上 右下 左下，顺时针方向
    -->
    <!--
        圆圈： 圆角 = 半径
    -->
    <style>
        div{
            width: 100px;
            height: 100px;
            border: 10px solid red;
            border-radius: 100px;
        }
    </style>
</head>
<body>
<div>

</div>
</body>
</html>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        #div1{
            width: 100px;
            height: 100px;
            border: 10px solid red;
            border-radius: 100px;
        }
        #div2{
            width: 100px;
            height: 50px;
            border: 10px solid red;
            border-radius: 100px 100px 0 0;
        }
        #div3{
            width: 50px;
            height: 50px;
            border: 10px solid red;
            border-radius: 100px 0 0 0;
        }
        img{
            border-radius: 100px;
        }
    </style>
</head>
<body>
<div id="div1"></div>
<div id="div2"></div>
<div id="div3"></div>

<img src="images/tx.jpg" alt="">
</body>
</html>

```

### 6.5.盒子阴影

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--margin：0 auto; 居中
    要求：块元素，块元素有固定宽度-->
    <style>
        img{
            border-radius: 50px;
            box-shadow: 10px 10px 100px yellow;
        }
    </style>
</head>
<body>
<div>
    <div style="width: 500px;display: block;text-align: center ">
        <div>
            <img src="images/tx.jpg" alt="">
        </div>
    </div>
</div>
</body>
</html>
```

## 七、CSS3扩展属性

### 7.1 border-radius创建圆角矩形

- 示例: border-radius: 25px;

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200802094308447.jpg)



### 7.2 box-shadow:用于向方框添加阴影

- 示例: box-shadow: 10px 10px 5px #88888;

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200802094251280.jpg)



### 7.3 动画

 自我了解

