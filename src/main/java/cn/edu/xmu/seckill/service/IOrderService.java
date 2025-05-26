package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.pojo.Order;
import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);

}
