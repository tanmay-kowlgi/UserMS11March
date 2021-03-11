package com.infy.UserMS.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.infy.UserMS.dto.WishListDTO;
import com.infy.UserMS.entity.WishListEntity;
import com.infy.UserMS.entity.WishListEntityUsingIdClass;
import com.infy.UserMS.repository.WishListRepository;

@Service
public class WishListService {
	
	@Autowired
	WishListRepository wishListRepo;
	
	public void addItemToWishList(WishListDTO wishListDTO) throws Exception{
		   Optional<WishListEntity> optWishList = wishListRepo.findById(new WishListEntityUsingIdClass(wishListDTO.getBuyerId(),wishListDTO.getProdId()));
		   if(optWishList.isPresent()) {
			   throw new Exception("WISHLIST_EXISTS");
		   }
		   WishListEntity wishList = wishListDTO.createEntity();
		   wishListRepo.save(wishList);
	}
	public boolean deleteItemFromWishList(WishListDTO wishListDTO){
		Optional<WishListEntity> optWishList = wishListRepo.findById(new WishListEntityUsingIdClass(wishListDTO.getBuyerId(),wishListDTO.getProdId()));
		if(optWishList.isPresent()) {
			WishListEntity wishList = optWishList.get();
			wishListRepo.delete(wishList);
			return true;
		}
		return false;
	}

}
