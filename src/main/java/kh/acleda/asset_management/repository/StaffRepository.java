package kh.acleda.asset_management.repository;

import kh.acleda.asset_management.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByStaffCode(String staffCode);

    boolean existsByStaffCode(String staffCode);
}