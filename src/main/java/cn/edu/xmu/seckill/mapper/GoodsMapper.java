package cn.edu.xmu.seckill.mapper;

import cn.edu.xmu.seckill.pojo.Goods;
import cn.edu.xmu.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface GoodsMapper extends BaseMapper<Goods> {

    List<GoodsVo> getGoodsVo();

    GoodsVo getGoodsVoById(Long id);
}
