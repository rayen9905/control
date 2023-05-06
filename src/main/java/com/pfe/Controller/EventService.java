package com.pfe.Controller;

import com.pfe.entities.Controlleur;
import com.pfe.entities.Event;
import com.pfe.entities.WaveShare;
import com.pfe.repos.EventRepository;
import com.pfe.repos.WaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Event")
public class EventService {
    @Autowired(required=true)
    EventRepository evtr;

    private Event e;
 /*   @PostMapping(value="/add")
    @PreAuthorize(value = "hasAuthority('ADMIN')")*/
    public Event addevt(@RequestBody Event ev) {
        Event evnt=new Event();
        evnt.setEtEvent(ev.getEtEvent());
        evnt.setDateEvent(ev.getDateEvent());
        return evtr.save(evnt);
    }
    @GetMapping(value="all")
    public List<Event> getallevent(){
        return evtr.findAll();
    }
    @GetMapping(value="get-one")
    public Event getone(Long id){
        return evtr.getById(id);
    }
   /* public WaveShare updatewave(@RequestBody WaveShare wa) {
        return waver.save(wa);
    }

    @PutMapping(value="/update/{id}")
    public ResponseEntity<Void> updatewave(@PathVariable String id, @RequestBody WaveShare wa) {
        wa.setAdresse(id);
        updatewave(wa);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value="/delete/{id}")
    public void deleteProfileById(@PathVariable String id)
    {
        waver.deleteById(id);
    }*/
    @GetMapping(value = "count")
    public int countevent(String ev){
        return evtr.countevent(ev);
    }
}
