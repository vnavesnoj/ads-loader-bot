package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vnavesnoj.ads_loader_bot_common.constant.Platform;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"url", "hash"})
@ToString(exclude = "adBody")
@Builder
@Entity
@Table(name = "ad")
public class Ad {

    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    Platform platform;

    @Column(unique = true, nullable = false, length = 255)
    String url;

    @Column(nullable = false, length = 255)
    String title;

    @Column(columnDefinition = "timestamp")
    Instant pushupTime;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @Column(nullable = false)
    int hash;

    @OneToOne(mappedBy = "ad", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    AdBody adBody;
}
