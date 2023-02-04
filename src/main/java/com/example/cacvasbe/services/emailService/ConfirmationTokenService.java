package com.example.cacvasbe.services.emailService;


import com.example.cacvasbe.email.ConfirmationToken;

public interface ConfirmationTokenService {
     void saveConfirmationToken(ConfirmationToken token);
     ConfirmationToken getToken(String token);
     int setConfirmedAt(String token);
}
