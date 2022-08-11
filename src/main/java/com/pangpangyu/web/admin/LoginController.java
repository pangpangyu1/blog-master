package com.pangpangyu.web.admin;

import com.pangpangyu.po.User;
import com.pangpangyu.service.UserService;
import com.pangpangyu.util.CookieUtils;
import com.pangpangyu.util.JWTUtils;
import com.pangpangyu.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequestMapping("/admin")
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String login(HttpServletRequest request, HttpServletResponse response){
        Cookie token = CookieUtils.get(request, "token");
        if (token != null) {
            //验证token
            try {
                JWTUtils.verifyToken(token.getValue());
                CookieUtils.delete(request, response, "tokenInvalid");
                return "redirect:admin/index";
            } catch (Exception e) { //发生异常说明token失效
                e.printStackTrace();
            }
            if (CookieUtils.get(request, "tokenInvalid") == null) {
                CookieUtils.set(response, "tokenInvalid", "请先登录", -1);
            }
            CookieUtils.delete(request, response, "token");
            HttpSession session = request.getSession();
            session.removeAttribute("user");
        }
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes){//为什么不用Model？它适用的域只是转发域，重定向会刷掉。

            User user = userService.findByUsername(username);
            CookieUtils.delete(request, response, "tokenInvalid");
            if (user == null) {
                attributes.addFlashAttribute("message", "用户不存在！");
                return "redirect:/admin";
            }

            if (!user.getPassword().equals(MD5Utils.code(password))) { //密码正确
                attributes.addFlashAttribute("message", "密码错误！");
                return "redirect:/admin";
            }
            //验证通过
            //生成token
        System.out.println(user);
            String token = JWTUtils.createToken(user);
            //将token存储在cookie中
            CookieUtils.set(response, "token", token, -1);
            user.setPassword(null);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            return "redirect:/admin/index";
    }
    @GetMapping("/index")
    public String mainPage() {
        return "admin/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtils.delete(request, response, "token");
        CookieUtils.delete(request, response, "tokenInvalid");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/admin";

    }
}
