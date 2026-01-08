# 广告管理平台

JavaWeb课程设计项目 - 匿名用户精准广告投放生态系统（服务端）

---

## 项目简介

本项目为广告管理子系统，是整个精准广告投放生态系统的核心后端。提供广告创建、管理、智能推荐、跨站用户追踪与行为分析等完整功能。通过RESTful API为视频、购物、新闻等站点提供广告素材及个性化推荐服务。

**开发者**：王和宵（后端核心）  
**工作占比**：约25%

---

## 技术栈

- **Java**：JDK 21
- **Web框架**：原生Jakarta Servlet 6.0 + JSP
- **数据库**：MySQL 8.0
- **构建工具**：Maven
- **核心依赖**：
    - MySQL Connector Java 8.0.33
    - Gson 2.10.1（JSON处理）
    - Jakarta Servlet API 6.1.0
    - Jakarta JSTL 3.0.1（JSP标签库）

---

## 项目结构

```
src/main/java/
├── com.adplatform
│   ├── controller          # Servlet控制器层
│   │   ├── AdApiServlet    # 核心广告API接口
│   │   ├── AdListServlet   # 广告列表管理
│   │   ├── AuthServlet     # 登录认证
│   │   ├── DashboardServlet# 仪表板
│   │   ├── TrackClickServlet  # 点击追踪
│   │   └── TrackImpressionServlet # 展示追踪
│   ├── dao                 # 数据访问层
│   ├── model               # 实体类
│   ├── service             # 业务逻辑层
│   │   ├── impl
│   │   │   └── AdRecommendationServiceImpl # 智能推荐算法
│   ├── filter              # 过滤器（登录验证）
│   └── util                # 工具类（DBUtil、加密等）

src/main/resources/
├── db.properties           # 数据库配置

src/main/webapp/
├── WEB-INF/
│   ├── views/              # JSP页面
│   │   ├── login.jsp       # 登录页
│   │   ├── ads.jsp         # 广告列表
│   │   ├── ad-form.jsp     # 广告表单
│   │   ├── dashboard.jsp   # 仪表板
│   │   └── reports.jsp     # 数据报表
│   └── web.xml             # Web配置
└── static/                 # 静态资源（Bootstrap、JS SDK）
```

---

## 核心功能

### 1. 用户认证系统
- **登录/注册**：MD5密码加密，Session管理
- **权限控制**：过滤器拦截后台页面
- **记住我功能**：Cookie保存登录状态（7天）

### 2. 广告管理（CRUD）
- **广告类型**：支持文字、图片、视频三种格式
- **字段**：标题、内容（URL或文本）、分类、标签（JSON数组）
- **状态控制**：上线/下线切换
- **权限验证**：仅允许操作本人发布的广告

### 3. 智能推荐引擎
**算法逻辑**（`AdRecommendationServiceImpl`）：
- 基于用户兴趣画像（`user_interests`表，score权重）
- 支持站点类型过滤：video站仅推视频、shopping站仅推图片、news站仅推文字
- 优先展示Top5兴趣分类，当前页面分类权重×1.5倍
- 冷启动兜底：返回最新广告
- 随机排序避免重复曝光

### 4. 跨站用户追踪
- **UID生成**：`ad_platform_uid` Cookie，365天有效期
- **行为权重**：
    - 页面访问：+10分
    - 广告展示：+1分
    - 广告点击：+5分
- **兴趣衰减**：非当前分类兴趣分-1（防止旧兴趣累积）

### 5. 数据追踪与报表
- **实时上报**：展示（1x1透明GIF）、点击（302重定向）
- **CTR计算**：精确到天，支持趋势图
- **数据可视化**：ECharts折线图、表格/图表切换

---

## API文档

### 接口总览

| 接口 | 方法 | 用途 | 调用方 |
|------|------|------|--------|
| `/api/ad/get` | GET | 获取推荐广告 | 各站点前端 |
| `/api/track/impression` | GET | 上报广告展示 | SDK自动调用 |
| `/api/track/click` | GET | 上报广告点击 | SDK自动调用 |
| `/api/user/interest` | POST | 更新用户兴趣 | 各站点手动调用 |

---

### 1. 获取推荐广告

**请求URL**：
```
GET /ad-platform/api/ad/get?uid={用户ID}&category={页面分类}&site={站点类型}
```

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `uid` | String | 是 | 用户唯一标识（Cookie或URL参数） |
| `category` | String | 是 | **中文分类名**，如"电子产品"、"体育" |
| `site` | String | 是 | 站点类型：`shopping`、`video`、`news` |
| `callback` | String | 否 | JSONP回调函数名（跨域时使用） |

