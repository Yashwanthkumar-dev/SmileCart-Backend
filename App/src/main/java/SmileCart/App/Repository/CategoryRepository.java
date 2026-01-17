package SmileCart.App.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SmileCart.App.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long>{
	Optional<Category> findBycategoryName(String name);

}
