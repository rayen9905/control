package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;

import com.pfe.DTO.ProfileDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Profile;
import com.pfe.entities.User;
import com.pfe.repos.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Profile")
public class ProfileService {
	@Autowired(required = true)
	ProfileRepository prfr;

	private Profile p;
	ProfileDto dt;

	@PostMapping(value = "/add")
	@PreAuthorize(value = "hasAuthority('ADMIN')")
	public void addprofile(@RequestBody Profile prf) {
		Profile pr = new Profile();
		pr.setNomProfile(prf.getNomProfile());
		prfr.save(pr);
	}

	public Profile updateprofile(@RequestBody Profile p) {
		return prfr.save(p);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Void> updateProfile(@PathVariable Long id, @RequestBody Profile prf) {
		prf.setIdProf(id);
		updateprofile(prf);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/delete/{id}")
	public void deleteProfileById(@PathVariable Long id) {
		prfr.deleteById(id);
	}
	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/

	@GetMapping(value = "all")
	public List<Profile> getAllprfs() {
		return prfr.findAll();
	}
	@GetMapping(value="/get-one/{id}")
	public Profile getone(@PathVariable Long id){
		return prfr.getById(id);
	}
}


