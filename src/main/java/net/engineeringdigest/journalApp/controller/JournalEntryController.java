//package net.engineeringdigest.journalApp.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//
//@RestController
//@RequestMapping
//public class JournalEntryController {
//	
//	private Map<Long, JournalEntry> journaEntries = new HashMap<>();
//	
//	@GetMapping("/get")
//	public List<JournalEntry> getAll(){
//	return new ArrayList<JournalEntry>(journaEntries.values())	;
//	}
//	
//	@PostMapping("/create")
//    public boolean createEntry(@RequestBody JournalEntry myEntry) {
//    journaEntries.put(myEntry.equals(id), myEntry);
//	return true;
//	}
//	
//	 @GetMapping("/get/{id}")
//	 public JournalEntry getById(@PathVariable Long id) {
//	 return  journaEntries.get(id);
//	}
//	   
//    @DeleteMapping("/delete/{id}")
//	 public JournalEntry deleteById(@PathVariable Long id) {
//	 return  journaEntries.remove(id);
//    }
//    
//    @PutMapping("/change/{id}")
//	 public JournalEntry updateById(@PathVariable Long id ,@RequestBody JournalEntry myEntry ) {
//	 return  journaEntries.put(id, myEntry);
//   }
//	
//}
//
//
//
