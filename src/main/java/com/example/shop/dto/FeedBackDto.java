package com.example.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackDto {
    private long id;
    private String content;
    private int score;
    @JsonProperty("feedback_time")
    private LocalDateTime feedbackTime;
    @JsonProperty("user_name")
    private String username; // From User entity
}
