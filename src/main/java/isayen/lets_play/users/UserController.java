package isayen.lets_play.users;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import isayen.lets_play.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users") 
@RequiredArgsConstructor 
@PreAuthorize("hasRole('ADMIN')")
public class UserController{

    private final UserService userSer;

    @GetMapping 
    public ResponseEntity<ApiResponse<List<UserRespDTO>>> getAllUsers() {
        return userSer.getAllUsers();
    }

    @GetMapping ("/{id}") 
    public ResponseEntity<ApiResponse<UserRespDTO>> getUser(@PathVariable String id) {
        return userSer.getUserById(id);
    }

    @PutMapping("/{id}") 
    public ResponseEntity<String> UpdatePersonalInfo(@PathVariable String id, @Valid @RequestBody UpdateDto userDTO) {
        return userSer.updateUser(id,userDTO);
    }

    @DeleteMapping ("/{id}") 
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails auth,@PathVariable String id) {
        return userSer.deleteUser(auth,id);
    }

}