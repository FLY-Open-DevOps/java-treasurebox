# Vue概述：![](https://cn.vuejs.org/images/logo.png)

 Vue (读音/vju/, 类似于view)是一套用于构建用户界面的**渐进式框架**，发布于2014年2月。与其它大型框架不同的是，Vue被设计为可以自底向上逐层应用。**Vue的核心库只关注视图层**，不仅易于上手，还便于与第三方库(如: **vue-router: 跳转，vue-resource: 通信，vuex:管理**)或既有项目整合。

## 前端知识体系

 想要成为真正的“互联网Java全栈工程师”还有很长的一段路要走，其中“我大前端”是绕不开的一门必修课。本阶段课程的主要目的就是带领我Java后台程序员认识前端、了解前端、掌握前端,为实现成为“互联网Java全栈工程师”再向前迈进一步。

# 前端三要素

●HTML (结构) :超文本标记语言(Hyper Text Markup Language) ，决定网页的结构和内容
●CSS (表现) :层叠样式表(Cascading Style sheets) ，设定网页的表现样式
●JavaScript (行为) :是一种弱类型脚本语言，其源代码不需经过编译，而是由浏览器解释运行,用于控制网页的行为

## 结构层（HTML）

略

## 表现层（CSS）

 CSS层叠样式表是**一门标记语言**,并不是编程语言，因此不可以自定义变量，不可以引用等，换句话说就是不具备任何语法支持，它主要缺陷如下:
●语法不够强大，比如无法嵌套书写，导致模块化开发中需要写很多重复的选择器;
●没有变量和合理的样式复用机制，使得逻辑上相关的属性值必须以字面量的形式重复输出，导致难以维护;
这就导致了我们在工作中无端增加了许多工作量。为了解决这个问题，前端开发人员会使用一种称之为[**CSS预处理器**]的工具，提供CSS缺失的样式层复用机制、减少冗余代码，提高样式代码的可维护性。大大提高了前端在样式上的开发效率。（例如页面在不同的时候有不同的需求，淘宝在双11和618的样式就会不一样）

## CSS预处理器

 CSS预处理器定义了一种新的语言，其基本思想是，用一种专门的**编程语言**，为CSS增加了一些编程的特性，将CSS作为目标生成文件,然后开发者就只要使用这种语言进行CSS的编码工作。转化成通俗易懂的话来说就是“**用一种专门的编程语言,进行Web页面样式设计，再通过编译器转化为正常的CSS文件，以供项目使用”**
常用的CSS预处理器有哪些
●**SASS**:基于Ruby,通过服务端处理，功能强大。解析效率高。需要学习Ruby语言，上手难度高于LESS。
●**LESS**:基于NodeJS,通过客户端处理，使用简单。功能比SASS简单，解析效率也低于SASS,但在实际开发中足够了，所以我们后台人员如果需要的话，**建议使用LESS。**

## 行为层（JavaScript）

 JavaScript一门弱类型脚本语言，其源代码在发往客户端运行之前不需经过编译，而是将文本格式的字符代码发送给浏览器由浏览器解释运行。
 Native原生JS开发
 原生JS开发，也就是让我们按照[ECMAScript] 标准的开发方式，简称是ES,特点是所有浏览器都支持。截止到当前博客发布时间，ES 标准已发布如下版本:
●ES3
●ES4 (内部,未征式发布)
**●ES5 (全浏览器支持)**
**●ES6 (常用，当前主流版本: webpack打包成为ES5支持! )**
●ES7
●ES8
●ES9 (草案阶段)
区别就是逐步增加新特性。

