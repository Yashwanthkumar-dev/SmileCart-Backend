package SmileCart.App.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import SmileCart.App.Model.Category;
import SmileCart.App.Model.Product;
import SmileCart.App.Repository.CategoryRepository;
import SmileCart.App.Repository.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CategoryRepository categoryRepo;

	public ResponseEntity<?> createProduct(String productName, Double productPrice, String productDescription,
			String categoryName, boolean isTrending, MultipartFile productImage) {
		try {
			Optional<Product> isProduct = productRepo.findByproductName(productName);
			if (isProduct.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("product already exists");
			}
			Optional<Category> categoryOpt = categoryRepo.findBycategoryName(categoryName);

			if (categoryOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("category not found");
			}
			Category category = categoryOpt.get();
			Product newProduct = new Product();
			newProduct.setProductName(productName);
			newProduct.setProductPrice(productPrice);
			newProduct.setProductDescription(productDescription);
			newProduct.setCategory(category);
			newProduct.setTrending(isTrending);
			if (productImage != null) {
				newProduct.setImageName(productImage.getOriginalFilename());
				newProduct.setImageType(productImage.getContentType());
				newProduct.setImageData(productImage.getBytes());
			}
			productRepo.save(newProduct);

			return ResponseEntity.status(HttpStatus.CREATED).body("product successfully created");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> getAllProduct() {
		try {
			List<Product> allProduct = productRepo.findAll();
			if (allProduct.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product was not found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(allProduct);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	public ResponseEntity<?> getProductByName(String productName) {
		try {
			Optional<Product> isProduct = productRepo.findByproductName(productName);
			if (isProduct.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product was not found in this name ");
			}
			return ResponseEntity.status(HttpStatus.OK).body(isProduct);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> updateProduct(String oldProductName, String newProductName, Double productPrice,
			String productDescription, String categoryName, MultipartFile image) {

		try {
			Optional<Product> productOpt = productRepo.findByproductName(oldProductName);

			if (productOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found");
			}
			Optional<Category> categoryOpt = categoryRepo.findBycategoryName(categoryName);

			if (categoryOpt.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("category not found");
			}
			Product updateProduct = productOpt.get();
			updateProduct.setProductName(newProductName);
			updateProduct.setProductPrice(productPrice);
			updateProduct.setProductDescription(productDescription);
			updateProduct.setCategory(categoryOpt.get());
			if (image != null && !image.isEmpty()) {
				updateProduct.setImageName(image.getOriginalFilename());
				updateProduct.setImageType(image.getContentType());
				updateProduct.setImageData(image.getBytes());
			}
			productRepo.save(updateProduct);
			return ResponseEntity.status(HttpStatus.OK).body("product updated successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteAllProduct() {
		try {
			productRepo.deleteAll();
			return ResponseEntity.status(HttpStatus.OK).body("successfully deleted all product");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteProductById(Long id) {
		try {
			Optional<Product> isProduct = productRepo.findById(id);
			if (isProduct.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body("product was not found in this id");
			}
			productRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("successfully product was deleted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> getAllProductByCategoryId(Long categoryId) {
		try {
			List<Product> product = productRepo.findByCategoryId(categoryId);
			if (product.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No product was found");
			}
			return ResponseEntity.status(HttpStatus.FOUND).body(product);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
