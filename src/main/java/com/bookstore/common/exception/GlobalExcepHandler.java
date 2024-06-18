package com.bookstore.common.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExcepHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class,  // vlalid data
//            ConstraintViolationException.class,  //  url, error param
            MissingServletRequestParameterException.class // miss field, data
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST) // return code
    public ErrorResponse handleValid(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        System.out.println("====> Handle Valid");
        errorResponse.setTimeStamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
//        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());


        String message = e.getMessage();
        if (e instanceof MethodArgumentNotValidException) {
            int start = message.lastIndexOf("[") + 1;
            int end = message.lastIndexOf("]") - 1;

            message = message.substring(start, end);
            errorResponse.setError("Invalid Payload");
            errorResponse.setMessage(message);
        } else if (e instanceof MissingServletRequestParameterException) {
            errorResponse.setError("Invalid Parameter");
            errorResponse.setMessage(message);
        } else {
            errorResponse.setError("Unknown Error");
            errorResponse.setMessage(message);
        }
        return errorResponse;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {

        List<String> details = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .collect(Collectors.toList());


        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimeStamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setError("Invalid Parameter");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setDetails(details);

        return errorResponse;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})  // error data type
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // return code
    public ErrorResponse handleInternalServer(Exception e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        System.out.println("====> Handle INTERNAL SERVER");
        errorResponse.setTimeStamp(new Date());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        if (e instanceof MethodArgumentTypeMismatchException) {
            errorResponse.setMessage("Failed to convert value of type");
        }


        return errorResponse;
    }

}
