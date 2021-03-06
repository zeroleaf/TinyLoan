package com.zeroleaf.web.controller;

import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.business.service.LoanTradeService;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.business.service.dto.InvestAnalysis;
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

    @Resource
    private LoanTradeService loanTradeService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@RequestParam(defaultValue = "1") int page,
                        @RequestParam(required = false) String refresh,
                        HttpSession session, ModelMap map) {

        User user = ((User) session.getAttribute("user"));
        if (refresh != null) {
            user = userService.findByNick(user.getNick());
        }
        List<LoanApplicationForm> lafs = loanApplicationFormService.getInvestable(page);

        map.addAttribute("user", user);
        map.addAttribute("lafs", lafs);

        return "invest/index";
    }

    @RequestMapping(value = "amount", method = RequestMethod.GET)
    public String amount(@RequestParam(required = false) String refresh,
                         HttpSession session,
                         ModelMap map) {

        User user = (User) session.getAttribute("user");
        if (refresh != null) {
            user = userService.findByNick(user.getNick());
            session.setAttribute("user", user);
        }

        InvestAnalysis analysis = loanTradeService.getInvestAnalysis(user);
        double totalProfit = loanTradeService.getTotalInvestProfit(user);
        double totalAmount = loanTradeService.getTotalInvestAmount(user);


        map.addAttribute("user",     user);
        map.addAttribute("analysis", analysis);
        map.addAttribute("totalProfit", totalProfit);
        map.addAttribute("totalAmount", totalAmount);

        return "invest/amount";
    }

    @RequestMapping(value = "refund", method = RequestMethod.GET)
    public String refund() {
        return "invest/refund";
    }

    @RequestMapping(value = "/action", method = RequestMethod.POST)
    @ResponseBody
    public String action(@RequestParam Long id,
                          @RequestParam Integer quantity,
                          HttpSession session) {

        User user = (User) session.getAttribute("user");
        loanApplicationFormService.newInvest(user, id, quantity);
        user = userService.findByNick(user.getNick());
        session.setAttribute("user", user);
        return "";
    }

    @RequestMapping(value = "record", method = RequestMethod.GET)
    public String record() {
        return "app/record";
    }

    @RequestMapping(value = "/invest", method = RequestMethod.GET)
    public String invest() {
        return "invest/invest";
    }
}