# TypeScript

 TypeScript是一种由微软开发的自由和开源的编程语言。它是JavaScript的一个超集，而且本质上向这个语言添加了可选的静态类型和基于类的面向对象编程。由安德斯海尔斯伯格(C#、Delphi、TypeScript 之父; .NET 创立者)主导。
 该语言的特点就是除了具备ES的特性之外还纳入了许多不在标准范围内的新特性，所以会导致很多浏览器不能直接支持TypeScript语法，需要编译后(编译成JS)才能被浏览器正确执行。

# JavaScript框架

●**jQuery**: 大家熟知的JavaScript框架，优点是简化了DOM操作，缺点是**DOM操作太频繁**,影响前端性能;在前端眼里使用它仅仅是为了兼容IE6、7、8;
●**Angular**: Google收购的前端框架，由一群Java程序员开发，其特点是将后台的MVC模式搬到了前端并增加了**模块化开发的理念**，与微软合作，采用TypeScript语法开发;对后台程序员友好，对前端程序员不太友好;最大的缺点是版本迭代不合理(如: 1代-> 2代，除了名字，基本就是两个东西;截止发表博客时已推出了Angular6)
●**React**: Facebook出品，一款高性能的JS前端框架;特点是提出了新概念[**虚拟DOM**]用于
**减少真实DOM操作**，在内存中模拟DOM操作，有效的提升了前端渲染效率;缺点是使用复
杂，因为需要额外学习一门[JSX] 语言;
●**Vue**:一款渐进式JavaScript框架，所谓渐进式就是逐步实现新特性的意思，如实现模块化开发、路由、状态管理等新特性。**其特点是综合了Angular (模块化)和React (虚拟DOM)的优点;**
●**Axios** :前端通信框架;因为Vue 的边界很明确，就是为了处理DOM,所以并不具备通信能
力，此时就需要额外使用一个通信框架与服务器交互;当然也可以直接选择使用jQuery提供的AJAX通信功能;
前端三大框架：**Angular**、**React**、**Vue**

# UI框架

●Ant-Design:阿里巴巴出品，基于React的UI框架
**●ElementUI、 iview、 ice: 饿了么出品，基于Vue的UI框架**
●Bootstrap: Twitter推出的一个用于前端开发的开源工具包
●AmazeUI:又叫"妹子UI"，一款HTML5跨屏前端框架.
JavaScript 构建工具
●Babel: JS编译工具，主要用于浏览器不支持的ES新特性，比如用于编译TypeScript
●WebPack: 模块打包器，主要作用是打包、压缩、合并及按序加载
注：以上知识点将WebApp开发所需技能全部梳理完毕

# 三端合一

## 混合开发（Hybid App）

 主要目的是实现一套代码三端统一(PC、Android:.apk、iOS:.ipa )并能备够调用到底层件(如:传感器、GPS、 摄像头等)，打包方式主要有以下两种:
●云打包: **HBuild -> HBuildX， DCloud出品; API Cloud**
●本地打包: **Cordova** (前身是PhoneGap)
后端技术
 前端人员为了方便开发也需要掌握一定的后端技术， 但我们Java后台人员知道后台知识体系极其庞大复杂，所以为了方便前端人员开发后台应用，就出现了NodeJS这样的技术。
 NodeJS的作者已经声称放弃NodeJS (说是架构做的不好再加上笨重的node_ modules，可能让作者不爽了吧)，开始开发全新架构的Deno
 既然是后台技术，那肯定也需要框架和项目管理工具，NodeJS 框架及项目管理工具如下:
●Express: NodeJS框架
●Koa: Express简化版
●NPM:项目综合管理工具，类似于Maven
●YARN: NPM的替代方案，类似于Maven和Gradle的关系

# 第一个Vue程序

## Vue

 Vue (读音/vju/, 类似于view)是一套用于**构建用户界面的渐进式框架**，发布于2014年2月。与其它大型框架不同的是，Vue被设计为可以自底向上逐层应用。Vue的核心库只关注视图层，不仅易于上手，还便于与第三方库(如: vue-router, vue-resource, vuex)或既有项目整合。

## MVVM模式的实现者

●Model:模型层，在这里表示JavaScript对象
●View:视图层,在这里表示DOM (HTML操作的元素)
●ViewModel:连接视图和数据的中间件，Vue.js就是MVVM中的ViewModel层的实现者
在MVVM架构中，是不允许数据和视图直接通信的，只能通过ViewModel来通信，而
ViewModel就是定义了一个Observer观察者
●ViewModel能够观察到数据的变化，并对视图对应的内容进行更新
●ViewModel能够监听到视图的变化，并能够通知数据发生改变
**至此，我们就明白了，Vue.js 就是一个MVVM的实现者，他的核心就是实现了DOM监听与数据绑定**

## 为什么要使用Vue.js

●轻量级，体积小是一个重要指标。Vue.js 压缩后有只有20多kb (Angular 压缩后56kb+ ,
React压缩后44kb+ )
●移动优先。更适合移动端，比如移动端的Touch事件
●易上手，学习曲线平稳,文档齐全
**●吸取了Angular (模块化)和React (虚拟DOM)的长处，并拥有自己独特的功能，如:计算属性**
●开源，社区活跃度高

代码 demo1.html 初入门之绑定数据

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    {{message}}
</div>
<script>
     let vm = new Vue({
        el: "#app",
        data: {
            message: "hello,vue"
        }
    });
</script>
</body>
</html>
```

## 什么是MVVM?

MVVM (Model-View-ViewModel) 是一种软件架构设计模式，由微软WPF (用于替代
WinForm，以前就是用这个技术开发桌面应用程序的)和Silverlight (类似于Java Applet,简单点说就是在浏览器上运行的WPF)的架构师Ken Cooper和Ted Peters 开发，是一种简化用户界面的**事件驱动编程方式**。由John Gossman (同样也是WPF和Silverlight的架构师)于2005年在他的博客上发表。
MVVM源自于经典的MVC (ModI-View-Controller) 模式。**MVVM的核心是ViewModel**
层，**负责转换Model中的数据对象来让数据变得更容易管理和使用**，其作用如下:
**●该层向上与视图层进行双向数据绑定
●向下与Model层通过接口请求进行数据交互**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200619091407278.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图1 MVVM图解

## 为什么要使用MVVM

MVVM模式和MVC模式一样，主要目的是**分离视图(View)\**和\**模型(Model)**,有几大好处
●低耦合:视图(View)可以独立于Model变化和修改,一个ViewModel可以绑定到不同的
View上，当View变化的时候Model可以不变，当Model变化的时候View也可以不变。
●可复用:你可以把一些视图逻辑放在一个ViewModel里面，让很多View重用这段视图逻辑。
●独立开发:开发人员可以专注于业务逻辑和数据的开发(ViewModel),设计人员可以专注于页面设计。
●可测试:界面素来是比较难于测试的，而现在测试可以针对ViewModel来写。

VUE基础语法学习
代码 demo2.html if else 语法

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    <h1 v-if="ok">Yes</h1>
    <h1 v-else>No</h1>
</div>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            ok: true
        }
    });
</script>
</body>
</html>
```

代码 demo03.html for循环获取数据

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>

<div id="app">
    <li v-for="text in allTexts">
        {{text.message}}
    </li>
</div>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            allTexts: [
                {message: "1"},
                {message: "2"},
                {message: "3"},
            ]
        }
    });
