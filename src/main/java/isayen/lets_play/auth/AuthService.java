package isayen.lets_play.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isayen.lets_play.users.UserDTO;
import isayen.lets_play.users.UserRepository;
import isayen.lets_play.utils.ApiResponse;

@Service 
public class AuthService {

    @Autowired 
    private UserRepository userRepository;

    public ApiResponse<UserDTO> registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.username())) {
            return ApiResponse.<UserDTO>builder()
                    .status(400)
                    .message("Username already exists.")
                    .build();
        }

        userDTO = userDTO.withoutPassword();
        return ApiResponse.<UserDTO>builder()
                .status(201)
                .message("User registered successfully.")
                .data(userDTO)
                .build();
    }
}
