package cn.edu.xmu.seckill.vo;

import cn.edu.xmu.seckill.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVO {

    private Order order;

    private GoodsVo goodsVO;
}
