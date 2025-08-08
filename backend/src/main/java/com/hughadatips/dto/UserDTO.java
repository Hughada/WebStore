package com.hughadatips.dto;

import com.hughadatips.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private User.ROLE role;
}
