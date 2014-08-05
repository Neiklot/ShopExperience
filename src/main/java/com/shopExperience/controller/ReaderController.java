package com.shopExperience.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopExperience.entities.Card;

@Controller
public class ReaderController {

	@Autowired
	BasicController bc;

	@RequestMapping(value = "/reading", method = RequestMethod.POST)
	@ResponseBody
	public String reading(@RequestParam("scanFormat") String scanFormat) {
		if (scanFormat != null) {
			Card card = bc.searchCardByBarCode(scanFormat.substring(scanFormat
					.indexOf(":") + 2));
			if (card != null) {
				System.out.println("Reading::>" + scanFormat + " User::>"
						+ card.getClient().getClientName());
			}else{
				System.out.println("Communication OK - User Not found");
			}
		}
		return "ok";
	}
}
