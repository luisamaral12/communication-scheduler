package br.com.luizalabs.communication.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.luizalabs.communication.domain.model.Message;
import br.com.luizalabs.communication.domain.model.Recipient;
import br.com.luizalabs.communication.domain.model.Status;
import br.com.luizalabs.communication.domain.model.Type;

@DataJpaTest
@ActiveProfiles("test")
class MessageRepositoryTest {

	@Autowired
	private MessageRepository repository;

	@BeforeEach
	void setUp() {
		repository.save(buildMessage());
	}
	
	@Test
	void whenInjectRepositoryAssertIsNotNull() {
		assertThat(repository).isNotNull();
	}
	
	@Test
	void whenSaveMessageThenFindById() {
		assertThat(repository.findById(1L)).isNotNull();
	}
	
	@Test
	void whenCancelMessageThenAssertStatus() {
		repository.cancel(3L);
		Message actual = repository.findById(3L).get();
		
		assertEquals(Status.CANCELED, actual.getStatus());
	}
	
	@Test
	void whenFindAllThenAssertCollectionSize() {
		assertThat(repository.findAll().size()).isPositive();
	}
	
	private Message buildMessage() {
		return Message
				.builder()
				.content("Mensagem de teste")
				.scheduledDate(LocalDateTime.now())
				.status(Status.CANCELED)
				.recipient(buildRecipient())
				.build();
	}

	private Recipient buildRecipient() {
		return Recipient
				.builder()
				.contact("teste@luizalabs.com.br")
				.type(Type.EMAIL)
				.build();
	}

}
