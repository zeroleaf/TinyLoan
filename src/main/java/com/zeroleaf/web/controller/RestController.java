package com.zeroleaf.web.controller;

import com.zeroleaf.web.business.service.*;
import com.zeroleaf.web.business.service.dto.InvestRecord;
import com.zeroleaf.web.business.service.dto.MyDebt;
import com.zeroleaf.web.business.service.dto.RaRecord;
import com.zeroleaf.web.model.AmountFlow;
import com.zeroleaf.web.model.LoanApplicationForm;
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

    @Resource
    private LoanTradeService loanTradeService;

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
    @ResponseBody
    public String getUserAmountFlows(@RequestParam Integer page,
                                     HttpSession session) {

        User user = (User) session.getAttribute("user");
        Page<AmountFlow> afs = amountFlowService.getPage(user, page);

        return JSONUtils.toJson(afs);
    }

    @RequestMapping(value = "/investor/profit", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getInvestorProfit(@RequestParam Integer page,
                                    HttpSession session) {

        User user = (User) session.getAttribute("user");
        Page<AmountFlow> profits = amountFlowService.getProfit(user, page);

        return JSONUtils.toJson(profits);
    }

    @RequestMapping(value = "/investor/my_investment",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getInvestorRecord(@RequestParam Integer page,
                                    HttpSession session) {

        User user = (User) session.getAttribute("user");
        Page<InvestRecord> iRecord = loanTradeService.getInvestRecord(user, page);

        return JSONUtils.toJson(iRecord);
    }

    @RequestMapping(value = "/debtor/my_debt",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getMyDebt(@RequestParam Integer page,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        Page<MyDebt> myDebt = loanTradeService.getMyDebt(user, page);

        return JSONUtils.toJson(myDebt);
    }

    //----------------------------------------------------------------------
    // Admin
    //----------------------------------------------------------------------

    @RequestMapping(value = "/platform/debt_record",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getDebtRecord(@RequestParam Integer page) {

        Page<MyDebt> myDebt = loanTradeService.getAllMyDebt(page);

        return JSONUtils.toJson(myDebt);
    }

    @RequestMapping(value = "/platform/ra_record",
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getRaRecord(@RequestParam Integer page) {

        Page<RaRecord> record = amountFlowService.getRaRecord(page);

        return JSONUtils.toJson(record);
    }
}
