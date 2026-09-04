package isayen.lets_play.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/auth")
public class AuthController {
    

    @PostMapping ("/login")
    public String login() {
        return "Login endpoint";
    }

    @PostMapping ("/register")
    public String register() {  
        return "Register endpoint";
    }
    
}