</script>
</body>
</html>
```

代码demo04 事件绑定

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    <button v-on:click="sayHi()">点我</button>
</div>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            message: "cqh"
        },
        methods: {
            sayHi: function () {
                alert(this.message);
            }
        }
    });
</script>
</body>
</html>
```

## 什么是双向数据绑定

 **Vue.js是一个MVVM框架，即数据双向绑定,即当数据发生变化的时候,视图也就发生变化，当视图发生变化的时候，数据也会跟着同步变化。这也算是Vue.js的精髓之处了。**
 值得注意的是，我们所说的数据双向绑定，一定是对于UI控件来说的，非UI控件不会涉及到数据双向绑定。单向数据绑定是使用状态管理工具的前提。如果我们使用vuex，那么数据流也是单项的，这时就会和双向数据绑定有冲突。

## 为什么要实现数据的双向绑定

 在Vue.js 中，如果使用vuex ，实际上数据还是单向的，之所以说是数据双向绑定，这是用的UI控件来说，对于我们处理表单，Vue.js的双向数据绑定用起来就特别舒服了。即两者并不互斥，在全局性数据流使用单项,方便跟踪;局部性数据流使用双向，简单易操作。

## 在表单中使用双向数据绑定

 你可以用v-model 指令在表单 、 及 元素上创建双向数据绑定。它会根据控件类型自动选取正确的方法来更新元素。尽管有些神奇，但v-model本质上不过是语法糖。它负责监听户的输入事件以更新数据，并对一些极端场景进行一些特殊处理。
代码 demo05 数据双向绑定示例 实现之后当输入框输入相应文字 在后面提示框会输入相同文字

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    数据双向绑定示例：<input type="text" v-model="message">{{message}}
</div>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            message: ""
        },
    });
</script>
</body>
</html>
```

# 第一个Vue组件

## 什么是组件

 组件是可复用的Vue实例，说白了就是一组可以重复使用的模板，跟JSTL的自定义标签、Thymeleaf的th:fragment 等框架有着异曲同工之妙。通常一个应用会以一棵嵌套的组件树的形式来组织:
 注意:在实际开发中，我们并不会用以下方式开发组件，而是采用vue-cli创建.vue模板文件的方式开发，以下方法只是为了让大家理解什么是组件。
Vue.component()方法注册组件
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200619091459336.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图2 组件树

代码 组件练习 demo06

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    <cqh v-for="item in items" v-bind:testComponent="item"></cqh>
</div>
<script>
    /*定义一个vue组件*/
    Vue.component("cqh", {
        props: ['testComponent'],
        template: '<li>{{testComponent}}</li>'
    });
    let vm = new Vue({
        el: "#app",
        data: {
            items: ["Java", "Linux", "前端"]
        }
    });
</script>
</body>
</html>
```

# Axios异步通信(通信框架)

 Axios是一个开源的可以用在**浏览器端**和**NodeJS** 的**异步通信框架**，她的**主要作用就是实现AJAX异步通信**，其功能特点如下:
●从浏览器中创建XMLHttpRequests
●从node.js创建http请求
●支持Promise API [JS中链式编程]
●拦截请求和响应
●转换请求数据和响应数据
●取消请求
●自动转换JSON数据
●客户端支持防御XSRF (跨站请求伪造)
GitHub: https://github.com/ axios/axios
中文文档: http://www.axios-js.com/

## 为什么要使用Axios

由于Vue.js是一个视图层框架且作者(尤雨溪) 严格准守SoC (关注度分离原则)，所以Vue.js并不包含AJAX的通信功能，为了解决通信问题，作者单独开发了一个名为vue-resource的插件，不过在进入2.0 版本以后停止了对该插件的维护并推荐了Axios 框架。少用jQuery，因为它操作Dom太频繁!

# Vue的生命周期

官方文档: https://cn.vuejs.org/v2/guide/instance.html#生 命周期图示
Vue实例有一个完整的生命周期，也就是从**开始创建**、**初始化数据**、**编译模板**、**挂载DOM**、渲染→更新→渲染、卸载等一系列过程，我们称这是Vue的生命周期。通俗说就是Vue实例从创建到销毁的过程，就是生命周期。
在Vue的整个生命周期中，它提供了一系列的事件，可以让我们在事件触发时注册JS方法,可以让我们用自己注册的JS方法控制整个大局，在这些事件响应方法中的this直接指向的是Vue的实例。

代码 初探axios
先建立一个data.json

```xml
{
  "name": "cqh",
  "age": "18",
  "sex": "男",
  "url":"https://www.baidu.com",
  "address": {
    "street": "缇香郡",
    "city": "宁波",
    "country": "中国"
  },
  "links": [
    {
      "name": "bilibili",
      "url": "https://www.bilibili.com"
    },
    {
      "name": "baidu",
      "url": "https://www.baidu.com"
    },
    {
      "name": "cqh video",
      "url": "https://www.4399.com"
    }
  ]
}
```

demo07.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--在线CDN-->
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
    <!--导入axios-->
    <script src="https://cdn.bootcdn.net/ajax/libs/axios/0.19.2/axios.min.js"></script>

