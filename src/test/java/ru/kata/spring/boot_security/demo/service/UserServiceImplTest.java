package ru.kata.spring.boot_security.demo.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindByUsername() {
        User expectedUser = new User("Петр", "Петрович", 17, "user2", passwordEncoder.encode("user2"));
        Mockito.when(userRepository.findByUsername("user2")).thenReturn(expectedUser);
        User foundUser = userService.findByUsername("user2");
        Assertions.assertEquals(expectedUser, foundUser);
    }
}
