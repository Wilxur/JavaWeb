<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>广告管理</title>
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
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div class="d-flex align-items-center">
                    <h2 class="mb-0">我的广告列表</h2>
                    <select id="adTypeFilter" class="form-select ms-3" style="width: 150px;">
                        <option value="all">全部类型</option>
                        <option value="text">文字广告</option>
                        <option value="image">图片广告</option>
                        <option value="video">视频广告</option>
                    </select>
                </div>
                <a href="${pageContext.request.contextPath}/ad/new" class="btn btn-primary">
                    <i class="bi bi-plus-circle me-2"></i>发布新广告
                </a>
            </div>

            <!-- 操作提示 -->
            <c:if test="${param.deleted != null}">
                <div class="alert alert-success">广告已删除</div>
            </c:if>
            <c:if test="${param.error != null}">
                <div class="alert alert-danger">操作失败：${param.error}</div>
            </c:if>

            <!-- 广告列表表格 -->
            <div class="card">
                <div class="card-body">
                    <c:if test="${empty ads}">
                        <div class="text-center py-5 text-muted">
                            <i class="bi bi-megaphone fs-1"></i>
                            <p class="mt-3">您还没有发布任何广告</p>
                            <a href="${pageContext.request.contextPath}/ad/new" class="btn btn-primary mt-2">发布第一个广告</a>
                        </div>
                    </c:if>

                    <c:if test="${not empty ads}">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <span id="filterCount" class="text-muted">正在加载...</span>
                        </div>
                        <div class="table-responsive" style="max-height: 65vh; overflow-y: auto;">
                            <table class="table table-hover">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>标题</th>
                                    <th>类型</th>
                                    <th>分类</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${ads}" var="ad">
                                    <tr>
                                        <td>${ad.id}</td>
                                        <td>${ad.title}</td>
                                        <td>
                                        <span class="badge bg-info">
                                                ${ad.adType == 'text' ? '文字' : ad.adType == 'image' ? '图片' : '视频'}
                                        </span>
                                        </td>
                                        <td>${ad.category}</td>
                                        <td>
                                        <span class="badge ${ad.status == 1 ? 'bg-success' : 'bg-secondary'}">
                                                ${ad.status == 1 ? '上线' : '下线'}
                                        </span>
                                        </td>
                                        <td>${ad.createdAt}</td>
                                        <td>
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/ad/edit?id=${ad.id}"
                                                   class="btn btn-outline-primary">编辑</a>
                                                <c:choose>
                                                    <c:when test="${ad.status == 1}">
                                                        <a href="${pageContext.request.contextPath}/ad/toggle?id=${ad.id}&status=0"
                                                           class="btn btn-outline-warning">下线</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <a href="${pageContext.request.contextPath}/ad/toggle?id=${ad.id}&status=1"
                                                           class="btn btn-outline-success">上线</a>
                                                    </c:otherwise>
                                                </c:choose>
                                                <a href="${pageContext.request.contextPath}/ad/delete?id=${ad.id}"
                                                   class="btn btn-outline-danger"
                                                   onclick="return confirm('确定要删除这条广告吗？')">删除</a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- 本地Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
<!-- 自动消失提示 + 分类筛选脚本 -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const filterSelect = document.getElementById('adTypeFilter');
        const tableRows = document.querySelectorAll('tbody tr');
        const filterCount = document.getElementById('filterCount');

        function updateCount(visibleCount, totalCount) {
            if (filterCount) {
                if (visibleCount === totalCount) {
                    filterCount.textContent = `共 ${totalCount} 条广告`;
                } else {
                    filterCount.textContent = `显示 ${visibleCount} / ${totalCount} 条广告`;
                }
            }
        }

        function filterAds() {
            const selectedType = filterSelect.value;
            const totalCount = tableRows.length;
            let visibleCount = 0;

            tableRows.forEach(row => {
                const typeText = row.cells[2].querySelector('.badge').textContent.trim();
                let adType = '';
                if (typeText === '文字') adType = 'text';
                else if (typeText === '图片') adType = 'image';
                else if (typeText === '视频') adType = 'video';

                if (selectedType === 'all' || adType === selectedType) {
                    row.style.display = '';
                    visibleCount++;
                } else {
                    row.style.display = 'none';
                }
            });

            updateCount(visibleCount, totalCount);
        }

        filterSelect.addEventListener('change', filterAds);

        const totalCount = tableRows.length;
        let initialCount = 0;
        tableRows.forEach(() => initialCount++);
        updateCount(initialCount, totalCount);

        // 自动消失提示（原有功能）
        const successAlert = document.querySelector('.alert-success');
        if (successAlert) {
            setTimeout(function() {
                successAlert.classList.add('fade');
                successAlert.style.opacity = '0';
                setTimeout(function() {
                    successAlert.remove();
                }, 500);
            }, 3000);
        }
    });
</script>
</body>
</html>