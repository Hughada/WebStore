package com.hughadatips.controller;

import com.hughadatips.dto.ReviewDTO;
import com.hughadatips.service.ReviewService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody AddReviewRequest request) {
        ReviewDTO reviewDTO = reviewService.addReview(request.getUserId(), request.getTripId(), request.getNote(), request.getCommentaire());
        return ResponseEntity.ok(reviewDTO);
    }

    @GetMapping("/trip/{tripId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByTrip(@PathVariable Long tripId) {
        List<ReviewDTO> list = reviewService.findByTripId(tripId);
        return ResponseEntity.ok(list);
    }

    @Data
    public static class AddReviewRequest {
        private Long userId;
        private Long tripId;
        private int note;
        private String commentaire;
    }

}
