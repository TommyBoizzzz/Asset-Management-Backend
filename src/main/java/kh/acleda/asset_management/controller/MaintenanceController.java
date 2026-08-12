package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.Maintenance;
import kh.acleda.asset_management.repository.MaintenanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaintenanceController {

    private final MaintenanceRepository repository;

    // Get all maintenance records
    @GetMapping
    public ResponseEntity<List<Maintenance>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Get maintenance by ID
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getById(
            @PathVariable Long id
    ) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get maintenance by asset
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<Maintenance>> getByAsset(
            @PathVariable Long assetId
    ) {
        return ResponseEntity.ok(
                repository.findByAssetId(assetId)
        );
    }

    // Get maintenance by staff
    @GetMapping("/staff/{staffId}")
    public ResponseEntity<List<Maintenance>> getByStaff(
            @PathVariable Long staffId
    ) {
        return ResponseEntity.ok(
                repository.findByAssignedToId(staffId)
        );
    }

    // Get maintenance by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Maintenance>> getByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                repository.findByStatus(status)
        );
    }

    // Create maintenance
    @PostMapping
    public ResponseEntity<Maintenance> create(
            @RequestBody Maintenance maintenance
    ) {

        if (maintenance.getCost() == null) {
            maintenance.setCost(
                    java.math.BigDecimal.ZERO
            );
        }

        if (maintenance.getStatus() == null) {
            maintenance.setStatus("SCHEDULED");
        }

        return ResponseEntity.ok(
                repository.save(maintenance)
        );
    }

    // Update maintenance
    @PutMapping("/{id}")
    public ResponseEntity<Maintenance> update(
            @PathVariable Long id,
            @RequestBody Maintenance maintenance
    ) {

        return repository.findById(id)
                .map(existing -> {

                    existing.setAsset(
                            maintenance.getAsset()
                    );

                    existing.setAssignedTo(
                            maintenance.getAssignedTo()
                    );

                    existing.setMaintenanceType(
                            maintenance.getMaintenanceType()
                    );

                    existing.setScheduledDate(
                            maintenance.getScheduledDate()
                    );

                    existing.setCompletedDate(
                            maintenance.getCompletedDate()
                    );

                    existing.setProblemDescription(
                            maintenance.getProblemDescription()
                    );

                    existing.setActionTaken(
                            maintenance.getActionTaken()
                    );

                    existing.setCost(
                            maintenance.getCost()
                    );

                    existing.setStatus(
                            maintenance.getStatus()
                    );

                    existing.setNextMaintenanceDate(
                            maintenance.getNextMaintenanceDate()
                    );

                    existing.setNotes(
                            maintenance.getNotes()
                    );

                    return ResponseEntity.ok(
                            repository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Mark maintenance as completed
    @PutMapping("/{id}/complete")
    public ResponseEntity<Maintenance> complete(
            @PathVariable Long id,
            @RequestBody Maintenance maintenance
    ) {

        return repository.findById(id)
                .map(existing -> {

                    existing.setStatus("COMPLETED");
                    existing.setCompletedDate(
                            LocalDate.now()
                    );

                    if (maintenance.getActionTaken() != null) {
                        existing.setActionTaken(
                                maintenance.getActionTaken()
                        );
                    }

                    if (maintenance.getCost() != null) {
                        existing.setCost(
                                maintenance.getCost()
                        );
                    }

                    if (maintenance.getNextMaintenanceDate() != null) {
                        existing.setNextMaintenanceDate(
                                maintenance.getNextMaintenanceDate()
                        );
                    }

                    if (maintenance.getNotes() != null) {
                        existing.setNotes(
                                maintenance.getNotes()
                        );
                    }

                    return ResponseEntity.ok(
                            repository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete maintenance
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