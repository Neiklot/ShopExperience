package com.shopExperience.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shopExperience.entities.Client;

@Controller
@RequestMapping(value = "/reading")
public class ReaderController {
	
    
	@RequestMapping(method = RequestMethod.GET)
    public String viewRegistration(Map<String, Object> model) {
        Client clientForm = new Client();    
        model.put("clientForm", clientForm);
        System.out.println("Receiving");
        
        return "Readed";
         
    }
}
