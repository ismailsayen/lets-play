package isayen.lets_play.products;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor 
public class ProductsService {
    
    private final ProductsRepository prdtRepo;

    public String saveProduct(ProductDTO product, UserDetails auth) {
       ProductEnti
    }

    
}