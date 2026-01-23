package SmileCart.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.DTO.addToCartDTO;
import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.UserRepository;
import SmileCart.App.Service.CartService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/add")
	public ResponseEntity<?> addToCart(@RequestBody addToCartDTO dto, Authentication auth) {
		String currentEmail = auth.getName();

		UserEntity user = userRepo.findByemail(currentEmail)
				.orElseThrow(() -> new RuntimeException("User was not found"));
		return cartService.addToCart(dto.getProductId(), dto.getQuantity(), user);

	}

	@GetMapping
	public ResponseEntity<?> viewAllCartItems(Authentication auth) {
		String email = auth.getName();
		return cartService.viewAllCartItems(email);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> deleteCartById(@PathVariable Long id) {
		return cartService.deleteSingleCart(id);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteAllCart() {
		return cartService.deleteAllCart();

	}

}