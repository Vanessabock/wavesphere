package de.vanessabock.backend.user.service;

import de.vanessabock.backend.user.model.ListeningStatistic;
import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    public User addListenedTimeToUser(User user, String radioStationName, double listenedTime){
        if (user == null){
            return null;
        } else {
            if (user.listeningStatistics().stream().anyMatch(s -> s.stationName().equals(radioStationName))){
                List<ListeningStatistic> ls = user.listeningStatistics().stream().filter(s -> s.stationName().equals(radioStationName)).toList();
                user.listeningStatistics().remove(ls.getFirst());
                user.listeningStatistics().add(new ListeningStatistic(ls.getFirst().stationName(), ls.getFirst().listenedTime() + listenedTime));
            } else {
                user.listeningStatistics().add(new ListeningStatistic(radioStationName, listenedTime));
            }
            return updateUser(user);
        }

    }
}
