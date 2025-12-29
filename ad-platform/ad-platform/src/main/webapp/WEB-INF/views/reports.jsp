<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<html>
<head>
    <title>数据报表</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- ECharts CDN -->
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.3/dist/echarts.min.js"></script>
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
        .stat-card {
            border-left: 4px solid #667eea;
            transition: all 0.3s;
        }
        .stat-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        /* 图表容器样式 */
        .chart-container {
            background: #f8f9fa;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 20px;
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
                    <a class="nav-link" href="${pageContext.request.contextPath}/ads">
                        <i class="bi bi-megaphone me-2"></i>广告管理
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/reports">
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
            <!-- 筛选条件 -->
            <div class="card mb-4">
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/reports" method="get" class="row g-3">
                        <div class="col-md-4">
                            <label for="startDate" class="form-label">开始日期</label>
                            <input type="date" class="form-control" id="startDate" name="startDate"
                                   value="${report.filter.startDate}">
                        </div>
                        <div class="col-md-4">
                            <label for="endDate" class="form-label">结束日期</label>
                            <input type="date" class="form-control" id="endDate" name="endDate"
                                   value="${report.filter.endDate}">
                        </div>
                        <div class="col-md-4 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary me-2">查询</button>
                            <a href="${pageContext.request.contextPath}/reports" class="btn btn-secondary">重置</a>
                        </div>
                    </form>
                </div>
            </div>

            <!-- 汇总卡片 -->
            <div class="row mb-4">
                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">总展示</h6>
                                    <h3 class="card-text fw-bold">
                                        <fmt:formatNumber value="${report.summary.totalImpressions}" pattern="#,###"/>
                                    </h3>
                                </div>
                                <i class="bi bi-eye fs-1 text-info opacity-25"></i>
                            </div>
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
                                        <fmt:formatNumber value="${report.summary.totalClicks}" pattern="#,###"/>
                                    </h3>
                                </div>
                                <i class="bi bi-hand-index fs-1 text-warning opacity-25"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">平均CTR</h6>
                                    <h3 class="card-text fw-bold">${report.summary.avgCtr}</h3>
                                </div>
                                <i class="bi bi-graph-up fs-1 text-success opacity-25"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-3">
                    <div class="card stat-card h-100">
                        <div class="card-body">
                            <div class="d-flex justify-content-between align-items-center">
                                <div>
                                    <h6 class="card-title text-muted mb-1">统计天数</h6>
                                    <h3 class="card-text fw-bold">${fn:length(report.stats)}</h3>
                                </div>
                                <i class="bi bi-calendar fs-1 text-primary opacity-25"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 数据展示（带图表/表格切换） -->
            <div class="card">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h6 class="mb-0"><i class="bi bi-table me-2"></i>详细数据</h6>
                        <!-- 切换按钮 -->
                        <div class="btn-group btn-group-sm" role="group">
                            <button type="button" class="btn btn-outline-primary active" id="tableBtn">
                                <i class="bi bi-table"></i> 表格
                            </button>
                            <button type="button" class="btn btn-outline-primary" id="chartBtn">
                                <i class="bi bi-graph-up"></i> 图表
                            </button>
                        </div>
                    </div>
                </div>

                <div class="card-body">
                    <!-- 表格视图（默认显示） -->
                    <div id="tableView">
                        <c:if test="${empty report.stats}">
                            <div class="text-center py-5 text-muted">
                                <i class="bi bi-bar-chart fs-1"></i>
                                <p class="mt-3">暂无数据</p>
                                <p class="text-muted">请检查日期范围或稍后再试</p>
                            </div>
                        </c:if>

                        <c:if test="${not empty report.stats}">
                            <div class="table-responsive">
                                <table class="table table-hover align-middle">
                                    <thead>
                                    <tr>
                                        <th>日期</th>
                                        <th>展示量</th>
                                        <th>点击量</th>
                                        <th>CTR</th>
                                        <th>环比变化</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${report.stats}" var="data" varStatus="status">
                                        <tr>
                                            <td>${data.date}</td>
                                            <td><fmt:formatNumber value="${data.impressions}" pattern="#,###"/></td>
                                            <td><fmt:formatNumber value="${data.clicks}" pattern="#,###"/></td>
                                            <td>${data.ctr}%</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${status.index > 0 && prev.ctr > 0.001}">
                                                        <c:set var="prev" value="${report.stats[status.index - 1]}"/>
                                                        <c:set var="change" value="${((data.ctr - prev.ctr) / prev.ctr * 100)}"/>
                                                        <span class="badge ${change >= 0 ? 'bg-success' : 'bg-danger'}">
                                                            ${change >= 0 ? '+' : ''}<fmt:formatNumber value="${change}" pattern="0.0"/>%
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:if>
                    </div>

                    <!-- 图表视图（默认隐藏） -->
                    <div id="chartView" style="display: none;">
                        <c:if test="${empty report.stats}">
                            <div class="text-center py-5 text-muted">
                                <i class="bi bi-bar-chart fs-1"></i>
                                <p class="mt-3">暂无数据可图表化</p>
                            </div>
                        </c:if>

                        <c:if test="${not empty report.stats}">
                            <!-- 上图：展示量+点击量 -->
                            <div class="chart-container">
                                <h6 class="mb-3"><i class="bi bi-graph-up me-2"></i>展示量与点击量</h6>
                                <div id="reportsChartQuantity" style="height: 300px;"></div>
                            </div>

                            <!-- 下图：CTR -->
                            <div class="chart-container">
                                <h6 class="mb-3"><i class="bi bi-percent me-2"></i>点击率(CTR)</h6>
                                <div id="reportsChartCTR" style="height: 200px;"></div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

            <!-- 切换脚本 -->
            <script>
                document.getElementById('tableBtn').addEventListener('click', function() {
                    this.classList.add('active');
                    document.getElementById('chartBtn').classList.remove('active');
                    document.getElementById('tableView').style.display = 'block';
                    document.getElementById('chartView').style.display = 'none';
                });

                document.getElementById('chartBtn').addEventListener('click', function() {
                    this.classList.add('active');
                    document.getElementById('tableBtn').classList.remove('active');
                    document.getElementById('tableView').style.display = 'none';
                    document.getElementById('chartView').style.display = 'block';

                    // 初始化图表（仅第一次点击时）
                    if (!window.reportsChartInitialized) {
                        initReportsCharts();
                        window.reportsChartInitialized = true;
                    }
                });

                function initReportsCharts() {
                    // 准备数据
                    var dates = [];
                    var impressions = [];
                    var clicks = [];
                    var ctr = [];

                    <c:forEach items="${report.stats}" var="data">
                    dates.push('${data.date}');
                    impressions.push(${data.impressions});
                    clicks.push(${data.clicks});
                    ctr.push(${data.ctr});
                    </c:forEach>
                    dates.reverse();
                    impressions.reverse();
                    clicks.reverse();
                    // 初始化数量图表
                    var chartQty = echarts.init(document.getElementById('reportsChartQuantity'));
                    var optionQty = {
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: { type: 'cross' }
                        },
                        legend: {
                            data: ['展示量', '点击量'],
                            bottom: 0
                        },
                        xAxis: {
                            type: 'category',
                            data: dates,
                            axisLabel: { rotate: 45 }
                        },
                        yAxis: {
                            type: 'value',
                            name: '次数',
                            axisLabel: { formatter: '{value}' }
                        },
                        series: [
                            {
                                name: '展示量',
                                type: 'line',
                                data: impressions,
                                smooth: true,
                                symbol: 'circle',
                                itemStyle: { color: '#667eea' },
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
                            },
                            {
                                name: '点击量',
                                type: 'line',
                                data: clicks,
                                smooth: true,
                                symbol: 'circle',
                                itemStyle: { color: '#764ba2' },
                                areaStyle: {
                                    color: {
                                        type: 'linear',
                                        x: 0, y: 0, x2: 0, y2: 1,
                                        colorStops: [
                                            { offset: 0, color: 'rgba(118, 75, 162, 0.3)' },
                                            { offset: 1, color: 'rgba(118, 75, 162, 0.05)' }
                                        ]
                                    }
                                }
                            }
                        ],
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '10%',
                            containLabel: true
                        }
                    };
                    chartQty.setOption(optionQty);
                    dates.reverse();
                    ctr.reverse();
                    // 初始化CTR图表
                    var chartCTR = echarts.init(document.getElementById('reportsChartCTR'));
                    var optionCTR = {
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
                            name: 'CTR(%)',
                            axisLabel: { formatter: '{value}%' }
                        },
                        series: [{
                            name: 'CTR',
                            type: 'line',
                            data: ctr,
                            smooth: true,
                            symbol: 'circle',
                            symbolSize: 8,
                            lineStyle: {
                                color: '#f5576c',
                                width: 3
                            },
                            itemStyle: { color: '#f5576c' }
                        }],
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '10%',
                            containLabel: true
                        }
                    };
                    chartCTR.setOption(optionCTR);

                    // 响应式
                    window.addEventListener('resize', function() {
                        chartQty.resize();
                        chartCTR.resize();
                    });
                }
            </script>
        </main>
    </div>
</div>

<script src="${pageContext.request.contextPath}/static/js/bootstrap.bundle.min.js"></script>
</body>
</html>