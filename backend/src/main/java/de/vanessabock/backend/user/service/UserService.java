package de.vanessabock.backend.user.service;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getLoggedInUser(OAuth2User user) {
        if (user == null){
            return null;
        }

        Integer githubId = user.getAttribute("id");

        if(githubId == null){
            return null;
        }

        return userRepo.findUserByGithubId(githubId.toString());
    }

    public User updateUser(User user){
        return userRepo.save(user);
    }

    public User getUserById(String id){
        return userRepo.findById(id).orElse(null);
    }
}
