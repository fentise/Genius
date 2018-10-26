package com.example.Genius.controller;

import com.example.Genius.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class testController {

    @RequestMapping("/test")
    String test(Model model){
        List<HashMap<String,Object>> vos = new ArrayList<>();
        for(int i = 0;i<100;i++){
            HashMap<String,Object> vo = new HashMap<>();
            User user = new User();
            user.setUserNickname("user"+String.valueOf(i));
            vo.put("user",user);
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "Sample";
    }
}
