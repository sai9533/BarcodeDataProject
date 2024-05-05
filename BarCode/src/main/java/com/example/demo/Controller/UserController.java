package com.example.demo.Controller;

import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.Entity.User;
import com.example.demo.Repo.UserRepository;
import com.example.demo.Service.UserService;
import com.example.demo.Util.EmailUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;


@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailUtils email;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/login")
    public String login() {
    	
        return "login";
    }
    
   
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/createUser")
    public String AddUser(@ModelAttribute User user, HttpSession session) {
        
    	String data="Hello\t"+user.getFullName()+"<br/>"+"\n Thank you for registering";
    	
    	StringBuffer sb=new StringBuffer();
    	sb.append("Hello \t");
    	sb.append(user.getFullName()+"\n");
    	sb.append("Thank you for registering");
    	
    	boolean sendEmail = email.sendEmail("NewUser",data,user.getEmail());
    	
        boolean f=userService.checkEmail(user.getEmail());
        if(f)
        {
            session.setAttribute("msg","Email Id already Exist");
        }
        else if(!userService.IsValidName(user.getFullName())) {
            session.setAttribute("msg", "Please Enter the Valid Name");
        }
        else if(!userService.IsValidEmail(user.getEmail())) {
            session.setAttribute("msg", "Please Enter the Valid Mail");
        }
        else if(!userService.IsValidPassword(user.getPassword())) {
            session.setAttribute("msg", "Password must contain atleast 8 characters,atleast 1 uppercase, 1 special character and 1 digit");
        }
        else {
            User user1=userService.AddUser(user);
            if(user1!=null && sendEmail ) {
                session.setAttribute("msg","Registered Successfully");
            }else {
                session.setAttribute("msg","Something went wrong");
            }
        }


        return "redirect:/register";
    }
    
    
    @GetMapping("/userforgotPwd")
    public String forgot(Model model)
    {
    
    	
    	return "ForgotPassword";
    }
    
    @PostMapping("/emailsentd")
    public String forgotpwd(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        boolean isValidEmail = userService.IsValidEmail(email);
        
      /*  if (isValidEmail) {
            boolean forgotPassword = userService.forgotpassword(email);
            
            if (forgotPassword) {
                redirectAttributes.addFlashAttribute("msgs", "Please check your email for further instructions.");
            } else {
                redirectAttributes.addFlashAttribute("msgs", "Failed to initiate password reset. Please try again later.");
            }
        } else {
            redirectAttributes.addFlashAttribute("msgs", "Invalid email address.");
        }*/
        
        return "redirect:/userforgotpwd";
    }
    @PostMapping("/emailsent")
    public String forgotpwde(@RequestParam("email") String email,Model model) {
        boolean isValidEmail = userService.IsValidEmail(email);
        System.out.println(email);
       if (isValidEmail) {
            boolean forgotPassword = userService.forgotpassword(email);
            System.out.println(forgotPassword);
            if (forgotPassword) {
                //redirectAttributes.addFlashAttribute("msgs", "Please check your email for further instructions.");
            	model.addAttribute("msgs", "please check your email");
            } else {
               // redirectAttributes.addFlashAttribute("msgs", "Failed to initiate password reset. Please try again later.");
            }
        } else {
           // redirectAttributes.addFlashAttribute("msgs", "Invalid email address.");
        	 model.addAttribute("msgs", "Invalide Email");
        }
       
        
      
        return "ForgotPassword";
    }
    
    @GetMapping("/resetpwd")
    public String reset(HttpSession session,Model model)
    {
    	User user= (User) session.getAttribute("authenticatedUser");
    	System.out.println(user);
    	user.setPassword("");
    	model.addAttribute("user", user);
    	return "PasswordReset";
    }
    
    @PostMapping("/users/edit")
    public String processResetForm(@ModelAttribute("user") User user) {
        
    	Optional<User> findById = userRepository.findById(user.getId());
    	User orElse = findById.orElse(null);
    	 String password=passwordEncoder.encode(user.getPassword());
    	orElse.setPassword(password);
    	userRepository.save(orElse);
    	
    	return "redirect:/resetpwd";
    }
    
    @GetMapping("/adminRegistration")
	public String adminReg(Model model)
	{
		User user=new User();
		model.addAttribute("user", user);
		
		return "CreateAdmin";
	}
    
	@PostMapping("/adminSuccess")
	public String adminRegSucces(User user,Model model)
	{
	  
	boolean adminReg = userService.adminReg(user);
	String msg="InvalidDetails";
	if(adminReg)
	{
		msg="Registered Successfully";
	}
	model.addAttribute("msg", msg);
	User user1=new User();
	model.addAttribute("user", user1);
	return "CreateAdmin";
	}
   
}