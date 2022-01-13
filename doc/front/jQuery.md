## 一、引言

### 1.1 jQuery 概述

- jQuery是一个快速、简洁的JavaScript代码库。
- jQuery设计的宗旨是“Write Less, Do More”，即倡导写更少的代码，倣更多的事情。
- 它封装JavaScript常用的功能代码，提供一种简便的JavaScript操作方式，优化HTML文档操作、事件处理、动画设计和Ajax交互。

### 1.2 jQuery 特点

- 具有独特的链式语法。

- 支持高效灵活的CSS选择器。

- 拥有丰富的插件。

- 兼容各种主流浏览器，如IE6.0+、FF 1.5+、Safari 2.0+、Opera 9.0+等。

- | jQuery库包含以下功能 |
  | -------------------- |
  | HTML元素选择         |
  | HTML元素操作         |
  | CSS操作              |
  | HTML事件函数         |
  | JavaScript特效和动画 |
  | HTML DOM遍历和修改   |
  | AJAX                 |
  | Utilities            |

### 1.3为什么要用jQuery

- 目前网络上有大量开源的JS框架，但是jQuery是目前最流行的JS框架，而且提供了大量的扩展。很多大公司都在使用jQuery,
- 例如：Google、Microsoft、IBM、Netflix

## 二、jQuery安装

### 2.1直接引用jQuery

- 从jQuery.com官网或从GitHub下载合适版本（此处使用1.12.4版本，兼容性更好，最新版本为3.x>，放入服务器的合适目录中， 在页面中直接引用。
- 有两个版本的jQuery可供下载：
  - Production version-用于实际的网站中，已被精简和压缩。
  - Development version-用于测试和开发（未压缩，便于可读）。

jQuery 库是一个 JavaScript 文件，使用 HTML 的 < script src="">< /script> 标签引用

```html
<head>
	<script src="jquery-1.12.2.min.js"></script>
</head>
```

### 2.2CDN引用

#### 2.2.1**什么是**CDN?

- CDN的全称是Content Delivery Network,即**内容分发网络**，使用户就近获取所需内容，降低网络拥塞，提高用户访问响应速度和命中率。

#### 2.2.2 **常见** CDN

- 百度CDN

```html
<head>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
    </script>
</head>
```

- 新浪CDN

```html
<head>
    <script src="http://ib.sinaapp.com/js/jquery/2.0.2/jquery-2.0.2.min.js">
    </script>
</head>
1234
```

- Google CDN

```html
<head>
    <script src= "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery 			.min.js">
    </script>
</head>
```

- Microsoft CDN

```html
<head>
    <script src="http://ajax.htmlnetcdn.com/ajax/jQuery/jquery-1.10.2.min.js">
    </script>
</head>
```

代码:

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo1 jquery导入库</title>
	</head>
	<body>
		<div id="dd"></div>
		
		<!--注意js文件的路径-->
			<script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>

		<!--
			<script type="text/javascript" src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
	    -->
		
		<script type="text/javascript">
			var d=$("div");
			alert(d);
		</script>	
	</body>
</html>
```

## 三、jQuery语法【重点】

- Query 语法是通过选取HTML元素，并对选取的元素执行某些操作。

- 基础语法: **$(selector).action()**

- 美元符号定义jQuery

- 选择符(selector) “查询"和"查找" HTML元素

- jQuery 的action()执行对元素的操作

  实例:

  - $(this).hide() -隐藏当前元素
  - $(“p”).hide() -隐藏所有< P>元素
  - $(“p.test”).hide()-隐藏所有class="test"的< p>元素
  - $("#test").hide() -隐藏所有id="test"的元素

```js
//js获取对象
document.getElementById("#dd");

//jquery id选择器
$("#dd");
```

### 3.1基本使用

- $(匿名函数）：表示页面DOM加载完毕，则执行，比onload事件执行早，并且可以写多个。
- $是jQuery函数的简写

```html
<html>
    <head>
    <script src="jquery-1.12.2.min.js"></script>
    < /head>
</html>
<body>
    <script>
        $( function(){
        	alert("欢迎使用jQuery1");
        });
        $( function(){
       	 	alert("欢迎使用jQuery2");
        });
    </script>
</body>
```

- $(selector).action():通过选取HTML元素，井对选取的元素执行某些操作。
  - 选择符（selector)表示"查找"HTML元素
  - jQuery的action()执行对元素的操作

```html
-$(this).hide()-隐藏当前元素
-$("p").hide()-隐藏所有<p>元素
-$("p.test").hide()-隐藏所有 class="test"的 <p> 元素
-$("#test").hide()-隐藏所有 id="test"的元素 

<html>
    <head>
    <script src="jquery-1.12.2.min.js"></script>
    </head>
</html>
<body>
    <p>窗前明月光</p>
    <p>疑是地上霜</p>
    <p>举头望明月</p>
    <p>低头思故乡</p>
    <script>
        $("p").hide();
        $("p.test").hide();
        $("#test").hide();
    </script>
