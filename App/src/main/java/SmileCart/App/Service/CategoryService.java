package SmileCart.App.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import SmileCart.App.Model.Category;
import SmileCart.App.Repository.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository catRepo;

	public ResponseEntity<?> getAllCategory() {
		try {
			List<Category> allCat = catRepo.findAll();
			if (allCat.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("categories was not found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(allCat);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> getAllCategoryById(Long id) {
		try {
			Optional<Category> isCat = catRepo.findById(id);
			if (isCat.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category are not found");
			}
			return ResponseEntity.status(HttpStatus.OK).body(isCat);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> createCategory(String categoryName) {
		try {
			Optional<Category> isCat = catRepo.findBycategoryName(categoryName);
			if (isCat.isPresent()) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("category was already exists");
			}
			Category newcategory = new Category();
			newcategory.setCategoryName(categoryName);
			catRepo.save(newcategory);
			return ResponseEntity.status(HttpStatus.OK).body("category was successfully created");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> updateCategory(String oldCategoryName, String newCategoryName) {
		try {
			Optional<Category> oldCat = catRepo.findBycategoryName(oldCategoryName);
			if (oldCat.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category are not found");
			}
			Category updateCategory = oldCat.get();
			updateCategory.setCategoryName(newCategoryName);
			return ResponseEntity.status(HttpStatus.OK).body("category was successfully updated");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteAllCategory() {
		try {
			catRepo.deleteAll();
			return ResponseEntity.status(HttpStatus.OK).body("Deleted all categories successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	public ResponseEntity<?> deleteCategoryById(Long id) {
		try {
			Optional<Category> cat = catRepo.findById(id);
			if (cat.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("category was not found");
			}
			catRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body(" Successfully categories was deleted ");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
