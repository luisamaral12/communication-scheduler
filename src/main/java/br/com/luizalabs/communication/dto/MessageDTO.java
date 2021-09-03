package br.com.luizalabs.communication.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageDTO {
	
	@ApiModelProperty("Recipient's contact. Could be either an e-mail (e.g. example@domain.com.br) or a phone number [e.g. (99) 99999-9999]")
	private String contact;
	
	@ApiModelProperty("The message to be sent")
	private String content;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@ApiModelProperty("Date when the message will be / was sent. Format: dd-MM-yyyy HH:mm:ss")
	private LocalDateTime scheduledDate;
	
	@ApiModelProperty("Communication type. Possible values: EMAIL, SMS, PUSH or WHATSAPP")	
	private String type;
	
	@ApiModelProperty("Schedule status. Possible values: PENDING, SENT, CANCELED")	
	private String status;
	
}
