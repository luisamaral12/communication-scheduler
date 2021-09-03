package br.com.luizalabs.communication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.luizalabs.communication.domain.model.Recipient;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

}
