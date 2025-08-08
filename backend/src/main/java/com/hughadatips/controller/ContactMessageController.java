package com.hughadatips.controller;

import com.hughadatips.dto.ContactMessageDTO;
import com.hughadatips.entity.ContactMessage;
import com.hughadatips.service.ContactMessageService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    public ContactMessageController(ContactMessageService contactMessageService) {
        this.contactMessageService = contactMessageService;
    }

    @PostMapping
    public ResponseEntity<ContactMessageDTO> sendMessage(@Valid @RequestBody ContactMessageRequest request) {
        ContactMessage message = ContactMessage.builder()
                .nom(request.getNom())
                .email(request.getEmail())
                .sujet(request.getSujet())
                .contenu(request.getMessage())
                .build();

        ContactMessageDTO dto = contactMessageService.saveMessage(message);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<ContactMessageDTO>> getAllMessages() {
        List<ContactMessageDTO> list = contactMessageService.findAll();
        return ResponseEntity.ok(list);
    }

    @Data
    public static class ContactMessageRequest {
        private String nom;
        private String email;
        private String sujet;
        private String message;
    }
}
