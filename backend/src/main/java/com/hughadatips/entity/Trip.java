package com.hughadatips.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "trip")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trip {

    public enum Statut {
        DISPONIBLE, COMPLET, INDISPONIBLE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titre;

    @NotBlank
    @Column(nullable = false, length = 3000)
    private String description;

    @FutureOrPresent
    @Column(name = "date_depart", nullable = false)
    private LocalDate dateDepart;

    @Min(1)
    @Column(nullable = false)
    private int duree;

    @DecimalMin("0.0")
    @Column(nullable = false)
    private BigDecimal prix;

    @Min(0)
    @Column(name = "nb_places", nullable = false)
    private int nbPlaces;

    @Column(length = 2000)
    private String images; // comma separated URLs

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;
}
