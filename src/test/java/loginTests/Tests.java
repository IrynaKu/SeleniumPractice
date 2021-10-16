package loginTests;

import io.github.bonigarcia.wdm.WebDriverManager;
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
       // System.setProperty("webdriver.chrome.driver", "C:/Users/nkozh/Desktop/Ira/Webdriver/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
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
    public void testRegisterNewUser() {
        driver.get(baseUrl);
        WebElement signInButton = driver.findElement(By.className("login"));
        signInButton.click();

        /*  Generate random e-mail  */


        StringBuilder email;
        StringBuilder randomString = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < 8; i++) {
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
        firstName.sendKeys("FirstName");

        WebElement lastName = driver.findElement(By.id("customer_lastname"));
        lastName.sendKeys("LastName");

        WebElement password = driver.findElement(By.id("passwd"));
        password.sendKeys("newUserpassword");

        Select day = new Select(driver.findElement(By.id("days")));
        day.selectByValue(String.valueOf(rand.nextInt(31 - 1) + 1));

        Select month = new Select(driver.findElement(By.id("months")));
        month.selectByIndex(rand.nextInt(12 - 1) + 1);

        Select year = new Select(driver.findElement(By.id("years")));
        year.selectByIndex(rand.nextInt(121 - 1) + 1);

        WebElement newsletter = driver.findElement(By.id("newsletter"));
        newsletter.click();

//        WebElement firstNameField = driver.findElement(By.id ("firstname"));
//        firstNameField.sendKeys("TestFirstName");
//
//        WebElement lastNameField = driver.findElement(By.id("lastname"));
//        lastNameField.sendKeys("TestLastName");

        WebElement address = driver.findElement(By.id("address1"));
        address.sendKeys("Test street " /*+ rand.nextInt(100)*/);

        WebElement city = driver.findElement(By.id("city"));
        city.sendKeys("Test City");

        Select state = new Select(driver.findElement(By.id("id_state")));
        state.selectByIndex(rand.nextInt(54 - 1) + 1);

        WebElement postcode = driver.findElement(By.id("postcode"));
        postcode.sendKeys(String.valueOf(rand.nextInt(99999 - 10000) + 10000));

        Select country = new Select(driver.findElement(By.id("id_country")));
        country.selectByValue("21");

        WebElement mobile = driver.findElement(By.id("phone_mobile"));
        mobile.sendKeys("+126754378921");

        WebElement alias = driver.findElement(By.id("alias"));
        alias.sendKeys("My address alias");

        WebElement registerButton = driver.findElement(By.id("submitAccount"));
        registerButton.click();

        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=my-account");

    }

    @AfterMethod
    private void SetDown() {
        driver.quit();
    }
}
