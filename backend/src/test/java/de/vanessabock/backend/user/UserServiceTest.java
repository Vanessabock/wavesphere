package de.vanessabock.backend.user;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import de.vanessabock.backend.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void getLoggedInUser_ShouldReturnNull_WhenUserIsNull() {
        // When
        User result = userService.getLoggedInUser(null);

        // Then
        assertNull(result);
        verifyNoInteractions(userRepo);
    }

    @Test
    void getLoggedInUser_ShouldSaveNewUser_WhenUserNotInDatabase() {
        // Given
        OAuth2User user = mock(OAuth2User.class);
        int id = 123456;
        String name = "User";
        when(user.getAttribute("id")).thenReturn(id);
        when(user.getAttribute("name")).thenReturn(name);
        when(userRepo.existsUserByGithubId(id)).thenReturn(false);

        User expected = new User("generatedId", id, name, new ArrayList<>());
        when(userRepo.save(any(User.class))).thenReturn(expected);

        // When
        User actual = userService.getLoggedInUser(user);

        // Then
        assertEquals(expected, actual);
        verify(userRepo, times(1)).existsUserByGithubId(id);
        verify(userRepo, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void getUserShouldReturnExistingUserWhenUserInDatabase() {
        // Given
        OAuth2User user = mock(OAuth2User.class);
        int id = 123456;
        String name = "User";
        when(user.getAttribute("id")).thenReturn(id);
        when(userRepo.existsUserByGithubId(id)).thenReturn(true);
        when(user.getAttribute("name")).thenReturn(name);
        User expected = new User("existingId", id, name, new ArrayList<>());
        when(userRepo.findUserByGithubId(id)).thenReturn(expected);

        // When
        User actual = userService.getLoggedInUser(user);

        // Then
        assertEquals(expected, actual);
        verify(userRepo, times(1)).existsUserByGithubId(id);
        verify(userRepo, times(1)).findUserByGithubId(id);
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void updateUserTest_ShouldSaveUserToDatabaseAndReturnUser_WhenUserIsUpdated() {
        // Given
        User expected = new User("123", 123423, "User", new ArrayList<>());
        when(userRepo.save(any(User.class))).thenReturn(expected);

        // When
        User actual = userService.updateUser(expected);

        // Then
        assertEquals(expected, actual);
        verify(userRepo, times(1)).save(expected);
        verifyNoMoreInteractions(userRepo);
    }
}
