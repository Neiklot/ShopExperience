package com.shopExperience.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopExperience.entities.Client;
 
@Controller
@RequestMapping(value = "/register")
public class RegistrationController {
	
	@Autowired
	BasicController bc;
 
    @RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(Map<String, Object> model) {
        Client clientForm = new Client();    
        model.put("clientForm", clientForm);
         
        return "Registration";
    }
     
    @RequestMapping(method = RequestMethod.POST)
    public String processRegistration(@ModelAttribute("clientForm") Client client,
            Map<String, Object> model) {

        bc.addClient(client); 
        // for testing purpose:
        System.out.println("clientName: " + client.getClientName());
        System.out.println("password: " + client.getPassword());
        
        return "RegistrationSuccess";
    }
}