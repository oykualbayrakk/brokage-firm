package org.service;

import org.model.User;
import org.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public User save(final User user) {
        return userRepository.save(user);
    }
}
