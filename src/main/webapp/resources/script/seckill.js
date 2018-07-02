// 存放主要交互逻辑js代码
// 模块化
var seckill = {           // json对象
    // 第一个属性：封装秒杀相关ajax的地址的url
    URL: {

    },
    // 验证手机号
    validatePhone: function(phone) {
        if (phone && phone.length() == 11 && !isNaN(phone)) {   // phone 存在，长度11，且是数字则 true ；isNaN是否是非数字
            return true;
        } else {
            return false;
        }
    },
    // 详情页秒杀逻辑
    detail: {
        // 详情页初始化
        init: function (params) {
            // 手机验证和登陆，计时交互
            // 规划交互流程
            // 在cookie中查找手机号
            var killPhone = $.cookie('killPhone');
            var startTime = params("startTime")
            var startTime = params("endTime");
            var seckillId = params("seckillId");
            // 验证手机号
            if (!seckill.validatePhone(killPhone)) {
                // 绑定 phone
                // 控制输出
                var killPhoneModal = $('killPhoneModal');
                killPhone.modal({
                    // 显示弹出层
                    show:true,
                    backdrop:'static',   //禁止位置关闭
                    keyboard:false      // 关闭键盘事件
                });
                $('#killPhoneBtn').click(fuction(){
                    var =
                });

            }
        }
    }
}