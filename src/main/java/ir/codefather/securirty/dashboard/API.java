package ir.codefather.securirty.dashboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class API {

    @GetMapping("/hello")
    public String hello() {
        return "Hello you've login with JWT";
    }
}
