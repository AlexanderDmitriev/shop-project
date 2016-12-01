package uits.jv.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    @RequestMapping(value = {"/login"})
    public String login(){
        return "login";
    }
    @RequestMapping(value = {"/","/home"})
    public String home(){
        return "home";
    }
    @RequestMapping(value = {"/me"})
    public String profile(){
        return "me";
    }
    
}
