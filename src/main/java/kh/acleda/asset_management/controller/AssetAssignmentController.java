package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.AssetAssignment;
import kh.acleda.asset_management.repository.AssetAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/asset-assignments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssetAssignmentController {

    private final AssetAssignmentRepository repository;

    // Get all assignments
    @GetMapping
    public ResponseEntity<List<AssetAssignment>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Get assignment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssetAssignment> getById(
            @PathVariable Long id
    ) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get assignments by asset
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<AssetAssignment>> getByAsset(
            @PathVariable Long assetId
    ) {
        return ResponseEntity.ok(
                repository.findByAssetId(assetId)
        );
    }

    // Get assignments by staff
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<AssetAssignment>> getByStaff(
            @PathVariable Long staffId
    ) {
        return ResponseEntity.ok(
                repository.findByStaffId(staffId)
        );
    }

    // Get active assignments
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AssetAssignment>> getByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                repository.findByStatus(status)
        );
    }

    // Create assignment
    @PostMapping
    public ResponseEntity<AssetAssignment> create(
            @RequestBody AssetAssignment assignment
    ) {
        if (assignment.getAssignedAt() == null) {
            assignment.setAssignedAt(LocalDateTime.now());
        }

        if (assignment.getStatus() == null) {
            assignment.setStatus("ACTIVE");
        }

        return ResponseEntity.ok(
                repository.save(assignment)
        );
    }

    // Update assignment
    @PutMapping("/{id}")
    public ResponseEntity<AssetAssignment> update(
            @PathVariable Long id,
            @RequestBody AssetAssignment assignment
    ) {

        return repository.findById(id)
                .map(existing -> {

                    existing.setAsset(assignment.getAsset());
                    existing.setStaff(assignment.getStaff());
                    existing.setAssignedBy(assignment.getAssignedBy());
                    existing.setAssignedAt(assignment.getAssignedAt());
                    existing.setExpectedReturnDate(
                            assignment.getExpectedReturnDate()
                    );
                    existing.setReturnedAt(
                            assignment.getReturnedAt()
                    );
                    existing.setConditionBefore(
                            assignment.getConditionBefore()
                    );
                    existing.setConditionAfter(
                            assignment.getConditionAfter()
                    );
                    existing.setNotes(
                            assignment.getNotes()
                    );
                    existing.setStatus(
                            assignment.getStatus()
                    );

                    return ResponseEntity.ok(
                            repository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Return asset
    @PutMapping("/{id}/return")
    public ResponseEntity<AssetAssignment> returnAsset(
            @PathVariable Long id,
            @RequestParam(required = false) String conditionAfter,
            @RequestParam(required = false) String notes
    ) {

        return repository.findById(id)
                .map(existing -> {

                    existing.setReturnedAt(
                            LocalDateTime.now()
                    );

                    existing.setConditionAfter(
                            conditionAfter
                    );

                    if (notes != null) {
                        existing.setNotes(notes);
                    }

                    existing.setStatus("RETURNED");

                    return ResponseEntity.ok(
                            repository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete assignment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}