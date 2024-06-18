package com.bookstore.common.exception;


//Custom Excep
// Handle at Glolbal Excep or Service -> Controller
public class ResourceNotFoundExcep extends RuntimeException {
    public ResourceNotFoundExcep(String message){
        super(message);
    }
    public ResourceNotFoundExcep(String message,Throwable cause){
        super(message,cause);
    }
}
