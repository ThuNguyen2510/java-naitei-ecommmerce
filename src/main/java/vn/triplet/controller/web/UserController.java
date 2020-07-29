package vn.triplet.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.bean.UserInfo;
import vn.triplet.model.User;
import vn.triplet.service.UserService;
import vn.triplet.validate.UserValidation;

@PropertySource("classpath:messages.properties")
@Controller(value = "users")
public class UserController {

	@Autowired
	private UserService userService;

	@Value("${messages.login}")
	private String msg_login;

	@Value("${messages.error_mail}")
	private String msg_error_mail;

	@Value("${messages.register}")
	private String msg_register;

	@Value("${Login_error}")
	private String Login_error;

	private static final int ADMIN = 0;
	private static final int USER = 1;

	@RequestMapping("users/{id}")
	public String redering(@PathVariable("id") int id, Model model) {
		return "views/web/profile";
	}

	@RequestMapping("/welcome")
	public String welcome(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpServletRequest request, Model model, final RedirectAttributes redirectAttributes) {
		User user = userService.findByEmailAndPassword(email, password.trim());
		if (user != null) {
			HttpSession session = request.getSession();
			redirectAttributes.addFlashAttribute("loginsuccess", msg_login);
			session.setAttribute("msg", user.getName().toUpperCase());
			session.setAttribute("currentUser", user.getId());
			if (user.getRole().toString().equals("ADMIN")) {
				session.setAttribute("roleUser", ADMIN);
			} else {
				session.setAttribute("roleUser", USER);
			}

			return "redirect:/";
		} else {
			model.addAttribute("error", Login_error);
		}
		return "views/web/layout/signin-modal";
	}

	@RequestMapping("/registerProcess")
	public String register(@ModelAttribute("user") UserInfo userInfo, BindingResult result, Model model,
			final RedirectAttributes redirectAttributes) {
		UserValidation userVali = new UserValidation();
		userVali.validate(userInfo, result);
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			return "views/web/layout/signup-modal";
		} else if (userService.createUser(userInfo.convertToUser()) == false) {
			model.addAttribute("error", msg_error_mail);
			return "views/web/layout/signup-modal";
		}
		userService.createUser(userInfo.convertToUser());
		redirectAttributes.addFlashAttribute("registersuccess", msg_register);
		return "views/web/layout/signin-modal";
	}

}
