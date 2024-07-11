package com.hsgumussoy.school_management_project.exceptions;

import com.hsgumussoy.school_management_project.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundExceptions.class)
    private ResponseEntity<Object> handleException(RecordNotFoundExceptions e){
        System.out.println(e.getMessage());
        BaseResponse response = new BaseResponse();
        response.setCode(e.getCode());
        response.setMessage(e.getMessage());

        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
        return  entity;
    }
    @ExceptionHandler
    private ResponseEntity<Object> handleException(NullPointerException e){
        System.out.println(e.getMessage());
        BaseResponse response =  new BaseResponse();
        response.setCode(4000);
        response.setMessage("Null Pointer Exception");
        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.OK);
        return entity;
    }

}
