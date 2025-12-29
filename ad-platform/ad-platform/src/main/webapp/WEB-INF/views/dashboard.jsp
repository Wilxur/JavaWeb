<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
    <title>广告管理平台 - 仪表板</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="${pageContext.request.contextPath}/static/icons/bootstrap-icons.css" rel="stylesheet">
    <!-- ECharts CDN -->
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
        }
        .sidebar {
            min-height: 100vh;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 1.5rem;
        }
        .sidebar .nav-link {
            color: rgba(255,255,255,0.8);
            padding: 0.75rem 1rem;
            margin: 0.25rem 0;
            border-radius: 0.375rem;
            transition: all 0.3s;
        }
        .sidebar .nav-link:hover {
            color: white;
            background: rgba(255,255,255,0.1);
        }
        .sidebar .nav-link.active {
            color: white;
            background: rgba(255,255,255,0.2);
        }
        .stat-card {
            border-left: 4px solid #667eea;
        }
        .stat-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            transition: all 0.3s;
        }
        .navbar-brand {
            font-weight: 600;
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- 左侧导航栏 -->
        <nav class="col-md-3 col-lg-2 px-0 sidebar">
            <div class="text-center mb-4">
                <h4 class="text-white">广告管理平台</h4>
            </div>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/dashboard">
                        <i class="bi bi-speedometer2 me-2"></i>仪表板
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/ads">
                        <i class="bi bi-megaphone me-2"></i>广告管理
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reports">
                        <i class="bi bi-bar-chart me-2"></i>数据报表
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/password">
                        <i class="bi bi-lock me-2"></i>修改密码
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
            <!-- 顶部导航栏 -->
            <nav class="navbar navbar-light bg-white rounded shadow-sm mb-4">
                <div class="container-fluid">
                        <span class="navbar-brand mb-0 h5">
                            <i class="bi bi-speedometer2 me-2"></i>仪表板
                        </span>
                    <div class="d-flex align-items-center">
                            <span class="text-muted me-3">
                                <i class="bi bi-person-circle me-1"></i>
                                ${currentUser.username}
                                <span class="badge bg-primary ms-1">${currentUser.role}</span>
                            </span>
                    </div>
                </div>
            </nav>

            <!-- 欢迎信息 -->
            <c:if test="${not empty sessionScope.welcomeMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>欢迎回来！</strong> ${sessionScope.welcomeMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="welcomeMessage" scope="session"/>
            </c:if>

            <!-- 统计卡片 -->
            <div class="row mb-4">
                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">有效广告</h6>
                                    <h3 class="card-text fw-bold">${stats.totalAds}</h3>
                                </div>
                                <i class="bi bi-megaphone fs-1 text-primary opacity-25"></i>
                            </div>
                            <small class="text-success">
                                <i class="bi bi-arrow-up me-1"></i>今日新增 ${stats.todayAds}
                            </small>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">活跃用户</h6>
                                    <h3 class="card-text fw-bold">${stats.activeUsers}</h3>
                                </div>
                                <i class="bi bi-people fs-1 text-success opacity-25"></i>
                            </div>
                            <small class="text-muted">有画像记录的匿名用户</small>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">总展示</h6>
                                    <h3 class="card-text fw-bold">
                                        <fmt:formatNumber value="${stats.totalImpressions}" pattern="#,###"/>
                                    </h3>
                                </div>
                                <i class="bi bi-eye fs-1 text-info opacity-25"></i>
                            </div>
                            <small class="text-muted">累计广告展示次数</small>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">总点击</h6>
                                    <h3 class="card-text fw-bold">
                                        <fmt:formatNumber value="${stats.totalClicks}" pattern="#,###"/>
                                    </h3>
                                </div>
                                <i class="bi bi-hand-index fs-1 text-warning opacity-25"></i>
                            </div>
                            <small class="text-primary">
                                CTR: ${stats.ctr}
                            </small>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 快速操作和图表区域 -->
            <div class="row">
                <div class="col-lg-8 mb-4">
                    <div class="card">
                        <div class="card-header bg-white">
                            <h6 class="mb-0"><i class="bi bi-graph-up me-2"></i>最近7天数据趋势</h6>
                        </div>
                        <div class="card-body">
                            <c:if test="${empty last7Days}">
                                <div class="text-center text-muted py-5">
                                    <i class="bi bi-bar-chart-line fs-1"></i>
                                    <p class="mt-2">暂无最近7天数据</p>
                                    <small>请稍后再试</small>
                                </div>
                            </c:if>

                            <c:if test="${not empty last7Days}">
                                <!-- 折线图容器 -->
                                <div id="dashboardChart" style="height: 300px;"></div>

                                <script>
                                    // 准备数据
                                    var dates = [];
                                    var ctrData = [];
                                    <c:forEach items="${last7Days}" var="data">
                                    dates.push('${data.date}');
                                    ctrData.push(${data.ctr});
                                    </c:forEach>

                                    dates.reverse();
                                    ctrData.reverse();
                                    // 初始化图表
                                    var chart = echarts.init(document.getElementById('dashboardChart'));
                                    var option = {
                                        title: {
                                            text: '最近7天CTR趋势',
                                            left: 'center',
                                            textStyle: { fontSize: 16 }
                                        },
                                        tooltip: {
                                            trigger: 'axis',
                                            formatter: '{b}<br/>CTR: {c}%'
                                        },
                                        xAxis: {
                                            type: 'category',
                                            data: dates,
                                            axisLabel: { rotate: 45 }
                                        },
                                        yAxis: {
                                            type: 'value',
                                            axisLabel: { formatter: '{value}%' }
                                        },
                                        series: [{
                                            data: ctrData,
                                            type: 'line',
                                            smooth: true,
                                            symbol: 'circle',
                                            symbolSize: 8,
                                            lineStyle: {
                                                color: '#667eea',
                                                width: 3
                                            },
                                            itemStyle: {
                                                color: '#667eea'
                                            },
                                            areaStyle: {
                                                color: {
                                                    type: 'linear',
                                                    x: 0, y: 0, x2: 0, y2: 1,
                                                    colorStops: [
                                                        { offset: 0, color: 'rgba(102, 126, 234, 0.3)' },
                                                        { offset: 1, color: 'rgba(102, 126, 234, 0.05)' }
                                                    ]
                                                }
                                            }
                                        }],
                                        grid: {
                                            left: '3%',
                                            right: '4%',
                                            bottom: '3%',
                                            containLabel: true
                                        }
                                    };
                                    chart.setOption(option);

                                    // 响应式
                                    window.addEventListener('resize', function() {
                                        chart.resize();
                                    });
                                </script>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 mb-4">
                    <div class="card">
                        <div class="card-header bg-white">
                            <h6 class="mb-0"><i class="bi bi-lightning me-2"></i>快速操作</h6>
                        </div>
                        <div class="card-body">
                            <div class="d-grid gap-2">
                                <a href="${pageContext.request.contextPath}/ad/new" class="btn btn-primary">
                                    <i class="bi bi-plus-circle me-2"></i>发布新广告
                                </a>
                                <a href="${pageContext.request.contextPath}/ads" class="btn btn-outline-secondary">
                                    <i class="bi bi-list me-2"></i>管理广告
                                </a>
                                <a href="${pageContext.request.contextPath}/reports" class="btn btn-outline-info">
                                    <i class="bi bi-bar-chart me-2"></i>查看报表
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 系统信息 -->
            <div class="row mt-4">
                <div class="col-12">
                    <div class="card bg-light">
                        <div class="card-body">
                            <div class="row text-center">
                                <div class="col-md-3">
                                    <h6 class="text-muted">当前登录用户</h6>
                                    <p class="mb-0 fw-bold">${currentUser.username}</p>
                                </div>
                                <div class="col-md-3">
                                    <h6 class="text-muted">用户角色</h6>
                                    <p class="mb-0">
                                        <span class="badge bg-success">${currentUser.role}</span>
                                    </p>
                                </div>
                                <div class="col-md-3">
                                    <h6 class="text-muted">登录时间</h6>
                                    <p class="mb-0">
                                        <fmt:formatDate value="<%= new java.util.Date() %>" pattern="yyyy-MM-dd HH:mm"/>
                                    </p>
                                </div>
                                <div class="col-md-3">
                                    <h6 class="text-muted">系统版本</h6>
                                    <p class="mb-0">v1.0.0</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>