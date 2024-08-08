package mvp.deplog.domain.member.application;

import mvp.deplog.domain.member.dto.response.MyInfoRes;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    SuccessResponse<MyInfoRes> getMyInfo(UserDetailsImpl userDetails);

    SuccessResponse<Message> modifyAvatar(UserDetailsImpl userDetails, MultipartFile multipartFile);
}
