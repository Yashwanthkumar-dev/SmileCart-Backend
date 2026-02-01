package SmileCart.App.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import SmileCart.App.Model.Cart;
import SmileCart.App.Model.Product;
import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.CartRepository;
import SmileCart.App.Repository.ProductRepository;
import SmileCart.App.Repository.UserRepository;

@Service
public class CartService {
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private UserRepository userRepo;

	public ResponseEntity<?> addToCart(Long productId, int quantity, UserEntity user) {
		try {
			Product isProduct = productRepo.findById(productId)
					.orElseThrow(() -> new RuntimeException("product was not found"));
			Cart cart;
			Optional<Cart> existingCart = cartRepo.findByUserAndProduct(user, isProduct);
			if (existingCart.isPresent()) {
				cart = existingCart.get();
				cart.setQuantity(cart.getQuantity() + quantity);
			} else {
				cart = new Cart();
				cart.setQuantity(quantity);
				cart.setProduct(isProduct);
				cart.setUser(user);
			}
			cartRepo.save(cart);
			return ResponseEntity.status(HttpStatus.CREATED).body("add to cart success");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> viewAllCartItems(String email) {
		try {
			UserEntity user = userRepo.findByemail(email).orElseThrow(() -> new RuntimeException("User not found"));

			List<Cart> cartItems = cartRepo.findByUser(user);
			return ResponseEntity.ok(cartItems);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteAllCart() {
		try {
			cartRepo.deleteAll();
			return ResponseEntity.status(HttpStatus.OK).body("deleted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteSingleCart(Long id) {
		try {
			cartRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Cart items was deleted successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

}
