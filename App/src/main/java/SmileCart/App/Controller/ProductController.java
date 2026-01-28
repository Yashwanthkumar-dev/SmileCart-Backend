package SmileCart.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import SmileCart.App.Service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin("*")
public class ProductController {
	@Autowired
	private ProductService productService;

	// create product
	@PostMapping

	public ResponseEntity<?> createProduct(@RequestParam String productName, @RequestParam Double productPrice,
			@RequestParam String productDescription, @RequestParam String categoryName,
			@RequestParam boolean isTrending, @RequestParam MultipartFile productImage) {
		return productService.createProduct(productName, productPrice, productDescription, categoryName, isTrending,
				productImage);
	}

	// get all product
	@GetMapping
	public ResponseEntity<?> getAllProduct() {
		return productService.getAllProduct();
	}

	// get product by name
	@GetMapping("/name")
	public ResponseEntity<?> getProductByName(@RequestParam String productName) {
		return productService.getProductByName(productName);
	}

	// update product
	@PutMapping
	public ResponseEntity<?> updateProduct(@RequestParam String oldProductName, @RequestParam String newProductName,
			@RequestParam Double productPrice, @RequestParam String productDescription,
			@RequestParam String categoryName, @RequestParam MultipartFile image) {
		return productService.updateProduct(oldProductName, newProductName, productPrice, productDescription,
				categoryName, image);
	}

	// delete all product
	@DeleteMapping
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> deleteAllProduct() {
		return productService.deleteAllProduct();
	}

	// delete product by id
	@DeleteMapping("delete-id/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> deleteProductById(@PathVariable Long id) {
		return productService.deleteProductById(id);
	}

	// get all products by category id
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<?> getAllProductByCategoryId(@PathVariable Long categoryId) {
		return productService.getAllProductByCategoryId(categoryId);
	}
}
