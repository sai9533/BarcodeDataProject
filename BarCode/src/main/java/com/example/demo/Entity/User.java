package com.example.demo.Entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String fullName;
    @Email(message = "Email must be valid")
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
    private String role;
    
    private String newPassword;
    public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    public User() {
        super();

    }
    public User(Long id, String fullName, String email, String password) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password + "]";
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


}