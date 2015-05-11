package com.zeroleaf.web.controller;

import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.model.LoanApplicationForm;
import com.zeroleaf.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zeroleaf on 2015/4/29.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Resource
    private LoanApplicationFormService loanApplicationFormService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "redirect:/login";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) String refresh,
                        HttpSession session, ModelMap map) {

        User admin = (User) session.getAttribute("user");
        if (refresh != null) {
            admin = userService.findByNick(admin.getNick());
            session.setAttribute("user", admin);
        }

        map.addAttribute("user", admin);

        return "admin/index";
    }

    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public String audit(@RequestParam(defaultValue = "1") int page,
                        ModelMap map) {
        List<LoanApplicationForm> lafs = loanApplicationFormService.getUnaudited(page);

        map.addAttribute("lafs", lafs);

        return "admin/audit";
    }

    @RequestMapping(value = "ra_record", method = RequestMethod.GET)
    public String raRecord() {
        return "admin/ra_record";
    }

    @RequestMapping(value = "app_record", method = RequestMethod.GET)
    public String appRecord() {
        return "admin/app_record";
    }

    @RequestMapping(value = "/debt_record", method = RequestMethod.GET)
    public String debtRecord() {
        return "admin/debt_record";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String user(@RequestParam Integer type,
                       ModelMap map) {

        map.addAttribute("type", type);

        return "admin/user";
    }
}
