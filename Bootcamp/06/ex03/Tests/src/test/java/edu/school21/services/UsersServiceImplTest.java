package edu.school21.services;


import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    UsersRepository usersRepository = Mockito.mock(UsersRepository.class);
    UsersServiceImpl usersService = new UsersServiceImpl(usersRepository);

    @Test
    public void authenticateSuccess() {
        User user = new User(1, "log", "123", false);
        when(usersRepository.findByLogin("log")).thenReturn(user);
        assertTrue(usersService.authenticate("log", "123"));
    }

    @Test
    public void authenticateFalse() {
        User user = new User(1, "log", "123", true);
        when(usersRepository.findByLogin("log")).thenReturn(user);
        Throwable exception = assertThrows(AlreadyAuthenticatedException.class,
                () -> usersService.authenticate("log", "123"));
        assertEquals(exception.getMessage(), "User already authenticated");
    }

    @Test
    public void passwordIncorrect() {
        User user = new User(1, "log", "123", false);
        when(usersRepository.findByLogin("log")).thenReturn(user);
        Throwable exception = assertThrows(EntityNotFoundException.class,
                () -> usersService.authenticate("log", "111"));
        assertEquals(exception.getMessage(), "invalid password");
    }

    @Test
    public void loginIncorrect() {
        User user = new User(1, "log", "123", false);
        when(usersRepository.findByLogin("log")).thenReturn(user);
        Throwable exception = assertThrows(EntityNotFoundException.class,
                () -> usersService.authenticate("login", "111"));
        assertEquals(exception.getMessage(), "invalid login");
    }

    @Test
    public void verifyCountMethods() {
        User user = new User(1, "log", "123", false);
        when(usersRepository.findByLogin("log")).thenReturn(user);
        assertTrue(usersService.authenticate("log", "123"));
        Mockito.verify(usersRepository, Mockito.times(1)).findByLogin("log");
        Mockito.verify(usersRepository, Mockito.times(1)).update(user);
    }

}
