package mvp.deplog.domain.scrap.application;

import lombok.RequiredArgsConstructor;
import mvp.deplog.domain.scrap.domain.repository.ScrapRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ScrapService {

    private final ScrapRepository scrapRepository;
}
