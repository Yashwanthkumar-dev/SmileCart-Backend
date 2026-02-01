package SmileCart.App.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import SmileCart.App.Service.CategoryService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
	 @Autowired
	    private CategoryService catService;

	    // get all category
	    @GetMapping
	    public ResponseEntity<?> getAllcategory() {
	        return catService.getAllCategory();
	    }

	    // get category by id
	    @GetMapping("/category/{id}")
	    public ResponseEntity<?> getAllCategoryById(@PathVariable Long id) {
	        return catService.getAllCategoryById(id);
	    }

	    // create category
	    @PostMapping
	    public ResponseEntity<?> createCategory(@RequestParam String categoryName) {
	        return catService.createCategory(categoryName);
	    }

	    // update category
	    @PutMapping
	    public ResponseEntity<?> updateCategory(@RequestParam String oldCategoryName,
	            @RequestParam String newCategoryName) {
	        return catService.updateCategory(oldCategoryName, newCategoryName);
	    }

	    // delete all category
	    @DeleteMapping
	    public ResponseEntity<?> deleteAllCategory() {
	        return catService.deleteAllCategory();
	    }

	    // delete category by id
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
	        return catService.deleteCategoryById(id);
	    }
}
