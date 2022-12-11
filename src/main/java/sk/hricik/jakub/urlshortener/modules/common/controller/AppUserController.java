package sk.hricik.jakub.urlshortener.modules.common.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sk.hricik.jakub.urlshortener.modules.common.dto.AppUserDto;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.url.service.ShortUrlService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(headers = HttpHeaders.AUTHORIZATION)
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final ShortUrlService urlService;

    @ApiOperation(value = "Get all users", notes = "Only for users with ADMIN permissions")
    @GetMapping(value = "/users")
    @PreAuthorize(value = "hasAnyAuthority('ROLE_ADMIN')")
    public List<AppUserDto> getUsers() {
        return appUserService.getUsers();
    }

    @ApiOperation(value = "Get info about all urls by account id")
    @ApiResponse(code = 400, message = "You have no permissions to get stats of {accountId}")
    @GetMapping(value = "/statistics/{accountId}")
    public Map<String, Integer> getStatisticsByAccountId(
            @PathVariable
            @ApiParam(name = "accountId", value = "Id of user", type = "string", example = "jakubhricik", required = true)
            String accountId
    ) {
        return urlService.getStatisticsByAccountId(accountId);
    }

}
