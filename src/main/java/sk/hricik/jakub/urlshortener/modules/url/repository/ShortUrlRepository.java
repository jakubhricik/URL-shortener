package sk.hricik.jakub.urlshortener.modules.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;
import sk.hricik.jakub.urlshortener.modules.url.model.ShortUrl;

import java.util.List;
import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    List<ShortUrl> findShortUrlsByUser(AppUser user);

    Optional<ShortUrl> findShortUrlByOriginalUrl(String originalUrl);
}
