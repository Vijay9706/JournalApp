package net.engineeringdigest.journalApp.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
@Service
public class JournalEntryService {
  
	@Autowired
	JournalEntryRepository entryRepository;
	
	@Autowired
	UserService userService;
	
    private final static Logger logger = LoggerFactory.getLogger(JournalEntryService.class);
			
			
	public List<JournalEntry> getAll(String userName) {
		User byUser=userService.findByUserName(userName);
		List<JournalEntry> all = byUser.getJournalEntries();
	    return all ;
	}
	
	@Transactional
	public void saveEntry(JournalEntry entry , String userName) {
		
		try {
			User user = userService.findByUserName(userName);
			entry.setDate(LocalDateTime.now());
			 JournalEntry saved =  entryRepository.save(entry);
			 user.getJournalEntries().add(saved);
			 userService.saveEntry(user);
		} catch (Exception e) {
			logger.info("Some error occured");
			throw new RuntimeException("An error occured while saving entry", e);
		}
		
	}

	
	public void saveEntry(JournalEntry old) {
		old.setDate(LocalDateTime.now());
		entryRepository.save(old);
		
	}
	

	public Optional<JournalEntry> findJournalEntryById(ObjectId id) {
		
		return entryRepository.findById(id);
	}


    @Transactional
	public void deleteJournaEntry( ObjectId  id , String userName) {
    	try {
    		User user = userService.findByUserName(userName);
    		boolean removed= user.getJournalEntries().removeIf(x -> x.getId().equals(id));
    		if (removed) {
    		userService.saveEntry(user);
    		entryRepository.deleteById(id);
    		}
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occured while deleteing entry", e);
		}
		
	}

	public JournalEntry updateJournal(ObjectId id, JournalEntry myEntry) {
		Optional<JournalEntry> old = entryRepository.findById(id);
		if(old!=null) {
			JournalEntry newEntry =old.get();
			//newEntry.setContent(myEntry.getContent()!=null && myEntry.getContent().equals("")? myEntry.getContent() :old.getContent());
			newEntry.setContent(myEntry.getContent());
			//newEntry.setTitle(myEntry.getTitle()!=null && myEntry.getTitle().equals("") ?myEntry.getTitle() : old.getContent() );
			newEntry.setTitle(myEntry.getTitle());
			newEntry.setDate(LocalDateTime.now());
			return entryRepository.save(newEntry) ;
		}
		
		   return myEntry;
			
	}

	
	  public List<JournalEntry> findByUserName(String userName){
		return null;
	    	
	    }
	

	
	
	
}
