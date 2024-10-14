package nl.rdb.spring_boot_examples.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScheduledTasks {

    private final SimpleDateFormat dateFormat;

    public ScheduledTasks() {
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
    }

    @Scheduled(cron = "#{@scheduleConfigProperties.cron}")
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
}