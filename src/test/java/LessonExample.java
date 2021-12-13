import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LessonExample {

    @ParameterizedTest
    @CsvSource({"Смартфон Huawei P40 lite (ярко-зеленый), '625,00 р.'"})
    public void testFindMobileHuawei(String expectedPhoneName, String expectedPhonePrice) {
        WebDriver driver = null;
        try {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get("https://onliner.by");
            driver.findElement(By.xpath("//input[@class='fast-search__input']")).sendKeys("Huawei P40 Lite");
            driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@class='modal-iframe']")));
            new WebDriverWait(driver, 10)
                    .withMessage("Can't find green Huawei")
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'ярко-зеленый')]"))).click();
            driver.switchTo().defaultContent();

            String actualPhoneName = driver.findElement(By.cssSelector("h1.catalog-masthead__title")).getText();
            String actualPhonePrice = driver.findElement(
                    By.xpath("//a[@class='offers-description__link offers-description__link_nodecor']")).getText();

            Assertions.assertEquals(expectedPhoneName, actualPhoneName, "Phone name is not correct");
            Assertions.assertEquals(expectedPhonePrice, actualPhonePrice, "Phone price is not correct");

            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

}