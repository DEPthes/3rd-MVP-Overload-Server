package mvp.deplog.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvp.deplog.domain.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role; //USER, ADMIN

    @Enumerated(EnumType.STRING)
    private Part part;

    private Integer generation;

    private Boolean isVerified;

    private Boolean isApproval;

    // etc ..

    @Builder
    public User(String email, String password, String username, Integer generation, Part part) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = Role.USER;
        this.generation = generation;
        this.part = part;
        this.isVerified = false;
        this.isApproval = false;
    }
}
