<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 静态引用，引用jstl -->
<%@include file="common/tag.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <title>秒杀列表页</title>
    <%@include file="common/head.jsp" %>
</head>

<body>

<!-- 页面显示部分 -->
<!-- 使用Bootstrap现成的组件 -->
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="sk" items="${list}">
                    <tr>
                        <td>${sk.name}</td>
                        <td>${sk.number}</td>
                        <td>
                            <fmt:formatDate value="${sk.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${sk.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                        <td>
                            <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank">link</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>


<%--<!--jquery文件：需要在Bootstrap 的 JavaScript 插件之前引用-->--%>
<%--<script src="js/jquery-3.3.1.min.js"></script>--%>
<%--<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->--%>
<%--<script src="js/bootstrap.min.js"></script>--%>


<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<!--可能出错的位置-->
<%--0.打不开list页面，tag 里 是 /jsp/jstl/core 不是/jstl/core--%>
<%--1.bootstrap文件使用本地的css，js等无法显示bootstrap样式，使用cdn在线的--%>
<%--2.打不开detail页面，href="/seckill/${sk.seckillId}/detail 可能写成了 href="/seckill/{sk.seckillId}/detail--%>
</body>

</html>
