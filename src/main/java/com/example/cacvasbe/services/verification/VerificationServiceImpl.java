package com.example.cacvasbe.services.verification;

import org.springframework.stereotype.Service;

@Service
public class VerificationServiceImpl implements VerificationService{

//    private final ConfirmationTokenService confirmationTokenService;
//
//    public VerificationServiceImpl(ConfirmationTokenService confirmationTokenService) {
//        this.confirmationTokenService = confirmationTokenService;
//    }


//    @Override
//    public Object verifyUser(String userToken) {
//        ConfirmationToken confirmationToken = confirmationTokenService.getToken(userToken);
//        if (confirmationToken == null) {
//            throw new CustomNotFoundException("token not found");
//        }
//        if (confirmationToken.getConfirmedAt() != null) {
//            throw new EntityAlreadyExistException("email already confirmed");
//        }
//        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
//        if (expiredAt.isBefore(LocalDateTime.now())) {
//            throw new CustomNotFoundException("token expired");
//        }
//        confirmationTokenService.setConfirmedAt(userToken);
//        confirmationToken.getUser().setVerified(true);
//        return "confirmed";
//    }
}