</head>
<body>
<div id="app">
    <div>{{info.name}}</div>
    <a v-bind:href="info.url">点我到百度链接</a>
</div>
<script>
    let vm = new Vue({
        el: "#app",
        //和data: 不同 属性：vm
        data() {
            return {
                info: {
                    name:null,
                    url:null,
                },
            }
        },
        mounted() {
            //钩子函数 链式编程 ES6新特性
            axios.get("../data.json").then(response => (this.info=response.data));
        }
    });
</script>
</body>
</html>
```

# 什么是计算属性？（VUE相比Angular和React的特性）

 计算属性的重点突出在属性两个字上(属性是名词)，首先它是个属性其次这个属性有计算的能力(计算是动词)，这里的计算就是个函数;简单点说，它就是一个能够将计算结果缓存起来的属性(将行为转化成了静态的属性)，仅此而已;可以想象为**缓存**!
代码 demo08.html 计算属性了解

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <!--在线CDN-->
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    <p>c1:{{getTime()}}</p>
    <p>c2:{{getTime2()}}</p>
</div>
<script>
    let vm = new Vue({
        el: "#app",
        data: {
            message: "hello cqh"
        },
        methods: {
            getTime: function () {
                return Date.now();
            }
        },
        //计算属性
        computed: {
            getTime2: function () {
                this.message;
                return Date.now();
            }
        }
    });
</script>
</body>
</html>
```

**结论:**
 调用方法时，每次都需要进行计算，既然有计算过程则必定产生系统开销，那如果这个结果是不经常变化的呢?此时就可以考虑将这个结果缓存起来，采用计算属性可以很方便的做到这一点,**计算属性的主要特性就是为了将不经常变化的计算结果进行缓存，以节约我们的系统开销;**

# 内容分发

 在Vue.js中我们使用 元素作为承载分发内容的出口，作者称其为插槽，可以应用在组合组件的场景中;
这里穿插以下vue的语法缩写
v:bind: 可以缩写为一个:
v-on: 可以缩写为一个@
代码demo9 插槽初体验

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    <todo>
        <todo-title slot="todo-title" v-bind:title="title"></todo-title>
        <todo-items slot="todo-items" v-for="item in todoItems" v-bind:item="item"></todo-items>
    </todo>
</div>
<script>
    //slot 插槽 这个组件要定义在前面不然出不来数据
    Vue.component("todo", {
        template: '<div>\
                <slot name="todo-title"></slot>\
                <ul>\
                <slot name="todo-items"></slot>\
                </ul>\
                <div>'
    });
    Vue.component("todo-title", {
        //属性
        props: ['title'],
        template: '<div>{{title}}</div>'
    });
    Vue.component("todo-items", {
        props: ['item'],
        template: '<li>{{item}}</li>'
    });
    let vm = new Vue({
        el: "#app",
        data: {
            //标题
            title: "图书馆系列图书",
            //列表
            todoItems: ['三国演义', '红楼梦', '西游记', '水浒传']
        }
    });
</script>
</body>
</html>
```

# 自定义事件

 通过以上代码不难发现，数据项在Vue的实例中，但删除操作要在组件中完成，那么组件如何才能删除Vue实例中的数据呢?此时就涉及到参数传递与事件分发了，Vue为我们提供了自定义事件的功能很好的帮助我们解决了这个问题;使用this.$emit(‘自定义事件名’,参数)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622162938277.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 自定义事件图解

代码 demo09-1 自定义组件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <!--1.导入vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.21/dist/vue.min.js"></script>
</head>
<body>
<div id="app">
    <todo>
        <todo-title slot="todo-title" v-bind:title="title"></todo-title>
        <todo-items slot="todo-items"
                    v-for="(item,i) in todoItems"
                    v-bind:item="item" v-bind:index="i"
                    v-on:remove="removeItem(i)">
        </todo-items>
    </todo>
</div>
<script>
    //slot 插槽 这个组件要定义在前面不然出不来数据
    Vue.component("todo", {
        template: '<div>\
                <slot name="todo-title"></slot>\
                <ul>\
                <slot name="todo-items"></slot>\
                </ul>\
                <div>'
    });
    Vue.component("todo-title", {
        //属性
        props: ['title'],
        template: '<div>{{title}}</div>'
    });
    Vue.component("todo-items", {
        props: ['item', 'index'],
        template: '<li>{{index}}-{{item}}<button style="margin: 5px" @click="remove">删除</button></li>',
        methods: {
            remove: function (index) {
                // this.$emit('事件',参数) 自定义事件分发（远程调用方法）
                this.$emit('remove', index)
            }
        },
    });
    let vm = new Vue({
        el: "#app",
        data: {
            //标题
            title: "图书馆系列图书",
            //列表
            todoItems: ['三国演义', '红楼梦', '西游记', '水浒传']
        },
        methods: {
            removeItem: function (index) {
                // 一次删除一个元素
                this.todoItems.splice(index, 1)
                console.log("删除了" + this.todoItems[index] + "OK")
            }
        },
    });
</script>
</body>
</html>
```

# vue-cli

 vue-cli 官方提供的一个脚手架,用于快速生成一个 vue 的项目模板;
 预先定义好的目录结构及基础代码，就好比咱们在创建 Maven 项目时可以选择创建一个骨架项目，这个骨架项目就是脚手架,我们的开发更加的快速;
主要的功能:
 统一的目录结构
 本地调试
 热部署
 单元测试
 集成打包上线
