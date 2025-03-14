package br.com.reminderly.scheduler.service;

import br.com.reminderly.scheduler.dto.ReminderRecordDto;
import br.com.reminderly.scheduler.enums.LogMessage;
import br.com.reminderly.scheduler.exception.ReminderNotFoundException;
import br.com.reminderly.scheduler.exception.ReminderSerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GetAllReminderSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(GetAllReminderSchedulerService.class);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String SERVICE_ACTION_LOG = "Get Reminders";
    private static final String REMINDER_PATTERN = "reminder:*";

    public List<ReminderRecordDto> execute() {

        try {

            logger.info(LogMessage.SERVICE_PROCESS_START.getMessage(SERVICE_ACTION_LOG));

            Set<String> keys = redisTemplate.keys(REMINDER_PATTERN);

            if (keys.isEmpty()) {
                throw new ReminderNotFoundException(LogMessage.REMINDERS_NOT_FOUND.getMessage());
            }

            logger.info(LogMessage.SERVICE_PROCESS_FINISH.getMessage(SERVICE_ACTION_LOG));

            List<ReminderRecordDto> reminders = keys.stream()
                    .map(redisTemplate.opsForValue()::get)
                    .map(json -> {
                        try {
                            return objectMapper.readValue(json, ReminderRecordDto.class);
                        } catch (JsonProcessingException e) {
                            throw new ReminderSerializationException(LogMessage.ERROR_DESERIALIZING_REMINDER.getMessage());
                        }
                    })
                    .toList();

            logger.info(LogMessage.SERVICE_PROCESS_FINISH.getMessage(SERVICE_ACTION_LOG));

            return reminders;

        } catch (Exception e) {
            logger.error(LogMessage.ERROR_PROCESSING_SERVICE_ACTION.getMessage(SERVICE_ACTION_LOG));
            throw e;
        }

    }

}
