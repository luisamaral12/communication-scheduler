package br.com.luizalabs.communication.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.luizalabs.communication.domain.model.Message;
import br.com.luizalabs.communication.domain.model.Status;
import br.com.luizalabs.communication.domain.model.Type;
import br.com.luizalabs.communication.dto.JsonResponse;
import br.com.luizalabs.communication.dto.MessageDTO;
import br.com.luizalabs.communication.dto.RecipientDTO;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class CommunicationControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	private ModelMapper modelMapper;
	
	private static final String ENDPOINT = "/api/messages";
	
	@Test
	@Order(2)
	void whenCreateThenReturn201() throws Exception {
		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(buildValidRequest())))
				.andExpect(status().isCreated())
				.andExpect(content().json(objectMapper.writeValueAsString(buildCreatedResponse())));
	}

	@Test
	@Order(3)
	void whenCreateThenReturn400() throws Exception {
		mockMvc.perform(post(ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(objectMapper.writeValueAsString(buildInvalidRequest())))
				.andExpect(content().json(objectMapper.writeValueAsString(buildBadRequestResponse())));
	}
	
	@Test
	@Order(4)
	void whenFindThenReturn200() throws Exception {
		mockMvc.perform(get(ENDPOINT + "/{id}", 1L))
				.andExpect(content().json(objectMapper.writeValueAsString(buildSuccesfulResponse())));
	}

	@Test
	@Order(5)
	void whenFindThenReturn404() throws Exception {
		mockMvc.perform(get(ENDPOINT + "/{id}", 55L))
				.andExpect(content().json(objectMapper.writeValueAsString(buildNotFoundResponse())));
	}
	
	@Test
	@Order(6)
	void whenFindAllThenReturn200() throws Exception {
		mockMvc.perform(get(ENDPOINT)).andExpect(
				jsonPath("$.data", objectMapper.writeValueAsString(buildSuccesfulArrayResponse())).isArray());
	}

	@Test
	@Order(1)
	void whenFindAllThenReturn404() throws Exception {
		mockMvc.perform(get(ENDPOINT))
				.andExpect(content().json(objectMapper.writeValueAsString(buildNotFoundResponse())));
	}
	
	@Test
	@Order(7)
	void whenCancelThenReturn204() throws Exception {
		mockMvc.perform(put(ENDPOINT + "/{id}", 1L))
				.andExpect(status().isNoContent());
	}
	
	@Test
	@Order(8)
	void whenCancelThenReturn400() throws Exception {
		mockMvc.perform(put(ENDPOINT + "/{id}", 1L))
				.andExpect(content().json(objectMapper.writeValueAsString(buildBadRequestResponse())));
	}
	
	@Test
	@Order(9)
	void whenCancelThenReturn404() throws Exception {
		mockMvc.perform(put(ENDPOINT + "/{id}", 5L))
				.andExpect(content().json(objectMapper.writeValueAsString(buildNotFoundResponse())));
	}
	
	private JsonResponse buildSuccesfulResponse() {
		return JsonResponse
				.builder()
				.statusCode(HttpStatus.OK.value())
				.message(HttpStatus.OK.getReasonPhrase())
				.data(buildMessageDTO())
				.build();
	}
	
	private JsonResponse buildSuccesfulArrayResponse() {
		List<Object> responseList = buildArrayMessageDTO();
		
		return JsonResponse
				.builder()
				.statusCode(HttpStatus.OK.value())
				.message(HttpStatus.OK.getReasonPhrase())
				.data(responseList)
				.build();
	}

	private JsonResponse buildCreatedResponse() {
		return JsonResponse
				.builder()
				.statusCode(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.data("Schedule created: 1")
				.build();
	}
	
	private JsonResponse buildBadRequestResponse() {
		return JsonResponse
				.builder()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.message(HttpStatus.BAD_REQUEST.getReasonPhrase())
				.errors(null)
				.build();
	}
	
	private JsonResponse buildNotFoundResponse() {
		return JsonResponse
				.builder()
				.statusCode(HttpStatus.NOT_FOUND.value())
				.message(HttpStatus.NOT_FOUND.getReasonPhrase())
				.build();
	}
	
	private RecipientDTO buildValidRequest() {
		RecipientDTO request = new RecipientDTO();
		
		request.setContact("(99) 99999-9999");
		request.setContent("Testando agendamento válido.");
		request.setScheduledDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)).plusDays(7L));
		request.setType(Type.WHATSAPP.name());
		
		return request;
	}
	
	private RecipientDTO buildInvalidRequest() {
		RecipientDTO request = new RecipientDTO();
		
		request.setContact("(99) 99999-9999");
		request.setContent("Testando agendamento inválido.");
		request.setScheduledDate(LocalDateTime.now().minusDays(7L));
		request.setType(Type.EMAIL.name());
		
		return request;
	}
	
	private List<Object> buildArrayMessageDTO() {
		List<Object> responseList = new ArrayList<>();
		responseList.add(buildMessageDTO());
		return responseList;
	}
	
	private MessageDTO buildMessageDTO() {
		modelMapper = configureModelMapper();
		MessageDTO response = new MessageDTO();
		
		response = modelMapper.map(buildMessage(), MessageDTO.class);
		response.setStatus(Status.PENDING.name());
		
		return response;
	}
	
	private Message buildMessage() {
		modelMapper = configureModelMapper();
		
		return modelMapper.map(buildValidRequest(), Message.class);
	}
	
	private ModelMapper configureModelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		return mapper;
	}
}
