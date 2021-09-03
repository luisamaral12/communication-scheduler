package br.com.luizalabs.communication.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.luizalabs.communication.domain.model.Recipient;
import br.com.luizalabs.communication.domain.model.Type;

@DataJpaTest
@ActiveProfiles("test")
class RecipientRepositoryTest {

	@Autowired
	private RecipientRepository repository;

	@Test
	void whenInjectRepositoryAssertIsNotNull() {
		assertThat(repository).isNotNull();
	}
	
	@Test
	void whenSaveMessageThenFindById() {
		repository.save(buildRecipient());
		
		assertThat(repository.findById(1L)).isNotNull();
	}
	
	private Recipient buildRecipient() {
		return Recipient
				.builder()
				.contact("teste@luizalabs.com.br")
				.type(Type.EMAIL)
				.build();
	}
}
