package cn.edu.xmu.seckill.controller;


import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IOrderService;
import cn.edu.xmu.seckill.vo.OrderDetailVO;
import cn.edu.xmu.seckill.vo.RespBean;
import cn.edu.xmu.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /***
     * 订单详情
     * @param user
     * @param orderId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId) {
        if(user == null) return RespBean.error(RespBeanEnum.SESSION_ERROR);
        OrderDetailVO orderDetailVO = orderService.detail(orderId);
        return RespBean.success(orderDetailVO);
    }
}