</body>
```

### 3.2 jQuery选择器

#### 元素选择器：

- jQuery元素选择器基于元素名选取元素
- 示例：在页面中选取所有元素

```js
$(document).ready(function(){
	$("button").click(function(){ 
    	$("p").hide();
    });                   
});
```

#### id选择器：

- jQuery #id选择器通过HTML元素的id属性选取指定的元素。
- 页面中元素的id应该是唯一的，所以您要在页面中选取唯一的元素需要通过#id选择器。
- 通过id选取元素语法如下

```js
$(document).ready(function(){
	$("button").click(function(){ 
        $("#test").hide();
      });  
});
```

#### class选择器：

- jQuery类选择器可以通过指定的class查找元素。
- 语法如下：

```js
$(document).ready(function(){
	$("button").click(function(){ 
        $(".test").hide();
     });
});
```

### 3.3 jQuery事件及常用事件方法

#### 什么是事件？

- 页面对不同访问者的响应叫做事件。
- 事件处理程序指的是当HTML中发生某些事件时所调用的方法。
- 实例：
  - 在元素上移动鼠标。
  - 选取单选按纽
- 点击元素：在事件中经常使用术语”触发”（或"激发"）例如：“当您按下按键时触发keypress事件”。
- 常见DOM事件:

| 鼠标事件   | 键盘事件 | 表单事件 | 文档/窗口事件 |
| ---------- | -------- | -------- | ------------- |
| click      | keypress | submit   | load          |
| dblclick   | keydown  | change   | resize        |
| mouseenter | key up   | focus    | scroll        |
| mouseleave |          | blur     | unload        |

#### jQuery事件方法语法:

- 在jQuery中，大多数DOM事件都有一个等效的jQuery方法。
- 页面中指定一个点击事件：

```js
$("p").click();
```

- 下一步是定义什么时间触发事件。您可以通过一个事件函数实现：

```js
$("p").click(function(){
	//动作触发后执行的代码！！
});
```

- 总结：也就是说，不传参数是点击，传参数是设置事件。

#### 常用的jQuery事件方法

##### $(document).ready()

- $(document).ready()方法允许我们在文档完全加载完后执行函数。该事件方法在 《jQuery选择器》中已经提到过。

##### click()：

- 当按钮点击事件被触发时会调用一个函数。
- 该函数在用户点击HTML元素时执行。

在下面的实例中，当点击事件在某个 元素上触发时，隐藏当前的 元素：

```js
$("p" ).click(function(){
	$(this).hide();
});
```

##### dbclick()：

- 当双击元素时，会发生dbclick事件。

- dbclick()方法触发dbclick事件，或规定当发生dbldick事件时运行的函数:

```js
$("p").dblclick(function(){ 
  $(this).hide();
});
```

##### mouseenter()：

- 当鼠标指针穿过元素时，会发生mouseenter事件。
- mouseenter()方法触发mouseenter事件，或规定当发生mouseenter事件时运行的函数:

```js
$("#p1").mouseenter(function(){
	alert("您的鼠标移到了 id="p1"的元素上！");
});
```

##### mouseleave()：

- 当鼠标指针离开元素时，会发生mouseleave事件。
- mouseleave()方法触发mouseleave事件，或规定当发生mouseleave事件时运行的函数:

```js
$("#p1").mouseleave(function(){
	alert("再见，您的鼠标离开了该段落.");
});
```

##### mousedown()：

- 当鼠标指针移动到元素上方，并按下鼠标按键时，会发生mousedown事件。
- mousedown()方法触发mousedown事件，或规定当发生mousedown事件时运行的函数：

```js
$('#p1").mousedown(function(){
 	alert("鼠标在该段落上按下！");
});
```

##### mouseup()：

- 当在元素上松开鼠标按钮时，会发生mouseup事件。
- mouseup()方法触发mouseup事件，或规定当发生mouseup事件时运行的函数：

```js
$("#p1").mouseup(function(){
     alert("鼠标在段落上松开。");
});
```

##### hover()：

- hover()方法用于模拟光标悬停事件。
- 当鼠标移动到元素上时，会触发指定的第一个函数(mouseenter);当鼠标移出这个元素吋，会触发指定的第二个函数 (mouseleave)。

```js
%("#p1").hover(
    function(){
		alter("你进入了P1");
	},function(){
   	 	alter("你离开了P1");
	}
);
```

##### focus()：

- 当元素获得焦点时，发生focus事件。
- 当通过鼠标点击选中元素或通过tab键定位到元素时，该元素就会获得焦点。fooisO方法触发focus事件，或规定当发生focus 事件时运行的函数:

```js
$("input").focus(function(){
	$( this).css("background-color","#cccccc"); 
});
```

##### blur()：

- 当元素失去焦点时，发生blur事件。
- blur()方法触发blur事件，或规定当发生blur事件时运行的函数:

```js
$("input").blur(function(){
	$( this).css("background-color","#ffffff"); 
});
```

## 四、jQuery效果

### 4.1隐藏显示

#### hide():

- 可以使用hide()将元素隐藏

```js
$("#hide").click(function(){
	$("p").hide();
});
```

#### show():

- 您可以使用show()将元素显示

```js
$("#show").click(function(){
	$("p").show();
})；
```

#### toggle():

- 通过jQuery，您可以使用toggle()方法来切换hide()和show()方法。
- 显示被隐藏的元素，并隐藏已显示的元素。

```js
$("button").click(function(){
    $("p").toggle();
)};
```

- 事实上，这三种方法都是有两个参数的:

```js
$(selector).hide(speed,callback);
$(selector).show(speed,callback);
$(selector).toggle(speed,callback);
```

- 可选的speed参数规定隐藏/显示的速度，可以取以下值：“slow”、"fast” 或毫秒。
- 可选的callback参数是隐藏或显示完成后所执行的函数名称。

### 4.2淡入淡出

- 通过jQuery,您可以实现元素的淡入淡出效果。
- jQuery拥有下面四种fade方法：
  - fadeln()
  - fadeOut()
  - fadeToggle()
  - fadeTo()

#### jQuery fadeln()方法：

- jQuery fadeIn()用于淡入已隐藏的元素。

```js
$(selector).fadeln(speed,callback);
```

- 可选的speed参数规定效果的时长。它可以取以下值："slow”、"fast"或毫秒。
- 可选的callback参数是fading完成后所执行的函数名称。
- 下面的例子演示了带有不同参数的fadelnO方法：

```js
${"button").click(function(){
	$("#div1").fadeln();
	$("#div2").fadeIn("slow");
	$("#div3").fadeln(3000);
});
```

#### jQuery fadeOut()方法：

- jQuery fadeOut()方法用于淡出可见元素。

```js
$(selector).fadeOut(speed,callback);
```

- 可选的speed参数规定效果的时长。它可以取以下值：“slow”、"fast"或毫秒。
- 可选的callback参数是fading完成后所执行的函数名称。
- 下面的例子演示了带有不同参数的fadeOutO方法：

```js
$("button").click(function(){
    $("#div1").fadeout();
	$("#div2n").fadeOut("slow"); 
    $("#div3").fadeQut(3000); 
});
```

#### jQuery fadeToggle()方法:

- jQuery fadeToggle()方法可以在fadeln() 与 fadeOut()方法之间进行切换。
- 如果元素已淡出，则fadeToggle()会向元素添加淡入效果。
- 如果元素已淡入，则fadeToggle()会向元素添加淡出效果。

```js
$(selector).fadeToggle(speed,callback);
```

- 可选的speed参数规定效果的时长。它可以取以下值："slow”、"fast"或毫秒。
- 可选的callback参数是fading完成后所执行的函数名称。
- 下面的例子演示了带有不同参数的fadeToggleO方法：

```js
$("button").click(function(){
    $("#div1").fadeToggle();
    $("#div2").fadeToggle(slow);
    $("#div3").fadeToggle(3000);
});
```

#### jQueryfadeTo()方法:

- jQueryfadeTo()方法允许渐变为给定的不透明度 (值介于0与1之间）。

```js
$(selector).fadeTo(speed,opacity,callback);
```

- 必需的speed参数规定效果的时长。它可以取以下值："slow”、"fast"或毫秒。
- fadeTo()方法中必需的opacity参数将淡入淡出效果设置为给定的不透明度（值介于0与1之间）。
- 可选的callback参数是该函数完成后所执行的函数名称。
- 下面的例子演示了带有不同参数的fadeT〇()方法：

```js
$("button").click(function(){
    $("#div1").fadeTo("slow",0.15);
    $("#div2").fadeTo("slow",0.04);
    $("#div3").fadeTo("slow",0.7);
});
```

### 4.3滑动

- 通过jQuery,您可以在元素上创建滑动效果。jQuery拥有以下滑动方法:
  - slideDown()
  - slideUp()
  - slideToggle()

#### jQuery slideDown():

- jQuery slideDown()方法用于向下滑动元素。

```js
$(selector).slideDown(speed,callback);
```

- 可选的speed参数规定效果的时长。它可以取以下值："slow”、"fast”或毫秒。
- 可选的callback参数是滑动完成后所执行的函数名称。
- 下面的例子演示了 slideDown()方法：

```js
$("#flip").click(function(){
	$("#panel").slideDown();
});
```

#### jQuery slideUp():

- jQuery slideUp()方法用于向上滑动元素。

```js
$(selector).slideUp(speed,callback);
```

- 可选的speed参数规定效果的时长。它可以取以下值：“slow”、"fast"或毫秒。
- 可选的callback参数是滑动完成后所执行的函数名称。
- 下面的例子演示了 slideUpO方法：

```js
$("#flip").click(function(){
	$("#panel").slideUp();
});
```

#### jQuery slideToggle():

- jQuery slideToggle()方法可以在slideDown()与slideUpO方法之间进行切换。
- 如果元素向下滑动,则slideToggle()可向上滑动它们。
- 如果元素向上滑动，则slideToggle()可向下滑动它们。

```js
$(selector).slideToggle(speed,callback);
```

- 可选的speed参数规定效果的时长。它可以取以下值："slow”、"fast”或毫秒。
- 可选的callback参数是滑动完成后所执行的函数名称。
- 下面的例子演示了slideToggle()方法：

```js
$("#flip").click(function(){
	$("#panel").slideToggle();
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo2 jquery效果1</title>
		
		<style type="text/css">
			#div1,#div2,#div3{width: 200px;height: 100px; border: 1px solid #f00;}
		</style>
		
	</head>
	<body>	 
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn1").click(function(){
					$("#div1").hide("slow",function(){
						alert("隐藏了");					
					});
				});
				$("#btn2").click(function(){
					$("#div1").show();
				});
				$("#btn3").click(function(){
					$("#div1").toggle();
				});
				$("#btn4").click(function(){
					$("#div2").fadeIn(1000);
					$("#div2").fadeto(1000,0.5);
				});
				$("#btn5").click(function(){
					$("#div2").fadeOut(1000,function(){
						alert("淡出了");
					});
				});
				$("#btn6").click(function(){
					$("#div2").fadeToggle(1000);
				});
				$("#btn7").click(function(){
					$("#div3").slideUp();
				});
				$("#btn8").click(function(){
					$("#div3").slideDown();
				});
				$("#btn9").click(function(){
					$("#div3").slideToggle();
				});
			});
		</script>
		<button id="btn1">隐藏</button> <button id="btn2">显示</button><button id="btn3">隐藏/显示</button>
		<div id="div1"></div> 
		
		<button id="btn4">淡入</button> <button id="btn5">淡出</button><button id="btn6">淡入/淡出</button>
		<div id="div2"></div>
		
		<button id="btn7">UP</button> <button id="btn8">DOWN</button><button id="btn9">UP/DOWN</button>
		<div id="div3"></div>
	</body>
