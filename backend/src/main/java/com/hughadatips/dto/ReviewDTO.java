package com.hughadatips.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private int note;
    private String commentaire;
    private LocalDateTime date;
    private Long userId;
    private Long tripId;
}
