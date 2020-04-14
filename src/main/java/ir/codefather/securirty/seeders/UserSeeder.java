package ir.codefather.securirty.seeders;

import ir.codefather.securirty.entities.User;
import ir.codefather.securirty.entities.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSeeder implements CommandLineRunner {

    @Autowired
    UserRepo repo;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        User parsa = new User("parsa", encoder.encode("test"), "admin", "ACCESS_ADMIN");

        List<User> users = List.of(parsa);
        repo.saveAll(users);

        System.out.println("User seeded");
    }
}
