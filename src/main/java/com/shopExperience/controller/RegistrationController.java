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

import com.shopExperience.entities.Client;

@Controller
@RequestMapping(value = "/registerClient")
public class RegistrationController {

	@Autowired
	BasicController bc;

	public String generateBarCode() throws IOException, BarcodeException, SAXException {

		final int dpi = 150;
		File outputFile = new File("out.png");
		OutputStream out = new FileOutputStream(outputFile);
		try {
		    BitmapCanvasProvider canvas = new BitmapCanvasProvider(
		            out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
		    new EAN13Bean().generateBarcode( canvas, "1234567890135" );

		    canvas.finish();
		} finally {
		    out.close();
		}

		return "ERROR";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String viewRegistration(Map<String, Object> model)
			throws IOException,  BarcodeException, SAXException {
		Client clientForm = new Client();
		String barcodeNr = generateBarCode();
		model.put("clientForm", clientForm);

		return "Registration";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processRegistration(
			@ModelAttribute("clientForm") Client client,
			Map<String, Object> model) {

		bc.addClient(client);
		// for testing purpose:
		System.out.println("clientName: " + client.getClientName());
		System.out.println("password: " + client.getPassword());

		return "RegistrationSuccess";
	}
}