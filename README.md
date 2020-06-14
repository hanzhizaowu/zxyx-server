﻿# 社交App-朝夕印象

ZxyxServerApi：后台api项目

## 技术选型
下面列举技术栈，并说明选择的原因:
<br>1.后台服务
<br>基于java，spring boot,swagger2
<br>数据库mysql, mybatis
<br>缓存redis
<br>消息rabbitmq


##  总体架构
<br>1. 后台总体架构
<br>Android接口请求通过网络，到达服务器后，经过nginx反向代理到后台服务
<br>Android通过网络发送或获取图片、视频等资源时，通过nginx反代到本地的远程oss映射
<br>调用后台接口的时候， 一部分接口拦截进行权限验证的，权限验证对应redis使用userId/token
<br>图片资源提交后，通过mq异步进行多种图片的处理