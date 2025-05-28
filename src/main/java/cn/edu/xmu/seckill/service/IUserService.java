package cn.edu.xmu.seckill.service;

import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.vo.LoginVo;
import cn.edu.xmu.seckill.vo.RespBean;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


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
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /*根据cookie获得用户
     * */
    User getUserByCookie(String userTicket, HttpServletRequest request, HttpServletResponse response);

    /*更新密码
     * */
    RespBean updatePassword(String userTicket, String Password, HttpServletRequest request, HttpServletResponse response);
}

