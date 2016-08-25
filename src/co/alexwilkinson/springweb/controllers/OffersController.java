package co.alexwilkinson.springweb.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OffersController {
	
	@RequestMapping("/")
	public String showHome(Model model){
		
		model.addAttribute("name", "Sasha");
		
		return "home";
	}
}
