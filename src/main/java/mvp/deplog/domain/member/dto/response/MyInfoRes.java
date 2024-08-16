package mvp.deplog.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mvp.deplog.domain.member.domain.Part;
import mvp.deplog.domain.member.dto.Avatar;

@Data
@Builder
public class MyInfoRes {

    @Schema(type = "Long", example = "1", description= "회원의 id입니다.")
    private Long memberId;

    @Schema(type = "Avatar", description = "작성자의 아바타 이미지 객체입니다.")
    private Avatar avatar;

    @Schema(type = "String", example = "홍길동", description= "회원의 이름입니다.")
    private String memberName;

    @Schema(type = "ENUM(Part)", example = "SERVER", description= "회원의 파트입니다.", allowableValues = {"PLAN", "DESIGN", "ANDROID", "WEB", "SERVER"})
    private Part part;

    @Schema(type = "String", example = "deplog@naver.com", description= "회원의 계정 이메일입니다.")
    private String email;
}
