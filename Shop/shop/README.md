
# 🛍️ 简易购物网站（JSP + Servlet + MySQL）

> 一个基于 Java Web 技术栈的轻量级 B2C 购物平台 Demo，涵盖用户、商品、购物车、订单、收藏、广告等核心模块，适合课程设计、毕业设计或二次开发练手。

---

## 📦 技术架构

| 层级        | 技术选型                              |
|-------------|---------------------------------------|
| 前端        | JSP + JSTL + 原生 CSS/JS（无框架）    |
| 后端        | Java 17 + Jakarta Servlet 5           |
| 数据库      | MySQL 8.0                             |
| 连接池      | 原生 JDBC（已封装 DBUtil）            |
| 构建工具    | 无（动态 Web 项目，直接部署 Tomcat）  |
| 服务器      | Apache Tomcat 10+                     |

---

## 🧱 功能清单

| 模块     | 功能点                                                                 |
|----------|------------------------------------------------------------------------|
| 用户     | 注册、登录、记住用户名、验证码、个人中心（仅静态展示）                |
| 商品     | 分类浏览、搜索、排序、筛选、库存/销量/评分展示、商品标签              |
| 购物车   | 增删改、数量 +/-、小计、库存校验、清空、去结算                        |
| 订单     | 创建、取消、状态流转（待付款→已付款→已发货→已收货）、订单详情、统计 |
| 支付     | 模拟支付（95% 成功率）、支付结果页                                    |
| 收藏     | 商品收藏/取消、收藏夹列表、一键加入购物车                             |
| 广告     | 接入外部广告 SDK（顶部 & 底部广告位）、曝光上报                       |
| 安全     | 登录态过滤器、字符编码过滤器、验证码防刷、SQL 注入防护（预编译）      |

---

## 🗂️ 项目结构

```
src/
└─ com.example.shop
├─ filter          # 字符编码 & 登录态检查
├─ model           # 实体类（User/Product/Cart/Order...）
├─ dao             # 数据访问层
├─ servlet         # 业务控制层
└─ util            # 工具类（DBUtil、验证码生成）

WebContent/
├─ *.jsp             # 视图页面
├─ static/           # 图片、css、js（如有）
└─ WEB-INF/
└─ lib/            # 依赖 jar（jakarta.servlet-api、mysql-connector-j、jstl...）
```

---

## 🚀 快速开始

### 1. 克隆/解压源码到本地

### 2. 数据库初始化
```sql
-- 创建数据库
CREATE DATABASE shop_db DEFAULT CHARSET utf8mb4;

-- 执行项目根目录下的 sql/shop_db.sql 脚本
-- 内含：users/products/carts/orders/order_items/favorites 表 + 演示数据
```

### 3. 修改数据库连接
打开 `src/com.example.shop.util.DBUtil.java`：
```java
private static final String URL = "jdbc:mysql://localhost:3306/shop_db?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
private static final String USER = "root";
private static final String PASSWORD = "你的密码";
```

### 4. 配置 Tomcat
- 版本 ≥ 10，Jakarta 命名空间
- 部署方式：将项目文件夹或 war 包放入 `webapps`
- 启动参数建议：`-Xms256m -Xmx512m`

### 5. 访问首页
```
http://localhost:8080/shop/index.jsp
```
首次使用请先注册账号，验证码 5 分钟内有效。

---

## 🔑 默认账号（演示）

| 用户名 | 密码   | 角色 |
|--------|--------|------|
| admin  | 123456 | 管理员 |

---

## 📸 页面速览

| 页面     | 路径示例                          |
|----------|-----------------------------------|
| 首页（商品分类） | `http://localhost:8080/shop/index.jsp` |
| 登录页   | `/login`                          |
| 商品列表 | `/products.jsp?category=electronics` |
| 购物车   | `/cart`                           |
| 订单列表 | `/order`                          |
| 收藏夹   | `/favorite`                       |
| 个人中心 | `/userInfo.jsp`                   |

> 启动项目后，**访问首页**示例：
> ```
> http://10.100.164.18:8080/shop/login
> ```
> 点击右上角「登录」→ 输入账号 `admin` / 密码 `123456` 即可体验完整流程。

---
## 🧪 二次开发指南

### 新增商品字段
1. 修改 `Product.java` 与数据表
2. 同步更新 `ProductDAO#getProductsByCategory` 的 SQL
3. 前端 `products.jsp` 按需调整

### 接入真实支付
- 在 `PaymentServlet#simulatePayment` 中替换为支付宝/微信 SDK
- 支付回调地址需外网可访问（内网穿透或部署云服务器）

### 日志与监控
- 已集成 SLF4J + Logback，控制台输出 SQL 与异常
- 生产环境请关闭 `DBUtil` 中的 `printStackTrace`

---
## ⚠️ 注意事项

1. **编码**：全项目统一 UTF-8，JSP 页面已加 `<meta charset="UTF-8">`
2. **图片资源**：商品图使用外网 URL，断网时请在 `products.image_url` 替换为本地路径
3. **广告 SDK**：需外网访问 `10.100.164.17:8080`，若无法访问会显示“广告加载失败”，不影响主流程
4. **Session 超时**：默认 30 min，可在 `web.xml` 调整 `<session-timeout>`

---
## 📄 开源协议

MIT License - 可自由修改、商用，保留原作者署名即可。

---
## 🤝 贡献 & 反馈

欢迎提交 Issue / Pull Request，一起完善更多功能（后台管理、秒杀、优惠券、分布式会话...）。

> 如果对你有帮助，给个 ⭐ Star 支持一下！
```
