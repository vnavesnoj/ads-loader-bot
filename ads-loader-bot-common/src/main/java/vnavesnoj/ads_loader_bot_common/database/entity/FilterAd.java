package vnavesnoj.ads_loader_bot_common.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "filter_ad", uniqueConstraints = @UniqueConstraint(columnNames = {"ad_url", "filter_id"}))
public class FilterAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ad_url", referencedColumnName = "url", nullable = false)
    Ad ad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Filter filter;
}
