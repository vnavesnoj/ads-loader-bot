package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnTransformer;
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
@Builder
@Entity
@Table(name = "filter_builder")
public class FilterBuilder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Platform platform;

    @Column(name = "pattern", nullable = false, columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    String pattern;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    User user;
}
