package isayen.lets_play.products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isayen.lets_play.utils.ApiResponse;
import jakarta.validation.Valid;

@RequestMapping ("/products")
@RestController 
public class ProductsController {
    @Autowired 
    private ProductsService prdtServ;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        return prdtServ.getAllProducts();
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductDTO product, @AuthenticationPrincipal UserDetails auth) {
        return prdtServ.saveProduct(product,auth);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@Valid @RequestBody ProductDTO product, @AuthenticationPrincipal UserDetails auth, @PathVariable String id) {
        return prdtServ.updatePrdt(auth,product, id); 
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct( @AuthenticationPrincipal UserDetails auth, @PathVariable String id) {
        return prdtServ.DeletePrdt(auth, id);
    }
}