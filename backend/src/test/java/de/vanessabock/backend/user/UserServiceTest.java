package de.vanessabock.backend.user;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import de.vanessabock.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {
    UserRepo userRepo = Mockito.mock(UserRepo.class);

    UserService userService = new UserService(userRepo);


    @Test
    void getLoggedInUser_ShouldReturnNull_WhenUserIsNull() {
        //GIVEN
        //WHEN
        User result = userService.getLoggedInUser(null);

        //THEN
        assertNull(result);
        verifyNoInteractions(userRepo);
    }

    @Test
    void getLoggedInUser_ShouldReturnNull_WhenGithubIdIsNull() {
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
    void getLoggedInUser_ShouldSaveNewUser_WhenUserNotInDatabase() {
        //GIVEN
        OAuth2User user = mock(OAuth2User.class);
        int id = 123456;
        String name = "User";
        when(user.getAttribute("id")).thenReturn(id);
        when(user.getAttribute("name")).thenReturn(name);
        when(userRepo.existsUserByGithubId(id)).thenReturn(false);

        User expected = new User("generatedId", id, name, new ArrayList<>());
        when(userRepo.save(any(User.class))).thenReturn(expected);

        //WHEN
        User actual = userService.getLoggedInUser(user);

        //THEN
        assertEquals(expected, actual);
        verify(userRepo, times(1)).existsUserByGithubId(id);
        verify(userRepo, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void getUserShouldReturnExistingUserWhenUserInDatabase() {
        //GIVEN
        OAuth2User user = mock(OAuth2User.class);
        int id = 123456;
        String name = "User";
        when(user.getAttribute("id")).thenReturn(id);
        when(userRepo.existsUserByGithubId(id)).thenReturn(true);
        when(user.getAttribute("name")).thenReturn(name);
        User expected = new User("existingId", id, name, new ArrayList<>());
        when(userRepo.findUserByGithubId(id)).thenReturn(expected);

        //WHEN
        User actual = userService.getLoggedInUser(user);

        //THEN
        assertEquals(expected, actual);
        verify(userRepo, times(1)).existsUserByGithubId(id);
        verify(userRepo, times(1)).findUserByGithubId(id);
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void updateUserTest_ShouldSaveUserToDatabaseAndReturnUser_WhenUserIsUpdated() {
        //GIVEN
        User expected = new User("123", 123423, "User", new ArrayList<>());
        when(userRepo.save(any(User.class))).thenReturn(expected);

        //WHEN
        User actual = userService.updateUser(expected);

        //THEN
        assertEquals(expected, actual);
        verify(userRepo, times(1)).save(expected);
        verifyNoMoreInteractions(userRepo);
    }
}
