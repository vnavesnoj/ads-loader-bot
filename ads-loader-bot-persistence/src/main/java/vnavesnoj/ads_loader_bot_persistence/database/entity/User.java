package vnavesnoj.ads_loader_bot_persistence.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vnavesnoj.ads_loader_bot_common.constant.ChatStateEnum;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 31)
    ChatStateEnum chatState;
}
