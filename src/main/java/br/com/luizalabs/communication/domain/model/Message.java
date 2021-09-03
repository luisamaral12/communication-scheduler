package br.com.luizalabs.communication.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_MESSAGE")
public class Message implements Serializable {

	private static final long serialVersionUID = 4131840513631124981L;

	@Id
	@Column(name = "CD_MESSAGE")
	@SequenceGenerator(name = "CD_SEQ_MESSAGE", allocationSize = 1)
	@GeneratedValue(generator = "CD_SEQ_MESSAGE", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DS_CONTENT", nullable = false, length = 2000)
	private String content;
	
	@Column(name = "DT_SCHEDULED", nullable = false)
	private LocalDateTime scheduledDate;
	
	@Column(name = "CD_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "CD_RECIPIENT")
	private Recipient recipient;
	
	@PrePersist
	private void setStatusPending() {
		if (this.status == null) {
			this.status = Status.PENDING;
		}
	}
}
