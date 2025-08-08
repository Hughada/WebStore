package com.hughadatips.dto;

import com.hughadatips.entity.Trip;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDTO {
    private Long id;
    private String titre;
    private String description;
    private LocalDate dateDepart;
    private int duree;
    private BigDecimal prix;
    private int nbPlaces;
    private List<String> images; // <-- liste d’URLs
    private Trip.Statut statut;
}
