package br.com.luizalabs.communication.domain.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "TB_RECIPIENT")
public class Recipient implements Serializable {

	private static final long serialVersionUID = -9189580562541858579L;

	@Id
	@Column(name = "CD_RECIPIENT")
	@SequenceGenerator(name = "CD_SEQ_RECIPIENT", allocationSize = 1)
	@GeneratedValue(generator = "CD_SEQ_RECIPIENT", strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DS_CONTACT", unique = true)
	private String contact;
	
	@Column(name = "CD_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private Type type;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipient")
	private List<Message> messages;
}
