package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.masai.global.GlobalData;
import com.masai.model.Product;
import com.masai.service.ProductService;

@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/addToCart/{id}")
	public String addToCart(@PathVariable Integer id) {
		
		GlobalData.cart.add(productService.getProductById(id)); 
		return "redirect:/shop";
	}

	@GetMapping("/cart")
	public String cart(Model m) {
		
		m.addAttribute("cartCount",GlobalData.cart.size());
		m.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		m.addAttribute("cart",GlobalData.cart);
		return "cart";
	}
	
	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
	
		GlobalData.cart.remove(index);
		
		return "redirect:/cart";
	}
	
	@GetMapping("/checkout")
	public String checkout(Model model) {
		
		model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute(model);
		
		return "checkout";
	}
	
}
