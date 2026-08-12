package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.User;
import kh.acleda.asset_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create user
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userRepository.save(user));
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> update(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return userRepository.findById(id)
                .map(existing -> {

                    // Login information
                    existing.setUsername(user.getUsername());
                    existing.setEmail(user.getEmail());

                    // Personal information
                    existing.setFirstName(user.getFirstName());
                    existing.setLastName(user.getLastName());
                    existing.setGender(user.getGender());
                    existing.setPhone(user.getPhone());

                    // Staff information
                    existing.setPosition(user.getPosition());
                    existing.setDepartment(user.getDepartment());

                    // Role and status
                    existing.setRole(user.getRole());
                    existing.setStatus(user.getStatus());

                    // Only update password if provided
                    if (user.getPassword() != null &&
                            !user.getPassword().isBlank()) {

                        existing.setPassword(user.getPassword());
                    }

                    return ResponseEntity.ok(
                            userRepository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {

        return userRepository.findByUsername(loginRequest.getUsername())
                .map(user -> {
                    if (!user.getPassword().equals(loginRequest.getPassword())) {
                        return ResponseEntity
                                .status(401)
                                .body("Invalid username or password");
                    }

                    return ResponseEntity.ok(user);
                })
                .orElse(
                        ResponseEntity
                                .status(401)
                                .body("Invalid username or password")
                );
    }
}