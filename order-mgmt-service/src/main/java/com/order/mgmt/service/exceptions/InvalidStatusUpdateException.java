package com.order.mgmt.service.exceptions;

public class InvalidStatusUpdateException extends RuntimeException{
    public InvalidStatusUpdateException(String message){
        super(message);
    }
}
