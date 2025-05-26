package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;



@Controller
public class GoodsController {
    @Autowired
    IUserService userService;
    @Autowired
    IGoodsService goodsService;

    /**
     * 跳转首页
     *
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/goods/toList")
    public String tolist(Model model, User user){
//        if(ticket == null){
//            return "login";
//        }
//        这段逻辑放入WebConfig中去了
//        User user = userService.getUserByCookie(ticket, request, response);
//        if(user == null){
//            return "login";
//        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }

    /**
     * 跳转详情页
     *
     * @param goodsId
     * @param model
     * @param user
     * @return
     */
    @GetMapping("/goodsDetail.html")
    public String goodsDetail(@RequestParam Long goodsId, Model model, User user) {
        GoodsVo goods = goodsService.findGoodsById(goodsId);
        Date start = goods.getStartDate();
        Date end = goods.getEndDate();
        Date now = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (now.before(start)) {
            remainSeconds = (int) ((start.getTime() - now.getTime()) / 1000);
        } else if (now.after(end)) {
            remainSeconds = -1;
            secKillStatus = 2;
        } else {
            secKillStatus = 1;
        }

        model.addAttribute("user", user);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("goods", goods);
        return "goodsDetail";
    }
}
