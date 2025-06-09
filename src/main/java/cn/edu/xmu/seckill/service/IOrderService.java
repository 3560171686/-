package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.pojo.Order;
import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.vo.GoodsVo;
import cn.edu.xmu.seckill.vo.OrderDetailVO;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IOrderService extends IService<Order> {

    Order seckill(User user, GoodsVo goods);

    OrderDetailVO detail(Long orderId);

    /***
     * 创建秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    String createPath(User user, Long goodsId);

    /***
     * 秒杀路径校验
     * @param user
     * @param goodsId
     * @return
     */
    boolean checkPath(User user, Long goodsId, String path);

    /***
     * 验证码校验
     * @param user
     * @param goodsId
     * @param captcha
     * @return
     */
    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
