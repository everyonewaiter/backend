package com.handwoong.everyonewaiter.user.infrastructure;

import static com.handwoong.everyonewaiter.user.domain.Username.USERNAME_MAX_LENGTH;

import com.handwoong.everyonewaiter.common.domain.PhoneNumber;
import com.handwoong.everyonewaiter.common.infrastructure.BaseEntity;
import com.handwoong.everyonewaiter.user.domain.Password;
import com.handwoong.everyonewaiter.user.domain.User;
import com.handwoong.everyonewaiter.user.domain.UserId;
import com.handwoong.everyonewaiter.user.domain.UserRole;
import com.handwoong.everyonewaiter.user.domain.UserStatus;
import com.handwoong.everyonewaiter.user.domain.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = USERNAME_MAX_LENGTH, unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Column(length = 20)
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private Long lastLoggedIn;

    public static UserEntity from(final User user) {
        final UserEntity userEntity = new UserEntity();
        userEntity.id = Objects.isNull(user.getId()) ? null : user.getId().value();
        userEntity.username = user.getUsername().toString();
        userEntity.password = user.getPassword().toString();
        userEntity.phoneNumber = user.getPhoneNumber().toString();
        userEntity.role = user.getRole();
        userEntity.status = user.getStatus();
        userEntity.lastLoggedIn = user.getLastLoggedIn();
        return userEntity;
    }

    public User toModel() {
        return User.builder()
            .id(new UserId(id))
            .username(new Username(username))
            .password(new Password(password))
            .phoneNumber(new PhoneNumber(phoneNumber))
            .role(role)
            .status(status)
            .lastLoggedIn(lastLoggedIn)
            .timestamp(getDomainTimestamp())
            .build();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final UserEntity that)) {
            return false;
        }
        return Objects.nonNull(id) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
