package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.pojo.SeckillOrder;
import cn.edu.xmu.seckill.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * 获取秒杀结果
     * @param user
     * @param goodsId
     * @return
     */
    Long getResult(User user, Long goodsId);
}
