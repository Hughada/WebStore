package com.hughadatips.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactMessageDTO {
    private Long id;
    private String nom;
    private String email;
    private String sujet;
    private String contenu;
    private LocalDateTime date;
}