</html>
```

### 4.4动画

#### animate()方法：

- jQuery animate()方法用于创建自定义动画。

```js
$(selector).animate({params},speed,callback);
```

- 必需的params参数定义形成动画的CSS属性。
- 可选的speed参数规定效果的时长。它可以取以下值："slow”、"fast”或毫秒。
- 可选的callback参数是动画完成后所执行的函数名称。
- 下面的例子演示animate()方法的简单应用。它把元素往右边移动了 250像素：

```js
$("button").click(function(){
	$("div1").animate( {left:'250px'}); 
});
```

- 操作多个属性
- 请注意，生成动画的过程中可同时使用多个属性:

```js
$("button").click(function(){
	$("div").animate({ 
        left: '250px', 
        opacity:'0.5',
        height:'150px',
        width:'150px'
	});
});
```

- 可以用animate()方法来操作所有CSS属性吗？
- 是的，几乎可以！不过，需要记住一件重要的事情：当使用animate())时，必须使用Camel标记法书写所有的属性名，比如，必须使用 paddingLeft 而不是 padding-left，使用 marginRight 而不是 margin-right,等等。
- 同时，色彩动画并不包含在核心jQuery库中。
- 如果需要生成颜色动画，您需要从jquery.com下载颜色动画插件。
- 也可以定义相对值（该值相对于元素的当前值）。需要在值的前面加上+=或

```js
$("button").click(function(){
	$("div").animate({ 
        left: '250px',
        height:'+=150px'，
        width:'+=150px',
    });
});
```

- 预定义的值：您甚至可以把属性的动画值设置为"show"、“hide”或"toggle”:

```js
$("button").click(function(){
	$("div").animate({ 
        height:'toggle'，
    });
});
```

- 使用队列功能：默认地，jQuery提供针对动画的队列功能。
- 这意味着如果您在彼此之后编写多个animate()调用，jQuery会创建包含这些方法调用的"内部”队列。然后逐一运行这些animate 调用。

```js
$("button").click(function(){ 
    var div=$("div");
    div.animate({height:'300px',opacity:'0.4'},"slow"); 								div.animate({width:'300px',opacity:'0.8'},"slow"); 	             	     			div.animate({height:'100px',opacity:'0.4'},"slow");
    div.animate({width:'100px',opacity:'0.8'},"slow"); 		
});
```

- 下面的例子把元素往右边移动了 100像素，然后增加文本的字号:

```js
$( button").click(function(){ 
  	var div=$("div");
	div.animate({left:'1100px'},"slow");
  	div.animate({fontSize:'3em'}, 'slow');
});
```

### 4.5停止动画

#### stop()方法

- jQuerystop()方法用于停止动画或效果，在它们完成之前。
- stop()方法适用于所有jQuery效果函数，包括滑动、淡入淡出和自定义动画。

```js
$(selector).stop(stopAll,goToEnd);
```

- 可选的stopAll参数规定是否应该清除动画队列。默认是false,即仅停止活动的动画，允许任何排入队列的动画向后执行。
- 可选的goToEnd参数规定是否立即完成当前动画。默认是false。
- 因此，默认地，stop())会清除在被选元素上指定的当前动画。
- 下面的例子演示stop()方法，不带参数：

```js
$("#stop").click(function(){
	$("#panel").stop();
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo3 jquery动画</title>
		<style type="text/css">
			.div{width:300px; height: 100px; border:solid 5px #f00;}
		</style>
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn1").click(function(){
					$(".div").animate({
						width:'150px',
						height:'+=50px',
						borderWidth:'+=10px'
					},3000);
				});
				$("#btn2").click(function(){
					$(".div").stop();
				});
			});
		</script>
		
		<button id="btn1">启动动画</button><button id="btn2">stop</button>
		<div id="div" class="div"></div>
	</body>
