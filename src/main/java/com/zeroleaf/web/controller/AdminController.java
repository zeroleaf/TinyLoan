package com.zeroleaf.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zeroleaf on 2015/4/29.
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "admin/index";
    }

    @RequestMapping(value = "ra_record", method = RequestMethod.GET)
    public String raRecord() {
        return "admin/ra_record";
    }

    @RequestMapping(value = "app_record", method = RequestMethod.GET)
    public String appRecord() {
        return "admin/app_record";
    }
}
