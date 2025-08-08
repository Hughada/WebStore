package com.hughadatips.service;

import com.hughadatips.dto.ReviewDTO;
import com.hughadatips.entity.Review;
import com.hughadatips.entity.Trip;
import com.hughadatips.entity.User;
import com.hughadatips.repository.ReviewRepository;
import com.hughadatips.repository.TripRepository;
import com.hughadatips.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         UserRepository userRepository,
                         TripRepository tripRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.tripRepository = tripRepository;
    }

    public ReviewDTO addReview(Long userId, Long tripId, int note, String commentaire) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));

        Review review = Review.builder()
                .user(user)
                .trip(trip)
                .note(note)
                .commentaire(commentaire)
                .date(LocalDateTime.now())
                .build();

        Review saved = reviewRepository.save(review);

        return toDTO(saved);
    }

    public List<ReviewDTO> findByTripId(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new IllegalArgumentException("Voyage introuvable"));
        return reviewRepository.findByTrip(trip).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO toDTO(Review review) {
        if (review == null) return null;
        return ReviewDTO.builder()
                .id(review.getId())
                .note(review.getNote())
                .commentaire(review.getCommentaire())
                .date(review.getDate())
                .tripId(review.getTrip().getId())
                .userId(review.getUser().getId())
                .build();
    }
}
