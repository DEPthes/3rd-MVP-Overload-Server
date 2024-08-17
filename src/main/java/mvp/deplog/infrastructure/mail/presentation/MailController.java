package mvp.deplog.infrastructure.mail.presentation;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.infrastructure.mail.dto.MailCodeRes;
import mvp.deplog.infrastructure.mail.application.MailService;
import mvp.deplog.infrastructure.mail.dto.MailVerifyRes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/mails")
public class MailController implements MailApi {

    private final MailService mailService;

    @Override
    @ResponseBody
    @GetMapping
    public ResponseEntity<SuccessResponse<MailCodeRes>> sendMail(@RequestParam String email) throws Exception {
        return ResponseEntity.ok(mailService.sendMail(email));
    }

    @Override
    @GetMapping(value = "/verify")
    public String verify(@RequestParam String code, HttpServletResponse response) throws IOException {
        return mailService.verifyCode(code);
    }

    @Override
    @GetMapping("/verified")
    public ResponseEntity<SuccessResponse<MailVerifyRes>> checkVerify(@RequestParam String email) {
        return ResponseEntity.ok(mailService.checkVerify(email));
    }
}
