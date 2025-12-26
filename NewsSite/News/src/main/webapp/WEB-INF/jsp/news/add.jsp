<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>发布新闻</title>

    <style>
        /* ===== 基础 ===== */
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            background-color: #f4f6fb;
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI",
            "PingFang SC", "Microsoft YaHei", Arial, sans-serif;
            color: #1f2937;
            line-height: 1.7;
        }

        a {
            color: #2563eb;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        /* ===== 顶部 ===== */
        .header {
            background: linear-gradient(90deg, #2563eb, #3b82f6);
            padding: 20px 0;
            color: white;
            box-shadow: 0 4px 16px rgba(37, 99, 235, 0.25);
        }

        .header .container {
            max-width: 900px;
            margin: 0 auto;
            padding: 0 20px;
            font-size: 18px;
            font-weight: 600;
        }

        /* ===== 主体 ===== */
        .container {
            max-width: 900px;
            margin: 32px auto;
            padding: 0 20px;
        }

        /* ===== 表单卡片 ===== */
        .form-card {
            background: white;
            border-radius: 16px;
            padding: 36px 40px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.06);
        }

        .form-card h2 {
            margin-bottom: 26px;
            font-size: 26px;
        }

        /* ===== 表单项 ===== */
        .form-group {
            margin-bottom: 22px;
        }

        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #374151;
            font-weight: 500;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            font-size: 14px;
            font-family: inherit;
        }

        .form-group textarea {
            resize: vertical;
        }

        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #2563eb;
            box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.15);
        }

        /* ===== 错误提示 ===== */
        .error-msg {
            background-color: #fee2e2;
            color: #b91c1c;
            padding: 12px 16px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-size: 14px;
        }

        /* ===== 按钮 ===== */
        .submit-btn {
            margin-top: 10px;
            padding: 12px 26px;
            background: linear-gradient(90deg, #2563eb, #3b82f6);
            border: none;
            border-radius: 10px;
            color: white;
            font-size: 15px;
            font-weight: 500;
            cursor: pointer;
        }

        .submit-btn:hover {
            background: linear-gradient(90deg, #1d4ed8, #2563eb);
        }

        /* ===== 返回 ===== */
        .back-link {
            margin-top: 22px;
            display: inline-block;
            font-size: 14px;
        }

        /* ===== 响应式 ===== */
        @media (max-width: 768px) {
            .form-card {
                padding: 24px;
            }
        }
    </style>
</head>
<body>

<!-- 顶部 -->
<div class="header">
    <div class="container">
        发布新闻
    </div>
</div>

<!-- 主体 -->
<div class="container">

    <div class="form-card">

        <h2>发布新闻</h2>

        <!-- 错误提示 -->
        <c:if test="${not empty error}">
            <div class="error-msg">
                    ${error}
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/news/add">

            <div class="form-group">
                <label>标题</label>
                <input type="text" name="title" required />
            </div>

            <div class="form-group">
                <label>分类</label>
                <select name="categoryId" required>
                    <option value="">请选择分类</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}">
                                ${c.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label>内容</label>
                <textarea name="content" rows="10" required></textarea>
            </div>

            <button class="submit-btn" type="submit">
                发布新闻
            </button>

        </form>

        <a class="back-link" href="${pageContext.request.contextPath}/home">
            ← 返回首页
        </a>

    </div>

</div>

</body>
</html>