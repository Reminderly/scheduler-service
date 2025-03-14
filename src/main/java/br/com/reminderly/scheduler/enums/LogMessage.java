package br.com.reminderly.scheduler.enums;

import java.text.MessageFormat;

public enum LogMessage {

    SERVICE_PROCESS_START("Starting process for {0} request."),
    SERVICE_PROCESS_FINISH("Finishing process for {0} request."),
    REMINDER_RECEIVED("Reminder {0} received!"),
    REMINDERS_NOT_FOUND("Reminders not found"),
    ERROR_PROCESSING_SERVICE_ACTION("Error processing service action: {0}"),
    ERROR_REMINDER_OVERDUE("The reminder is already overdue and cannot be scheduled."),
    ERROR_SERIALIZING_REMINDER("Error serializing reminder: {0}"),
    ERROR_DESERIALIZING_REMINDER("Error deserializing reminders"),
    UNEXPECTED_ERROR("Unexpected error");

    private final String message;

    LogMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return MessageFormat.format(message, args);
    }

}