需要的环境
Node.js : http://nodejs.cn/download/
安装就无脑下一步就好,安装在自己的环境目录下
Git : https://git-scm.com/downloads
镜像:https://npm.taobao.org/mirrors/git-for-windows/
确认nodejs安装成功:
cmd 下输入 node -v,查看是否能够正确打印出版本号即可!
cmd 下输入 npm-v,查看是否能够正确打印出版本号即可!
这个npm,就是一个软件包管理工具,就和linux下的apt软件安装差不多!
安装 Node.js 淘宝镜像加速器（cnpm）
这样子的话,下载会快很多~
在命令台输入

```powershell
# -g 就是全局安装
npm install cnpm -g
# 或使用如下语句解决 npm 速度慢的问题
npm install --registry=https://registry.npm.taobao.org
```

狂神老师用的是npm 我用的是cnpm npm用的应该是外网 所以没有条件的同学就用cnpm
安装的位置:C:\Users\Administrator\AppData\Roaming\npm
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622163358392.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 安装的npm地址

# 安装 vue-cli

```powershell
#在命令台输入
cnpm install vue-cli -g
#查看是否安装成功
vue list
```

# 第一个 vue-cli 应用程序

 创建一个Vue项目,我们随便建立一个空的文件夹在电脑上,我这里在D盘下新建一个目录D:\Project\vue-study;
 创建一个基于 webpack 模板的 vue 应用程序

```powershell
# 这里的 myvue 是项目名称，可以根据自己的需求起名
vue init webpack myvue
```

一路都选择no即可;
初始化并运行

```powershell
cd myvue
npm install
npm run dev
```

执行完成后,目录多了很多依赖
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622163713564.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 依赖存放的位置

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622163745319.png#pic_center)

图 输入npm run dev 后 进入localhost:8080

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200622163815236.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 效果图

# Webpack

 WebPack 是一款**模块加载器兼打包工具**，它能把各种资源，如 JS、JSX、ES6、SASS、LESS、图片等**都作为模块来处理和使用。**

```powershell
npm install webpack -g
npm install webpack-cli -g
```

测试安装成功: 输入以下命令有版本号输出即为安装成功

```powershell
webpack -v
webpack-cli -v
```

# 什么是Webpack

 本质上，webpack是一个现代JavaScript应用程序的静态模块打包器(module bundler)。当webpack处理应用程序时，它会递归地构建一个依赖关系图(dependency graph),其中包含应用程序需要的每个模块，然后将所有这些模块打包成一个或多个bundle.
 Webpack是当下最热门的前端资源模块化管理和打包工具，它可以将许多松散耦合的模块按照依赖和规则打包成符合生产环境部署的前端资源。还可以将按需加载的模块进行代码分离，等到实际需要时再异步加载。通过loader转换，任何形式的资源都可以当做模块，比如CommonsJS、AMD、ES6、 CSS、JSON、CoffeeScript、LESS等;
 伴随着移动互联网的大潮，当今越来越多的网站已经从网页模式进化到了WebApp模式。它们运行在现代浏览器里，使用HTML5、CSS3、ES6 等新的技术来开发丰富的功能，网页已经不仅仅是完成浏览器的基本需求; WebApp通常是一个SPA (单页面应用) ，每一个视图通过异步的方式加载，这导致页面初始化和使用过程中会加载越来越多的JS代码，这给前端的开发流程和资源组织带来了巨大挑战。
 前端开发和其他开发工作的主要区别，首先是前端基于多语言、多层次的编码和组织工作，其次前端产品的交付是基于浏览器的，这些资源是通过增量加载的方式运行到浏览器端，如何在开发环境组织好这些碎片化的代码和资源，并且保证他们在浏览器端快速、优雅的加载和更新，就需要一个模块化系统，这个理想中的模块化系统是前端工程师多年来一直探索的难题。

## webpack demo

1. 先创建一个包 交由idea打开 会生成一个.idea文件 那么就说明该文件就交由idea负责
2. 在idea中创建modules包，再创建hello.js
   hello.js 暴露接口 相当于Java中的类

```js
exports.sayHi = function () {
    document.write("<h1>hello world</h1>")
}
```

1. 创建main.js 当作是js主入口
   main.js 请求hello.js 调用sayHi()方法

```js
let hello=require("./hello");
hello.sayHi()
```

1. 在主目录创建webpack-config.js
   webpack-config.js 这个相当于webpack的配置文件 enrty请求main.js的文件 output是输出的位置和名字

```js
module.exports = {
    entry: './modules/main.js',
    output: {
        filename: "./js/bundle.js"
    }
};
```

1. 在idea命令台输入webpack命令（idea要设置管理员启动）
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623085600641.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 webpack命令效果

1. 完成上述操作之后会在主目录生成一个dist文件 生成的js文件夹路径为/dist/js/bundle.js
2. 在主目录创建index.html 导入bundle.js
   index.html

```html
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="dist/js/bundle.js"></script>
</head>
<body>
</body>
</html>
```

1. 实际效果如图
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623085852753.png#pic_center)

图 webpack demo示例图

# vue-router

 Vue Router是Vue.js官方的**路由管理器**（路径跳转）。它和Vue.js的核心深度集成，让构建单页面应用变得易如反掌。包含的功能有:

