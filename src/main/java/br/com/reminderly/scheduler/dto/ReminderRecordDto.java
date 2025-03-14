package br.com.reminderly.scheduler.dto;

import java.time.Instant;
import java.util.UUID;

public record ReminderRecordDto(UUID id, String message, String title, String sendingTo,
                                String notificationType, Instant reminderTime) {
}
