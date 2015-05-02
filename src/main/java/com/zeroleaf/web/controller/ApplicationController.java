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
                        ModelMap map) {

        LoginResult result = userService.login(nick, password);
        if (result.isValid()) {
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
}