1. 嵌套的路由/视图表
2. 模块化的、基于组件的路由配置
3. 路由参数、查询、通配符
4. 基于Vue.js过渡系统的视图过渡效果
5. 细粒度的导航控制
6. 带有自动激活的CSS class的链接
7. HTML5历史模式或hash模式，在IE9中自动降级
8. 自定义的滚动条行为
   安装
    基于第一个vue-cli进行测试学习;先查看node_modules中是否存在 vue-router
    vue-router 是一个插件包，所以我们还是需要用 npm/cnpm 来进行安装的。打开命令行工具，进入你的项目目录，输入下面命令。

```powershell
npm install vue-router --save-dev
```

安装完之后去node_modules路径看看是否有vue-router信息 有的话则表明安装成功

## vue-router demo实例

1． 将之前案例由vue-cli生成的案例用idea打开
2． 清理不用的东西 assert下的logo图片 component定义的helloworld组件 我们用自己定义的组件
3． 清理代码 以下为清理之后的代码 src下的App.vue 和main.js以及根目录的index.html
这三个文件的关系是 **index.html 调用 main.js 调用 App.vue**
index.html

```html
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0">
</head>
<body>
<div id="app">
</div>
</body>
</html>
```

main.js

```js
import Vue from 'vue'
import App from './App'
Vue.config.productionTip = false

new Vue({
  el: '#app',
  components: {App},
  template: '<App/>'
})
```

App.vue

```html
<template>
  <div id="app">
  </div>
</template>

<script>
  export default {
    name: 'App',
  }
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
```

4． 在components目录下创建一个自己的组件Content,Test,Main(这两个和Content内容一样的就不放示例代码了)
Content.vue

```html
<template>
  <div><h1>内容</h1>
  </div>
</template>
<script>
  export default {
    name: "Content"
  }
</script>
<!--scoped ：style作用域-->
<style scoped>
</style>
```

5． 安装路由,在src目录下,新建一个文件夹 : router,专门存放路由
index.js(默认配置文件都是这个名字)

```javascript
/**
 * vue router的配置
 */
//导入vue和vu-router
import Vue from 'vue'
import VueRouter from 'vue-router';
//导入组件
import Content from "../components/Content";
import Main from "../components/Main";
import Test from "../components/Test";
//安装路由
Vue.use(VueRouter)

//配置导出路由
export default new VueRouter({
  routes: [
    //Content组件
    {
      //  路由的路径
      path: '/content',
      name: 'content',
      //  跳转的组件
      component: Content
    },
    //Main组件
    {
      //  路由的路径
      path: '/main',
      name: 'main',
      //  跳转的组件
      component: Main
    },
    //Test组件
    {
      //  路由的路径
      path: '/test',
      name: 'test',
      //  跳转的组件
      component: Test
    }
  ]
});
```

6． 在main.js中配置路由
main.js

```javascript
import Vue from 'vue'
import App from './App'
//自动扫描里面的路由配置
import router from "./router";

Vue.config.productionTip = false

new Vue({
  el: '#app',
  //配置路由
  router,
  components: {App},
  template: '<App/>'
})
```

7． 在App.vue中使用路由
App.vue

```html
<template>
  <div id="app">
    <h1>vue-router</h1>
    <!--a标签 to就是href属性-->
    <router-link to="/main">首页</router-link>
    <router-link to="/content">内容页</router-link>
    <router-link to="/test">测试页</router-link>
    <!—这个标签就是用来展示视图-->
    <router-view></router-view>
  </div>
</template>
<script>

  export default {
    name: 'App',
  }
</script>
<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
```

8． 启动测试一下 ： npm run dev
9． 效果图如下
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623143643991.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 vue-router效果图

# vue+elementUI实战

根据之前创建vue-cli项目一样再来一遍 创建项目
1． 创建一个名为 hello-vue 的工程 vue init webpack hello-vue
2． 安装依赖，我们需要安装 vue-router、element-ui、sass-loader 和 node-sass 四个插件

```powershell
# 进入工程目录
cd hello-vue
# 安装 vue-router
npm install vue-router --save-dev
# 安装 element-ui
npm i element-ui -S
# 安装依赖
npm install
# 安装 SASS 加载器
cnpm install sass-loader node-sass --save-dev
# 启动测试
npm run dev	
```

3． 创建成功后用idea打开，并删除净东西 创建views和router文件夹用来存放视图和路由
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623143803994.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 整体目录结构

4． 在views创建Main.vue
Main.vue

```html
<template>
  <h1>首页</h1>
</template>
<script>
  export default {
    name: "Main"
  }
</script>
<style scoped>
</style>
```

5． 在views中创建Login.vue视图组件
Login.vue（用的饿了么UI中的代码）

```markup
<template>
  <div>
    <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
      <h3 class="login-title">欢迎登录</h3>
      <el-form-item label="账号" prop="username">
        <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" v-on:click="onSubmit('loginForm')">登录</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="温馨提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <span>请输入账号和密码</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        form: {
          username: '',
          password: ''
        },

        // 表单验证，需要在 el-form-item 元素中增加 prop 属性
        rules: {
          username: [
            {required: true, message: '账号不可为空', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '密码不可为空', trigger: 'blur'}
          ]
        },

        // 对话框显示和隐藏
        dialogVisible: false
      }
    },
    methods: {
      onSubmit(formName) {
        // 为表单绑定验证功能
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // 使用 vue-router 路由到指定页面，该方式称之为编程式导航
            this.$router.push("/main");
          } else {
            this.dialogVisible = true;
            return false;
          }
        });
      }
    }
  }
</script>

<style lang="scss" scoped>
  .login-box {
    border: 1px solid #DCDFE6;
    width: 350px;
    margin: 180px auto;
    padding: 35px 35px 15px 35px;
    border-radius: 5px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    box-shadow: 0 0 25px #909399;
  }

  .login-title {
    text-align: center;
    margin: 0 auto 40px auto;
    color: #303133;
  }
</style>
```

