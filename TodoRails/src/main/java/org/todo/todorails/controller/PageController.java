package org.todo.todorails.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {
    @GetMapping("/")
    public String showIndexPage(){
        return "index";
    }
    @GetMapping("/login")
    public String showLoginPage(RedirectAttributes redirectAttributes){
        return "login";
    }
    @GetMapping("/terms")
    public String showTermsPage(){
        return "terms";
    }
}
