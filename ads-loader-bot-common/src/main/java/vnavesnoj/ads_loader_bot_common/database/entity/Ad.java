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
@EqualsAndHashCode(of = {"url", "hash"})
@ToString(exclude = "adBody")
@Builder
@Entity
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    Platform platform;

    @Column(unique = true, nullable = false, length = 255)
    String url;

    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "timestamp")
    Instant pushupTime;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @Column(nullable = false)
    int hash;

    @OneToOne(mappedBy = "ad", fetch = FetchType.LAZY)
    AdBody adBody;
}
