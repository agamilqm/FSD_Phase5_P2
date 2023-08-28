package com.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Login;
import com.dto.ChangePasswordFormat;
import com.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired 
	LoginService loginService;
	
	@RequestMapping(value = "/" , method = RequestMethod.GET)
	public String open(Model mm,Login ll) {
		mm.addAttribute("login",ll);
		
		return "index";
	}
	
	@RequestMapping(value = "/openSignUp", method =RequestMethod.GET)
	public String openSignUpPage (Model mm , Login ll) {
		
		mm.addAttribute("login",ll);
		return "signUp";
	}
	
	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public String signIn (Model mm , Login ll,HttpSession hs) {
		
		String result = loginService.signIn(ll);
		if (result.equals("Customer login successfully")) {
			hs.setAttribute("emailid", ll.getEmailid());
			return "customerHome";
		}else if (result.equals("Admin login successfully")) {
			hs.setAttribute("emailid", ll.getEmailid());
			return "adminHome";
		}
		mm.addAttribute("msg",result);
		return "index";
	}
	
	@RequestMapping(value = "/signUp",method = RequestMethod.POST)
	public String signUp(Model mm,Login ll) {
		
		String result = loginService.signUp(ll);
		mm.addAttribute("login",ll);
		mm.addAttribute("msg",result);
		if (result.equalsIgnoreCase("Account already exists") ||result.equalsIgnoreCase("Admin account cannot be created")) {
			return "signUp";
		}
		return "index";
		
	}
	
	@RequestMapping(value = "/searchAccount",method =  RequestMethod.GET)
	public String openSearchAccountPage(Model mm,Login ll) {
		mm.addAttribute("login",ll);
		return "searchAccount";
	}
	@RequestMapping(value = "/searchAccount",method =  RequestMethod.POST)
	public String searchAccount(Model mm , Login ll) {
		
		String result = loginService.searchAccount(ll);
		mm.addAttribute("login",ll);
		mm.addAttribute("msg",result);
		
		if (result.equals("Account was not found")) {
			return "searchAccount"; 
		}
		
		return "adminHome";
	}
	
	@RequestMapping (value = "/adminHome",method =  RequestMethod.GET)
	public String openAdminPage() {
		return "adminHome";
	}
	
	@RequestMapping (value = "/customerHome",method =  RequestMethod.GET)
	public String openCustomerPage() {
		return "customerHome";
	}
	@RequestMapping (value = "/listAccounts" , method = RequestMethod.GET)
	public String listAllAccounts(Model mm , Login ll) {
		List<Login> listOfAccounts = loginService.getAccounts();
//		System.out.println(listOfAccount);
		mm.addAttribute("login",listOfAccounts);
		return "listAccounts";
	}
	
	@RequestMapping (value = "/removeAccount",method = RequestMethod.POST)
	public String deleteAccount(@ModelAttribute("ll") Login ll , Model mm, RedirectAttributes rr ) {
		
		if (ll.getTypeofuser().equals("admin")) {
			
			rr.addFlashAttribute("msg","Admin Cannot Be Deleted");
			
			return "redirect:listAccounts";
		}
		String result = loginService.deleteAccount(ll);
		rr.addFlashAttribute("msg",result);
		return "redirect:listAccounts";
	}
	
	@RequestMapping (value = "/openChangePassword",method = RequestMethod.GET)
	public String changePasswordPage (Model mm,ChangePasswordFormat cpf) {
		mm.addAttribute("changepasswordformat",cpf);
		return "changePassword";
	}
	
	@RequestMapping (value = "/changePassword",method = RequestMethod.POST)
	public String changePassword (Model mm ,@ModelAttribute("changepasswordformat")ChangePasswordFormat cpf) {
		
		String result = loginService.changePassword(cpf);
		mm.addAttribute("msg",result);
		return "changePassword";
	}


}
