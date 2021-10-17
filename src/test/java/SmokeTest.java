import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class SmokeTest {

    WebDriver driver;
    String baseUrl;

    @BeforeMethod
    public void Set() {
//
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "http://automationpractice.com/";
    }

    @AfterMethod
    private void SetDown() {
        driver.quit();
    }

    @Test
    public void AddToCart() {

        driver.get(baseUrl);
        WebElement womenCategory = driver.findElement(By.xpath("//li/a[@title='Women']"));
        womenCategory.click();

        WebElement topsCategory = driver.findElement(By.xpath("//div/a[@title='Tops']"));
        topsCategory.click();

        WebElement productCard = driver.findElement(By.xpath("//div[@class='left-block']/div/a[@title='Faded Short Sleeve T-shirts']"));
        Actions action = new Actions(driver);
        action.moveToElement(productCard);
        action.perform();

        WebElement moreButton = driver.findElement(By.xpath("//span[text()='More']"));
        moreButton.click();

        Select size = new Select(driver.findElement(By.id("group_1")));
        size.selectByValue("2");

        WebElement color = driver.findElement(By.id("color_14"));
        color.click();

        WebElement addToCart = driver.findElement(By.id("add_to_cart"));
        addToCart.click();

        WebElement message = driver.findElement(By.xpath("//i[@class='icon-ok']/.."));

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(message));

        String actualResult = message.getText();
        String expectedResult = "Product successfully added to your shopping cart";
        Assert.assertEquals(actualResult, expectedResult);
    }


}
