package cn.edu.xmu.seckill.service.impl;

import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.utils.MD5Util;
import cn.edu.xmu.seckill.utils.ValidatorUtil;
import cn.edu.xmu.seckill.vo.LoginVo;
import cn.edu.xmu.seckill.vo.RespBean;
import cn.edu.xmu.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author liyejia
 * @since 2025-05-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /*登录
     * */
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
//     根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (user == null) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
//判断密码是否正确
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}
