package com.hughadatips.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    public enum Statut {
        PENDING, SUCCESS, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @DecimalMin("0.0")
    @Column(nullable = false)
    private BigDecimal montant;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String methode; // ex: "Stripe"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;
}
