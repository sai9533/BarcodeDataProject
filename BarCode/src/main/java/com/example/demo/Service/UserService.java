package com.example.demo.Service;


import com.example.demo.Entity.User;

public interface UserService {
    public User AddUser(User user);
    public boolean checkEmail(String email);
    public void removeSessionMessage();
    public boolean IsValidName(String str);
    public boolean IsValidEmail(String str);
    public boolean IsValidPassword(String str);
    
    public boolean forgotpassword(String uemail);
    public boolean adminReg(User user);
}