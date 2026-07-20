package com.branch.githubuserservice.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.concurrent.CompletionException;

import org.junit.jupiter.api.Test;

class CompletionExceptionUtilTest {

    @Test
    void shouldUnwrapRuntimeException() {
        RuntimeException originalException = new IllegalArgumentException("Test exception");
        CompletionException completionException = new CompletionException(originalException);

        RuntimeException result = CompletionExceptionUtil.unwrap(completionException);

        assertSame(originalException, result);
        assertEquals("Test exception", result.getMessage());
    }
}
