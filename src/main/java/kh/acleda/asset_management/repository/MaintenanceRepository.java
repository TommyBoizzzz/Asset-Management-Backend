package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByAssetId(Long assetId);

    List<Maintenance> findByAssignedToId(Long staffId);

    List<Maintenance> findByStatus(String status);

    List<Maintenance> findByMaintenanceType(
            String maintenanceType
    );

    List<Maintenance> findByScheduledDate(LocalDate date);

    List<Maintenance> findByNextMaintenanceDate(
            LocalDate date
    );
}