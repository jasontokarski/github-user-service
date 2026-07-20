package com.branch.vcsuserservice.common.util;

import java.util.concurrent.CompletionException;

public class CompletionExceptionUtil {
    
    public static RuntimeException unwrap(CompletionException ex) {
        Throwable cause = ex.getCause();
        return (cause instanceof RuntimeException) 
            ? (RuntimeException) cause 
            : ex;
    }
    
    private CompletionExceptionUtil() {
    }
}
