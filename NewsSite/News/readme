# NewsSite - 新闻发布管理系统

一个基于 Java Web 技术栈开发的新闻内容管理系统，采用 Servlet + JSP 架构，实现了新闻的发布、管理、分类浏览等核心功能，并集成了广告平台接口对接能力。

## 目录

- [项目简介](#项目简介)
- [在线演示](#在线演示)
- [功能特性](#功能特性)
- [技术架构](#技术架构)
- [项目结构](#项目结构)
- [核心功能说明](#核心功能说明)
- [广告平台集成](#广告平台集成)

## 项目简介

NewsSite 是一个企业级新闻发布管理系统，旨在提供完整的新闻内容生命周期管理能力。系统采用经典的 MVC 三层架构设计，代码结构清晰，易于维护和扩展。

### 主要特点

- 完整的用户认证与授权机制
- 灵活的新闻分类管理
- 响应式用户界面设计
- 与第三方广告平台的解耦式集成
- 规范的代码组织和注释

## 在线演示

**访问地址**: http://news.login

## 功能特性

### 用户管理模块

- 用户注册（表单验证、密码加密）
- 用户登录（Session 会话管理）
- 用户退出登录
- 登录状态持久化
- 访问权限控制（Filter 拦截）

### 新闻管理模块

- 新闻列表展示（分页、排序）
- 新闻详情查看
- 新闻发布（富文本编辑器）
- 新闻编辑（权限校验）
- 新闻删除（软删除机制）
- 新闻分类筛选

### 分类导航模块

支持以下新闻分类：

| 分类 | 说明 |
|------|------|
| 科技 | 科技资讯、IT动态 |
| 财经 | 财经新闻、市场分析 |
| 体育 | 体育赛事、运动资讯 |
| 娱乐 | 娱乐八卦、影视资讯 |
| 社会 | 社会新闻、民生话题 |
| 教育 | 教育政策、学术动态 |

### 广告平台集成

- 基于 Meta 标签的上下文传递
- JavaScript SDK 动态加载
- 异步广告渲染
- 内容与广告系统解耦

## 技术架构

### 后端技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| JDK | 17 | Java 开发工具包 |
| Servlet | 6.0 | Web 请求处理 |
| JSP | 3.1 | 视图层模板引擎 |
| JSTL | 3.0 | JSP 标准标签库 |
| JDBC | - | 数据库连接 |
| MySQL | 8.x | 关系型数据库 |
| Tomcat | 11 | Servlet 容器 |
| Maven | 3.8+ | 项目构建工具 |

### 前端技术栈

- HTML5 / CSS3
- JavaScript (ES6+)
- JSP 模板引擎
- 响应式布局设计

### 架构模式

系统采用经典的 MVC 三层架构：

**表现层（Presentation Layer）**
- JSP 页面
- HTML/CSS/JavaScript
- 负责用户界面展示

**控制层（Controller Layer）**
- Servlet 控制器
- Filter 过滤器
- 负责请求分发和处理

**业务层（Service Layer）**
- 业务逻辑处理
- 事务管理
- 数据验证

**数据访问层（DAO Layer）**
- 数据库操作
- SQL 语句执行
- 结果集处理

**数据层（Database Layer）**
- MySQL 数据库
- 数据持久化

## 项目结构
```
NewsSite/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── news/
│   │   │               ├── controller/      # Servlet 控制器
│   │   │               │   ├── HomeServlet.java
│   │   │               │   ├── NewsServlet.java
│   │   │               │   ├── LoginServlet.java
│   │   │               │   └── RegisterServlet.java
│   │   │               ├── service/         # 业务逻辑层
│   │   │               │   ├── UserService.java
│   │   │               │   └── NewsService.java
│   │   │               ├── dao/             # 数据访问接口
│   │   │               │   ├── UserDao.java
│   │   │               │   └── NewsDao.java
│   │   │               ├── dao/impl/        # DAO 实现
│   │   │               │   ├── UserDaoImpl.java
│   │   │               │   └── NewsDaoImpl.java
│   │   │               ├── model/           # 实体类
│   │   │               │   ├── User.java
│   │   │               │   ├── News.java
│   │   │               │   └── Category.java
│   │   │               ├── filter/          # 过滤器
│   │   │               │   ├── LoginFilter.java
│   │   │               │   └── CharacterEncodingFilter.java
│   │   │               └── util/            # 工具类
│   │   │                   └── DBUtil.java
│   │   ├── resources/
│   │   │   └── db.properties               # 数据库配置
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   ├── web.xml                 # Web 配置文件
│   │       │   └── jsp/                    # JSP 页面
│   │       │       ├── home.jsp
│   │       │       ├── detail.jsp
│   │       │       ├── login.jsp
│   │       │       ├── register.jsp
│   │       │       └── publish.jsp
│   │       ├── css/                        # 样式文件
│   │       │   └── style.css
│   │       └── js/                         # JavaScript 文件
│   └── test/
│       └── java/                           # 单元测试
├── docs/                                   # 项目文档
├── sql/                                    # 数据库脚本
│   └── init.sql
├── pom.xml                                 # Maven 配置
└── README.md                               # 项目说明
```

## 核心功能说明

### 用户认证流程

1. 用户访问登录页面
2. 输入用户名和密码
3. 系统验证用户信息
4. 验证成功创建 Session
5. 重定向到首页
6. 后续请求携带 Session 信息

### 新闻发布流程

1. 用户登录系统
2. 点击发布新闻按钮
3. 填写新闻标题、内容、分类
4. 提交表单数据
5. 系统验证表单信息
6. 保存新闻到数据库
7. 返回新闻列表页面

### 权限控制机制

使用 `LoginFilter` 实现基于 Session 的权限控制：

**需要登录的操作**
- `/publish` - 发布新闻
- `/edit` - 编辑新闻
- `/delete` - 删除新闻

**公开访问的页面**
- `/home` - 首页
- `/detail` - 新闻详情
- `/login` - 登录页面
- `/register` - 注册页面

### 数据库操作规范

所有数据库操作统一通过 `DBUtil` 工具类管理：

- 连接池管理
- 自动资源释放
- 异常处理统一化
- SQL 注入防护

## 广告平台集成

### 集成架构

本系统实现了与广告平台的松耦合集成方案，通过标准化的接口协议实现内容系统与广告系统的分离。

### 实现方式

#### 1. Meta 标签注入

在新闻详情页的 `<head>` 中注入分类信息：
```html
<meta name="ad-category" content="${news.category}">
<meta name="ad-keywords" content="${news.keywords}">
<meta name="ad-page-type" content="article-detail">
```

#### 2. SDK 加载

异步加载广告平台的 JavaScript SDK：
```html
<script async src="http://10.100.164.17:8080/ad-platform/static/js/sdk.js"></script>
```

#### 3. 广告容器

在页面中预留广告展示位置：
```html
<div id="ad-container" class="ad-slot" data-ad-position="sidebar"></div>
```

### 技术优势

| 优势 | 说明 |
|------|------|
| 解耦设计 | 内容系统与广告系统独立开发和部署 |
| 灵活扩展 | 可轻松切换或集成多个广告平台 |
| 性能优化 | 异步加载不影响页面主要内容的渲染 |
| 上下文感知 | 基于新闻分类提供精准的广告投放上下文 |

### 数据流程

1. 新闻页面加载完成
2. 系统注入 Meta 信息（新闻分类、关键词等）
3. 广告 SDK 读取 Meta 标签
4. SDK 调用广告平台 API
5. 广告平台根据上下文决策
6. 返回匹配的广告内容
7. SDK 将广告渲染到页面容器

---

**项目说明**: 本项目为 Java Web 课程设计项目，重点展示 MVC 架构设计、权限控制机制和系统集成能力。
