# 左眼在线视频课程系统

这个项目的技术亮点在于：

对于刚刚掌握了一大堆技术栈的初学者，是一个很好的练手项目

后端：springboot、ringCloud、自己动手设计的通用代码生成器、网关、枚举类的设计、权限管理、版本控制器git，等等

前端：Vue2.x.1、bootatrap4.4.1、树插件、富文本编辑器、axios库、等等

此项目还在优化阶段，后续还会有以下更新（我看我精力吧，之后还会去做一些其他项目分享一下）

1.注册中心更换成Nacoa

2.对接第三方支付接口

3.对接第三方短信发送

4.加入MQ消息队列

5.页面模板可能会更换



## 需要以下技术：

* jdk-8u221-windows-x64.exe<br>
* ideaIU-2019.2.3.exe<br>
* Git-2.23.0-64-bit.exe<br>
* mysql-installer-community-5.7.27.0.msi<br>
* node-v12.10.0-x64.msi<br>
* zTree-zTree_v3-master.zip<br>
* nacos-server-1.2.1.zip<br>
* jdk-8u261-linux-x64.tar.gz<br>

## 源码下载
使用下面的命令将源码从远程仓库拉取到本地，需要本地提前安装好git

```
git clone https://gitee.com/mr-tzp/course.git
会配置ssh的，可以用ssh:
git clone ssh://git@gitee.com:mr-tzp/course.git
```

## 项目模块说明
* **admin**<br>
控台管理，vue cli项目
* **business**<br>
核心业务模块，spring boot项目
* **doc**<br>
项目相关的文档，包含项目数据库初始化脚本和所有资源文件
* **eureka**<br>
注册中心，spring boot项目
* **file**<br>
文件模块，spring boot项目
* **gateway**<br>
路由模块，spring boot项目
* **generator**<br>
代码生成器
* **server**<br>
公共jar模块，被business, file, system依赖
* **system**<br>
系统模块，spring boot项目
* **web**<br>
网站模块，vue cli项目


## 项目初始化
* 需要本地安装好idea, node.js，jdk1.8, mysql8.0, navicat（数据库可视化工具）
* 将下载好的源码，用idea打开
* 刷新maven依赖
* 安装vue cli
```
npm install -g @vue/cli
```
* 下载node模块
```
初始化web模块
cd web
npm install

初始化admin模块
cd admin
npm install
```
* 新建数据库courseimooc，并courseimooc，密码courseimooc
* 数据库初始脚本在/doc/db/all.sql 

## 项目启动
* 启动注册中心：EurekaApplication
* 启动路由模块：GatewayApplication
* 启动系统模块：SystemApplication
* 启动业务模块：BusinessApplication
* 启动文件模块：FileApplication
* 启动控台管理：admin\package.json
* 启动前端网站：web\package.json

启动前，记得一下的配置文件换成自己的

- 阿里oss
- 阿里云vod
- 本地/阿里redis
- 本地/阿里mysql



#### 阿里oss和vod

在file的application.properties

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/OssVodRedis/oss%E3%80%81vod.png)



#### mysql有一处，redis有2处需要替换：

一处在gateway的application.properties

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/OssVodRedis/%E7%BD%91%E5%85%B3%E7%9A%84redis.png)

另外一处在server的application.properties，mysql也在这里面替换

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/OssVodRedis/redis.png)



## 页面访问
* 控台地址: http://localhost:8080/login<br>
初始用户名密码：test/test
* 控台地址: http://localhost:8081<br>
可以自己注册用户，短信验证码从后台日志看，或看sms表

## 资源配置
所有资源都在/doc/db/resource.json，<br>
需要在控台上将所有的资源配置进去，并在角色管理中配置权限
权限配置好后，需要重新登录



## 前端 admin 视图组件介绍（包含图片）

（这里使用了，ace免费模板，网上可以查找到原版）

路径： admin/src/views

- 头部栏、侧边栏：admin.vue
- 登录页面：login.vue

路径：admin/src/views/admin

- 课程分类页面（包含一级分类和二级分类）：category.vue
- 课程列表页面：course.vue
- 大章列表页面：chapter.vue
- 小节列表页面：section.vue
- 大章内容编辑页面（富文本编辑器）：content.vue
- 讲师管理页面：teacher.vue
- 文件管理页面（上传的文件路径展示）：file.vue
- 欢迎页面（还未做完）：welcome.vue
- 用户管理页面：userCourse.vue
- 角色资源分配：file.vue
- 资源管理页面(动态资源管理)：resource.vue
- 短信页面：sms
- 会员管理页面：member.vue

路径：admin/src/views/components

通用组件---部分组件间存在调用关系：

