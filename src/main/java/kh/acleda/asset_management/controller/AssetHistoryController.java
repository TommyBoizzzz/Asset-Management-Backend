package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.AssetHistory;
import kh.acleda.asset_management.repository.AssetHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-history")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssetHistoryController {

    private final AssetHistoryRepository repository;

    // Get all history
    @GetMapping
    public ResponseEntity<List<AssetHistory>> getAll() {
        return ResponseEntity.ok(
                repository.findAll()
        );
    }

    // Get history by ID
    @GetMapping("/{id}")
    public ResponseEntity<AssetHistory> getById(
            @PathVariable Long id
    ) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get history for specific asset
    @GetMapping("/asset/{assetId}")
    public ResponseEntity<List<AssetHistory>> getByAsset(
            @PathVariable Long assetId
    ) {
        return ResponseEntity.ok(
                repository.findByAssetIdOrderByCreatedAtDesc(
                        assetId
                )
        );
    }

    // Get history created by specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetHistory>> getByUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                repository.findByUserIdOrderByCreatedAtDesc(
                        userId
                )
        );
    }

    // Get history by action
    @GetMapping("/action/{action}")
    public ResponseEntity<List<AssetHistory>> getByAction(
            @PathVariable String action
    ) {
        return ResponseEntity.ok(
                repository.findByActionOrderByCreatedAtDesc(
                        action
                )
        );
    }

    // Create history record
    @PostMapping
    public ResponseEntity<AssetHistory> create(
            @RequestBody AssetHistory history
    ) {
        return ResponseEntity.ok(
                repository.save(history)
        );
    }

    // Delete history
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