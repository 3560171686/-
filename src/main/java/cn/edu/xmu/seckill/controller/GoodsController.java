package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.vo.DetailVo;
import cn.edu.xmu.seckill.vo.GoodsVo;
import cn.edu.xmu.seckill.vo.RespBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.resource.HttpResource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private TemplateEngine templateEngine; // 添加此注入
    @Autowired
    private ThymeleafViewResolver viewResolver;

    /**
     * 跳转首页
     *
     * @param model
     * @param user
     * @return
     */
    @GetMapping(value = "/toList", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String tolist(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
//        if(ticket == null){
//            return "login";
//        }
//        这段逻辑放入WebConfig中去了
//        User user = userService.getUserByCookie(ticket, request, response);
//        if(user == null){
//            return "login";
//        }
//        redis中获取页面，如果不为空则返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
//        return "goodsList";
//        如果为空，手动渲染，存入redis并且返回
        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(
                request.getServletContext()
        );
        IWebExchange webExchange = application.buildExchange(request, response);
        IWebContext context = new WebContext(webExchange, request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
    }

    /**
     * 跳转详情页
     *
     * @param goodsId
     * @param model
     * @param user
     * @return
     */
    @GetMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8")
    @ResponseBody
    public String goodsDetail(@PathVariable Long goodsId, Model model, User user, HttpServletRequest request, HttpServletResponse response) {
        if(user == null) {
            return "login";
        }
        ValueOperations valueOperations = redisTemplate.opsForValue();
//        redis中获取页面，如果不为空则返回页面
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        GoodsVo goods = goodsService.findGoodsById(goodsId);
        Date start = goods.getStartDate();
        Date end = goods.getEndDate();
        Date now = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if(now.before(start)) {
            secKillStatus = 0;
            remainSeconds = (int)((start.getTime() - now.getTime()) / 1000);
        } else if(now.after(end)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("user", user);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goods);
        //如果为空，手动渲染页面
//        ServletWebRequest servletWebRequest = new ServletWebRequest(request, response);
//        JakartaServletWebApplication application = JakartaServletWebApplication.buildApplication(
//                request.getServletContext()
//        );
//        IWebExchange webExchange = application.buildExchange(request, response);
//        IWebContext context = new WebContext(webExchange, request.getLocale(), model.asMap());
//        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        JakartaServletWebApplication app = JakartaServletWebApplication.buildApplication(request.getServletContext());
        IServletWebExchange exchange = app.buildExchange(request, response);
        WebContext webContext = new WebContext(exchange, request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", webContext);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail", html, 60, TimeUnit.SECONDS);
        }
        return html;

//        return "goodsDetail";
    }

    @RequestMapping("/toDetail/{goodsId}")
    @ResponseBody
    public RespBean toDetail(Model model, User user, @PathVariable Long goodsId) {
        GoodsVo goodsVo = goodsService.findGoodsById(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if(nowDate.before(startDate)) {
            secKillStatus = 0;
            remainSeconds = (int)((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if(nowDate.after(endDate)) {
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            secKillStatus = 1;
            remainSeconds = 0;
        }
        DetailVo detail = new DetailVo();
        detail.setUser(user);
        detail.setGoodsVo(goodsVo);
        detail.setRemainSeconds(remainSeconds);
        detail.setSecKillStatus(secKillStatus);
        // 打印对象内容（用于调试）
        log.info("返回的Detail对象: {}", detail);

        return RespBean.success(detail);
    }
}