</html>
```

### 4.6 Callback

- 许多jQuery函数涉及动画。这些函数也许会将speed或作为可选参数。
- 例子：$(“p”).hide(“slow”);
- speed*或 duration 参数可以设置许多不同的值，比如"slow", “fast”, "normal"或毫秒。

```js
$("button").click(function(){
	$("p").hide("slow",function(){
        alert("段落现在被隐藏了");
    });
 });
```

- 以下实例没有回调函数，警告框会在隐藏效果完成前弹出：

```js
$("button").click(function(){
	$("p").hide(1000);
 });
```

### 4.7链式编程

- 直到现在，我们都是一次写一条jQuery语句（一条接着另一条）。
- 不过，有一种名为链接（chaining)的技术，允许我们在相同的元素上运行多条jQuery命令，一条接着另一条。
- 提示：这样的话，浏览器就不必多次查找相同的元素。
- 如需链接一个动作，您只需简单地把该动作追加到之前的动作上。
- 下面的例子把css()、slideUp()和slideDown()链接在一起. "p1”元素首先会变为红色，然后向上滑动，再然后向下滑动：

```js
$("#pl").css( "color","red").slideUp(2000).slideDown(2000);
```

- 如果需要，我们也可以添加多个方法调用。
- 提示：当进行链接时，代码行会变得很差。不过，jQuery语法不是很严格；您可以按照希望的格式来写，包含换行和缩进。
- 如下书写也可以很好地运行：

```js
$("#p1").css( "color","red")
.slicfeUp(2000)
.slideDown(2000);
```

## 五、jQueryDOM操作【重点】

### 5.1捕获内容

- jQuery拥有可操作HTML元素和属性的强大方法。
- jQuery中非常重要的部分，就是操作DOM的能力。
- jQuery提供一系列与DOM相关的方法，这使访问和操作元素和属性变得很容易。
- 三个简单实用的用于DOM操作的jQuery方法：
  - text() -设置或返回所选元素的文本内容
  - html() -设置或返回所选元素的内容（包括H TML标记）
  - val() -设置或返回表单字段的值
- 下面的例子演示如何通过jQuery text() 和 html()方法来获得内容：

```js
$("#btn1").click(function(){
	alert("Text:"+ $("#test").text());
});

$("#btn2").click(function(){
	alert("HTML:"+ $("#test").html());
});

