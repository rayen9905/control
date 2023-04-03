package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;

import com.pfe.DTO.ControllerDto;
import com.pfe.DTO.ProfileDto;
import com.pfe.DTO.UserDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Profile;
import com.pfe.entities.User;
import com.pfe.repos.ControllerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

		private Controlleur cnt;
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
		/*
		@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
		public produit getProduit(Long id) {
			return produitRepository.findById(id).get();

		}*/

		@GetMapping(value="all")
		public List<ControllerDto> getAllcnts() {
			List<Controlleur> u= new ArrayList<>();
			List<ControllerDto> udt= new ArrayList<>();
			u=cntrlr.findAll();
			for(Controlleur t : u){
				udt.add(dt.toDto((t)));
			}
			return udt;
		}

}
