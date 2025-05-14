package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.vo.LoginVo;
import cn.edu.xmu.seckill.vo.RespBean;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author liyejia
 * @since 2025-05-10
 */
public interface IUserService extends IService<User> {
    /*登录
     * */
    RespBean doLogin(LoginVo loginVo);
}
