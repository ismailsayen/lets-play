package isayen.lets_play.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import isayen.lets_play.exception.DuplicateResourceException;
import isayen.lets_play.users.UserEntity;
import isayen.lets_play.users.UserRepository;
import isayen.lets_play.users.UserReqDTO;
import isayen.lets_play.users.UserRespDTO;
import isayen.lets_play.utils.ApiResponse;
import isayen.lets_play.utils.FormatDate;

@Service 
public class AuthService {

    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public ResponseEntity<ApiResponse<UserRespDTO>> registerUser(UserReqDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
                throw new DuplicateResourceException("Username already exists.");
        }

        if (userRepository.existsByEmail(userDTO.email())) {
                throw new DuplicateResourceException("Email already exists.");
        }
        String cryptedPassword=  encoder.encode(userDTO.password());
        String formattedString=FormatDate.CurrentDateToString();
        
        UserEntity user =UserEntity.builder()
                        .username(userDTO.username())
                        .email(userDTO.email())
                        .password(cryptedPassword)
                        .role("USER")
                        .createdAt(formattedString)
                        .build();
        userRepository.save(user);
        
        UserRespDTO resp = UserRespDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole())
        .createdAt(formattedString)
        .build();

        return  ResponseEntity.status(HttpStatus.CREATED).body(
        ApiResponse.<UserRespDTO>builder()
            .status(HttpStatus.CREATED.value())
            .message("User registered successfully.")
            .data(resp)
        .build()
        );
    }
}
