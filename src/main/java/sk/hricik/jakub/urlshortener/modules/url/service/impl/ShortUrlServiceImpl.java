package sk.hricik.jakub.urlshortener.modules.url.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.hricik.jakub.urlshortener.modules.ApiException;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.common.model.Role;
import sk.hricik.jakub.urlshortener.modules.common.service.AppUserService;
import sk.hricik.jakub.urlshortener.modules.common.service.RoleService;
import sk.hricik.jakub.urlshortener.modules.url.dto.RegisterUrlDto;
import sk.hricik.jakub.urlshortener.modules.url.dto.ShortUrlResponse;
import sk.hricik.jakub.urlshortener.modules.url.model.ShortUrl;
import sk.hricik.jakub.urlshortener.modules.url.repository.ShortUrlRepository;
import sk.hricik.jakub.urlshortener.modules.url.service.ShortUrlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShortUrlServiceImpl implements ShortUrlService {

    private final ShortUrlRepository urlRepository;
    private final AppUserService appUserService;
    private final RoleService roleService;
    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;


    @Override
    public ShortUrlResponse registerUrl(RegisterUrlDto registerUrlDto) {

        AppUser user = appUserService.getLoggedUser();

        try {
            new URL(registerUrlDto.getUrl());
        } catch (MalformedURLException e) {
            throw ApiException.createInvalidParams("Url is not valid");
        }

        if (registerUrlDto.getRedirectType() == null) {
            registerUrlDto.setRedirectType((short) 302);
        }
        else if (registerUrlDto.getRedirectType() < 301 || registerUrlDto.getRedirectType() > 302)
            throw ApiException.createInvalidParams("Redirect Type must be 301 or 302");


        Optional<ShortUrl> url = urlRepository.findShortUrlByOriginalUrl(registerUrlDto.getUrl());
        if (url.isPresent())
            throw new ApiException(ApiException.FaultType.OBJECT_ALREADY_EXISTS, "Given URL is already shorten: " + getShortUrlById(url.get().getId()));

        ShortUrl shortUrl = ShortUrl.builder()
                .originalUrl(registerUrlDto.getUrl())
                .redirectType(registerUrlDto.getRedirectType())
                .user(user)
                .build();
        shortUrl = urlRepository.save(shortUrl);

        return ShortUrlResponse.builder()
                .shortUrl(getShortUrlById(shortUrl.getId()))
                .build();
    }

    @Override
    public void redirectToUrl(String shortenUrlCode) {
        int urlId = getIdOfShortenUrlCode(shortenUrlCode);
        Optional<ShortUrl> shortUrl = urlRepository.findById(urlId);
        if (shortUrl.isEmpty())
            throw ApiException.createObjectNotFound("There is no such URL with code: " + shortenUrlCode);
        shortUrl.get().setNumberOfCalls(shortUrl.get().getNumberOfCalls() + 1);

        httpServletResponse.setHeader("Location", shortUrl.get().getOriginalUrl());
        httpServletResponse.setStatus(shortUrl.get().getRedirectType());
    }

    @Override
    public Map<String, Integer> getStatisticsByAccountId(String accountId) {
        AppUser user = appUserService.getLoggedUser();
        Role adminRole = roleService.getRole("ROLE_ADMIN");
        if (user.getRoles().contains(adminRole)) {
            if (!user.getAccountId().equals(accountId)) {
                user = appUserService.getUser(accountId);
                return getStatisticsByUser(user);
            }
        } else if (!user.getAccountId().equals(accountId))
            throw new ApiException(ApiException.FaultType.ACCESS_DENIED, "You have no permissions to get stats of " + accountId);

        return getStatisticsByUser(user);
    }

    private Map<String, Integer> getStatisticsByUser(AppUser user) {
        Map<String, Integer> results = new HashMap<>();
        urlRepository.findShortUrlsByUser(user)
                .forEach(url ->
                        results.put(url.getOriginalUrl(), url.getNumberOfCalls())
                );
        return results;
    }

    private String getShortUrlById(int id) {
        char[] map = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

        StringBuilder shortUrl = new StringBuilder();

        while (id > 0) {
            shortUrl.append(map[id % 62]);
            id = id / 62;
        }

        return httpServletRequest.getRequestURL()
                .toString()
                .replace("register", "sh_") + shortUrl.reverse();
    }

    private int getIdOfShortenUrlCode(String shortenUrlCode) {
        int id = 0;

        for (int i = 0; i < shortenUrlCode.length(); i++) {
            if ('a' <= shortenUrlCode.charAt(i) && shortenUrlCode.charAt(i) <= 'z')
                id = id * 62 + shortenUrlCode.charAt(i) - 'a';
            if ('A' <= shortenUrlCode.charAt(i) && shortenUrlCode.charAt(i) <= 'Z')
                id = id * 62 + shortenUrlCode.charAt(i) - 'A' + 26;
            if ('0' <= shortenUrlCode.charAt(i) && shortenUrlCode.charAt(i) <= '9')
                id = id * 62 + shortenUrlCode.charAt(i) - '0' + 52;
        }
        return id;
    }


}
