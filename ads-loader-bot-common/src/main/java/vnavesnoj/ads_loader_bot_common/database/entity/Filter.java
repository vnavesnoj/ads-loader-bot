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
@EqualsAndHashCode(exclude = "user")
@Builder
@Entity
@Table(name = "filter")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Platform platform;

    @Column(nullable = false, length = 255)
    String spot;

    @Column(nullable = false)
    boolean enabled;

    @Column(name = "pattern", nullable = false, columnDefinition = "jsonb")
    String jsonPattern;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    User user;
}
