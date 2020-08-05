package vn.triplet.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.bean.RateInfo;
import vn.triplet.model.Product;
import vn.triplet.model.Rate;
import vn.triplet.model.User;

import org.springframework.beans.factory.annotation.Value;

import vn.triplet.service.ProductService;
import vn.triplet.service.RateService;

@PropertySource("classpath:messages.properties")
@Controller
@RequestMapping("/ratings")
public class RatingController extends BaseController {

	@Autowired
	private RateService rateService;

	@Autowired
	private ProductService productService;

	private static final Logger logger = Logger.getLogger(RatingController.class);

	@Value("${null_value_rate}")
	private String null_value_rate;

	@Value("${rating_success}")
	private String rating_success;

	@Value("${messages.product.null}")
	private String messages_product_null;

	@PostMapping("/add")
	public String index(@ModelAttribute("newRate") RateInfo rating, @RequestParam("productId") Integer productId,
			HttpSession session, final RedirectAttributes redirectAttributes, HttpServletRequest request) {
		logger.info("save rating");
		Product product = productService.findById(productId);
		if (product == null) {
			redirectAttributes.addFlashAttribute("messages_product_null", messages_product_null);
			return "redirect:/";
		}
		User user = loadCurrentUser(request);
		if (user != null) {
			if (rating.getValue() == null) {
				redirectAttributes.addFlashAttribute("value_rate_null", null_value_rate);
			} else if (rateService.createReview(rating, product, user)) {
				redirectAttributes.addFlashAttribute("rating_success", rating_success);
			}
			return "redirect:/products/" + productId;
		}
		return "redirect:/login";

	}

}