6． 创建路由,在 router 目录下创建一个名为 index.js 的 vue-router 路由配置文件
index.js

```javascript
//导入vue
import Vue from 'vue';
import VueRouter from 'vue-router';
//导入组件
import Main from "../views/Main";
import Login from "../views/Login";
//使用
Vue.use(VueRouter);
//导出
export default new VueRouter({
  routes: [
    {
      //登录页
      path: '/main',
      component: Main
    },
    //首页
    {
      path: '/login',
      component: Login
    },
  ]
})
```

7． 在main.js中配置相关
main.js main.js是index.html调用的 所以基本上所有东西都导出到这
一定不要忘记扫描路由配置并将其用到new Vue中

```javascript
import Vue from 'vue'
import App from './App'
import VueRouter from "vue-router";
//扫描路由配置
import router from "./router"
//导入elementUI
import ElementUI from "element-ui"
//导入element css
import 'element-ui/lib/theme-chalk/index.css'
//使用
Vue.use(VueRouter)
Vue.use(ElementUI)
Vue.config.productionTip = false
new Vue({
  el: '#app',
  router,
  render: h => h(App),//ElementUI规定这样使用
})
```

8． 在App.vue中配置显示视图
App.vue

```markup
<template>
  <div id="app">
    <!--展示视图-->
    <router-view></router-view>
  </div>
</template>
<script>
  export default {
    name: 'App',
  }
</script>
```

9． 最后效果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200623144127374.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 vue+elementUI实战效果图

测试：在浏览器打开 http://localhost:8080/#/login
如果出现错误: 可能是因为sass-loader的版本过高导致的编译错误，当前最高版本是8.0.2，需要退回到7.3.1 ；
去package.json文件里面的 "sass-loader"的版本更换成7.3.1，然后重新cnpm install就可以了；

## 路由嵌套

 嵌套路由又称子路由，在实际应用中，通常由多层嵌套的组件组合而成。
demo
1、 创建用户信息组件，在 views/user 目录下创建一个名为 Profile.vue 的视图组件；
Profile.vue

```markup
<template>
  <h1>个人信息</h1>
</template>
<script>
  export default {
    name: "UserProfile"
  }
</script>
<style scoped>
</style>
```

2、在用户列表组件在 views/user 目录下创建一个名为 List.vue 的视图组件；
List.vue

```markup
<template>
  <h1>用户列表</h1>
</template>
<script>
  export default {
    name: "UserList"
  }
</script>
<style scoped>
</style>
```

3、 修改首页视图，我们修改 Main.vue 视图组件，此处使用了 ElementUI 布局容器组件，代码如下：
Main.vue

```html
<template>
    <div>
      <el-container>
        <el-aside width="200px">
          <el-menu :default-openeds="['1']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-caret-right"></i>用户管理</template>
              <el-menu-item-group>
                <el-menu-item index="1-1">
                <!--插入的地方-->
                  <router-link to="/user/profile">个人信息</router-link>
                </el-menu-item>
                <el-menu-item index="1-2">
                <!--插入的地方-->
                  <router-link to="/user/list">用户列表</router-link>
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
              <template slot="title"><i class="el-icon-caret-right"></i>内容管理</template>
              <el-menu-item-group>
                <el-menu-item index="2-1">分类管理</el-menu-item>
                <el-menu-item index="2-2">内容列表</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>

        <el-container>
          <el-header style="text-align: right; font-size: 12px">
            <el-dropdown>
              <i class="el-icon-setting" style="margin-right: 15px"></i>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-header>
          <el-main>
          <!--在这里展示视图-->
            <router-view />
          </el-main>
        </el-container>
      </el-container>
    </div>
</template>
<script>
    export default {
        name: "Main"
    }
</script>
<style scoped lang="scss">
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  .el-aside {
    color: #333;
  }
</style>
```

4、 配置嵌套路由修改 router 目录下的 index.js 路由配置文件，使用children放入main中写入子模块，代码如下
index.js

```js
//导入vue
import Vue from 'vue';
import VueRouter from 'vue-router';
//导入组件
import Main from "../views/Main";
import Login from "../views/Login";
//导入子模块
import UserList from "../views/user/List";
import UserProfile from "../views/user/Profile";

//使用
Vue.use(VueRouter);
//导出
export default new VueRouter({
  routes: [
    {
      //登录页
      path: '/main',
      component: Main,
      //  写入子模块
      children: [
        {
          path: '/user/profile',
          component: UserProfile,
        }, {
          path: '/user/list',
          component: UserList,
        },
      ]
    },
    //首页
    {
      path: '/login',
      component: Login

    },
  ]
})
```

5、 路由嵌套实战效果图
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062413282631.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 路由嵌套效果图

## 参数传递

 这里演示如果请求带有参数该怎么传递
demo
 用的还是上述例子的代码 修改一些代码 这里不放重复的代码了
