package com.zeroleaf.web.controller;

import com.zeroleaf.web.business.service.AmountFlowService;
import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.business.service.Page;
import com.zeroleaf.web.business.service.UserService;
import com.zeroleaf.web.model.AmountFlow;
import com.zeroleaf.web.model.User;
import com.zeroleaf.web.util.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by zeroleaf on 2015/5/3.
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController {

    @Resource
    private LoanApplicationFormService loanApplicationFormService;

    @Resource
    private UserService userService;

    @Resource
    private AmountFlowService amountFlowService;

//    TODO Point - 如何更好的接收 $http.post() 的参数?
    @RequestMapping(value = "/laf/status", method = RequestMethod.POST)
    @ResponseBody
    public void setLoanApplicationFormStatus(@RequestParam Long id,
                                             @RequestParam Integer status) {

        loanApplicationFormService.setStatus(id, status);
    }

    @RequestMapping(value = "/asset/recharge", method = RequestMethod.POST)
    @ResponseBody
    public void recharge(@RequestParam Double charge,
                         @RequestParam String pay,
                         HttpSession session) {

        System.out.println(pay);

        User user = (User) session.getAttribute("user");
        userService.recharge(user, charge, pay);
    }

    @RequestMapping(value = "/asset/advance", method = RequestMethod.POST)
    @ResponseBody
    public void advance(@RequestParam Double amount,
                        @RequestParam String credit,
                        HttpSession session) {

        User user = (User) session.getAttribute("user");
        userService.advance(user, amount, credit);
    }

    @RequestMapping(value = "/user/afs", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    @ResponseBody()
    public String getUserAmountFlows(@RequestParam Integer page,
                                     HttpSession session) {

        User user = (User) session.getAttribute("user");
        Page<AmountFlow> afs = amountFlowService.getPage(user, page);

        return JSONUtils.toJson(afs);
    }
}
