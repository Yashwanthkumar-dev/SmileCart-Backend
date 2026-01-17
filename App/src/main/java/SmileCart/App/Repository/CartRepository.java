package SmileCart.App.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SmileCart.App.Model.Cart;
import SmileCart.App.Model.Product;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByProduct (Product product);

}
