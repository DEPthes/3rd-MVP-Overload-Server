package mvp.deplog.infrastructure.mail.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import mvp.deplog.infrastructure.mail.dto.MailCodeRes;
import mvp.deplog.infrastructure.mail.application.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SuccessResponse<Message>> verify(@RequestParam String code) {
        return ResponseEntity.ok(mailService.verifyCode(code));
    }
}
