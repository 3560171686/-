package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.service.IUserService;
import cn.edu.xmu.seckill.vo.RespBean;
import cn.edu.xmu.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
@Slf4j
public class loginController {
    @Autowired
    private IUserService userService;

    /*跳转登录页面
     * */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /*登录功能
     * */
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(LoginVo loginVo) {
        return userService.doLogin(loginVo);
    }
}
