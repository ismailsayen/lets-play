package isayen.lets_play.products;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends MongoRepository<Product, String> {
    void deleteByUserId(String userId);
}