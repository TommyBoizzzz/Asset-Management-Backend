package kh.acleda.asset_management.service;

import kh.acleda.asset_management.dto.role.PermissionResponse;
import kh.acleda.asset_management.dto.role.RoleRequest;
import kh.acleda.asset_management.dto.role.RoleResponse;
import kh.acleda.asset_management.entity.Permission;
import kh.acleda.asset_management.entity.Role;
import kh.acleda.asset_management.repository.PermissionRepository;
import kh.acleda.asset_management.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public List<RoleResponse> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RoleResponse getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found")
                );

        return toResponse(role);
    }

    public RoleResponse createRole(RoleRequest request) {

        if (roleRepository.existsByName(request.getName())) {
            throw new RuntimeException(
                    "Role already exists"
            );
        }

        Set<Permission> permissions =
                getPermissions(request.getPermissionIds());

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        Role savedRole = roleRepository.save(role);

        return toResponse(savedRole);
    }

    public RoleResponse updateRole(
            Long id,
            RoleRequest request
    ) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found")
                );

        role.setName(request.getName());
        role.setDescription(request.getDescription());

        Set<Permission> permissions =
                getPermissions(request.getPermissionIds());

        role.setPermissions(permissions);

        Role updatedRole = roleRepository.save(role);

        return toResponse(updatedRole);
    }

    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found")
                );

        roleRepository.delete(role);
    }

    public List<PermissionResponse> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .map(this::toPermissionResponse)
                .toList();
    }

    private Set<Permission> getPermissions(
            Set<Long> permissionIds
    ) {

        if (permissionIds == null ||
                permissionIds.isEmpty()) {

            return new HashSet<>();
        }

        List<Permission> permissions =
                permissionRepository.findAllById(permissionIds);

        if (permissions.size() != permissionIds.size()) {
            throw new RuntimeException(
                    "One or more permissions not found"
            );
        }

        return new HashSet<>(permissions);
    }

    private RoleResponse toResponse(Role role) {

        List<PermissionResponse> permissions =
                role.getPermissions()
                        .stream()
                        .map(this::toPermissionResponse)
                        .toList();

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(permissions)
                .build();
    }

    private PermissionResponse toPermissionResponse(
            Permission permission
    ) {

        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .category(permission.getCategory())
                .description(permission.getDescription())
                .build();
    }
}