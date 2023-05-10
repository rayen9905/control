package com.pfe.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name ="Lecteur")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Lecteur {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long IdLecteur;
	@Column(name = "Ip_Adresse", length = 500)
private String IpAdresse;
	@Column(name = "Etat_Lecteur", length = 500)
	private String EtatLecteur;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "id_porte")
@JsonIgnoreProperties("lecteur")
private Porte prt;	

	
}
