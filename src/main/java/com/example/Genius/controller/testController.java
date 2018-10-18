package com.example.Genius.controller;

import com.example.Genius.model.User;
import com.example.Genius.model.viewObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class testController {

    @RequestMapping("/test")
    String test(Model model){
        List<User> vos = new ArrayList<>();
        for(int i = 0;i<100;i++){
            //viewObject vo = new viewObject();
            User user = new User();
            user.setUserNickname("user"+String.valueOf(i));
            //vo.set("user",user);
            vos.add(user);
        }
        model.addAttribute("users",vos);
        return "Sample";
    }
}
