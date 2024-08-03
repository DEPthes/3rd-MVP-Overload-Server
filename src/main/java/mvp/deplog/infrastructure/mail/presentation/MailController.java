package mvp.deplog.infrastructure.mail.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.infrastructure.mail.dto.MailCodeRes;
import mvp.deplog.infrastructure.mail.application.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/mails")
public class MailController implements MailApi {

    private final MailService mailService;

    @Override
    @GetMapping
    public ResponseEntity<SuccessResponse<MailCodeRes>> sendMail(@RequestParam String email) throws Exception {
        return ResponseEntity.ok(mailService.sendMail(email));
    }

    @Override
    @GetMapping(value = "/verify")
    public void verify(@RequestParam String code, HttpServletResponse response) throws IOException {
        mailService.verifyCode(code);
        String redirect_uri = "http://www.naver.com";
        response.sendRedirect(redirect_uri);
    }
}
