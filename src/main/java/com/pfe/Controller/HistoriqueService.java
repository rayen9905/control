package com.pfe.Controller;

import java.util.List;

import com.pfe.entities.Historique;
import com.pfe.repos.HistoriqueRepository;
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
@RequestMapping("/Historique")
public class HistoriqueService {
	@Autowired(required=true)
	HistoriqueRepository hisr;

	private Historique his;

	@PostMapping(value="/add")
	public void addhis(@RequestBody Historique his) {
		hisr.save(his);
	}
	public Long addhiss(Historique his) {
		hisr.save(his);
		return his.getIdHis();
	}
	public Historique updatehis(@RequestBody Historique h) {
		return hisr.save(h);
	}

	@PutMapping(value="/update/{id}")
	public ResponseEntity<Void> updateHis(@PathVariable Long id, @RequestBody Historique his) {
	    his.setIdHis(id);
	    updatehis(his);
	    return ResponseEntity.ok().build();
	  }

	@DeleteMapping(value="/delete/{id}")
	public void deleteHisById(@PathVariable Long id) {
		hisr.deleteById(id);
	}
	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/

	@GetMapping(value="all")
	public List<Historique> getAllhis() {
		return hisr.findAll();
	}
	public Historique getbyid(Long a) {
		return hisr.getById(a);
	}
}
