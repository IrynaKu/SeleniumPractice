package searchTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SearchTests {

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


    @Test
    public void checkSearch() {
        driver.get(baseUrl);
        WebElement searchField = driver.findElement(By.id("search_query_top"));
        searchField.sendKeys("dress");

        WebElement submitButton = driver.findElement(By.name("submit_search"));
        submitButton.click();


        List<WebElement> productsList = driver.findElements(By.xpath("//a[@class='product-name']"));

        for (int i = 0; i < productsList.size(); i++) {
            String productName = productsList.get(i).getText();
            Assert.assertTrue(productName.contains("Dress"));
        }
    }

    @AfterMethod
    private void SetDown() {
        driver.quit();
    }
}


