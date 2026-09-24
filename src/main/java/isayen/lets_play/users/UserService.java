package isayen.lets_play.users;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import isayen.lets_play.exception.ForbiddenException;
import isayen.lets_play.products.ProductsRepository;
import isayen.lets_play.utils.ApiResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final ProductsRepository prdtRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Invalid credentials: email or password is incorrect."));
        return user;
    }

    public ResponseEntity<ApiResponse<List<UserRespDTO>>> getAllUsers() {
        List<UserEntity> UserEntities = userRepo.findAll();
        List<UserRespDTO> users = UserEntities.stream().map(user -> UserRespDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .Token(null)
                .build()).toList();
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<List<UserRespDTO>>builder()
                        .data(users)
                        .status(HttpStatus.OK.value())
                        .message(null)
                        .build());
    }

    public ResponseEntity<ApiResponse<UserRespDTO>> getUserById(String id) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
        UserRespDTO userInfo = UserRespDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .Token(null)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<UserRespDTO>builder()
                        .data(userInfo)
                        .status(HttpStatus.OK.value())
                        .message(null)
                        .build());

    }

    public ResponseEntity<String> updateUser(String id, UpdateDto userDTO) {
        UserEntity user = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("User %s is updtaed", id));
    }

    @Transactional 
    public ResponseEntity<String> deleteUser(UserDetails auth, String id) {
        UserEntity user = (UserEntity) auth;
        if (id.equals(user.getId())) {
            throw new ForbiddenException("You can't Delete your self.");
        }

        UserEntity userEnt = userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));

        prdtRepo.deleteByUserId(id);

        userRepo.delete(userEnt);

        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("User %s and all their products are deleted", id));
    }

}
