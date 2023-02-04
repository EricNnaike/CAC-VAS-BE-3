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
@Table(name = "process_task_history")
public class ProcessTaskHistory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_master_table")
    private String taskMasterTable;
    private Long taskMasterTableId;
    private String taskStatus;
    private LocalDateTime timeCreated;
    @Column(name = "company_fk")
    private Long companyFk;
    private Long officerPortalUserFk;
    private Long portalUserFk;
    @Column(name = "process_type_fk")
    private Long processTypeFk;
}
