package kh.acleda.asset_management.controller;

import kh.acleda.asset_management.entity.Notification;
import kh.acleda.asset_management.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationRepository repository;

    // Get all notifications
    @GetMapping
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(
            @PathVariable Long id
    ) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get notifications for user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getByUser(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                repository.findByUserId(userId)
        );
    }

    // Get unread notifications
    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnread(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                repository.findByUserIdAndIsRead(
                        userId,
                        false
                )
        );
    }

    // Count unread notifications
    @GetMapping("/user/{userId}/unread/count")
    public ResponseEntity<Long> countUnread(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
                repository.countByUserIdAndIsRead(
                        userId,
                        false
                )
        );
    }

    // Create notification
    @PostMapping
    public ResponseEntity<Notification> create(
            @RequestBody Notification notification
    ) {
        return ResponseEntity.ok(
                repository.save(notification)
        );
    }

    // Mark notification as read
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(
            @PathVariable Long id
    ) {

        return repository.findById(id)
                .map(notification -> {

                    notification.setRead(true);

                    return ResponseEntity.ok(
                            repository.save(notification)
                    );
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Mark all user notifications as read
    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @PathVariable Long userId
    ) {

        List<Notification> notifications =
                repository.findByUserIdAndIsRead(
                        userId,
                        false
                );

        notifications.forEach(
                notification -> notification.setRead(true)
        );

        repository.saveAll(notifications);

        return ResponseEntity.noContent().build();
    }

    // Delete notification
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