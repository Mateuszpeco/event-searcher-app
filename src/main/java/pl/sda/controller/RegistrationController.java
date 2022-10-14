package pl.sda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.exception.EmptyUsernameException;
import pl.sda.model.User;
import pl.sda.service.AutologinService;
import pl.sda.service.UserService;

@Slf4j
@Controller
public class RegistrationController {
    private final UserService userService;
    private final AutologinService autologinService;

    public RegistrationController(UserService userService, AutologinService autologinService) {
        this.userService = userService;
        this.autologinService = autologinService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(ModelMap modelMap) {
        modelMap.addAttribute("emptyUser", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String handleNewUser(@ModelAttribute("emptyUser") User user, ModelMap modelMap) {
        log.info("Received new user: " + user.getUsername());
        try {
            if (userService.existsByUsername(user.getUsername())) {
                log.info("User with username: " + user.getUsername() + " exists!");
                modelMap.addAttribute("exceptionMessage", "User with username: " + user.getUsername() + " exists!");
                return "registration";
            }
        } catch (EmptyUsernameException e) {
            log.info(e.getMessage());
            modelMap.addAttribute("exceptionMessage", e.getMessage());
            return "registration";
        }

        userService.save(user);

        autologinService.autologin(user.getUsername());
        return "redirect:/event/list";
    }
}
