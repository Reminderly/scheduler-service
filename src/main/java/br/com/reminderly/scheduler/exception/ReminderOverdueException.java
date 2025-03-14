package br.com.reminderly.scheduler.exception;

public class ReminderOverdueException extends SchedulerServiceException {
    public ReminderOverdueException(String message) {
        super(message);
    }
}
