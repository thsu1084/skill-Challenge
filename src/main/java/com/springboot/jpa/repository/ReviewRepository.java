package com.springboot.jpa.repository;

import com.springboot.jpa.domain.Review;
import com.springboot.jpa.dto.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {


}
