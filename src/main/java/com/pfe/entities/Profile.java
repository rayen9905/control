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
@Table(name = "Profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//Auto increment
	private Long IdProf;
	@Column(name = "NomProfile", length = 50)
	private String NomProfile;
	@OneToMany(mappedBy="prof")
	private List<User> usr;
/*
	public Long getIdProf() {
		return IdProf;
	}

	public void setIdProf(Long idProf) {
		IdProf = idProf;
	}

	public String getNomProfile() {
		return NomProfile;
	}

	public void setNomProfile(String nomProfile) {
		NomProfile = nomProfile;
	}

	public List<User> getUsr() {
		return usr;
	}

	public void setUsr(List<User> usr) {
		this.usr = usr;
	}

 */
}
