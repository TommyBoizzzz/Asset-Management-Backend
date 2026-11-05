
package kh.acleda.asset_management.config;

import kh.acleda.asset_management.entity.Role;
import kh.acleda.asset_management.entity.User;
import kh.acleda.asset_management.repository.RoleRepository;
import kh.acleda.asset_management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;

@Component
@RequiredArgsConstructor
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // Create ONLY SUPERADMIN role
        Role superAdminRole = roleRepository.findByName("SUPERADMIN")
                .orElseGet(() -> roleRepository.save(
                        Role.builder()
                                .name("SUPERADMIN")
                                .description("Full system access")
                                .permissions(new LinkedHashSet<>())
                                .permissionsConfigured(false)
                                .build()
                ));

        // Create Super Admin account
        if (userRepository.existsByUsername("Administrator")) {
            System.out.println("Super Admin already exists.");
            return;
        }

        User superAdmin = User.builder()
                .username("Administrator")
                .password("@Administrator123")
                .email("administrator@assetmanagement.com")
                .fullName("Administrator")
                .gender("Male")
                .phone("011779155")
                .department("Administrator")
                .position("System Administrator")
                .role(superAdminRole)
                .status("ACTIVE")
                .build();

        userRepository.save(superAdmin);

        System.out.println("Super Admin created successfully!");
    }
}
