package com.hughadatips.service;
import org.springframework.beans.factory.annotation.Autowired;
import com.hughadatips.dto.ReservationDTO;
import com.hughadatips.entity.Reservation;
import com.hughadatips.entity.Trip;
import com.hughadatips.entity.User;
import com.hughadatips.repository.ReservationRepository;
import com.hughadatips.repository.TripRepository;
import com.hughadatips.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Autowired
    private TripService tripService;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              TripRepository tripRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    public ReservationDTO create(Long userId, Long tripId, int nbPlaces) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));
        if (trip.getStatut() != Trip.Statut.DISPONIBLE) {
            throw new IllegalStateException("Voyage complet ou indisponible");
        }

        int nbPlacesRestantes = trip.getNbPlaces() - getTotalBookedPlaces(trip);
        if (nbPlaces > nbPlacesRestantes) {
            throw new IllegalStateException("Nombre de places indisponibles");
        }

        Reservation reservation = Reservation.builder()
                .user(user)
                .trip(trip)
                .nbPlaces(nbPlaces)
                .dateReservation(LocalDateTime.now())
                .statut(Reservation.Statut.EN_ATTENTE)
                .build();

        Reservation saved = reservationRepository.save(reservation);

        if (getTotalBookedPlaces(trip) >= trip.getNbPlaces()) {
            trip.setStatut(Trip.Statut.COMPLET);
            tripRepository.save(trip);
        }

        return toDTO(saved);
    }

    public ReservationDTO updateReservation(Long reservationId, Long userId, int newNbPlaces) {
        // Récupération de la réservation
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));

        // Vérification que l'utilisateur est bien le propriétaire
        if (!reservation.getUser().getId().equals(userId)) {
            throw new SecurityException("Cette réservation ne vous appartient pas");
        }

        // Vérification que la réservation est encore modifiable
        if (reservation.getStatut() != Reservation.Statut.EN_ATTENTE) {
            throw new IllegalStateException("Seules les réservations en attente peuvent être modifiées");
        }

        // Mise à jour du nombre de places
        reservation.setNbPlaces(newNbPlaces);
        reservation = reservationRepository.save(reservation);

        // Retour du DTO
        return toDTO(reservation);
    }

    public ReservationDTO cancelReservation(Long reservationId, Long userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new SecurityException("Cette réservation ne vous appartient pas");
        }

        if (reservation.getStatut() != Reservation.Statut.EN_ATTENTE) {
            throw new IllegalStateException("Seules les réservations en attente peuvent être annulées");
        }

        reservation.setStatut(Reservation.Statut.ANNULEE);
        reservation = reservationRepository.save(reservation);
        return toDTO(reservation);
    }


    private int getTotalBookedPlaces(Trip trip) {
        List<Reservation> reservations = reservationRepository.findByUser(trip.getId() != null ? null : null); // fix: all reservations of trip
        reservations = reservationRepository.findAll().stream()
                .filter(r -> r.getTrip().getId().equals(trip.getId()) && r.getStatut() != Reservation.Statut.ANNULEE)
                .toList();
        return reservations.stream().mapToInt(Reservation::getNbPlaces).sum();
    }

    public List<ReservationDTO> findByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        return reservationRepository.findByUser(user).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO changeStatus(Long reservationId, Reservation.Statut statut) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));
        reservation.setStatut(statut);
        return toDTO(reservationRepository.save(reservation));
    }

    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReservationDTO updateStatus(Long id, Reservation.Statut newStatus) {
        Reservation r = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));
        r.setStatut(newStatus);
        return toDTO(reservationRepository.save(r));
    }

    public ReservationDTO toDTO(Reservation r) {
        if (r == null) return null;
        return ReservationDTO.builder()
                .id(r.getId())
                .userId(r.getUser().getId())
                .trip(tripService.toDTO(r.getTrip()))   // <- full object
                .nbPlaces(r.getNbPlaces())
                .dateReservation(r.getDateReservation())
                .statut(r.getStatut())
                .build();
    }
}
