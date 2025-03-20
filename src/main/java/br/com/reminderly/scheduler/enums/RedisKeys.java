package br.com.reminderly.scheduler.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKeys {

    SCHEDULED_REMINDER_KEY("scheduled_reminder:"),
    REMINDER_HISTORY_KEY("reminder_history:"),
    SCHEDULED_REMINDER_PATTERN("scheduled_reminder:*");

    private final String message;

}
