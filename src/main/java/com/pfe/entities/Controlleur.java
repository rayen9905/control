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
@Table(name ="Controlleur")
public class Controlleur {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long IdCont;
	
@Column(name = "Nom_Controlleur", length = 100)
private String NomCont;

@Enumerated(EnumType.STRING)
@Column(name = "Status", length = 50, nullable = false)
private StatuCntrl Status;

@OneToMany(mappedBy="cntrl")
private List<Porte>porte;

@ManyToOne
private Departement dept;


}
