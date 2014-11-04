package com.shopExperience.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.krysalis.barcode4j.BarcodeException;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import com.shopExperience.entities.Association;
import com.shopExperience.entities.Card;
import com.shopExperience.entities.Client;

@Controller
@RequestMapping(value = "/registerClient")
public class RegistrationController {

	@Autowired
	BasicController bc;

	public String generateBarCode() throws IOException, BarcodeException,
			SAXException {
		final int dpi = 150;
		String code = generateCode();
		File outputFile = new File(code+".png");
		OutputStream out = new FileOutputStream(outputFile);
		try {
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out,
					"image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false,
					0);
			new EAN13Bean().generateBarcode(canvas, code);

			canvas.finish();
		} finally {
			out.close();
		}
		return code;
	}

	public String generateCode() {
		int lastCode = 0;
		if (bc.getCards() != null && bc.getCards().size() > 0) {
			String lastBarCode=bc.getCards().get(bc.getCards().size() - 1)
					.getBarcode();
			lastCode=Integer.parseInt(lastBarCode.substring(0, 12));
			lastCode++;
		}
		String newCode=String.format("%012d", lastCode);
		String checkSum=checkSumCalcul(newCode);

		return newCode+checkSum;

	}
	
	public String checkSumCalcul(String newCode){
		int checkSumInt=0;
		for(int i=0;i<12;i++){
			if(i%2==0){
				checkSumInt+=Integer.parseInt(""+newCode.charAt(i))*1;
			}else{
				checkSumInt+=Integer.parseInt(""+newCode.charAt(i))*3;
			}
		}
		if(checkSumInt==0){return ""+0;}
		return ""+(10-checkSumInt%10);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String viewRegistration(Map<String, Object> model) {
		Client client = new Client();
		model.put("clientForm", client);
		return "Registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processRegistration(
			@ModelAttribute("clientForm") Client client,
			Map<String, Object> model) throws IOException, BarcodeException,
			SAXException {
		Association association=new Association();
		if (bc.getAssociations() != null && bc.getAssociations().size() > 0) {
			association=bc.getAssociations().iterator().next();
		}
		Card card = new Card();
		String barcodeNr = generateBarCode();
		card.setBarcode(barcodeNr);
		card.setClient(client);

//		bc.addClient(client);
		// for testing purpose:
		System.out.println("clientName: " + client.getClientName());
		System.out.println("password: " + client.getPassword());

		return "RegistrationSuccess";
	}
}