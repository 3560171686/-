package cn.edu.xmu.seckill.controller;


import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.vo.RespBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liyejia
 * @since 2025-05-10
 */
@Controller
@RequestMapping("/user")
public class UserController {
    /*
     * 用户信息（测试）
     * */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }
}
