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
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String approved_name;
    private String objectives;
    private Boolean registration_approved;
    private String rcNumber;
    private String address;
    private String branch_address;
    private Boolean is_registration_submitted;
    private LocalDateTime business_commencement_date;
    private String reservation_serial_no;
    private Boolean active;
    private String registration_serial_no;
    private Boolean registration_approved_by_rg;
    private String tracking_id;
    private LocalDateTime date_of_reservation;
    private Boolean needs_proficiency_docs;
    private String availability_code;
    private Long name_availibility_id;
    @Column(name = "company_type_fk")
    private Long companyTypeFk;
}
