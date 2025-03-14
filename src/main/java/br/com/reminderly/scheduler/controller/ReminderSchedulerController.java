package br.com.reminderly.scheduler.controller;

import br.com.reminderly.scheduler.dto.ReminderRecordDto;
import br.com.reminderly.scheduler.service.GetAllReminderSchedulerService;
import br.com.reminderly.scheduler.service.ReminderSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/scheduler")
public class ReminderSchedulerController {

    private final GetAllReminderSchedulerService getAllReminderSchedulerService;
    private final ReminderSchedulerService reminderSchedulerService;

    @GetMapping()
    public  ResponseEntity<List<ReminderRecordDto>> getReminders(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllReminderSchedulerService.execute());
    }

    @PostMapping("/schedule")
    public  ResponseEntity<ReminderRecordDto> createReminder(@RequestBody ReminderRecordDto reminderRecordDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(reminderSchedulerService.execute(reminderRecordDto));
    }


}
