package com.shopExperience.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopExperience.entities.User;
 
@Controller
@RequestMapping(value = "/register")
public class RegistrationController {
	
	@Autowired
	BasicController bc;
 
    @RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(Map<String, Object> model) {
        User userForm = new User();    
        model.put("userForm", userForm);
         
        return "Registration";
    }
     
    @RequestMapping(method = RequestMethod.POST)
    public String processRegistration(@ModelAttribute("userForm") User user,
            Map<String, Object> model) {

        bc.addUSer(user); 
        // for testing purpose:
        System.out.println("username: " + user.getUserName());
        System.out.println("password: " + user.getPassword());
        
        return "RegistrationSuccess";
    }
}