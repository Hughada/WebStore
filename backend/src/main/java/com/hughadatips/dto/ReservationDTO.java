package com.hughadatips.dto;

import com.hughadatips.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDTO {
    private Long id;
    private Long userId;
    private TripDTO trip;
    private int nbPlaces;
    private LocalDateTime dateReservation;
    private Reservation.Statut statut;
}
