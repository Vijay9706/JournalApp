package net.engineeringdigest.journalApp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WeatherService weatherService;
	
	
	@GetMapping
	public String  greeting(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//  String userName = authentication.getName();
		WeatherResponse weather = weatherService.getWeather("Mumbai");
		if (weather!=null) {
		  return "Hi " +  authentication.getName() + ", Weather feels like " + weather.getCurrent().getFeelslike();
		}
		else {
			return "Hi " + authentication.getName();
		}
	}
	
	
	@GetMapping("id/{id}")
	public ResponseEntity<User> getById(@PathVariable ObjectId id) {
		Optional<User> user =userService.findUserById(id);
		if(user.isPresent()) {
			 return new ResponseEntity<>(user.get() ,HttpStatus.OK);
		 }
		return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	}
	
	
	
	
	@DeleteMapping()
	public void deleteUser( ) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  String userName = authentication.getName();
			User InDb = userService.findByUserName(userName);
		userService.deleteUserEntry(InDb);
	}
	
	
	//will Authenticate first
	@PutMapping
	public   ResponseEntity<?> updateUser (@RequestBody User user) {
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  String userName = authentication.getName();
		
		User InDb = userService.findByUserName(userName);
	
			InDb.setUserName(user.getUserName());
			InDb.setPassoword(user.getPassoword());
			userService.saveNewEntry(InDb);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
		
		
	}
}
