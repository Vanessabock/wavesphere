package de.vanessabock.backend.security;

import de.vanessabock.backend.user.model.User;
import de.vanessabock.backend.user.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SecurityConfigTest {
    private final UserRepo userRepo = mock(UserRepo.class);

    private SecurityConfig underTest;

    @BeforeEach
    void setUp() {
        underTest = new SecurityConfig(userRepo);
    }

    @Test
    void saveNewUserTest_whenUserNotExists_thenSaveNewUser() {
        // GIVEN
        Integer githubId = 123423;
        OAuth2User oauth2User = mock(OAuth2User.class);
        when(oauth2User.getAttribute("id")).thenReturn(githubId);
        when(userRepo.existsUserByGithubId(githubId.toString())).thenReturn(false);

        User testUser = new User("123", "123423", "User", new ArrayList<>(), new ArrayList<>());
        when(userRepo.save(testUser)).thenReturn(testUser);

        // WHEN
        boolean actual = underTest.saveNewUser(oauth2User);

        // THEN
        assertTrue(actual);
        verify(userRepo, times(1)).save(any(User.class));
        verify(userRepo, times(1)).existsUserByGithubId(githubId.toString());
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void saveNewUserTest_whenUserExists_thenReturnTrue() {
        // GIVEN
        Integer githubId = 123423;
        OAuth2User oauth2User = mock(OAuth2User.class);
        when(oauth2User.getAttribute("id")).thenReturn(githubId);
        when(userRepo.existsUserByGithubId(githubId.toString())).thenReturn(true);

        // WHEN
        boolean actual = underTest.saveNewUser(oauth2User);

        // THEN
        assertTrue(actual);
        verify(userRepo, times(1)).existsUserByGithubId(githubId.toString());
        verifyNoMoreInteractions(userRepo);
    }

    @Test
    void saveNewUserTest_whenOAuthUserHasNoEmail_thenReturnFalse() {
        // ARRANGE
        String githubId = "";
        OAuth2User oauth2User = mock(OAuth2User.class);
        when(oauth2User.getAttribute("email")).thenReturn(githubId);

        // ACT
        boolean actual = underTest.saveNewUser(oauth2User);

        // ASSERT
        assertFalse(actual);
    }
}