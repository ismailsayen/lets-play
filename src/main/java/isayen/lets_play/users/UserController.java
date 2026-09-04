package isayen.lets_play.users;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users") 
public class UserController{

    @GetMapping 
    public String getAllUsers() {
        return "List of users";
    }

    @GetMapping ("/{id}") 
    public String getUser(@PathVariable String id) {
        return "User with ID: " + id;
    }

    @PutMapping("/{id}") 
    public String UpdatePersonalInfo(@PathVariable String id) {
        return "Update User with ID: " + id;
    }

    @DeleteMapping ("/{id}") 
    public String deleteUser(@PathVariable String id) {
        return "Delete User with ID: " + id;
    }
}