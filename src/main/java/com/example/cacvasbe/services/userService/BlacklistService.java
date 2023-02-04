package com.example.cacvasbe.services.userService;

import com.example.cacvasbe.entities.BlacklistedToken;

public interface BlacklistService {
    BlacklistedToken blacklistToken(String token);
    boolean tokenExist(String token);
}
