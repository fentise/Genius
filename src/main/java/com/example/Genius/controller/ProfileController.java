package com.example.Genius.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    //TODO:个人主页
    @RequestMapping(path={"/profile"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String profile(){
        logger.info("visiting profile");
        return "profile";
    }
}
