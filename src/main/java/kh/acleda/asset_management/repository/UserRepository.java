
package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check duplicate username
    boolean existsByUsername(String username);

    // Check duplicate email
    boolean existsByEmail(String email);
}
