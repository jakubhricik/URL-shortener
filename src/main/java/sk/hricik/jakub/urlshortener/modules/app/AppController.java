package sk.hricik.jakub.urlshortener.modules.app;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AppController {

    @GetMapping(value = "/")
    public String welcomeAsHTML() {
        return "index";
    }


    @GetMapping(value = "/help" )
    public String helpAsHTML() {
        return "help";
    }

}
