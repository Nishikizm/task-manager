package io.github.nishikizm.taskmanager.mapper.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

@Component
public class DeadlineConverter {

    private final ZoneId zoneId;
    
    public DeadlineConverter(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public Instant toInstant(int year, int month, int day, String time) {
        String[] timeArray = time.split(":");
        int hour = Integer.parseInt(timeArray[0].strip());
        int minute = Integer.parseInt(timeArray[1].strip());

        return ZonedDateTime.of(year, month, day, hour, minute, 0, 0, zoneId)
                .toInstant();
    }

}