$("#btn3").click(function(){
	alert("值为:"+ $("#test").val());
}); 
```

- 获取属性-attr()
- jQuery attr() 方法用于获取属性值。
- 下面的例子演示如何获得链接中href属性的值:

```js
$("button").click(function(){ 
    alert( $("#a1").attr("href"));
});
```

### 5.2设置内容

- 我们将使用前一章中的三个相同的方法来设置内容：
  - text()-设置或返回所选元素的文本内容 。
  - html() -设置或返回所选元素的内容（包括HTML标记）
  - val() -设置或返回表单字段的值
- 下面的例子演示如何通过text()、html(）以及val()方法来设置内容:

```js
$("#btn1").click(function(){
	$("#test1").text("Hello world!');
});

${"#btn2").click(function(){
	$("#test2").html("<b>Helloworld!</b>");
});

$("#btn3").click(function(){
	$("#test3").val( "Hello world!");
});
```

- 上面的三个jQuery方法：text()、html()以及val(),同样拥有回调函数。回调函数有两个参数：被选元素列表中当前元素的下标， 以及原始（旧的）值。然后以函数新值返回您希望使用的字符串。
- 下面的例子演示带有回调函数的text()和html():

```js
$("#btn1").click(function(){
    $("#test1").text(function(i,origText){
        return "旧文本" + origText + "新文本： Hello world!(index:"+ i +") ";
    });
});
$("#btn2").click(function(){
    $("#test2").html(function(i,origText){
        return "旧html" + origText + "新html： Hello <b>world!</b> (index:"+ i +") ";
    });
});
```

- jQuery attr()方法也用于设置/改变属性值。
- 下面的例子演示如何改变（设置）文本中color属性的值:

```js
$("button").click(function(){
    $("#font1").attr("color","red");
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo4 jQueryDOM操作-捕获设置内容</title>
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn").click(function(){
					var s1=$("#s1").text();
					var d1=$("#d1").html();
					var ip1=$("#ip1").val();
					alert(s1+"==="+d1+"==="+ip1);
					
					$("#s1").text("new span");
					$("#d1").html("new div");
					$("#ip1").val("content");
				});
				
				$("#btn1").click(function(){
					var url=$("a").attr("href");
					window.location.href=url;
				});
				
				$("#btn2").click(function(){
					$("a").attr("href","text.html");
				});
			})
		</script>
		
		<!--获得元素内容-->
		<span id="s1">span inner</span>
		<div id="d1">div inner</div>
		<input id="ip1" value="input val">
		<button id="btn">获得元素内容</button>
		<hr>
		<a href="http://www.baidu.com">百度</a>
		<button id="btn1">跳转</button>
		<button id="btn2">更改地址</button>
	</body>
</html>
```

### 5.3添加元素

- 我们将学习用于添加新内容的四个jQuery方法：
  - append() -在被选元素的结尾插入内容 。
  - prepend() -在被选元素的开头插入内容 。
  - after() - 在被选元素之后插入内容 。
  - before() -在被选元素之前插入内容

#### jQuery append()方法

- 在被选元素的结尾插入内容。

```js
$("p").append("追加文本");
```

#### jQuery prepend()方法

- 在被选元素的开头插入内容。

```js
$("p").prepend("追加文本");
```

- 在上面的例子中，我们只在被选元素的开头/结尾插入文本/HTML。
- 不过，append（）和prepend（）方法能够通过参数接收无限数量的新元素。可以通过jQuery来生成文本/HTML (就像上面的例子那 样），或者通过JavaScript代码和DOM元素0
- 在下面的例子中，我们创建若干个新元素。这些元素可以通过text/HTML、jQuery或者JavaScript/DOM来创建。然后我们通过 append()方法把这些新元素追加到文本中（对prepend()同样有效）：

```js
function appendText()
    var txt1="<p>文本。 </p>";            //使用HTML标签创建文本
    var txt2=$("<p></p>").text("文本。"); // 使用jQuery创建文本
    var txt3=document.createElement("p");
    txt3. innerHTML="文本。";              // 使用DOM创建文本 text with DOM
    $("body").append(txt1, txt2, txt3);  //追加新元素
}
```

#### jQuery after〇方法

- 在被选元素之后插入内容。

#### jQuery beforef()

- 方法在被选元素之前插入内容。

```js
$("img").after("在后面添加文本");
$ ("img").before ("在前面添加文本");
```

- after()和before()方法能够通过参数接收无限数量的新元素。可以通过text/HTML、jQuery或者JavaScript/DOM来创建新元素.
- 在下面的例子中，我们创建若干新元素。这些元素可以通过text/HTML、jQuery或者JavaScript/DOM来创建。然后我们通过 after〇方法把这些新元素插到文本中（对beforeO同样有效）：

```js
function afterText()
    var txt1="<b> I </b>";               //使用HTML标签创建元素
    var txt2=$("<i></i>").text("love");  // 使用jQuery创建元素
    var txt3=document.createElement("big");
    txt3. innerHTML="JQuery";              // 使用DOM创建元素
    $("ing").after(txt1, txt2, txt3);     //在图片后面添加文本
}
```

### 5.4删除元素

- 如需删除元素和内容，一般可使用以下两个jQuery方法:
  - remove（）-删除被选元素（及其子元素）
  - empty（）-从被选元素中删除子元素

```js
$("#div1").remove();
$("#divl").empty();
```

- jQuery remove()方法也可接受一个参数，允许您对被删元素进行过滤。
- 该参数可以是任何jQuery选择器的语法。
- 下面的例子删除class="italic"的所有元素：

```js
$("p").remove(".italic");
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo5 jQueryDOM操作-添加删除元素</title>
		<style type="text/css">
			#append,#prepend,#after,#before{background-color: #ff0;}
		</style>
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#append").append("<p style='background-color:#f00;'>测试append</p>");
				
				$("#prepend").prepend("<p style='background-color:#f00;'>测试prepend</p>");
				
				$("#after").after("<p style='background-color:#f00;'>测试after</p>");
				
				$("#before").before("<p style='background-color:#f00;'>测试before</p>");
				
				$("#btn").click(function(){
					$("p").remove("#append");
				});
				$("#btn2").click(function(){
					$("#prepend").empty();
				});
			})
		</script>
		<p id="append">段落append</p>	
		<p id="prepend">段落prepend</p>
		<p id="after">段落after</p>
		<p id="before">段落before</p>
		
		<hr>	
		<button id="btn">删除</button>
		<button id="btn2">删除2</button>		
	</body>
</html>
```

### 5.5 CSS类

- jQuery 拥有若干进行CSS操作的方法。我们将学习下面这些：
  - addClass()-向被选元素添加一个或多个类
  - removeClass() -从被选元素删除一个或多 个类
  - toggleClass() -对被选元素进行添加/删除类的切换操作
  - css()-设置或返回样式属性
- 下面的样式表将用于本页的所有例子:

```css
.important{
    font-weight:bold; 
    font-size:xx-large; 
}

