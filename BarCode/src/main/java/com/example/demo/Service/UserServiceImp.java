package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepository;
import com.example.demo.Util.EmailUtils;
import com.example.demo.Util.PasswordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailUtils email;
    
    @Autowired
    private PasswordUtils pwd;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    public User AddUser(User user) {
        String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        user.setNewPassword(pwd.generateRandomPassword(6));
        return userRepository.save(user);

    }
    @Override
    public boolean checkEmail(String email) {
        // TODO Auto-generated method stub
        return userRepository.existsByEmail(email);
    }

    @Override
    public void removeSessionMessage() {
        // TODO Auto-generated method stub
        HttpSession session=((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.removeAttribute("msg");
        session.removeAttribute("authenticatedUser");
    }

    @Override
    public boolean IsValidName(String str) {
        //String str=user.getFullName();
        if (str.length()<2) {
            return false;
        }

        if (!str.matches("[a-zA-Z]+")) {
            return false;
        }
        return true;
    }
    @Override
    public boolean IsValidEmail(String str) {
        // TODO Auto-generated method stub
        //String str=user.getEmail();
        if(str.length()==0)
            return false;
        if(!str.matches("[a-zA-Z]+[0-9]*@gmail.com"))
            return false;

        return true;
    }
    @Override
    public boolean IsValidPassword(String str) {
        //String password = user.getPassword();

        if (str.length() < 8||str.length() > 15) {
            return false;
        }

        if (!str.matches(".*[A-Z].*")) {
            return false;
        }
        if (!str.matches(".*[0-9].*")) {
            return false;
        }
        if (!str.matches(".*[!@#$%^&*()-_=+\\|\\[{\\]};:'\",<.>/?`~].*")) {
            return false;
        }

        return true;
    }
    
    public  boolean forgotpassword(String uemail)
    {
    	User findByEmail = userRepository.findByEmail(uemail);
    	
    	System.out.println(findByEmail.getEmail()+" "+findByEmail.getNewPassword());
    	boolean sendEmail=false;
    	if(findByEmail!=null)
    	{
    	    sendEmail = email.sendEmail("PasswordChange","New Password is :"+findByEmail.getNewPassword(),findByEmail.getEmail());
    	    String password=passwordEncoder.encode(findByEmail.getNewPassword());
    	    findByEmail.setPassword(password);
    	    findByEmail.setNewPassword(pwd.generateRandomPassword(6));
            userRepository.save(findByEmail);    		

    	}
    	
    	return sendEmail;
    }
    public String pwdreset(Long id) {
    	
   	 User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
   	    //existingUser.setEmail(u);
   	    userRepository.save(existingUser);
   	    return "redirect:/users";
    }
	@Override
	public boolean adminReg(User user) {
		// TODO Auto-generated method stub
		String password=passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ADMIN");
        user.setNewPassword(pwd.generateRandomPassword(6));
		User save = userRepository.save(user);
		
		return save!=null?true:false;
	}

}