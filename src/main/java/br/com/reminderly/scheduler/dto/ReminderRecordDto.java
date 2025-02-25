package br.com.reminderly.scheduler.dto;

import java.util.UUID;

public record ReminderRecordDto(UUID reminderId, String message, String reminderTitle, String sendingTo,
                                String notificationType) {
}