.blue{
   color:blue; 
}
```

- 下面的例子展示如何向不同的元素添加class属性。当然，在添加类时，也可以选取多个元素:

```js
$("button").click(function(){
	$("h1,h2,p").addClass("blue");
	$("div").addClass("important");
});
```

- 也可以在addClass()方法中规定多个类：

```js
$("button").click(function(){
	$("body div:first").addClass("important blue"); 
});
```

- 下面的例子演示如何在不同的元素中删除指定的class属性：

```js
$("button").click(function(){
	$("hi,h2,p").removeClass("blue");
});
```

- 下面的例子将展示如何使用jQuery toggleClass()方法。该方法对被选元素进行添加/删除类的切换操作:

```js
$("button").click(function(){
	$('h1,h2,p").toggleClass("blue");
});
```

### 5.6 css()方法

- css()方法设置或返回被选元素的一个或多个样式属性。
- 如需返回指定的CSS属性的值，请使用如下语法：

```js
css("propertyname");
```

- 下面的例子将返回首个匹配元素的background-color值：

```js
$("p").css("background-color");
```

- 如需设置指定的css属性，请使用如下语法：

```js
css("propertyname","value");
```

- 下面的例子将为所有匹配元素设置background-color值：

```js
$("p").css("background-color","yellow");
```

- 如需设置多个CSS属性，请使用如下语法：

```js
css({"propertyname":"value","propertyname":"value",...});
```

- 下面的例子将为所有匹配元素设置background-color和font-size:

```js
$("p").css({"background-colo":"yellow" ,"font-size":"200%"});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo6 jQueryDOM操作CSS类</title>
		<link style="text/css" rel="stylesheet" href="css/bootstrap.min.css" />
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#btn1").click(function(){
					$("#res").addClass("btn btn-success");
				});
				
				$("#btn2").click(function(){
					$("#res").addClass("btn btn-danger");
				});
				
				$("#btn3").click(function(){
					$("#res").toggleClass("btn btn-success");
				});
				
				$("#btn4").click(function(){
					$("#res").removeClass("btn btn-success");
				});
				
				$("#div").css("border","solid 5px #f00").css("width","300px")
				.css("height","100px");
				
				alert($("#div").css("width"));
			})
		</script>
		<button id="btn1">成功</button><button id="btn2">失败</button>
		<button id="btn3">成功/失败</button><button id="btn4">删除</button>
		<hr>
		<button id="res">结果</button>
		<hr>
		
		<div id="div">样式添加结果</div>
	</body>
