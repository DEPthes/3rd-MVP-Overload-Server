package mvp.deplog.domain.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mvp.deplog.domain.common.BaseEntity;
import mvp.deplog.domain.member.dto.request.ModifyAvatarReq;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "generation", columnDefinition = "TINYINT", nullable = false)
    @Min(value = 1)
    private int generation;

    @Column(name = "avatar_face")
    private String avatarFace;

    @Column(name = "avatar_body")
    private String avatarBody;

    @Column(name = "avatar_eyes")
    private String avatarEyes;

    @Column(name = "avatar_nose")
    private String avatarNose;

    @Column(name = "avatar_mouth")
    private String avatarMouth;

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateAvatar(ModifyAvatarReq modifyAvatarReq) {
        this.avatarFace = modifyAvatarReq.getAvatarFace();
        this.avatarBody = modifyAvatarReq.getAvatarBody();
        this.avatarEyes = modifyAvatarReq.getAvatarEyes();
        this.avatarNose = modifyAvatarReq.getAvatarNose();
        this.avatarMouth = modifyAvatarReq.getAvatarMouth();
    }

    @Builder
    public Member(String email, String password, String name, int generation, Part part) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = Role.APPLICANT;
        this.generation = generation;
        this.part = part;
        this.avatarFace = null;
        this.avatarBody = null;
        this.avatarEyes = null;
        this.avatarNose = null;
        this.avatarMouth = null;
    }
}
