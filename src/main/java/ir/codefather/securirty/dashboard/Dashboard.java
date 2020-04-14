package ir.codefather.securirty.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Dashboard {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/manage")
    public String manage() {
        return "manage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
