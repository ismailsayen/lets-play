package isayen.lets_play.products;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import isayen.lets_play.exception.ForbiddenException;
import isayen.lets_play.users.UserEntity;
import isayen.lets_play.utils.ApiResponse;
import isayen.lets_play.utils.FormatDate;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductsService {
    
    private final ProductsRepository prdtRepo;
    private static final String formattedString = FormatDate.CurrentDateToString();


    public ResponseEntity<ApiResponse<Product>> saveProduct(ProductDTO productDTO, UserDetails auth) {
        UserEntity user = (UserEntity) auth;
      Product prdt = Product.builder()
                .name(productDTO.name())
                .description(productDTO.description())
                .price(Double.valueOf(productDTO.price()))
                .userId(user.getId()) 
                .createdAt(formattedString) 
                .build();
        prdtRepo.save(prdt);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<Product>builder()
            .data(prdt)
            .status(HttpStatus.CREATED.value())
            .message("Product Created")
            .build()
        );
    }


    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products=prdtRepo.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.<List<Product>>builder()
            .data(products)
            .status(HttpStatus.OK.value())
            .message(null)
            .build()
        );
    }


    public ResponseEntity<String> updatePrdt(UserDetails auth, ProductDTO productDTO,String id) {

        Product existingProduct = prdtRepo.findById(id)
        .orElseThrow(()-> new NoSuchElementException("Product not found."));
        UserEntity user = (UserEntity) auth;

        if(!existingProduct.getUserId().equals(user.getUsername())){
            throw new ForbiddenException("You can only update your Product.");
        }

        existingProduct.setName(productDTO.name());
        existingProduct.setDescription(productDTO.description());
        existingProduct.setPrice(Double.valueOf(productDTO.price()));

        prdtRepo.save(existingProduct);

       return ResponseEntity.status(HttpStatus.OK).body(String.format("Product %s is updtaed",id ));
    }


	public ResponseEntity<String> DeletePrdt(UserDetails auth, String id) {
		Product existingProduct = prdtRepo.findById(id)
        .orElseThrow(()-> new NoSuchElementException("Product not found."));
        UserEntity user = (UserEntity) auth;
        if(!existingProduct.getUserId().equals(user.getUsername())){
            throw new ForbiddenException("You can only Delete your Product.");
        }
        prdtRepo.delete(existingProduct);      
       return ResponseEntity.status(HttpStatus.OK).body(String.format("Product %s is Deleted",id ));

	}

    
}