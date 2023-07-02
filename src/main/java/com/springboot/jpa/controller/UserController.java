package com.springboot.jpa.controller;

import com.springboot.jpa.domain.User;
import com.springboot.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;


    /*
    점주 사용자를 등록하는 기능을 수행합니다.
     */
    @PostMapping("/register_owner")
    public User RegisterOwner(@RequestBody User user) {
        user.setRole("OWNER");
        return userRepository.save(user);
    }

    /*
    일반 고객 사용자를 등록하는 기능을 수행합니다.
     */
    @PostMapping("/register_customer")
    public User RegisterCustomer(@RequestBody User user) {
        user.setRole("CUSTOMER");
        return userRepository.save(user);
    }


}