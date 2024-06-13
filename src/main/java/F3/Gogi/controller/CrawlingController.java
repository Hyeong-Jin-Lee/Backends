package F3.Gogi.controller;

import F3.Gogi.service.WebCrawlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/crawl")
@RequiredArgsConstructor
public class CrawlingController {

    private final WebCrawlService webCrawlService;

    @GetMapping("/price")
    public Map<String, String> crawlEkapepia() {
        return webCrawlService.crawlEkapepia();
    }
}