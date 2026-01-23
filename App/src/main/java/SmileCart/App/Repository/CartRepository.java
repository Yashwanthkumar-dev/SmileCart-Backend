package SmileCart.App.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SmileCart.App.Model.Cart;
import SmileCart.App.Model.Product;
import SmileCart.App.Model.UserEntity;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByProduct(Product product);

	Optional<Cart> findByUserAndProduct(UserEntity user, Product product);
	
	List<Cart> findByUser(UserEntity user);
}
