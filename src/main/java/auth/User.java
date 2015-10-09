package auth;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	// Persistent Fields:
	@Id @GeneratedValue
	private Long id;
	private String email;
	private String name;
	private String password; // TODO: add salt and hash password
	private Date registerDate;
	
        public User() {
            
        }
        
	public User(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.registerDate = new Date(System.currentTimeMillis()); 
	}
        
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setNewPassword(String newPassword) throws Exception {
		if (Objects.equals(newPassword, "")) {
			throw new Exception("Password cannot be empty.");
		}
		
		this.password = newPassword;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(long timestamp) {
		this.registerDate = new Date(timestamp);
	}
	
	public Long getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s)", this.name, this.email);
	}
}
