package com.example.cacvasbe.email;

import com.example.cacvasbe.entities.PortalUsers;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken{


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    @Column
    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private PortalUsers users;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public ConfirmationToken( String token, LocalDateTime expiresAt, PortalUsers portalUsers) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.users = portalUsers;
    }

    public ConfirmationToken(String token, LocalDateTime expiresAt, PortalUsers portalUsers, LocalDateTime createdAt) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.users = portalUsers;
        this.createdAt = createdAt;
    }
}
