package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "users")
public class User {

    @Id
    Long id;

    @Column(length = 7)
    String languageCode;

    @Column(nullable = false, columnDefinition = "timestamp")
    Instant instant;

    @Column(nullable = false)
    boolean notify;
}
