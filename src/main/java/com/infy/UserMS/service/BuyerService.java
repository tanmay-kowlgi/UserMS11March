package com.infy.UserMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infy.UserMS.dto.BuyerDTO;
import com.infy.UserMS.dto.LoginDTO;
import com.infy.UserMS.entity.BuyerEntity;
import com.infy.UserMS.repository.BuyerRepository;
import com.infy.validator.Validator;

@Service
public class BuyerService {
	
	@Autowired
	BuyerRepository buyerRepo;
	
	public void createBuyer(BuyerDTO buyerDTO) throws Exception{
		Validator.validateBuyer(buyerDTO);
		Optional<BuyerEntity> optBuyerPhone = buyerRepo.findByPhonenumber(buyerDTO.getPhonenumber());
		Optional<BuyerEntity> optBuyerEmail = buyerRepo.findByEmail(buyerDTO.getEmail());
		if(optBuyerPhone.isPresent()) {
			throw new Exception("USER_PHONE_EXISTS");
		}
		if(optBuyerEmail.isPresent()) {
			throw new Exception("USER_EMAIL_EXISTS");
		}
		BuyerEntity buyer = buyerDTO.createEntity();
		buyerRepo.save(buyer);
	}
	
	public boolean login(LoginDTO loginDTO) {
		Optional<BuyerEntity> optBuyer = buyerRepo.findByEmail(loginDTO.getEmail());
		if (optBuyer.isPresent()) {
			BuyerEntity buyer = optBuyer.get();
			if (buyer.getPassword().equals(loginDTO.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
	public List<BuyerDTO> buyerListAll() {
		List<BuyerDTO> buyerDTOList = new ArrayList<>();
		List<BuyerEntity> buyerList = buyerRepo.findAll();
	    for(BuyerEntity buyer:buyerList) {
			BuyerDTO buyerDTO = BuyerDTO.valueOf(buyer);
			buyerDTOList.add(buyerDTO);
			
		}
		return buyerDTOList;
		
	}
	
	public boolean inactivateBuyer(String email) {
		Optional<BuyerEntity> optBuyer = buyerRepo.findByEmail(email);
		if(optBuyer.isPresent()) {
			BuyerEntity Buyer = optBuyer.get();
			buyerRepo.delete(Buyer);
			return true;
		}
		return false;
	}
	

}
