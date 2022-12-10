package sk.hricik.jakub.urlshortener.modules.url.model;

import lombok.*;
import sk.hricik.jakub.urlshortener.modules.common.model.AppUser;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "original_url", unique = true, nullable = false)
    private String originalUrl;

    @Min(301)
    @Max(302)
    @Column(name = "redirect_type", nullable = false)
    private short redirectType = 302;

    @Min(0)
    @Column(name = "cals")
    private long numberOfCalls = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;
}
