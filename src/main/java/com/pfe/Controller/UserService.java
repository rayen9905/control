package com.pfe.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.pfe.DTO.UserDto;
import com.pfe.entities.User;
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
	UserDto dt;
private User usr;

@Bean
private PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
@PostMapping(value="/add") 
public ResponseEntity<User> adduser(@RequestBody User user) {
	//Users user=new Users("rayen",passwordEncoder().encode("1234"));
	 user.setPassword(passwordEncoder().encode(user.getPassword()));
	 usrr.save(user);
	 return ResponseEntity.ok(user);
}
public User updateuser(@RequestBody User u) {
	return usrr.save(u);
}

@PutMapping(value="/update/{id}")
public ResponseEntity<Void> updateUser(@PathVariable int id, @RequestBody User user) {
    user.setId(id);
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
	@GetMapping(value="/all")
public List<UserDto> getAllUsers() {
		List<User> u= new ArrayList<>();
		List<UserDto> udt= new ArrayList<>();
		u=usrr.findAll();
	for(User t : u){
			udt.add(dt.toDto((t)));
		}
		return udt;
}
	@GetMapping(value="/allu")
	public User getAllUsersss() {
	return usrr.findByuid("1234567899");
	}
	@GetMapping(value="/alll")
	public List<User> getAllUserss() {
		return usrr.findAll();
	}
	public Optional<User> getbyid() {
		return usrr.findById(52);
	}
}

