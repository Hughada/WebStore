package com.hughadatips.repository;

import com.hughadatips.entity.Reservation;
import com.hughadatips.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
}
