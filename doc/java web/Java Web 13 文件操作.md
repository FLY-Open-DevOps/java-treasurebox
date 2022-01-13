## 一、引言

### 1.1场景

- 在项目中，文件的上传和下载是常见的功能。很多程序或者软件中都经常使用到文件的上传和下载。
- 邮箱中有附件的上传和下载
- OA办公系统中有附件材料的上传

## 二、文件上传

### 2.1概念

- 当用户在前端页面点击文件上传后，用户上传的文件数据提交给服务器端，实现保存。

### 2.2文件上传实现步骤

#### 2.2.1提交方式

- 提供form表单，method必须是post。因为post请求无数据限制。

```jsp
<form method="post"> </form>
```

#### 2.2.2提交数据格式

- 表单的 enctype 属性值必须为 multipart/form-data。

- 以多段的形式进行拼接提交。以二进制流的方式来处理表单数据，会把指定的文件内容封装进请求参数中。

  ```jsp
  <form enctype="multipart/form-data" method="post"></form>
  ```

#### 2.2.3提供组件

- 提供file表单组件，提供给用户上传文件。

```jsp
<form enctype="multipart/form-data" method= "post">
	上传用户：<input type="text" name="username"><br/> 
    上传文件 1:<input type="file" name="file1"><br/> 
    <input type= "submit" value="提交">
</form>
```

#### 2.2.4 Controller 编写

- 在Servlet 3.0及其以上版本的容器中进行服务器端文件上传的编程，是围绕着注解类型MultipartConfig和javax. servlet.http.Part接口进行的。
- 处理已上传文件的Servlet必须以 @MultipartConfig 进行注解。

```java
package com.zssea.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "FileUploadController",value = "/upload")
@MultipartConfig(maxFileSize = 1024*1024*100,maxRequestSize = 1024*1024*200)//单个文件大小，多个文件总大小
public class FileUploadController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //实现文件上传
        //1.处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //2.收参，获取客户端传过来的数据
        String username = request.getParameter("username");
        Part part = request.getPart("file1");

        //3.设置（获取）文件上传的路径（即，将用户上传的文件放在哪里？）
        String uploadPath = request.getServletContext().getRealPath("/WEB-INF/upload");
        File file = new File(uploadPath);
        if(!file.exists()){
            file.mkdirs();//新建文件
        }
        //4.文件上传
        part.write(uploadPath+"\\"+part.getSubmittedFileName());

        //5.响应客户端
        response.getWriter().println(part.getSubmittedFileName()+"上传成功！");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

### 2.3文件上传细节注意

- 上述的代码虽然可以成功将文件上传到服务器的指定目录当中，但是文件上传功能有许多需要注意的小细节问题。

#### 2.3.1安全问题

- 为保证服务器安全，上传文件应该放在外界无法直接访问的目录下，比如放于WEB-INF目录下。

```java
String filepath = request.getServletContext().getRealPath("/WEB-INF/upload");
```

#### 2.3.2文件覆盖

- 当上传重名的文件时，为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名。

```java
package com.zssea.utils;

import java.util.UUID;

public class UploadUtils {
    public static String newFileName(String filename){
        return UUID.randomUUID().toString().replaceAll("-","")+"_"+filename;
    }
}
```

#### 2.3.3散列存储

- 为防止一个目录下面出现太多文件，要使用hash算法生成二级、三级目录，散列存储上传的文件。

```java
package com.zssea.utils;

import java.io.File;
import java.util.UUID;

public class UploadUtils {
    public static String newFilePath(String basepath,String filename ){
        int hashCode = filename.hashCode();
        int path1 = hashCode & 15; //二级目录，与运算 结果为0~15
        int path2 = (hashCode>>4)  & 15; //三级目录，结果为0~15
        String newPath = basepath+"\\"+path1+"\\"+path2;
        File file = new File(newPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return newPath;
    }
}
```

- 目录分离算法

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200813194723204.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dhbmdfbHV3ZWk=,size_16,color_FFFFFF,t_70#pic_center)

#### 2.3.4文件类型限制

- 要限制上传文件的类型，在收到上传文件名时，判断后缀名是否合法。

```java
 String oldName = part.getSubmittedFileName(); //文件上传时的文件名

