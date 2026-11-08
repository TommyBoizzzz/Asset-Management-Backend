package kh.acleda.asset_management.migration;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MaintenanceAssigneeUserBackfill implements ApplicationRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(MaintenanceAssigneeUserBackfill.class);

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        int mapped = jdbcTemplate.update("""
                UPDATE maintenance maintenance_record
                SET assigned_to_user_id = app_user.id
                FROM staff legacy_staff
                JOIN users app_user
                  ON lower(trim(app_user.email)) = lower(trim(legacy_staff.email))
                WHERE maintenance_record.assigned_to = legacy_staff.id
                  AND maintenance_record.assigned_to_user_id IS NULL
                  AND legacy_staff.email IS NOT NULL
                  AND trim(legacy_staff.email) <> ''
                """);

        Long unmapped = jdbcTemplate.queryForObject("""
                SELECT count(*)
                FROM maintenance maintenance_record
                WHERE maintenance_record.assigned_to IS NOT NULL
                  AND maintenance_record.assigned_to_user_id IS NULL
                """, Long.class);

        logger.info("Mapped {} maintenance assignees to system users.", mapped);
        if (unmapped != null && unmapped > 0) {
            logger.warn(
                    "{} maintenance records remain linked to legacy staff because no user account with the same email exists.",
                    unmapped);
        }
    }
}
