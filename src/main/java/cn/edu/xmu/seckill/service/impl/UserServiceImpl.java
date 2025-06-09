package cn.edu.xmu.seckill.service.impl;

import cn.edu.xmu.seckill.exception.GlobalException;
import cn.edu.xmu.seckill.mapper.UserMapper;
import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.utils.CookieUtil;
import cn.edu.xmu.seckill.utils.MD5Util;
import cn.edu.xmu.seckill.utils.UUIDUtil;
import cn.edu.xmu.seckill.vo.LoginVo;
import cn.edu.xmu.seckill.vo.RespBean;
import cn.edu.xmu.seckill.vo.RespBeanEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
    @Autowired
    private RedisTemplate redisTemplate;

    /*登录
     * */
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
////参数校验
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
//     根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
//判断密码是否正确
//        System.out.println(MD5Util.inputPassToDBPass(password, user.getSalt()));
//        System.out.println(MD5Util.formPassToDBPass(password, user.getSalt()));
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        String UserTicket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("User:"+UserTicket,user);
//        request.getSession().setAttribute (UserTicket,user);
        CookieUtil.setCookie(request, response, "UserTicket", UserTicket);

        return RespBean.success(UserTicket);
    }

    /**
     * 利用cookie从Redis查询User
     * @param userTicket
     * @param request
     * @param response
     * @return
     */
    public User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response) {
        if(userTicket == null){
            return null;
        }
        User user = (User)redisTemplate.opsForValue().get("User:"+userTicket);
        if(user != null){
            CookieUtil.setCookie(request, response, "UserTicket", userTicket);
        }
        return user;
    }

    /*更新密码
     * */
    @Override
    public RespBean updatePassword(String userTicket, String Password, HttpServletRequest request, HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIT);
        }
        user.setPassword(MD5Util.inputPassToDBPass(Password, user.getSalt()));
        int result = userMapper.updateById(user);
        if (result == 1) {
            redisTemplate.delete("user:" + userTicket);
            return RespBean.success();
        }
        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}
