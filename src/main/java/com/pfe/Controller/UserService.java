package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pfe.DTO.UserDto;
import com.pfe.entities.Controlleur;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import com.pfe.repos.PorteRepository;
import com.pfe.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/User")
@CrossOrigin("*")
public class UserService {

@Autowired(required=true)
UserRepository usrr;
	@Autowired(required=true)
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
	@PostMapping(value="/add")
	public int adduser(@RequestBody User user) {
		//Users user=new Users("rayen",passwordEncoder().encode("1234"));
		user.setPassword(passwordEncoder().encode(user.getPassword()));
		User u=usrr.save(user);
	return u.getId();
	}
	@GetMapping(value="/addd/{i}/{u}")
	public void adduserprt(@PathVariable Long i,@PathVariable int u) {
	    User uu = usrr.getById(u);
	    Porte p = prtr.getById(i);
		List<Porte> lp =new ArrayList<>();
		lp.add(p);
		uu.setPrt(lp);
		usrr.save(uu);
	}
public User updateuser(@RequestBody User u) {
	return usrr.save(u);
}

@PutMapping(value="/update/{id}")
public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {
    user.setId(id);
	user.setPassword(passwordEncoder().encode(user.getPassword()));
	updateuser(user);
    return ResponseEntity.ok().build();
  }

@DeleteMapping(value="/delete/{id}")
public void deleteUserById(@PathVariable int id) {
	usrr.deleteById(id);
}
/*
@GetMapping(value="/deleteuser/{id}")
*/
public Optional<User> findByEmail(@PathVariable String id) {
	return usrr.findByEmail(id);
}
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
	/*@GetMapping(value="/all")
public List<UserDto> getAllUsers() {
		List<User> u= new ArrayList<>();
		List<UserDto> udt= new ArrayList<>();
		u=usrr.findAll();
	for(User t : u){
			udt.add(dt.toDto((t)));
		}
		return udt;
}*/
	@GetMapping(value="/allu")
	public User getAllUsersss() {
	return usrr.findByuid("1234567899");
	}
	@GetMapping(value="/all")
	public List<User> getAllUserss() {
		return usrr.findAll();
	}
	@GetMapping(value="/get-one/{id}")
	public User getone(@PathVariable int id){
		return usrr.getById(id);
	}
	public Optional<User> getbyid() {
		return usrr.findById(52);
	}
}

