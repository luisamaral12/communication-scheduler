package br.com.luizalabs.communication.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RecipientDTO {

	@NotNull(message = "'scheduledDate' cannot be null.")
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@ApiModelProperty(value = "Date when the message will be sent. Format: dd-MM-yyyy HH:mm:ss", example = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime scheduledDate;
	
	@NotEmpty(message = "'contact' cannot be empty.")
	@ApiModelProperty("Recipient's contact. Could be either an e-mail (e.g. example@domain.com.br) or a phone number [e.g. (99) 99999-9999]")
	private String contact;
	
	@NotEmpty(message = "'type' cannot be empty.")
	@ApiModelProperty("Communication type. Possible values: EMAIL, SMS, PUSH or WHATSAPP")
	private String type;
	
	@NotEmpty(message = "'content' cannot be empty.")
	@ApiModelProperty("The message to be sent")
	private String content;
}
