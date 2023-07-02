package com.springboot.jpa.service;

import com.springboot.jpa.domain.Review;
import com.springboot.jpa.dto.ReviewDTO;
import com.springboot.jpa.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    /*
     사용자의 이메일과 리뷰 내용을 기반으로 리뷰 객체를 생성하고 저장하는 역할을 수행합니다.
     */

    public void createReview(ReviewDTO reviewDTO) {

        String userEmail = reviewDTO.getUserEmail(reviewDTO);
        String reviewContent = reviewDTO.getReviewContent(reviewDTO);


        Review review = Review.builder()
                .email(userEmail)
                .content(reviewContent)
                .build();

        reviewRepository.save(review);
    }


}