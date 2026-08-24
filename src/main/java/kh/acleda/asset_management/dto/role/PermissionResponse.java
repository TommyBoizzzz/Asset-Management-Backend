package kh.acleda.asset_management.dto.role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {

    private Long id;

    private String name;

    private String category;

    private String description;
}