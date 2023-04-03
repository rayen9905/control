package com.pfe.entities;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="Lecteur")
public class Lecteur {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long IdLecteur;
	@Column(name = "Ip_Adresse", length = 500)
private String IpAdresse;

@ManyToOne
private Porte prt;	

	
}
