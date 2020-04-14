package ir.codefather.securirty.dashboard;

import ir.codefather.securirty.entities.User;
import ir.codefather.securirty.entities.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Admin {


    @GetMapping("/admin")
    public String indexAdmin(){
        return "this is admin page";
    }

    @Autowired
    UserRepo repo;

    @GetMapping("/users")
    public List<User> getUsers(){
        return repo.findAll();
    }

}
