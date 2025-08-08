package com.hughadatips.controller;

import com.hughadatips.entity.Payment;
import com.hughadatips.service.PaymentService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        Payment payment = paymentService.createPayment(request.getReservationId(), request.getMontant(), request.getMethode());
        return ResponseEntity.ok(payment);
    }

    @GetMapping
    public ResponseEntity<List<Payment>> getPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Payment> updateStatus(@PathVariable Long id, @RequestParam Payment.Statut statut) {
        Payment payment = paymentService.updatePaymentStatus(id, statut);
        return ResponseEntity.ok(payment);
    }

    @Data
    public static class CreatePaymentRequest {
        private Long reservationId;
        private BigDecimal montant;
        private String methode;
    }
}
