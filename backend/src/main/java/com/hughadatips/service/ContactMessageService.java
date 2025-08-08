package com.hughadatips.service;

import com.hughadatips.dto.ContactMessageDTO;
import com.hughadatips.entity.ContactMessage;
import com.hughadatips.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageService(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    public List<ContactMessageDTO> findAll() {
        return contactMessageRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContactMessageDTO saveMessage(ContactMessage message) {
        message.setDate(LocalDateTime.now());
        ContactMessage saved = contactMessageRepository.save(message);
        return toDTO(saved);
    }

    public ContactMessageDTO toDTO(ContactMessage message) {
        if (message == null) return null;
        return ContactMessageDTO.builder()
                .id(message.getId())
                .nom(message.getNom())
                .email(message.getEmail())
                .sujet(message.getSujet())
                .contenu(message.getContenu())
                .date(message.getDate())
                .build();
    }
}