        List<String> nameList = new ArrayList<String>();//用集合来存储可以上传的文件的后缀名
        nameList.add(".jpg");
        nameList.add(".jpeg");
        nameList.add(".png");
        String extName = oldName.substring(oldName.lastIndexOf("."));//截取文件名的后缀名
        if(!nameList.contains(extName)){
            response.getWriter().println(oldName+"不符合上传文件的规则");
            return;
        }
```

### 2.4多文件上传

```java
package com.zssea.servlet;

import com.zssea.utils.UploadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

@WebServlet(name = "MoreUploadController",value = "/moreupload")
@MultipartConfig(maxFileSize = 1024*1024*100,maxRequestSize = 1024*1024*200)
public class MoreUploadController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获取存储路径
        String basePath = request.getServletContext().getRealPath("/WEB-INF/upload");
        File file = new File(basePath);
        if(!file.exists()){
            file.mkdirs();//新建文件夹
        }

        //获取客户端提交的数据
        Collection<Part> parts = request.getParts();// 这是获取了表单提交的所有数据（包括普通表单项）
        for (Part part : parts){
            String fileName = part.getSubmittedFileName();
            if(fileName != null){//该part为文件
                if(fileName.trim().equals("")){//判断file表单项是否上传了文件，如果没有跳过这次
                    continue;
                }
                String newFileName = UploadUtils.newFileName(fileName);//创建一个唯一的文件名
                String newFilePath = UploadUtils.newFilePath(basePath, fileName);//实现散列存储
                part.write(newFilePath+"\\"+newFileName);//上传（存储）
            }else { //普通表单项
                String username = request.getParameter(part.getName());//part.getName() 得到的是表单项里的 name 值
                System.out.println(username);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

## 三、文件下载

## 3.1概念

我们要将Web应用系统中的文件资源提供给用户进行下载，首先我们要有一个页面列出上传文件目录下的所有文件，当用户点击文件 下载超链接时就进行下载操作。

### 3.2获取文件列表

#### 3.2.1 DownLoadUtils

```java
package com.zssea.utils;

import java.io.File;
import java.util.HashMap;

public class DownLoadUtils {
    public static void getFileList(File file, HashMap<String,String> fileMap){
        File[] files = file.listFiles();
        for (File f : files){
            if(f.isDirectory()){//如果是文件夹
                getFileList(f,fileMap);
            }else {//文件
                String fileName = f.getName();
                int index = fileName.indexOf("_");
                String realFileName = fileName.substring(index + 1);
                fileMap.put(fileName,realFileName);
            }
        }

    }
}
```

#### 3.2.2 FileListController

```java
package com.zssea.servlet;

import com.zssea.utils.DownLoadUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "FileListController" ,value = "/filelist")
public class FileListController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //创建map集合 key：带有UUID的文件名称  value：源文件名称， 然后通过request传递给jsp
        HashMap<String,String> fileMap = new HashMap<>();
        String basePath = request.getServletContext().getRealPath("/WEB-INF/upload");
        DownLoadUtils.getFileList(new File(basePath),fileMap);

        //转发
        request.setAttribute("fileMap",fileMap);
        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```

### 3.3下载

#### 3.3.1 fileList.jsp

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件下载页面</title>
</head>
<body>
    <table >
        <tr>
            <th>文件</th>
            <th>操作</th>
        </tr>
        <c:forEach var="entry" items="${fileMap}">
            <tr>
                <td>${entry.value}</td>
                <td><a href="${pageContext.request.contextPath}/download?filename=${entry.key}">下载</a></td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
```

#### 3.3.2 DownLoadController

```java
package com.zssea.servlet;

import com.zssea.utils.UploadUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "DownLoadController",value = "/download")
public class DownLoadController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //获取存储路径
        String basePath = request.getServletContext().getRealPath("/WEB-INF/upload");
        //收参
        String uuidFilename = request.getParameter("filename");
        //反向获取 源文件名
        String fileName = uuidFilename.split("_")[1];
        //通过 工具类与源文件名，来获得这个文件散列存储的存储路径，(就是下载路径)
        String downPath = UploadUtils.newFilePath(basePath, fileName);

        //设置响应头
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fileName,"utf-8"));
        //输入流
        FileInputStream fis = new FileInputStream(downPath+"\\"+uuidFilename);
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024*1024*100];
        int len = 0;
        while ( (len = fis.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }

        outputStream.close();
        fis.close();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
```