- 自己写的文件上传组件：file.vue  （已弃用）
- 优化后的文件上传组件：big-file.vue
- 分页组件：pagination.vue
- 阿里云播放器实例化组件：pagination.vue
- 视频上传组件：vod.vue
- 拟态播放器组件: modal-player.vue



## 前端 web 视图组件介绍（包含图片）

（这里使用了，bootstrap4.4.1免费模板，bootstrap管网可以找到原版）

路径： admin/src/views

- 首页：index.vue
- 查找全部课程页面：list.vue
- 课程详情页面（包含介绍和视频播放）：detail.vue

路径：admin/src/views/components

通用组件：

- 头部栏：the-header.vue
- 底部栏：the-footer.vue
- 课程卡片组件：the-course.vue
- 会员登录注册组件：login.vue
- 阿里云播放器实例化组件：pagination.vue
- 拟态播放器组件: modal-player.vue
- 分页组件：pagination.vue

## 图片展示

### 后台展示

#### 1.后台登录页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E5%90%8E%E5%8F%B0%E7%99%BB%E5%BD%95%E9%A1%B5%E9%9D%A2.png)

#### 2.后台欢迎页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E5%90%8E%E5%8F%B0%E6%AC%A2%E8%BF%8E%E9%A1%B5%E9%9D%A2.png)

#### 3.用户管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E7%94%A8%E6%88%B7%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 4.资源管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E8%B5%84%E6%BA%90%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2%EF%BC%88%E5%8A%A8%E6%80%81%E8%8F%9C%E5%8D%95%EF%BC%89.png)

#### 5.角色管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 6.课程分类管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E5%88%86%E7%B1%BB%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 7.课程管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E8%AF%BE%E7%A8%8B%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 8.课程管理中的大章管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E8%AF%BE%E7%A8%8B%E7%AE%A1%E7%90%86%E4%B8%AD%E7%9A%84%E5%A4%A7%E7%AB%A0%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 9.课程管理中的小节管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E8%AF%BE%E7%A8%8B%E7%AE%A1%E7%90%86%E4%B8%AD%E7%9A%84%E5%B0%8F%E8%8A%82%E9%A1%B5%E9%9D%A2.png)

#### 10.课程管理中的内容编辑页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E5%AF%8C%E6%96%87%E6%9C%AC%E6%A1%86-%E8%AF%BE%E7%A8%8B%E5%86%85%E5%AE%B9%E7%BC%96%E8%BE%91%E9%A1%B5%E9%9D%A2.png)

#### 11.讲师管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E8%AE%B2%E5%B8%88%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 12.会员管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E5%90%8E%E5%8F%B0%E4%BC%9A%E5%91%98%E7%AE%A1%E7%90%86.png)

#### 13.短信管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E7%9F%AD%E4%BF%A1%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)

#### 14.文件管理页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%90%8E%E5%8F%B0%E5%9B%BE%E7%89%87/%E6%96%87%E4%BB%B6%E7%AE%A1%E7%90%86%E9%A1%B5%E9%9D%A2.png)



### 前端展示

#### 1前端页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E5%89%8D%E7%AB%AF%E9%A1%B5%E9%9D%A2.png)

#### 2.最新上线

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E6%9C%80%E6%96%B0%E4%B8%8A%E7%BA%BF.png)

#### 3.好课推荐

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E5%A5%BD%E8%AF%BE%E6%8E%A8%E8%8D%90.png)

#### 4.全部课程页面

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E5%85%A8%E9%83%A8%E8%AF%BE%E7%A8%8B%E9%A1%B5%E9%9D%A2.png)

#### 5.登录框

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E7%99%BB%E5%BD%95%E6%A1%86.png)

#### 6.注册框

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E6%B3%A8%E5%86%8C%E6%A1%86.png)

#### 7.忘记密码框

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E5%BF%98%E8%AE%B0%E5%AF%86%E7%A0%81%E6%A1%86.png)

#### 8.课程详情与讲师信息

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E8%AF%BE%E7%A8%8B%E8%AF%A6%E6%83%85%E4%B8%8E%E8%AF%BE%E7%A8%8B%E5%86%85%E5%AE%B9%E5%92%8C%E8%AE%B2%E5%B8%88%E4%BF%A1%E6%81%AF.png)

#### 9.章节目录与讲师信息以及视频播放

![Alt](https://tangrontao-course.oss-cn-chengdu.aliyuncs.com/screenshotsImg/%E5%89%8D%E7%AB%AF%E5%9B%BE%E7%89%87/%E7%AB%A0%E8%8A%82%E7%9B%AE%E5%BD%95%E5%92%8C%E8%AE%B2%E5%B8%88%E4%BF%A1%E6%81%AF.png)

