package com.zeroleaf.web.controller;

import com.zeroleaf.web.business.service.LoginResult;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * Created by zeroleaf on 2015/5/1.
 */
@Controller
public class ApplicationController {

    @Resource
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register() {
        return "app/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request) {

        String nick     = request.getParameter("nick");
        String password = request.getParameter("password");
        Integer type    = Integer.valueOf(request.getParameter("type"));

        User user = new User(nick, password, type);
        userService.save(user);

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "app/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String nick,
                        @RequestParam String password,
                        HttpSession session,
                        ModelMap map) {

        LoginResult result = userService.login(nick, password);
        if (result.isValid()) {
            session.setAttribute("user", result.getUser());
            switch (result.getType()) {
                case User.DEBTOR:
                    return "redirect:/debt/index";
                case User.INVESTOR:
                    return "redirect:/invest/index";
            }
        }

        map.addAttribute("error", result.getMsg());
        return "app/login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public String changePassword() {
        return "app/password";
    }

    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public String changePassword(HttpSession session, ModelMap map,
                                 @RequestParam(value = "old_password") String old,
                                 @RequestParam String password) {

        String nick = ((User) session.getAttribute("user")).getNick();
        boolean success = userService.changePassword(nick, old, password);

        if (!success) {
            map.addAttribute("error", "旧密码错误, 请重新输入");
        } else {
            map.addAttribute("success", true);
        }

        return changePassword();
    }

    @RequestMapping(value = "/info/change", method = RequestMethod.GET)
    public String changeInfo(ModelMap map, HttpSession session) {
        map.addAttribute("user", session.getAttribute("user"));
        return "app/info";
    }

    @RequestMapping(value = "/info/change", method = RequestMethod.POST)
    public String changeInfo(HttpServletRequest request) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String idNumber     = request.getParameter("idNumber");
        String name         = request.getParameter("name");
        String phoneNumber  = request.getParameter("phoneNumber");
        String cardNumber   = request.getParameter("cardNumber");
        String email        = request.getParameter("email");

        User user = (User) request.getSession().getAttribute("user");
        if (user.getIdNumber() == null) {
            user.setIdNumber(idNumber);
        }
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setCardNumber(Long.valueOf(cardNumber));
        user.setEmail(email);

        userService.updateInfo(user);
        request.getSession().setAttribute("user", user);

        System.out.println(user);

        return "redirect:/info/change";
    }
}
