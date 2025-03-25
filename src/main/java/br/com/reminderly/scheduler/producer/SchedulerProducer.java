package br.com.reminderly.scheduler.producer;

import br.com.reminderly.scheduler.dto.ReminderRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.notification.name}")
    private String routingKey;

    public void publishNotificationMessage(ReminderRecordDto reminderRecordDto) {

        rabbitTemplate.convertAndSend("", routingKey, reminderRecordDto);
    }


}

