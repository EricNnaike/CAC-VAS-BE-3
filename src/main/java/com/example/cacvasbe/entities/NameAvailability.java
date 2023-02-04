package com.example.cacvasbe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "name_availability")
public class NameAvailability {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String option2;
    private String option1;
    private String objectives;
    @Column(name = "reservation_approved")
    private Boolean reservationApproved;
    @Column(name = "is_reservation_submitted")
    private Boolean isReservationSubmitted;
    private LocalDateTime submission_date;
    private LocalDateTime approval_date;
    private LocalDateTime rejection_date;
    @Column(name = "rejection_reason")
    private String rejectionReason;
    private Long company_type_fk;
}
