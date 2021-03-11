package com.infy.UserMS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.UserMS.dto.CartDTO;
import com.infy.UserMS.entity.CartEntity;
import com.infy.UserMS.entity.CartEntityUsingIdClass;
import com.infy.UserMS.repository.CartRepository;

@Service
public class CartService {
	
	@Autowired
	CartRepository cartRepo;
	
	public void addItemToCart(CartDTO cartDTO) throws Exception{
	   Optional<CartEntity> optCart = cartRepo.findById(new CartEntityUsingIdClass(cartDTO.getBuyerId(),cartDTO.getProdId()));
	   if(optCart.isPresent()) {
		   throw new Exception("CART_EXISTS");
	   }
	   CartEntity cart = cartDTO.createEntity();
	   cartRepo.save(cart);
	}
	
	public boolean deleteItemFromCart(CartDTO cartDTO){
		Optional<CartEntity> optCart = cartRepo.findById(new CartEntityUsingIdClass(cartDTO.getBuyerId(),cartDTO.getProdId()));
		if(optCart.isPresent()) {
			CartEntity cart = optCart.get();
			cartRepo.delete(cart);
			return true;
		}
		return false;
	}

}
