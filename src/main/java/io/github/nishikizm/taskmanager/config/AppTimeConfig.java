package io.github.nishikizm.taskmanager.config;

import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppTimeConfig {

    private final String timeZone;

    public AppTimeConfig(@Value("${app.time-zone}") String timeZone) {
        this.timeZone = timeZone;
    }
    
    @Bean
    public ZoneId getZone() {
        return ZoneId.of(timeZone);
    }

}
