package kh.acleda.asset_management.dto.role;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    private String name;

    private String description;

    private Set<Long> permissionIds;
}