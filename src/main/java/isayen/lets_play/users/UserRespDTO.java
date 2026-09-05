package isayen.lets_play.users;

import lombok.Builder;

@Builder
public record UserRespDTO(
     String id,
     String username,
     String email,
     String role,
     String createdAt
){}
