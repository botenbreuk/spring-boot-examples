package nl.rdb;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rdb.config.example.Example;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Application {

    private final List<Example> examples;

    public void start() {
        log.info("Start examples");
        examples.forEach(Example::run);
    }
}
