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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
    @GetMapping(value = "/count/{ev}")
    public Long countevent(@PathVariable String ev){
        //LocalDateTime date = LocalDateTime.now();
      //  Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
       // Date datee = new Date();
        LocalDate date = LocalDate.now();
        LocalDate currentDate = LocalDate.of(2023,5,3);
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        String formattedDate = currentDate.format(formatter);
        return java.sql.Date.valueOf(formattedDate);*/
        //return datee=new Date("2023/05/03");
        return evtr.countevent(ev,currentDate);
    }
    @GetMapping(value = "/countt")
    public Date counteventt(){
        //LocalDateTime date = LocalDateTime.now();
        //  Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
       // Date datee = new Date();
        LocalDate currentDate = LocalDate.of(2023,5,3);
        int year = currentDate.getYear();
        int month = currentDate.getMonthValue();
        int day = currentDate.getDayOfMonth();
        return java.sql.Date.valueOf(String.format("%d-%02d-%02d", year, month, day));
        //return datee=new Date("2023/05/03");
        //return evtr.countevent(ev,datee);
    }
    @GetMapping("/dates")
    public List<String> getDates() {
        // Récupérer la date d'aujourd'hui
        LocalDate today = LocalDate.now();

        // Récupérer les six derniers jours
        List<String> dates = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 6; i++) {
            LocalDate date = today.minusDays(i + 1);
            String formattedDate = date.format(formatter);
            dates.add(formattedDate);
        }

        return dates;
    }
}