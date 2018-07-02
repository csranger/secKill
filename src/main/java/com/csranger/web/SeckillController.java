package com.csranger.web;

import com.csranger.dto.Exposer;
import com.csranger.dto.SeckillExecution;
import com.csranger.dto.SeckillResult;
import com.csranger.entity.Seckill;
import com.csranger.enums.SeckillStatEnum;
import com.csranger.exception.RepeatKillException;
import com.csranger.exception.SeckillCloseException;
import com.csranger.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller      // 类似于 @Service 将其放入 Spring 容器当中
@RequestMapping("/seckill")      // url: /模块/资源/{id}/细分
public class SeckillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;


    // 获取秒杀的列表页
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        // 获取列表页
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute( "list", list);
        // list.jsp + model = ModelAndView
        return "list";        // 视图解析器解析，指的就是 WEB-INF/jsp/list.jsp
    }


    // 详情页
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {

        if (seckillId == null) {                     // 请求信息里没有秒杀商品的id
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {                       // 随便写的id，找不到秒杀商品
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        return "detail";                             // 视图解析器解析，指的就是 WEB-INF/jsp/detail.jsp
    }


    // ajax接口 返回类型是json，不是视图，所以不需要Model
    // 所有ajax请求返回类型均是 SeckillResult<T>    T 是数据类型
    @RequestMapping(value = "/{seckillId}/exposer",
            method = RequestMethod.POST,                           // 请求方法是POST，所以在浏览器中直接输入地址访问是无效的
            produces = {"application/json;charset=UTF-8"})         // 客户端发的http请求Accept：application/json;charset=UTF-8 即要求返回的响应只能是json格式
    @ResponseBody   // 返回是json类型，Spring MVC看到 @ResponseBody 会返回类型封装成json
    public SeckillResult<Exposer> exposer(Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillID}/{md5}/execution",
            method = RequestMethod.POST,
            produces = {"application/json;character=UTF-8"})   // 客户端发的http请求Accept：application/json;charset=UTF-8 即要求返回的响应只能是json格式
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {      //required = false说明它不是必须的，验证逻辑放到程序里处理，而不是直接返回错误
        if (phone == null) return new SeckillResult(false, "未注册");    // 验证逻辑
        SeckillResult<SeckillExecution> result;
        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (SeckillCloseException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.END);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (RepeatKillException e) {
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false, execution);
        }
    }


    // 获取系统时间
    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();     //new 时间对象基本上不会报错，所以这里没有食欲try catch 语句
        return new SeckillResult<Long>(true, now.getTime());
    }

}
