package net.engineeringdigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;



import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;



@Document(collection = "users")
public class User {
  @Id
  private ObjectId id;
  
  @Indexed(unique = true)
  @NonNull
  private String userName;
  
  private String email;
  private boolean sentimentalAnalysis;
  
  @NonNull
  private String passoword;
  
  @DBRef
  private List<JournalEntry> journalEntries = new ArrayList<>();
  
  private List<String> roles;

public List<String> getRoles() {
	return roles;
}

public void setRoles(List<String> roles) {
	this.roles = roles;
}

public ObjectId getId() {
	return id;
}

public void setId(ObjectId id) {
	this.id = id;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getPassoword() {
	return passoword;
}

public void setPassoword(String passoword) {
	this.passoword = passoword;
}

public List<JournalEntry> getJournalEntries() {
	return journalEntries;
}

public void setJournalEntries(List<JournalEntry> journalEntries) {
	this.journalEntries = journalEntries;
}

}