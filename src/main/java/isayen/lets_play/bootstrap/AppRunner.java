package isayen.lets_play.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import isayen.lets_play.users.UserEntity;
import isayen.lets_play.users.UserRepository;
import isayen.lets_play.utils.FormatDate;

@Component
public class AppRunner implements ApplicationRunner {
    @Value("${adminName}")
    private String adminName;
    @Value("${adminPass}")
    private String adminPass;
    @Value("${adminEmail}")
    private String adminEmail;

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    public AppRunner(UserRepository userRepo,BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder=encoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRepo.existsByUsername(adminName) && userRepo.existsByEmail(adminEmail)) {
            System.out.println("Admin Already exist!");
            
            return;
        }
        String cryptedPassword=  encoder.encode(adminPass);

        UserEntity admin = UserEntity.builder()
                .username(adminName)
                .email(adminEmail)
                .password(cryptedPassword)
                .createdAt(FormatDate.CurrentDateToString())
                .role("ADMIN")
                .build();
        userRepo.save(admin);
        System.out.println("Admin Created successfully");

    }

}
