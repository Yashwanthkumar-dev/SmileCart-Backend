package SmileCart.App.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.Model.OrderEntity;
import SmileCart.App.Model.Product;
import SmileCart.App.Model.UserEntity;
import SmileCart.App.Repository.OrderRepository;
import SmileCart.App.Repository.ProductRepository;
import SmileCart.App.Repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminDashboard {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private ProductRepository productRepo;

	@GetMapping("/dashboard-stats")
	public ResponseEntity<?> getDashboardStats() {
		try {
			Long userCount = userRepo.count();
			Long productCount = productRepo.count();
			Long orderCount = orderRepo.count();
			double totalRevenue = 0.0;
			List<OrderEntity> allOrder = orderRepo.findAll();
			for (OrderEntity order : allOrder) {
				totalRevenue += order.getTotalPrice();
			}
			return ResponseEntity.ok(Map.of("totalUser", userCount, "totalProduct", productCount, "totalOrder",
					orderCount, "totalRevenue", totalRevenue));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@GetMapping("/all-orders")
	public ResponseEntity<?> getAllOrder() {
		try {
			List<OrderEntity> allOrders = orderRepo.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(allOrders);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/all-products")
	public ResponseEntity<?> getAllProduct() {
		try {
			List<Product> allProduct = productRepo.findAll();
			return ResponseEntity.status(HttpStatus.OK).body(allProduct);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping("/total-user")
	public ResponseEntity<?> getAllUserCount() {
		try {
			Long allUser = userRepo.count();
			return ResponseEntity.status(HttpStatus.OK).body( allUser);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
