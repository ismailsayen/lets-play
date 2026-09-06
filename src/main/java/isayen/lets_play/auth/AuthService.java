package isayen.lets_play.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import isayen.lets_play.config.JwtService;
import isayen.lets_play.exception.DuplicateResourceException;
import isayen.lets_play.users.UserEntity;
import isayen.lets_play.users.UserRepository;
import isayen.lets_play.users.UserRespDTO;
import isayen.lets_play.utils.ApiResponse;
import isayen.lets_play.utils.FormatDate;

@Service 
public class AuthService {

    private final AuthenticationManager authenticationManager;
    @Autowired 
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private JwtService jwtSer;

    AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<ApiResponse<UserRespDTO>> registerUser(RegisterDTO userDTO) {
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

        UserRespDTO userInfo = UserRespDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole())
        .createdAt(formattedString)
        .Token(jwtSer.generateToken(user.getId()))
        .build();

        return  ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<UserRespDTO>builder()
            .data(userInfo)
            .status(HttpStatus.CREATED.value())
            .message("User Registred successfully.")
            .build()
        );
    }

    public ResponseEntity<ApiResponse<UserRespDTO>> loginUser(LoginDTO req) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );

        UserEntity user = userRepository.findByEmail(req.email()).get();

        UserRespDTO userInfo = UserRespDTO.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .Token(jwtSer.generateToken(user.getId()))
        .build();

        return  ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.<UserRespDTO>builder()
            .data(userInfo)
            .status(HttpStatus.OK.value())
            .message("User Registred successfully.")
            .build()
        );
    }
}
