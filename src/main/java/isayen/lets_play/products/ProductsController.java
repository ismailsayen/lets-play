package isayen.lets_play.products;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.Valid;

@RequestMapping ("/products")
@RestController 
public class ProductsController {
    @Autowired 
    private ProductsService prdtServ;
    
    @PostMapping
    public String createProduct(@Valid @RequestBody ProductDTO product,@AuthenticationPrincipal UserDetails auth) {
        System.out.println(product.name());
        System.out.println(product.price());
        
        return prdtServ.saveProduct(product,auth);
    }

    @GetMapping
    public String getAllProducts() {
        return "List of products";
    }
    
    @GetMapping("/{id}")
    public String getProductById(@PathVariable String id) {
        return "Get product by ID endpoint";
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable String id) {
        return "Update product by ID endpoint"; 
    }


    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable String id) {
        return "Delete product by ID endpoint";
    }
}