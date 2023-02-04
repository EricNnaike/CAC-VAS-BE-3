package com.example.cacvasbe.services.emailService;


import com.example.cacvasbe.email.ConfirmationToken;
import com.example.cacvasbe.repository.ConfirmationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
@Setter
@AllArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository repository;
    @Override
    public void saveConfirmationToken(ConfirmationToken token) {
        repository.save(token);
    }
    @Override
    public ConfirmationToken getToken(String token) {
        return repository.findByToken(token);
    }
    @Override
    public int setConfirmedAt(String token) {
        return repository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}