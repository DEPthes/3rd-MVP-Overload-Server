package mvp.deplog.domain.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import mvp.deplog.domain.member.domain.Part;

@Data
@Builder
public class MyInfoRes {

    @Schema(type = "Long", example = "1", description= "회원의 id입니다.")
    private Long memberId;

    @Schema(type = "String", example = "www.abcd.jpg", description= "회원의 아바타 이미지입니다.")
    private String avatarImage;

    @Schema(type = "String", example = "홍길동", description= "회원의 이름입니다.")
    private String memberName;

    @Schema(type = "ENUM(Part)", example = "SERVER", description= "회원의 파트입니다.", allowableValues = {"PLAN", "DESIGN", "ANDROID", "WEB", "SERVER"})
    private Part part;

    @Schema(type = "String", example = "deplog@naver.com", description= "회원의 계정 이메일입니다.")
    private String email;
}
