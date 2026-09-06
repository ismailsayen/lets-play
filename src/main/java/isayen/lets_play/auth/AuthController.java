package isayen.lets_play.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isayen.lets_play.users.UserRespDTO;
import isayen.lets_play.utils.ApiResponse;
import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/auth")
public class AuthController {
    @Autowired 
    private AuthService authService;

    @PostMapping ("/login")
    public ResponseEntity<ApiResponse<UserRespDTO>> login(@Valid @RequestBody LoginDTO req) {
        return authService.loginUser(req);
    }

    @PostMapping ("/register")
    public ResponseEntity<ApiResponse<UserRespDTO>> register(@Valid @RequestBody RegisterDTO userDTO) {  
        return authService.registerUser(userDTO);
    }
    
}