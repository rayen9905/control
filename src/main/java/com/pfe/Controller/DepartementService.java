package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;

import com.pfe.DTO.DepartementDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Departement;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import com.pfe.repos.DepartementRepository;
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
@RequestMapping("/Departement")
public class DepartementService {
	@Autowired(required=true)
	DepartementRepository depr;

	private Departement dep;
DepartementDto dt;
	@PostMapping(value="/add")
	public void adddep(@RequestBody Departement dep) {
		depr.save(dep);
	}
	public void addepp(String a){
		Departement d=new Departement();
		d.setNomDep(a);
		depr.save(d);
	}
	public Departement updatedep(@RequestBody Departement d) {
		return depr.save(d);
	}

	@PutMapping(value="/update/{id}")
	public ResponseEntity<Void> updateDep(@PathVariable Long id, @RequestBody Departement dep) {
	    dep.setIdDep(id);
	    updatedep(dep);
	    return ResponseEntity.ok().build();
	  }

	@DeleteMapping(value="/delete/{id}")
	public void deleteDepById(@PathVariable Long id) {
		depr.deleteById(id);
	}
	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/

	/*@GetMapping(value="all")
	public List<DepartementDto> getAlldeps() {
		List<Departement> u= new ArrayList<>();
		List<DepartementDto> udt= new ArrayList<>();
		u=depr.findAll();
		for(Departement t : u){
			udt.add(dt.toDto((t)));
		}
		return udt;
	}*/
	@GetMapping(value="all")
	public List<Departement> getAlldeps() {
		return depr.findAll();
	}
	@GetMapping(value="/getByDep/{id}")
	public List<Porte> getBydeps(@PathVariable Long id) {
		Departement d =depr.getById(id);
	  List<Porte>p=d.getPorte();
	  return p;
	}
	@GetMapping(value="/get-one/{id}")
	public Departement getone(@PathVariable Long id){
		return depr.getById(id);
	}
}
