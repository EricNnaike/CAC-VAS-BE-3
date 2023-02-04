package com.example.cacvasbe.entities;

import com.example.cacvasbe.enums.PermissionTypeConstant;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authorities")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PermissionTypeConstant permissionTypeConstant;

    @ManyToOne
    @JoinColumn(name = "_ID", referencedColumnName = "id")
    private Role role;

}
