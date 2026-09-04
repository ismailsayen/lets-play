package isayen.lets_play.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isayen.lets_play.users.UserDTO;
import isayen.lets_play.utils.ApiResponse;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/auth")
public class AuthController {
    @Autowired 
    private AuthService authService;

    @PostMapping ("/login")
    public String login() {
        return "Login endpoint";
    }

    @PostMapping ("/register")
    public ApiResponse<UserDTO> register(@Valid @RequestBody UserDTO userDTO) {  
        return authService.registerUser(userDTO);
    }
    
}