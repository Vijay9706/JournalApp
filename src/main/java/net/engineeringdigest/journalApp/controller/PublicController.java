package net.engineeringdigest.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.CustomUserDetailsServiceImpl;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.utils.JwtUtil;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	 @Autowired
	 private JwtUtil jwtUtil;
	 
	 @Autowired
	 private CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
	
	//public no authentication
		@PostMapping("/signup")
		public void signup(@RequestBody User user) {
			 userService.saveNewEntry(user);
		}
		
		@PostMapping("/login")
		  public ResponseEntity<String> login(@RequestBody User user) {
	        try{
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassoword()));
	                    
	             UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(user.getUserName());
	            String jwt = jwtUtil.generateToken(userDetails.getUsername());
	            return new ResponseEntity<>(jwt, HttpStatus.OK);
	        }catch (Exception e){
	            log.error("Exception occurred while createAuthenticationToken ", e);
	            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
	        }	
		}
		
		
}
