package com.zeroleaf.web.controller;

import com.zeroleaf.web.domain.CustomerRepository;
import com.zeroleaf.web.model.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;

@Controller
@RequestMapping("/")
public class HelloController {

    @Resource
    private CustomerRepository customerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String hello(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "hello";
    }

    @RequestMapping(value = "customer", method = RequestMethod.GET)
    public @ResponseBody String customer(@RequestParam String firstName,
                                         @RequestParam String lastName) {

        Customer customer = new Customer(firstName, lastName);
        customerRepository.save(customer);

        return "" + customerRepository.count();
    }
}