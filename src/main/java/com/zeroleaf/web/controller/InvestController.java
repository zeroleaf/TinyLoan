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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zeroleaf on 2015/4/29.
 */
@Controller
@RequestMapping("invest")
public class InvestController {

    @Resource
    private LoanApplicationFormService loanApplicationFormService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(required = false) String refresh,
                        HttpSession session, ModelMap map) {

        User user = ((User) session.getAttribute("user"));
        // TODO refresh 的默认值为?  貌似以下的判断都会执行
        if (refresh != null) {
            user = userService.findByNick(user.getNick());
        }
        List<LoanApplicationForm> lafs = loanApplicationFormService.getInvestable(page);

        map.addAttribute("user", user);
        map.addAttribute("lafs", lafs);

        return "invest/index";
    }

    @RequestMapping(value = "amount", method = RequestMethod.GET)
    public String amount() {
        return "invest/amount";
    }

    @RequestMapping(value = "refund", method = RequestMethod.GET)
    public String refund() {
        return "invest/refund";
    }

    @RequestMapping(value = "/modal_invest", method = RequestMethod.GET)
    public String modalInvest() {
        return "invest/_modal_invest";
    }

    @RequestMapping(value = "/action", method = RequestMethod.POST)
    @ResponseBody
    public String action(@RequestParam Long id,
                          @RequestParam Integer quantity,
                          HttpSession session) {

        User user = (User) session.getAttribute("user");
        loanApplicationFormService.newInvest(user, id, quantity);

        return "";
    }
}
