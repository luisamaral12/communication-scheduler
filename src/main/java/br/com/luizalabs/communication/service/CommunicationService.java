package br.com.luizalabs.communication.service;

import br.com.luizalabs.communication.dto.JsonResponse;
import br.com.luizalabs.communication.dto.RecipientDTO;

public interface CommunicationService {

	public JsonResponse findById(Long id);
	
	public JsonResponse findAll();

	public JsonResponse save(RecipientDTO request);

	public void cancel(Long id);
}