</html>
```

### 5.7尺寸

- jQuery提供多个处理尺寸的重要方法：
  - width()
  - height()
  - innerWidth()
  - innerHeight()
  - outerWidth()
  - outerHeight()

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200804195750167.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70)

- width()方法设置或返回元素的宽度（不包括内边距、边框或外边距）。
- height()方法设置或返回元素的高度（不包括内边距、边框或外边距）。
- 下面的例子返回指定的< div>元素的宽度和高度：

```js
$("button").click(function(){
    var txt="";
    txt+= "div的宽度是:" + $("#div1").width() + "</br>";
    txt+= "div的高度是:" + $("#div1").height();
    $("#div1").html(txt);
});
```

- innerWidth() 方法返回元素的宽度( 包括内边距)
- innerHeight() 方法返回元素的高度( 包括内边距)
- 下面的例子返回指定的< div>元素的inner-width/height:

```js
$("button").click(function(){ 
    var txt="";
	txt+= "div 宽度，包含内边距：" + $("#div1").innerWidth() + "</br>";
	txt+= "div 高度，包含内边距：" + $("#div1").innerHeight(); 
    $("#div1").html(txt);
});
```

- outerWidth()方法返回元素的宽度（包括内边距和边框）。
- outerHeight()方法返回元素的高度（包括内边距和边框）。
- 下面的例子返回指定的< div>元素的outer-width/height:

```js
$("button").click(function(){ 
 	var txt="";
	txt+= "div 宽度，包含内边距和边框：" + $("#div1").outWidth() + "</br>";
	txt+= "div 高度，包含内边距和边框：" + $("#div1").outHeight(); 
    $("#div1").html(txt);
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo7 jQueryDOM操作-尺寸</title>
		<style type="text/css">
			#d1{width:200px; height: 200px; border:solid 10px #f00;
			 padding-left: 100px; padding-top: 100px; margin-left: 100px; margin-top: 100px;}
			#d2{width:100px; height: 100px;  background-color: #f00;}
		</style>
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			//width innerWidth  outerWidth 
			$(function(){
				var w=$("#d1").width();
				var iw=$("#d1").innerWidth();
				var ow=$("#d1").outerWidth();
				var ow2=$("#d1").outerWidth(true);
				$("#res").html("width:"+w+"===innerWidth:"+iw+"===outerWidth:"+ow+"===outerWidth2:"+ow2);
				//alert(ow2);
			});
		</script>
		<div id="d1">		
			<div id="d2"></div>
		</div>
		<div id="res"></div>
	</body>
</html>
```

## 六、jQuery遍历

### 6.1遍历

- jQuery遍历，意为"移动"，用于根据其相对于其他元素的关系来”查找"（或选取）HTML元素。以某项选择开始，并沿着这个选择移动，直到抵达您期望的元素为止。
- 下图展示了一个家族树。通过jQuery遍历，您能够从被选（当前的）元素开始，轻松地在家族树中向上移动（袓先），向下移动 (子孙），水平移动（同胞）。这种移动被称为对DOM进行遍历。

### 6.2 祖先 jQuery parent()方法

- parent()方法返回被选元素的直接父元素。
- 该方法只会向上一级对DOM树进行遍历。
- 下面的例子返回每个span元素的直接父元素:

```js
$(document).ready(function(){
	$("span").parent();
});
```

parents()方法返回被选元素的所有祖先元素，它一路向上直到文档的根元素(html)。

下面的例子返回所有span元素的所有祖先：

```js
$(document).ready(function(){ 
    $("span").parents();
});
```

- 也可以使用可选参数来过滤对祖先元素的搜索。
- 下面的例子返回所有span元素的所有祖先，并且它是ul元素:

```js
$(document).ready(function(){ 
    $("span").parents("ul");
});
```

- parentsUntil()方法返回介于两个给定元素之间的所有祖先元素。
- 下面的例子返回介于span与div元素之间的所有祖先元素：

```js
$(document).ready(function(){
	$("span").parentsUntil("div"); 
});
```

### 6-3 后代 jQuery children()方法

- children()方法返回被选元素的所有直接子元素。
- 该方法只会向下一级对DOM树进行遍历。
- 下面的例子返回每个div元素的所有直接子元素:

```js
$(document).ready(function(){
	$("div").children();
});
```

- 也可以使用可选参数来过滤对子元素的搜索。
- 下面的例子返回类名为"a”的所有p元素，并且它们是div的直接子元素:

```js
$(document).ready(function(){
	$("div").children("p.a");
});
```

- find()方法返回被选元素的后代元素，一路向下直到最后一个后代。
- 下面的例子返回属于div后代的所有span元素：

```js
$(document).ready(function(){
	$("div").find("span");
});
```

- 下面的例子返回div的所有后代：

```js
$(document).ready(function() {
	$("h2").find("*");
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo8 jQuery遍历1</title>
		<style type="text/css">
			#d1{width:300px; height: 300px; border: solid 10px #f00;}
			#d21{width:300px; height: 100px; background-color: #ff0;}
			#d22{width:300px; height: 100px; background-color: #00f;}
			#d23{width:300px; height: 100px; background-color: #0f0;}
			#d3{width:100px; height: 100px; background-color: #999; margin-left: 100px;}
		</style>
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				//$("#d3").parent().css("backgroundColor","#111");
				//$("#d3").parents("#d1").css("border","dotted 3px #f00");
				//$("#d3").parentsUntil("#d1").css("border","dotted 3px #f00");
				//$("#d1").children().css("border","solid 5px #ccc");;
				//$("#d1").find("*").css("border","solid 5px #ccc");;
				$("#d1").find("#d22").css("border","solid 5px #ccc");;
			})
		</script>
		<div id="d1">
			<div id="d21">
				<div id="d3"></div>
			</div>
			
			<div id="d22"></div>
			<div id="d23"></div>
		</div>
	</body>
</html>
```

### 6.4 同胞 jQuery siblings() 方法

- siblings()方法返回被选元素的所有同胞元素。
- 下面的例子返回h2的所有同胞元素：

```js
$(document).ready(function(){
	$("h2").siblings();
});
```

- 也可以使用可选参数来过滤对同胞元素的搜索。
- 下面的例子返回属于h2的同胞元素的所有p元素：

```js
$ (document). ready(function() {
	$("h2").siblings("p");
});
```

- next()方法返回被选元素的下一个同胞元素。
- 该方法只返回一个元素。
- 下面的例子返回h2的下一个同胞元素：

```js
$(document).ready(function(){
	$("h2").next();
});
```

- nextAll()方法返回被选元素的所有跟随的同胞元素。
- 下面的例子返回h2的所有跟随的同胞元素：

```js
$(document).ready(function(){
$("h2").nextAll();
});
```

- nextUntil() 方法返回介于两个给定参数之间的所有跟随的同胞元素。
- 下面的例子返回介于h2与h6元素之间的所有同胞元素：

```js
$(document).ready(function(){
	$("h2").nextUntil("h6");
});
```

- prev()方法取得一个包含匹配的元素集合中每一个元素紧邻的前一个同辈元素的元素集合
- 下面的例子返回h2的下一个同胞元素

```js
$ (document).ready(function() {
	$("h2").prev();
});
```

- prevAll()方法查找当前元素之前所有的同辈元素
  - prevUntil()方法查找当前元素之前所有的同辈元素，直到遇到匹配的那个元素为止

### 6.5 过滤 jQuery first() 方法

- first()方法返回被选元素的首个元素。
- 下面的例子选取首个div元素内部的第一个p元素：

```js
$(document).ready(function(){
	$("div p").first();
});
```

- last()方法返回被选元素的最后一个元素。
- 下面的例子选择最后一个div元素中的最后一个p元素：

```js
$(document).ready(function(){
	$("div p").last();
});
```

- eq()方法返回被选元素中带有指定索引号的元素。
- 索引号从0开始，因此首个元素的索引号是0而不是1。
- 下面的例子选取第二个P元素（索引号1):

```js
$(document).ready(function(){
    $("P").eq(1);
});
```

- filter()方法允许您规定一个标准。不匹配这个标准的元素会被从集合中删除，匹配的元素会被返回。
- 下面的例子返回带有类名 "url” 的所有p元素：

```js
$(document).ready(function(){ 
    $("p").filter(".url");
});
```

- not()方法返回不匹配标准的所有元素。
- 提示：not()方法与filter()相反。
- 下面的例子返回不带有类名"url”的所有P元素:

```js
$(document).ready(function() {
    $("p").not(".url");
});
```

代码：

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>demo9 jQuery遍历2</title>
		<style type="text/css">
			#d{width: 500px; height: 500px; border: 5px solid #f00;}
			#d1{width:500px; height:100px; background-color: #ff0;}
			#d2{width:500px; height:100px; background-color: #0f0;}
			#d3{width:500px; height:100px; background-color: #00f;}
			#d4{width:500px; height:100px; background-color: #0ff;}
			#d5{width:500px; height:100px; background-color: #ccc;}
		</style>
	</head>
	<body>
		<script type="text/javascript" src="jquery-3.4.1.min.js"></script>
		<script type="text/javascript">
			$(function(){
				//$("#d2").siblings().html("siblings");
				//$("#d2").next().html("next");
				//$("#d2").nextAll().html("nextAll");
				//$("#d2").nextUntil("#d5").html("nextUntil");
				//$("#d2").prev().html("prev");
				//$("#d5").prevAll().html("prevAll");
				//$("#d5").prevUntil("#d3").html("preUntil");
				//$("#d").children().first().html("first");
				//$("#d").children().last().html("last");
				//$("#d").children().eq(1).html("second");
				//$("#d").children().filter("#d3").html("d3");
				$("#d").children().not("#d3").html("not d3");
			})
		</script>
		<div id="d">
			<div id="d1"></div>
			<div id="d2"></div>
			<div id="d3"></div>
			<div id="d4"></div>
			<div id="d5"></div>
		</div>
	</body>
</html>
```

## 七、jQueryAJAX

### 7.1jQueryAJAX 简介

- AJAX =异步 JavaScript 和 XML (Asynchronous JavaScript and XML)。
- 简短地说，在不重载整个网页的情况下，AJAX通过后台加载数据，并在网页上进行显示。
- 使用AJAX的应用程序案例：谷歌地图、腾讯微博、优酷视频、人人网等等。

### 7.2 get和post方法

- $.get() 方法通过HTTP GET请求从服务器上请求数据。

```js
$.get(URL,callback);
```

- 必需的 URL 参数规定您希望请求的URL。
- 可选的 callback 参数是请求成功后所执行的函数名。
- 下面的例子使用$.get()方法从服务器上的一个文件中取回数据：

```js
$("button").click(function(){
	$.get("demo.test.php",function(data){
        alert("数据："+ data );
	});
});
```

- $.post()方法通过HTTP POST请求从服务器上请求数据。
  - $.post( URL,data,callback );
  - 必需的 URL 参数规定您希望请求的URL。
  - 可选的 data 参数规定连同请求发送的数据。
  - 可选的 callback 参数是请求成功后所执行的函数名。
  - 下面的例子使用$.post()连同请求一起发送数据：

```js
$("button").click( function(){
    $.post("/try/ajax/demo_test.post.jsp",
    {
        name:"百度",
        url:"http://www.baidu.com
     },
        function(data){
        alert("数据: \n" + data );
    });
});
```

### 7.3 ajax()方法

- jQuery底层AJAX实现。简单易用的高层实现见 $. get, $.post等。
- $.ajax()返回其创建的XMLHttpRequest对象。
- 大多数情况下你无需直接操作该函数，除非你需要操作不常用的选项，以获得更多的灵活性。
- 最简单的情况下，$.ajax()可以不带任何参数直接使用

代码：

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style type="text/css">
        #time,#res{
            width:300px;
            height:100px;
            border:1px solid #f00;
        }
    </style>
