package etu.lic.tpsecuritywebcookies;

import etu.lic.tpsecuritywebcookies.entity.user.Role;
import etu.lic.tpsecuritywebcookies.entity.user.User;
import etu.lic.tpsecuritywebcookies.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadUserDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new User("LEGEAY Loris", Role.ADMIN)));
            log.info("Preloading " + repository.save(new User("VERLYNDE Audrey", Role.STUDENT)));
            log.info("Preloading " + repository.save(new User("BAHAKI Eissam", Role.STUDENT)));
            log.info("Preloading " + repository.save(new User("POINT Jonathan", Role.STUDENT)));
            log.info("Preloading " + repository.save(new User("DALVAI Aimeric-Thomas", Role.STUDENT)));
        };
    }
}