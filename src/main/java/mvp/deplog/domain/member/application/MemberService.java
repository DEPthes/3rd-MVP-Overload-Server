package mvp.deplog.domain.member.application;

import mvp.deplog.domain.member.dto.response.MyInfoRes;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;

public interface MemberService {

    SuccessResponse<MyInfoRes> getMyInfo(UserDetailsImpl userDetails);
}
