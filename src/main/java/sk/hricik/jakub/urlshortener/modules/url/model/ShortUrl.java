package sk.hricik.jakub.urlshortener.modules.url.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", unique = true, nullable = false)
    private String originalUrl;

    @Column(name = "shorten_url", unique = true, nullable = false)
    private String shortenUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    private AppUser user;
}
