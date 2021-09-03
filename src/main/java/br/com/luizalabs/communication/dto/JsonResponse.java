package br.com.luizalabs.communication.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {
	
    private Integer statusCode;
	
	private String message;
	
	@JsonInclude(Include.NON_NULL)
    private List<String> errors;
	
	@JsonInclude(Include.NON_NULL)
	private Object data;
	
	public JsonResponse (HttpStatus status) {
		 this.statusCode = status.value();
		 this.message = status.getReasonPhrase();
	}
}
