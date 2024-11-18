package net.engineeringdigest.journalApp.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.engineeringdigest.journalApp.entity.User;

import net.engineeringdigest.journalApp.repository.UserRepository;
@Service

public class UserService {
  
	@Autowired
	UserRepository userRepository;
	
	private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();	
    private static final  Logger logger = LoggerFactory.getLogger(UserService.class);

	
	public List<User> getAll() {
	  	
	   return userRepository.findAll();
	}
	
	public void saveEntry(User user) {
	
		userRepository.save(user);
	}
   
	public void saveNewEntry(User user) {
		try {
			user.setPassoword(passwordEncoder.encode(user.getPassoword()));
			user.setRoles(Arrays.asList("USER"));
			userRepository.save(user);
		} catch (Exception e) {
			
		logger.error("dhgjhdjf" , e);
		}
	}
	

	public Optional<User> findUserById(ObjectId id) {
		
		return userRepository.findById(id);
	}



	public void deleteUserEntry(User user) {
		// TODO Auto-generated method stub
		userRepository.delete(user);
	}

	public User findByUserName(String userName) {
	//	userRepository.findByUserName(userName);
		return userRepository.findByUserName(userName);

	}

//	public JournalEntry updateJournal(String id, JournalEntry myEntry) {
//		Optional<JournalEntry> old = user.findById(id);
//		if(old!=null) {
//			JournalEntry newEntry =old.get();
//			//newEntry.setContent(myEntry.getContent()!=null && myEntry.getContent().equals("")? myEntry.getContent() :old.getContent());
//			newEntry.setContent(myEntry.getContent());
//			//newEntry.setTitle(myEntry.getTitle()!=null && myEntry.getTitle().equals("") ?myEntry.getTitle() : old.getContent() );
//			newEntry.setTitle(myEntry.getTitle());
//			newEntry.setDate(LocalDateTime.now());
//			return entryRepository.save(newEntry) ;
//		}
//		
//		   return myEntry;
//			
//	}

	
	
	
}
