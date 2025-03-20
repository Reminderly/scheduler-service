package br.com.reminderly.scheduler.service;

import br.com.reminderly.scheduler.dto.ReminderRecordDto;
import br.com.reminderly.scheduler.enums.LogMessage;
import br.com.reminderly.scheduler.exception.ReminderOverdueException;
import br.com.reminderly.scheduler.exception.ReminderSerializationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

import static br.com.reminderly.scheduler.enums.RedisKeys.REMINDER_HISTORY_KEY;
import static br.com.reminderly.scheduler.enums.RedisKeys.SCHEDULED_REMINDER_KEY;

@Service
@RequiredArgsConstructor
public class ReminderSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(ReminderSchedulerService.class);

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    private static final String SERVICE_ACTION_LOG = "Schedule reminder";


    public ReminderRecordDto execute(ReminderRecordDto reminderRecordDto) {

        try {

            logger.info(LogMessage.SERVICE_PROCESS_START.getMessage(SERVICE_ACTION_LOG));

            String scheduledReminderKey = SCHEDULED_REMINDER_KEY.getMessage() + reminderRecordDto.id();
            String reminderHistoryKey = REMINDER_HISTORY_KEY.getMessage() + reminderRecordDto.id();
            String reminderJson;

            long ttlSeconds = Duration.between(Instant.now(), reminderRecordDto.reminderTime()).getSeconds();

            if (ttlSeconds > 0) {

                try {
                    reminderJson = objectMapper.writeValueAsString(reminderRecordDto);
                } catch (JsonProcessingException ex) {
                    throw new ReminderSerializationException(LogMessage.ERROR_SERIALIZING_REMINDER.getMessage(reminderRecordDto.id()));
                }

                redisTemplate.opsForValue().set(scheduledReminderKey, reminderJson, Duration.ofSeconds(ttlSeconds));
                redisTemplate.opsForValue().set(reminderHistoryKey, reminderJson);

            } else {
                throw new ReminderOverdueException(LogMessage.ERROR_REMINDER_OVERDUE.getMessage());
            }

        } catch (Exception e) {
            logger.error(LogMessage.ERROR_PROCESSING_SERVICE_ACTION.getMessage(SERVICE_ACTION_LOG));
            throw e;
        }

        logger.info(LogMessage.SERVICE_PROCESS_FINISH.getMessage(SERVICE_ACTION_LOG));

        return reminderRecordDto;

    }

}
