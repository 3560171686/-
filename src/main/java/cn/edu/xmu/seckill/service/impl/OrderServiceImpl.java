package cn.edu.xmu.seckill.service.impl;

import cn.edu.xmu.seckill.mapper.OrderMapper;
import cn.edu.xmu.seckill.pojo.Order;
import cn.edu.xmu.seckill.pojo.SeckillGoods;
import cn.edu.xmu.seckill.pojo.SeckillOrder;
import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.service.IOrderService;
import cn.edu.xmu.seckill.service.ISeckillGoodsService;
import cn.edu.xmu.seckill.service.ISeckillOrderService;
import cn.edu.xmu.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private ISeckillGoodsService  seckillGoodsService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IGoodsService goodsService;

    /**
     * 秒杀
     * @param user
     * @param goods
     * @return
     */
    @Override
    public Order seckill(User user, GoodsVo goods) {
        //查询秒杀商品
        QueryWrapper<SeckillGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id",goods.getId());
        SeckillGoods seckillGoods = seckillGoodsService.getOne(queryWrapper);

        //库存减一
        UpdateWrapper<SeckillGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("stock_count ="+"stock_count - 1");
        updateWrapper.eq("id",seckillGoods.getId());
        updateWrapper.gt("stock_count",0);
        boolean seckilled = seckillGoodsService.update(updateWrapper);
        if(!seckilled){
            return null;
        }
        //生成订单
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setGoodsCount(1);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsId(goods.getId());
        order.setGoodsPrice(goods.getSeckillPrice());
        order.setUserId(user.getId());
        order.setDeliveryAddrId(0l);
        order.setOrderChannel(1);
        order.setStatus(0);
        orderMapper.insert(order);

        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setUserId(user.getId());
        seckillOrderService.save(seckillOrder);
        return order;

    }
}
