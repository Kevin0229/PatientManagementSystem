package com.Patient.Exception;

public class UserInvalidException extends RuntimeException{
    public UserInvalidException(String message){
        super(message);
    }
}
