package com.pfe.Controller;

import com.pfe.DTO.ProfileDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Profile;
import com.pfe.entities.WaveShare;
import com.pfe.repos.ProfileRepository;
import com.pfe.repos.WaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/Wave")
public class WaveService {
    @Autowired(required=true)
    WaveRepository waver;

    private WaveShare w;
    ProfileDto dt;
    @PostMapping(value="/add")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public void addwave(@RequestBody WaveShare wa) {
        WaveShare wav=new WaveShare();
        wav.setAdresse(wa.getAdresse());
        wav.setStatus(wa.getStatus());
        wav.setPrt(wa.getPrt());
        waver.save(wav);
    }
    public WaveShare updatewave(@RequestBody WaveShare wa) {
        return waver.save(wa);
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<Void> updatewave(@PathVariable String id, @RequestBody WaveShare wa) {
        wa.setAdresse(id);
        updatewave(wa);
        return ResponseEntity.ok().build();
    }
    @GetMapping(value="verif")
    public WaveShare getwbyid1(){
        return waver.getById(
                "9ca525b998e4");}

public WaveShare getwbyid(String mac){
        return waver.getById(mac);
}
    @DeleteMapping(value="/delete/{id}")
    public void deleteProfileById(@PathVariable String id)
    {
        waver.deleteById(id);
    }
    @GetMapping(value="get-one")
    public WaveShare getone(String id){
        return waver.getById(id);
    }
   @GetMapping(value="all")
    public List<WaveShare> getAllprfs(){
        return waver.findAll();
    }
	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/

 /*   @GetMapping(value="all")
    public List<ProfileDto> getAllprfs() {
        List<Profile> u= new ArrayList<>();
        List<ProfileDto> udt= new ArrayList<>();
        u=prfr.findAll();
        for(Profile t : u){
            udt.add(dt.toDto((t)));
        }
        return udt;
    }*/
}