**分类映射表**（SDK自动转换）：

| 中文分类 | 英文映射 | 适用场景 |
|----------|----------|----------|
| 电子产品 / 科技 / 教育 | `electronics` | 数码、科技类页面 |
| 服装鞋帽 | `clothing` | 服饰类页面 |
| 食品饮料 | `food` | 食品类页面 |
| 美妆护肤 / 娱乐 | `beauty` | 美容、娱乐类页面 |
| 家居用品 / 财经 / 社会 | `home` | 家居、财经类页面 |
| 运动户外 / 体育 | `sports` | 运动类页面 |

**成功响应**：
```json
{
  "success": true,
  "ad": {
    "id": 15,
    "title": "小米智能手环8 Pro",
    "adType": "image",
    "content": "https://example.com/ad-image.jpg",
    "category": "electronics",
    "tags": "[\"运动\",\"健康\",\"智能\"]",
    "status": 1,
    "createdAt": "2025-12-17T10:30:00"
  },
  "adId": 15
}
```

**无广告响应**：
```json
{
  "success": false,
  "message": "暂无可用广告"
}
```

**调用示例**：

```javascript
// JavaScript调用示例
const API_HOST = 'http://10.100.164.17:8080/ad-platform';
const site = 'shopping'; // 或'video'、'news'

function loadAd(containerId, pageCategory) {
  const uid = getCookie('ad_platform_uid') || generateUID();
  
  fetch(`${API_HOST}/api/ad/get?uid=${encodeURIComponent(uid)}&category=${encodeURIComponent(pageCategory)}&site=${site}`)
    .then(response => response.json())
    .then(data => {
      if (data.success && data.ad) {
        // 渲染广告
        renderAd(containerId, data.ad);
        
        // 上报展示（会自动调，可手动）
        reportImpression(data.ad.id, uid, pageCategory);
      }
    });
}

function reportImpression(adId, uid, category) {
  const url = `${API_HOST}/api/track/impression?uid=${uid}&adId=${adId}&site=${site}&category=${category}`;
  new Image().src = url; // 使用Image标签触发GET请求
}
```

---

### 2. 上报广告展示

**请求URL**：
```
GET /ad-platform/api/track/impression?uid={用户ID}&adId={广告ID}&site={站点}&category={分类}
```

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `uid` | String | 是 | 用户ID |
| `adId` | Integer | 是 | 广告ID |
| `site` | String | 是 | 站点类型 |
| `category` | String | 否 | 页面中文分类（用于更新兴趣） |

**响应**：返回1x1透明GIF图片（204状态）

**调用示例**：

```javascript
// 方式1：使用Image标签（推荐，自动处理跨域）
const impressionUrl = `http://10.100.164.17:8080/ad-platform/api/track/impression?uid=${uid}&adId=${adId}&site=${site}&category=${category}`;

const img = new Image();
img.src = impressionUrl;
document.body.appendChild(img);
setTimeout(() => img.remove(), 1000); // 1秒后移除

// 方式2：使用fetch（需处理CORS）
fetch(impressionUrl, { mode: 'no-cors' }); // 注意：no-cors模式无法读取响应
```

---

### 3. 上报广告点击

**请求URL**：
```
GET /ad-platform/api/track/click?uid={用户ID}&adId={广告ID}&site={站点}&category={分类}&redirect={落地页URL}
```

**请求参数**：

| 参数 | 类型 | 必填 | 说明 |
|------|------|------|------|
| `uid` | String | 是 | 用户ID |
| `adId` | Integer | 是 | 广告ID |
| `site` | String | 是 | 站点类型 |
| `category` | String | 否 | 页面分类 |
| `redirect` | String | 否 | 点击后跳转的URL（需URL编码） |

**响应**：302重定向到`redirect`参数指定的URL

**调用示例**：

```javascript
// 点击事件处理
function handleAdClick(adId, redirectUrl, category) {
  const clickUrl = `http://10.100.164.17:8080/ad-platform/api/track/click?uid=${uid}&adId=${adId}&site=${site}&category=${category}&redirect=${encodeURIComponent(redirectUrl)}`;
  
  // 直接跳转（302重定向）
  window.location.href = clickUrl;
  
  // 或者新开标签页
  window.open(clickUrl, '_blank');
}
```

---

### 4. 更新用户兴趣（高级）

**请求URL**：
```
POST /ad-platform/api/user/interest
```

**请求体**（JSON）：
```json
{
  "uid": "uid-123456789",
  "category": "电子产品"
}
```

**成功响应**：
```json
{
  "success": true,
  "message": "兴趣更新成功"
}
```

**调用示例**：

```javascript
// 当用户浏览页面时手动调用
fetch(`${API_HOST}/api/user/interest`, {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    uid: uid,
    category: pageCategory
  }),
  credentials: 'include'
});
```

---

## 部署配置

### 1. 数据库配置

**完整建表SQL**：

```sql
USE ad_platform;

