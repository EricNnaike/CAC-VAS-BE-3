package com.example.cacvasbe.dto.role;

import com.example.cacvasbe.dto.authority.Permission;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDto {
    private String name;
    private Set<Permission> authorities;

}
