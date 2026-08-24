package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.dto.role.PermissionResponse;
import kh.acleda.asset_management.dto.role.RoleRequest;
import kh.acleda.asset_management.dto.role.RoleResponse;
import kh.acleda.asset_management.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        return ResponseEntity.ok(
                roleService.getAllRoles()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getRoleById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                roleService.getRoleById(id)
        );
    }

    @PostMapping
    public ResponseEntity<RoleResponse> createRole(
            @RequestBody RoleRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.createRole(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> updateRole(
            @PathVariable Long id,
            @RequestBody RoleRequest request
    ) {

        return ResponseEntity.ok(
                roleService.updateRole(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable Long id
    ) {

        roleService.deleteRole(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<PermissionResponse>>
    getAllPermissions() {

        return ResponseEntity.ok(
                roleService.getAllPermissions()
        );
    }
}