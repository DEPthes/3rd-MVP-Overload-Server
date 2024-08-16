package mvp.deplog.domain.member.dto.mapper;

import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.dto.Avatar;
import mvp.deplog.domain.member.dto.response.MyInfoRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    @Mapping(source = "id", target = "memberId")
    @Mapping(source = "name", target = "memberName")
    @Mapping(source = "member", target = "avatar", qualifiedByName = "mapToAvatar")
    MyInfoRes memberToMyInfo(Member member);

    @Named("mapToAvatar")
    default Avatar mapToAvatar(Member member) {
        if (member == null) {
            return null;
        }

        return Avatar.builder()
                .avatarFace(member.getAvatarFace())
                .avatarBody(member.getAvatarBody())
                .avatarEyes(member.getAvatarEyes())
                .avatarNose(member.getAvatarNose())
                .avatarMouth(member.getAvatarMouth())
                .build();
    }
}
