package mvp.deplog.infrastructure.mail.application;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.member.domain.repository.MemberRepository;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.infrastructure.mail.dto.MailCodeRes;
import mvp.deplog.infrastructure.mail.MailUtil;
import mvp.deplog.infrastructure.mail.dto.MailVerifyRes;
import mvp.deplog.infrastructure.redis.RedisUtil;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring6.SpringTemplateEngine;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MailService {

    private final String VERIFY_SUFFIX = "_verify";

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final RedisUtil redisUtil;
    private final MailUtil mailUtil;

    private final MemberRepository memberRepository;

    public SuccessResponse<MailCodeRes> sendMail(String email) throws Exception {
        String code = mailUtil.generateCode();
        redisUtil.setDataExpire(code, email, 60 * 3L);

        MimeMessage mimeMessage = mailUtil.createMessage(code, email);
        mailSender.send(mimeMessage);

        MailCodeRes mailCodeRes = MailCodeRes.builder()
                .code(code)
                .build();

        return SuccessResponse.of(mailCodeRes);
    }

    public String verifyCode(String code) {
        String email = redisUtil.getData(code);
        if (email == null) {
//            throw new IllegalArgumentException("유효하지 않은 코드입니다.");
            return "fail";
        }
        redisUtil.deleteData(code);
        redisUtil.setDataExpire(email + VERIFY_SUFFIX, String.valueOf(true), 60 * 60L);
        return "success";
    }

    public SuccessResponse<MailVerifyRes> checkVerify(String email) {
        String data = redisUtil.getData(email + "_verify");
        boolean verified = data != null;

        if (verified)
            redisUtil.deleteData(email + "_verify");

        MailVerifyRes mailVerifyRes = MailVerifyRes.builder()
                .verified(verified)
                .build();

        return SuccessResponse.of(mailVerifyRes);
    }
}
