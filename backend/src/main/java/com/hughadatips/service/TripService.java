package com.hughadatips.service;

import com.hughadatips.dto.TripDTO;
import com.hughadatips.entity.Trip;
import com.hughadatips.repository.TripRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public List<TripDTO> findAll() {
        return tripRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<TripDTO> findAvailable() {
        return tripRepository.findByStatut(Trip.Statut.DISPONIBLE).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public TripDTO findById(Long id) {
        return tripRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public Trip createOrUpdate(Trip trip) {
        if (trip.getStatut() == null) {
            trip.setStatut(Trip.Statut.DISPONIBLE);
        }
        return tripRepository.save(trip);
    }

    public TripDTO createFromDto(TripDTO dto,List<MultipartFile> files) {
        Trip trip = new Trip();
        trip.setTitre(dto.getTitre());
        trip.setDescription(dto.getDescription());
        trip.setDateDepart(dto.getDateDepart());
        trip.setDuree(dto.getDuree());
        trip.setPrix(dto.getPrix());
        trip.setNbPlaces(dto.getNbPlaces());
        String images = dto.getImages() == null ? "" : String.join(",", dto.getImages());
        trip.setImages(images);
        trip.setStatut(Trip.Statut.DISPONIBLE);            // ou dto.getStatut()

        // upload images
        List<String> urls = files == null ? List.of() : files.stream()
                .map(this::storeImage)
                .toList();
        trip.setImages(String.join(",", urls));
        return toDTO(tripRepository.save(trip));
    }

    private String storeImage(MultipartFile file) {
        Path path = Paths.get("uploads", file.getOriginalFilename());
        try {
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Upload failed", e);
        }
    }

    public TripDTO updateFromDto(Long id, TripDTO dto ,List<MultipartFile> files) {
        Trip existing = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trip introuvable"));
        existing.setTitre(dto.getTitre());
        existing.setDescription(dto.getDescription());
        existing.setDateDepart(dto.getDateDepart());
        existing.setDuree(dto.getDuree());
        existing.setPrix(dto.getPrix());
        existing.setNbPlaces(dto.getNbPlaces());
        String images = dto.getImages() == null ? "" : String.join(",", dto.getImages());
        existing.setImages(images);
        existing.setStatut(dto.getStatut() != null ? dto.getStatut() : existing.getStatut());




        // upload images
        List<String> urls = files == null ? List.of() : files.stream()
                .map(this::storeImage)
                .toList();
        existing.setImages(String.join(",", urls));
        return toDTO(tripRepository.save(existing));

    }


    public void delete(Long id) {
        tripRepository.deleteById(id);
    }

    public  TripDTO toDTO(Trip trip) {
        if (trip == null) return null;
        return TripDTO.builder()
                .id(trip.getId())
                .titre(trip.getTitre())
                .description(trip.getDescription())
                .dateDepart(trip.getDateDepart())
                .duree(trip.getDuree())
                .prix(trip.getPrix())
                .nbPlaces(trip.getNbPlaces())
                .images(List.of(trip.getImages().split(",")))
                .statut(trip.getStatut())
                .build();
    }
}
