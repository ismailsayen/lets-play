package isayen.lets_play.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
    @NotBlank(message = "The email address is required.")
    @Email(message = "The email address is invalid.")
    String email, 
    @NotBlank(message = "The password is required.")
    @Size(min = 6, max = 100, message = "The password must contain between 6 and 100 characters.")
    String password
) {  }
