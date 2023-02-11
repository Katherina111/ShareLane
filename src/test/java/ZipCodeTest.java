import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ZipCodeTest {

    WebDriver driver;

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @DataProvider
    public Object[][] zipCodeValidateResults() {
        return new Object[][] {
            {"12345", true},
            {"22222", true},
            {"1234555555", false},
            {"123", false},
            {"   ", false},
            {"", false},
            {"asd", false},
            {"лдор", false},
            {"№-%", false},
            {"1sd 12", false},
            {"1 2 3", false}};
    }
    @Test (dataProvider = "zipCodeValidateResults")
    public void validZipCode(String zipCode, boolean toBeValid){
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement zipCodeInput = driver.findElement(By.name("zip_code"));
        zipCodeInput.sendKeys(String.valueOf(zipCode));
        driver.findElement(By.cssSelector("input[value='Continue']")).click();

        WebElement registerButton = driver.findElement(By.cssSelector("input[value='Register']"));
        Assert.assertEquals(registerButton.isDisplayed(), toBeValid, "ZIP code is incorrect");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
