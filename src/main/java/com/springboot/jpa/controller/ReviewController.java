package com.springboot.jpa.controller;

import com.springboot.jpa.dto.ReviewDTO;
import com.springboot.jpa.service.ReviewService;
import com.springboot.jpa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/*
이 컨트롤러는 사용자가 "CUSTOMER" 역할을 가지고 있을 때만 리뷰를 생성할 수 있도록 하며, 그 외의 역할을 가진 사용자는 권한이 없다는 응답을 반환합니다.
*/
@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final UserService userService;
    private final ReviewService reviewService;


    @PostMapping("/reviews")
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO) {

        String userEmail = reviewDTO.getUserEmail(reviewDTO);
        String userRole = userService.getUserRole(userEmail);

        if (userRole != null && userRole.equals("CUSTOMER")) {
            reviewService.createReview(reviewDTO);
            return ResponseEntity.ok("리뷰가 성공적으로 생성되었습니다.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( "리뷰를 생성할 권한이 없습니다.");
    }
}