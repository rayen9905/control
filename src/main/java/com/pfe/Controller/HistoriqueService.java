package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;

import com.pfe.entities.Controlleur;
import com.pfe.entities.Historique;
import com.pfe.repos.HistoriqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
@Service
public class HistoriqueService {
	@Autowired
	private HistoriqueRepository hisr;

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
	@GetMapping(value="get-one")
	public Historique getone(Long id){
		return hisr.getById(id);
	}
	@GetMapping(value="/one/{p}")
	public Historique gethisbyprt1(@PathVariable Long p) {
		List<Historique>lh=hisr.findAll();
		List<Historique>lhh=new ArrayList<>();
		for (Historique h:lh
		) {
			if (h.getPrt().getIdPorte() == p) {
				lhh.add(h);
			}

		}
		//return lhh;
		return lhh.get(lhh.size()-1);
	}
	public Historique gethisbyprt(Long p) {
		List<Historique>lh=hisr.findAll();
		List<Historique>lhh=new ArrayList<>();
		for (Historique h:lh
		) {
			if (h.getPrt().getIdPorte() == p) {
				lhh.add(h);
			}

		}
		//return lhh;
		return lhh.get(lhh.size()-1);
		}
			/*for (int i =0; i <= lh.size(); i++) {
				if (lh.get(i).getPrt().getIdPorte() == p) {
					lhh.add(lh.get(i));				}
			}*/



	public Historique getbyid(Long a) {
		return hisr.getById(a);
	}
}
//controlleur/reader/
//conncted /gedh/

