package loginTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tests {

    WebDriver driver;
    String baseUrl;

    @BeforeMethod
    public void Set() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/nkozh/Desktop/Ira/Webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        baseUrl = "http://automationpractice.com/";
    }

    @Test
    public void testEnterExistingEmail() {
        driver.get(baseUrl);
        WebElement signInButton = driver.findElement(By.className("login"));
        signInButton.click();

        WebElement emailField = driver.findElement(By.id("email_create"));
        emailField.sendKeys("test@test.com");

        WebElement createAccountButton = driver.findElement(By.id("SubmitCreate"));
        createAccountButton.click();
        boolean errorMessage = driver.findElement(By.xpath("//li[contains(text(), 'An account using this email address has')]")).isDisplayed();
        Assert.assertTrue(errorMessage, "Asserting error message here");


    }

    @Test
    public void registerNewUser(){
        driver.get(baseUrl);
        WebElement signInButton = driver.findElement(By.className("login"));
        signInButton.click();

        /*  Generate random e-mail  */


        StringBuilder email;
        StringBuilder randomString = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 8; i++){
            randomString.append((char) (97 + rand.nextInt(25)));
        }

        email = randomString.append("@mail.com");

        WebElement emailField = driver.findElement(By.id("email_create"));
        emailField.sendKeys(email);

        WebElement createAccountButton = driver.findElement(By.id("SubmitCreate"));
        createAccountButton.click();

        WebElement gender = driver.findElement(By.id("id_gender1"));
        gender.click();

        WebElement firstName = driver.findElement(By.xpath("//input[@id='customer_firstname']"));
        firstName.sendKeys(randomString);

        WebElement lastName = driver.findElement(By.id("customer_lastname"));
        lastName.sendKeys(randomString.append(Math.random()));

        WebElement password = driver.findElement(By.id("passwd"));
        lastName.sendKeys("newUserpassword");

        Select day = new Select(driver.findElement(By.id("days")));
        day.selectByValue(String.valueOf(rand.nextInt(31-1) + 1));

        Select month = new Select(driver.findElement(By.id("months")));
        month.selectByIndex(rand.nextInt(12 - 1) + 1);

        Select year = new Select(driver.findElement(By.id("years")));
        month.selectByIndex(rand.nextInt(121 - 1) + 1);








    }

    @AfterMethod
    private void SetDown() {
        driver.quit();
    }
}
