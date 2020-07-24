package vn.triplet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.triplet.service.UserService;

@Controller
@RequestMapping(value = "/")
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@RequestMapping(value = "/")
	public String rendering() {
		userService.loadUsers();
		return "views/index";
	}
	
}
