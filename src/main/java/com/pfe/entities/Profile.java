package com.pfe.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Profile")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//Auto increment
	private Long IdProf;
	@Column(name = "NomProfile", length = 50)
	private String NomProfile;
	@OneToMany(mappedBy="prof")
	@JsonIgnoreProperties("prof")
	private List<User> usr;

}
