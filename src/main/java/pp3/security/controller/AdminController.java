package pp3.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pp3.security.module.User;
import pp3.security.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private final BCryptPasswordEncoder encoder;

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users";
    }

    @RequestMapping("/addNewUser")
    public String addNewUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user-info";
    }

    @RequestMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/updateInfo/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getByIdThrowException(id);
        model.addAttribute("user", user);
        return "user-info";
    }


    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }


}
