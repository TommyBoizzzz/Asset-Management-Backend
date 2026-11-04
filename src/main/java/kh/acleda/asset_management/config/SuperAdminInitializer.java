
package kh.acleda.asset_management.config;

import kh.acleda.asset_management.entity.Role;
import kh.acleda.asset_management.entity.User;
import kh.acleda.asset_management.repository.RoleRepository;
import kh.acleda.asset_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // 1. Create default roles
        Role superAdminRole = createRole(
                "SUPERADMIN",
                "Full system access"
        );

        createRole(
                "ADMIN",
                "Manage assets, users and departments"
        );

        createRole(
                "USER",
                "Standard system user"
        );

        // 2. Create Super Admin account
        if (userRepository.existsByUsername("superadmin")) {
            System.out.println("Super Admin already exists.");
            return;
        }

        User superAdmin = User.builder()
                .username("superadmin")
                .password("CHANGE_ME")
                .email("superadmin@assetmanagement.com")
                .fullName("Super Admin")
                .department("IT")
                .role(superAdminRole)
                .status("ACTIVE")
                .build();

        userRepository.save(superAdmin);

        System.out.println("Super Admin created successfully!");
    }

    // Create role if it does not exist
    private Role createRole(String name, String description) {

        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name(name)
                                .description(description)
                                .build()
                ));
    }
}
