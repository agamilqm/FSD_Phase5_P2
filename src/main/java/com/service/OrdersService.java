package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bean.Login;
import com.bean.Orders;
import com.repository.OrdersRepository;

@Service
public class OrdersService {

	@Autowired
	OrdersRepository ordersRepository;
	
	public String placeOrder(Orders order) {
		ordersRepository.save(order);
		return "Order Placed successfully";
	}
	
	public List<Orders> viewAllOrderDetails() {
		return ordersRepository.findAll();
	}
	
	public List<Orders> viewSpecificCustomerOrders(Orders order){
		
		List<Orders> listOfOrders = ordersRepository.getListOfOrdersUsingCustomerId(order.getEmailid());
		return listOfOrders;
	}
	
	public String deleteOrder(Orders order) {
		
		if (!ordersRepository.existsById(order.getOrderid())) {
			return "Purchase doesn't exist"; 
		}
		ordersRepository.deleteById(order.getOrderid());
		return "Purchase Deleted Successfully";
	}
}
