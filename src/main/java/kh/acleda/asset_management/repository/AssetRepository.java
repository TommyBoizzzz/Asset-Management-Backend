package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {

    Optional<Asset> findByAssetCode(String assetCode);

    Optional<Asset> findBySerialNumber(String serialNumber);

    List<Asset> findByStatus(String status);

    List<Asset> findByCondition(String condition);

    List<Asset> findByCategoryId(Long categoryId);

    boolean existsByAssetCode(String assetCode);
}