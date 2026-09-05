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
    @Value("${admineName}")
    private String admineName;
    @Value("${adminePass}")
    private String adminePass;
    @Value("${admineEmail}")
    private String admineEmail;

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    public AppRunner(UserRepository userRepo,BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder=encoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (userRepo.existsByUsername(admineName) && userRepo.existsByEmail(admineEmail)) {
            System.out.println("Admin Already exist!");
            
            return;
        }
        String cryptedPassword=  encoder.encode(adminePass);

        UserEntity admin = UserEntity.builder()
                .username(admineName)
                .email(admineEmail)
                .password(cryptedPassword)
                .createdAt(FormatDate.CurrentDateToString())
                .role("ADMIN")
                .build();
        userRepo.save(admin);
        System.out.println("Admin Created successfully");

    }

}
