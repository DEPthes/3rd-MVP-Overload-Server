package mvp.deplog.domain;

import io.swagger.v3.oas.annotations.Hidden;
import mvp.deplog.domain.user.domain.User;
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

//        User user = userDetails.getUser();
//        return "Test Success !" +
//                "\n id : " + user.getId() +
//                "\n username : " + user.getUsername() +
//                "\n email : " + user.getEmail() +
//                "\n role : " + user.getRole() +
//                "\n part : " + user.getPart() +
//                "\n generation : " + user.getGeneration() +
//                "\n isVerified : " + user.getIsVerified() +
//                "\n isApproval : " + user.getIsApproval() +
//                "\n HELLO !! "
//                ;

    }
}
