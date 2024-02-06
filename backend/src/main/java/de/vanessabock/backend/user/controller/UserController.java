package de.vanessabock.backend.user.controller;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public User getLoggedInUser(@AuthenticationPrincipal OAuth2User principal){
        return userService.getLoggedInUser(principal);
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

}
