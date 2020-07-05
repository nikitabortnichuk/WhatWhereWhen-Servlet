package com.bortni.model.exception;

public class MySqlException extends RuntimeException {

    public MySqlException(String message, Throwable err) {
        super(message, err);
    }
}
