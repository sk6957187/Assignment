package in.nayak.foodiesapi.controller;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpaController {

    @GetMapping(value = { "/", "/{path:^(?!api$).*$}/**" })
    public String forwardReact() {
        return "forward:/index.html";
    }

}

