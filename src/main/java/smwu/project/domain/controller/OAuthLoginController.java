package smwu.project.domain.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OAuthLoginController {
    @GetMapping("/users/login-page")
    public String loginPage() {
        return "login";
    }
}

