package com.springboot.jpa.service;

import com.springboot.jpa.domain.User;
import com.springboot.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;



    /*
     주어진 이메일을 사용하여 사용자를 조회하고, 사용자가 존재하는 경우에는 해당 사용자의 역할을 반환하는 역할을 수행합니다.
     */
    public String getUserRole(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getRole();
        }
        return null;
    }
}
