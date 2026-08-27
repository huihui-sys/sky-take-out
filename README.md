# 苍穹外卖项目
## 📖项目介绍
苍穹外卖外卖平台后端接口服务，包含管理端、用户端两套接口。
前端Vue独立部署，通过Nginx反向代理做请求转发。

## 🛠技术栈
- 后端：SpringBoot、MyBatis、MySQL
- 缓存：Redis
- 实时推送：WebSocket
- 部署：Nginx反向代理

## ✨项目功能
### 管理端
- 菜品管理、套餐管理
- 分类管理
- 订单管理，订单状态流转
- 定时任务处理超时/派送完成订单

### 用户端
- 用户登录，地址簿管理
- 购物车操作
- 下单、历史订单、再来一单

## 🚀运行说明
1. 创建MySQL数据库，导入项目提供的SQL脚本
2. 修改 `application‑dev‑template.yml`，填入自己数据库、Redis连接信息
3. 启动 `SkyApplication`，接口文档：http://localhost:8080/doc.html

### 部署说明
1. Vue前端打包后静态资源交给Nginx托管
2. Nginx做反向代理，`/api/**` 请求转发到SpringBoot后端服务
3. WebSocket订单消息实时推送