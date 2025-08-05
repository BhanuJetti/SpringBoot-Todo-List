package org.todo.todorails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.todo.todorails.model.User;
import org.todo.todorails.service.UserService;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/changepasswd")
    public String getChangePassword(){
        return "/changepasswd";
    }

    @PostMapping("/changepasswd")
    public String changePassword(String currentPassword, String newPassword, RedirectAttributes redirectAttributes){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if(user == null || !passwordEncoder.matches(currentPassword,user.getPassword())){
            redirectAttributes.addFlashAttribute("errorMessage","Password not updated");
            return "redirect:/dashboard";
        }
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userService.save(user);
        redirectAttributes.addFlashAttribute("successMessage","Password Updated...!");
        return "redirect:/dashboard";
    }
}
