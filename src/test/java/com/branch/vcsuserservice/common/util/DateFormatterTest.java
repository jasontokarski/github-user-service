package com.branch.vcsuserservice.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class DateFormatterTest {

    @Test
    void shouldFormatInstantToRfc1123() {
        Instant instant = Instant.parse("2011-01-25T18:44:36Z");
        
        String result = DateFormatter.formatToRfc1123(instant);
        
        assertEquals("Tue, 25 Jan 2011 18:44:36 GMT", result);
    }
    
    @Test
    void shouldReturnNullForNullInstant() {
        String result = DateFormatter.formatToRfc1123(null);
        
        assertNull(result);
    }
}
