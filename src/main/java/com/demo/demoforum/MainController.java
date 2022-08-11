package com.demo.demoforum;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @RequestMapping("/forum")
    @ResponseBody
    public String index() {
        return "aaa";
    }
}
