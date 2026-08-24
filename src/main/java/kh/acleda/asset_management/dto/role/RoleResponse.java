package kh.acleda.asset_management.dto.role;

import kh.acleda.asset_management.entity.Permission;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Long id;

    private String name;

    private String description;

    private List<PermissionResponse> permissions;
}