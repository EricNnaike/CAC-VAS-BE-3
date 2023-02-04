package com.example.cacvasbe.entities;

import com.google.common.base.Strings;
import lombok.*;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Indexed
@Table(name = "AFFILIATES")
public class Affiliates {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String surname;
    private String firstname;
    @Column(name = "other_name")
    private String otherName;
    private String address;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String nationality;
    private String gender;
    @Column(name = "former_nationality")
    private String formerNationality;
    private Long age;
    private String city;
    private String occupation;
    private String state;
    private String accreditationnumber;
    @Column(name = "is_lawyer")
    private boolean lawyer;
    private Long last_visit;
    private String form_type;
    @Column(name = "is_presenter")
    private Boolean presenter;
    private Double num_shares_alloted;
    private String type_of_shares;
    private LocalDateTime date_of_birth;
    private String status;
    private LocalDateTime date_of_termination;
    private LocalDateTime date_of_appointment;
    private LocalDateTime date_of_change_of_address;
    @Column(name = "identity_number")
    private String identityNumber;
    @Column(name = "identity_issue_state")
    private String identityIssueState;

    @Column(name = "process_type_fk")
    private Long processTypeFk;

    @Column(name = "company_fk")
    private Long companyFk;
    @Column(name = "affiliate_type_fk")
    private Long affiliateTypeFk;

    @Column(name = "other_directorship_details")
    private String otherDirectorshipDetails;
    @Column(name = "country_of_residence")
    private String countryOfResidence;
    @Column(name = "is_carried_over_from_name_avai")
    private Boolean carriedOverFromNameAvai;
    @Column(name = "is_company_deleted")
    private Boolean companyDeleted;
    @Column(name = "is_corporate")
    private Boolean corporate;
    @Column(name = "is_chairman", nullable = false)
    private Boolean chairman;






}
