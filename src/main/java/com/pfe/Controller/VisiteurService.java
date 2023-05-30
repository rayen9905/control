package com.pfe.Controller;

import com.pfe.DTO.UserDto;
import com.pfe.entities.Porte;
import com.pfe.entities.User;
import com.pfe.entities.Visiteur;
import com.pfe.repos.PorteRepository;
import com.pfe.repos.RefreshTokenRepository;
import com.pfe.repos.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Visiteur")
@CrossOrigin("*")
public class VisiteurService {
}
