package br.com.reminderly.scheduler.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static br.com.reminderly.scheduler.enums.RedisKeys.REMINDER_HISTORY_KEY;
import static br.com.reminderly.scheduler.enums.RedisKeys.SCHEDULED_REMINDER_KEY;

@Component
public class ReminderExpirationListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ReminderExpirationListener.class);

    private final StringRedisTemplate redisTemplate;

    public ReminderExpirationListener(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        logger.info("Expired reminder: {}", expiredKey);

        String reminderHistoryKey = REMINDER_HISTORY_KEY.getMessage() + expiredKey.replace(SCHEDULED_REMINDER_KEY.getMessage(), "");
        String reminderJson = redisTemplate.opsForValue().get(reminderHistoryKey);
        redisTemplate.delete(reminderHistoryKey);


        logger.info(reminderJson);

    }
}
