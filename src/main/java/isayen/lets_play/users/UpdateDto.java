package isayen.lets_play.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateDto(
    @NotBlank (message = "The username is required.")
    @Size (min = 3, max = 20, message = "The username must contain between 3 and 20 characters.")
    String username, 
    @NotBlank(message = "The email address is required.")
    @Email(message = "The email address is invalid.")
    String email 
) {
}