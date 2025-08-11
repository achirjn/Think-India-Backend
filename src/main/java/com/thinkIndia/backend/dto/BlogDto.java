package com.thinkIndia.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDto {
    private int imageId;
    private String heading;
    private String content;
    private LocalDateTime postTime;
}
