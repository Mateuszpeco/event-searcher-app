package pl.sda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.sda.DTO.UserDto;
import pl.sda.exception.EmptyUsernameException;
import pl.sda.model.User;
import pl.sda.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {this.userService = userService; }

    @GetMapping("/admin/users/list")
    public String showUserList(ModelMap modelMap){
    List<UserDto> userDtoList = new ArrayList<>();
    for (User user : userService.findAll()) {
    UserDto userDto = new UserDto (user.getId(), user.getUsername(), user.getEmail(),
        user.getRoles()
                .stream()
                .map(u -> u.getName() + " ")
                .collect(Collectors.joining()));
        userDtoList.add(userDto);
    }
        modelMap.addAttribute("users", userDtoList);
        return "user-list";
    }
    @GetMapping("/admin/users/delete/{username}")
    public String deleteUser(@PathVariable String username) {

        try {
            String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
            if (userService.existsByUsername(username)) {

                if (!username.equals(loggedUser)) {
                    log.info("Deleted user with username: " + username);
                    userService.deleteByUserName(username);
                } else {
                    log.info("There is try to delete yourself user with username: " + username);
                }
            } else {
                log.info("User with username: " + username + " not exists!");
            }
        } catch (EmptyUsernameException e) {
            log.error(e.getMessage());
        } finally {
            return "redirect:/admin/users/list";
        }

    }

    @GetMapping("/admin/user/create")
    public String showNewUserForm(ModelMap modelMap) {
        modelMap.addAttribute("newUser", new UserDto());
        return "new-user";
    }

    @PostMapping("/admin/user/save")
    public String handleNewUser(@ModelAttribute("newUser") UserDto userDto) {
        log.info("Handle new user: " + userDto);

        userService.save(userDto);

        return "redirect:/admin/users/list";

    }
}
