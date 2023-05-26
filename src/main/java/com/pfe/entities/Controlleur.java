package com.pfe.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString
@Table(name ="Controlleur")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Controlleur {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
private Long IdCont;
	
@Column(name = "Nom_Controlleur", length = 100)
private String NomCont;
	@Column(name = "Serial_Number")
	private String Serial_Number;
	@Column(name = "Nbr_Porte")
	private int NbrPorte;

@Enumerated(EnumType.STRING)
@Column(name = "Status", length = 50, nullable = false)
private StatuCntrl Status;
	@Column(name = "Ip_Adresse", length = 500)
	private String IpAdresse;
	@OneToMany(mappedBy="cntrl")
	@JsonIgnoreProperties("cntrl")
	private List<Porte>porte;
	@Column(name = "Date_Status")
	private LocalDate DateStatus;

@ManyToOne
@JsonIgnoreProperties("cntrls")
private Departement dept;


}
