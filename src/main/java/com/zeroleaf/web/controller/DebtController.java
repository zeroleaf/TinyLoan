package com.zeroleaf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zeroleaf on 2015/4/29.
 */
@Controller
@RequestMapping("/debt")
public class DebtController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "debt/index";
    }

    @RequestMapping(value = "loan", method = RequestMethod.GET)
    public String loan() {
        return "debt/loan";
    }

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info() {
        return "debt/info";
    }

    @RequestMapping(value = "record", method = RequestMethod.GET)
    public String record() {
        return "debt/record";
    }
}
