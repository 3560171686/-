package cn.edu.xmu.seckill.controller;

import cn.edu.xmu.seckill.pojo.User;
import cn.edu.xmu.seckill.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IUserService userService;

    @GetMapping("/toList")
    public String tolist(Model model, User user){
//        if(ticket == null){
//            return "login";
//        }
//        这段逻辑放入WebConfig中去了
//        User user = userService.getUserByCookie(ticket, request, response);
//        if(user == null){
//            return "login";
//        }
        model.addAttribute("user",user);
        return "goodsList";
    }
}
