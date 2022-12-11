package sk.hricik.jakub.urlshortener.modules.app;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@ApiOperation("Application Pages")
public class AppController {

    @ApiOperation(value = "Open welcome page")
    @GetMapping(value = "/")
    public String welcomeAsHTML() {
        return "index";
    }

    @ApiOperation(value = "Open help page", notes = "Returns HTML page that contains examples of API")
    @GetMapping(value = "/help" )
    public String helpAsHTML() {
        return "help";
    }

}
