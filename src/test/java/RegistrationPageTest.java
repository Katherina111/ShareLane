import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegistrationPageTest {

    private WebDriver driver;

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @DataProvider
    public Object[][] registrationDataValidateResults() {
        return new Object[][]{
                {"Anna", "Ivanova", "123@mail.ru", 12345, 12345, true}, //valid case
                {"Anna", "", "123@mail.ru", 12345, 12345, true}, //valid case
                {"", "Ivanova", "123@mail.ru", 12345, 12345, false},
                {"", "", "123@mail.ru", 12345, 12345, false},
                {"  ", "  ", "123@mail.ru", 12345, 12345, false},
                {"123", "", "123@mail.ru", 12345, 12345, false},
                {"Anna", "123", "123@mail.ru", 12345, 12345, false},
                {"#$%", "", "123@mail.ru", 12345, 12345, false},
                {"Anna", "", "123@mail.ru", 12345, 12345, false},
                {"Anna", "", "123@m", 12345, 12345, false},
                {"", "", "", 12345, 12345, false},
                {"Anna", "", "", 12345, 12345, false},
                {"Anna", "", "123 @m.ru", 12345, 12345, false},
                {"Anna", "", "#$%@mail.ru", 12345, 12345, false},
                {"Anna", "", "123@mail.ru", 12345, "", false},
                {"Anna", "", "123@mail.ru", 12345, "  ", false},
                {"Anna", "", "123@mail.ru", 12345, 123, false},
                {"Anna", "", "123@mail.ru", 12345, 54321, false}
        };
    }

    @Test(dataProvider = "registrationDataValidateResults")
    public void testRegistrationPage (String firstName, String lastName, String email, int password1, int password2, boolean toBeValid) {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        WebElement firstNameInput = driver.findElement(By.name("first_name"));
        firstNameInput.sendKeys(String.valueOf(firstName));
        WebElement lastNameInput = driver.findElement(By.name("last_name"));
        lastNameInput.sendKeys(String.valueOf(lastName));
        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.sendKeys(String.valueOf(email));
        WebElement password1Input = driver.findElement(By.name("password1"));
        password1Input.sendKeys(String.valueOf(password1));
        WebElement password2Input = driver.findElement(By.name("password2"));
        password2Input.sendKeys(String.valueOf(password2));
        driver.findElement(By.cssSelector("input[value='Register']")).click();

        WebElement validateMessage = driver.findElement(By.className("confirmation_message"));
        Assert.assertEquals(validateMessage.isDisplayed(), toBeValid, "Input incorrect data");
    }
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
