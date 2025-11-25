# UID 设计文档（UID Design）

系统使用 LocalStorage 生成跨站统一 UID。

格式：

```
UID-{随机字符串}-{时间戳}
```

示例：

```
UID-f39ab2-1732441992000
```

公共脚本：

```javascript
(function () {
    function getUID() {
        let uid = localStorage.getItem("AD_UID");
        if (!uid) {
            uid = "UID-" + Math.random().toString(36).substring(2) + Date.now();
            localStorage.setItem("AD_UID", uid);
        }
        return uid;
    }
    window.AD_UID = getUID();
})();
```

所有 API 必须带 UID 字段。

---
