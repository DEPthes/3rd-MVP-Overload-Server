package mvp.deplog.domain.scrap.presentation;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.scrap.application.ScrapService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/scrap")
public class ScrapController implements ScrapApi {

    private final ScrapService scrapService;
}
