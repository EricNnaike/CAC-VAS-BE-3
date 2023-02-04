package com.example.cacvasbe.eventlisteners;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class UserLogoutSuccessListener extends ApplicationEvent {

    @Serial
    private static final long serialVersionID = 1L;
    private final String email;
    private final String token;
    private final Date timeOfTime;

    public UserLogoutSuccessListener(String email, String token) {
        super(email);
        this.email = email;
        this.token = token;
        this.timeOfTime = Date.from(Instant.now());
    }
}
