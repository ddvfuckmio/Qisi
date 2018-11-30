# 奇思孵化项目

## 名词解释
* course   课程
* chapter  课程下的章节
* lesson   章节下的训练
* task     训练下的任务
* case     训练下的测试用例
* code     用户提交的代码
* progress 用户某门课程的进度

## 软件环境搭设
```shell
确保部署时物理机满足以下条件
 1: jdk1.8支持
 2: maven支持
 3: mysql支持
 4: activeMQ支持
 5  redis支持
 6: 修改application.yml中的
 spring:
    profiles:
       active: dev --> production
 并修改生产环境的配置文件(mysql,MQ...)
```

## 后端基本逻辑
```shell
用户登陆之后保存用户个人信息在session中
所有接口(除了注册,登陆)都需要对session检查用户是否登录
如未登录会强制用户登录 ajax需要做额外判断 
后续API会从session中读取用户信息
```

## Jms配置
```$xslt
手动消息监听自行实现,未整合到框架中,需要观察GC状态
是否有死线程,死连接现象
```
##API

http://localhost:8080/swagger-ui.html#/

所有的API都在controller包中
API分为两种
* 跳转页面API  跳转页面
* restful API 返回json 

controller中有二者的区分注释

###UserController
* /user/login          用户登录
* /user/register       用户注册
* /user/password       用户更新密码
* /user/profile        用户更新个人信息
* /upload              用户上传文件 (暂存image文件夹 上传文件限制1024MB)

###AdminController (pre="/admin 获取将add修改为get即可)
* /users                      获取所有用户
* /user/{username}            获取用户根据用户名
* /addCourses                 添加/获取Courses
* /addChapters                添加/获取Chapters
* /addLessons                 添加/获取Lessons   
* /addTasks                   添加/获取Tasks
* /addCases                   添加/获取Cases

###PageController
* /pages/login    登录页面 
* /pages/register 注册页面

###CourseController
* /courses                     课程列表
* /course/{courseId}/chapters  对应课程的目录
* /chapter/{chapterId}/lessons 对应目录的训练
* /lesson/{lessonId}/tasks     对应训练的任务
* /task/{taskId}               对应任务详情
* /course?courseName           根据courseName查找
* /course/{courseId}           根据courseId查找
* /code/commit                 提交代码
