package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.AssetCategory;
import kh.acleda.asset_management.repository.AssetCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset-categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssetCategoryController {

    private final AssetCategoryRepository repository;

    @GetMapping
    public ResponseEntity<List<AssetCategory>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetCategory> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AssetCategory> create(
            @RequestBody AssetCategory category
    ) {
        return ResponseEntity.ok(repository.save(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetCategory> update(
            @PathVariable Long id,
            @RequestBody AssetCategory category
    ) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(category.getName());
                    existing.setDescription(category.getDescription());
                    existing.setStatus(category.getStatus());

                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}