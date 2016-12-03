package uits.jv.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uits.jv.shop.config.domain.User;

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
    public ModelAndView profile(){
        ModelAndView mv = new ModelAndView("me");
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mv.addObject("email", user.getEmail());
        return mv;
    }
    @RequestMapping(value = {"/admin"})
    public ModelAndView admin(){
        ModelAndView mv = new ModelAndView("admin");
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mv.addObject("email", user.getEmail());
        return mv;
    }
    @RequestMapping(value = {"/check"})
    public String check(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getRoleUser().equals("ROLE_USER")){
            return "redirect:/me";
        }
        return "redirect:/admin";
    }
    
}
