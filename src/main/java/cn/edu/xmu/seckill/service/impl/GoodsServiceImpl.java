package cn.edu.xmu.seckill.service.impl;

import cn.edu.xmu.seckill.mapper.GoodsMapper;
import cn.edu.xmu.seckill.pojo.Goods;
import cn.edu.xmu.seckill.service.IGoodsService;
import cn.edu.xmu.seckill.vo.GoodsVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    /**
     * 查找商品列表
     * @return
     */
    @Override
    public List<GoodsVo> findGoodsVo() {
        return goodsMapper.getGoodsVo();
    }

    /**
     * 根据ID查询goods
     * @param id
     * @return
     */
    @Override
    public GoodsVo findGoodsById(Long id) {
        return goodsMapper.getGoodsVoById(id);
    }
}
