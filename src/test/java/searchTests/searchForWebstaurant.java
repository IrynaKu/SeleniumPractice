package searchTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class searchForWebstaurant {
    WebDriver driver;
    String baseUrl;

    @BeforeMethod
    public void set() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "https://www.webstaurantstore.com/";
    }

//    @AfterMethod
//    private void SetDown() {
//        driver.quit();
//    }

    private static void checkSearchResult(List<WebElement> list, String string) {
        for (int i = 0; i < list.size(); i++) {
            String productName = list.get(i).getText();
            if (productName.contains(string) == false) {
                System.out.print(productName);
            } else {
                Assert.assertTrue(productName.contains(string));
            }

        }
    }


    @Test
    public void checkSearch() {
        driver.get(baseUrl);

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Accept')]")));

        WebElement acceptButton = driver.findElement(By.xpath("//button[contains(text(),'Accept')]"));
        acceptButton.click();

        WebElement searchField = driver.findElement(By.id("searchval"));
        searchField.sendKeys("stainless work table");

        WebElement searchButton = driver.findElement(By.xpath("//button[@value = 'Search']"));
        searchButton.click();

        List<WebElement> searchResult;
        do {
            searchResult = driver.findElements(By.xpath("//a[@data-testid='itemDescription']"));
            checkSearchResult(searchResult, "Table");
            WebElement nextPage = driver.findElement(By.xpath("//li[@class='rc-pagination-next']"));
            nextPage.click();
        }
        while (!driver.findElements(By.xpath("//li[@class='rc-pagination-next']")).isEmpty());

        List<WebElement> addToCartButtons = driver.findElements(By.xpath("//input[@data-testid='itemAddCart']"));
        WebElement lastItemButton = addToCartButtons.get(addToCartButtons.size()-1);
        lastItemButton.click();

        WebElement viewCartButton = driver.findElement(By.xpath("//a[contains(text(), 'View Cart')]"));
        viewCartButton.click();

        WebElement emptyCartButton = driver.findElement(By.xpath("//a[@class='emptyCartButton btn btn-mini btn-ui pull-right']"));
        emptyCartButton.click();

       WebDriverWait wait2 = new WebDriverWait(driver, 3);
       wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Empty Cart')]")));

        WebElement emptyCartButton2 = driver.findElement(By.xpath("//button[contains(text(), 'Empty Cart')]"));
        emptyCartButton2.click();

        WebDriverWait wait3 = new WebDriverWait(driver, 3);
        wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='header-1']")));

        WebElement emptyCartText = driver.findElement(By.xpath("//p[@class='header-1']"));
        Assert.assertEquals(emptyCartText.getText(), "Your cart is empty.");
    }
}


