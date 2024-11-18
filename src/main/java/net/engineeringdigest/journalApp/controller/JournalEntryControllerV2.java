package net.engineeringdigest.journalApp.controller;


import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;

//import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
	
	@Autowired
	JournalEntryService entryService;
	
   @Autowired
   UserService userService;
	//will Authenticate first
	@GetMapping
	public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		List<JournalEntry> entries= entryService.getAll(userName);
		if(entries !=null && !entries.isEmpty()) {
		return new ResponseEntity<>(entries ,HttpStatus.OK);
		}
		return new ResponseEntity<>(entries ,HttpStatus.NOT_FOUND);
	}
	
	//will Authenticate first
	@PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry ) {
	
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		try {
          entryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry ,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(myEntry ,HttpStatus.BAD_REQUEST);
		}
	}
	
	////will Authenticate first
	 @GetMapping("id/{id}")
	 public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId id) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			User user = userService.findByUserName(userName);
			List<JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());
			if(!collect.isEmpty()) {
				 Optional<JournalEntry> JournalEntryDetails = entryService.findJournalEntryById(id);
				 if(JournalEntryDetails.isPresent()) {
					 return new ResponseEntity<>(JournalEntryDetails.get() ,HttpStatus.OK);
				 }
			}
		 
		return new ResponseEntity<>( HttpStatus.NOT_FOUND);
	}
		//will Authenticate first  
    @DeleteMapping("id/{id}")
	 public ResponseEntity<?> deleteById(@PathVariable  ObjectId id ) {
    	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
	   entryService.deleteJournaEntry(id ,userName);
	  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PutMapping("/id/{id}")
	 public ResponseEntity<JournalEntry>  updateJournalById(@PathVariable ObjectId id ,
			 @RequestBody JournalEntry newEntry )
           {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> collect = user.getJournalEntries().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());
    	
    	if(!collect.isEmpty()) {
			 Optional<JournalEntry> JournalEntryDetails = entryService.findJournalEntryById(id);
			 if(JournalEntryDetails.isPresent()) {
				 JournalEntry old = JournalEntryDetails.get();
				 old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() :old.getContent());
		    		old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ?newEntry.getTitle() : old.getContent() );
					entryService.saveEntry(old);
					return  new ResponseEntity<>(old,HttpStatus.OK);
			 }
		}
    	
    	 return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
    
  
}



