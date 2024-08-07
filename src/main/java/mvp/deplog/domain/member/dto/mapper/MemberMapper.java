package mvp.deplog.domain.member.dto.mapper;

import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.dto.response.MyInfoRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {

    @Mapping(source = "id", target = "memberId")
    @Mapping(source = "name", target = "memberName")
    MyInfoRes memberToMyInfo(Member member);
}
