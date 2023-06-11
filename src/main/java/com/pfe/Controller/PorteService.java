package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pfe.DTO.PorteDto;
import com.pfe.entities.*;
import com.pfe.repos.ControllerRepository;
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
	@Autowired(required=true)
	ControllerRepository cntrr;
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

	//@GetMapping(value="/getadr")
	public Porte getbyadr(Long mac){
		//mac="9ca525b998e4";
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
	public List<Porte> getbyall() {
		return prtr.findAll();
	}
	@GetMapping(value="/get-one/{id}")
	public Porte getone(@PathVariable Long id){
		return prtr.getById(id);
	}
   public Porte getBysn(Long a, int b){
		return prtr.findBynum(a,b);
}
	@GetMapping(value="/countall")
	public int countAllporte() {

		return prtr.findAll().size();
	}
	@GetMapping(value="/getDoorsCnt/{idc}")
	public List<PorteDto> verifprt(@PathVariable Long idc) {
Controlleur c=cntrr.getById(idc);
int a = c.getNbrPorte();
List<Porte>p =c.getPorte();
List<Lecteur>l=new ArrayList<>();
List<Integer>num=new ArrayList<>();
		List<Integer>numf=new ArrayList<>(a);
		List<PorteDto>prt=new ArrayList<>();

		for (Porte pr:p
			 ) {
			l.addAll(pr.getLecteur());
		}
		for (Lecteur ll:l
			 ) {
numf.add(ll.getNumLecteur());
		}
		for (int i=numf.size();i<a;i++){
			//if(numf.get(i)==null){
				numf.add(i,0);
			//}
		}
		for (int i=0;i<numf.size();i++){
			PorteDto pdt=new PorteDto();
			if (numf.get(i)!=0){
				pdt.setNb(numf.get(i));
				pdt.setColor("Red");
			}
			else{
				pdt.setNb(i+1);
				pdt.setColor("Green");
			}
			prt.add(pdt);
		}
		return prt;
	}

	}
