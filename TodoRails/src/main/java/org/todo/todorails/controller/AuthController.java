package org.todo.todorails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.todo.todorails.model.User;
import org.todo.todorails.service.UserService;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegistrationPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user")User user, Model model, RedirectAttributes redirectAttributes){
        try{
            User userReturned = userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage","Registration Successful.Please Login!");
            return "redirect:/login";
        }
        catch(Exception e){
            if(e.getMessage().equalsIgnoreCase("Username already exists")){
                redirectAttributes.addFlashAttribute("errorMessage","Registration unsuccessful due to existing username.Please try again");
            }
            else{
                redirectAttributes.addFlashAttribute("errorMessage","Registration Failed.Please try again");
            }
            model.addAttribute("user",user);
            return "redirect:/register";
        }
    }
}
