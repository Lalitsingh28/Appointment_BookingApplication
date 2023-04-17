package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;




@ControllerAdvice
public class GlobalExceptionHandler {

	
		@ExceptionHandler(UserException.class)
		public ResponseEntity<MyErrorDetails> UserExceptionHandler(UserException e, WebRequest wr) {
			
	        MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(),wr.getDescription(false));
			return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		}
	
	
		@ExceptionHandler(ApiException.class)
		public ResponseEntity<MyErrorDetails> ApiExceptionHandler(ApiException e, WebRequest wr) {
			
            MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(),wr.getDescription(false));
			return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		}
		

		@ExceptionHandler(Exception.class)
		public ResponseEntity<MyErrorDetails> logicalHandler(Exception e, WebRequest wr){
			
			MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), wr.getDescription(false));
			return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
				
	    }

	
	   @ExceptionHandler(NoHandlerFoundException.class)
	   public ResponseEntity<MyErrorDetails> wrongApiHandler(NoHandlerFoundException e, WebRequest wr){
		
		  MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), wr.getDescription(false));
		  return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
		
	   }
	
	
	   @ExceptionHandler(MethodArgumentNotValidException.class)
	   public ResponseEntity<MyErrorDetails> methodHandler(MethodArgumentNotValidException ie,WebRequest req) {
			
			MyErrorDetails err=new MyErrorDetails(LocalDateTime.now(), ie.getMessage(), req.getDescription(false));
			return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
			
	    }
	   	
	
}
