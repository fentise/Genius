package com.example.Genius.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping(value={"/","/index"},method={RequestMethod.POST,RequestMethod.GET})
    String indexPage(Model model){
        model.addAttribute("user",null);
        return "header";
    }
}
