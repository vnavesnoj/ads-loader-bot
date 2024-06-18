package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vnavesnoj.ads_loader_bot_common.constant.Analyzer;
import vnavesnoj.ads_loader_bot_common.constant.Platform;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Entity
@Table(name = "spot", uniqueConstraints = @UniqueConstraint(columnNames = {"platform", "url"}))
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 7)
    Platform platform;

    @Column(nullable = false, length = 255)
    String url;

    @Column(nullable = false, length = 255)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    Analyzer analyzer;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Category category;
}
