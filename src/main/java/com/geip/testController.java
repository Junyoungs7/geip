package com.geip;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

    @GetMapping("/index")
    public String testIndex(){
        return "index";
    }
    @GetMapping("/")
    public String testmain(){
        return "test";
    }
}
