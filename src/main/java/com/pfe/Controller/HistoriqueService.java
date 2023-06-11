package com.pfe.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.pfe.DTO.DeviceDto;
import com.pfe.DTO.FilterEv;
import com.pfe.DTO.FilterEv2;
import com.pfe.DTO.HistoriqueDto;
import com.pfe.entities.*;
import com.pfe.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Historique")
@Service
public class HistoriqueService {
	@Autowired
	private HistoriqueRepository hisr;
	@Autowired
	private DepartementRepository depr;
	@Autowired
	private ControllerRepository cr;
	@Autowired
	private LecteurRepository lr;
	@Autowired
	private WaveRepository wr;
	@Autowired
	private UserRepository us;


	private Historique his;

	@PostMapping(value="/add")
	public void addhis(@RequestBody Historique his) {
		hisr.save(his);
	}
	public Long addhiss(Historique his) {
		hisr.save(his);
		return his.getIdHis();
	}
	public Historique updatehis(@RequestBody Historique h) {
		return hisr.save(h);
	}

	@PutMapping(value="/update/{id}")
	public ResponseEntity<Void> updateHis(@PathVariable Long id, @RequestBody Historique his) {
	    his.setIdHis(id);
	    updatehis(his);
	    return ResponseEntity.ok().build();
	  }

	@DeleteMapping(value="/delete/{id}")
	public void deleteHisById(@PathVariable Long id) {
		hisr.deleteById(id);
	}
	/*
	@RequestMapping(value="/deleteuser", method=RequestMethod.POST)
	public produit getProduit(Long id) {
		return produitRepository.findById(id).get();

	}*/

	@GetMapping(value="all")
	public List<Historique> getAllhis() {
		return hisr.findAll();
	}
	@GetMapping(value="/get-one/{id}")
	public Historique getone(@PathVariable Long id){
		return hisr.getById(id);
	}
	@GetMapping(value="/one/{p}")
	public Historique gethisbyprt1(@PathVariable Long p) {
		List<Historique>lh=hisr.findAll();
		List<Historique>lhh=new ArrayList<>();
		for (Historique h:lh
		) {
			//System.out.println(h.getPrt().getIdPorte());
			if (h.getPrt().getIdPorte() == p) {
				lhh.add(h);
			}

		}
		//return lhh;
		return lhh.get(lhh.size()-1);
	}
	//@GetMapping(value="/aaaa/{p}")
	public Historique gethisbyprt(Long p) throws NullPointerException{
		List<Historique>lh=hisr.findAll();
		List<Historique>lhh=new ArrayList<>();
		for (Historique h:lh
		) {
			System.out.println(h.getPrt().getIdPorte());
			if (h.getPrt().getIdPorte()==p) {
				lhh.add(h);
			}

		}
		//return lhh;
		return lhh.get(lhh.size()-1);
		}
			/*for (int i =0; i <= lh.size(); i++) {
				if (lh.get(i).getPrt().getIdPorte() == p) {
					lhh.add(lh.get(i));				}
			}*/



	public Historique getbyid(Long a) {
		return hisr.getById(a);
	}
	@GetMapping(value="/countdev")
	public List<DeviceDto> countdisconnected(){
		List<DeviceDto>hd=new ArrayList<>();
		DeviceDto hdd=new DeviceDto();
		LocalDate today = LocalDate.now();
        hdd.setDate(today);
		hdd.setDiconnected(wr.countdisw("Disconnected",today)+lr.countdisl("Disconnected",today)+cr.countdisc("Disconnected",today));

		hd.add(hdd);
		for (int i = 0; i < 6; i++) {
			DeviceDto h= new DeviceDto();
			LocalDate date = today.minusDays(i + 1);
			System.out.println(date);
			h.setDate(date);
			h.setDiconnected(wr.countdisw("Disconnected",date)+lr.countdisl("Disconnected",date)+cr.countdisc("Disconnected",date));
			hd.add(h);
		}
		return hd;
	}

