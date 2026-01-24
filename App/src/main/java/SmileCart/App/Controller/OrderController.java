package SmileCart.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.Service.OrderService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/place")
	public ResponseEntity<?> placeOrder(Authentication auth) {
		if (auth == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user was not logged in");
		}
		String email = auth.getName();
		return orderService.placeOrder(email);

	}
	
	@GetMapping("/my-orders")
	public ResponseEntity<?> getMyOrders(Authentication auth) {
	    return orderService.getMyOrders(auth.getName());
	}
}
