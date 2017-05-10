package org.preonlang.transform;

public class TransformError {
    private final String message;

    public TransformError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
