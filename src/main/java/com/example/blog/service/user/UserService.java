package com.example.blog.service.user;

import com.example.blog.domain.User;
import com.example.blog.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).get();
    }
}
