package com.pfe.Controller;

import com.pfe.DTO.ProfileDto;
import com.pfe.entities.*;
import com.pfe.repos.EventRepository;
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
    @Autowired(required=true)
    EventRepository evtr;

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
   /* @GetMapping(value="verif")
    public WaveShare getwbyid1(){
        return waver.getById(
                "9ca525b998e4");}*/

    @GetMapping(value="/aaa/{mac}")
    public WaveShare getwbyid(@PathVariable String mac){
        return waver.getwave(mac);
}
    @DeleteMapping(value="/delete/{id}")
    public void deleteProfileById(@PathVariable Long id)
    {
        waver.deleteById(id);
    }
    @GetMapping(value="/get-one/{id}")
    public WaveShare getone(@PathVariable Long id){
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
 @GetMapping(value="/adddd/{i}/{u}")
 public void adduserprttt(@PathVariable Long i,@PathVariable Long u) {
     WaveShare uu = waver.getById(u);
     Event p = evtr.getById(i);
     List<WaveShare> lp =new ArrayList<>();
     lp.add(uu);
     p.setWaves(lp);
     evtr.save(p);
 }
}
