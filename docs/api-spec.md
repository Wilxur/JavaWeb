# API 规范文档（API Specification）

本文件定义广告系统对外暴露的全部接口，包括：

- 广告获取接口
- 广告曝光日志接口
- 广告点击日志接口
- 浏览行为上报接口
- 用户画像查询接口

所有接口均基于 **RESTful API + JSON**。

---

# 1. 获取广告接口

## GET /api/ads

根据 UID、页面分类、站点信息返回最佳广告。

### 请求参数（Query）

| 参数名 | 类型 | 必填 | 说明 |
|--------|--------|--------|--------|
| uid | string | 是 | 匿名用户 ID |
| category | string | 否 | 页面分类 |
| site | string | 否 | 来源站点 |
| limit | int | 否 | 广告数量，默认 3 |

### 返回示例

```json
{
  "code": 0,
  "message": "ok",
  "data": {
    "ads": [
      {
        "adId": 101,
        "title": "新品智能手机限时优惠",
        "type": "image",
        "imageUrl": "https://ad.example.com/img/phone.jpg",
        "targetUrl": "https://shop.example.com/product/123",
        "category": "tech"
      }
    ]
  }
}
```

---

# 2. 广告曝光日志接口

## POST /api/log/exposure

```json
{
  "uid": "UID123",
  "adId": 101,
  "site": "news",
  "category": "tech",
  "position": "article_sidebar",
  "ts": 1732435200000
}
```

---

# 3. 广告点击日志接口

## POST /api/log/click

```json
{
  "uid": "UID123",
  "adId": 101,
  "site": "shop",
  "category": "tech",
  "ts": 1732435200123
}
```

---

# 4. 浏览行为上报

## POST /api/log/view

```json
{
  "uid": "UID123",
  "category": "fashion",
  "site": "news",
  "refId": 882,
  "ts": 1732435555000
}
```

---

# 5. 用户画像查询

## GET /api/user/profile?uid=UID123

```json
{
  "uid": "UID123",
  "profile": {
    "tech": 0.9,
    "sport": 0.2
  }
}
```

---
