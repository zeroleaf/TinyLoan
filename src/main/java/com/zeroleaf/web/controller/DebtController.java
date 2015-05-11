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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by zeroleaf on 2015/4/29.
 */
@Controller
@RequestMapping("/debt")
public class DebtController {

    @Resource
    private UserService userService;

    @Resource
    private LoanApplicationFormService loanApplicationFormService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@RequestParam(required = false) String refresh,
                        HttpSession session, ModelMap map) {

        User user = (User) session.getAttribute("user");
        if (refresh != null) {
            user = userService.findByNick(user.getNick());
            session.setAttribute("user", user);
        }

        List<LoanApplicationForm> refs = userService.getRefundForms(user, 5);
        List<LoanApplicationForm> lafs = userService.getLoanApplicationForms(user.getNick(), 5);
        double toRefundBalance = loanApplicationFormService.getToRefundBalance(user);
        double totalDebtAmount = loanApplicationFormService.getTotalDebtAmount(user);

        map.addAttribute("user", user);
        map.addAttribute("refs", refs);
        map.addAttribute("lafs", lafs);
        map.addAttribute("toRefundBalance", toRefundBalance);
        map.addAttribute("totalDebtAmount", totalDebtAmount);

        return "debt/index";
    }

    @RequestMapping(value = "/loan", method = RequestMethod.GET)
    public String loan() {
        return "debt/loan";
    }

    @RequestMapping(value = "/loan", method = RequestMethod.POST)
    public String loan(HttpServletRequest request) {

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String nick      = ((User) request.getSession().getAttribute("user")).getNick();
        String title     = request.getParameter("title");
        String pledge    = request.getParameter("pledge");
        Integer quantity = Integer.valueOf(request.getParameter("quantity"));
        Integer deadline = Integer.valueOf(request.getParameter("deadline"));
        LoanApplicationForm laf =
                LoanApplicationForm.newDefaultInstance(title, quantity, pledge, deadline);
        userService.addLoanApplicationForm(nick, laf);
        return "redirect:/debt/index";
    }


    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info() {
        return "debt/info";
    }

    @RequestMapping(value = "record", method = RequestMethod.GET)
    public String record() {
        return "app/record";
    }

    @RequestMapping(value = "/refund", method = RequestMethod.POST)
    @ResponseBody
    public void refund(@RequestParam Long refId,
                       HttpSession session) {
        User user = (User) session.getAttribute("user");
        loanApplicationFormService.newRefund(user, refId);
    }

    @RequestMapping(value = "/debt", method = RequestMethod.GET)
    public String debt() {
        return "debt/debt";
    }
}
