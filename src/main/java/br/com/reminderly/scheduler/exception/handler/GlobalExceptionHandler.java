package br.com.reminderly.scheduler.exception.handler;

import br.com.reminderly.scheduler.enums.LogMessage;
import br.com.reminderly.scheduler.exception.ReminderSerializationException;
import br.com.reminderly.scheduler.exception.SchedulerServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        logger.error(LogMessage.UNEXPECTED_ERROR.getMessage() , ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("errorMessage", "An unexpected error occurred"));
    }

    @ExceptionHandler(value = SchedulerServiceException.class)
    public ResponseEntity<Map<String, String>> handleSchedulerServiceExceptions(SchedulerServiceException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("errorMessage", ex.getMessage()));
    }

    @ExceptionHandler(value = ReminderSerializationException.class)
    public ResponseEntity<Map<String, String>> handleReminderSerializationExceptions(SchedulerServiceException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("errorMessage", ex.getMessage()));
    }

}
