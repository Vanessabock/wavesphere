package de.vanessabock.backend.user.controller;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public User getLoggedInUser(@AuthenticationPrincipal OAuth2User principal){
        return userService.getLoggedInUser(principal);
    }

    @PutMapping
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @GetMapping("/logout")
    public void logout(HttpSession httpSession){
        httpSession.invalidate();
    }

}
