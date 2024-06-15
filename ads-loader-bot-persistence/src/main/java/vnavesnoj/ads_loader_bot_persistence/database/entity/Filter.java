package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "user")
@Builder
@Entity
@Table(name = "filter", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "user_id"}))
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, length = 15)
    String name;

    @Column(length = 63)
    String description;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Spot spot;

    @Column(nullable = false)
    boolean enabled;

    @Column(name = "pattern", nullable = false, columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    String jsonPattern;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    User user;
}
