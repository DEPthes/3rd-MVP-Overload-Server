package mvp.deplog.domain.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvp.deplog.domain.common.BaseEntity;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "part", nullable = false)
    private Part part;

    @Column(name = "generation", nullable = false)
    private int generation;

    @Column(name = "avatar_image")
    private String avatarImage;

    @Builder
    public Member(String email, String password, String name, int generation, Part part) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = Role.MEMBER;
        this.generation = generation;
        this.part = part;
        this.avatarImage = null;
    }
}
