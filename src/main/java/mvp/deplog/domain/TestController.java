package mvp.deplog.domain;

import io.swagger.v3.oas.annotations.Hidden;
import mvp.deplog.global.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "\n name : " + authentication.getName() +
                "\n getPrincipal : " + authentication.getPrincipal() +
                "\n getAuthorities : " + authentication.getAuthorities() +
                "\n getDetails : " + authentication.getDetails() +
                "\n getCredentials : " + authentication.getCredentials() +
                "\n HELLO !! ";

//        Member member = userDetails.getMember();
//        return "Test Success !" +
//                "\n id : " + member.getId() +
//                "\n name : " + member.getName() +
//                "\n email : " + member.getEmail() +
//                "\n role : " + member.getRole() +
//                "\n part : " + member.getPart() +
//                "\n generation : " + member.getGeneration() +
//                "\n isVerified : " + member.getIsVerified() +
//                "\n isApproval : " + member.getIsApproval() +
//                "\n HELLO !! "
//                ;

    }
}
