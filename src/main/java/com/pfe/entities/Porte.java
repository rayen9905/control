package com.pfe.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
	@Column(name = "Num_Porte")
	private int NumPorte;
	@Enumerated(EnumType.STRING)
	@Column(name = "Type", length = 50, nullable = false)
	private TypePorte Type;
	
	@OneToMany(mappedBy="prt",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnoreProperties("prt")
	private List<Lecteur> lecteur= new ArrayList<>();

	@ManyToOne
	@JsonIgnoreProperties("porte")
	private Controlleur cntrl;

	@ManyToMany
	@JsonIgnoreProperties("prt")
	private List<User> usr;

	@OneToOne
	@JsonIgnoreProperties("prt")
	private WaveShare wsh;
	
}
