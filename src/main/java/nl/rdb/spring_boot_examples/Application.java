package nl.rdb.spring_boot_examples;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.rdb.spring_boot_examples.config.example.Example;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Application {

    private final List<Example> examples;

    public void start() {
        log.info("Start examples");
        examples.forEach(e -> {
            try {
                e.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
