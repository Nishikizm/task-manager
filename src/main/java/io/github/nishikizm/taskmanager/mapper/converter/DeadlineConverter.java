package io.github.nishikizm.taskmanager.mapper.converter;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import io.github.nishikizm.taskmanager.web.form.DeadlineParts;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeadlineConverter {

    private final ZoneId zoneId;

    public Instant toInstant(DeadlineParts parts) {
        String[] timeArray = parts.time().split(":");
        int hour = Integer.parseInt(timeArray[0].strip());
        int minute = Integer.parseInt(timeArray[1].strip());

        try {
            return ZonedDateTime.of(
                parts.year(), 
                parts.month(), 
                parts.day(), 
                hour, minute, 0, 0, 
                zoneId
            ).toInstant();
        } catch(DateTimeException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public DeadlineParts toParts(Instant instant) {
        ZonedDateTime zdt = instant.atZone(zoneId);
        return new DeadlineParts(
            zdt.getYear(), 
            zdt.getMonthValue(), 
            zdt.getDayOfMonth(), 
            String.format("%02d:%02d", zdt.getHour(), zdt.getMinute())
        );
    }

}
