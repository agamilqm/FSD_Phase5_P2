package com.controller;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Login;
import com.bean.Orders;
import com.bean.Product;
import com.service.OrdersService;
import com.service.ProductService;

@Controller
public class OrdersController {

	@Autowired
	ProductService productService;
	@Autowired
	OrdersService ordersService;
	
	@RequestMapping(value = "/viewCustomerPurchase",method = RequestMethod.GET)
	public String listAllCustomerOrders(Model mm) {
		
		List<Orders> listOfOrders = ordersService.viewAllOrderDetails();
		mm.addAttribute("orders", listOfOrders);
		return "viewCustomerPurchase";
	}
	

	@RequestMapping(value = "/viewSpecificCustomerPurchase" , method = RequestMethod.GET)
	public String listSpecificCustomerOrders(Model mm, Orders order , HttpSession hs) {
		String emailid = (String) hs.getAttribute("emailid");
		
		order.setEmailid(emailid);
		
		List<Orders> listOfOrders = ordersService.viewSpecificCustomerOrders(order);
		mm.addAttribute("orders", listOfOrders);
		return "viewSpecificCustomerPurchase";
	}
	
	@RequestMapping (value = "/deletePurchase" , method = RequestMethod.POST)
	public String deletePurchase(RedirectAttributes rr , Model mm ,@ModelAttribute("orders") Orders order) {
		String result = ordersService.deleteOrder(order);
		rr.addFlashAttribute("msg",result);
		return "redirect:viewSpecificCustomerPurchase";
	}
	
	@RequestMapping(value = "/purchaseProduct" , method = RequestMethod.POST)
	public String placeOrder(Model mm, HttpSession hs ,@ModelAttribute("product") Product pp ,Orders order) {
//		System.out.println("Pid is "+pp.getPid());
		String emailid = (String) hs.getAttribute("emailid");
		
		order.setEmailid(emailid);
		order.setOrderplaced(LocalDate.now());
		order.setProductid(pp.getPid());
		
		String productResult = productService.decrementQty(pp.getPid());
		List<Product> listOfProducts = productService.findAllProduts();

		if (productResult.equals("Product doesn't exist") || productResult.equals("Out of Stock")) {
			mm.addAttribute("msgProduct", productResult);
			mm.addAttribute("products", listOfProducts);
			return "viewProductsByCustomer";
		}
		String result = ordersService.placeOrder(order);
		
		
		mm.addAttribute("products", listOfProducts);
		mm.addAttribute("msg", result);
		mm.addAttribute("msgProduct", productResult);
		
		return "viewProductsByCustomer";
	}	
}
