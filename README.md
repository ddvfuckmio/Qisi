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
 5: 修改application.yml中的
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
/admin API会对session中的用户进行鉴权
```

## Jms配置
```$xslt
手动消息监听自行实现,未整合到框架中,需要观察GC状态
是否有死线程,死连接现象
```
##API(需要登录)
http://localhost:8080/swagger-ui.html#/

所有的API都在controller包中
API分为两种
* 跳转页面API  跳转页面
* restful API 返回json 
controller中有二者的区分注释
