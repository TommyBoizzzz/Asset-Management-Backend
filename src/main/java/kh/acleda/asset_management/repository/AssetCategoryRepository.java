package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.AssetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssetCategoryRepository
        extends JpaRepository<AssetCategory, Long> {

    Optional<AssetCategory> findByName(String name);

    boolean existsByName(String name);
}