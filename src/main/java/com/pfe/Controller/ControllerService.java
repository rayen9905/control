package com.pfe.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pfe.DTO.ControllerDto;
import com.pfe.DTO.PorteDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.*;
import com.pfe.repos.ControllerRepository;
import com.pfe.repos.HistoriqueRepository;
import com.pfe.repos.PorteRepository;
import com.pfe.repos.UserRepository;
import com.pfe.socket1.client3;
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
	UserService usr;
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
	//public Controlleur getbysn(String sn) {
		//return cntrlr.GetBySn(sn);
	//}
	@GetMapping(value="/get-one/{id}")
	public Controlleur getone(@PathVariable Long id){
		return cntrlr.getById(id);
	}
	public String send(Historique h) throws JsonProcessingException, NullPointerException {

			//String jsonString = null;
			Map<String, Object> jsonObject = new HashMap<>();
			jsonObject.put("idhis", h.getIdHis());
			jsonObject.put("date", h.getDateHistorique());
			jsonObject.put("time", h.getTimeHistorique());
			jsonObject.put("nomporte", h.getPrt().getNomPorte());
			jsonObject.put("Departement", h.getPrt().getCntrl().getDept().getNomDep());
			jsonObject.put("etat", h.getEtatHistorique());
			jsonObject.put("cause", h.getCause());
			jsonObject.put("idevent", h.getIdEvent());
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			String jsonString = objectMapper.writeValueAsString(jsonObject);
		return jsonString;

		//return jsonString;

	}
	public String send1(Historique h) throws JsonProcessingException, NullPointerException {

		//String jsonString = null;
		Map<String, Object> jsonObject = new HashMap<>();
		jsonObject.put("idhis", h.getIdHis());
		jsonObject.put("date", h.getDateHistorique());
		jsonObject.put("time", h.getTimeHistorique());
		jsonObject.put("etat", h.getEtatHistorique());
		jsonObject.put("cause", h.getCause());
		jsonObject.put("idevent", h.getIdEvent());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String jsonString = objectMapper.writeValueAsString(jsonObject);
		return jsonString;

		//return jsonString;

	}
	@GetMapping(value="/des/{cntrl}/{dr}/{uid}")
	public ResponseEntity<String> rayen(@PathVariable String cntrl,@PathVariable String dr,@PathVariable String uid) throws IOException,NullPointerException, EncodeException, DeploymentException, URISyntaxException {
		boolean verif1 = false;
		String uidd;
		String ps;
		Visiteur u1 = null;
		Porte ptt = null;
		List<Porte> ptrrr=new ArrayList<>();
		List<Lecteur>let=new ArrayList<>();
		Historique h = new Historique();
		LocalDate d=LocalDate.now();
		client3 c3=new client3();
int vdr=Integer.valueOf(dr);
		try {
			 cnt = cntrlr.GetBySn(cntrl);//get by serial number
			System.out.println(cnt.getNomCont() + "ahawa");
		}catch(NullPointerException | EntityNotFoundException e ){
			//return "ghalta fel controlleur";
			//Porte p = prtr.getById(dr);
			//h.setUsr(u1);
			//h.setPrt(p);
			h.setDateHistorique(LocalDate.now());
			h.setTimeHistorique(LocalTime.now());
			h.setEtatHistorique("accès refusé");
			h.setCause("erreur du controlleur");
			hisr.save(h);
			String info=send1(h);
			c3.sendMessage(info);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("te3ba la3bed");

		}
		
		try{
			 if (uid.length()==14) {
				uidd=uid.substring(0,10);
				System.out.println("97 "+uidd);
				ps=uid.substring(10,14);
				System.out.println("99 "+ps);
				u1 = usr.findByuid(uidd);
				 if(!u1.getCodePin().equals(ps)){
					 System.out.println("98 "+u1.getCodePin());
					 //verif=false;
					 //return "ghalta f pin1";
					 ptrrr= cnt.getPorte();
					 for (Porte p:ptrrr
					 ) {
						 let.addAll(p.getLecteur());
					 }
					 for (Lecteur l:let
					 ) {
						 if(l.getNumLecteur()==vdr){
							 ptt=l.getPrt();
						 }
					 }
					// Porte p = prtr.findBynum(cnt.getIdCont(),vdr);
					 h.setUsr(u1);
					 h.setPrt(ptt);
					 h.setCause("Utilisateur introuvable erreur du code pin");
					 h.setDateHistorique(LocalDate.now());
					 h.setTimeHistorique(LocalTime.now());
					 h.setEtatHistorique("accès refusé");
					 hisr.save(h);
					 String info=send(h);
					 c3.sendMessage(info);
					 return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body("te3ba la3bed");

				 }
			 }
		}
				catch(NullPointerException e){
				//return "ghalta f uid1";
					ptrrr= cnt.getPorte();
					for (Porte p:ptrrr
					) {
						let.addAll(p.getLecteur());
					}
					for (Lecteur l:let
					) {
						if(l.getNumLecteur()==vdr){
							ptt=l.getPrt();
						}
					}
					h.setUsr(u1);
					h.setPrt(ptt);
					h.setCause("Utilisateur introuvable erreur du code uid");
					h.setDateHistorique(LocalDate.now());
					h.setTimeHistorique(LocalTime.now());
					h.setEtatHistorique("accès refusé");
					hisr.save(h);
					String info=send(h);
					c3.sendMessage(info);
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("te3ba la3bed");

				}

		try{
		if (uid.length()==8) {
				u1 = usr.findByuid(uid);
				/* if (u1.getCodePin()!=null) {
					//return "ghalta f pin2";
					 ptrrr= cnt.getPorte();
					 for (Porte p:ptrrr
					 ) {
						 let.addAll(p.getLecteur());
					 }
					 for (Lecteur l:let
					 ) {
						 if(l.getNumLecteur()==vdr){
							 ptt=l.getPrt();
						 }
					 }					 h.setUsr(u1);
					 h.setPrt(ptt);
					 h.setCause("Utilisateur introuvable erreur du code pin");
					 h.setDateHistorique(LocalDate.now());
					 h.setTimeHistorique(LocalTime.now());
					 h.setEtatHistorique("accès refusé");
					 hisr.save(h);
					 String info=send(h);
					 c3.sendMessage(info);
					 return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body("te3ba la3bed");
				 }*/
		}
		}
				catch(NullPointerException e){
				//return "ghalta f uid2";
					ptrrr= cnt.getPorte();
					for (Porte p:ptrrr
					) {
						let.addAll(p.getLecteur());
					}
					for (Lecteur l:let
					) {
						if(l.getNumLecteur()==vdr){
							ptt=l.getPrt();
						}
					}					h.setUsr(u1);
					h.setPrt(ptt);
					h.setCause("Utilisateur introuvable erreur du code uid");
					h.setDateHistorique(LocalDate.now());
					h.setTimeHistorique(LocalTime.now());
					h.setEtatHistorique("accès refusé");
					hisr.save(h);
					String info=send(h);
					c3.sendMessage(info);
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("te3ba la3bed");
				}
		try{
			if (uid.length()==10) {
				u1 = usr.findByuid(uid);
				/* if (u1.getCodePin()!=null) {
					//return "ghalta f pin2";
					 ptrrr= cnt.getPorte();
					 for (Porte p:ptrrr
					 ) {
						 let.addAll(p.getLecteur());
					 }
					 for (Lecteur l:let
					 ) {
						 if(l.getNumLecteur()==vdr){
							 ptt=l.getPrt();
						 }
					 }					 h.setUsr(u1);
					 h.setPrt(ptt);
					 h.setCause("Utilisateur introuvable erreur du code pin");
					 h.setDateHistorique(LocalDate.now());
					 h.setTimeHistorique(LocalTime.now());
					 h.setEtatHistorique("accès refusé");
					 hisr.save(h);
					 String info=send(h);
					 c3.sendMessage(info);
					 return ResponseEntity.status(HttpStatus.NOT_FOUND)
							 .body("te3ba la3bed");
				 }*/
			}
		}
		catch(NullPointerException e){
			//return "ghalta f uid2";
			ptrrr= cnt.getPorte();
			for (Porte p:ptrrr
			) {
				let.addAll(p.getLecteur());
			}
			for (Lecteur l:let
			) {
				if(l.getNumLecteur()==vdr){
					ptt=l.getPrt();
				}
			}					h.setUsr(u1);
			h.setPrt(ptt);
			h.setCause("Utilisateur introuvable erreur du code uid");
			h.setDateHistorique(LocalDate.now());
			h.setTimeHistorique(LocalTime.now());
			h.setEtatHistorique("accès refusé");
			hisr.save(h);
			String info=send(h);
			c3.sendMessage(info);
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("te3ba la3bed");
		}
		        List<Porte> pdd= cnt.getPorte();
		ptrrr= cnt.getPorte();
		for (Porte p:ptrrr
		) {
			let.addAll(p.getLecteur());
		}
		for (Lecteur l:let
		) {
			if(l.getNumLecteur()==vdr){
				ptt=l.getPrt();
			}
		}				//Porte p = prtr.getById(dr);
				List<Porte> pd = u1.getPrt();
				for (Porte pt : pd
				) {
					if (ptt.getIdPorte() == pt.getIdPorte()) {
						verif1=true;
					}
				}
				if (!verif1) {
//					return "ghalta fel beb";
					//Porte p = prtr.getById(dr);
					h.setUsr(u1);
					h.setPrt(ptt);
					h.setCause("Porte Non Autorisé");
					h.setDateHistorique(LocalDate.now());
					h.setTimeHistorique(LocalTime.now());
					h.setEtatHistorique("accès refusé");
					hisr.save(h);
					String info=send(h);
					c3.sendMessage(info);
					return ResponseEntity.status(HttpStatus.NOT_FOUND)
							.body("te3ba la3bed");

				}
		//Porte p = prtr.getById(dr);
		h.setUsr(u1);
		h.setPrt(ptt);
		h.setCause("pas de probléme");
		h.setDateHistorique(LocalDate.now());
		h.setTimeHistorique(LocalTime.now());
		h.setEtatHistorique("access accepté");
		hisr.save(h);
		//return "haw s7ii7 mara7be";
		return ResponseEntity.status(HttpStatus.OK)
				.body("zaretna el barka");


	}
	@GetMapping(value = "/verifcntrl/{id}/{np}")
	public boolean verifcntrl(@PathVariable Long id,@PathVariable int np){
			int b = 0;
		Controlleur c= cntrlr.getById(id);
		int a= c.getNbrPorte();
		List<Porte>pr=c.getPorte();
		for (Porte p:pr
			 ) {
			System.out.println(p.getIdPorte());
			if(p.getType().equals(Type_Prt.valueOf("Porte_Principal"))){
	            b=b+2;
			}
else{
	b=b+1;
}
		}
		if(np<=a-b){
			return true;
		}
		else{
			return false;
		}
	}

	///////// sawer el materiellllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll
	// amélioration de pris de décision
//liason entre historique et event
	// architecture d'envoi en des alarms et events en websocket
}
