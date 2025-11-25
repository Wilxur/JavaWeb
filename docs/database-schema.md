# 数据库结构文档（Database Schema）

## 广告系统数据库 ad_system

### Ad 表

| 字段 | 类型 | 说明 |
|--------|--------|--------|
| ad_id | int PK | 广告 ID |
| title | varchar | 标题 |
| type | enum | text/image/video |
| media_url | varchar | 媒体地址 |
| target_url | varchar | 点击跳转 |
| category_id | int | 分类 |
| created_at | datetime | 创建时间 |

### Category 表

| category_id | int PK |
| code | varchar |
| name | varchar |

### UserProfile

| uid | varchar PK |
| profile_json | json |

### 日志表（曝光/点击）

| log_id | bigint PK |
| uid | varchar |
| ad_id | int |
| site | varchar |
| ts | datetime |

---

## 内容站点数据库（购物 / 视频 / 新闻）

### Product（购物）

| product_id | int PK |
| name | varchar |
| price | decimal |
| category | varchar |

### Video（视频）

| video_id | int PK |
| title | varchar |
| file_url | varchar |
| category | varchar |

### News（新闻）

| news_id | int PK |
| title | varchar |
| content | text |
| category | varchar |

---
