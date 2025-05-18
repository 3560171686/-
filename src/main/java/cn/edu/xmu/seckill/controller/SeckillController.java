package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.pojo.Order;
import cn.edu.xmu.seckill.pojo.SeckillOrder;
import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.IOrderService;
import cn.edu.xmu.seckill.service.ISeckillOrderService;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.vo.GoodsVo;
import cn.edu.xmu.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Wrapper;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
    @Autowired
    ISeckillOrderService seckillOrderService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IOrderService orderService;

    /**
     * 秒杀功能
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user,Long goodsId){
        if(user == null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.findGoodsById(goodsId);
        model.addAttribute("goods",goods);
        //库存检查
        if(goods.getStockCount() <= 0){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK.getMessage());
            return "secKillFail";
        }
        //重复秒杀检查
        QueryWrapper<SeckillOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId);
        wrapper.eq("user_id",user.getId());
        SeckillOrder seckillOrder = seckillOrderService.getOne(wrapper);
        if(seckillOrder != null){
            model.addAttribute("errmsg",RespBeanEnum.HAS_SECKILL.getMessage());
            return "secKillFail";
        }
        Order order = orderService.seckill(user,goods);
        model.addAttribute("order",order);
        return "orderDetail";


    }
}
