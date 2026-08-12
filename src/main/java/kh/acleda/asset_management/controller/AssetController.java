package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.Asset;
import kh.acleda.asset_management.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AssetController {

    private final AssetRepository assetRepository;

    @GetMapping
    public ResponseEntity<List<Asset>> getAll() {
        return ResponseEntity.ok(assetRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asset> getById(@PathVariable Long id) {
        return assetRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Asset>> getByStatus(
            @PathVariable String status
    ) {
        return ResponseEntity.ok(
                assetRepository.findByStatus(status)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Asset>> getByCategory(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(
                assetRepository.findByCategoryId(categoryId)
        );
    }

    @PostMapping
    public ResponseEntity<Asset> create(@RequestBody Asset asset) {
        return ResponseEntity.ok(assetRepository.save(asset));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> update(
            @PathVariable Long id,
            @RequestBody Asset asset
    ) {
        return assetRepository.findById(id)
                .map(existing -> {

                    existing.setAssetCode(asset.getAssetCode());
                    existing.setName(asset.getName());
                    existing.setCategory(asset.getCategory());
                    existing.setBrand(asset.getBrand());
                    existing.setModel(asset.getModel());
                    existing.setSerialNumber(asset.getSerialNumber());
                    existing.setPurchaseDate(asset.getPurchaseDate());
                    existing.setPurchasePrice(asset.getPurchasePrice());
                    existing.setWarrantyEndDate(asset.getWarrantyEndDate());
                    existing.setCondition(asset.getCondition());
                    existing.setLocation(asset.getLocation());
                    existing.setStatus(asset.getStatus());
                    existing.setDescription(asset.getDescription());
                    existing.setImageUrl(asset.getImageUrl());

                    return ResponseEntity.ok(
                            assetRepository.save(existing)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (!assetRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        assetRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}