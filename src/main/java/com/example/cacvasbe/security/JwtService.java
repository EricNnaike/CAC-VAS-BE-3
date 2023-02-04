package com.example.cacvasbe.security;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.infrastructure.configuration.AppConfigurationProperties;
import com.example.cacvasbe.repository.PortalUserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author uhuegbulem chinomso
 * email: chimaisaac60@gmail.com
 * Oct, 2022
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final AppConfigurationProperties appConfigurationProperties;
    private final PortalUserRepository portalUserRepository;
    public PortalUsers user;

    public String generateJwtToken(Long id){
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(appConfigurationProperties.getJwtSecret());
            token = JWT.create()
                    .withClaim("userId", id)
                    .withExpiresAt((new Date((new Date()).getTime() + appConfigurationProperties.getJwtExpiration() * 1000L)))
                    .withIssuer("")
                    .sign(algorithm);

        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return token;
    }

    public String detokenizeJwtToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(appConfigurationProperties.getJwtSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("")
                .build();
        DecodedJWT jwt = verifier.verify(token);

        requestPrincipal(jwt.getClaim("userId").asLong());

        return portalUserRepository.findByIdAndStatus(jwt.getClaim("userId").asLong(), GenericStatusConstant.ACTIVE).get().getUsername();
    }

    private void requestPrincipal(Long id){
        this.user = portalUserRepository.findByIdAndStatus(id, GenericStatusConstant.ACTIVE).get();
    }


    public boolean validateJwtToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(appConfigurationProperties.getJwtSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Claim userId = jwt.getClaim("userId");
            log.info("USER ======" + userId.toString());
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }


}
