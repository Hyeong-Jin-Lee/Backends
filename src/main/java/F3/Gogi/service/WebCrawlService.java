package F3.Gogi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebCrawlService {

    private WebDriver webDriver;

    public Map<String, String> crawlEkapepia() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\MSI\\Desktop\\chromedriver-win64\\chromedriver.exe"); // chromedriver 경로 설정


        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        String url = "https://www.ekapepia.com/priceComparison/poducerPrice/retail/periodPrice.do?menuId=menu100236&boardInfoNo=";
        webDriver.get(url);

        Map<String, String> resultData = new LinkedHashMap<>();
        String[] items = {"samgyeopsal", "moksim", "galbi", "apdari"};
        String[] itemValues = {"27", "68", "28", "25"};

        // 돼지 선택
        try {
            Select firstDropdown = new Select(webDriver.findElement(By.id("searchCondition1")));
            firstDropdown.selectByValue("4304"); // 돼지 value 선택

            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("searchCondition2"))); // 두 번째 드롭다운이 로드될 때까지 대기

            for (int i = 0; i < items.length; i++) {
                String item = items[i];
                String itemValue = itemValues[i];

                // 각각의 항목 선택
                Select secondDropdown = new Select(webDriver.findElement(By.id("searchCondition2")));
                secondDropdown.selectByValue(itemValue);

                // 검색 버튼 클릭
                WebElement searchButton = webDriver.findElement(By.id("ipt_search"));
                searchButton.click();

                // 페이지가 로드되기를 기다림
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

                // 데이터 크롤링
                List<WebElement> headerCells = webDriver.findElements(By.cssSelector("table thead tr th"));
                List<WebElement> rows = webDriver.findElements(By.cssSelector("table tbody tr"));
                if (!rows.isEmpty()) {
                    WebElement latestRow = rows.get(0); // 첫 행 선택
                    List<WebElement> cells = latestRow.findElements(By.tagName("td"));
                    String date = headerCells.get(headerCells.size()-3).getText(); // 10번째 th 요소의 텍스트 가져오기

                    if (cells.size() >= 2) {
                        String avgPrice = cells.get(cells.size()-3).getText();

                        if (i == 0) {
                            resultData.put("date", date);
                        }
                        resultData.put(item, avgPrice);
                    }
                }
            }

        } catch (Exception e) {
            log.error("데이터 크롤링 중 오류 발생: ", e);
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }
        return resultData;
    }
}
