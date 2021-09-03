package br.com.luizalabs.communication.exception;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(Collection<String> details) {
		super(Arrays.asList(details).toString());
	}
}
