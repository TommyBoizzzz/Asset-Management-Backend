package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.Staff;
import kh.acleda.asset_management.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {

    private final StaffRepository staffRepository;

    @GetMapping
    public ResponseEntity<List<Staff>> getAll() {
        return ResponseEntity.ok(staffRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Staff> getById(@PathVariable Long id) {
        return staffRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Staff> create(@RequestBody Staff staff) {
        return ResponseEntity.ok(staffRepository.save(staff));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> update(
            @PathVariable Long id,
            @RequestBody Staff staff
    ) {
        return staffRepository.findById(id)
                .map(existing -> {
                    existing.setStaffCode(staff.getStaffCode());
                    existing.setFullName(staff.getFullName());
                    existing.setPhone(staff.getPhone());
                    existing.setEmail(staff.getEmail());
                    existing.setPosition(staff.getPosition());
                    existing.setStatus(staff.getStatus());

                    return ResponseEntity.ok(staffRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!staffRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        staffRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}