<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>${empty ad ? '发布新广告' : '编辑广告'}</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 本地Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .sidebar {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        }
        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 0.75rem 1rem;
        }
        .sidebar .nav-link.active {
            background: rgba(255,255,255,0.2);
            color: white;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- 左侧导航栏 -->
        <nav class="col-md-3 col-lg-2 px-0 sidebar">
            <div class="text-center mb-4 pt-4">
                <h4 class="text-white">广告管理平台</h4>
            </div>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard">
                        <i class="bi bi-speedometer2 me-2"></i>仪表板
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/ads">
                        <i class="bi bi-megaphone me-2"></i>广告管理
                    </a>
                </li>
                <li class="nav-item mt-4">
                    <a class="nav-link text-warning" href="${pageContext.request.contextPath}/logout" onclick="return confirm('确定要退出登录吗？')">
                        <i class="bi bi-box-arrow-right me-2"></i>退出登录
                    </a>
                </li>
            </ul>
        </nav>

        <!-- 主内容区 -->
        <main class="col-md-9 col-lg-10 px-4 py-4">
            <div class="card">
                <div class="card-header">
                    <h4>${empty ad ? '发布新广告' : '编辑广告'}</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/ad/save" method="post">
                        <!-- 隐藏域：广告ID（编辑时有用） -->
                        <c:if test="${not empty ad}">
                            <input type="hidden" name="id" value="${ad.id}">
                        </c:if>

                        <!-- 标题 -->
                        <div class="mb-3">
                            <label for="title" class="form-label">广告标题</label>
                            <input type="text" class="form-control" id="title" name="title"
                                   value="${ad.title}" required maxlength="255">
                        </div>

                        <!-- 广告类型 -->
                        <div class="mb-3">
                            <label for="adType" class="form-label">广告类型</label>
                            <select class="form-select" id="adType" name="adType" required>
                                <option value="text" ${ad.adType == 'text' ? 'selected' : ''}>文字</option>
                                <option value="image" ${ad.adType == 'image' ? 'selected' : ''}>图片</option>
                                <option value="video" ${ad.adType == 'video' ? 'selected' : ''}>视频</option>
                            </select>
                        </div>

                        <!-- 内容 -->
                        <div class="mb-3">
                            <label for="content" class="form-label">广告内容</label>
                            <textarea class="form-control" id="content" name="content" rows="5" required
                                      placeholder="文字内容、图片URL或视频URL">${ad.content}</textarea>
                            <div class="form-text">
                                文字：直接输入内容 | 图片/视频：填写可访问的URL地址
                            </div>
                        </div>

                        <!-- 分类（下拉选择） -->
                        <div class="mb-3">
                            <label for="category" class="form-label">广告分类 *</label>
                            <select class="form-select" id="category" name="category" required>
                                <option value="">请选择分类</option>
                                <c:forEach items="${categories}" var="cat">
                                    <option value="${cat.value}" ${ad.category == cat.value ? 'selected' : ''}>
                                            ${cat.label}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 标签 -->
                        <div class="mb-3">
                            <label for="tags" class="form-label">标签 *</label>
                            <input type="text" class="form-control" id="tags" name="tags"
                                   value="${ad.tags}" placeholder='["科技","数码"]' required>
                            <div class="form-text">JSON格式，如：["科技","数码"]</div>
                        </div>

                        <!-- 按钮 -->
                        <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                            <a href="${pageContext.request.contextPath}/ads" class="btn btn-secondary me-md-2">取消</a>
                            <button type="submit" class="btn btn-primary">${empty ad ? '发布' : '保存'}</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- 本地Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>