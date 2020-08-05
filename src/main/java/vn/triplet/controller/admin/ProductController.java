package vn.triplet.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.triplet.model.Product;

import vn.triplet.service.CategoryService;
import vn.triplet.service.ProductService;

@PropertySource("classpath:messages.properties")
@Controller(value = "productControllerOfadmin")
@RequestMapping("/admin/products")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = { "", "/" })
	public String index(Model model) {
		List<Product> products = productService.loadFullProducts();
		model.addAttribute("products", products);
		return "views/admin/product/products";
	}

	@GetMapping(value = "/{id}")
	public String showProduct(@PathVariable("id") int id, Model model) {
		model.addAttribute("product", productService.findById(id));
		return "views/admin/product/product";
	}

	@GetMapping(value = "/{id}/edit")
	public String updateProduct(@PathVariable("id") int id, Model model, final RedirectAttributes redirectAttributes) {
		logger.info("edit");
		String typeCss = "error";
		String message = "Product not found!";
		Product product = productService.findById(id);
		List<vn.triplet.model.Category> list = categoryService.loadFullCategories();
		logger.info(list.size());
		model.addAttribute("categories", categoryService.loadFullCategories());
		if (product != null) {
			model.addAttribute("productForm", product);
			model.addAttribute("status", "update");
			return "views/admin/product/product-form";
		}
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/admin/products";
	}

	@GetMapping(value = "/add")
	public String createProduct(Model model) {
		model.addAttribute("newProduct", new Product());
		model.addAttribute("status", "add");
		return "views/admin/product/product-form";
	}

	@RequestMapping(value = "/saveUpdate")
	public String saveOrUpdate(@ModelAttribute("productForm") Product product, Model model,
			final RedirectAttributes redirectAttributes) {
		String typeCss = "error";
		String message = "Fail to create product!";
		
		if (productService.saveOrUpdate(product) == null) {
			redirectAttributes.addFlashAttribute("css", typeCss);
			redirectAttributes.addFlashAttribute("msg", message);

		}
		typeCss = "success";
		message = "Product is created successfully!!";
		redirectAttributes.addFlashAttribute("css", typeCss);
		redirectAttributes.addFlashAttribute("msg", message);
		return "redirect:/admin/products";

	}

	@GetMapping(value = "/{id}/delete")
	public String deleteProduct(@PathVariable("id") Integer id, final RedirectAttributes redirectAttributes) {
		return "redirect:/admin/products";
	}
}
