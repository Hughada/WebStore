package com.hughadatips.service;

import com.hughadatips.entity.Payment;
import com.hughadatips.entity.Reservation;
import com.hughadatips.repository.PaymentRepository;
import com.hughadatips.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ReservationRepository reservationRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          ReservationRepository reservationRepository) {
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    public Payment createPayment(Long reservationId, BigDecimal montant, String methode) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Réservation introuvable"));

        Payment payment = Payment.builder()
                .reservation(reservation)
                .montant(montant)
                .date(LocalDateTime.now())
                .methode(methode)
                .statut(Payment.Statut.PENDING)
                .build();

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment updatePaymentStatus(Long paymentId, Payment.Statut statut) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Paiement introuvable"));
        payment.setStatut(statut);
        return paymentRepository.save(payment);
    }
}
