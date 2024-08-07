package mvp.deplog.domain.member.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.Member;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.domain.member.dto.mapper.MemberMapper;
import mvp.deplog.domain.member.dto.response.MyInfoRes;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public SuccessResponse<MyInfoRes> getMyInfo(UserDetailsImpl userDetails) {
        Member member = userDetails.getMember();
        MyInfoRes myInfoRes = memberMapper.memberToMyInfo(member);

        return SuccessResponse.of(myInfoRes);
    }
}
