package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bean.Category;
import com.bean.Product;
import com.service.CategoryService;
import com.service.ProductService;

@Controller
public class ProductController {

	
	
	@Autowired
	ProductService productService;
	@Autowired 
	CategoryService categoryService;
	
	
	@RequestMapping(value = "/addProductPage",method = RequestMethod.GET)
	public String openAddProductPage(Model mm, Product pp) {
			mm.addAttribute("product", pp);
		List<Category> listOfCategory = categoryService.findAllCategory();
			mm.addAttribute("category",listOfCategory);
		return "addProduct";
	}
	
	
	@RequestMapping(value = "/storeProductInfo",method = RequestMethod.POST)
	public String storeProductData(Model mm,Product pp) {
		String result = productService.storeProduct(pp);
		mm.addAttribute("product", pp);
		mm.addAttribute("msg", result);
		List<Category> listOfCategory = categoryService.findAllCategory();
		mm.addAttribute("category",listOfCategory);
		System.out.println(pp);  
		return "addProduct";
	}
	
	@RequestMapping(value = "/viewProduct",method = RequestMethod.GET)
	public String listAllProducts(Model mm, Product pp) {
		List<Product> listOfProducts = productService.findAllProduts();
		mm.addAttribute("product", listOfProducts);
		
		return "viewProduct";
	}
	
	@RequestMapping(value = "/removeProduct",method = RequestMethod.POST)
	public String removeProduct(RedirectAttributes rr, @ModelAttribute("product") Product pp) {
		
		String result = productService.deleteProduct(pp);
		rr.addFlashAttribute("msg",result);
		return "redirect:viewProduct";
	}
	
	
	@RequestMapping(value = "/viewProductDetailsByCustomer",method = RequestMethod.GET)
	public String viewProduct(Model mm, Product pp) {
		List<Product> listOfProducts = productService.findAllProduts();
		mm.addAttribute("products", listOfProducts);
		
		return "viewProductsByCustomer";
	}
	
}