</head>
<body>
    <script type="text/javascript" src="jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var time=new Date();
            $("#time").html(time.getHours()+":"+time.getMinutes()+":"+time.getSeconds());
            $("#btn").click(function () {
                $.get("get",function(data){
                    $("#res").html(data);
                });
            });
        })
    </script>
    <button id="btn">get请求</button>
    <div id="time"></div>
    <div id="res"></div>
</body>
</html>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style type="text/css">
        #time,#res{
            width:300px;
            height:100px;
            border:1px solid #f00;
        }
    </style>
</head>
<body>
    <script type="text/javascript" src="jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var time=new Date();
            $("#time").html(time.getHours()+":"+time.getMinutes()+":"+time.getSeconds());
            $("#btn").click(function () {
                $.post("post",{name:'zs',age:25},function(data){
                    $("#res").html(data.res);
                }," json");
            });
        })
    </script>
    <button id="btn">post请求</button>
    <div id="time"></div>
    <div id="res"></div>
</body>
</html>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style type="text/css">
        #time, #res {
            width: 300px;
            height: 100px;
            border: 1px solid #f00;
        }
    </style>
</head>
<body>
    <script type="text/javascript" src="jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            var time = new Date();
            $("#time").html(time.getHours() + ":" + time.getMinutes() + ":" + time.getSeconds());
            $("#btn").click(function () {
                $.ajax(
                    {
                        url: "post",  //路径
                        data: {name: 'lisi', age: '30'}, //传过去的数据
                        type: "post", //表明是post请求
                        success: function (data) { //回调函数
                            $("#res").html(data.res);
                        },
                        dataType:"json" //告知 返回的数据类型
                    }
                )
            });
        })
    </script>

    <button id="btn">ajax方法</button>
    <div id="time"></div>
    <div id="res"></div>
</body>
</html>

package com.ziming.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/get")
public class GetTest  extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("hello ajax");
    }
}


package com.ziming.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/post")
public class PostTest extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        System.out.println("name="+name+";age = " + age);
        PrintWriter writer = resp.getWriter();
        //writer.write("hello ajax!!!");
        String json="{\"res\":\"success\"}";
        writer.write(json);
    }
}
```

## 八、其他

### 8.1 jQuery noConflict 方法

- 正如您已经了解到的，jQuery使用 $ 符号作为jQuery的简写。
- 如果其他JavaScript框架也使用$符号作为简写怎么办？
- 其他一些 JavaScript 框架包括：MooTools、Backbone、Sammy、Cappuccino、Knockout、JavaScript MV/C、Google Web Toolkit、 Google Closure、Ember、Batman 以及 Ext JS。
- 其中某些框架也使用$符号作为简写（就像jQuery)，如果您在用的两种不同的框架正在使用相同的简写符号，有可能导致脚本停止运行。
- jQuery的团队考虑到了这个问题，并实现了 noConflict()方法。
- noConflictO方法会释放对$标识符的控制，这样其他脚本就可以使用它了。
- 当然，您仍然可以通过全名替代简写的方式来使用jQuery:

```js
$.noConflict();
jQuery(document).ready(function(){
    jQuery("button").click(function(){ 
        jQuery("p").text( "jQuery 仍然在工作！");
	});
});
```

- 也可以创建自己的简写。noConflict() 可返回对jQuery的引用，可以把它存入变量，以供稍后使用。
- 看这个例子:

```js
var jq = $.noConflict(); 
jq(document).ready(function(){
    jq("button").click(function(){
		jq("p").text ("jQuery 仍然在工作！");
	});
});
```

- 如果你的JQuery代码块使用简写，并且您不愿意改变这个快捷方式，那么你可以把符号作为变量传递给ready方法。这样就可以在函数内使 用$符号了
- 而在函数外，依旧不得不使用"jQuery” ：

```js
$.noConflict();
jQuery(document).ready(function($){
	$("button").click(function(){
		$("p").text("jQuery 仍然在工作");
    });
});
```