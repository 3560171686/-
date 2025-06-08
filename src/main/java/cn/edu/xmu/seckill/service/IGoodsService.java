package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.pojo.Goods;
import cn.edu.xmu.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IGoodsService extends IService<Goods> {

    List<GoodsVo> findGoodsVo();

    GoodsVo findGoodsById(Long id);

}
