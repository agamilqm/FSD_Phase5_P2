package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Login;
import com.dto.ChangePasswordFormat;
import com.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	LoginRepository loginRepository;
	
	public String signUp(Login login) {
		
		if (login.getTypeofuser().equalsIgnoreCase("admin")) {
			return "Admin account cannot be created";
		}
		
		Optional<Login> result = loginRepository.findById(login.getEmailid());
		
		if(result.isPresent()) {
			return "Account already exists";
		}
		
		loginRepository.save(login);
		return "Account created successfully";
		
	}
	
	public String signIn(Login login) {
			
		Login ll = loginRepository.signIn(login.getEmailid(),login.getPassword(), login.getTypeofuser());
			if(ll==null) {
				return "Invalid emailid or password";
			}
			if(ll.getTypeofuser().equalsIgnoreCase("admin")) {
				return "Admin login successfully";
			}
			return "Customer login successfully";
	}
	
	public String searchAccount(Login login) {
		
		Login ll = loginRepository.getSpecificAccount(login.getEmailid(), login.getTypeofuser());
		if (ll == null) {
			return "Account was not found";
		}
		return "Account Found successfully";
	}
	
	public List<Login> getAccounts () {
		List<Login> listOfAccounts = loginRepository.findAll();
		for (Login login : listOfAccounts) {
			
			System.out.println(login.getEmailid());
		}
		return  listOfAccounts;
	}
		
	public String deleteAccount (Login login) {
		
		if (!loginRepository.existsById(login.getEmailid())) {
			return "Account does not exist";
		}
		loginRepository.deleteById(login.getEmailid());
		return "Account Deleted Successfully";
	}
	
	public String changePassword (ChangePasswordFormat cpf) {
		
		if (!loginRepository.existsById(cpf.getEmailid())) {
			return "Account does not exist";
		}
		Optional<Login> ll = loginRepository.findById(cpf.getEmailid());
		if (ll.get().getPassword().equals(cpf.getOldpassword())) {
			ll.get().setPassword(cpf.getNewpassword());
			loginRepository.saveAndFlush(ll.get());
			return "Password Changed Successfully";
		}
		return "Old Password is wrong";
	}
	
}
