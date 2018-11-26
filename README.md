# 奇思孵化项目

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
如未登录会强制用户登录 后续API会从session中读取用户信息
```

## Jms配置
```$xslt
手动消息监听自行实现,未整合到框架中,需要观察GC状态
```

## 持久层
```$xslt
db为mysql 字符集为utf-8
持久层规范为JPA
```

## 前端样例
```使用thymeleaf渲染```

