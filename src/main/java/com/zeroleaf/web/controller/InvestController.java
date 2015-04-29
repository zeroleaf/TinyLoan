package com.zeroleaf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zeroleaf on 2015/4/29.
 */
@Controller
@RequestMapping("invest")
public class InvestController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
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
}
