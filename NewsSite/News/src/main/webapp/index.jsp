<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 自动跳转到新闻列表
    response.sendRedirect(request.getContextPath() + "/news/list");
%>
