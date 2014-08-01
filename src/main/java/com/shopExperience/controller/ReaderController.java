package com.shopExperience.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopExperience.entities.Card;

@Controller
public class ReaderController {

	@Autowired
	BasicController bc;
	
	@RequestMapping(value = "/reading",method = RequestMethod.POST)
	public String reading(@RequestParam("scanFormat") String scanFormat) {
		Card card=bc.searchCardByBarCode(scanFormat.substring(scanFormat.indexOf(":")+2));
		System.out.println("Reading::>"+scanFormat+" User::>"+card.getClient().getClientName());
		return "ok";
	}
}
