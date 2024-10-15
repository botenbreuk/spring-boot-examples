package nl.rdb.spring_boot_examples;

import java.awt.*;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import nl.rdb.spring_boot_examples.javafx.GUILauncher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringBootExamples {

    public static void main(String[] args) {
        if (args.length == 0 || Arrays.asList(args).contains("context-one")) {
            startContextExampleOne(args);
        } else if (Arrays.asList(args).contains("context-two")) {
            startContextExampleTwo(args);
        } else {
            log.info("Could not find context!");
        }
    }

    private static void startContextExampleOne(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SpringBootExamples.class, args);
        applicationContext.getBean(Application.class).start();
    }

    private static void startContextExampleTwo(String[] args) {
        var context = new SpringApplicationBuilder(SpringBootExamples.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> context.getBean(GUILauncher.class).run(args));
    }
}