package br.com.reminderly.scheduler.exception;

public class ReminderNotFoundException extends SchedulerServiceException {
    public ReminderNotFoundException(String message) {
        super(message);
    }
}
