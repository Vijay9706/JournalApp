package net.engineeringdigest.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
     
	@Autowired
	UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user = repository.findByUserName(username);
		 if(user!= null) {
			return org.springframework.security.core.userdetails.User.builder()
             .username(user.getUserName()).password(user.getPassoword())
             .roles(user.getRoles().toArray(new String[0]))
             .build();
			 
			}
		 
		  throw new UsernameNotFoundException("User not found with username: " + username);

}
}