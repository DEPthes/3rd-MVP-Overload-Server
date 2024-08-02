package mvp.deplog.infrastructure.mail;

import lombok.RequiredArgsConstructor;
import mvp.deplog.global.common.Message;
import mvp.deplog.global.common.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/mails")
public class MailController {

    private final MailService mailService;

    @GetMapping
    public ResponseEntity<SuccessResponse<MailCodeRes>> sendMail(@RequestParam String email) throws Exception {
        return ResponseEntity.ok(mailService.sendMail(email));
    }

    @GetMapping(value = "/verify")
    public ResponseEntity<SuccessResponse<Message>> verify(@RequestParam String code) {

        return ResponseEntity.ok(SuccessResponse.of(Message.builder().message("verify 호출").build()));
    }
}