第一种取值方式
1、 修改路由配置, 主要是router下的index.js中的 path 属性中增加了 :id 这样的占位符

```javascript
{
	path: '/user/profile/:id', 
	name:'UserProfile', 
	component: UserProfile
}
```

2、传递参数
 此时我们在Main.vue中的route-link位置处 to 改为了 :to，是为了将这一属性当成对象使用，注意 router-link 中的 name 属性名称 一定要和 路由中的 name 属性名称 匹配，因为这样 Vue 才能找到对应的路由路径；

```html
<!--name是组件的名字 params是传的参数 如果要传参数的话就需要用v:bind:来绑定-->
<router-link :to="{name:'UserProfile',params:{id:1}}">个人信息</router-link>
```

3、在要展示的组件Profile.vue中接收参数 使用 {{$route.params.id}}来接收
Profile.vue 部分代码

```markup
<template>
  <!--  所有的元素必须在根节点下-->
  <div>
    <h1>个人信息</h1>
    {{$route.params.id}}
  </div>
</template>
```

第二种取值方式 使用props 减少耦合
1、修改路由配置 , 主要在router下的index.js中的路由属性中增加了 props: true 属性

```javascript
{
	path: '/user/profile/:id', 
	name:'UserProfile', 
	component: UserProfile, 
	props: true
}
```

2、传递参数和之前一样 在Main.vue中修改route-link地址

```html
<!--name是组件的名字 params是传的参数 如果要传参数的话就需要用v:bind:来绑定-->
<router-link :to="{name:'UserProfile',params:{id:1}}">个人信息</router-link>
```

3、在Profile.vue接收参数为目标组件增加 props 属性
Profile.vue

```markup
<template>
  <div>
    个人信息
    {{ id }}
  </div>
</template>
<script>
    export default {
      props: ['id'],
      name: "UserProfile"
    }
</script>
<style scoped>
</style>
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200624140100715.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 传参效果图

## 组件重定向

 重定向的意思大家都明白，但 Vue 中的重定向是作用在路径不同但组件相同的情况下，比如：
在router下面index.js的配置

```javascript
{
  path: '/main',
  name: 'Main',
  component: Main
},
{
  path: '/goHome',
  redirect: '/main'
}
```

 说明：这里定义了两个路径，一个是 /main ，一个是 /goHome，其中 /goHome 重定向到了 /main 路径，由此可以看出重定向不需要定义组件；

 使用的话，只需要在Main.vue设置对应路径即可；

```markup
<el-menu-item index="1-3">
    <router-link to="/goHome">回到首页</router-link>
</el-menu-item>
```

## 路由模式与 404

路由模式有两种

- hash：路径带 # 符号，如 http://localhost/#/login
- history：路径不带 # 符号，如 http://localhost/login

修改路由配置，代码如下：

```javascript
export default new Router({
  mode: 'history',
  routes: [
  ]
});
```

404 demo
1.创建一个NotFound.vue视图组件
NotFound.vue

```markup
<template>
    <div>
      <h1>404,你的页面走丢了</h1>
    </div>
</template>
<script>
    export default {
        name: "NotFound"
    }
</script>
<style scoped>
</style>
```

2.修改路由配置index.js

```javascript
import NotFound from '../views/NotFound'
{
   path: '*',
   component: NotFound
}
```

3.效果图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200624142229445.png)

图 404效果图

## 路由钩子与异步请求

beforeRouteEnter：在进入路由前执行
beforeRouteLeave：在离开路由前执行

在Profile.vue中写

```js
  export default {
    name: "UserProfile",
    beforeRouteEnter: (to, from, next) => {
      console.log("准备进入个人信息页");
      next();
    },
    beforeRouteLeave: (to, from, next) => {
      console.log("准备离开个人信息页");
      next();
    }
  }
```

参数说明：
to：路由将要跳转的路径信息
from：路径跳转前的路径信息
next：路由的控制参数
next() 跳入下一个页面
next(’/path’) 改变路由的跳转方向，使其跳到另一个路由
next(false) 返回原来的页面
next((vm)=>{}) 仅在 beforeRouteEnter 中可用，vm 是组件实例

## 在钩子函数中使用异步请求

1、安装 Axios

```powershell
cnpm install --save vue-axios
1
```

2、main.js引用 Axios

```js
import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(VueAxios, axios)
123
```

3、准备数据 ： 只有我们的 static 目录下的文件是可以被访问到的，所以我们就把静态文件放入该目录下。
数据和之前用的json数据一样 需要的去上述axios例子里

```java
// 静态数据存放的位置
static/mock/data.json
12
```

4.在 beforeRouteEnter 中进行异步请求
Profile.vue

```javascript
  export default {
    //第二种取值方式
    // props:['id'],
    name: "UserProfile",
    //钩子函数 过滤器
    beforeRouteEnter: (to, from, next) => {
      //加载数据
      console.log("进入路由之前")
      next(vm => {
        //进入路由之前执行getData方法
        vm.getData()
      });
    },
    beforeRouteLeave: (to, from, next) => {
      console.log("离开路由之前")
      next();
    },
    //axios
    methods: {
      getData: function () {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/static/mock/data.json'
        }).then(function (response) {
          console.log(response)
        })
      }
    }
  }
```

5.路由钩子和axios结合图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200624143534392.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L29rRm9ycmVzdDI3,size_16,color_FFFFFF,t_70#pic_center)

图 效果图