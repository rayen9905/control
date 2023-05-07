package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;

import com.pfe.DTO.LecteurDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Lecteur;
import com.pfe.entities.User;
import com.pfe.repos.LecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Lecteur")
public class LecteurService {
	@Autowired(required = true)
	LecteurRepository lecr;
	LecteurDto dt;
	private Lecteur l;

	@PostMapping(value = "/add")
	public void addlecteur(@RequestBody Lecteur lec) {
		lecr.save(lec);
	}

	public Lecteur updatelecteur(@RequestBody Lecteur lec) {
		return lecr.save(lec);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Void> updateLecteur(@PathVariable Long id, @RequestBody Lecteur lec) {
		lec.setIdLecteur(id);
		updatelecteur(lec);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/delete/{id}")
	public void deleteLecteurById(@PathVariable Long id) {
		lecr.deleteById(id);
	}

	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/
	@GetMapping(value = "all")
	public List<Lecteur> getAlllecss() {
		return lecr.findAll();
	}
	@GetMapping(value="/get-one/{id}")
	public Lecteur getone(@PathVariable Long id){
		return lecr.getById(id);
	}
	/*@GetMapping(value="all")
	public List<LecteurDto> getAlllecs() {

		List<Lecteur> u= new ArrayList<>();
		List<LecteurDto> udt= new ArrayList<>();
		u=lecr.findAll();
		for(Lecteur t : u){
			udt.add(dt.toDto((t)));
		}
		return udt;
	}*/
}

