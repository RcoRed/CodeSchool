package org.generation.italy.codeSchool.model.data.exceptions;

public class DataException extends Exception{

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataException(String message) {
        super(message);
    }
}