	@GetMapping(value = "/counthiss")
	public List<Historique> countehis1(){
		//LocalDateTime date = LocalDateTime.now();
		//  Date date1 = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
		// Date datee = new Date();
		LocalDate date = LocalDate.now();
		LocalDate currentDate = LocalDate.of(2023,5,3);
        /*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
        String formattedDate = currentDate.format(formatter);
        return java.sql.Date.valueOf(formattedDate);*/
		//return datee=new Date("2023/05/03");
		return hisr.counthis1(date);
	}
	@GetMapping(value = "/counthist/{dep}")
	public int blocbydep(@PathVariable String dep){
		LocalDate date = LocalDate.now();
		List<Historique> his=hisr.counthis2("accès refusé",date);
		List<Historique> his1=new ArrayList<>();
		for (Historique h: his
			 ) {
			if(h.getPrt().getCntrl().getDept().getNomDep().equals(dep)){
				his1.add(h);
			}
		}
		return  his1.size();
	}
	public List<User> getusr(String a){
		Specification<User> spec = Specification.where(null);
		spec = spec.and((root, query, builder) ->
				builder.equal(root.get("Cin"), a));
		return us.findAll(spec);
	}
	@PostMapping("/filterEV1")
	public List<Historique> filtrer1(@RequestBody FilterEv2 fe) {
		Specification<Historique> spec = Specification.where(null);
		List<Long>a=new ArrayList<>();

		if (fe.getEtat() != null) {
			spec = spec.and((root, query, builder) ->
					builder.equal(root.get("EtatHistorique"), fe.getEtat()));
		}

		if (fe.getDateDeb() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd"); // Define the format of the input string
			LocalDate localDate1 = LocalDate.parse(fe.getDateDeb(), formatter);
			LocalDate localDate2 = LocalDate.parse(fe.getDateFin(), formatter);
			spec = spec.and((root, query, builder) ->
					builder.between(root.get("DateHistorique"), localDate1,localDate2));
		}

		if (fe.getTimeDeb() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			LocalTime localTimee1 = LocalTime.parse(fe.getTimeDeb(), formatter);
			LocalTime localTimee2 = LocalTime.parse(fe.getTimeFin(), formatter);
			spec = spec.and((root, query, builder) ->
					builder.between(root.get("TimeHistorique"),localTimee1,localTimee2));
		}
		if (fe.getCin() != null) {
			User u=getusr(fe.getCin()).get(0);
			spec = spec.and((root, query, builder) ->
					builder.equal(root.get("usr"),u));
		}
		if (fe.getDep() != null) {
			Departement d= depr.getById(fe.getDep());
			List<Controlleur> c=d.getCntrls();
			List<Porte> p =new ArrayList<>();
			for (Controlleur cc:c
				 ) {
			p.addAll(cc.getPorte());
			}
			for (Porte pp:p
				 ) {
				System.out.println(pp.getIdPorte());
				a.add(pp.getIdPorte());
			}
		spec = spec.and((root, query, builder) ->
				root.get("prt").in(a));
		}
		return hisr.findAll(spec);
	}

	public int hac(@PathVariable LocalDate a,@PathVariable Long b){
		/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the format of the input string
		LocalDate localDate1 = LocalDate.parse(dl, formatter);*/
		Specification<Historique> spec = Specification.where(null);
		spec = spec.and((root, query, builder) ->
				builder.equal(root.get("DateHistorique"), a));
		spec = spec.and((root, query, builder) ->
				builder.equal(root.get("EtatHistorique"), "accès refusé"));
		Departement d= depr.getById(b);
		List<Controlleur> c=d.getCntrls();
		List<Porte> p =new ArrayList<>();
		List<Long>ld=new ArrayList<>();
		for (Controlleur cc:c
		) {
			p.addAll(cc.getPorte());
		}
		for (Porte pp:p
		) {
			System.out.println(pp.getIdPorte());
			ld.add(pp.getIdPorte());
		}
		spec = spec.and((root, query, builder) ->
				root.get("prt").in(ld));
		List<Historique> lh= hisr.findAll(spec);
		return lh.size();
	}
	@GetMapping("/hac/{a}/{b}")
	public List<HistoriqueDto> getDatess(@PathVariable int a, @PathVariable Long b) {
		// Récupérer la date d'aujourd'hui
		LocalDate today = LocalDate.now();
		System.out.println(today.getDayOfMonth());
		LocalDate currentDate;
		List<HistoriqueDto> hd = new ArrayList<>();
		if (a == today.getMonthValue()) {
			currentDate = LocalDate.of(today.getYear(), a, 1);
			for (int i = 0; i <= today.getDayOfMonth()-1; i++) {
				HistoriqueDto h = new HistoriqueDto();
				LocalDate date = currentDate.plusDays(i);
				h.setDate(date);
				h.setDen(hac(date, b));
				hd.add(h);
			}
		} else if (a % 2 != 0) {
			currentDate = LocalDate.of(today.getYear(), a, 1);
			for (int i = 0; i <= 30; i++) {
				HistoriqueDto h = new HistoriqueDto();
				LocalDate date = currentDate.plusDays(i + 1);
				h.setDate(date);
				h.setDen(hac(date, b));
				hd.add(h);
			}
		} else {
			currentDate = LocalDate.of(today.getYear(), a, 1);
			for (int i = 0; i <= 29; i++) {
				HistoriqueDto h = new HistoriqueDto();
				LocalDate date = currentDate.plusDays(i + 1);
				h.setDate(date);
				h.setDen(hac(date, b));
				hd.add(h);

			}
		}
		return hd;

	}

}
//controlleur/reader/
//conncted /gedh/

