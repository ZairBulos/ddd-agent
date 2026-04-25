package com.zair.ddd_agent.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public void writeToFile(final String content, final String outputDir) {
        var filename = LocalDateTime.now().format(FILE_NAME_FORMATTER) + ".txt";
        var dir = Path.of(outputDir);
        var filePath = dir.resolve(filename);

        try {
            Files.createDirectories(dir);
            Files.writeString(filePath, content);
        } catch (IOException e) {
            log.error("Failed to write post to {}: {}", filePath, e.getMessage());
        }
    }

}
