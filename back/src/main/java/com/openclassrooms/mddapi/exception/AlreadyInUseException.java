package com.openclassrooms.mddapi.exception;

public class AlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AlreadyInUseException(String msg) {
        super(msg);
    }
    
}
