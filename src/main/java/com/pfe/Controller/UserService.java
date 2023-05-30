package com.pfe.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pfe.DTO.UserDto;
import com.pfe.entities.*;
import com.pfe.repos.PorteRepository;
import com.pfe.repos.RefreshTokenRepository;
import com.pfe.repos.UserRepository;
import com.pfe.repos.VisiteurRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/User")
@CrossOrigin("*")
public class UserService {
	@Autowired
	private ResourceLoader resourceLoader;
	Resource resource = resourceLoader.getResource("classpath:images/your_image_filename.jpg");

	@Value("${upload.directory}")
	private String uploadDirectory; // Path to the directory where you want to store the uploaded files

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
		if (file != null && !file.isEmpty()) {
			try {
				String filePath = uploadDirectory + File.separator + file.getOriginalFilename();
				File dest = new File(filePath);
				file.transferTo(dest);

				return new ResponseEntity<>("Image uploaded successfully.", HttpStatus.OK);
			} catch (IOException e) {
				return new ResponseEntity<>("Error occurred while uploading the image.", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return new ResponseEntity<>("No file selected.", HttpStatus.BAD_REQUEST);
	}

	@Autowired
	private service_email emailService;
	@Autowired(required = true)
	VisiteurRepository usrr;
	@Autowired(required = true)
	UserRepository viss;
	@Autowired(required = true)
	RefreshTokenRepository rr;
	@Autowired(required = true)
	PorteRepository prtr;
	UserDto dt;
	private User usr;

	@Bean
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/*@PostMapping(value="/add/{prt}")
	public ResponseEntity<User> adduser(@RequestBody User user,@PathVariable List<Long> prt) {
		//Users user=new Users("rayen",passwordEncoder().encode("1234"));
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		User u=usrr.save(user);
		for (Long i:prt
			 ) {
			Porte p = prtr.getById(i);
			List<User> pr1 = p.getUsr();
			pr1.add(u);
			p.setUsr(pr1);
			prtr.save(p);
		}
		 return ResponseEntity.ok(user);
	}*/
	@PostMapping(value = "/add")
	public int adduser(@RequestBody User user) {
		//Users user=new Users("rayen",passwordEncoder().encode("1234"));
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		User u = usrr.save(user);
		return u.getId();
	}
	@PostMapping(value = "/addvis")
	public int addvis(@RequestBody User user) {
		//Users user=new Users("rayen",passwordEncoder().encode("1234"));
		//user.setPassword(passwordEncoder().encode(user.getPassword()));
		Visiteur u = (Visiteur) user;
		return usrr.save(u).getId();
	}//////mecheeeeeet

	@GetMapping(value = "/addd/{i}/{u}")
	public void adduserprt(@PathVariable Long i, @PathVariable int u) {
		Visiteur uu = usrr.getById(u);
		Porte p = prtr.getById(i);
		List<Porte> lp = new ArrayList<>();
		lp.add(p);
		uu.setPrt(lp);
		usrr.save(uu);
	}////a verifier

	public User updateuser(@RequestBody User u) {
		return usrr.save(u);
	}

	@PutMapping(value = "/updateps/{id}/{ps}")
	public User updateuser(@PathVariable int id, @PathVariable String ps) {
		User u =  viss.getById(id);
		u.setPassword(passwordEncoder().encode(ps));
		return viss.save(u);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {
		user.setId(id);
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		updateuser(user);
		return ResponseEntity.ok().build();
	}
	@PutMapping(value = "/updatevis/{id}")
	public ResponseEntity<Void> updatevis(@PathVariable int id, @RequestBody User user) {
		user.setId(id);
		usrr.save(user);
		return ResponseEntity.ok().build();
	}

/*	@DeleteMapping(value = "/delete/{id}")
	public void deleteUserById(@PathVariable int id) {
		usrr.deleteById(id);
	}*/

	/*
	@GetMapping(value="/deleteuser/{id}")
	*/
/*public Optional<User> findByEmail22(@PathVariable String id) {
	return usrr.findByEmail(id);
}*/
/*
@Override
@GetMapping(value="/deleteuserrr/{idd}")
public UserDetails loadUserByUsername(@PathVariable String idd)throws UsernameNotFoundException{
  return new User("khalid",passwordEncoder().encode("password"), AuthorityUtils.NO_AUTHORITIES);
 /* Users user = usrr.findByEmail(idd);
  if (user == null) {
	  throw new NotFoundException("user not found");
  }

  return user;*/
	/*@GetMapping(value="/allu")
	public User getAllUsersss() {
	return usrr.findByuid("1234567899");
	}*/
	@GetMapping(value = "/alluser")
	public List<User> getAllUserss() {
	List<User> u= new ArrayList<>();
		for (User uu :viss.findAll()
			 ) {
			if(uu.getPassword()!=null){
				u.add(uu);
			}
		}
		return u;
	}
	@GetMapping(value = "/allvisit")
	public List<User> getAllvisit() throws NullPointerException {
		List<User> u= new ArrayList<>();
		for (User uu :viss.findAll()
		) {
			if(uu.getPassword()==null){
				u.add(uu);
			}
		}
		return u;
	}

	@GetMapping(value = "/get-one/{id}")
	public User getone(@PathVariable int id) {
		return  viss.getById(id);
	}
	/*public Optional<User> getbyid() {
		return usrr.findById(52);
	}*/


	@GetMapping("/send-email-ps/{rec}")
	public String sendEmail(@PathVariable String rec) {
		User u = findbyem(rec);
		if (u != null) {

			try {
				emailService.sendEmail(rec, "mot de passe oublié", "salut monsieur le responsable j'ai oublié mon mot de passe j'éspére me donner un nouveau mot de passe");
				return "Email sent successfully.";
			} catch (MessagingException e) {
				return "Failed to send email: " + e.getMessage();
			}
		} else {
			return "user not found";
		}
	}

	@PostMapping("/send-email/{rec}")
	public String sendEmailps(@PathVariable String rec) {
		User u = findbyem(rec);
		if (u != null) {

			try {
				emailService.sendEmail(rec, "nouveau mot de passe", "salut monsieur :" + u.getLastname() + " voila ton email:" + u.getEmail() + "et ton nouveau mot de passe:" + u.getPassword());
				return "Email sent successfully.";
			} catch (MessagingException e) {
				return "Failed to send email: " + e.getMessage();
			}
		} else {
			return "user not found";
		}
	}

	@PostMapping("/send-emaill/{rec}")
	public String sendEmailAdd(@PathVariable String rec) {
		User u = findbyem(rec);
		if (u != null) {

			try {
				emailService.sendEmail(rec, "nouveau Compte", "salut monsieur :" + u.getFirstname() + " voila ton email:" + u.getEmail() + "et ton mot de passe:" + u.getPassword());
				return "Email sent successfully.";
			} catch (MessagingException e) {
				return "Failed to send email: " + e.getMessage();
			}
		} else {
			return "user not found";
		}
	}

	@GetMapping(value = "/countall")
	public int countAllUser() {

		return usrr.findAll().size();
	}
	public User findbyem(String em) {
		List<User> u = viss.findAll();
		//return u;
		User f = null;
		for (User uu : u
		) {
			//System.out.println("ahayaaaa1");
			//System.out.println(uu.getEmail());
			if (uu.getEmail().equals(em)) {
				//f = new Visiteur();

				//System.out.println("ahayaaaa2");
				f = uu;
			}
		}
		return f;

	}

	@GetMapping(value = "/getref/{em}")
	public String getref(@PathVariable String em) {
		User u = findbyem(em);
		String s = rr.findByRefToken(u.getId()).getToken();
		return s;
	}
	//@GetMapping(value = "/farejj")
	public Optional<User> findByEmail(String em) {
		List<User> u = viss.findAll();
		//return u;
		Optional<User> f = null;
		for (User uu : u
		) {
			System.out.println("ahayaaaa1");
			System.out.println(uu.getEmail());
			if (uu.getEmail().equals(em)) {
				//f = new Visiteur();

				System.out.println("ahayaaaa2");
				f = Optional.of(uu);
			}
		}
		return f;

	}
	public Visiteur findBypin(String em) {
		List<Visiteur> u = usrr.findAll();
		//return u;
		Visiteur f = null;
		for (Visiteur uu : u
		) {
			System.out.println("ahayaaaa1");
			System.out.println(uu.getEmail());
			if (uu.getCodePin().equals(em)) {
				//f = new Visiteur();

				System.out.println("ahayaaaa2");
				f = uu;
			}
		}
		return f;

	}
	public 	Visiteur findByuid(String em) {
		List<User> u = viss.findAll();
		//return u;
		User f = null;
		for (User uu : u
		) {
			System.out.println("ahayaaaa1");
			System.out.println(uu.getCodeUid());
			if (uu.getCodeUid().equals(em)) {
				//f = new Visiteur();

				System.out.println("ahayaaaa2");
				f = uu;
			}
		}
		return f;
	}
	@GetMapping(value = "/farej/{em}")
	public 	Visiteur findByuidd(@PathVariable String em) {
		List<User> u = viss.findAll();
		//return u;
		User f = null;
		for (Visiteur uu : u
		) {
			System.out.println("ahayaaaa1");
			System.out.println(uu.getCodeUid());
			if (uu.getCodeUid().equals(em)) {
				//f = new Visiteur();

				System.out.println("ahayaaaa2");
				f = (User) uu;
			}
		}
		return f;
	}
}

