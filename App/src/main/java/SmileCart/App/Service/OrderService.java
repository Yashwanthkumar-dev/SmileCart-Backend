package SmileCart.App.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import SmileCart.App.Model.Cart;
import SmileCart.App.Model.OrderEntity;
import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.CartRepository;
import SmileCart.App.Repository.OrderRepository;
import SmileCart.App.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private OrderRepository orderRepo;

	@Transactional
	public ResponseEntity<?> placeOrder(String email) {
		try {
			UserEntity user = userRepo.findByemail(email).orElseThrow(() -> new RuntimeException("User was not found"));

			List<Cart> cartItems = cartRepo.findByUser(user);
			if (cartItems.isEmpty()) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cart was empty");
			}

			for (Cart items : cartItems) {
				OrderEntity order = new OrderEntity();
				order.setUser(user);
				order.setProduct(items.getProduct());
				order.setQuantity(items.getQuantity());
				double totalPrice = items.getProduct().getProductPrice() * items.getQuantity();
				order.setTotalPrice(totalPrice);
				order.setOrderDate(LocalDateTime.now());
				order.setSuccess("SUCESS");
				orderRepo.save(order);
			}
			cartRepo.deleteAll(cartItems);
			return ResponseEntity.status(HttpStatus.OK).body("order have placed");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}
	
	public ResponseEntity<?> getMyOrders(String email) {
		try {
			UserEntity user = userRepo.findByemail(email).orElseThrow(() -> new RuntimeException("User not found"));
	    List<OrderEntity> orders = orderRepo.findByUser(user); 
	    return ResponseEntity.status(HttpStatus.OK).body(orders);
		} catch (Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	    
	}
	

}
