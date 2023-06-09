package com.pfe.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pfe.DTO.LecteurDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Lecteur;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import com.pfe.repos.ControllerRepository;
import com.pfe.repos.LecteurRepository;
import com.pfe.socket1.client2;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EncodeException;
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
	@Autowired(required = true)
	ControllerRepository cntrl;
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
	public String statutdevice(String a,LocalDate b) throws JsonProcessingException {
		Map<String, Object> jsonObject = new HashMap<>();
		jsonObject.put("etat", a);
		jsonObject.put("PreviousDate", b);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		 String jsonString = objectMapper.writeValueAsString(jsonObject);
		return jsonString;
	}
	@GetMapping(value = "/connected/{cn}/{nl}")
public void connected(@PathVariable String cn, @PathVariable int nl) throws DeploymentException, URISyntaxException, IOException, EncodeException {
		List<Porte>lp=new ArrayList<>();
		List<Lecteur>ll=new ArrayList<>();
		Controlleur c =cntrl.GetBySn(cn);
		Lecteur ld=new Lecteur();
		lp=c.getPorte();
		for (Porte p:lp
			 ) {
			ll=p.getLecteur();
			for (Lecteur l:ll
			) {
				if(l.getNumLecteur()==nl){
					ld=l;
				}
			}

		}
		client2 c2=new client2();
		LocalDate olddate=ld.getDateStatus();
		ld.setEtatLecteur("Connected");
		ld.setDateStatus(LocalDate.now());
		lecr.save(ld);
		c2.sendMessage(statutdevice("Connected",olddate));
	}
	@GetMapping(value = "/disconnected/{cn}/{nl}")
	public void disconnected(@PathVariable String cn, @PathVariable int nl) throws DeploymentException, URISyntaxException, IOException, EncodeException {
		List<Porte>lp=new ArrayList<>();
		List<Lecteur>ll=new ArrayList<>();
		Controlleur c =cntrl.GetBySn(cn);
		Lecteur ld=new Lecteur();
		lp=c.getPorte();
		for (Porte p:lp
		) {
			ll=p.getLecteur();
			for (Lecteur l:ll
			) {
				if(l.getNumLecteur()==nl){
					ld=l;
				}
			}

		}
		client2 c2=new client2();
		//LocalDate olddate=ld.getDateStatus();
		ld.setEtatLecteur("Disconnected");
		ld.setDateStatus(LocalDate.now());
		lecr.save(ld);
		c2.sendMessage(statutdevice("Disconnected",LocalDate.now()));
	}
}

