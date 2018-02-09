package org.lds.ldssa.model.navigation;

public class InvalidExtraException extends IllegalStateException {
    public InvalidExtraException(String key, Object value) {
        super("Invalid Extras for key: [" + key + "] value: [" + value + "]");
    }
}