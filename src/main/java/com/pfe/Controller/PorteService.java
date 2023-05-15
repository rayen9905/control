package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pfe.DTO.PorteDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Lecteur;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import com.pfe.repos.PorteRepository;
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
@RequestMapping("/Porte")
public class PorteService {
	@Autowired(required=true)
	PorteRepository prtr;
static PorteDto dt;
	private Porte prt;

	@PostMapping(value="/add")
	public void addporte(@RequestBody Porte porte) {
	 prtr.save(porte);
	}
	public Porte updateporte(@RequestBody Porte p) {
		return prtr.save(p);
	}

	@PutMapping(value="/update/{id}")
	public ResponseEntity<Void> updatePorte(@PathVariable Long id, @RequestBody Porte porte) {
	    porte.setIdPorte(id);
	    updateporte(porte);
	    return ResponseEntity.ok().build();
	  }
	@GetMapping(value="/getadr1")
	public PorteDto getbyadr1(){
		Porte p= prtr.findByadresse("9ca525b998e4");
		return dt.toDto(p);
	  }
	@GetMapping(value="/getadr")
	public Porte getbyadr(String mac){
		Porte p= prtr.findByadresse(mac);
		return p;
	}
	@DeleteMapping(value="/delete/{id}")
	public void deletePorteById(@PathVariable Long id) {
		prtr.deleteById(id);
	}
	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/
	@GetMapping(value="/get/{id}")
	public Porte getPorteById(@PathVariable Long id) {
		Porte pt = new Porte();
		Optional<Porte> p =null;
		p= prtr.findById(id);
		pt.setIdPorte(p.get().getIdPorte());
		return pt;
	}
	/*@GetMapping(value="/all")
	public List<PorteDto> getAllPrt() {

		List<Porte> u= new ArrayList<>();
		List<PorteDto> udt= new ArrayList<>();
		u=prtr.findAll();
		for(Porte t : u){
			udt.add(dt.toDto((t)));
		}
		return udt;
	}*/
	public Optional<Porte> getbyid() {
		return prtr.findById(1L);
	}
	@GetMapping(value="/get-all")
	public List<Porte> getbyidd() {
		return prtr.findAll();
	}
	@GetMapping(value="/get-one/{id}")
	public Porte getone(@PathVariable Long id){
		return prtr.getById(id);
	}
   public Porte getBysn(Long a, int b){
		return prtr.findBynum(a,b);
}
}
