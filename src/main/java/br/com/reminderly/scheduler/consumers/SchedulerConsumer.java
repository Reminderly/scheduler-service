package br.com.reminderly.scheduler.consumers;

import br.com.reminderly.scheduler.dto.ReminderRecordDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class SchedulerConsumer {

    @RabbitListener(queues = "${broker.queue.scheduler.name}")
    public void listenSchedulerQueue(@Payload ReminderRecordDto reminderRecordDto){
        System.out.println(reminderRecordDto.message());
    }
}
