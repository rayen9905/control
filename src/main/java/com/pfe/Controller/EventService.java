package com.pfe.Controller;

import com.pfe.DTO.FilterEv;
import com.pfe.entities.*;
import com.pfe.repos.EventRepository;
import com.pfe.repos.WaveRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.beans.Expression;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
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
        evnt.setTimeEvent(ev.getTimeEvent());
        evnt.setDateEvent(ev.getDateEvent());
        return evtr.save(evnt);
    }
    @GetMapping(value="all")
    public List<Event> getallevent(){
        return evtr.findAll();
    }
    @GetMapping(value="/get-one/{id}")
    public Event getone(@PathVariable Long id){
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
        return evtr.countevent(ev,date);
    }
    @GetMapping(value = "/countev")
    public List<Event> countevent1(){
        //LocalDateTime date = LocalDateTime.now();
        //  Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        // Date datee = new Date();
        LocalDate date = LocalDate.now();
        LocalDate currentDate = LocalDate.of(2023,5,3);
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        String formattedDate = currentDate.format(formatter);
        return java.sql.Date.valueOf(formattedDate);*/
        //return datee=new Date("2023/05/03");
        return evtr.countevent1(date);
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
    public static Specification<Event> priceLessThanAndCategoryEqual(Type_Evt ete, LocalDate db, LocalDate df, LocalTime td,LocalTime tf) {
        return (root, query, builder) -> {
            Predicate etePredicate = builder.equal(root.get("EtEvent"), ete);
           // Predicate categoryPredicate = builder.greaterThan(root.get("DateEvent"), category);
            Predicate datePredicate = builder.between(root.get("DateEvent"), db,df);
            Predicate timePredicate = builder.between(root.get("TimeEvent"), db,df);

            return builder.or(etePredicate,datePredicate,timePredicate);

            //return builder.or(etePredicate, datePredicate,timePredicate);
        };
    }
    /*@PostMapping("/filterEV")
    public List<Event> getProducts(@RequestBody FilterEv fe) {
        Type_Evt maxPrice=null;
        LocalDate category=LocalDate.of(2023,5,3);
        Specification<Event> spec = priceLessThanAndCategoryEqual(fe.getTypeEv(), fe.getDateDeb(),fe.getDateFin(),fe.getTimeDeb(),fe.getTimeFin());
        return evtr.findAll(spec);
    }*/
    @PostMapping("/filterEV")
    public List<Event> filtrer(@RequestBody FilterEv fe) {
        Specification<Event> spec = Specification.where(null);

        if (fe.getTypeEv() != null) {
            spec = spec.and((root, query, builder) ->
                    builder.equal(root.get("EtEvent"), fe.getTypeEv()));
        }

        if (fe.getDateDeb() != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("DateEvent"), fe.getDateDeb(),fe.getDateFin()));
        }

        if (fe.getTimeDeb() != null) {
            spec = spec.and((root, query, builder) ->
                    builder.between(root.get("TimeEvent"), fe.getTimeDeb(),fe.getTimeFin()));
        }

        return evtr.findAll(spec);
    }


    @GetMapping(value = "monitoring")
    public List<Event> monit(){
        return evtr.monitoring("Entry_Close","Entry_Open","Exist_Open","Exist_Close");
    }
    @GetMapping(value = "monitoring1")
    public List<Event> monit1(){
        return evtr.monitoring("Intrusion_Alarm","Stayed_On","Tailing_Alarm","Reverse_Alarm");
    }


}
