package nl.rdb.config;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.schedule")
public class ScheduleConfigProperties {

    private String cron = "0/10 * * * * *";
}
