package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnTransformer;

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

    @Column(name = "pattern", nullable = false, columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    String pattern;

    @Column(length = 31)
    String currentInput;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    Spot spot;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    User user;
}
