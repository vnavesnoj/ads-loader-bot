package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnTransformer;

/**
 * @author vnavesnoj
 * @mail vnavesnoj@gmail.com
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "ad")
@Builder
@Entity
@Table(name = "ad_body")
public class AdBody {

    @Id
    Long adId;

    @Column(name = "body", nullable = false, columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    String jsonBody;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(unique = true, nullable = false)
    @MapsId
    Ad ad;
}
