package br.com.luizalabs.communication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.luizalabs.communication.domain.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
	
	@Modifying
	@Query("UPDATE Message m SET m.status = 'CANCELED' WHERE m.id = :id")
	public void cancel(Long id);
}
