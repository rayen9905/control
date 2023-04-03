package com.pfe.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Porte")
public class Porte {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//Auto increment
	private Long IdPorte;
	
	@Column(name = "Nom_Porte", length = 50)
	private String NomPorte;
	
	@OneToMany(mappedBy="prt")
	private List<Lecteur>lecteur;
	
	@ManyToOne
	private Controlleur cntrl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Type", length = 50, nullable = false)
	private TypePorte Type;

	
}
