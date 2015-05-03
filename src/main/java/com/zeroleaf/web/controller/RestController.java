package com.zeroleaf.web.controller;

import com.zeroleaf.web.business.service.LoanApplicationFormService;
import com.zeroleaf.web.model.LoanApplicationForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zeroleaf on 2015/5/3.
 */
@Controller
@RequestMapping(value = "/rest")
public class RestController {

    @Resource
    private LoanApplicationFormService loanApplicationFormService;

//    TODO Point - 如何更好的接收 $http.post() 的参数?
    @RequestMapping(value = "/laf/status", method = RequestMethod.POST)
    @ResponseBody
    public void setLoanApplicationFormStatus(@RequestParam Long id,
                                             @RequestParam Integer status) {

        loanApplicationFormService.setStatus(id, status);
    }
}
