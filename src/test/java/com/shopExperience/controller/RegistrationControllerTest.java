package com.shopExperience.controller;

import static org.junit.Assert.*;

import org.junit.Test;

//@RunWith(JUnit.class)
public class RegistrationControllerTest {
	
	@Test
	public void generateCodeTest(){
		RegistrationController rc=new RegistrationController();
//		assertEquals("3",rc.checkSumCalcul("629104150021"));
		assertEquals("3",rc.checkSumCalcul("247124506783"));
	}

}
