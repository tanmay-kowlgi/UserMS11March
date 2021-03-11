package com.infy.UserMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.infy.UserMS.dto.BuyerDTO;
import com.infy.UserMS.dto.LoginDTO;
import com.infy.UserMS.service.BuyerService;

@RestController
@CrossOrigin
public class BuyerController {
	
	@Autowired
	BuyerService buyerService;
	
	@Autowired
	Environment environment;
	
	
	@PostMapping(value = "/api/buyer/register",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createBuyer(@RequestBody BuyerDTO buyerDTO) throws Exception {
		ResponseEntity<String> response = null;
		try {
	     	 buyerService.createBuyer(buyerDTO);
			 String successMessage = environment.getProperty("API.SAVING_SUCCESSFUL");
			 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
				 
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		return response;
			
			
	}
		@PostMapping(value = "/api/buyer/login",  consumes = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<String> Login(@RequestBody LoginDTO loginDTO) throws Exception {
			 ResponseEntity<String> response = null;
		//	 System.out.println(loginDTO);
			 boolean flag = buyerService.login(loginDTO);
			 if(flag) {
				String successMessage = environment.getProperty("LOGIN_SUCCESSFUL");
				response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
			 }else {
				 String failureMessage = environment.getProperty("LOGIN_FAILED");
			     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
			 }
			 return response;
			
			
		}
		@GetMapping(value = "/api/buyer",  produces = MediaType.APPLICATION_JSON_VALUE)
		public List<BuyerDTO> getAllBuyers() {
             return buyerService.buyerListAll();
		}
		@DeleteMapping(value = "/api/buyer/inactivate")
		public ResponseEntity<String> inactivateBuyer(@RequestBody BuyerDTO buyerDTO){
			 ResponseEntity<String> response = null;
			// System.out.println(buyerDTO.getEmail());
			 boolean flag = buyerService.inactivateBuyer(buyerDTO.getEmail());
			 if(flag) {
				String successMessage = environment.getProperty("DELETION_SUCCESSFUL_BUYER");
				response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
			 }else {
				 String failureMessage = environment.getProperty("BUYER_DOES_NOT_EXISTS");
			     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
			 }
			 return response;
		}
		

}
