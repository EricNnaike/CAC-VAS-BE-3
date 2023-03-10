package com.example.cacvasbe.services.userService;

import com.example.cacvasbe.entities.BlacklistedToken;
import com.example.cacvasbe.repository.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final BlacklistedTokenRepository blacklistedTokenRepository;

    @Override
    public BlacklistedToken blacklistToken(String token) {
        BlacklistedToken blackListedToken = new BlacklistedToken();
        blackListedToken.setToken(token.substring(7));

        blacklistedTokenRepository.save(blackListedToken);

        return blackListedToken;
    }

    @Override
    public boolean tokenExist(String token) {
        return false;
    }
}
