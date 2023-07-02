package com.springboot.jpa.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {

    @Id
    private String email;

    @Column
    private String content;

    public String getUserEmail(ReviewDTO reviewDTO) {

        return reviewDTO.email;
    }

    public String getReviewContent(ReviewDTO reviewDTO) {
        return reviewDTO.content;
    }
}