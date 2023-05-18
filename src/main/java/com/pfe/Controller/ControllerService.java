package com.pfe.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.pfe.DTO.ControllerDto;
import com.pfe.DTO.PorteDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.*;
import com.pfe.repos.ControllerRepository;
import com.pfe.repos.HistoriqueRepository;
import com.pfe.repos.PorteRepository;
import com.pfe.repos.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.EncodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/Controller")
public class ControllerService {

		@Autowired(required=true)
		ControllerRepository cntrlr;
	@Autowired(required=true)
	PorteRepository prtr;
	PorteDto dtt;
	@Autowired(required=true)
	UserRepository usrr;
	@Autowired(required=true)
	HistoriqueRepository hisr;
	UserDto dtu;

		private Controlleur cnt;
		private boolean verif;
ControllerDto dt;
		@PostMapping(value="/add")
		public void addcnt(@RequestBody Controlleur cnt) {
			cntrlr.save(cnt);
		}
		public Controlleur updatecnt(@RequestBody Controlleur c) {
			return cntrlr.save(c);
		}

		@PutMapping(value="/update/{id}")
		public ResponseEntity<Void> updateCnt(@PathVariable Long id, @RequestBody Controlleur cnt) {
		    cnt.setIdCont(id);
		    updatecnt(cnt);
		    return ResponseEntity.ok().build();
		  }

		@DeleteMapping(value="/delete/{id}")
		public void deleteCntById(@PathVariable Long id) {
			cntrlr.deleteById(id);
		}
	@GetMapping(value="all")
	public List<Controlleur> getAllcnts() {
			return cntrlr.findAll();
	}
	@GetMapping(value="test")
	public Controlleur getcnttt() {
		return cntrlr.getById(5L);
	}
	@GetMapping(value="/get-one/{id}")
	public Controlleur getone(@PathVariable Long id){
		return cntrlr.getById(id);
	}
	@GetMapping(value="/des/{cntrl}/{dr}/{uid}")
	public ResponseEntity<String> rayen(@PathVariable Long cntrl,@PathVariable int dr,@PathVariable String uid) throws IOException, EncodeException, DeploymentException, URISyntaxException {
		boolean verif1 = false;
		String uidd;
		String ps;
		User u1 = null;
		Porte ptt;
		Historique h = new Historique();
		LocalDate d=LocalDate.now();
		try {
			 cnt = cntrlr.getById(cntrl);//get by serial number
			System.out.println(cnt.getNomCont() + "ahawa");
		}catch(EntityNotFoundException e){
			//return "ghalta fel controlleur";
			//Porte p = prtr.getById(dr);
			//h.setUsr(u1);
			//h.setPrt(p);
			h.setDateHistorique(LocalDate.now());
			h.setTimeHistorique(LocalTime.now());
			h.setEtatHistorique("accès refusé");
			h.setCause("erreur du controlleur");
			hisr.save(h);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("te3ba la3bed");

		}
		try{
			 if (uid.length()==10) {
				uidd=uid.substring(0,6);
				System.out.println("97 "+uidd);
				ps=uid.substring(6,10);
				System.out.println("99 "+ps);
				u1 = usrr.findByuid(uidd);
				 if(!u1.getCodePin().equals(ps)){
					 System.out.println("98 "+u1.getCodePin());
					 //verif=false;
					 //return "ghalta f pin1";
					 Porte p = prtr.findBynum(cnt.getIdCont(),dr);
					 h.setUsr(u1);
					 h.setPrt(p);
					 h.setCause("Utilisateur introuvable erreur du code pin");
					 h.setDateHistorique(LocalDate.now());
					 h.setTimeHistorique(LocalTime.now());
					 h.setEtatHistorique("accès refusé");
					 hisr.save(h);

					 return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body("te3ba la3bed");

				 }
			 }
		}
				catch(NullPointerException e){
				//return "ghalta f uid1";
					Porte p = prtr.findBynum(cnt.getIdCont(),dr);
					h.setUsr(u1);
					h.setPrt(p);
					h.setCause("Utilisateur introuvable erreur du code uid");
					h.setDateHistorique(LocalDate.now());
					h.setTimeHistorique(LocalTime.now());
					h.setEtatHistorique("accès refusé");
					hisr.save(h);
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("te3ba la3bed");

				}

		try{
		if (uid.length()==6) {
				u1 = usrr.findByuid(uid);
				 if (u1.getCodePin()!=null) {
					//return "ghalta f pin2";
					 Porte p = prtr.findBynum(cnt.getIdCont(),dr);
					 h.setUsr(u1);
					 h.setPrt(p);
					 h.setCause("Utilisateur introuvable erreur du code pin");
					 h.setDateHistorique(LocalDate.now());
					 h.setTimeHistorique(LocalTime.now());
					 h.setEtatHistorique("accès refusé");
					 hisr.save(h);
					 return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body("te3ba la3bed");
				 }
		}
		}
				catch(NullPointerException e){
				//return "ghalta f uid2";
					Porte p = prtr.findBynum(cnt.getIdCont(),dr);
					h.setUsr(u1);
					h.setPrt(p);
					h.setCause("Utilisateur introuvable erreur du code uid");
					h.setDateHistorique(LocalDate.now());
					h.setTimeHistorique(LocalTime.now());
					h.setEtatHistorique("accès refusé");
					hisr.save(h);
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("te3ba la3bed");
				}
		        List<Porte> pdd= cnt.getPorte();
		         Porte p = prtr.findBynum(cnt.getIdCont(),dr);
				//Porte p = prtr.getById(dr);
				List<Porte> pd = u1.getPrt();
				for (Porte pt : pd
				) {
					if (p.getIdPorte() == pt.getIdPorte()) {
						verif1=true;
					}
				}
				if (!verif1) {
//					return "ghalta fel beb";
					//Porte p = prtr.getById(dr);
					h.setUsr(u1);
					h.setPrt(p);
					h.setCause("Porte Non Autorisé");
					h.setDateHistorique(LocalDate.now());
					h.setTimeHistorique(LocalTime.now());
					h.setEtatHistorique("accès refusé");
					hisr.save(h);
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("te3ba la3bed");

				}
		//Porte p = prtr.getById(dr);
		h.setUsr(u1);
		h.setPrt(p);
		h.setCause("pas de probléme");
		h.setDateHistorique(LocalDate.now());
		h.setTimeHistorique(LocalTime.now());
		h.setEtatHistorique("access accepté");
		hisr.save(h);
		//return "haw s7ii7 mara7be";
		return ResponseEntity.status(HttpStatus.OK)
				.body("zaretna el barka");


	}
	///////// sawer el materiellllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll
	// amélioration de pris de décision
//liason entre historique et event
	// architecture d'envoi en des alarms et events en websocket
}
