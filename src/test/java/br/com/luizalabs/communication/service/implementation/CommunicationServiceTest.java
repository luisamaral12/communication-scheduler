package br.com.luizalabs.communication.service.implementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import br.com.luizalabs.communication.domain.model.Type;
import br.com.luizalabs.communication.dto.JsonResponse;
import br.com.luizalabs.communication.dto.RecipientDTO;
import br.com.luizalabs.communication.service.CommunicationService;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class CommunicationServiceTest {
 
	@Autowired
	private CommunicationService service;
	
	@Test
	@Order(0)
	void whenInjectServiceAssertIsNotNull() {
		assertThat(service).isNotNull();
	}
	
	@Test
	@Order(1)
	void whenSaveThenReturnSuccessResponse() throws Exception {
		assertThat(
				service.save(buildRequest()))
					.usingRecursiveComparison()
					.isEqualTo(buildSaveSuccessResponse());
	}
	
	@Test
	@Order(2)
	void whenFindTheReturnSuccessResponse() throws Exception {
		assertThat(service.findById(1L)).isNotNull();
	}
	
	@Test
	@Order(3)
	void whenFindAllTheReturnSuccessResponse() throws Exception {
		assertThat(service.findAll()).isNotNull();
	}
	
	private JsonResponse buildSaveSuccessResponse() {
		return JsonResponse
				.builder()
				.statusCode(HttpStatus.CREATED.value())
				.message(HttpStatus.CREATED.getReasonPhrase())
				.data("Schedule created: 1")
				.build();
	}

	private RecipientDTO buildRequest() {
		RecipientDTO request = new RecipientDTO();
		
		request.setContact("teste@luizalabs.com.br");
		request.setContent("Testando agendamento válido.");
		request.setScheduledDate(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)).plusDays(7L));
		request.setType(Type.EMAIL.name());
		
		return request;
	}

}
