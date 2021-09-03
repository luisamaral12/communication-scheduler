package br.com.luizalabs.communication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.luizalabs.communication.dto.JsonResponse;
import br.com.luizalabs.communication.dto.RecipientDTO;
import br.com.luizalabs.communication.service.CommunicationService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "api/messages")
@ApiModel(value = "Provides management of message scheduling.")
public class CommunicationController {

	@Autowired
	private CommunicationService service;

	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", response = JsonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = JsonResponse.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = JsonResponse.class)
	})
	@ApiOperation("Find a schedule.")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonResponse> find(
			@ApiParam(value = "The schedule id.", example = "0") @PathVariable(name = "id") Long id) {
		return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 200, message = "Success", response = JsonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = JsonResponse.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = JsonResponse.class)
	})
	@ApiOperation("Find all schedules.")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonResponse> findAll() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}

	@ApiResponses({
		@ApiResponse(code = 201, message = "Created", response = JsonResponse.class),
		@ApiResponse(code = 400, message = "Bad Request", response = JsonResponse.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = JsonResponse.class)
	})
	@ApiOperation("Create a new schedule.")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonResponse> create(@Valid @RequestBody RecipientDTO request) {
		
		return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
	}

	@ApiResponses({
		@ApiResponse(code = 204, message = "No Content"),
		@ApiResponse(code = 400, message = "Bad Request", response = JsonResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = JsonResponse.class),
		@ApiResponse(code = 500, message = "Internal Server Error", response = JsonResponse.class)
	})
	@ApiOperation("Cancel a schedule.")
	@PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancel(
			@ApiParam(value = "The schedule id.", example = "0") @PathVariable(name = "id") Long id) {
		service.cancel(id);
		return ResponseEntity.noContent().build();
	}
}
