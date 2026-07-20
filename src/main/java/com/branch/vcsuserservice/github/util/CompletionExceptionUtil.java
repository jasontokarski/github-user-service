package com.branch.vcsuserservice.github.util;

import java.util.concurrent.CompletionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CompletionExceptionUtil {

    private CompletionExceptionUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Unwraps a CompletionException and returns the underlying cause.
     * 
     * @param ex the CompletionException to unwrap
     * @return the underlying RuntimeException
     * @throws Error if the cause is an Error (e.g., OutOfMemoryError)
     * @throws RuntimeException if the cause is a checked exception (wrapped in RuntimeException)
     */
    public static RuntimeException unwrap(CompletionException ex) {
        Throwable cause = ex.getCause();
        
        if (cause instanceof RuntimeException runtimeException) {
            return runtimeException;
        }
        
        if (cause instanceof Error error) {
            throw error;
        }
        
        log.error("Unexpected checked exception wrapped in CompletionException: {}", cause.getMessage(), cause);
        return new RuntimeException("Unexpected error occurred during async operation", cause);
    }
}
