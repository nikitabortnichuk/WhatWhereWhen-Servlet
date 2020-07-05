package com.bortni.model.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entity) {
        super("No " + entity + "s were found");
    }

}
