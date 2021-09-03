package br.com.luizalabs.communication.service.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.luizalabs.communication.domain.model.Message;
import br.com.luizalabs.communication.domain.model.Status;
import br.com.luizalabs.communication.domain.repository.MessageRepository;
import br.com.luizalabs.communication.dto.JsonResponse;
import br.com.luizalabs.communication.dto.MessageDTO;
import br.com.luizalabs.communication.dto.RecipientDTO;
import br.com.luizalabs.communication.exception.BadRequestException;
import br.com.luizalabs.communication.exception.NotFoundException;
import br.com.luizalabs.communication.service.CommunicationService;
import br.com.luizalabs.communication.validation.CommunicationValidation;

@Service
public class CommunicationServiceImpl implements CommunicationService {

	@Autowired
	private MessageRepository messageRepository;

	private ModelMapper mapper = initModelMapper();

	@Override
	public JsonResponse findById(Long id) {
		Message entity = messageRepository.findById(id).orElseThrow(NotFoundException::new);
		
		JsonResponse response = new JsonResponse(HttpStatus.OK);
		response.setData(mapper.map(entity, MessageDTO.class));
		return response;
	}

	@Override
	public JsonResponse findAll() {
		List<Message> sourceList = messageRepository.findAll(PageRequest.of(0, 20)).toList();
		if (sourceList.isEmpty()) {
			throw new NotFoundException();
		}
		List<MessageDTO> destinationList = new ArrayList<>();
		sourceList.forEach(entity -> destinationList.add(mapper.map(entity, MessageDTO.class)));
		
		JsonResponse response = new JsonResponse(HttpStatus.OK);
		response.setData(destinationList);
		return response;	
	}

	@Override
	public JsonResponse save(RecipientDTO request) {
		CommunicationValidation.validate(request);
		Message entity = mapper.map(request, Message.class);
		messageRepository.save(entity);
		
		JsonResponse response = new JsonResponse(HttpStatus.CREATED);
		response.setData(String.format("Schedule created: %d", entity.getId()));
		return response;	
	}

	@Transactional
	@Override
	public void cancel(Long id) {
		Message entity = messageRepository.findById(id).orElseThrow(NotFoundException::new);
		if (entity.getStatus() == Status.CANCELED) {
			throw new BadRequestException(Arrays.asList("Schedule already canceled"));
		}
		messageRepository.cancel(id);
	}
	
	private ModelMapper initModelMapper() {
		mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
		
		return mapper;
	}
}
