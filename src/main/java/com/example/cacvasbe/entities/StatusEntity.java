package com.example.cacvasbe.entities;

import com.example.cacvasbe.enums.GenericStatusConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class StatusEntity implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private Long id;

    @Basic
    protected LocalDateTime dateDeactivated;
    @Basic
    @Column(
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    protected GenericStatusConstant status;

    @JsonIgnore
    @ManyToOne
    private PortalUsers deactivatedBy;

    @JsonIgnore
    @ManyToOne
    private PortalUsers createdBy;

    @Basic
    private String reasonForDeactivation;

//    @Basic
//    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
//    private LocalDateTime createdAt;

    @Basic
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;

    @Basic
    private LocalDateTime lastUpdatedAt;

}