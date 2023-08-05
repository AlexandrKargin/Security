package pp3.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pp3.security.module.User;
import pp3.security.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public String welcomeUser(Model model, Principal principal) {
        User user = userService.getByUsernameThrowException(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
