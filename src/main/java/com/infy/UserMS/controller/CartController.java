package com.infy.UserMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.service.CartService;

@RestController
@CrossOrigin
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	Environment environment;
	
	@PostMapping(value = "/api/cart/add",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addItemToCart(@RequestBody CartDTO cartDTO) throws Exception {
		ResponseEntity<String> response = null;
		try {
	     	 cartService.addItemToCart(cartDTO);
			 String successMessage = environment.getProperty("CART_ADDED");
			 response = new ResponseEntity<String>(successMessage,HttpStatus.CREATED);
				 
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.OK,environment.getProperty(e.getMessage()),e);
				 
		}
		return response;
			
			
	}
	@DeleteMapping(value = "/api/cart/remove")
	public ResponseEntity<String> deleteItemFromCart(@RequestBody CartDTO cartDTO){
		 ResponseEntity<String> response = null;
		 boolean flag = cartService.deleteItemFromCart(cartDTO);
		 if(flag) {
			String successMessage = environment.getProperty("DELETION_CART");
			response = new ResponseEntity<String>(successMessage,HttpStatus.OK);
		 }else {
			 String failureMessage = environment.getProperty("CART_DOES_NOT_EXIST");
		     response = new ResponseEntity<String>(failureMessage,HttpStatus.BAD_REQUEST);
		 }
		 return response;
	}

}
