package com.omartech.engine.client;

/**
 * Created by feng on 3/17/14.
 */
public class ClientException extends Exception {
    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}
