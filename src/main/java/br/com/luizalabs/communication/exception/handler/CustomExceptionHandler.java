package br.com.luizalabs.communication.exception.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.luizalabs.communication.dto.JsonResponse;
import br.com.luizalabs.communication.exception.BadRequestException;
import br.com.luizalabs.communication.exception.NotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		JsonResponse response = JsonResponse
				.builder()
				.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();
    	
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> errors = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		
		JsonResponse response = JsonResponse
				.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.errors(errors)
				.build();
    	
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		JsonResponse response = JsonResponse
				.builder()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.message(HttpStatus.NOT_FOUND.getReasonPhrase())
				.build();
    	
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {
		JsonResponse response = JsonResponse
				.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.errors(Arrays.asList(ex.getLocalizedMessage()))
				.build();
    	
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
