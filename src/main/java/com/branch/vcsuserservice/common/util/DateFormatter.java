package com.branch.vcsuserservice.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    
    private static final DateTimeFormatter RFC_1123_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME;
    private static final ZoneId GMT = ZoneId.of("GMT");
    
    public static String formatToRfc1123(Instant instant) {
        return instant != null 
            ? RFC_1123_FORMATTER.format(instant.atZone(GMT))
            : null;
    }
    
    private DateFormatter() {
    }
}
