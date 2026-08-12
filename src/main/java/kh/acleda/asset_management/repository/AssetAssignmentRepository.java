package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.AssetAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetAssignmentRepository
        extends JpaRepository<AssetAssignment, Long> {

    List<AssetAssignment> findByAssetId(Long assetId);

    List<AssetAssignment> findByStaffId(Long staffId);

    List<AssetAssignment> findByAssignedById(Long userId);

    List<AssetAssignment> findByStatus(String status);

    List<AssetAssignment> findByAssetIdAndStatus(
            Long assetId,
            String status
    );
}