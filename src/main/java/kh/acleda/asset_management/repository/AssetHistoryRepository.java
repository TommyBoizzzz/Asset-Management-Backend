package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.AssetHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetHistoryRepository
        extends JpaRepository<AssetHistory, Long> {

    List<AssetHistory> findByAssetIdOrderByCreatedAtDesc(
            Long assetId
    );

    List<AssetHistory> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    List<AssetHistory> findByActionOrderByCreatedAtDesc(
            String action
    );

    List<AssetHistory> findByAssetIdAndActionOrderByCreatedAtDesc(
            Long assetId,
            String action
    );
}