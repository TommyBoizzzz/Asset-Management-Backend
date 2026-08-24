package kh.acleda.asset_management.config;

import kh.acleda.asset_management.entity.Permission;
import kh.acleda.asset_management.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {

        createPermission(
                "View Dashboard",
                "Dashboard",
                "View dashboard"
        );

        createPermission(
                "View Assets",
                "Assets",
                "View assets"
        );

        createPermission(
                "Create Assets",
                "Assets",
                "Create assets"
        );

        createPermission(
                "Edit Assets",
                "Assets",
                "Edit assets"
        );

        createPermission(
                "Delete Assets",
                "Assets",
                "Delete assets"
        );

        createPermission(
                "Assign Assets",
                "Assets",
                "Assign assets"
        );

        createPermission(
                "View Users",
                "Users",
                "View users"
        );

        createPermission(
                "Register Users",
                "Users",
                "Register users"
        );

        createPermission(
                "Edit Users",
                "Users",
                "Edit users"
        );

        createPermission(
                "Delete Users",
                "Users",
                "Delete users"
        );

        createPermission(
                "View Roles",
                "Roles & Permissions",
                "View roles"
        );

        createPermission(
                "Create Roles",
                "Roles & Permissions",
                "Create roles"
        );

        createPermission(
                "Edit Roles",
                "Roles & Permissions",
                "Edit roles"
        );

        createPermission(
                "Delete Roles",
                "Roles & Permissions",
                "Delete roles"
        );

        createPermission(
                "Manage Permissions",
                "Roles & Permissions",
                "Manage permissions"
        );

        createPermission(
                "View History",
                "History",
                "View history"
        );
    }

    private void createPermission(
            String name,
            String category,
            String description
    ) {

        if (!permissionRepository.existsByName(name)) {

            Permission permission = Permission.builder()
                    .name(name)
                    .category(category)
                    .description(description)
                    .build();

            permissionRepository.save(permission);
        }
    }
}