-- 用户表
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL COMMENT '登录用户名',
    password VARCHAR(255) NOT NULL COMMENT '加密后的密码',
    email VARCHAR(100) COMMENT '邮箱',
    role VARCHAR(20) DEFAULT 'advertiser' COMMENT '角色：advertiser',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
);

-- 广告表
CREATE TABLE ads (
    id INT PRIMARY KEY AUTO_INCREMENT,
    advertiser_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    ad_type ENUM('text','image','video') NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(50) NOT NULL,
    tags JSON,
    status TINYINT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_category (category)
);

-- 匿名用户表
CREATE TABLE anonymous_users (
    uid VARCHAR(64) PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 用户兴趣画像
CREATE TABLE user_interests (
    uid VARCHAR(64) NOT NULL,
    category VARCHAR(50) NOT NULL,
    score INT DEFAULT 0,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (uid, category),
    INDEX idx_score (score)
);

-- 广告展示日志
CREATE TABLE ad_impressions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ad_id INT NOT NULL,
    uid VARCHAR(64),
    site VARCHAR(50),
    page_category VARCHAR(50),
    shown_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_uid (uid),
    INDEX idx_ad_id (ad_id)
);

-- 广告点击日志
CREATE TABLE ad_clicks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ad_id INT NOT NULL,
    uid VARCHAR(64),
    site VARCHAR(50),
    clicked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_uid (uid),
    INDEX idx_ad_id (ad_id)
);
```

**配置`src/main/resources/db.properties`**：
```properties
db.url=jdbc:mysql://{数据库IP}:3306/ad_platform?useSSL=false&characterEncoding=utf8&serverTimezone=UTC
db.username=ad_admin
db.password=你的密码
```

### 2. Maven打包

```bash
mvn clean package
```

生成`ad-platform-1.0-SNAPSHOT.war`文件

### 3. Tomcat部署

将WAR包放入Tomcat `webapps`目录，启动后访问：
```
http://{服务器IP}:8080/ad-platform
```

**默认登录账号**（需先注册）：
- 用户名：`admin`
- 密码：`123456`（建议首次登录后修改）

---

## 使用说明

### 1. 发布广告

1. 登录后进入"广告管理"页面
2. 点击"发布新广告"按钮
3. 填写表单：
    - **标题**：广告显示名称
    - **类型**：text（文字）、image（图片URL）、video（视频URL）
    - **内容**：根据类型填写文本或URL
    - **分类**：从下拉选择（对应各站点页面分类）
    - **标签**：JSON数组格式，如`["科技","数码"]`
4. 提交后广告自动上线

> **注意**：视频广告需在各站点页面通过SDK渲染，图片/文字广告直接由SDK插入DOM

### 2. 查看数据报表

1. 登录后点击"数据报表"菜单
2. 可筛选日期范围（默认最近7天）
3. 查看：
    - 汇总指标：总展示、总点击、平均CTR
    - 趋势图表：支持表格/图表切换
    - 环比变化：每日CTR对比

---

## 注意事项

1. **CORS白名单**：默认允许`localhost:8080`及`10.100.164.*`网段，修改请编辑`AdApiServlet`和`TrackImpressionServlet`中的`ALLOWED_ORIGINS`
2. **Cookie跨域**：确保各站点使用HTTP协议（非HTTPS）且端口一致，否则Cookie无法共享
3. **推荐算法调优**：可修改`AdRecommendationServiceImpl`中的`TOP_INTERESTS_LIMIT`和`CURRENT_CATEGORY_BOOST`参数
4. **日志清理**：`ad_impressions`和`ad_clicks`表数据量较大，建议定期归档历史数据（如>90天）
5. **密码安全**：当前使用MD5加密，生产环境建议升级为BCrypt
