package br.com.luizalabs.communication.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import br.com.luizalabs.communication.domain.model.Type;
import br.com.luizalabs.communication.dto.RecipientDTO;
import br.com.luizalabs.communication.exception.BadRequestException;

public class CommunicationValidation {

	private static final String INVALID_SCHEDULED_DATE = "Scheduled date cannot be before today.";
	private static final String INVALID_PHONE_NUMBER = "Invalid phone number.";
	private static final String INVALID_EMAIL = "Invalid e-mail.";
	private static final String INVALID_COMMUNICATION_TYPE = "Invalid communication type.";
	private static final String REGEX_COMMUNICATION_TYPES = "^(EMAIL|SMS|PUSH|WHATSAPP)$";
	private static final String REGEX_EMAIL = "^(.+)@(.+)$";
	private static final String REGEX_PHONE_NUMBER = "^(\\([0-9]{2}\\)\\s*)?([9]{1})([4-9]{1})([0-9]{3})[-]?([0-9]{4})$";

	private CommunicationValidation() {
		throw new IllegalStateException("Validation class");
	}

	public static void validate(RecipientDTO dto) {
		Collection<String> errors = new ArrayList<>();

		if (!dto.getType().toUpperCase().matches(REGEX_COMMUNICATION_TYPES)) {
			errors.add(INVALID_COMMUNICATION_TYPE);
		} else {
			if (dto.getType().equalsIgnoreCase(Type.EMAIL.name())) {
				if (!dto.getContact().matches(REGEX_EMAIL)) {
					errors.add(INVALID_EMAIL);
				}
			} else {
				if (!dto.getContact().matches(REGEX_PHONE_NUMBER)) {
					errors.add(INVALID_PHONE_NUMBER);
				}
			}
		}
		
		if (LocalDateTime.now().isAfter(dto.getScheduledDate())) {
			errors.add(INVALID_SCHEDULED_DATE);
		}

		if (!errors.isEmpty()) {
			throw new BadRequestException(errors);
		}
	}

}
