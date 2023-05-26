package com.pfe.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Setter
@Entity
@Table(name="Porte")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Porte {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//Auto increment
	private Long IdPorte;
	
	@Column(name = "Nom_Porte", length = 50)
	private String NomPorte;
	@Enumerated(EnumType.STRING)
	@Column(name = "type", length = 50)
	private Type_Prt type;
	@Column(name = "Num_Porte")
	private int NumPorte;
	@OneToMany(mappedBy="prt",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("prt")
	private List<Lecteur> lecteur= new ArrayList<>();

	@ManyToOne
	@JsonIgnoreProperties("porte")
	private Controlleur cntrl;

	@OneToOne
	@JsonIgnoreProperties("prt")
	private WaveShare wsh;

}
