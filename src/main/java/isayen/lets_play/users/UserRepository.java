package isayen.lets_play.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository 
public interface UserRepository extends MongoRepository<UserEntity, String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

}