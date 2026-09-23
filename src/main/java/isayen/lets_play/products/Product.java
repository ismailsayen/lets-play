package isayen.lets_play.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Builder 
@Document (collection = "products")
public class Product {

    @Id 
    private String id;
    private String name;
    private String description;
    private Double price;
    private String userId;
    private String createdAt;
    
}