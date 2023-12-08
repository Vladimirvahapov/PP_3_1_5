package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DatabaseInitializer {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public DatabaseInitializer(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    @Transactional
    public void initializeDatabase() {
        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");
        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        User userFirst = new User("Иван", "Иванович", 23, "admin", passwordEncoder.encode("admin"));
        User userSecond = new User("Петр", "Петрович", 17, "user", passwordEncoder.encode("user"));
        userFirst.setUserRoles(Set.of(adminRole, userRole));
        userSecond.setUserRoles(Set.of(userRole));

        userRepository.save(userFirst);
        userRepository.save(userSecond);
    }
}
