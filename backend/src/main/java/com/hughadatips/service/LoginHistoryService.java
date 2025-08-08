package com.hughadatips.service;

import com.hughadatips.entity.LoginHistory;
import com.hughadatips.entity.User;
import com.hughadatips.repository.LoginHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository repo;

    public void recordLogin(User user) {
        LoginHistory h = new LoginHistory();
        h.setUser(user);
        h.setLoginDate(LocalDateTime.now());
        repo.save(h);
    }
}
