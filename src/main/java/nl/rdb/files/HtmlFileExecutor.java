package nl.rdb.files;

import java.awt.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HtmlFileExecutor {

    public void start() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(getClass().getClassLoader().getResource("index.html").toURI());
                }
            }
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
    }
}
