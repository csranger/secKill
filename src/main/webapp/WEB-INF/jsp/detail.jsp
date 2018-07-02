<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>秒杀详情页</title>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckill.name}</h1>
        </div>

        <div class="panel-body">
            <!-- 些按钮显示 -->
            <h2 class="text-danger">
                <!--显示time图标-->
                <span class="glyphicon glyphicon-time"></span>
                <%--展示计时面板--%>
                <span class="glyphicon" id="seckill-box"></span>
            </h2>
        </div>
    </div>
</div>

<!-- 使用bootstrap modal 插件：登陆弹出层，输入手机号码，点击提交就完成登陆操作 -->
<!-- 仅使用前端将登陆模块完成，主要关注秒杀相关的逻辑 -->
<div id="killPhoneModal" class="modal fade">         <!-- bootstrap 的一个组件modal，默认隐藏 -->
    <div class="modal-dialog">                                 <!-- bootstrap modal 对话框 -->
        <div class="modal-content">                            <!-- bootstrap modal 内容 -->
            <div class="modal-header">          <!--1. bootstrap modal header 填写秒杀电话 -->
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话:
                </h3>
            </div>

            <div class="modal-body">            <!--2. bootstrap modal body 主干内容：填手机号 -->
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killPhone" id="killPhoneKey" placeholder="填手机号" class="form-control">
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <!-- 当用户填写出错时将验证信息放在span里-->
                <span id="killPhoneMassage" class="glyphicon"></span>
                <!-- 提交按钮 -->
                <button type="button" id="killPhoneButton" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    submit
                </button>
            </div>
        </div>
    </div>
</div>


<%--<!--jquery文件：需要在Bootstrap 的 JavaScript 插件之前引用-->--%>
<%--<script src="js/jquery-3.3.1.min.js"></script>--%>
<%--<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->--%>
<%--<script src="js/bootstrap.min.js"></script>--%>


<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!--jQuery 的 cookie 插件-->
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQuery 的 count down 倒计时插件-->
<script src="https://cdn.bootcss.com/jquery-countdown/2.0.2/jquery.countdown.min.js"></script>

<%--开始编写交互逻辑--%>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<%--开始编写交互逻辑--%>
<script type="text/javascript">
    $(function () {

        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId : ${seckill.id},
            startTime: ${seckill.startTime.time},   // 毫秒
            endTime: ${seckill.endTime.time}

        });
    });
</script>


</body>
</html>
