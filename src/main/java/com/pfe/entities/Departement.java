package com.pfe.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name ="Departement")
public class Departement {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long IdDep;
	@Column(name = "Nom_Departement", length = 100)
private String NomDep;
	
@OneToMany(mappedBy="dept")
private List<Controlleur>cntrls;

@OneToMany(mappedBy="depar")
private List<User>usr	;

	
	
}
