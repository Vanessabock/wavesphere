package de.vanessabock.backend.user;

import de.vanessabock.backend.user.model.ListeningStatistic;
import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import de.vanessabock.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    UserRepo userRepo = Mockito.mock(UserRepo.class);

    UserService userService = new UserService(userRepo);


    @Test
    void getLoggedInUserTest_ShouldReturnNull_WhenUserIsNull() {
        //GIVEN
        //WHEN
        User result = userService.getLoggedInUser(null);

        //THEN
        assertNull(result);
        verifyNoInteractions(userRepo);
    }

    @Test
    void getLoggedInUserTest_ShouldReturnNull_WhenGithubIdIsNull() {
        //GIVEN
        OAuth2User user = mock(OAuth2User.class);
        when(user.getAttribute("id")).thenReturn(null);

        //WHEN
        User result = userService.getLoggedInUser(user);

        //THEN
        assertNull(result);
        verifyNoInteractions(userRepo);
    }

    @Test
    void getUserTest_ShouldReturnExistingUser_WhenUserInDatabase() {
        //GIVEN
        OAuth2User user = mock(OAuth2User.class);
        Integer id = 123456;
        String name = "User";
        when(user.getAttribute("id")).thenReturn(id);
        when(userRepo.existsUserByGithubId(id.toString())).thenReturn(true);
        when(user.getAttribute("name")).thenReturn(name);
        User expected = new User("existingId", id.toString(), name, new ArrayList<>(), new ArrayList<>());
        when(userRepo.findUserByGithubId(id.toString())).thenReturn(expected);

        //WHEN
        User actual = userService.getLoggedInUser(user);

        //THEN
        assertEquals(expected, actual);
        verify(userRepo, times(1)).findUserByGithubId(id.toString());
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void updateUserTest_ShouldSaveUserToDatabaseAndReturnUser_WhenUserIsUpdated() {
        //GIVEN
        User expected = new User("123", "123423", "User", new ArrayList<>(), new ArrayList<>());
        when(userRepo.save(any(User.class))).thenReturn(expected);

        //WHEN
        User actual = userService.updateUser(expected);

        //THEN
        assertEquals(expected, actual);
        verify(userRepo, times(1)).save(expected);
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void getUserByIdTest_whenUserExists_returnUser(){
        // GIVEN
        User expected = new User("123", "123423", "User", new ArrayList<>(), new ArrayList<>());
        when(userRepo.findById(any(String.class))).thenReturn(Optional.of(expected));

        // WHEN
        User actual = userService.getUserById("123");

        // THEN
        assertEquals(expected, actual);
        verify(userRepo, times(1)).findById(any(String.class));
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void getUserByIdTest_whenUserNotExists_returnNull(){
        // GIVEN
        when(userRepo.findById(any(String.class))).thenReturn(Optional.empty());

        // WHEN
        User actual = userService.getUserById("123");

        // THEN
        assertNull(actual);
        verify(userRepo, times(1)).findById(any(String.class));
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void addListenedTimeToUserTest_whenNewStationListened_addStationAndTimeToUser(){
        // GIVEN
        User user = new User("123", "123", "user", new ArrayList<>(), new ArrayList<>());
        String radioStationName = "RadioStation";
        double listenedTime = 60.0;
        User expected = new User("123", "123", "user", new ArrayList<>(), List.of(new ListeningStatistic(radioStationName, listenedTime)));
        when(userService.updateUser(any(User.class))).thenReturn(expected);

        // WHEN
        User actual = userService.addListenedTimeToUser(user, radioStationName, listenedTime);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void addListenedTimeToUserTest_whenStationAlreadyListened_addTimeToUser(){
        // GIVEN
        String radioStationName = "RadioStation";
        double listenedTime = 60.0;
        User user = new User("123", "123", "user", new ArrayList<>(), new ArrayList<>());
        user.listeningStatistics().add(new ListeningStatistic(radioStationName, 30));

        User expected = new User("123", "123", "user", new ArrayList<>(), List.of(new ListeningStatistic(radioStationName, 90)));
        when(userService.updateUser(any(User.class))).thenReturn(expected);

        // WHEN
        User actual = userService.addListenedTimeToUser(user, radioStationName, listenedTime);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    void addListenedTimeToUserTest_whenUserNull_returnNull(){
        // GIVEN
        String radioStationName = "RadioStation";
        double listenedTime = 60.0;
        User user = null;

        // WHEN
        User actual = userService.addListenedTimeToUser(user, radioStationName, listenedTime);

        // THEN
        assertNull(actual);
    }
}
