package nl.rdb;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Application {

    public void start() {
        log.info("Hello world");
    }
}
