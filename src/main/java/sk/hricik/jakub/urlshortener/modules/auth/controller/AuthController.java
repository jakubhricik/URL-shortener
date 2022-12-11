package sk.hricik.jakub.urlshortener.modules.auth.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.hricik.jakub.urlshortener.modules.auth.dto.*;
import sk.hricik.jakub.urlshortener.modules.auth.service.AuthService;

import javax.validation.Valid;

@ApiOperation(value = "Account controller", notes = "Endpoints for account management")
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "Login with credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully logged in"),
            @ApiResponse(code = 400, message = "Wrong username"),
            @ApiResponse(code = 500, message = "Wrong password")
    })
    @PostMapping("/login")
    public AccountLoginResponse login(@Valid @RequestBody AccountLoginDto credentials) {
        return authService.login(credentials);
    }

    @ApiOperation(value = "Register with credentials")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Your account is opened"),
            @ApiResponse(code = 400, message = "Already exists user with account identifier: {accountId}"),
            @ApiResponse(code = 500, message = "Problem while generating password, please contact support team.")
    })
    @PostMapping()
    public AccountCreationResponse accountCreation(@Valid @RequestBody AccountCreationDto credentials) {
        return authService.createAccount(credentials);
    }
}
